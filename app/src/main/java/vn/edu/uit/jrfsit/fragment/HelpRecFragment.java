package vn.edu.uit.jrfsit.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.edu.uit.jrfsit.R;

/**
 * Created by KhánhDuy on 12/12/2015.
 */
public class HelpRecFragment extends BaseFragment {
    View v;
    @Override
    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle) {
        v = layoutinflater.inflate(R.layout.content_help_recommend, viewgroup, false);
        load();
        return v;
    }
    @Override
    public void initControlOnView() {

    }

    @Override
    public void initListener() {

    }

    private void load() {
        super.loadActivity(R.string.title_activity_help_job_rec);
    }
}
