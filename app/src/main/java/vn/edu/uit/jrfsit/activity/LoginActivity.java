package vn.edu.uit.jrfsit.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import vn.edu.uit.jrfsit.R;
import vn.edu.uit.jrfsit.connect.Connect;

/**
 * Created by LeDuy on 10/27/2015.
 */
public class LoginActivity extends Activity{
    Button bt_exit;
    Button bt_dangNhap;
    Button bt_dangki;
    EditText et_email;
    EditText et_password;
    ProgressDialog dialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupUI(findViewById(R.id.dangnhap_ln));
        if (Build.VERSION.SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        et_email= (EditText) findViewById(R.id.dangnhap_et_email);
        et_password= (EditText) findViewById(R.id.dangnhap_et_matkhau);
        bt_dangki= (Button) this.findViewById(R.id.dangnhap_bt_danngki);
        bt_dangNhap= (Button) this.findViewById(R.id.dangnhap_bt_dangnhap);
        bt_dangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SearchJobActivity.class));
            }
        });
        bt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        bt_dangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if(et_email.getText().length()>12){
                    if (et_password.getText().length()>6){*/
                dialog = ProgressDialog.show(LoginActivity.this, "",
                        "Đang đăng nhập.....", true);
                new Thread(new Runnable() {
                    public void run() {
                        DangNhap();
                    }
                }).start();
                    /*}else{
                        Toast.makeText(getBaseContext(), "Password nhập vào phải lớn hơn 10 kí tự !", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getBaseContext(), "Email nhập vào phải lớn hơn 12 kí tự !", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
        hideNavBar();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    ;

    private void hideNavBar() {
        if (Build.VERSION.SDK_INT >= 19) {
            View v = getWindow().getDecorView();
            v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LOW_PROFILE);
        }
    }

    public void setupUI(View view) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(LoginActivity.this);
                    return false;
                }
            });
        }
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
    public void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        hideNavBar();
    }
    public void DangNhap(){
        String email= et_email.getText().toString();
        String password= et_password.getText().toString();
        String link = "http://mto.16mb.com/mto/dangnhap.php";
        Connect connect = new Connect(link);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("Email", email));
        nameValuePairs.add(new BasicNameValuePair("PassWord",password));
        JSONArray array=null;
        //array=connect.connect_all(nameValuePairs, "user");
        boolean check=false;
        if(array!=null) {
            JSONObject last;

        }

        if(check){
            dialog.dismiss();
            finish();
            startActivity(new Intent(getApplicationContext(), SearchJobActivity.class));
        }else{
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getBaseContext(), "Sai email hoặc password!", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.dismiss();
        }
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View v = getWindow().getDecorView();
        if (hasFocus) {
            v.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
