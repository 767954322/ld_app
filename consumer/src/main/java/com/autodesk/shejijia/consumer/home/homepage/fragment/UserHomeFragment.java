package com.autodesk.shejijia.consumer.home.homepage.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter.CaseViewPagerAdapter;
import com.autodesk.shejijia.consumer.home.homepage.activity.MPConsumerHomeActivity;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.matertab.MaterialTabs;
import com.autodesk.shejijia.shared.components.common.uielements.slippingviewpager.NoSlippingViewPager;
import com.autodesk.shejijia.shared.components.common.utility.DensityUtil;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import java.util.ArrayList;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 2015/12/13 0025 9:47 .
 * @file UserHomeFragment  .
 * @brief 首页案例fragment, 展示案例 .
 */
public class UserHomeFragment extends BaseFragment {

    private NoSlippingViewPager caseViewPager;
    private CaseViewPagerAdapter caseViewPagerAdapter;
    private ArrayList<Fragment> fragments;

    public static UserHomeFragment getInstance() {
        UserHomeFragment uhf = new UserHomeFragment();
        return uhf;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_case_home;
    }

    @Override
    protected void initView() {
        caseViewPager = (NoSlippingViewPager) rootView.findViewById(R.id.case_library_viewPager);

    }

    public void onResume() {
        super.onResume();
        if (CustomProgress.dialog != null && CustomProgress.dialog.isShowing()){
            CustomProgress.cancelDialog();
        }

    }

    @Override
    protected void initData() {
        String[] tabItems = this.getResources().getStringArray(R.array.caseLibraryTitle);
        fragments = new ArrayList<>();
        fragments.add(new UserHome2DFragment());
        fragments.add(new UserHome3DFragment());
        caseViewPagerAdapter = new CaseViewPagerAdapter(getChildFragmentManager(), fragments, tabItems);
        caseViewPager.setPagingEnabled(false);
        caseViewPager.setAdapter(caseViewPagerAdapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        caseViewPager.setPageMargin(pageMargin);
        MPConsumerHomeActivity mpConsumerHomeActivity = (MPConsumerHomeActivity) activity;
//
        MaterialTabs pagerSlidingTabStrip = mpConsumerHomeActivity.getMaterialTabs();
        pagerSlidingTabStrip.setIndicatorColor(Color.BLUE);//下滑指示器的颜色
        pagerSlidingTabStrip.setIndicatorHeight(DensityUtil.dip2px(mpConsumerHomeActivity, 2));//下滑指示器的高度
        pagerSlidingTabStrip.setTextColorSelected(Color.BLUE);//设置选中的tab字体颜色
        pagerSlidingTabStrip.setTextColorUnselected(Color.BLACK);//设置未选中的tab字体颜色
        pagerSlidingTabStrip.setTabPaddingLeftRight(40);//设置tab距离左右的padding值
        pagerSlidingTabStrip.setTabTypefaceSelectedStyle(Typeface.NORMAL);//选中时候字体
        pagerSlidingTabStrip.setTabTypefaceUnselectedStyle(Typeface.NORMAL);//未选中时候字体
        pagerSlidingTabStrip.setTextSize(DensityUtil.dip2px(mpConsumerHomeActivity, 16));
//
        pagerSlidingTabStrip.setViewPager(caseViewPager);
        caseViewPager.setCurrentItem(0);
    }

}