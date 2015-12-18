// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package vn.edu.uit.jrfsit.fragment;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

import vn.edu.uit.jrfsit.R;
import vn.edu.uit.jrfsit.adapter.ViewPagerAdapter;
import vn.edu.uit.jrfsit.layoutcomponent.SlidingTabLayout;

public class SearchFragment extends Fragment
{

    int Numboftabs = 2;
    CharSequence Titles[] = {
        "Tìm kiếm công việc", "Kết quả tìm  kiếm gần đây"
    };
    private AppCompatActivity activity;
    ViewPagerAdapter adapter;
    ViewPager pager;
    SlidingTabLayout tabs;
    View v;

    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle)
    {
        v = layoutinflater.inflate(R.layout.tab_control_job_search, viewgroup, false);
        load();
        return v;
    }

    private void initControlOnView()
    {
        pager = (ViewPager)v.findViewById(R.id.pager);
        tabs = (SlidingTabLayout)v.findViewById(R.id.tabs);
    }

    private void initListener()
    {
        pager.setOnPageChangeListener(new android.support.v4.view.ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int i) {
            }

            @Override
            public void onPageScrolled(int i, float f, int j) {
            }

            @Override
            public void onPageSelected(int i) {
                android.support.v4.app.Fragment fragment = (android.support.v4.app.Fragment) ((ViewPagerAdapter) pager.getAdapter()).fm.getFragments().get(i);
                if (i == 1 && fragment != null) {
                    fragment.onResume();
                }
            }
        });
    }

    private void load()
    {
        activity = (AppCompatActivity)getActivity();
        activity.getSupportActionBar().setTitle(getString(R.string.title_activity_search_job));
        initControlOnView();
        initListener();
        adapter = new ViewPagerAdapter(activity.getSupportFragmentManager(), Titles, Numboftabs);
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new vn.edu.uit.jrfsit.layoutcomponent.SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int i) {
                return getResources().getColor(R.color.colorPrimary);
            }
        });
    }
}
