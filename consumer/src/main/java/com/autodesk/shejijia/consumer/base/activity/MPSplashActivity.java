package com.autodesk.shejijia.consumer.base.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.autodesk.shejijia.consumer.home.homepage.activity.MPConsumerHomeActivity;
import com.autodesk.shejijia.shared.components.common.utility.DataCleanManager;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPFileUtility;
import com.autodesk.shejijia.shared.components.common.utility.SharedPreferencesUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.SplashActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;


public class MPSplashActivity extends SplashActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 全屏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = this.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        //设置全屏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        //每次进应用都默认清除缓存
        DataCleanManager.cleanInternalCache(UIUtils.getContext());
        DataCleanManager.cleanCustomCache(getCacheDir().getAbsolutePath());
        MPFileUtility.clearCacheContent(this);
        ImageLoader.getInstance().clearDiskCache();
        ImageLoader.getInstance().clearMemoryCache();
    }

    @Override
    protected Class getNextActivityToLaunch() {
        //fixme: 16-9-12 需要引导页逻辑
//        Boolean isfirst = SharedPreferencesUtils.readBoolean(WelcomePagerAdapter.ISFIRST);
//        return isfirst ? MPConsumerHomeActivity.class : WelcomeActivity.class;
        return MPConsumerHomeActivity.class;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //设置如果应用到应用退至后台超过60秒，再次回到前端视为再次启动项目
        MobclickAgent.setSessionContinueMillis(60 * 1000);
        super.initData(savedInstanceState);
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
