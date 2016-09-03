package com.autodesk.shejijia.enterprise.base.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.autodesk.shejijia.enterprise.projectlists.activitys.ProjectListsActivity;
import com.autodesk.shejijia.shared.components.common.tools.login.RegisterOrLoginActivity;

/**
 * Created by t_xuz on 9/2/16.
 *
 */
public class SplashActivity extends BaseActivity{

    @Override
    protected int getContentViewId() {
        return com.autodesk.shejijia.shared.R.layout.activity_splash;
    }

    @Override
    protected void initData(Bundle savedInstanceState) { }

    @Override
    protected void initViews() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getNextActivityToLaunch() != null)
                    startActivityAndFinish(getNextActivityToLaunch());
            }
        }, 1000);
    }

    @Override
    protected void initEvents() {

    }

    protected  Class getNextActivityToLaunch()
    {
        return null;
    }

    protected void startActivityAndFinish(final Class clazz) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, clazz));
                finish();
            }
        });
    }
}
