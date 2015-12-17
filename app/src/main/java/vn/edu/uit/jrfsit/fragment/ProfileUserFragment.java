// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package vn.edu.uit.jrfsit.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.ListViewCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import vn.edu.uit.jrfsit.R;
import vn.edu.uit.jrfsit.activity.AddSkillActivity;
import vn.edu.uit.jrfsit.activity.EditSkillActivity;
import vn.edu.uit.jrfsit.activity.EditUserExpectActivity;
import vn.edu.uit.jrfsit.activity.EditUserProfileActivity;
import vn.edu.uit.jrfsit.adapter.SkillArrayAdapter;
import vn.edu.uit.jrfsit.entity.Account;
import vn.edu.uit.jrfsit.entity.Skill;
import vn.edu.uit.jrfsit.entity.User;
import vn.edu.uit.jrfsit.preferences.AccountPreferences;
import vn.edu.uit.jrfsit.service.SkillService;
import vn.edu.uit.jrfsit.service.UserService;
import vn.edu.uit.jrfsit.utils.ListViewUtil;
import vn.edu.uit.jrfsit.utils.Utils;

// Referenced classes of package vn.edu.uit.jrfsit.fragment:
//            BaseFragment

public class ProfileUserFragment extends BaseFragment
    implements android.widget.AdapterView.OnItemClickListener
{

    static final int DATE_DIALOG_ID = 999;
    private static int REQUEST_ADD_SKILL = 2;
    private static int REQUEST_EDIT_EX = 4;
    private static int REQUEST_EDIT_PROFILE = 1;
    private static int REQUEST_EDIT_SKILL = 3;
    Account account;
    AccountPreferences accountPreferences;
    ArrayAdapter<Skill> adapter;
    AppCompatButton btAddSkill;
    AppCompatButton btEditExpect;
    AppCompatButton btEditProfile;
    private List<Skill> list;
    ListViewUtil listViewUtil;
    ListViewCompat lvSkill;
    ProgressDialog pDialog;
    SkillService skillService;
    int templItemClick;
    AppCompatTextView tvPsAddress;
    AppCompatTextView tvPsBirhday;
    AppCompatTextView tvPsEmail;
    AppCompatTextView tvPsGender;
    AppCompatTextView tvPsTNumber;
    AppCompatTextView tvUserEx;
    User user;
    UserService userService;
    View v;

    @Override
    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle)
    {
        v = layoutinflater.inflate(R.layout.content_user_profile, viewgroup, false);
        load();
        return v;
    }

    private void load()
    {
        super.loadActivity(R.string.title_activity_person);
        initControlOnView();
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Vui lòng chờ....");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();
        skillService = new SkillService();
        userService = new UserService();
        user = new User();
        list = new ArrayList();
        account = new Account();
        accountPreferences = new AccountPreferences(activity);
        account = accountPreferences.getAccount();
        templItemClick = 0;
        (new Thread(new Runnable() {
            public void run()
            {
                user = userService.getUser(account.getUserId());
                list = skillService.getListSkill(account.getUserId());
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        if (user != null)
                        {
                            setUser(user);
                        }
                        if (list != null)
                        {
                            adapter = new SkillArrayAdapter(activity, list);
                            lvSkill.setAdapter(adapter);
                            listViewUtil.setListViewHeightBasedOnChildrenAddSkill(lvSkill);
                        }
                        pDialog.dismiss();
                    }
                });
            }
        })).start();
        listViewUtil = new ListViewUtil();
        listViewUtil.setListViewHeightBasedOnChildren(lvSkill);
        initListener();
        pDialog.dismiss();
    }

    public void initControlOnView()
    {
        btEditProfile = (AppCompatButton) v.findViewById(R.id.bt_edit_personal_job_profile);
        tvPsEmail = (AppCompatTextView) v.findViewById(R.id.tv_ps_email_job_profile);
        tvPsTNumber = (AppCompatTextView) v.findViewById(R.id.tv_ps_sdt_job_profile);
        tvPsAddress = (AppCompatTextView) v.findViewById(R.id.tv_ps_address_job_profile);
        tvPsBirhday = (AppCompatTextView) v.findViewById(R.id.tv_ps_birthday_job_profile);
        tvPsGender = (AppCompatTextView) v.findViewById(R.id.tv_ps_gender_job_profile);

        btEditExpect = (AppCompatButton) v.findViewById(R.id.bt_edit_expect_user);
        tvUserEx = (AppCompatTextView) v.findViewById(R.id.tv_user_expect);

        btAddSkill = (AppCompatButton) v.findViewById(R.id.bt_add_skill_job_profile);
        lvSkill = (ListViewCompat) v.findViewById(R.id.lv_skill_job_profile);
    }

    public void initListener()
    {
        btAddSkill.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivityForResult(new Intent(activity.getApplicationContext(), AddSkillActivity.class), ProfileUserFragment.REQUEST_ADD_SKILL);
            }
        });
        lvSkill.setOnItemClickListener(this);
        lvSkill.setOnItemLongClickListener(new android.widget.AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView adapterview, View view, int i, long l)
            {
                (new android.app.AlertDialog.Builder(activity)).setMessage("Bạn thực sự muốn thoát không?").setPositiveButton("Có", dialogClickListener).setNegativeButton("Không", dialogClickListener).show();
                lvSkill.setTag(Integer.valueOf(i));
                return true;
            }
        });
        btEditProfile.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(activity.getApplicationContext(), EditUserProfileActivity.class);
                intent.putExtra("user", user);
                startActivityForResult(intent, ProfileUserFragment.REQUEST_EDIT_PROFILE);
            }
        });
        btEditExpect.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(activity.getApplicationContext(), EditUserExpectActivity.class);
                intent.putExtra("expect", tvUserEx.getText().toString());
                startActivityForResult(intent, ProfileUserFragment.REQUEST_EDIT_EX);
            }
        });
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    new Thread(new Runnable() {
                        public void run() {
                            final boolean check =skillService.deleteSkill(account.getUserId(),lvSkill.getTag().toString());
                            if (check) {
                            } else {
                                Utils.print(activity, "Cập nhật thất bại, kiểm tra kết nối");
                            }
                        }
                    }).start();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_EDIT_PROFILE) {
            if (resultCode == Activity.RESULT_OK) {
                pDialog.show();
                final User tempUser= (User) data.getSerializableExtra("result");

                if(tempUser!=null){
                    user = tempUser;
                    new Thread(new Runnable() {
                        public void run() {
                            final boolean check = userService.updateUser(account.getUserId(), tempUser.getFullName(),
                                    tempUser.getBirthday(),tempUser.getGender(),tempUser.getPhone(), tempUser.getAddress());
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (check) {
                                        setUser(user);
                                    } else {
                                        Utils.print(activity, "Cập nhật thất bại, kiểm tra kết nối");
                                    }
                                    pDialog.dismiss();
                                }
                            });
                        }
                    }).start();
                }
            }
        } else if (requestCode == REQUEST_ADD_SKILL) {
            if (resultCode == Activity.RESULT_OK) {
                pDialog.show();
                final Skill returnSkill = (Skill) data.getSerializableExtra("result");
                boolean check = false;
                if(list!=null){
                    for (Skill item:list) {
                        if(item.getId().equals(returnSkill.getId()))
                            check=true;
                    }
                }
                if(check){
                    new Thread(new Runnable() {
                        public void run() {
                            final boolean check = skillService.updateSkill(account.getUserId(), returnSkill.getId(), returnSkill.getExperience());
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (check) {
                                        Utils.print(activity, "Cập nhật kỹ năng thành công");
                                        adapter.getItem(templItemClick).setExperience(returnSkill.getExperience());
                                        lvSkill.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        Utils.print(activity, "Cập nhật kỹ năng thất bại, kiểm tra kết nối");
                                    }
                                    pDialog.dismiss();
                                }
                            });
                        }
                    }).start();
                }else {
                    new Thread(new Runnable() {
                        public void run() {
                            final boolean check =skillService.insertSkill(account.getUserId(), returnSkill.getId(), returnSkill.getExperience());
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (check) {
                                        Utils.print(activity, "Thêm kỹ năng thành công");
                                        if (list == null) {
                                            list = new ArrayList<Skill>();
                                            list.add(returnSkill);
                                            adapter = new SkillArrayAdapter(activity, list);
                                        } else {
                                            adapter.add(returnSkill);
                                        }

                                        adapter.notifyDataSetChanged();
                                        lvSkill.setAdapter(adapter);
                                        listViewUtil.setListViewHeightBasedOnChildrenAddSkill(lvSkill);
                                    } else {
                                        Utils.print(activity, "Thêm kỹ năng thất bại, kiểm tra kết nối");
                                    }
                                    pDialog.dismiss();
                                }
                            });
                        }
                    }).start();

                }
            }
        } else if (requestCode == REQUEST_EDIT_SKILL) {
            if (resultCode == Activity.RESULT_OK) {
                final Skill returnSkill = (Skill) data.getSerializableExtra("result");
                if (returnSkill != null) {
                    pDialog.show();
                    new Thread(new Runnable() {
                        public void run() {
                            final boolean check = skillService.updateSkill(account.getUserId(), returnSkill.getId(), returnSkill.getExperience());
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (check) {
                                        Utils.print(activity, "Cập nhật kỹ năng thành công");
                                        adapter.getItem(templItemClick).setExperience(returnSkill.getExperience());
                                        lvSkill.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        Utils.print(activity, "Cập nhật kỹ năng thất bại, kiểm tra kết nối");
                                    }
                                    pDialog.dismiss();
                                }
                            });
                        }
                    }).start();
                }
                // update data here
            }
        } else if (requestCode == REQUEST_EDIT_EX) {
            if (resultCode == Activity.RESULT_OK) {
                final String tempex=String.valueOf(data.getStringExtra("result"));
                if(tempex!=null){
                    pDialog.show();
                    new Thread(new Runnable() {
                        public void run() {
                            final boolean check = userService.updateCareerObjective(account.getUserId(), tempex);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (check) {
                                        tvUserEx.setText(tempex);
                                        user.setCareerObjective(tempex);
                                        Utils.print(activity, "Cập nhật thành công");
                                    } else {
                                        Utils.print(activity, "Cập nhật kỹ năng thất bại, kiểm tra kết nối");
                                    }
                                    pDialog.dismiss();
                                }
                            });
                        }
                    }).start();
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final int index = position;
        Intent intent = new Intent(activity, EditSkillActivity.class);
        Skill skill = list.get(position);
        intent.putExtra("skill1", skill);
        templItemClick = position;
        startActivityForResult(intent, REQUEST_EDIT_SKILL);
    }

    void setUser(User user1)
    {
        try
        {
            Thread.sleep(1000L);
        }
        catch (InterruptedException interruptedexception) { }
        tvPsEmail.setText(user1.getEmail());
        tvPsTNumber.setText(user1.getPhone());
        tvPsAddress.setText(user1.getAddress());
        tvPsBirhday.setText(user1.getBirthday());
        tvPsGender.setText(user1.getGender());
        tvUserEx.setText(user1.getCareerObjective());
    }
}
