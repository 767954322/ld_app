package com.autodesk.shejijia.shared.framework.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author lufan .
 * @version v1.0 .
 * @date 2016-6-12 .
 * @file BaseFragment.java .
 * @brief .
 */
public abstract class BaseFragment extends Fragment {
    protected String TAG = getClass().getSimpleName();

    /**
     * this context activity
     */
    protected Activity activity;
    /**
     * root view
     */
    protected View rootView;

    /**
     * transfer data
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(getLayoutResId(), container, false);
        initView();
        initData();
        initListener();
        return rootView;
    }


    public void onFragmentShown()
    {

    }
    /**
     * 获取当前布局的id
     *
     * @return
     */
    protected abstract int getLayoutResId();

    /**
     * 查找控件
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 监听事件
     */
    protected void initListener() {
    }


    public BaseFragment() {
    }
}
