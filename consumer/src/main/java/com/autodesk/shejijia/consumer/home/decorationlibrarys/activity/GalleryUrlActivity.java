package com.autodesk.shejijia.consumer.home.decorationlibrarys.activity;

import android.os.Bundle;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.autodesk.shejijia.shared.components.common.uielements.gallerywidget.BasePagerAdapter;
import com.autodesk.shejijia.shared.components.common.uielements.gallerywidget.GalleryViewPager;
import com.autodesk.shejijia.shared.components.common.uielements.gallerywidget.UrlPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 2016/3/28 0028 11:22 .
 * @file GalleryUrlActivity  .
 * @brief 查看大图页面 .
 */
public class GalleryUrlActivity extends NavigationBarActivity implements BasePagerAdapter.OnItemClickListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_gallery;
    }

    @Override
    protected void initView() {
        super.initView();
        mViewPager = (GalleryViewPager) findViewById(R.id.viewer);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        String url = getIntent().getStringExtra(Constant.CaseLibraryDetail.CASE_URL);
        mUrlList.add(url);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mUrlPagerAdapter = new UrlPagerAdapter(this, mUrlList);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mUrlPagerAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mUrlPagerAdapter.setOnItemClickListener(this);
    }

    /**
     * 单击并结束当前activity
     *
     * @param currentPosition 点击的索引
     */
    @Override
    public void onItemClick(int currentPosition) {
        finish();
        overridePendingTransition(R.anim.zoom_exit, R.anim.zoom_enter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(R.anim.zoom_exit, R.anim.zoom_enter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUrlList = null;
    }

    /// 控件.
    private GalleryViewPager mViewPager;

    /// 集合,类.
    private List<String> mUrlList = new ArrayList<>();
    private UrlPagerAdapter mUrlPagerAdapter;
}