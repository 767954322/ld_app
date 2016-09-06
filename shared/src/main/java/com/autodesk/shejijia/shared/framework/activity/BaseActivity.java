package com.autodesk.shejijia.shared.framework.activity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.autodesk.shejijia.shared.components.common.uielements.photoview.log.LogManager;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.feedback.PgyFeedbackShakeManager;
import com.pgyersdk.views.PgyerDialog;


/**
 * @author luchongbin .
 * @version v1.0 .
 * @date 2016-6-6 .
 * @file BaseActivity.java .
 * @brief Activity的基类 .
 */
public abstract class BaseActivity extends FragmentActivity {
    protected String TAG = getClass().getSimpleName();
    private boolean destroyed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        LogManager.getLogger().d("location",getClass().getSimpleName());
        initView();
        initExtraBundle();
        initData(savedInstanceState);
        initListener();
        //register pgy
        registerPgy();
    }

    private void registerPgy(){
        //crash注册
        PgyCrashManager.register(this);
        //version update
//        PgyUpdateManager.register(this); //放在主页里提示version update
        //设置反馈页面dialog的风格,符合app
        PgyerDialog.setDialogTitleBackgroundColor("#2B77C1");
        PgyerDialog.setDialogTitleTextColor("#ffffff");
    }
    /**
     * 获取布局的Id
     *
     * @return社会化设计师
     */
    protected abstract int getLayoutResId();

    /**
     * 查找控件
     */
    protected abstract void initView();

    /**
     * 获取bundle数据
     */
    protected void initExtraBundle() {
    }

    /**
     * 初始化数据操作
     *
     * @param savedInstanceState
     */
    protected abstract void initData(Bundle savedInstanceState);

    /**
     * 设置监听
     */
    protected void initListener() {
    }

    public boolean isDestroyed() {
        return destroyed;
    }


    @Override
    protected void onResume() {
        super.onResume();
        // 蒲公英feedback功能注册
        // custom sensitivity, defaults to 950, the smaller the number higher sensitivity.
        PgyFeedbackShakeManager.setShakingThreshold(1000);

        // Open as a dialog
        PgyFeedbackShakeManager.register(BaseActivity.this);

        // 以页面的形式展示反馈页面有点丑,屏蔽
        //  Open as an Activity, in the case you must configure FeedbackActivity in the file of AndroidManifest.xml
//        PgyFeedbackShakeManager.register(BaseActivity.this, false);

    }

    @Override
    protected void onPause() {
        super.onPause();
        // 蒲公英feedback取消注册功能
        PgyFeedbackShakeManager.unregister();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
}
