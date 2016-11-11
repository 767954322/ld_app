package com.autodesk.shejijia.shared.components.form.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.form.contract.PrecheckContract;
import com.autodesk.shejijia.shared.components.form.presenter.PrecheckPresenter;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

/**
 * Created by t_aij on 16/10/25.
 */

public class PrecheckActivity extends BaseActivity implements View.OnClickListener, PrecheckContract.View {

    private RadioButton mOkBtn;
    private RadioButton mNoBtn;
    private LinearLayout mNecessaryLayout;
    private LinearLayout mAdditionalLayout;
    private Button mSelectBtn;
    private Toolbar mToolbar;
    private Task mTask;
    private PrecheckPresenter mPresenter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_precheck;
    }

    @Override
    protected void initView() {
        //验收条件的按钮
        mOkBtn = (RadioButton) findViewById(R.id.btn_ok);
        mNoBtn = (RadioButton) findViewById(R.id.btn_no);
        //必要条件
        mNecessaryLayout = (LinearLayout) findViewById(R.id.ll_necessary);
        //辅助条件
        mAdditionalLayout = (LinearLayout) findViewById(R.id.ll_additional);
        //确定按钮
        mSelectBtn = (Button) findViewById(R.id.btn_select);
    }

    @Override
    protected void initExtraBundle() {
        // TODO: 16/10/27  获取到的task信息
        mTask = (Task) getIntent().getSerializableExtra("task");

        mPresenter = new PrecheckPresenter(this);
        mPresenter.showForm(mTask);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        // TODO: 16/10/27 意外终止时保存的数据
    }

    @Override
    protected void initListener() {
        // TODO: 16/10/27  设置监听事件
        mOkBtn.setOnClickListener(this);
        mNoBtn.setOnClickListener(this);
        mSelectBtn.setOnClickListener(this);
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
//            finish();
            ToastUtils.showShort(this,"back");
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}



























