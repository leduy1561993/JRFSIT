package vn.edu.uit.jrfsit.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import vn.edu.uit.jrfsit.R;
import vn.edu.uit.jrfsit.adapter.JobArrayAdapter;
import vn.edu.uit.jrfsit.dtosql.DatabaseHandler;
import vn.edu.uit.jrfsit.entity.JobSearch;
import vn.edu.uit.jrfsit.service.SearchService;

/**
 * Created by LeDuy on 10/23/2015.
 */
public class ResultJobSearchActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListViewCompat lvDSCV;
    Boolean loadingMore = false;
    Toolbar toolbar;
    List<JobSearch> list = new ArrayList<JobSearch>();
    List<JobSearch> listadd = new ArrayList<JobSearch>();
    JobArrayAdapter adapter;
    View footerView;
    LinearLayout lnJobSearchTrue;
    LinearLayout lnJobSearchFailed;
    private int total;
    SearchService searchService;
    int offset = 0;

    //information search
    String jobName;
    String location;
    String specialy;
    String styleSearch;
    String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);
        initControlOnView();
        initListener();
        setControl();
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
            listadd = searchService.getResultSearch(jobName, location
                    , specialy, String.valueOf(offset),styleSearch);
            if(listadd!=null&&listadd.size()>0){
                //list.addAll(listadd);
                runOnUiThread(returnRes);
            }else {
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
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(), DetailJobActivity.class);
        intent.putExtra("JobId",String.valueOf(list.get(position).getId()));
        startActivity(intent);
    }

    void insertHistory(){
        if(jobName.equals("")){
            jobName = "Mặc định";
        }
        if(location.equals("")){
            location = "Mặc định";
        }
        if(styleSearch.equals("Chọn chế độ sắp xếp")){
            styleSearch ="Mặc định";
        }
        if((specialy.equals("Mặc định")
                || specialy.equals("Chọn kỹ năng"))){
            specialy ="Mặc định";
        }

        DatabaseHandler db = new DatabaseHandler(ResultJobSearchActivity.this);
        db.addSearch(new JobSearch(jobName,
                location,
                styleSearch,
                specialy,
                System.currentTimeMillis()));
        db.close();
    }

    private void initControlOnView(){
        lnJobSearchFailed = (LinearLayout) findViewById(R.id.job_search_failed);
        lnJobSearchTrue = (LinearLayout) findViewById(R.id.job_search_true);
        lnJobSearchTrue.setVisibility(View.VISIBLE);
        lnJobSearchFailed.setVisibility(View.INVISIBLE);
        lvDSCV = (ListViewCompat) findViewById(R.id.lvDSCV);
        searchService = new SearchService();
        toolbar = (Toolbar) findViewById(R.id.toolbardscv);
        footerView = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.listfooter, null, false);
    }
    private void initListener(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        lvDSCV.setOnItemClickListener(this);
        lvDSCV.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
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
    private void setControl(){
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.lvDSCV.addFooterView(footerView);
        getData();

    }
    private void getData(){
        final ProgressDialog dialog = ProgressDialog.show(ResultJobSearchActivity.this,
                "", "Vui lòng chờ....", true);
        new Thread(new Runnable() {
            public void run() {
                list = new ArrayList<JobSearch>();
                Intent intent = getIntent();
                jobName=intent.getStringExtra("jobName");
                location=intent.getStringExtra("location");
                specialy=intent.getStringExtra("specialy");
                styleSearch=intent.getStringExtra("styleSearch");
                mode = intent.getStringExtra("mode");
                list = searchService.getResultSearch(jobName, location
                        , specialy, String.valueOf(offset),styleSearch);
                dismissDialog(dialog);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new JobArrayAdapter(ResultJobSearchActivity.this, list);
                        if (list != null) {
                            if(list.size()<10){
                                footerView.setVisibility(View.INVISIBLE);
                            }
                            if(mode.equals("0")){
                                insertHistory();
                            }
                            lvDSCV.setAdapter(adapter);
                        } else {
                            lnJobSearchTrue.setVisibility(View.INVISIBLE);
                            lnJobSearchFailed.setVisibility(View.VISIBLE);
                            //lvDSCV.setBackground(JobResultSearchActivity.this.getResources().getDrawable(R.layout.failed_search));
                        }
                    }
                });
            }
        }).start();
    }
}