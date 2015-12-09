// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package vn.edu.uit.jrfsit.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.edu.uit.jrfsit.R;

// Referenced classes of package vn.edu.uit.jrfsit.fragment:
//            BaseFragment

public class AboutFragment extends BaseFragment
{

    View v;

    public AboutFragment()
    {
    }

    private void load()
    {
        super.loadActivity(R.string.title_activity_about);
        initControlOnView();
        initListener();
    }

    public void initControlOnView()
    {
    }

    public void initListener()
    {
    }

    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle)
    {
        v = layoutinflater.inflate(R.layout.content_about, viewgroup, false);
        load();
        return v;
    }
}
