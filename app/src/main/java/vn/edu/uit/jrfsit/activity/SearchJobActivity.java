package vn.edu.uit.jrfsit.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import vn.edu.uit.jrfsit.R;
import vn.edu.uit.jrfsit.adapter.ViewPagerAdapter;
import vn.edu.uit.jrfsit.constants.Param;
import vn.edu.uit.jrfsit.layoutcomponent.SlidingTabLayout;
import vn.edu.uit.jrfsit.service.BitmapService;
import vn.edu.uit.jrfsit.utils.BitmapUtil;


public class SearchJobActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //Toolbar toolbar;
    //region definition
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Tìm kiếm công việc", "Kết quả tìm gần đây"};
    int Numboftabs = 2;
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawer;
    ImageView avatar;
    ActionBarDrawerToggle toggle;
    BitmapService bitmapService;
    BitmapUtil bitmapUtil;
    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ////-----------------------------set du lieu gia--------------
        Param.user.setUserId("1");
        load();
    }

    private void initControlOnView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        avatar = (ImageView) findViewById(R.id.imageView);
        pager = (ViewPager) findViewById(R.id.pager);
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
    }

    private void initListener() {
        navigationView.setNavigationItemSelectedListener(this);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float v, final int i2) {
            }

            @Override
            public void onPageSelected(final int position) {
                android.support.v4.app.Fragment fragment = ((ViewPagerAdapter) pager.getAdapter()).fm.getFragments().get(position);

                if (position == 1 && fragment != null) {
                    fragment.onResume();
                }
            }

            @Override
            public void onPageScrollStateChanged(final int position) {
            }
        });
    }

    private void load() {
        initControlOnView();
        bitmapUtil = new BitmapUtil();
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        initListener();
        getSupportActionBar().setTitle("Tìm kiếm");
        bitmapService = new BitmapService();
        // avatar tron+
        new Thread(new Runnable() {
            public void run(){
                String url="https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xfa1/v/t1.0-1/p160x160/12036363_804434892988567_8732210550839554237_n.jpg?oh=219c25857882d08f872a22b2c790c7da&oe=56C77AAD&__gda__=1455247396_a4c47a81ba63f492c8fbb2f86bee6e4b";
                final Bitmap image= bitmapService.getBitmapFromURL(url);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap circleBitmap = bitmapUtil.getRoundedShape(image);
                        avatar.setImageBitmap(circleBitmap);
                    }
                });
            }
        }).start();
        // add tab
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorAccent);
            }
        });
    }

    // Event
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_job_recomment) {
            startActivity(new Intent(getApplicationContext(), RecommendJobActivity.class));
        } else if (id == R.id.nav_job_save) {
            startActivity(new Intent(getApplicationContext(), SaveJobActivity.class));
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(getApplicationContext(), ProfileUserActivity.class));
        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_about) {
            startActivity(new Intent(getApplicationContext(), AboutActivity.class));
        } else if (id == R.id.nav_logout) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
