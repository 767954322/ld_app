package com.autodesk.shejijia.enterprise.nodeprocess.projectlists.activitys;

import com.autodesk.shejijia.enterprise.base.activitys.SplashActivity;
import com.autodesk.shejijia.shared.components.common.tools.login.RegisterOrLoginActivity;


public class MPSplashActivity extends SplashActivity
{
    @Override
    protected  Class getNextActivityToLaunch()
    {
        return RegisterOrLoginActivity.class;
    }
}
