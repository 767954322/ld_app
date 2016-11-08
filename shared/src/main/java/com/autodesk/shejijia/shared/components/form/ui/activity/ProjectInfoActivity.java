package com.autodesk.shejijia.shared.components.form.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.Project;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.form.contract.ProjectInfoContract;
import com.autodesk.shejijia.shared.components.form.presenter.ProjectInfoPresenter;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

/**
 * Created by t_aij on 16/10/21.
 */
public class ProjectInfoActivity extends BaseActivity implements ProjectInfoContract.View, View.OnClickListener {

    private TextView mUsername;
    private TextView mTelephone;
    private TextView mAddress;
    private TextView mCommunity;
    private ProjectInfoPresenter mPresenter;
    private Project mProjectBean;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_project_info;
    }

    @Override
    protected void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_topBar);
        mToolbarTitle = (TextView) findViewById(R.id.tv_toolbar_title);
        //获取到页面的内容控件
        mUsername = (TextView) findViewById(R.id.tv_username);
        mTelephone = (TextView) findViewById(R.id.tv_telephone);
        mAddress = (TextView) findViewById(R.id.tv_address);
        mCommunity = (TextView) findViewById(R.id.tv_community);

    }

    @Override
    protected void initExtraBundle() {
        Intent intent = getIntent();
        mProjectBean = (Project) intent.getSerializableExtra("projectBean");

        mPresenter = new ProjectInfoPresenter(this);
        mPresenter.setCustomer(mProjectBean);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        findViewById(R.id.btn_confirm).setOnClickListener(this);
    }

    @Override
    public void setToolbar() {
        mToolbar.setTitle("设计家");
        mToolbar.setTitleTextColor(UIUtils.getColor(R.color.white));
        mToolbar.setNavigationIcon(R.drawable.ic_close);
        mToolbarTitle.setVisibility(View.GONE);
        setSupportActionBar(mToolbar);
    }

    @Override
    public void setUsername(String username) {
        mUsername.setText(UIUtils.getString(R.string.form_username) + username);
    }

    @Override
    public void setTelephone(String telephone) {
        mTelephone.setText(UIUtils.getString(R.string.form_telephone)+ telephone);
    }

    @Override
    public void setAddress(String address) {
        mAddress.setText(UIUtils.getString(R.string.form_address) + address);
    }

    @Override
    public void setCommunity(String community) {
        mCommunity.setText(UIUtils.getString(R.string.form_community) + community);
    }

    @Override
    public void selectConfirm() {
        // TODO: 16/10/21 判断状态,选择进入的表格?
        Intent intent = new Intent(this,PrecheckActivity.class);
        startActivity(intent);
    }


    @Override
    public void selectCancel() {
        startActivity(new Intent(this,ProjectIdCodeActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_confirm) {
            mPresenter.confirm();

        } else if (i == R.id.btn_cancel) {
            mPresenter.cancel();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            mPresenter.cancel();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        mPresenter.cancel();
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
