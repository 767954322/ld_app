package com.autodesk.shejijia.enterprise.base.activitys;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.socks.library.KLog;

/**
 * Created by t_xuz on 8/15/16.
 *
 */
public abstract class BaseActivity extends AppCompatActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentViewId());

        initData();

        initViews();

        initEvents();

        KLog.e("BaseActivity--",getClass().getSimpleName());
    }

    protected abstract int getContentViewId();

    protected abstract void initData();

    protected abstract void initViews();

    protected abstract void initEvents();

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
}
