package com.autodesk.shejijia.consumer.home.homepage.activity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.autodesk.shejijia.shared.framework.activity.SplashActivity;


public class MPSplashActivity extends SplashActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Class getNextActivityToLaunch() {
        return MPConsumerHomeActivity.class;
    }
}
