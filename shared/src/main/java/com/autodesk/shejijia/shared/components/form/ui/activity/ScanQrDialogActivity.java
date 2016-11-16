package com.autodesk.shejijia.shared.components.form.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;


/**
 * Created by t_aij on 16/11/1.
 */

public class ScanQrDialogActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTitle;
    private TextView mContent;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_scanqr_dialog;
    }

    @Override
    protected void initView() {
        mTitle = (TextView) findViewById(R.id.tv_title);
        mContent = (TextView) findViewById(R.id.tv_content);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String mFormat = intent.getStringExtra("format");
        String mError = intent.getStringExtra("error");

        mTitle.setText("提示");
        mContent.setText(mError == null ? mFormat : mError);
    }

    @Override
    protected void initListener() {
        findViewById(R.id.btn_submit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.btn_submit == id) {
            finish();
        }
    }
}
