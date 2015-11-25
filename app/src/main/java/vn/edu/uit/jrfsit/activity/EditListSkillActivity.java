package vn.edu.uit.jrfsit.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.ListViewCompat;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import vn.edu.uit.jrfsit.R;
import vn.edu.uit.jrfsit.adapter.EditListSkillAdapter;
import vn.edu.uit.jrfsit.entity.Skill;

/**
 * Created by LeDuy on 11/1/2015.
 */
public class EditListSkillActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    //Skill
    ImageButton btDeleteSkill;
    AppCompatButton btSavekill;
    AppCompatButton btCancelSkill;
    ListViewCompat lvSkill;
    List<Skill> list ;
    ArrayAdapter<Skill> adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_edit_list_skill);
        initControlOnView();
        initData();
        initListener();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    private void initControlOnView(){
        btDeleteSkill = (ImageButton) findViewById(R.id.bt_delete_edit_skill);
        btSavekill = (AppCompatButton) findViewById(R.id.btSave_job_edit_list_skill);
        btCancelSkill = (AppCompatButton) findViewById(R.id.btCancel_job_edit_list_skill);
        lvSkill = (ListViewCompat) findViewById(R.id.lv_edit_skill_job_profile);
    }

    private void initData(){
        //set data apdapter
        list = new ArrayList<Skill>();
        //list = ProfileUserActivity.list;
        adapter = new EditListSkillAdapter(this,list);
        lvSkill.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void initListener(){
        lvSkill.setOnItemClickListener(this);
    }
}