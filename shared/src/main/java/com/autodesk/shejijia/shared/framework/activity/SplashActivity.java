package com.autodesk.shejijia.shared.framework.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.autodesk.shejijia.shared.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;


/**
 * @author yangxuewu .
 * @version 1.0 .
 * @date 2015/12/15 0015 14:00 .
 * @filename SplashActivity
 * @brief 闪屏页activity, 闪出logo并延时进入首页.
 */
public abstract class SplashActivity extends BaseActivity {

    @Override
    protected int getLayoutResId()
    {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {}

    @Override
    protected void initData(Bundle savedInstanceState) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {

                if (getNextActivityToLaunch() != null)
                    startActivityAndFinish(getNextActivityToLaunch());
            }
        }, 1000);
    }

    protected  abstract Class getNextActivityToLaunch();

    @Override
    protected void initListener() {}

    protected void startActivityAndFinish(final Class clazz)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                startActivity(new Intent(SplashActivity.this, clazz));
                finish();
            }
        });
    }
}
