package com.autodesk.shejijia.enterprise.base.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.autodesk.shejijia.enterprise.base.BaseView;
import com.autodesk.shejijia.enterprise.base.activitys.BaseEnterpriseActivity;

/**
 * Created by t_xuz on 8/15/16.
 *
 */
public abstract class BaseEnterpriseFragment extends Fragment implements BaseView{

    protected BaseEnterpriseActivity mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (BaseEnterpriseActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResId(),container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view,savedInstanceState);
        initData();
        initListener();
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
    protected abstract void initView(View view,Bundle savedInstanceState);

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 监听事件
     */
    protected void initListener() {
    }


    @Override
    public void showNetError(String msg) {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
