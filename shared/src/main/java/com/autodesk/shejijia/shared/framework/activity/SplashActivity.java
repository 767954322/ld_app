package com.autodesk.shejijia.shared.framework.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.utility.DataCleanManager;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPFileUtility;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;


/**
 * @author yangxuewu .
 * @version 1.0 .
 * @date 2015/12/15 0015 14:00 .
 * @filename SplashActivity
 * @brief 闪屏页activity, 闪出logo并延时进入首页.
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        //每次进应用都默认清除缓存
        DataCleanManager.cleanInternalCache(UIUtils.getContext());
        DataCleanManager.cleanCustomCache(getCacheDir().getAbsolutePath());
        MPFileUtility.clearCacheContent(this);
        ImageLoader.getInstance().clearDiskCache();
        ImageLoader.getInstance().clearMemoryCache();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        /**
         * 使用时长（t2为应用在后台运行的时间）
         * t2＜30s，则本次启动的总时长t=t1+t2+t3
         * t2≥30s，则本次启动的总时长t=t1，t3算为一次新启动的时长
         *
         * @param savedInstanceState
         */
        //设置如果应用到应用退至后台超过60秒，再次回到前端视为再次启动项目
        MobclickAgent.setSessionContinueMillis(60 * 1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (getNextActivityToLaunch() != null)
                    startActivityAndFinish(getNextActivityToLaunch());
            }
        }, 1000);
    }

    protected  Class getNextActivityToLaunch()
    {
        return null;
    }

    @Override
    protected void initListener() {
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

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageUtils.clearCache();
    }
}
