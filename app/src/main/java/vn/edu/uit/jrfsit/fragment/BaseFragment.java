// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package vn.edu.uit.jrfsit.fragment;

import android.app.Fragment;
import android.content.res.Resources;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseFragment extends Fragment
{

    protected AppCompatActivity activity;

    public BaseFragment()
    {
    }

    public abstract void initControlOnView();

    public abstract void initListener();

    public void loadActivity(int i)
    {
        activity = (AppCompatActivity)getActivity();
        activity.getSupportActionBar().setTitle(activity.getResources().getString(i));
    }
}
