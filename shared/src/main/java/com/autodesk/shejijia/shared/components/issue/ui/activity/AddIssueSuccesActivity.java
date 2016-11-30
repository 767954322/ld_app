package com.autodesk.shejijia.shared.components.issue.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

/**
 * Created by allengu on 16-12-15.
 */
public class AddIssueSuccesActivity extends BaseActivity {
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_issue_addsucces;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            finish();
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }
}
