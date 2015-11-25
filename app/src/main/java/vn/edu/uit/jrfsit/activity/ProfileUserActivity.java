package vn.edu.uit.jrfsit.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.ListViewCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;


import java.util.ArrayList;
import java.util.List;

import vn.edu.uit.jrfsit.R;
import vn.edu.uit.jrfsit.adapter.SkillArrayAdapter;
import vn.edu.uit.jrfsit.constants.Param;
import vn.edu.uit.jrfsit.entity.Skill;
import vn.edu.uit.jrfsit.entity.User;
import vn.edu.uit.jrfsit.service.SkillService;
import vn.edu.uit.jrfsit.service.UserService;
import vn.edu.uit.jrfsit.utils.ListViewUtil;
import vn.edu.uit.jrfsit.utils.Utils;

/**
 * Created by LeDuy on 10/29/2015.
 */
public class ProfileUserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {
    Toolbar toolbar;

    //region definition

    private static int REQUEST_EDIT_PROFILE = 1;
    private static int REQUEST_ADD_SKILL = 2;
    private static int REQUEST_EDIT_SKILL = 3;
    private static int REQUEST_EDIT_EX = 4;
    //profile

    ImageButton btEditProfile;
    AppCompatTextView tvPsName;
    AppCompatTextView tvPsEmail;
    AppCompatTextView tvPsTNumber;
    AppCompatTextView tvPsAddress;
    AppCompatTextView tvPsBirhday;
    AppCompatTextView tvPsGender;

    //Experience
    ImageButton btEditExpect;
    AppCompatTextView tvUserEx;

    //Skill
    //ImageButton btEditSkill;
    AppCompatButton btAddSkill;
    ListViewCompat lvSkill;

    //picker day
    private int year = 1993;
    private int month = 6;
    private int day = 15;
    static final int DATE_DIALOG_ID = 999;

    //List skill
    private List<Skill> list;
    ArrayAdapter<Skill> adapter;

    //List view util
    ListViewUtil listViewUtil;

    //Drawer bar
    DrawerLayout drawer;
    NavigationView navigationView;

    //Object
    User user;
    List<Skill> listSkill;
    SkillService skillService;
    UserService userService;
    int templItemClick;
    ProgressDialog pDialog;

    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        load();
    }

    /**
     * @param menuItem
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.nav_search) {
            startActivity(new Intent(getApplicationContext(), SearchJobActivity.class));
        } else if (id == R.id.nav_job_recomment) {
            startActivity(new Intent(getApplicationContext(), RecommendJobActivity.class));
        } else if (id == R.id.nav_job_save) {
            startActivity(new Intent(getApplicationContext(), SaveJobActivity.class));
        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * @param skill
     * @param experice
     * @return Skill
     */
    private Skill get(String skill, String experice) {
        return new Skill("1", skill, experice);
    }

    /**
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final int index = position;
        Intent intent = new Intent(getApplicationContext(), EditSkillActivity.class);
        Skill skill = list.get(position);
        intent.putExtra("skill1", skill);
        templItemClick = position;
        startActivityForResult(intent, REQUEST_EDIT_SKILL);

        // Toast.makeText(getBaseContext(), "Ban co muon xoa thang nay khong?"+String.valueOf(position), Toast.LENGTH_SHORT).show();
        //startActivity(new Intent(getApplicationContext(), JobActivity.class));
    }

    /**
     * maping controler
     */
    private void initControlOnView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_job_profile);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        tvPsName = (AppCompatTextView) findViewById(R.id.tv_name_user_profile);
        btEditProfile = (ImageButton) findViewById(R.id.bt_edit_personal_job_profile);
        tvPsEmail = (AppCompatTextView) findViewById(R.id.tv_ps_address_job_profile);
        tvPsTNumber = (AppCompatTextView) findViewById(R.id.tv_ps_sdt_job_profile);
        tvPsAddress = (AppCompatTextView) findViewById(R.id.tv_ps_address_job_profile);
        tvPsBirhday = (AppCompatTextView) findViewById(R.id.tv_ps_birthday_job_profile);
        tvPsGender = (AppCompatTextView) findViewById(R.id.tv_ps_gender_job_profile);

        btEditExpect = (ImageButton) findViewById(R.id.bt_edit_expect_user);
        tvUserEx = (AppCompatTextView) findViewById(R.id.tv_user_expect);

        //btEditSkill = (ImageButton) findViewById(R.id.bt_edit_skill_job_profile);
        btAddSkill = (AppCompatButton) findViewById(R.id.bt_add_skill_job_profile);
        lvSkill = (ListViewCompat) findViewById(R.id.lv_skill_job_profile);
    }

    /**
     * init listener
     */
    private void initListener() {
        navigationView.setNavigationItemSelectedListener(this);
        btAddSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), AddSkillActivity.class), REQUEST_ADD_SKILL);
            }
        });

        /*btEditSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditListSkillActivity.class);
                startActivityForResult(intent, REQUEST_EDIT_SKILL);
            }
        });*/
        lvSkill.setOnItemClickListener(this);

        lvSkill.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // delete item.
                //update data
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileUserActivity.this);
                builder.setMessage("Bạn thật sự muốn xóa kĩ năng này không?").setPositiveButton("Có", dialogClickListener)
                        .setNegativeButton("Không", dialogClickListener).show();
                lvSkill.setTag(position);
                return true;
            }
        });

        btEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditUserProfileActivity.class);
                intent.putExtra("user", user);
                startActivityForResult(intent, REQUEST_EDIT_PROFILE);
            }
        });

        btEditExpect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditUserExpectActivity.class);
                intent.putExtra("expect", tvUserEx.getText().toString());
                startActivityForResult(intent, REQUEST_EDIT_EX);
            }
        });
    }

    /**
     * DialogInterface listener delete skill
     */
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            //region switch
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    pDialog.show();
                    new Thread(new Runnable() {
                        public void run() {
                            final boolean check =skillService.deleteSkill(Param.user.getUserId(), list.get((Integer) lvSkill.getTag()).getId());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(check){
                                        Utils.print(ProfileUserActivity.this, "Thêm kỹ năng thành công");
                                        adapter.remove(list.get((Integer) lvSkill.getTag()));
                                        adapter.notifyDataSetChanged();
                                        lvSkill.setAdapter(adapter);
                                        listViewUtil.setListViewHeightBasedOnChildrenAddSkill(lvSkill);
                                    }else {
                                        Utils.print(ProfileUserActivity.this, "Thêm kỹ năng thất bại, kiểm tra kết nối");
                                    }
                                    pDialog.dismiss();
                                }
                            });
                        }
                    }).start();

                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
            //endregion
        }
    };

    // Load data
    private void loadData() {

        //User
        user = new User();
        /*user.setUserId("1");
        user.setFullName("Le Duy");
        user.setEmail("Leduy1561993@gmail.com");
        user.setAddress("Ho Chi Minh");
        user.setPhone("0909044291");
        user.setBirthday("1993-06-15");
        user.setGender("Nam");
        user.setCareerObjective("lao dong la thieng lieng");*/

        //Skill
        list = new ArrayList<Skill>();
        templItemClick = 0;
        new Thread(new Runnable() {
            public void run() {
                user = userService.getUser("1");
                list = skillService.getListSkill(Param.user.getUserId());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(user!=null){
                            setUser(user);
                        }
                        if (list != null) {
                            adapter = new SkillArrayAdapter(ProfileUserActivity.this, list);
                            lvSkill.setAdapter(adapter);
                            listViewUtil.setListViewHeightBasedOnChildrenAddSkill(lvSkill);
                        }
                    }
                });
            }
        }).start();
    }

    // Load control defaul
    private void load() {
        pDialog = new ProgressDialog(ProfileUserActivity.this);
        pDialog.setMessage("Đang xử lí, vui lòng đợi");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();
        initControlOnView();
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setTitle("Thông tin của bạn");
        skillService = new SkillService();
        userService = new UserService();


        //get user
        loadData();


        //set data apdapter

        listViewUtil = new ListViewUtil();
        listViewUtil.setListViewHeightBasedOnChildren(lvSkill);
        initListener();
        pDialog.dismiss();
    }

    /**
     * @param user
     */
    void setUser(User user) {
        tvPsName.setText(user.getFullName());
        tvPsEmail.setText(user.getEmail());
        tvPsTNumber.setText(user.getPhone());
        tvPsAddress.setText(user.getAddress());
        tvPsBirhday.setText(user.getBirthday());
        tvPsGender.setText(user.getGender());
        tvUserEx.setText(user.getCareerObjective());
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_EDIT_PROFILE) {
            if (resultCode == Activity.RESULT_OK) {
                pDialog.show();
                final User tempUser= (User) data.getSerializableExtra("result");

                if(tempUser!=null){
                    user = tempUser;
                    new Thread(new Runnable() {
                        public void run() {
                            final boolean check = userService.updateUser(Param.user.getUserId(), tempUser.getFullName(),
                                    tempUser.getBirthday(),tempUser.getGender(),tempUser.getPhone(), tempUser.getAddress());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (check) {
                                        setUser(user);
                                    } else {
                                        Utils.print(ProfileUserActivity.this, "Cập nhật thất bại, kiểm tra kết nối");
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
                            final boolean check = skillService.updateSkill(Param.user.getUserId(), returnSkill.getId(), returnSkill.getExperience());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (check) {
                                        Utils.print(ProfileUserActivity.this, "Cập nhật kỹ năng thành công");
                                        adapter.getItem(templItemClick).setExperience(returnSkill.getExperience());
                                        lvSkill.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        Utils.print(ProfileUserActivity.this, "Cập nhật kỹ năng thất bại, kiểm tra kết nối");
                                    }
                                    pDialog.dismiss();
                                }
                            });
                        }
                    }).start();
                }else {
                    new Thread(new Runnable() {
                        public void run() {
                            final boolean check =skillService.insertSkill(Param.user.getUserId(), returnSkill.getId(), returnSkill.getExperience());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(check){
                                        Utils.print(ProfileUserActivity.this, "Thêm kỹ năng thành công");
                                        if(list==null){
                                            list = new ArrayList<Skill>();
                                            list.add(returnSkill);
                                            adapter = new SkillArrayAdapter(ProfileUserActivity.this, list);
                                        }else {
                                            adapter.add(returnSkill);
                                        }

                                        adapter.notifyDataSetChanged();
                                        lvSkill.setAdapter(adapter);
                                        listViewUtil.setListViewHeightBasedOnChildrenAddSkill(lvSkill);
                                    }else {
                                        Utils.print(ProfileUserActivity.this, "Thêm kỹ năng thất bại, kiểm tra kết nối");
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
                            final boolean check = skillService.updateSkill(Param.user.getUserId(), returnSkill.getId(), returnSkill.getExperience());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (check) {
                                        Utils.print(ProfileUserActivity.this, "Cập nhật kỹ năng thành công");
                                        adapter.getItem(templItemClick).setExperience(returnSkill.getExperience());
                                        lvSkill.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        Utils.print(ProfileUserActivity.this, "Cập nhật kỹ năng thất bại, kiểm tra kết nối");
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
                            final boolean check = userService.updateCareerObjective(Param.user.getUserId(), tempex);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (check) {
                                        tvUserEx.setText(tempex);
                                        Utils.print(ProfileUserActivity.this, "Cập nhật thành công");
                                    } else {
                                        Utils.print(ProfileUserActivity.this, "Cập nhật kỹ năng thất bại, kiểm tra kết nối");
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
}
