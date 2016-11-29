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
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHPrecheckForm;
import com.autodesk.shejijia.shared.components.form.contract.FormContract;
import com.autodesk.shejijia.shared.components.form.presenter.FormPresenter;
import com.autodesk.shejijia.shared.components.form.ui.fragment.FormListFragment;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

import java.util.ArrayList;

/**
 * Created by t_aij on 16/11/17.
 */

public class FormActivity extends BaseActivity implements FormContract.View {

    private FrameLayout mMainLayout;
    public final String TAG_FORM_LIST_FRAGMENT = "formListFragment";
    public final String TAG_FORM_SUBLIST_FRAGMENT = "formSubListFragment";
    public final String TAG_FORM_ITEM_FRAGMENT = "itemListFragment";

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
        SHPrecheckForm shPrecheckForm = (SHPrecheckForm) intent.getSerializableExtra("shPrecheckForm");
        initToolbar(task);

        mPresenter = new FormPresenter(this);
        mPresenter.show(task);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            // TODO: 16/11/28 回退在做处理
            startActivity(new Intent(this, ScanQrCodeActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // TODO: 16/11/28 回退再再做处理
//        startActivity(new Intent(this, ScanQrCodeActivity.class));
//        finish();
    }

    private void initToolbar(Task task) {
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(task.getName());
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void showNetError(ResponseError error) {
        ToastUtils.showShort(this,"展示网络加载失败的界面");
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
    public void initFormList(ArrayList<String> titleList) {
        Bundle args = new Bundle();
        args.putStringArrayList("formTitles",titleList);
        FormListFragment formListFragment = FormListFragment.newInstance(args);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_main_container, formListFragment, TAG_FORM_LIST_FRAGMENT)
                .commit();
    }

    public FormPresenter getPresenter() {
        return mPresenter;
    }
}
