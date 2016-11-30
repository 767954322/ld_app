package com.autodesk.shejijia.shared.framework.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.autodesk.shejijia.shared.BuildConfig;
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
public abstract class BaseActivity extends AppCompatActivity {
    protected String TAG = getClass().getSimpleName();
    private boolean destroyed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setContentView(getLayoutResId());
        registerPyg();
        LogManager.getLogger().d("ClassName", getClass().getSimpleName());
        initView();
        initExtraBundle();
        initData(savedInstanceState);
        initListener();

    }

    private void registerPyg(){
        //Register pgy
        if (BuildConfig.DEBUG) {
            PgyCrashManager.register(this);
            PgyerDialog.setDialogTitleBackgroundColor("#2B77C1");
            PgyerDialog.setDialogTitleTextColor("#ffffff");
        }
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
    protected void initExtraBundle() {}

    /**
     * 初始化数据操作
     *
     * @param savedInstanceState
     */
    protected abstract void initData(Bundle savedInstanceState);

    /**
     * 设置监听
     */
    protected void initListener() {}

    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BuildConfig.DEBUG) {
            // custom sensitivity, defaults to 950, the smaller the number higher sensitivity.
            PgyFeedbackShakeManager.setShakingThreshold(1000);
            // Open as a dialog
            PgyFeedbackShakeManager.register(BaseActivity.this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (BuildConfig.DEBUG){
            PgyFeedbackShakeManager.unregister();
        }
    }
}
