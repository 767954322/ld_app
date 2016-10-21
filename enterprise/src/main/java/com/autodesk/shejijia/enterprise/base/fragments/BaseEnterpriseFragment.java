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
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

/**
 * Created by t_xuz on 8/15/16.
 *
 */
public abstract class BaseEnterpriseFragment extends BaseFragment implements BaseView{

    protected BaseEnterpriseActivity mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (BaseEnterpriseActivity) context;
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
