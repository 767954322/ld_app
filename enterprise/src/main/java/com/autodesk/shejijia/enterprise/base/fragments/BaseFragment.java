package com.autodesk.shejijia.enterprise.base.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.autodesk.shejijia.enterprise.base.activitys.BaseActivity;
import com.autodesk.shejijia.enterprise.base.activitys.BaseFragmentActivity;

/**
 * Created by t_xuz on 8/15/16.
 *
 */
public abstract class BaseFragment extends Fragment{

    protected BaseActivity mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (BaseActivity) context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(),container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initViews(view,savedInstanceState);
        initEvents();
    }

    protected abstract int getLayoutId();

    protected abstract void initViews(View view,Bundle savedInstanceState);

    protected abstract void initData();

    protected abstract void initEvents();


}
