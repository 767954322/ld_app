package com.autodesk.shejijia.shared.components.form.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.form.common.constant.SHFormConstant;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHPrecheckForm;
import com.autodesk.shejijia.shared.components.form.ui.fragment.FormListFragment;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

/**
 * Created by t_aij on 16/11/17.
 */

public class FormActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_form;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Task task = (Task) intent.getSerializableExtra("task");
        SHPrecheckForm precheckForm = (SHPrecheckForm) intent.getSerializableExtra("shPrecheckForm");

        Bundle args = new Bundle();
        args.putSerializable("task", task);
        args.putSerializable("precheckForm", precheckForm);
        FormListFragment formListFragment = FormListFragment.newInstance(args);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_main_container, formListFragment, SHFormConstant.FragmentTag.FORM_LIST_FRAGMENT)
                .commit();
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
        } else {
            super.onBackPressed();
        }
    }

}
