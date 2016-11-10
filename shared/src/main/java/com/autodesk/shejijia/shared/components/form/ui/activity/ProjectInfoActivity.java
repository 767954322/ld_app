package com.autodesk.shejijia.shared.components.form.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Building;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Member;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.form.contract.ProjectInfoContract;
import com.autodesk.shejijia.shared.components.form.presenter.ProjectInfoPresenter;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

/**
 * Created by t_aij on 16/10/21.
 */
public class ProjectInfoActivity extends BaseActivity implements ProjectInfoContract.View, View.OnClickListener {

    private TextView mUsernameTv;
    private TextView mTelephoneTv;
    private TextView mAddressTv;
    private TextView mCommunityTv;
    private ProjectInfoPresenter mPresenter;
    private Toolbar mToolbar;
    private TextView mToolbarTitleTv;
    private Task mTask;
    private Member mMember;
    private Building mBuilding;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_project_info;
    }

    @Override
    protected void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_topBar);
        mToolbarTitleTv = (TextView) findViewById(R.id.tv_toolbar_title);
        //获取到页面的内容控件
        mUsernameTv = (TextView) findViewById(R.id.tv_username);
        mTelephoneTv = (TextView) findViewById(R.id.tv_telephone);
        mAddressTv = (TextView) findViewById(R.id.tv_address);
        mCommunityTv = (TextView) findViewById(R.id.tv_community);

    }

    @Override
    protected void initExtraBundle() {
        Intent intent = getIntent();
        mTask = (Task) intent.getSerializableExtra("task");
        mMember = (Member) intent.getSerializableExtra("member");
        mBuilding = (Building) intent.getSerializableExtra("building");

        mPresenter = new ProjectInfoPresenter(this);
//        mPresenter.setCustomer(mProjectBean);
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
        mToolbarTitleTv.setVisibility(View.GONE);
        setSupportActionBar(mToolbar);

        mUsernameTv.setText(UIUtils.getString(R.string.form_username) + mMember.getProfile().getName());
        mTelephoneTv.setText(UIUtils.getString(R.string.form_telephone) + mMember.getProfile().getMobile());
//        String provinceName = building.getProvinceName();
//        String cityName = building.getCityName();
//        if(cityName.contains(provinceName)) {
//            mView.setAddress(cityName + building.getDistrictName() + building.getDistrict() + "号");
//        } else {
//            mView.setAddress(provinceName + cityName + building.getDistrictName());
//        }
//        mView.setCommunity(building.getCommunityName());
        String address = mBuilding.getCityName() + mBuilding.getDistrictName() + mBuilding.getDistrict();
        mAddressTv.setText(UIUtils.getString(R.string.form_address) + address);
        mCommunityTv.setText(UIUtils.getString(R.string.form_community) + mBuilding.getCommunityName());
    }

    public void setUsername(String usernameTv) {
        mUsernameTv.setText(UIUtils.getString(R.string.form_username) + usernameTv);
    }

    @Override
    public void setTelephone(String telephone) {
        mTelephoneTv.setText(UIUtils.getString(R.string.form_telephone)+ telephone);
    }

    @Override
    public void setAddress(String address) {
        mAddressTv.setText(UIUtils.getString(R.string.form_address) + address);
    }

    @Override
    public void setCommunity(String community) {
        mCommunityTv.setText(UIUtils.getString(R.string.form_community) + community);
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
