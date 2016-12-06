package com.autodesk.shejijia.shared.components.issue.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.issue.ui.fragment.IssueListFragment;
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

        if (savedInstanceState == null) {

            IssueListFragment issueListFragment = IssueListFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_issue_list_main, issueListFragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
