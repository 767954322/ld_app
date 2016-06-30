package com.autodesk.shejijia.consumer.personalcenter.designer.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.designer.fragment.OrderCommonFragment;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-6-7 .
 * @file DesignerOrderActivity.java .
 * @brief 普通设计师, 普通订单 .
 */
public class DesignerOrderActivity extends NavigationBarActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_designer_order;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(UIUtils.getString(R.string.decoration_order));
        initFragmentManager();
    }

    private void initFragmentManager(){

        /**
         * 复用北舒设计师普通订单
         */
        Fragment newFragment = new OrderCommonFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_commonorder_container, newFragment);
        transaction.commit();
    }
}
