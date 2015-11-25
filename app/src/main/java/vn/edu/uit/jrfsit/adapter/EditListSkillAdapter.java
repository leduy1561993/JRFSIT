package vn.edu.uit.jrfsit.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import vn.edu.uit.jrfsit.R;
import vn.edu.uit.jrfsit.entity.Skill;

/**
 * Created by LeDuy on 11/1/2015.
 */
public class EditListSkillAdapter extends ArrayAdapter<Skill> {
    private final Activity context;
    private final List<Skill> list;
    protected AppCompatSpinner pnSkill;
    protected AppCompatSpinner pnExperence;

    public EditListSkillAdapter(Activity context, List<Skill> list) {
        super(context, R.layout.list_edit_skill, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.list_edit_skill, null);
            pnSkill = (AppCompatSpinner) view.findViewById(R.id.pn_edit_skill_item);
            pnExperence = (AppCompatSpinner) view.findViewById(R.id.pn_edit_skill_experience);
        } else {
            view = convertView;
        }

        // set data pnskill
        ArrayAdapter<CharSequence> adapter_specialy = ArrayAdapter.createFromResource(context,
                R.array.specialy_array, android.R.layout.simple_spinner_item);
        adapter_specialy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pnSkill.setAdapter(adapter_specialy);
        pnSkill.setSelection(adapter_specialy.getPosition(list.get(position).getSkill()));

        //set data pnExperience
        String[] array_experience = context.getResources().getStringArray(R.array.experience_array);
        ArrayAdapter<CharSequence> adapterExperience = new ArrayAdapter(context
                ,R.layout.spinner_item, array_experience) { };
        adapterExperience.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pnExperence.setAdapter(adapterExperience);
        pnExperence.setSelection(adapterExperience.getPosition(list.get(position).getSkill()));
        //pnExperence.setText(list.get(position).getExperience());*/
        return view;
    }
}
