package vn.edu.uit.jrfsit.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import vn.edu.uit.jrfsit.R;

/**
 * Created by LeDuy on 10/27/2015.
 */
public class RegisterUserActivity extends Activity {
        EditText hoten;
        EditText email;
        EditText passWord;
        EditText rePassWord;
        RadioButton gtNam;
        RadioButton gtNu;
        EditText sdt;
        EditText ngaySinh;
        EditText diaChi;
        Button troLai;
        Button dangKi;
        private int year = 1993;
        private int month = 6;
        private int day = 15;
        static final int DATE_DIALOG_ID = 999;
        ProgressDialog dialog = null;
        String check = null;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);
            if (android.os.Build.VERSION.SDK_INT > 8) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
        /*
        dang ki button
         */
            ngaySinh = (EditText) findViewById(R.id.dktt_et_ngaysinh);
            dangKi = (Button) findViewById(R.id.dktt_bt_dangKi);
            hoten = (EditText) findViewById(R.id.dktt_et_ten);
            email = (EditText) findViewById(R.id.dktt_et_email);
            passWord = (EditText) findViewById(R.id.dktt_et_password);
            rePassWord = (EditText) findViewById(R.id.dktt_et_nhaplaiPassword);
            gtNam = (RadioButton) findViewById(R.id.dktt_rb_Nam);
            gtNam.setChecked(true);
            gtNu = (RadioButton) findViewById(R.id.dktt_rb_Nu);
            sdt = (EditText) findViewById(R.id.dktt_et_sdt);
            diaChi = (EditText) findViewById(R.id.dktt_et_diachi);
        /*
        su kiem button
         */
            ngaySinh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(DATE_DIALOG_ID);
                }
            });
            troLai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            });

            dangKi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (hoten.getText().length() > 6) {
                        if (email.getText().length() > 12) {
                            if (passWord.getText().length() > 6) {
                                if (rePassWord.getText().toString().trim().equals(passWord.getText().toString().trim())) {
                                    if (sdt.getText().length() > 9 || sdt.getText().length() < 12) {
                                        if (ngaySinh.getText().length() > 0) {
                                            if (diaChi.getText().length() > 10) {
                                            /*AlertDialog.Builder builder = new AlertDialog.Builder(DangKiNguoiDung.this);
                                            builder.setMessage("Tiến trình đang thực hiện, vui lòng chờ");
                                            AlertDialog OptionDialog = builder.create();
                                            OptionDialog.show();*/
                                                dialog = ProgressDialog.show(RegisterUserActivity.this, "",
                                                        "Đang đăng kí.....", true);
                                                new Thread(new Runnable() {
                                                    public void run() {
                                                        DangKi();
                                                    }
                                                }).start();
                                            } else {
                                                Toast.makeText(getBaseContext(), "Địa chỉ nhập vào phải lớn hơn 10 kí tự !", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(getBaseContext(), "Bạn chưa chọn ngày sinh !", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(getBaseContext(), "Số điện thoại nhập vào sai!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getBaseContext(), "Password nhập lại không khớp!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getBaseContext(), "Password nhập vào phải lớn hơn 6 kí tự !", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getBaseContext(), "Email nhập vào phải lớn hơn 6 kí tự !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "Tên nhập vào phải lớn hơn 6 kí tự !", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


        @Override
        protected Dialog onCreateDialog(int id) {
            switch (id) {
                case DATE_DIALOG_ID:
                    return new DatePickerDialog(this, datePickerListener,
                            year, (month), day);
            }
            return null;
        }
        private DatePickerDialog.OnDateSetListener datePickerListener
                = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int selectedYear,
                                  int selectedMonth, int selectedDay) {
                year = selectedYear;
                month = selectedMonth;
                day = selectedDay;
                String strDay=String.valueOf(day);
                String strMonth = String.valueOf(month+1);
                if(day<10){
                    strDay="0"+strDay;
                }
                if(month<10){
                    strMonth="0"+strMonth;
                }

                ngaySinh.setText(new StringBuilder().append(year)
                        .append("-").append(strMonth).append("-").append(strDay)
                        .append(" "));
            }
        };
        public void DangKi(){
            /*Util util = new Util();
            String text_hoTen = hoten.getText().toString();
            String text_email = email.getText().toString();
            String text_password = rePassWord.getText().toString();
            String text_sdt = sdt.getText().toString();
            String gt=null;
            if(gtNam.isChecked()){
                gt="Nam";
            }else{
                gt="Nu";
            }
            String text_ngaySinh = ngaySinh.getText().toString();
            String text_diaChi = diaChi.getText().toString();
            String link = "http://mto.16mb.com/mto/insertUser.php";
            Connect connect = new Connect(link);
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("MaNguoiDung", util.LayMaCuoi_User()));
            nameValuePairs.add(new BasicNameValuePair("HoTen", text_hoTen));
            nameValuePairs.add(new BasicNameValuePair("GioiTinh", gt));
            nameValuePairs.add(new BasicNameValuePair("NgaySinh", text_ngaySinh));
            nameValuePairs.add(new BasicNameValuePair("SDT", text_sdt));
            nameValuePairs.add(new BasicNameValuePair("DiaChi", text_diaChi));
            nameValuePairs.add(new BasicNameValuePair("Email", text_email));
            nameValuePairs.add(new BasicNameValuePair("PassWord", text_password));
            check = "a";
            check = connect.connect_ArrayList(nameValuePairs, "success");

            if (check.equals("1")) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getBaseContext(), " Đăng kí thành công!", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
                finish();
                startActivity(new Intent(getApplicationContext(), dangnhap.class));
            } else {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getBaseContext(), " Đăng kí thất bại!", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            }*/
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
