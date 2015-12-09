// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package vn.edu.uit.jrfsit.adapter;

import android.app.Activity;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.util.List;
import vn.edu.uit.jrfsit.entity.JobSearch;

public class SearchRecenlyListApdapter extends ArrayAdapter
{

    private final Activity context;
    private final List list;
    protected AppCompatTextView tvKey;
    protected AppCompatTextView tvLocation;
    protected AppCompatTextView tvSearchmode;
    protected AppCompatTextView tvSpecial;

    public SearchRecenlyListApdapter(Activity activity, List list1)
    {
        super(activity, 0x7f04003f, list1);
        context = activity;
        list = list1;
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        if (view == null)
        {
            view = context.getLayoutInflater().inflate(0x7f04003f, null);
            tvKey = (AppCompatTextView)view.findViewById(0x7f0e00ec);
            tvLocation = (AppCompatTextView)view.findViewById(0x7f0e00ed);
            tvSearchmode = (AppCompatTextView)view.findViewById(0x7f0e00ef);
            tvSpecial = (AppCompatTextView)view.findViewById(0x7f0e00ee);
        }
        tvKey.setText(((JobSearch)list.get(i)).getJobName());
        tvLocation.setText(((JobSearch)list.get(i)).getLocation());
        tvSearchmode.setText(((JobSearch)list.get(i)).getSortmode());
        tvSpecial.setText(((JobSearch)list.get(i)).getSpecialy());
        return view;
    }
}
