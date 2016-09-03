package com.autodesk.shejijia.consumer.home.homepage.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.homepage.activity.MPConsumerHomeActivity;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

/**
 * @author yaoxuehua .
 * @version 1.0 .
 * @date 16-7-13
 * @file MyDecorationProjectDesignerFragment.java  .
 * @brief 我的装修项目--设计师.
 */
public class MyDecorationProjectDesignerFragment extends BaseFragment{

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_my_decoration_project_designer;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {

        int high_level_audit = ((MPConsumerHomeActivity) getActivity()).high_level_audit;
        setDefaultFragment(high_level_audit);
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

    /**
     *  默认北舒套餐页面 .
     */
    public void setDefaultFragment(int high_level_audit) {
    //	MERGER MASTER
        // if (isLoho == IS_BEI_SHU){

        if (high_level_audit == 2){

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
    private Fragment mBidBidingFragment;

}
