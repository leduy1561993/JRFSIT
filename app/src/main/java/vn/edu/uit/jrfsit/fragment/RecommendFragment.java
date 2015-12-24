// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package vn.edu.uit.jrfsit.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import vn.edu.uit.jrfsit.service.RecService;

// Referenced classes of package vn.edu.uit.jrfsit.fragment:
//            BaseFragment

public class RecommendFragment extends BaseFragment
    implements android.widget.AdapterView.OnItemClickListener
{

    public int NUMBER_JOB_GET = 10;
    Account account;
    AccountPreferences accountPreferences;
    JobArrayAdapter adapter;
    View footerView;
    List<JobSearch> list = new ArrayList<JobSearch>();
    List<JobSearch> listadd = new ArrayList<JobSearch>();
    LinearLayout lnJobSearchFailed;
    LinearLayout lnJobSearchTrue;
    Boolean loadingMore =false;
    ListViewCompat lvDSCV;
    int offset;
    RecService recService;
    View v;
    ProgressDialog pDialog;

    @Override
    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle)
    {
        v = layoutinflater.inflate(R.layout.content_recommend_job, viewgroup, false);
        load();
        return v;
    }


    private void load()
    {
        super.loadActivity(R.string.title_activity_job_recommend);
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Vui lòng chờ....");
        pDialog.show();
        initControlOnView();
        recService = new RecService();
        initListener();
        lvDSCV.addFooterView(footerView);
        accountPreferences = new AccountPreferences(activity);
        account = accountPreferences.getAccount();
        (new Thread(new Runnable() {
            public void run()
            {
                list = new ArrayList();
                list = recService.getRecJob(account.getUserId(), String.valueOf(offset));
                dismissDialog(pDialog);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        adapter = new JobArrayAdapter(activity, list);
                        if (list != null)
                        {
                            if (list.size() < NUMBER_JOB_GET)
                            {
                                footerView.setVisibility(View.INVISIBLE);
                            }
                            lvDSCV.setAdapter(adapter);
                            return;
                        } else
                        {
                            lnJobSearchTrue.setVisibility(View.INVISIBLE);
                            lnJobSearchFailed.setVisibility(View.VISIBLE);
                            return;
                        }
                    }
                });
            }
        })).start();
    }

    public void dismissDialog(final ProgressDialog dialog)
    {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run()
            {
                dialog.dismiss();
            }
        });
    }

    public void initControlOnView()
    {
        lnJobSearchFailed = (LinearLayout) v.findViewById(R.id.ln_job_rec_failed);
        lnJobSearchTrue = (LinearLayout) v.findViewById(R.id.job_search_true);
        lnJobSearchTrue.setVisibility(View.VISIBLE);
        lnJobSearchFailed.setVisibility(View.INVISIBLE);
        lvDSCV = (ListViewCompat) v.findViewById(R.id.lvDSCV);
        footerView = ((LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.listfooter, null, false);
    }

    public void initListener()
    {
        lvDSCV.setOnItemClickListener(this);
        lvDSCV.setOnScrollListener(new android.widget.AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView abslistview, int i, int j, int k) {
                if (i + j == k && !loadingMore.booleanValue() && k >= NUMBER_JOB_GET) {
                    offset = offset + NUMBER_JOB_GET;
                    footerView.setVisibility(View.VISIBLE);
                    (new Thread(null, loadMoreListItems)).start();
                }
            }

            public void onScrollStateChanged(AbsListView abslistview, int i) {
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(activity.getApplicationContext(), DetailJobActivity.class);
        intent.putExtra("JobId", String.valueOf(list.get(position).getId()));
        startActivity(intent);
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
            listadd = recService.getRecJob(account.getUserId(), String.valueOf(offset));
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
