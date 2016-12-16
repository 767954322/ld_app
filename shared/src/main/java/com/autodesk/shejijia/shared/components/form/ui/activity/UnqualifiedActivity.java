package com.autodesk.shejijia.shared.components.form.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.uielements.CustomDialog;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.form.common.constant.SHFormConstant;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHPrecheckForm;
import com.autodesk.shejijia.shared.components.form.ui.fragment.UnqualifiedEditFragment;
import com.autodesk.shejijia.shared.components.form.ui.fragment.UnqualifiedSubmitFragment;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

/**
 * Created by t_panya on 16/12/7.
 */

public class UnqualifiedActivity extends BaseActivity implements UnqualifiedSubmitFragment.onFinishListener, View.OnClickListener {
    private static final String TAG = "PrecheckUnqualifiedActivity";
    public static final String UNQUALIFIED_FORM = "unqualified_form";
    public static final String UNQUALIFIED_COMMENT = "unqualified_comment";
    public static final String UNQUALIFIED_AUDIO = "unqualified_audio";
    public static final String UNQUALIFIED_IMAGES = "unqualified_images";

    private UnqualifiedEditFragment mFragment;

    private SHPrecheckForm mPreCheckForm;

    private RelativeLayout mSubmitSuccess;
    private ScrollView mScrollView;
    private Button mSubmitBtn;
    private AlertDialog mAlertDialog;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_unqualified;
    }

    @Override
    protected void initView() {
        mSubmitSuccess = (RelativeLayout) findViewById(R.id.rl_submit_success_container);
        mScrollView = (ScrollView) findViewById(R.id.sc_unqualified_container);
        mSubmitBtn = (Button) findViewById(R.id.btn_submit_success);
        mSubmitBtn.setOnClickListener(this);
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
    public void onClick(View v) {
        if(v.getId() == R.id.btn_submit_success){
            finish();
        }
    }

    @Override
    public void onSubmitFinish() {
        if(UnqualifiedSubmitFragment.isSubmitFinish){
            mSubmitSuccess.setVisibility(View.VISIBLE);
            mScrollView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if(UnqualifiedSubmitFragment.isSubmitFinish){
            finish();
            return;
        }
        showDialog("是否退出编辑");
        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            if(UnqualifiedSubmitFragment.isSubmitFinish){
                finish();
                return true;
            }
            showDialog("是否退出编辑");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage(message);
        builder.setPositiveButton(UIUtils.getString(R.string.sure), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton(UIUtils.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAlertDialog.dismiss();
            }
        });
        mAlertDialog = builder.show();

//        new CustomDialog(this, new CustomDialog.OnDialogClickListenerCallBack() {
//            @Override
//            public void onClickOkListener() {
//                finish();
//            }
//
//            @Override
//            public void onClickCancelListener() {
//
//            }
//        }).showCustomViewDialog("提示",message,false,true,true,false);
    }
}
