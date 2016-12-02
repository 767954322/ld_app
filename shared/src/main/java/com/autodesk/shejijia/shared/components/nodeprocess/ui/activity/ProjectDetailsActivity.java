package com.autodesk.shejijia.shared.components.nodeprocess.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.issue.ui.activity.IssueListActivity;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment.ProjectDetailsFragment;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

/**
 * Created by t_xuz on 8/24/16.
 * 项目详情页面
 */
public class ProjectDetailsActivity extends BaseActivity implements View.OnClickListener {

    //bottom bar
    private TextView mPatrolBtn;
    private TextView mIssueBtn;
    private TextView mSessionBtn;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_project_details_main;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void initView() {
        //top bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            if (getIntent().hasExtra(ConstructionConstants.BUNDLE_KEY_PROJECT_NAME)) {
                String projectName = getIntent().getStringExtra(ConstructionConstants.BUNDLE_KEY_PROJECT_NAME);
                actionBar.setTitle(projectName);
            }
        }
        //bottom bar
        mPatrolBtn = (TextView) this.findViewById(R.id.tv_project_patrol);
        mIssueBtn = (TextView) this.findViewById(R.id.tv_project_issue);
        mSessionBtn = (TextView) this.findViewById(R.id.tv_project_session);
    }

    @Override
    protected void initListener() {
        mPatrolBtn.setOnClickListener(this);
        mIssueBtn.setOnClickListener(this);
        mSessionBtn.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            ProjectDetailsFragment projectDetailsFragment = ProjectDetailsFragment.newInstance();
            if (getIntent().hasExtra(ConstructionConstants.BUNDLE_KEY_PROJECT_ID)) {
                Bundle bundle = new Bundle();
                bundle.putLong(ConstructionConstants.BUNDLE_KEY_PROJECT_ID,
                        getIntent().getLongExtra(ConstructionConstants.BUNDLE_KEY_PROJECT_ID, 0));
                projectDetailsFragment.setArguments(bundle);
            }
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_project_main, projectDetailsFragment)
                    .commit();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_project_patrol) {

        } else if (id == R.id.tv_project_issue) {

            Intent intent = new Intent(ProjectDetailsActivity.this, IssueListActivity.class);
            intent.putExtra(ConstructionConstants.IssueTracking.REQUEST_TAG_KEY, ConstructionConstants.IssueTracking.REQUEST_TAG_SINGLE_ISSUE);
            startActivity(intent);

        } else if (id == R.id.tv_project_session) {

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
