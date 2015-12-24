// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package vn.edu.uit.jrfsit.fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.internal.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;

import vn.edu.uit.jrfsit.R;
import vn.edu.uit.jrfsit.activity.DetailJobActivity;
import vn.edu.uit.jrfsit.adapter.JobArrayAdapter;
import vn.edu.uit.jrfsit.entity.Account;
import vn.edu.uit.jrfsit.entity.JobSearch;
import vn.edu.uit.jrfsit.preferences.AccountPreferences;
import vn.edu.uit.jrfsit.service.JobService;

// Referenced classes of package vn.edu.uit.jrfsit.fragment:
//            BaseFragment

public class SaveFragment extends BaseFragment
    implements android.widget.AdapterView.OnItemClickListener
{

    public int NUMBER_JOB_GET = 10;
    Account account;
    AccountPreferences accountPreferences;
    JobArrayAdapter adapter;
    Boolean firtsStart;
    View footerView;
    int indexSelect;
    JobService jobService;
    List<JobSearch> list = new ArrayList<JobSearch>();
    List<JobSearch> listadd = new ArrayList<JobSearch>();
    LinearLayout lnJobSearchFailed;
    LinearLayout lnJobSearchTrue;
    Boolean loadingMore = false;
    ListViewCompat lvDSCV;
    int offset;
    View v;
    ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle)
    {
        v = layoutinflater.inflate(R.layout.content_save_job, viewgroup, false);
        load();
        return v;
    }

    private void load()
    {
        super.loadActivity(R.string.title_activity_save_job);
        accountPreferences = new AccountPreferences(activity);
        account = accountPreferences.getAccount();
        initControlOnView();
        initListener();
        jobService = new JobService();
        lvDSCV.addFooterView(footerView);
        new Thread(new Runnable() {
            public void run()
            {
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                         dialog = ProgressDialog.show(activity,
                                "", "Vui lòng chờ....", true);
                    }
                });
                list = new ArrayList();
                list = jobService.getSaveJob(account.getUserId(), String.valueOf(offset));
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        adapter = new JobArrayAdapter(activity, list);
                        if (list != null) {
                            if (list.size() < NUMBER_JOB_GET) {
                                footerView.setVisibility(View.INVISIBLE);
                            }
                            lvDSCV.setAdapter(adapter);
                            firtsStart = Boolean.valueOf(false);
                        } else {
                            lnJobSearchTrue.setVisibility(View.INVISIBLE);
                            lnJobSearchFailed.setVisibility(View.VISIBLE);
                        }
                        dialog.dismiss();
                    }
                });
            }
        }).start();
    }

    public void initControlOnView()
    {
        lnJobSearchFailed = (LinearLayout) v.findViewById(R.id.ln_job_save_failed);
        lnJobSearchTrue = (LinearLayout) v.findViewById(R.id.job_search_true);
        lnJobSearchTrue.setVisibility(View.VISIBLE);
        lnJobSearchFailed.setVisibility(View.INVISIBLE);
        lvDSCV = (ListViewCompat) v.findViewById(R.id.lvDSCV);
        footerView = ((LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.listfooter, null, false);
    }

    public void initListener() {
        lvDSCV.setOnItemClickListener(this);
        lvDSCV.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastInScreen = firstVisibleItem + visibleItemCount;
                if ((lastInScreen == totalItemCount) && !loadingMore && totalItemCount >= NUMBER_JOB_GET) {
                    offset += 10;
                    footerView.setVisibility(View.VISIBLE);
                    Thread thread = new Thread(null, loadMoreListItems);
                    thread.start();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(activity.getApplicationContext(), DetailJobActivity.class);
        intent.putExtra("JobId", String.valueOf(list.get(position).getId()));
        indexSelect = position;
        this.startActivityForResult(intent, 1);
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
            listadd = jobService.getSaveJob(account.getUserId(), String.valueOf(offset));
            if(listadd!=null&&listadd.size()>0){
                activity.runOnUiThread(returnRes);
            }else {
                offset=0;
                loadingMore =true;
                activity.runOnUiThread(new Runnable() {
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
            activity.getSupportActionBar().setTitle("Tìm thấy " + String.valueOf(adapter.getCount()) + " công việc");
            adapter.notifyDataSetChanged();
            //Done loading more.
            loadingMore = false;
        }
    };

}
