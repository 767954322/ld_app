package com.autodesk.shejijia.consumer.home.homepage.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

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
    private Fragment mContent;
    private DesignBaseFragment designBaseFragment;

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

    public void switchContent(Fragment to) {
        if (mContent != to) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            if (mContent == null) {
                transaction.add(R.id.ll_contain, to).commit();
            } else {
                if (!to.isAdded()) { // 先判断是否被add过
                    transaction.hide(mContent).add(R.id.ll_contain, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
                } else {
                    transaction.hide(mContent).show(to).commit(); // 隐藏当前的fragment，显示下一个
                }
            }
            mContent = to;
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
        if (designBaseFragment == null)
            designBaseFragment = DesignBaseFragment.newInstance(high_level_audit, is_loho);
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

    private Fragment fromFragment;
    private Fragment mDesignerConstructionFragment;
    private Fragment mBidBidingFragment;

}
