package com.autodesk.shejijia.enterprise.homepage.activity;

import com.autodesk.shejijia.shared.framework.activity.SplashActivity;


public class MPSplashActivity extends SplashActivity
{
    @Override
    protected  Class getNextActivityToLaunch()
    {
        return MPEnterpriseHomeActivity.class;
    }
}
