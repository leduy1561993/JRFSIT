package vn.edu.uit.jrfsit.adapter;

import android.app.Activity;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import java.util.List;

import vn.edu.uit.jrfsit.R;
import vn.edu.uit.jrfsit.entity.JobSearch;

/**
 * Created by LeDuy on 11/5/2015.
 */
public class SearchRecenlyListApdapter extends ArrayAdapter<JobSearch> {
    private final Activity context;
    private final List<JobSearch> list;
    protected AppCompatTextView tvKey;
    protected AppCompatTextView tvLocation;
    protected AppCompatTextView tvSearchmode;
    protected AppCompatTextView tvSpecial;

    public SearchRecenlyListApdapter(Activity context, List<JobSearch> list) {
        super(context, R.layout.list_job_search, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.list_job_search, null);
            tvKey = (AppCompatTextView) view.findViewById(R.id.tv_keyword_search_item);
            tvLocation = (AppCompatTextView) view.findViewById(R.id.tv_location_search_item);
            tvSearchmode = (AppCompatTextView) view.findViewById(R.id.tv_search_mode_item);
            tvSpecial = (AppCompatTextView) view.findViewById(R.id.tv_special_search_item);
        } else {
            view = convertView;
        }
        tvKey.setText(list.get(position).getJobName());
        tvLocation.setText(list.get(position).getLocation());
        tvSearchmode.setText(list.get(position).getSortmode());
        tvSpecial.setText(list.get(position).getSpecialy());
        return view;
    }
}
