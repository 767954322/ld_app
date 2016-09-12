package com.autodesk.shejijia.consumer.home.homepage.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.widget.widget.SwipeViewPager;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjl on 16-9-12.
 */
public class WelcomeActivity extends BaseActivity {

    private SwipeViewPager mWelcomeViewPager;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        // 全屏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = this.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mWelcomeViewPager = (SwipeViewPager) findViewById(R.id.welcome_view_pager);
        WelcomePagerAdapter adapter = new WelcomePagerAdapter(this, getAdData());
        //初始化轮播图下面小点
        mWelcomeViewPager.updateIndicatorView(getAdData().size());
        mWelcomeViewPager.setAdapter(adapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    public List<Integer> getAdData() {
        List<Integer> adList = new ArrayList<>();
        adList.add(R.drawable.bg_guide_page_1);
        adList.add(R.drawable.bg_guide_page_2);
        adList.add(R.drawable.bg_guide_page_3);
        return adList;
    }
}
