// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package vn.edu.uit.jrfsit.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.MotionEvent;
import android.view.View;

import vn.edu.uit.jrfsit.R;
import vn.edu.uit.jrfsit.entity.Account;
import vn.edu.uit.jrfsit.preferences.AccountPreferences;
import vn.edu.uit.jrfsit.service.UserService;
import vn.edu.uit.jrfsit.utils.Utils;

public class ChangePassActivity extends AppCompatActivity
{

    Account account;
    AccountPreferences accountPreferences;
    private AppCompatButton btCancel;
    private AppCompatButton btSave;
    private AppCompatEditText etNewPass;
    private AppCompatEditText etOldPass;
    private AppCompatEditText etReNewPass;
    private UserService userService;

    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.content_change_pass);
        initControlOnView();
        initObject();
        initListener();
    }
    void initControlOnView()
    {
        etOldPass = (AppCompatEditText)findViewById(R.id.etSetting_old_pass);
        etNewPass = (AppCompatEditText)findViewById(R.id.etSetting_new_pass);
        etReNewPass = (AppCompatEditText)findViewById(R.id.etSetting_re_new_pass);
        btCancel = (AppCompatButton)findViewById(R.id.btSeting_pass_change_cancel);
        btSave = (AppCompatButton)findViewById(R.id.btSeting_pass_change_save);
    }

    void initListener()
    {
        btSave.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (etOldPass != null && etOldPass.getTag().equals("1") || !etOldPass.isActivated())
                {
                    if (etNewPass.getText() != null && !etNewPass.getText().equals(""))
                    {
                        final String  rePass = etReNewPass.getText().toString();
                        final String pass = etNewPass.getText().toString();
                        if (rePass.equals(pass))
                        {
                            new Thread(new Runnable() {
                                public void run() {
                                    final boolean check = userService.updatePassword(account.getUserId(), pass);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (check) {
                                                Utils.print(ChangePassActivity.this, "Đổi mật khẩu thành công");
                                                finish();
                                                return;
                                            } else {
                                                Utils.print(ChangePassActivity.this, "Cập nhật thất bại, kiểm tra kết nối");
                                                return;
                                            }
                                        }
                                    });
                                }
                            }).start();
                            return;
                        } else
                        {
                            Utils.print(ChangePassActivity.this, "Mật khẩu chưa trùng khớp");
                            return;
                        }
                    } else
                    {
                        Utils.print(ChangePassActivity.this, "Bạn chưa nhập mật khẩu mới");
                        return;
                    }
                } else {
                        Utils.print(ChangePassActivity.this, "Bạn chưa nhập mật khẩu cũ hoặc nhập sai");
                    return;
                }
            }
        });
        btCancel.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
        etNewPass.setOnTouchListener(new android.view.View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionevent)
            {
                String oldPass = "";
                if (etOldPass.getText() != null)
                {
                    oldPass = etOldPass.getText().toString();
                }
                if (Utils.md5(oldPass).equals(account.getPassword()) || account.isGoogle())
                {
                    etOldPass.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_check_ok), null);
                    etOldPass.setTag("1");
                } else
                {
                    etOldPass.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_delete), null);
                    etOldPass.setTag("0");
                }
                return false;
            }
        });
    }

    void initObject()
    {
        userService = new UserService();
        accountPreferences = new AccountPreferences(this);
        account = accountPreferences.getAccount();
        if (account.isGoogle())
        {
            etOldPass.setHint("Mặc định");
            etOldPass.setEnabled(false);
            etOldPass.setTag("1");
        }
    }
}
