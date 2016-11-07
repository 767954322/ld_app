package com.autodesk.shejijia.shared.framework.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.autodesk.shejijia.shared.R;

/**
 * Created by wenhulin on 11/7/16.
 */

public abstract class ToolBarActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(R.layout.activity_construction_navigation);
        ViewGroup contentContainer = (ViewGroup) findViewById(R.id.content_container);
        contentContainer.addView(LayoutInflater.from(this).inflate(layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(R.layout.activity_construction_navigation);
        ViewGroup contentContainer = (ViewGroup) findViewById(R.id.content_container);
        contentContainer.addView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(R.layout.activity_construction_navigation);
        ViewGroup contentContainer = (ViewGroup) findViewById(R.id.content_container);
        contentContainer.addView(view, params);
    }
}
