package com.autodesk.shejijia.shared.framework.activity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.autodesk.shejijia.shared.components.common.uielements.photoview.log.LogManager;


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
