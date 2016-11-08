package com.autodesk.shejijia.enterprise.personalcenter.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.enterprise.personalcenter.fragment.ProjectListFragment;
import com.autodesk.shejijia.shared.framework.activity.NavigationConstructionActivity;

/**
 * Created by t_xuz on 11/8/16.
 * 个人中心－项目列表
 */

public class ProjectListActivity extends NavigationConstructionActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_personal_center_common;
    }

    @Override
    protected void initView() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar_topBar);
        initToolbar(toolbar, null, true, false, getString(R.string.personal_center_completed_project));
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ProjectListFragment projectListFragment = (ProjectListFragment) getSupportFragmentManager().findFragmentById(R.id.fly_personal_center);
        if (projectListFragment == null) {
            projectListFragment = ProjectListFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fly_personal_center, projectListFragment)
                    .commit();
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
