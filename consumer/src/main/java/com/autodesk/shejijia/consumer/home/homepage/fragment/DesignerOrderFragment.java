package com.autodesk.shejijia.consumer.home.homepage.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.designer.fragment.OrderCommonFragment;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

/**
 * @author yaoxuehua .
 * @version v1.0 .
 * @date 16-7-19 .
 * @file DesignerOrderActivity.java .
 * @brief 普通设计师, 普通订单 .
 */
public class DesignerOrderFragment extends BaseFragment{
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_designer_order;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

        initFragmentManager();
    }

    private void initFragmentManager(){

        /**
         * 复用北舒设计师普通订单
         */
        Fragment newFragment = new OrderCommonFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_commonorder_container, newFragment);
        transaction.commit();
    }
}
