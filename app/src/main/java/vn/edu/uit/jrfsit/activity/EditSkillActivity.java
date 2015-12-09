// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package vn.edu.uit.jrfsit.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ArrayAdapter;

import vn.edu.uit.jrfsit.R;
import vn.edu.uit.jrfsit.entity.Skill;

public class EditSkillActivity extends AppCompatActivity
{

    private AppCompatButton btCancel;
    private AppCompatButton btSave;
    protected AppCompatSpinner pnExperence;
    private Skill skill;
    protected AppCompatTextView tvSkill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_editskill);
        initControlOnView();
        initData();
        initListener();
    }

    private void initControlOnView()
    {
        tvSkill = (AppCompatTextView) findViewById(R.id.tv_skill_edit);
        pnExperence = (AppCompatSpinner) findViewById(R.id.sn_experience);
        btCancel = (AppCompatButton) findViewById(R.id.btCancel_Skill);
        btSave = (AppCompatButton) findViewById(R.id.btSave_Skill);
    }

    private void initData()
    {
        skill= (Skill) getIntent().getSerializableExtra("skill1");
        // set data tvskill
        tvSkill.setText(skill.getSkill());

        //set data pnExperience
        String[] array_experience = this.getResources().getStringArray(R.array.experience_array);
        ArrayAdapter<CharSequence> adapterExperience = new ArrayAdapter(this
                ,R.layout.spinner_item, array_experience) { };
        adapterExperience.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pnExperence.setAdapter(adapterExperience);
        pnExperence.setSelection(adapterExperience.getPosition(skill.getExperience()));
    }

    private void initListener() {
        btSave.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                skill.setSkill(tvSkill.getText().toString());
                skill.setExperience(pnExperence.getSelectedItem().toString());
                returnIntent.putExtra("result", skill);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
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
