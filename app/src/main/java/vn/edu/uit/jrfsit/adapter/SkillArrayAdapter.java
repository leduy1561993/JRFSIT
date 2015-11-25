package vn.edu.uit.jrfsit.adapter;

import android.app.Activity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import java.util.List;

import vn.edu.uit.jrfsit.R;
import vn.edu.uit.jrfsit.entity.Skill;

/**
 * Created by LeDuy on 11/1/2015.
 */
public class SkillArrayAdapter extends ArrayAdapter<Skill> {
    private final Activity context;
    private final List<Skill> list;
    protected AppCompatTextView tvSkill;
    protected AppCompatTextView tvExperence;

    public SkillArrayAdapter(Activity context, List<Skill> list) {
        super(context, R.layout.list_skill, list);
        this.context = context;
        this.list = list;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.list_skill, null);
            tvSkill = (AppCompatTextView) view.findViewById(R.id.tv_skill_item);
            tvExperence = (AppCompatTextView) view.findViewById(R.id.tv_skill_experience);
        }else {
            view = convertView;
        }
        tvSkill.setText(list.get(position).getSkill());
        tvExperence.setText(list.get(position).getExperience());
        return view;
    }
}
