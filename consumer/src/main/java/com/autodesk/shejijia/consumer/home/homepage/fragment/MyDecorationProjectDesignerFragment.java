package com.autodesk.shejijia.consumer.home.homepage.fragment;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.homepage.activity.MPConsumerHomeActivity;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.DesignerInfoDetails;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

/**
 * @author yaoxuehua .
 * @version 1.0 .
 * @date 16-7-13
 * @file DesignerOrderBeiShuActivity.java  .
 * @brief 我的装修项目--设计师.
 */
public class MyDecorationProjectDesignerFragment extends BaseFragment{

    public static MyDecorationProjectDesignerFragment getInstance() {
        MyDecorationProjectDesignerFragment uhf = new MyDecorationProjectDesignerFragment();
        return uhf;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_my_decoration_project_designer;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {

        int is_loho = ((MPConsumerHomeActivity) getActivity()).is_loho;
        setDefaultFragment(is_loho);
    }
    /**
     * 设置应标的fragment
     * */
    public void setBidingFragment(){

            if (mBidBidingFragment == null) {

                mBidBidingFragment = new BidingFragment();
            }
        switchFragment(mBidBidingFragment);
    }
    /**
     * 设置设计的fragment
     * */
    public void setDesignBeiShuFragment(){

        if (mBeishuMealFragment == null  ){

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

    public void setDefaultFragment(int isLoho) {
        if (isLoho == IS_BEI_SHU){

            mCommonFragment = new DesignerOrderBeiShuFragment();
        }else {

            mCommonFragment = new DesignerOrderFragment();
        }
        fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.ll_contain, mCommonFragment)
                .commit();
        fromFragment = mCommonFragment;
    }

    private LinearLayout llFragmentContain;

    private TextView mBeishuOrder, mOrder;
    private Context context = getActivity();
    private FrameLayout mOrderContainer;
    private int mIsLoho;

    private static final int IS_BEI_SHU = 1;
    private Fragment mBeishuMealFragment, mCommonOrderFragment,mCommonFragment;
    private FragmentManager fragmentManager;
    private Fragment fromFragment;
    private Fragment mDesignerConstructionFragment;
    private BidingFragment mBidBidingFragment;

}
