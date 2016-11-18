package com.autodesk.shejijia.shared.components.form.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.form.ui.fragment.FormListFragment;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

/**
 * Created by t_aij on 16/11/17.
 */

public class FormListActivity extends BaseActivity {

    private FrameLayout mMainLayout;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_form_list;
    }

    @Override
    protected void initView() {
        mMainLayout = (FrameLayout) findViewById(R.id.fl_form_main);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Task task = (Task) intent.getSerializableExtra("task");

        FormListFragment formListFragment = FormListFragment.newInstance(task);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_form_main,formListFragment,"formlistFragment")
                .commit();
    }
}
