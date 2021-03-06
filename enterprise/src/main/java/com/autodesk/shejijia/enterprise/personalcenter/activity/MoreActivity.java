package com.autodesk.shejijia.enterprise.personalcenter.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.enterprise.personalcenter.fragment.MoreFragment;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

/**
 * Created by t_xuz on 11/8/16.
 */

public class MoreActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_personal_center_common;
    }

    @Override
    protected void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.personal_center_more));
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            MoreFragment moreFragment = MoreFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fly_personal_center, moreFragment)
                    .commit();
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
