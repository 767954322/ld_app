package com.autodesk.shejijia.shared.components.issue.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment.IssueListFragment;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment.ProjectDetailsFragment;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

/**
 * Created by Menghao.Gu on 2016/12/2.
 */

public class IssueListActivity extends BaseActivity {
    private FrameLayout mIssueList;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_issue_list_view;
    }

    @Override
    protected void initView() {
        mIssueList = (FrameLayout) findViewById(R.id.fl_issue_list_main);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        IssueListFragment issueListFragment = IssueListFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_issue_list_main, issueListFragment)
                .commit();

    }
}
