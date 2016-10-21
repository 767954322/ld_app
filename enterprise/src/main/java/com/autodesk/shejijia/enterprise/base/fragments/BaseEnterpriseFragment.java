package com.autodesk.shejijia.enterprise.base.fragments;

import android.content.Context;
import com.autodesk.shejijia.enterprise.base.BaseView;
import com.autodesk.shejijia.enterprise.base.activitys.BaseEnterpriseActivity;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

/**
 * Created by t_xuz on 8/15/16.
 *
 */
public abstract class BaseEnterpriseFragment extends BaseLazyFragment implements BaseView{

    protected BaseEnterpriseActivity mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (BaseEnterpriseActivity) context;
    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

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
