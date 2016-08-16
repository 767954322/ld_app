package com.autodesk.shejijia.enterprise.base.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.autodesk.shejijia.enterprise.base.activitys.BaseActivity;

/**
 * Created by t_xuz on 8/15/16.
 *
 */
public class BaseFragment extends Fragment{

    protected BaseActivity mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (BaseActivity)context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
