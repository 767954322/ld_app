package com.autodesk.shejijia.shared.framework.activity;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;

/**
 * Created by t_xuz on 11/8/16.
 * toolbar 统一管理类
 */

public abstract class NavigationConstructionActivity extends BaseActivity {

    protected void initToolbar(Toolbar toolbar, @Nullable TextView toolbarTitle, boolean homeAsUpEnabled, boolean isSelfDefineTile, String title) {
        if (!isSelfDefineTile) {
            toolbar.setTitle(title);
            toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
            if (toolbarTitle != null) {
                toolbarTitle.setVisibility(View.GONE);
            }
        } else {
            toolbar.setTitle("");
            if (toolbarTitle != null) {
                toolbarTitle.setVisibility(View.VISIBLE);
                toolbarTitle.setText(title);
            }
        }
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }
}
