package com.autodesk.shejijia.shared.components.issue.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.issue.common.entity.IssueDescription;
import com.autodesk.shejijia.shared.components.issue.contract.AddIssueDescriptionContract;
import com.autodesk.shejijia.shared.components.issue.presenter.AddIssueDescriptionPresent;
import com.autodesk.shejijia.shared.components.issue.ui.fragment.AddIssueTrackingFragment;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

import java.util.List;

/**
 * Created by Menghao.Gu on 2016/12/6.
 */

public class AddIssueListActivity extends BaseActivity implements AddIssueDescriptionContract.View {
    private AddIssueDescriptionPresent mTrackingPresent;
    private AddIssueTrackingFragment addIssueTrackingFragment;
    private IssueDescription mIssueDescription;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_add_issuetracking;
    }

    @Override
    protected void initView() {


    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            addIssueTrackingFragment = AddIssueTrackingFragment.getInstance();
            mTrackingPresent = new AddIssueDescriptionPresent(this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_add_issuetracking, addIssueTrackingFragment)
                    .commit();
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
            // TODO 上传图片语音文字
//            mTrackingPresent.putIssueTracking(mIssueDescription.getmDescription(), mIssueDescription.getmAudioPath()
//                    , mIssueDescription.getmImagePath());
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onShowStatus(boolean status) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && null != data) {
            switch (requestCode) {
                case ConstructionConstants.IssueTracking.ADD_ISSUE_DESCRIPTION_RESULT_CODE:
                    mIssueDescription = (IssueDescription) data.getSerializableExtra(ConstructionConstants.IssueTracking.ADD_ISSUE_DESCRIPTION_RESULT_KEY);
                default:
                    break;
            }
        }
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
