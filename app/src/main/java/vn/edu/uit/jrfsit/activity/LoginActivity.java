// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package vn.edu.uit.jrfsit.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import vn.edu.uit.jrfsit.R;
import vn.edu.uit.jrfsit.entity.Account;
import vn.edu.uit.jrfsit.preferences.AccountPreferences;
import vn.edu.uit.jrfsit.service.LoginService;
import vn.edu.uit.jrfsit.service.UserService;
import vn.edu.uit.jrfsit.utils.Utils;

// Referenced classes of package vn.edu.uit.jrfsit.activity:
//            MainActivity, RegisterUserActivity

public class LoginActivity extends AppCompatActivity
        implements com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks, com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener {

    private static final int PROFILE_PIC_SIZE = 400;
    private static final int RC_SIGN_IN = 0;
    private static final int TIME_INTERVAL = 2000;
    AccountPreferences accountPreferences;
    AppCompatButton btLogin;
    AppCompatButton btRegister;
    LoginService loginService;
    private long mBackPressed;
    private ConnectionResult mConnectionResult;
    AppCompatEditText mEtEmail;
    AppCompatEditText mEtPassword;
    private GoogleApiClient mGoogleApiClient;
    private boolean mIntentInProgress;
    private boolean mSignInClicked;
    AppCompatButton signInButton;
    UserService userService;
    ProgressDialog progressdialog;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_login);
        accountPreferences = new AccountPreferences(this);
        if (!accountPreferences.getAccount().getEmail().equals("")) {
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            return;
        } else {
            load();
            return;
        }
    }

    private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                final String fullName = currentPerson.getDisplayName();
                final String email =Plus.AccountApi.getAccountName(mGoogleApiClient);
                final String birthday =currentPerson.getBirthday();
                final String address = currentPerson.getCurrentLocation();
                final ProgressDialog dialog =progressdialog;
                final String gender;
                if (currentPerson.getGender() == 0) {
                    gender = "Nam";
                } else if(currentPerson.getGender() == 1){
                    gender = "Nữ";
                }else {
                    gender ="Không rõ";
                }
                final String imageUrl = currentPerson.getImage().getUrl();

                new Thread(new Runnable() {
                    public void run() {
                        String userId = userService.insertUserGoogle(fullName, email, "0", birthday, gender, address, "", "", imageUrl);
                        if (userId != null) {
                            accountPreferences.putAccount(userId, email, "0", true, imageUrl);
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Utils.print(LoginActivity.this, "Không thể đăng nhập, kiểm tra kết nối");
                                }
                            });
                        }
                        dismissDialog(dialog);
                    }
                }).start();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Person information is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    private void signInWithGplus() {
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }
    }

    public void dismissDialog(final ProgressDialog dialog) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        });
    }

    public void hideSoftKeyboard(AppCompatActivity appcompatactivity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        View v = this.getWindow().getDecorView();
        v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    void initControlOnView() {
        mEtEmail = (AppCompatEditText) findViewById(R.id.etEmail_login);
        mEtPassword = (AppCompatEditText) findViewById(R.id.etPassword_login);
        btLogin = (AppCompatButton) findViewById(R.id.btLogin_login);
        btRegister = (AppCompatButton) findViewById(R.id.btRegister_login);
        signInButton = (AppCompatButton) findViewById(R.id.btLoginGoogle);
    }

    void initListener() {
        btRegister.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterUserActivity.class));
            }
        });
        btLogin.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
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
                    Utils.print(LoginActivity.this, "Email không hợp lệ");
                    mEtEmail.setTag("0");
                }
                return false;
            }
        });
        signInButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInWithGplus();
            }
        });
    }

    void load() {
        progressdialog = new ProgressDialog(LoginActivity.this);
        progressdialog.setMessage("Đang xử lí, vui lòng đợi");
        progressdialog.setIndeterminate(true);
        progressdialog.setCancelable(false);
        mGoogleApiClient = (new
                com.google.android.gms.common.api.GoogleApiClient.Builder(this))
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this
                ).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
        initControlOnView();
        loginService = new LoginService();
        userService = new UserService();
        setupUI(findViewById(R.id.dangnhap_ln));
        initListener();
    }

    void login() {
        final String email = mEtEmail.getText().toString();
        final String password = mEtPassword.getText().toString();
        if (!email.equals("") && !password.equals("")) {
            if (Utils.isValidEmail(email) && password.length() > 5) {
                progressdialog.show();
                new Thread(new Runnable() {
                    public void run() {
                        Account account = loginService.LoginUser(email, password);
                        if (account != null) {
                            String imageUrl = account.getImageUrl();
                            if(imageUrl.length()<10){
                                imageUrl=null;
                            }
                            accountPreferences.putAccount(account.getUserId(), account.getEmail(), account.getPassword(), account.isGoogle(), imageUrl);
                            finish();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Utils.print(LoginActivity.this, "Mật khẩu hoặc email sai");
                                }
                            });
                        }
                        dismissDialog(progressdialog);
                    }
                }).start();
                return;
            } else {
                Utils.print(this, "Mật khẩu hoặc email không hợp lệ");
                return;
            }
        } else {
            Utils.print(this, "Bạn chưa nhập email hoặc mật khẩu");
            return;
        }
    }

    @Override
    protected void onActivityResult(int i, int j, Intent intent) {
        if (i == 0) {
            if (j != -1) {
                mSignInClicked = false;
            }
            mIntentInProgress = false;
            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + 2000L > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(getBaseContext(), "Nhấn thêm lần nữa để thoát",  Toast.LENGTH_SHORT).show();
            mBackPressed = System.currentTimeMillis();
            return;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mSignInClicked = false;
        getProfileInformation();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionresult) {
        if (!connectionresult.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(connectionresult.getErrorCode(), this, 0).show();
        } else if (!mIntentInProgress) {
            mConnectionResult = connectionresult;
            if (mSignInClicked) {
                resolveSignInError();
                return;
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    public void setupUI(View view) {
        if (!(view instanceof AppCompatAutoCompleteTextView)) {
            view.setOnTouchListener(new android.view.View.OnTouchListener() {
                @Override
                public boolean onTouch(View view1, MotionEvent motionevent) {
                    hideSoftKeyboard(LoginActivity.this);
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
