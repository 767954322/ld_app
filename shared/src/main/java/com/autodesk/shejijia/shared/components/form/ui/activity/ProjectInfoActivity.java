package com.autodesk.shejijia.shared.components.form.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView mCommunite;
    private ProjectInfoPresenter mPresenter;
    private Project mProjectBean;
    private TextView mCenter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_project_info;
    }

    @Override
    protected void initView() {
        mCenter = (TextView) findViewById(R.id.tv_center);
        //获取到页面的内容控件
        mUsername = (TextView) findViewById(R.id.tv_username);
        mTelephone = (TextView) findViewById(R.id.tv_telephone);
        mAddress = (TextView) findViewById(R.id.tv_address);
        mCommunite = (TextView) findViewById(R.id.tv_communite);

    }

    @Override
    protected void initExtraBundle() {
        Intent intent = getIntent();
        mProjectBean = (Project) intent.getSerializableExtra("projectBean");

        mPresenter = new ProjectInfoPresenter(this);
        mPresenter.setNavigation();
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
    public void setNavigation() {
        //设置navigationBar的显示
        findViewById(R.id.rl_navigationbar).setBackgroundResource(R.color.form_bar_bg_blue);
        mCenter.setText("设计家");
        mCenter.setTextColor(UIUtils.getColor(R.color.form_text_bar_write));
        findViewById(R.id.iv_left).setVisibility(View.GONE);
        findViewById(R.id.tv_right).setVisibility(View.GONE);
    }

    @Override
    public void setUsername(String username) {
        mUsername.setText(mUsername.getText() + username);
    }

    @Override
    public void setTelephone(String telephone) {
        mTelephone.setText(mTelephone.getText() + telephone);
    }

    @Override
    public void setAddress(String address) {
        mAddress.setText(mAddress.getText() + address);
    }

    @Override
    public void setCommunite(String communite) {
        mCommunite.setText(mCommunite.getText() + communite);
    }

    @Override
    public void selectConfirm() {
        // TODO: 16/10/21 进入表格
        Toast.makeText(this, "进入表格吧", Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(this,PrecheckActivity.class));
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
