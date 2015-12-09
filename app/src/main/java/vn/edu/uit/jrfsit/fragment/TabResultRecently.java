package vn.edu.uit.jrfsit.fragment;

/**
 * Created by LeDuy on 10/27/2015.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import vn.edu.uit.jrfsit.R;
import vn.edu.uit.jrfsit.activity.ResultJobSearchActivity;
import vn.edu.uit.jrfsit.adapter.SearchRecenlyListApdapter;
import vn.edu.uit.jrfsit.dtosql.DatabaseHandler;
import vn.edu.uit.jrfsit.entity.JobSearch;

/**
 * Created by hp1 on 21-01-2015.
 */
public class TabResultRecently extends Fragment implements OnItemClickListener {
    List<JobSearch> list = new ArrayList<JobSearch>();
    ArrayAdapter<JobSearch> adapter;
    ListViewCompat lvSearch;
    FloatingActionButton fabLearHistory;
    private AppCompatActivity activity;
    DatabaseHandler db;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_job_recently, container, false);
        lvSearch = (ListViewCompat) v.findViewById(R.id.lvDSCV);
        activity = (AppCompatActivity) getActivity();
        fabLearHistory = (FloatingActionButton) v.findViewById(R.id.fabLearHistory);
        db = new DatabaseHandler(activity);
        list = db.getAllContacts();
        adapter = new SearchRecenlyListApdapter(activity, list);
        lvSearch.setAdapter(adapter);
        lvSearch.setOnItemClickListener(this);
        fabLearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("Bạn thật sự muốn xóa lịch sử không?").setPositiveButton("Có", dialogClickListener)
                        .setNegativeButton("Không", dialogClickListener).show();
            }
        });
        return v;
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    list=null;
                    db.deleteJobAll();
                    adapter.clear();
                    adapter.notifyDataSetChanged();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        list = db.getAllContacts();
        adapter.clear();
        adapter = new SearchRecenlyListApdapter(activity, list);
        lvSearch.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(activity.getApplicationContext(), ResultJobSearchActivity.class);
        intent.putExtra("jobName", list.get(position).getJobName());
        intent.putExtra("location", list.get(position).getLocation());
        intent.putExtra("specialy", list.get(position).getSpecialy());
        intent.putExtra("styleSearch", list.get(position).getSortmode());
        intent.putExtra("mode", "1");
        startActivity(intent);
    }
}