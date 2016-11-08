package com.autodesk.shejijia.shared.components.form.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.form.contract.PrecheckContract;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

/**
 * Created by t_aij on 16/10/25.
 */

public class PrecheckActivity extends BaseActivity implements View.OnClickListener, PrecheckContract.View {

    private RadioButton mOk;
    private RadioButton mNo;
    private LinearLayout mNecessary;
    private LinearLayout mAdditional;
    private Button mSelect;
    private Toolbar mToolbar;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_precheck;
    }

    @Override
    protected void initView() {
        //Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar_topBar);
        //验收条件的按钮
        mOk = (RadioButton) findViewById(R.id.btn_ok);
        mNo = (RadioButton) findViewById(R.id.btn_no);
        //必要条件
        mNecessary = (LinearLayout) findViewById(R.id.ll_necessary);
        //辅助条件
        mAdditional = (LinearLayout) findViewById(R.id.ll_additional);
        //确定按钮
        mSelect = (Button) findViewById(R.id.btn_select);
    }

    @Override
    protected void initExtraBundle() {
        // TODO: 16/10/27  获取到的task信息
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        // TODO: 16/10/27 意外终止时保存的数据
    }

    @Override
    protected void initListener() {
        // TODO: 16/10/27  设置监听事件
//        mLeft.setOnClickListener(this);
        mOk.setOnClickListener(this);
        mNo.setOnClickListener(this);
        mSelect.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        //合格与不合格的标识,0为初始化,1为合格,2为不合格
        int i = v.getId();
        if (i == R.id.btn_ok) {// TODO: 16/10/27 点击合格的按钮

        } else if (i == R.id.btn_no) {// TODO: 16/10/27 点击不合格的按钮


        } else if (i == R.id.btn_select) {// TODO: 16/10/27  点击验收的按钮


        } else {

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setToolbarTitle(String title) {
        mToolbar.setTitle(title);
        mToolbar.setTitleTextColor(UIUtils.getColor(R.color.black));
        mToolbar.setNavigationIcon(R.drawable.back_ico);
        setSupportActionBar(mToolbar);
    }

    @Override
    public void showNetError(String msg) {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}



























