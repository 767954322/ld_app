package com.autodesk.shejijia.shared.components.form.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
        if (getSupportFragmentManager().findFragmentByTag(SHFormConstant.FragmentTag.FORM_LIST_FRAGMENT).isVisible()) {
            showBackDialog();


        } else {
            super.onBackPressed();
        }
    }

    private void showBackDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alert_dialog__default_title);
        builder.setMessage("保存表单的数据么?");
        builder.setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
// TODO: 16/12/14 后期需要添加保存表单数据在本地
                dialog.dismiss();
                enterInspectorActivity();
            }

        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                enterInspectorActivity();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void enterInspectorActivity() {
// TODO: 16/12/14 后期做监理主界面的跳转
        Intent intent = new Intent(this, ScanQrCodeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}
