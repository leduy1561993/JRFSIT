package vn.edu.uit.jrfsit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ArrayAdapter;

import vn.edu.uit.jrfsit.R;
import vn.edu.uit.jrfsit.entity.Skill;


/**
 * Created by LeDuy on 11/21/2015.
 */
public class EditSkillActivity extends AppCompatActivity {
    protected AppCompatTextView tvSkill;
    protected AppCompatSpinner pnExperence;
    private AppCompatButton btCancel;
    private AppCompatButton btSave;
    private Skill skill;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_editskill);
        initControlOnView();
        initData();
        initListener();
    }

    /**
     * initControlOnView
     */
    private void initControlOnView(){
        tvSkill = (AppCompatTextView) findViewById(R.id.tv_skill_edit);
        pnExperence = (AppCompatSpinner) findViewById(R.id.sn_experience);
        btCancel = (AppCompatButton) findViewById(R.id.btCancel_Skill);
        btSave = (AppCompatButton) findViewById(R.id.btSave_Skill);
    }

    /**
     * initData
     */
    private void initData(){
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

    /**
     * initListener
     */
    private void initListener(){
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                skill.setSkill(tvSkill.getText().toString());
                skill.setExperience(pnExperence.getSelectedItem().toString());
                returnIntent.putExtra("result",skill );
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
