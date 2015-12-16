package vn.edu.uit.jrfsit.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import vn.edu.uit.jrfsit.R;
import vn.edu.uit.jrfsit.service.LoginService;
import vn.edu.uit.jrfsit.utils.Utils;

/**
 * Created by KhánhDuy on 12/16/2015.
 */
public class ForgotPassActivity extends AppCompatActivity {
    AppCompatButton mBtSend;
    AppCompatButton mBtCancel;
    AppCompatEditText mEtEmail;
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.content_forgot_pass);
        load();
    }

    /**
     * load
     */
    void load(){
        initControlOnView();
        iniListener();
    }
    /**
     * initControlOnView
     */
    void initControlOnView(){
        mEtEmail = (AppCompatEditText) findViewById(R.id.etEmail_forgot_pass);
        mBtSend = (AppCompatButton) findViewById(R.id.btSend_forgot_pass);
        mBtCancel  = (AppCompatButton) findViewById(R.id.btCancel_forgot_pass);
    }

    /**
     * iniListener
     */
    void iniListener(){
        mBtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
         mBtSend.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 final String email= mEtEmail.getText().toString();
                 if(Utils.isValidEmail(email)){
                     new Thread(new Runnable() {
                         public void run() {
                             LoginService loginService = new LoginService();
                             if(loginService.forgotPass(email)){
                                 runOnUiThread(new Runnable() {
                                     @Override
                                     public void run() {
                                         Utils.print(ForgotPassActivity.this, "Đã đặt lại mật khẩu, vui lòng kiểm tra mail của bạn");
                                         finish();
                                         onBackPressed();
                                     }
                                 });
                             }else {
                                 runOnUiThread(new Runnable() {
                                     @Override
                                     public void run() {
                                         Utils.print(ForgotPassActivity.this, "Email bạn nhập không tồn tại, vui lòng đăng kí mới");
                                     }
                                 });
                             }
                         }
                     }).start();
                 }else {
                     Utils.print(ForgotPassActivity.this,"Email bạn nhập không hợp lệ");
                 }
             }
         });
    }
}
