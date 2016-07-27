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
import com.autodesk.shejijia.consumer.personalcenter.consumer.fragment.DecorationBeiShuFragment;
import com.autodesk.shejijia.consumer.personalcenter.designer.fragment.BidBidingFragment;
import com.autodesk.shejijia.consumer.personalcenter.designer.fragment.OrderBeiShuFragment;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

/**
 * @author yaoxuehua .
 * @version 1.0 .
 * @date 16-7-13
 * @file DesignerOrderBeiShuActivity.java  .
 * @brief 我的装修项目--设计师.
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
     * 设置应标的fragment
     * */
    public void setBidingFragment(){

            if (mBidBidingFragment == null){

                mBidBidingFragment = new BidingFragment();
            }

        switchFragment(mBidBidingFragment);
    }
    /**
     * 设置设计的fragment
     * */
    public void setDesignBeiShuFragment(){

        if (mBeishuMealFragment == null){

            mBeishuMealFragment = new DesignerOrderBeiShuFragment();

        }

        switchFragment(mBeishuMealFragment);
    }

    /**
     * 设置设计的fragment
     * */
    public void setDesignFragment(){

        if (mCommonOrderFragment == null){

            mCommonOrderFragment = new DesignerOrderFragment();

        }

        switchFragment(mCommonOrderFragment);
    }

    /**
     * 设置施工的fragment
     * */
    public void setConstructionFragment(){

        if (mDesignerConstructionFragment == null){

            mDesignerConstructionFragment = new DesignerConstructionFragment();

        }

        switchFragment(mDesignerConstructionFragment);
    }


    /**
     * 切换fragment
     *
     * @param
     * @param to
     */
    private void switchFragment(Fragment to) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!to.isAdded()) {
            transaction.hide(fromFragment).add(R.id.ll_contain, to).commit();
        } else {
            transaction.hide(fromFragment).show(to).commit();
        }
        fromFragment = to;
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
        fromFragment = mBeishuMealFragment;
    }

    private LinearLayout llFragmentContain;

    private TextView mBeishuOrder, mOrder;
    private Context context = getActivity();
    private FrameLayout mOrderContainer;
    private Fragment mBeishuMealFragment, mCommonOrderFragment;
    private FragmentManager fragmentManager;
    private Fragment fromFragment;
    private FragmentTransaction transaction;
    private GradientDrawable drawable;/// set Textview bordercolor .
    private Fragment mDesignerConstructionFragment;
    private Fragment mBidBidingFragment;

}
