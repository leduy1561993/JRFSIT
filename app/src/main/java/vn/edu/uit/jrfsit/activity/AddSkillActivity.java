// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package vn.edu.uit.jrfsit.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import vn.edu.uit.jrfsit.R;
import vn.edu.uit.jrfsit.entity.Skill;
import vn.edu.uit.jrfsit.utils.Utils;

public class AddSkillActivity extends AppCompatActivity
{
    private AppCompatButton btCancel;
    private AppCompatButton btSave;
    protected AppCompatSpinner pnExperence;
    protected AppCompatSpinner pnSkill;
    private Skill skill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_getskill);
        initControlOnView();
        initData();
        initListener();
    }

    private void initControlOnView()
    {
        pnSkill = (AppCompatSpinner) findViewById(R.id.sn_skill);
        pnExperence = (AppCompatSpinner) findViewById(R.id.sn_experience);
        btCancel = (AppCompatButton) findViewById(R.id.btCancel_Skill);
        btSave = (AppCompatButton) findViewById(R.id.btSave_Skill);
    }

    private void initData()
    {
        skill= new Skill();
        // set data pnskill


        String[] array_skill = this.getResources().getStringArray(R.array.specialy_array);
        for (int i=1;i<(array_skill.length-1);i++){
            array_skill[i]=array_skill[i+1];
        }
        ArrayAdapter<CharSequence> adapterSkill = new ArrayAdapter(this
                ,R.layout.spinner_item, array_skill) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapterSkill.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pnSkill.setAdapter(adapterSkill);

        //set data pnExperience
        String[] array_experience = this.getResources().getStringArray(R.array.experience_array);
        ArrayAdapter<CharSequence> adapterExperience = new ArrayAdapter(this
                ,R.layout.spinner_item, array_experience) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapterExperience.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pnExperence.setAdapter(adapterExperience);
    }

    private void initListener()
    {
        pnSkill.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pnSkill.setTag(String.valueOf(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(pnSkill.getSelectedItem().toString().equals("Chọn chuyên ngành")
                        || pnExperence.getSelectedItem().toString().equals("Chọn số năm kinh nghiệm"))) {
                    Intent returnIntent = new Intent();
                    skill.setId((String) pnSkill.getTag());
                    skill.setSkill(pnSkill.getSelectedItem().toString());
                    skill.setExperience(pnExperence.getSelectedItem().toString());
                    returnIntent.putExtra("result", skill);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                } else {
                    Utils.print(AddSkillActivity.this, "Bạn chưa chọn kĩ năng hoặc số năm kinh nghiệm");
                }
            }
        });
        btCancel.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
