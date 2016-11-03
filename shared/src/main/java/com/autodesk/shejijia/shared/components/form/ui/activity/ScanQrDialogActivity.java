package com.autodesk.shejijia.shared.components.form.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;

/**
 * Created by t_aij on 16/11/1.
 */

public class ScanQrDialogActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTitle;
    private TextView mContent;
    private String mFormat;
    private String mError;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanqr_dialog);
        initIntent();
        initView();
        initData();

    }

    private void initIntent() {
        Intent intent = getIntent();
        mFormat = intent.getStringExtra("format");
        mError = intent.getStringExtra("error");


    }

    private void initData() {
        mTitle.setText("提示");
        mContent.setText(mError==null?mFormat:mError);
    }

    private void initView() {
        mTitle = (TextView) findViewById(R.id.tv_title);
        mContent = (TextView) findViewById(R.id.tv_content);

        findViewById(R.id.btn_submit).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(R.id.btn_submit == id) {
            startActivity(new Intent(this,ProjectIdCodeActivity.class));
            finish();
        } else if(R.id.btn_cancel == id) {
            finish();
        }
    }
}
