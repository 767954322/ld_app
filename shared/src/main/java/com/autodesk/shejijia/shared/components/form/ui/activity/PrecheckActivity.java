package com.autodesk.shejijia.shared.components.form.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.form.common.entity.ContainedForm;
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
    private Button mOptionBtn;
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
        mOptionBtn = (Button) findViewById(R.id.btn_option);
    }

    @Override
    protected void initExtraBundle() {
        Task task = (Task) getIntent().getSerializableExtra("task");

        mPresenter = new PrecheckPresenter(this,this);
        mPresenter.showForm(task);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        // TODO: 16/10/27 意外终止时保存的数据
    }

    @Override
    protected void initListener() {
        mOkBtn.setOnClickListener(this);
        mNoBtn.setOnClickListener(this);
        mOptionBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.btn_ok) {// TODO: 16/10/27 点击合格的按钮
            mPresenter.showOkBtn();
        } else if (i == R.id.btn_no) {// TODO: 16/10/27 点击不合格的按钮
            mPresenter.showNoBtn();
        } else if (i == R.id.btn_option) {// TODO: 16/10/27  点击验收的按钮
            mPresenter.clickOptionBtn();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
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
        ActionBar actionBar = getSupportActionBar();
        if(null != actionBar) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void addNecessaryView(TextView view) {
        if(null != mNecessaryLayout) {
            mNecessaryLayout.addView(view);
        }
    }

    @Override
    public void addAdditionalLayout(View view) {
        if(null != mAdditionalLayout) {
            mAdditionalLayout.addView(view);
        }
    }

    @Override
    public void showQualifiedBtn() {
        if(null != mOptionBtn && null != mOkBtn) {
            mOptionBtn.setVisibility(View.VISIBLE);
            mOptionBtn.setText("同意,可进行质量验收");
            mOptionBtn.setBackgroundResource(R.drawable.ic_big_button_blue);

            mOkBtn.setTextColor(UIUtils.getColor(R.color.con_white));
            mNoBtn.setTextColor(UIUtils.getColor(R.color.form_btn_bg_grey));
        }
    }

    @Override
    public void showUnqualifiedBtn() {
        if(null != mOptionBtn && null != mNoBtn) {
            mOptionBtn.setVisibility(View.VISIBLE);
            mOptionBtn.setText("不同意验收");
            mOptionBtn.setBackgroundResource(R.drawable.ic_big_button_orange);

            mNoBtn.setTextColor(UIUtils.getColor(R.color.con_white));
            mOkBtn.setTextColor(UIUtils.getColor(R.color.form_btn_bg_grey));
        }
    }

    @Override
    public void enterQualified(Task task) {
        // TODO: 16/11/18 数据还未保存,需要将数据保存再内存中
        Intent intent = new Intent(this, FormListActivity.class);
        intent.putExtra("task",task);
        startActivity(intent);
    }

    @Override
    public void enterUnqualified(ContainedForm form) {
        // TODO: 16/11/18 数据还未保存,需要将数据保存再内存中
        ToastUtils.showShort(this,"另外再开启一个activity来处理验收条件不合格的情况");
    }

}



























