package com.autodesk.shejijia.consumer.home.homepage.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.homepage.activity.MPConsumerHomeActivity;
import com.autodesk.shejijia.consumer.personalcenter.designer.fragment.DesignBaseFragment;
import com.autodesk.shejijia.consumer.personalcenter.designer.fragment.BidBidingFragment;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

/**
 * @author yaoxuehua .
 * @version 1.0 .
 * @date 16-7-13
 * @file MyDecorationProjectDesignerFragment.java  .
 * @brief 我的装修项目--设计师.
 */
public class MyDecorationProjectDesignerFragment extends BaseFragment {

    private BidBidingFragment mBidBidingFragment;
    private DesignBaseFragment designBaseFragment;
    private DesignerConstructionFragment mDesignerConstructionFragment;
    private Fragment fromFragment;

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
        int is_loho = ((MPConsumerHomeActivity) getActivity()).is_loho;
        setDesigneBaseFragment(high_level_audit, is_loho);
    }

    public void switchContent(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (fromFragment != fragment) {
            if (fromFragment != null) {
                transaction.hide(fromFragment);
            }

            if (!fragment.isAdded()) {
                transaction.add(R.id.ll_contain, fragment);
            } else {
                transaction.show(fragment);
            }
            transaction.commit();
            fromFragment = fragment;
        }
    }


    /**
     * 应标
     */
    public void setBidingFragment() {
        if (mBidBidingFragment == null) {
            mBidBidingFragment = new BidBidingFragment();
        }
        switchContent(mBidBidingFragment);
    }

    public void setDesigneBaseFragment(int high_level_audit, int is_loho) {
        if (designBaseFragment == null) {
            designBaseFragment = DesignBaseFragment.newInstance(high_level_audit, is_loho);
        }
        switchContent(designBaseFragment);
    }

    /**
     * 施工
     */
    public void setConstructionFragment() {
        if (mDesignerConstructionFragment == null) {
            mDesignerConstructionFragment = new DesignerConstructionFragment();
        }
        switchContent(mDesignerConstructionFragment);
    }


    @Override
    public void onFragmentShown() {
        Log.d(TAG, "onFragmentShown: onFragmentShown");
        designBaseFragment.onFragmentShown();
    }
}
