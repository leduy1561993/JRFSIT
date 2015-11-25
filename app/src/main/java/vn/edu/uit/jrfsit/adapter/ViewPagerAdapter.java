package vn.edu.uit.jrfsit.adapter;

/**
 * Created by LeDuy on 10/27/2015.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;


import java.util.HashMap;

import vn.edu.uit.jrfsit.tabfragment.TabResultRecently;
import vn.edu.uit.jrfsit.tabfragment.TabSearchJob;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    HashMap<Integer,String> mFragmentTags;
    public FragmentManager fm;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        mFragmentTags = new HashMap<Integer,String>();
        this.Titles = mTitles;
        this.fm = fm;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            TabSearchJob tab1 = new TabSearchJob();
            return tab1;
        }
        else             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            TabResultRecently tab2 = new TabResultRecently();
            return tab2;
        }


    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object obj = super.instantiateItem(container, position);
        if (obj instanceof Fragment) {
            // record the fragment tag here.
            Fragment f = (Fragment) obj;
            String tag = (String) f.getTag();
            mFragmentTags.put(position, tag);
        }
        return obj;
    }
    public Fragment getFragment(int position) {
        String tag = mFragmentTags.get(position);
        if (tag == null)
            return null;
        return fm.findFragmentByTag(tag);
    }

}