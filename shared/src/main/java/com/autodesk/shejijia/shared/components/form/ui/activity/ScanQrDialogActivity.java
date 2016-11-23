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

    private TextView mTitleTv;
    private TextView mContentTv;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_scanqr_dialog;
    }

    @Override
    protected void initView() {
        mTitleTv = (TextView) findViewById(R.id.tv_title);
        mContentTv = (TextView) findViewById(R.id.tv_content);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String mError = intent.getStringExtra("error");

        mTitleTv.setText(R.string.hint);
        mContentTv.setText(mError);
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
