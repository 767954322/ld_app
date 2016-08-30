package com.autodesk.shejijia.consumer.home.homepage.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.codecorationBase.studio.fragment.fragment.StudioFragment;
import com.autodesk.shejijia.consumer.personalcenter.consumer.activity.IssueDemandActivity;
import com.autodesk.shejijia.consumer.codecorationBase.coelite.adapter.SixProductsAdapter;
import com.autodesk.shejijia.consumer.codecorationBase.average.fragment.AverageFragment;
import com.autodesk.shejijia.consumer.codecorationBase.codiy.fragments.DIYFragment;
import com.autodesk.shejijia.consumer.codecorationBase.grandmaster.fragment.GrandMasterFragment;
import com.autodesk.shejijia.consumer.codecorationBase.packages.fragment.PackagesFragment;
import com.autodesk.shejijia.consumer.codecorationBase.coelite.fragment.CoEliteFragment;
import com.autodesk.shejijia.shared.components.common.uielements.matertab.MaterialTabs;
import com.autodesk.shejijia.shared.components.common.uielements.slippingviewpager.NoSlippingViewPager;
import com.autodesk.shejijia.shared.components.common.utility.DensityUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import java.util.ArrayList;

public class SixProductsActivity extends NavigationBarActivity {


    private SixProductsAdapter sixProductsAdapter;
    private NoSlippingViewPager noSlippingViewPager;
    private ArrayList<Fragment> fragments;
    private MaterialTabs pagerSlidingTabStrip;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_six_products;
    }
    @Override
    protected void initView() {
        pagerSlidingTabStrip = (MaterialTabs) findViewById(R.id.consumer_six_products_tabs);
        noSlippingViewPager = (NoSlippingViewPager)findViewById(R.id.consumer_six_products_viewPager);
    }
    @Override
    protected void initData(Bundle savedInstanceState) {
        setTitleForNavbar(UIUtils.getString(R.string.tab_six_products));
        noSlippingViewPager.setPagingEnabled(false);
        fragments = new ArrayList<>();
        fragments.add(new GrandMasterFragment());
        fragments.add(new StudioFragment());
        fragments.add(new CoEliteFragment());
        fragments.add(new AverageFragment());
        fragments.add(new PackagesFragment());
        fragments.add(new DIYFragment());

        String[] tabItems =this.getResources().getStringArray(R.array.sixProducts);

        sixProductsAdapter = new SixProductsAdapter(getSupportFragmentManager(),fragments,tabItems);
        noSlippingViewPager.setAdapter(sixProductsAdapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        noSlippingViewPager.setPageMargin(pageMargin);


        pagerSlidingTabStrip.setBackgroundColor(Color.WHITE);//Tab的背景色
        pagerSlidingTabStrip.setIndicatorColor(Color.BLUE);//下滑指示器的颜色
        pagerSlidingTabStrip.setIndicatorHeight(DensityUtil.dip2px(this, 2));//下滑指示器的高度
        pagerSlidingTabStrip.setTextColorSelected(Color.BLUE);//设置选中的tab字体颜色
        pagerSlidingTabStrip.setTextColorUnselected(Color.BLACK);//设置未选中的tab字体颜色
        pagerSlidingTabStrip.setTabPaddingLeftRight(60);//设置tab距离左右的padding值
        pagerSlidingTabStrip.setTabTypefaceSelectedStyle(Typeface.NORMAL);//选中时候字体
        pagerSlidingTabStrip.setTabTypefaceUnselectedStyle(Typeface.NORMAL);//未选中时候字体
        pagerSlidingTabStrip.setTextSize(DensityUtil.dip2px(this, 16));

        pagerSlidingTabStrip.setViewPager(noSlippingViewPager);
        noSlippingViewPager.setCurrentItem(2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == IssueDemandActivity.RESULT_CODE){
            setResult(IssueDemandActivity.RESULT_CODE, data);
            finish();
        }
    }
}
