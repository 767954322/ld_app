package com.autodesk.shejijia.shared.components.form.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.tools.login.RegisterOrLoginActivity;
import com.autodesk.shejijia.shared.components.common.utility.LoginUtils;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.common.utility.UserInfoUtils;
import com.autodesk.shejijia.shared.components.form.contract.ProjectIdCodeContract;
import com.autodesk.shejijia.shared.components.form.presenter.ProjectIdCodePresenter;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

public class ProjectIdCodeActivity extends BaseActivity implements View.OnClickListener, ProjectIdCodeContract.View, NavigationView.OnNavigationItemSelectedListener {

    private EditText mProjectIdEt;
    private ProjectIdCodePresenter mPresenter;
    private Button mConfirmBtn;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private TextView mUserNameView;
    private TextView mUserRoleView;
    private ImageButton mHeadPicBtn;
    private Toolbar mToolbar;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_project_id_code;
    }

    @Override
    protected void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.inspect_drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.inspect_navigation_view);
        View headerView = mNavigationView.getHeaderView(0);
        mUserNameView = (TextView) headerView.findViewById(R.id.tv_user_name);
        mUserRoleView = (TextView) headerView.findViewById(R.id.tv_user_role);
        mHeadPicBtn = (ImageButton) headerView.findViewById(R.id.imgBtn_personal_headPic);

        mProjectIdEt = (EditText) findViewById(R.id.et_project_id);
        mConfirmBtn = (Button) findViewById(R.id.btn_confirm);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mPresenter = new ProjectIdCodePresenter(this, this);  //初始化Presenter类
    }

    @Override
    protected void initListener() {
        mConfirmBtn.setOnClickListener(this);
        mNavigationView.setNavigationItemSelectedListener(this);
        mHeadPicBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_confirm) {
            mPresenter.confirmProject();
        } else if(id == R.id.imgBtn_personal_headPic) {
            // TODO: 16/1/21 加载出个人的头像
            ToastUtils.showShort(this,"后面在处理加载出的图片");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.form_scan_code_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.scan_code) {
            mPresenter.enterCode();
            return true;
        } else if (id == android.R.id.home) {
            initNavigationHeadState();
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.inspect_exit) {
            LoginUtils.doLogout(this);
            startActivity(new Intent(this, RegisterOrLoginActivity.class));
            finish();
        } else if(itemId == R.id.inspect_more) {
            // TODO: 16/11/21  样式不定,以后再做
            ToastUtils.showShort(this,"显示更多");
        }
        mDrawerLayout.closeDrawers();
        return true;
    }



    private void initNavigationHeadState() {
        if (!TextUtils.isEmpty(UserInfoUtils.getNikeName(this))) {
            mUserNameView.setText(UserInfoUtils.getNikeName(this));
            mUserRoleView.setText(getString(R.string.inspector));
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void setToolbar() {
        mToolbar.setTitle(R.string.input_code);
        mToolbar.setTitleTextColor(UIUtils.getColor(R.color.con_white));
        setSupportActionBar(mToolbar);
    }

    @Override
    public String getProjectId() {
        return mProjectIdEt.getText().toString().trim();
    }

    @Override
    public void dismiss() {
        finish();
    }


    @Override
    public void showNetError(String msg) {
        ToastUtils.showShort(this, msg);
    }

    @Override
    public void showError(String msg) {
        ToastUtils.showShort(this, msg);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


}
