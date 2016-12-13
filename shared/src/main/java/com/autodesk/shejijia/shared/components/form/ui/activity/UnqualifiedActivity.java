package com.autodesk.shejijia.shared.components.form.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.uielements.CustomDialog;
import com.autodesk.shejijia.shared.components.form.common.constant.SHFormConstant;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHPrecheckForm;
import com.autodesk.shejijia.shared.components.form.ui.fragment.UnqualifiedEditFragment;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

/**
 * Created by t_panya on 16/12/7.
 */

public class UnqualifiedActivity extends BaseActivity{
    private static final String TAG = "PrecheckUnqualifiedActivity";
    public static final String UNQUALIFIED_FORM = "unqualified_form";
    public static final String UNQUALIFIED_COMMENT = "unqualified_comment";
    public static final String UNQUALIFIED_AUDIO = "unqualified_audio";
    public static final String UNQUALIFIED_IMAGES = "unqualified_images";

    private UnqualifiedEditFragment mFragment;

    private SHPrecheckForm mPreCheckForm;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_unqualified;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mPreCheckForm = (SHPrecheckForm) intent.getSerializableExtra(UNQUALIFIED_FORM);
        Bundle params = new Bundle();
        params.putSerializable(UNQUALIFIED_FORM, mPreCheckForm);
        mFragment = UnqualifiedEditFragment.getInstance(params);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.fl_activity_fragment_container,mFragment, SHFormConstant.FragmentTag.FORM_UNQUALIFIED_EDIT);
        ft.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            showDialog("是否退出编辑");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDialog(String message) {
        new CustomDialog(this, new CustomDialog.OnDialogClickListenerCallBack() {
            @Override
            public void onClickOkListener() {
                finish();
            }

            @Override
            public void onClickCancelListener() {

            }
        }).showCustomViewDialog("提示",message,false,true,true,false);
    }
}
