package com.autodesk.shejijia.consumer.home.homepage.activity;

import com.autodesk.shejijia.shared.framework.activity.SplashActivity;


public class MPSplashActivity extends SplashActivity
{
    @Override
    protected  Class getNextActivityToLaunch()
    {
        return MPConsumerHomeActivity.class;
    }
}
