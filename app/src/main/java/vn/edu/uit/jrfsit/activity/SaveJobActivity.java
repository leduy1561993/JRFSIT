package vn.edu.uit.jrfsit.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;


import java.util.ArrayList;
import java.util.List;

import vn.edu.uit.jrfsit.R;
import vn.edu.uit.jrfsit.adapter.JobArrayAdapter;
import vn.edu.uit.jrfsit.constants.Param;
import vn.edu.uit.jrfsit.entity.JobSearch;
import vn.edu.uit.jrfsit.service.JobService;

/**
 * Created by LeDuy on 10/23/2015.
 */
public class SaveJobActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,NavigationView.OnNavigationItemSelectedListener {

    //region defination
    ListViewCompat lvDSCV;
    Boolean loadingMore = false;
    Boolean firtsStart = true;
    Toolbar toolbar;
    List<JobSearch> list = new ArrayList<JobSearch>();
    List<JobSearch> listadd = new ArrayList<JobSearch>();
    JobArrayAdapter adapter;
    View footerView;
    LinearLayout lnJobSearchTrue;
    LinearLayout lnJobSearchFailed;
    NavigationView navigationView;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    JobService jobService;
    private int total;
    int offset = 0;
    int indexSelect=0;

    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_save);
        load();
        loadData();
    }


    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * Runnable to load the items
     */
    private Runnable loadMoreListItems = new Runnable() {
        @Override
        public void run() {
            //Set flag so we cant load new items 2 at the same time
            loadingMore = true;
            //Reset the array that holds the new items
            listadd = new ArrayList<JobSearch>();
            //Simulate a delay, delete this on a production environment!
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            listadd = jobService.getSaveJob(Param.user.getUserId(), String.valueOf(offset));
            if(listadd!=null&&listadd.size()>0){
                runOnUiThread(returnRes);
            }else {
                offset=0;
                loadingMore =true;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        footerView.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }
    };

    /**
     * returnRes
     */
    private Runnable returnRes = new Runnable() {
        @Override
        public void run() {
            if (listadd != null && listadd.size() > 0) {
                footerView.setVisibility(View.VISIBLE);
                for (int i = 0; i < listadd.size(); i++)
                    adapter.add(listadd.get(i));
            }
            //Update the Application title
            getSupportActionBar().setTitle("Tìm thấy " + String.valueOf(adapter.getCount()) + " công việc");
            adapter.notifyDataSetChanged();
            //Done loading more.
            loadingMore = false;
        }
    };

    public void dismissDialog(final ProgressDialog dialog) {
        runOnUiThread(new Runnable() {
            public void run() {
                dialog.dismiss();
            }
        });
    }

    /**
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(), DetailJobActivity.class);
        intent.putExtra("JobId", String.valueOf(list.get(position).getId()));
        indexSelect = position;
        this.startActivityForResult(intent, 1);
    }

    /**
     *
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_search) {
            startActivity(new Intent(getApplicationContext(), SearchJobActivity.class));
        }else if (id == R.id.nav_job_recomment) {
            startActivity(new Intent(getApplicationContext(), RecommendJobActivity.class));
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(getApplicationContext(), ProfileUserActivity.class));
        }else if (id == R.id.nav_setting) {

        }else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * onBackPressed
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                if(result.equals("1")){
                    list.remove(indexSelect);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    /**
     * initControlOnView
     */
    private void initControlOnView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_job_save);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView= (NavigationView) findViewById(R.id.nav_view);
        lnJobSearchFailed = (LinearLayout) findViewById(R.id.ln_job_save_failed);
        lnJobSearchTrue = (LinearLayout) findViewById(R.id.job_search_true);
        lnJobSearchTrue.setVisibility(View.VISIBLE);
        lnJobSearchFailed.setVisibility(View.INVISIBLE);
        lvDSCV = (ListViewCompat) findViewById(R.id.lvDSCV);
        footerView = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.listfooter, null, false);
    }

    /**
     * initListener
     */
    private void initListener(){
        navigationView.setNavigationItemSelectedListener(this);
        lvDSCV.setOnItemClickListener(this);
        lvDSCV.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //what is the bottom iten that is visible
                int lastInScreen = firstVisibleItem + visibleItemCount;
                if ((lastInScreen == totalItemCount) && !loadingMore && total != totalItemCount) {
                    total = totalItemCount;
                    offset += 10;
                    footerView.setVisibility(View.VISIBLE);
                    Thread thread = new Thread(null, loadMoreListItems);
                    thread.start();
                }
            }
        });
    }

    /**
     * loadData
     */
    private void loadData(){
        final ProgressDialog dialog = ProgressDialog.show(SaveJobActivity.this,
                "", "Vui lòng chờ....", true);
        new Thread(new Runnable() {
            public void run() {
                list = new ArrayList<JobSearch>();
                list = jobService.getSaveJob(Param.user.getUserId(), String.valueOf(offset));
                dismissDialog(dialog);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new JobArrayAdapter(SaveJobActivity.this, list);
                        if (list != null) {
                            if(list.size()<10){
                                footerView.setVisibility(View.INVISIBLE);
                            }
                            lvDSCV.setAdapter(adapter);
                            firtsStart = false;
                        } else {
                            lnJobSearchTrue.setVisibility(View.INVISIBLE);
                            lnJobSearchFailed.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * load
     */
    private void load(){
        initControlOnView();
        setSupportActionBar(toolbar);
        jobService = new JobService();
        toggle= new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        initListener();
        this.lvDSCV.addFooterView(footerView);
    }
}