package com.autodesk.shejijia.enterprise.base.activitys;

import com.autodesk.shejijia.shared.components.common.tools.login.RegisterOrLoginActivity;
import com.autodesk.shejijia.shared.framework.activity.SplashActivity;


public class MPSplashActivity extends SplashActivity
{
    @Override
    protected  Class getNextActivityToLaunch()
    {
        return RegisterOrLoginActivity.class;
    }
}
