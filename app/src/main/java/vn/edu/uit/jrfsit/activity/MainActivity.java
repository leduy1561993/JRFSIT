// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package vn.edu.uit.jrfsit.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

import vn.edu.uit.jrfsit.R;
import vn.edu.uit.jrfsit.entity.Account;
import vn.edu.uit.jrfsit.fragment.AboutFragment;
import vn.edu.uit.jrfsit.fragment.HelpFragment;
import vn.edu.uit.jrfsit.fragment.ProfileUserFragment;
import vn.edu.uit.jrfsit.fragment.RecommendFragment;
import vn.edu.uit.jrfsit.fragment.SaveFragment;
import vn.edu.uit.jrfsit.fragment.SearchFragment;
import vn.edu.uit.jrfsit.fragment.SettingFragment;
import vn.edu.uit.jrfsit.preferences.AccountPreferences;
import vn.edu.uit.jrfsit.service.BitmapService;
import vn.edu.uit.jrfsit.utils.BitmapUtil;

// Referenced classes of package vn.edu.uit.jrfsit.activity:
//            LoginActivity

public class MainActivity extends AppCompatActivity
        implements android.support.design.widget.NavigationView.OnNavigationItemSelectedListener, com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks, com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener {

    private static final int TIME_INTERVAL = 2000;
    Account account;
    AccountPreferences accountPreferences;
    ImageView avatar;
    BitmapService bitmapService;
    BitmapUtil bitmapUtil;
    DrawerLayout drawer;
    private long mBackPressed;
    GoogleApiClient mGoogleApiClient;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    TextView tvEmail;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        initLoad();
    }

    private void initControlOnView() {
        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        avatar = (ImageView) findViewById(R.id.imageView);
        tvEmail = (TextView) findViewById(R.id.tvJob_email_user);
    }

    private void initListener() {
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initLoad() {
        accountPreferences = new AccountPreferences(this);
        account = accountPreferences.getAccount();
        bitmapUtil = new BitmapUtil();
        bitmapService = new BitmapService();
        initControlOnView();
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        initListener();
        bitmapService = new BitmapService();
        new Thread(new Runnable() {
            public void run() {
                final Bitmap bitmap = bitmapService.getBitmapFromURL(account.getImageUrl());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap circleBitmap = bitmapUtil.getRoundedShape(bitmap);
                        avatar.setImageBitmap(circleBitmap);
                    }
                });
            }
        }).start();

        tvEmail.setText(account.getEmail());
        mGoogleApiClient = (new
                com.google.android.gms.common.api.GoogleApiClient.Builder(this))
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
        mGoogleApiClient.connect();
        setFragment(new SearchFragment());
        /*MenuItem item = (MenuItem) this.findViewById(R.id.nav_search);
        item.setCheckable(true);
        item.setChecked(true);
        navigationView.setCheckedItem(item.getItemId());*/
    }

    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_frame, fragment)
                .commit();
    }

    private void signOutFromGplus() {
        getApplicationContext().deleteDatabase("jobSearchRecently");
        accountPreferences.clearData();
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
        }
        finish();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (mBackPressed + 2000L > System.currentTimeMillis()) {
                super.onBackPressed();
            } else {
                Toast.makeText(getBaseContext(), "Nhấn thêm lần nữa để thoát", Toast.LENGTH_SHORT).show();
                mBackPressed = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionresult) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.nav_search) {
            setFragment(new SearchFragment());
        } else if (i == R.id.nav_job_recomment) {
            setFragment(new RecommendFragment());
        } else if (i == R.id.nav_job_save) {
            setFragment(new SaveFragment());
        } else if (i ==  R.id.nav_profile) {
            setFragment(new ProfileUserFragment());
        } else if (i ==  R.id.nav_about) {
            setFragment(new AboutFragment());
        } else if (i ==  R.id.nav_help) {
            setFragment(new HelpFragment());
        } else if (i ==  R.id.nav_setting) {
            setFragment(new SettingFragment());
        } else if (i ==  R.id.nav_logout) {
            signOutFromGplus();
        }
        item.setCheckable(true);
        item.setChecked(true);
        navigationView.setCheckedItem(i);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
