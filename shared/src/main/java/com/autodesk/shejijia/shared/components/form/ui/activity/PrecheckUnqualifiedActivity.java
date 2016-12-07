package com.autodesk.shejijia.shared.components.form.ui.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.CommentConfig;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.comment.CommentFragment;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.comment.CommentPresenter;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHPrecheckForm;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

/**
 * Created by t_panya on 16/12/7.
 */

public class PrecheckUnqualifiedActivity extends BaseActivity {
    private static final String TAG = "PrecheckUnqualifiedActivity";
    public static final String UNQUALIFIED_FORM = "unqualified_form";

    private FrameLayout mFragmentContainer;
    private ImageButton mEquipmentNotQualifiedBtn;
    private ImageButton mCustomDisagreeBtn;
    private ImageButton mMonitorAbsenceBtn;
    private ImageButton mUnStandardBtn;
    private CommentFragment mCommentFragment;
    private CommentPresenter mPresenter;

    private SHPrecheckForm mPreCheckForm;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_precheck_unqualified;
    }

    @Override
    protected void initView() {
        mFragmentContainer = (FrameLayout) findViewById(R.id.fl_comment_container);
        mEquipmentNotQualifiedBtn = (ImageButton) findViewById(R.id.imgbtn_equipment);
        mCustomDisagreeBtn = (ImageButton) findViewById(R.id.imgbtn_custom_disagree);
        mMonitorAbsenceBtn = (ImageButton) findViewById(R.id.imgbtn_monitor_absence);
        mUnStandardBtn = (ImageButton) findViewById(R.id.imgbtn_not_standard);
        mCommentFragment = CommentFragment.getInstance(new CommentConfig());
        mPresenter = new CommentPresenter(mCommentFragment,null);
        setFragment();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mPreCheckForm = (SHPrecheckForm) intent.getSerializableExtra(UNQUALIFIED_FORM);
    }

    private void setFragment(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.fl_comment_container,mCommentFragment,"common");
        ft.commitAllowingStateLoss();
    }
}
