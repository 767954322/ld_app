package com.autodesk.shejijia.consumer.home.homepage.fragment;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.designer.fragment.OrderBeiShuFragment;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

/**
 * Created by yaoxuehua on 16-7-13.
 */
public class MyDecorationProjectDesignerFragment extends BaseFragment{


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_my_decoration_project_designer;
    }

    @Override
    protected void initView() {

        llFragmentContain = (LinearLayout) rootView.findViewById(R.id.ll_contain);
        setDefaultFragment();

    }

    @Override
    protected void initData() {

    }


    /**
     * @brief 默认北舒套餐页面 .
     */
    public void setDefaultFragment() {
        mBeishuMealFragment = new DesignerOrderBeiShuFragment();
        fragmentManager = getChildFragmentManager();
        /*  fragmentManager.beginTransaction().add(R.id.fl_designer_order_beishu_container, mBeishuMealFragment)
                .commit();*/
        fragmentManager.beginTransaction().replace(R.id.ll_contain, mBeishuMealFragment)
                .commit();
    }

    private LinearLayout llFragmentContain;

    private TextView mBeishuOrder, mOrder;
    private Context context = getActivity();
    private FrameLayout mOrderContainer;
    private Fragment mBeishuMealFragment, mCommonOrderFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private GradientDrawable drawable;/// set Textview bordercolor .
}
