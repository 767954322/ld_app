package com.autodesk.shejijia.shared.components.form.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.form.common.constant.SHFormConstant;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHPrecheckForm;
import com.autodesk.shejijia.shared.components.form.contract.FormContract;
import com.autodesk.shejijia.shared.components.form.presenter.FormPresenter;
import com.autodesk.shejijia.shared.components.form.ui.fragment.FormListFragment;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

/**
 * Created by t_aij on 16/11/17.
 */

public class FormActivity extends BaseActivity implements FormContract.View {

    private FrameLayout mMainLayout;

    private FormPresenter mPresenter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_form;
    }

    @Override
    protected void initView() {
        mMainLayout = (FrameLayout) findViewById(R.id.fl_main_container);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Task task = (Task) intent.getSerializableExtra("task");
        // TODO: 16/11/30 验收条件的处理的表单
        SHPrecheckForm shPrecheckForm = (SHPrecheckForm) intent.getSerializableExtra("shPrecheckForm");
        initToolbar(task.getName());

        mPresenter = new FormPresenter(this);
        mPresenter.show(task);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // TODO: 16/11/30 回退时在做处理,弹框确认
        if (getSupportFragmentManager().findFragmentByTag(SHFormConstant.FragmentTag.FORM_LIST_FRAGMENT).isVisible()) {
            startActivity(new Intent(this, ScanQrCodeActivity.class));
            finish();
        }
        super.onBackPressed();
    }

    @Override
    public void showNetError(ResponseError error) {
        ToastUtils.showShort(this, "展示网络加载失败的界面");
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
    public void initFormList(String title) {
        Bundle args = new Bundle();
        args.putString("title", title);
        FormListFragment formListFragment = FormListFragment.newInstance(args);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_main_container, formListFragment, SHFormConstant.FragmentTag.FORM_LIST_FRAGMENT)
                .commit();
    }

    public void initToolbar(String title) {
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(title);
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    public FormPresenter getPresenter() {
        return mPresenter;
    }
}
