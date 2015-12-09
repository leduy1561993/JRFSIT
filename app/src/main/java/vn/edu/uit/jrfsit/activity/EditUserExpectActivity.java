// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package vn.edu.uit.jrfsit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.view.View;

import vn.edu.uit.jrfsit.R;
import vn.edu.uit.jrfsit.utils.Utils;

public class EditUserExpectActivity extends AppCompatActivity {

    //region defination
    protected AppCompatEditText etEx;
    private AppCompatButton btCancel;
    private AppCompatButton btSave;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_edit_user_expect);
        initControlOnView();
        initListener();
        initData();
    }

    private void initControlOnView() {
        etEx = (AppCompatEditText) findViewById(R.id.et_user_expect);
        btCancel = (AppCompatButton) findViewById(R.id.btCancel_user_expect);
        btSave = (AppCompatButton) findViewById(R.id.btSave__user_expect);
    }

    private void initData() {
        String temp = getIntent().getStringExtra("expect");
        etEx.setText(temp);
    }

    private void initListener() {
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                if (etEx.getText().length() > 5) {
                    returnIntent.putExtra("result", etEx.getText().toString());
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                } else {
                    Utils.print(EditUserExpectActivity.this, "Hãy miêu tả chi tiết hơn để chúng tôi có thể giúp bạn");
                }
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
