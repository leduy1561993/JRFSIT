package vn.edu.uit.jrfsit.activity;

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
import vn.edu.uit.jrfsit.service.RecService;

/**
 * Created by LeDuy on 10/23/2015.
 */
public class RecommendJobActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,NavigationView.OnNavigationItemSelectedListener {
    //region defination
    ListViewCompat lvDSCV;
    Boolean loadingMore = false;
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
    RecService recService;
    private int total;
    int offset = 0;
    int indexSelect=0;
    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_recommend);
        load();
        loadData();
    }


    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //Runnable to load the items
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
            //Get 15 new listitems
            for (int i = 0; i < 10; i++) {
                //Fill the item with some bogus information
                //listadd.add(get(String.valueOf(i), String.valueOf(i), String.valueOf(i), String.valueOf((i))));
                //list.add(get(String.valueOf(i), String.valueOf(i), String.valueOf(i), String.valueOf((i))));
            }
            //Done! now continue on the UI thread
            runOnUiThread(returnRes);
        }
    };

    private Runnable returnRes = new Runnable() {
        @Override
        public void run() {
            //Loop thru the new items and add them to the adapter
            if (listadd != null && listadd.size() > 0) {
                for (int i = 0; i < listadd.size(); i++)
                    adapter.add(listadd.get(i));
            }
            //Update the Application title
            setTitle("Neverending List with " + String.valueOf(adapter.getCount()) + " items");
            //Tell to the adapter that changes have been made, this will cause the list to refresh
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

    private JobSearch get(String congviec, String congty, String diachi, String url) {
        return new JobSearch(1,congviec, congty, diachi, url);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final int index = position;
        //Toast.makeText(getBaseContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), DetailJobActivity.class);
        intent.putExtra("JobId", String.valueOf(list.get(position).getId()));
        startActivity(intent);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_search) {
            startActivity(new Intent(getApplicationContext(), SearchJobActivity.class));
        } else if (id == R.id.nav_job_save) {
            startActivity(new Intent(getApplicationContext(), SaveJobActivity.class));
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
    private void initControlOnView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_job_recommend);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView= (NavigationView) findViewById(R.id.nav_view);
        lnJobSearchFailed = (LinearLayout) findViewById(R.id.ln_job_rec_failed);
        lnJobSearchTrue = (LinearLayout) findViewById(R.id.job_search_true);
        lnJobSearchTrue.setVisibility(View.VISIBLE);
        lnJobSearchFailed.setVisibility(View.INVISIBLE);
        lvDSCV = (ListViewCompat) findViewById(R.id.lvDSCV);
        footerView = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.listfooter, null, false);
    }
    private void initListener(){
        navigationView.setNavigationItemSelectedListener(this);
        lvDSCV.setOnItemClickListener(this);
        lvDSCV.setOnItemClickListener(this);
        lvDSCV.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastInScreen = firstVisibleItem + visibleItemCount;
                if ((lastInScreen == totalItemCount) && !loadingMore && total != totalItemCount&&totalItemCount>9) {
                    total = totalItemCount;
                    offset += 10;
                    footerView.setVisibility(View.VISIBLE);
                    Thread thread = new Thread(null, loadMoreListItems);
                    thread.start();
                }
            }
        });
    }
    private void loadData(){
        final ProgressDialog dialog = ProgressDialog.show(RecommendJobActivity.this,
                "", "Vui lòng chờ....", true);
        new Thread(new Runnable() {
            public void run() {
                list = new ArrayList<JobSearch>();
                list = recService.getSaveJob(Param.user.getUserId(), String.valueOf(offset));
                dismissDialog(dialog);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new JobArrayAdapter(RecommendJobActivity.this, list);
                        if (list != null) {
                            if(list.size()<10){
                                footerView.setVisibility(View.INVISIBLE);
                            }
                            lvDSCV.setAdapter(adapter);
                        } else {
                            lnJobSearchTrue.setVisibility(View.INVISIBLE);
                            lnJobSearchFailed.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }).start();
    }
    private void load(){
        initControlOnView();
        setSupportActionBar(toolbar);
        recService = new RecService();
        toggle= new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        initListener();
        getSupportActionBar().setTitle("Công việc có thể bạn quan tâm");
        this.lvDSCV.addFooterView(footerView);
    }
}