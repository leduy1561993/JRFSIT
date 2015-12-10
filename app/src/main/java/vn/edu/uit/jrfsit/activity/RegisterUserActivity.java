// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package vn.edu.uit.jrfsit.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

import vn.edu.uit.jrfsit.R;
import vn.edu.uit.jrfsit.adapter.AutoCompleteAdapter;
import vn.edu.uit.jrfsit.layoutcomponent.AutoCompletePlace;
import vn.edu.uit.jrfsit.service.UserService;
import vn.edu.uit.jrfsit.utils.Utils;

public class RegisterUserActivity extends AppCompatActivity
        implements com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks, com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener {

    static final int DATE_DIALOG_ID = 999;
    public static final String PARAM_EXTRA_QUERY = "place_picker_extra_query";
    int PLACE_PICKER_REQUEST = 1;
    ProgressDialog dialog;
    AppCompatAutoCompleteTextView mAcAddress;
    AppCompatButton mBtLocation;
    AppCompatButton mBtRegister;
    AppCompatEditText mEtBirthday;
    AppCompatEditText mEtEmail;
    AppCompatEditText mEtName;
    AppCompatEditText mEtPassword;
    AppCompatEditText mEtPhone;
    AppCompatEditText mEtRePassword;
    private GoogleApiClient mGoogleApiClient;
    AppCompatRadioButton mRbFemale;
    AppCompatRadioButton mRbMale;
    Toolbar mToolbar;
    private int month = 6;
    UserService userService;
    private int year = 1993;
    private int day = 15;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_register);
        load();
    }

    private void initControlOnView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_register);
        mBtRegister = (AppCompatButton) findViewById(R.id.btRegister_user);
        mEtName = (AppCompatEditText) findViewById(R.id.etName_resgister);
        mEtEmail = (AppCompatEditText) findViewById(R.id.etEmail_resgister);
        mEtPassword = (AppCompatEditText) findViewById(R.id.etPass_resgister);
        mEtRePassword = (AppCompatEditText) findViewById(R.id.etRe_pass_resgister);
        mRbFemale = (AppCompatRadioButton) findViewById(R.id.rbFeMale_register);
        mRbMale = (AppCompatRadioButton) findViewById(R.id.rbMale_register);
        mEtPhone = (AppCompatEditText) findViewById(R.id.etPhone_resgister);
        mEtBirthday = (AppCompatEditText) findViewById(R.id.etBirthday_resgister);
        mAcAddress = (AppCompatAutoCompleteTextView) findViewById(R.id.acLocation_register);
        mBtLocation = (AppCompatButton) findViewById(R.id.btLocation_register);
    }

    private void initListener() {
        mToolbar.setNavigationOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new android.app.AlertDialog.Builder(RegisterUserActivity.this))
                        .setMessage("Cài đặt chưa được lưu, bạn thực sự muốn thoát không?")
                        .setPositiveButton("Có", dialogClickListener)
                        .setNegativeButton("Không", dialogClickListener)
                        .show();
            }
        });
        mBtLocation.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    Intent intent = builder.build(getApplicationContext());
                    intent.putExtra(PARAM_EXTRA_QUERY, "&components=country:gh&types=(cities)");
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        mAcAddress.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AutoCompletePlace selection = (AutoCompletePlace) parent.getItemAtPosition(position);
                mAcAddress.setText(getCity(selection.getDescription()));
            }
        });
        mEtBirthday.setOnTouchListener(new android.view.View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionevent) {
                showDialog(999);
                return false;
            }
        });
        mEtPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (Utils.isValidEmail(mEtEmail.getText().toString())) {
                    mEtEmail.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_check_ok), null);
                    mEtEmail.setTag("1");
                } else {
                    mEtEmail.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_delete), null);
                    mEtEmail.setText("");
                    mEtEmail.setTag("0");
                }
                return false;
            }
        });
        mBtRegister.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = ProgressDialog.show(RegisterUserActivity.this, "", "Vui lòng chờ....", true);
                (new Thread(new Runnable() {
                    public void run() {
                        register();
                    }
                })).start();
            }
        });
    }

    private void load() {
        initControlOnView();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRbMale.setChecked(true);
        AutoCompleteAdapter autocompleteadapter = new AutoCompleteAdapter(this);
        mGoogleApiClient = (new
                com.google.android.gms.common.api.GoogleApiClient.Builder(this))
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();
        autocompleteadapter.setGoogleApiClient(mGoogleApiClient);
        mAcAddress.setAdapter(autocompleteadapter);
        setupUI(findViewById(R.id.ln_resgister));
        initListener();
    }

    String getCity(String address) {
        String[] temp;
        String city = null;
        if (address != null) {
            temp = address.split(",");
            if (temp != null && temp.length >= 2) {
                city = temp[temp.length - 2];
            }
            return city;
        }
        return null;
    }


    public void hideSoftKeyboard(AppCompatActivity appcompatactivity) {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        View v = this.getWindow().getDecorView();
        v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == -1) {
                Place place = PlacePicker.getPlace(data, RegisterUserActivity.this);
                mAcAddress.setText(getCity((String) place.getAddress()));
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionresult) {
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }


    @Override
    protected Dialog onCreateDialog(int i) {
        switch (i) {
            default:
                return null;

            case 999:
                return new DatePickerDialog(this, datePickerListener, year, month, day);
        }
    }

    /**
     * DatePickerDialog
     */
    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker datepicker, int i, int j, int k)
        {
            year = i;
            day = k;
            String strday = String.valueOf(day);
            String strmounth = String.valueOf(j + 1);
            month = Integer.parseInt(strmounth);
            if (day < 10)
            {
                strday = (new StringBuilder()).append("0").append(strday).toString();
            }
            if (month < 10)
            {
                strmounth = (new StringBuilder()).append("0").append(strmounth).toString();
            }
            mEtBirthday.setText((new StringBuilder()).append(year).append("-").append(strmounth).append("-").append(strday).append(" "));
        }
    };

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    onBackPressed();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    public void register() {
        userService = new UserService();
        String fullName = mEtName.getText().toString();
        String email = mEtEmail.getText().toString();
        String password = mEtRePassword.getText().toString();
        String birthday = mEtBirthday.getText().toString();
        String address = mAcAddress.getText().toString();
        String phone = mEtPhone.getText().toString();
        String gender;
        String tem="";
        if (mRbMale.isChecked()) {
            tem = "Nam";
        } else {
            tem = "Nữ";
        }
        gender = tem;
        String careerObjective;
        String imageUrl;
        if (fullName.length() > 5 || "".equals(fullName)) {
            if (email.length() > 13 && "1".equals(mEtEmail.getTag())) {
                if ("1".equals(mEtRePassword.getTag())) {
                    boolean flag = userService.insertUser(fullName, email, password, birthday, gender, address, phone,  "", "");
                    dialog.dismiss();
                    if (flag) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Utils.print(RegisterUserActivity.this, "Đăng kí thành công, vui lòng kiểm tra mail để kích hoạt tài khoản");
                                dialog.dismiss();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Utils.print(RegisterUserActivity.this, "Thất bại, kiểm tra kết nối");
                                dialog.dismiss();
                            }
                        });
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Utils.print(RegisterUserActivity.this, "Mật khẩu không (lớn hơn 5 ks tự)");
                            dialog.dismiss();
                        }
                    });
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.print(RegisterUserActivity.this, "Email không hợp lệ");
                        dialog.dismiss();
                    }
                });
            }
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Utils.print(RegisterUserActivity.this, "Tên không hợp lệ, lớn hơn 5 ký tự");
                    dialog.dismiss();
                }
            });
        }

    }

    public void setupUI(View view) {
        if (!(view instanceof AppCompatAutoCompleteTextView)) {
            view.setOnTouchListener(new android.view.View.OnTouchListener() {
                @Override
                public boolean onTouch(View view1, MotionEvent motionevent) {
                    hideSoftKeyboard(RegisterUserActivity.this);
                    if (mEtPassword.getText().toString().equals(mEtRePassword.getText().toString()) && mEtPassword.getText().toString().length() > 5) {
                        mEtRePassword.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_check_ok), null);
                        mEtRePassword.setTag("1");
                    } else {
                        mEtRePassword.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_delete), null);
                        mEtRePassword.setTag("0");
                    }
                    return false;
                }
            });
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                setupUI(((ViewGroup) view).getChildAt(i));
            }

        }
    }
}
