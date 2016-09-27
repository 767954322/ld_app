package com.autodesk.shejijia.shared.framework.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.utility.DataCleanManager;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPFileUtility;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.receiver.JPushMessageReceiver;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

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
public class SplashActivity extends BaseActivity
{

    @Override
    protected int getLayoutResId()
    {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView()
    {
        //每次进应用都默认清除缓存
        DataCleanManager.cleanInternalCache(UIUtils.getContext());
        DataCleanManager.cleanCustomCache(getCacheDir().getAbsolutePath());
        MPFileUtility.clearCacheContent(this);
        ImageLoader.getInstance().clearDiskCache();
        ImageLoader.getInstance().clearMemoryCache();
    }

    @Override
    protected void initData(Bundle savedInstanceState)
    {

        clearUnreadCount();
        /**
         * 使用时长（t2为应用在后台运行的时间）
         * t2＜30s，则本次启动的总时长t=t1+t2+t3
         * t2≥30s，则本次启动的总时长t=t1，t3算为一次新启动的时长
         *
         * @param savedInstanceState
         */
        //设置如果应用到应用退至后台超过60秒，再次回到前端视为再次启动项目
        MobclickAgent.setSessionContinueMillis(60 * 1000);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {

                if (getNextActivityToLaunch() != null)
                    startActivityAndFinish(getNextActivityToLaunch());
            }
        }, 1000);
    }

    protected Class getNextActivityToLaunch()
    {
        return null;
    }

    @Override
    protected void initListener()
    {
    }

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

    @Override
    public void onResume()
    {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ImageUtils.clearCache();
    }

    private void clearUnreadCount()
    {
        Intent intent = this.getIntent();

        if (intent != null)
        {
            Bundle bundle = intent.getExtras();

            if (bundle != null && bundle.containsKey(JPushInterface.EXTRA_MESSAGE))
            {
                String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);

                String notificationKey = String.valueOf(JPushMessageReceiver.DEFAULT_NOTIFICATION_ID);

                try
                {
                    JSONObject jsonObject = new JSONObject(message);

                    if (jsonObject != null)
                    {

                        JSONArray jArray = jsonObject.getJSONArray("data");

                        if (jsonObject != null && jArray.length() > 1)
                            notificationKey = jArray.getString(1); //get member id
                        else if (jsonObject.has("appId"))//give app level id
                            notificationKey = jsonObject.getString("appId");

                        SharedPreferences sharedpreferences = getSharedPreferences(AdskApplication.JPUSH_STORE_KEY, Context.MODE_PRIVATE);

                        if (sharedpreferences != null && sharedpreferences.contains(notificationKey))
                        {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putInt(notificationKey, 0);
                            editor.commit();
                        }
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }

    }
}
