package com.autodesk.shejijia.shared.components.nodeprocess.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
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
    //topBar
    private Toolbar toolbar;
    private TextView toolbarTitle;//self define

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_project_details_main;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void initView() {
        //top bar
        toolbar = (Toolbar) this.findViewById(R.id.toolbar_topBar);
        toolbarTitle = (TextView) toolbar.findViewById(R.id.tv_toolbar_title);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_ico);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
        ProjectDetailsFragment projectDetailsFragment = (ProjectDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fl_project_main);
        if (projectDetailsFragment == null) {
            projectDetailsFragment = ProjectDetailsFragment.newInstance();
            if (getIntent().hasExtra("projectId")) {
                Bundle bundle = new Bundle();
                bundle.putLong("projectId", getIntent().getLongExtra("projectId", 0));
                projectDetailsFragment.setArguments(bundle);
            }
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_project_main, projectDetailsFragment)
                    .commit();
        }
        if (getIntent().hasExtra("projectName") && !TextUtils.isEmpty(getIntent().getStringExtra("projectName"))) {
            toolbarTitle.setText(getIntent().getStringExtra("projectName"));
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_project_patrol) {

        } else if (id == R.id.tv_project_issue) {

        } else if (id == R.id.tv_project_session) {

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
