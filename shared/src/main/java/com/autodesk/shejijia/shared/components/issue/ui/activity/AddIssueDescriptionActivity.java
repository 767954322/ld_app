package com.autodesk.shejijia.shared.components.issue.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.uielements.commoncomment.CommentConfig;
import com.autodesk.shejijia.shared.components.common.uielements.commoncomment.comment.CommentFragment;
import com.autodesk.shejijia.shared.components.common.uielements.commoncomment.comment.CommentPresenter;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.issue.contract.AddIssueDescriptionContract;
import com.autodesk.shejijia.shared.components.issue.presenter.AddIssueDescriptionPresent;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

/**
 * Created by Menghao.Gu on 2016/12/5.
 */

public class AddIssueDescriptionActivity extends BaseActivity implements AddIssueDescriptionContract.View {
    private FrameLayout mAddIssueTracking;
    private CommentFragment addIssueTrackingFragment;
    private CommentPresenter mPresenter;
    private AddIssueDescriptionPresent mTrackingPresent;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_add_issuetracking;
    }

    @Override
    protected void initView() {

        mAddIssueTracking = (FrameLayout) findViewById(R.id.fl_add_issuetracking);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            mTrackingPresent = new AddIssueDescriptionPresent(this);
            addIssueTrackingFragment = CommentFragment.getInstance(new CommentConfig());

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_add_issuetracking, addIssueTrackingFragment)
                    .commit();
            mPresenter = new CommentPresenter(addIssueTrackingFragment, null);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.issuetraction_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            finish();
            return true;
        } else if (i == R.id.add_traction) {
            mTrackingPresent.putIssueTracking(addIssueTrackingFragment.getCommentContent(), addIssueTrackingFragment.getAudioRecordPath(), addIssueTrackingFragment.getImagePathList());
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onShowStatus(boolean status) {

        ToastUtils.showLong(this, "添加成功");

    }

    @Override
    public void showNetError(ResponseError error) {

    }

    @Override
    public void showError(String errorMsg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


}
