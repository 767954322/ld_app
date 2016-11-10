package com.autodesk.shejijia.shared.components.form.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.form.contract.ProjectIdCodeContract;
import com.autodesk.shejijia.shared.components.form.presenter.ProjectIdCodePresenter;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

public class ProjectIdCodeActivity extends BaseActivity implements View.OnClickListener, ProjectIdCodeContract.View {

    private EditText mProjectIdEt;
    private ProjectIdCodePresenter mPresenter;
    private Button mConfirmBtn;
    private Toolbar mToolbar;
    private TextView mToolbarTitleTv;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_project_id_code;
    }

    @Override
    protected void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_topBar);
        mToolbarTitleTv = (TextView) findViewById(R.id.tv_toolbar_title);

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
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_confirm) {// TODO: 16/10/18 根据登入状态,项目编码一步
            mPresenter.confirmProject();
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
            ToastUtils.showShort(this, "this is home,暂时未确定,以后再添加");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setToolbar() {
        mToolbarTitleTv.setVisibility(View.GONE);
        mToolbar.setTitle("输入编码");
        mToolbar.setTitleTextColor(UIUtils.getColor(R.color.white));
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
