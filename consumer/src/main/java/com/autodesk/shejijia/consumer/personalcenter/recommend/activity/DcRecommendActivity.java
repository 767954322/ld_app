package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.recommend.adapter.FragmentTabAdapter;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.fragment.RecommendFragment;
import com.autodesk.shejijia.consumer.personalcenter.recommend.view.RecommendView;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @User: 蜡笔小新
 * @date: 16-10-21
 * @GitHub: https://github.com/meikoz
 */

public class DcRecommendActivity extends NavigationBarActivity implements RecommendView, View.OnClickListener {

    private TextView mRightTextView;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> tabs = Arrays.asList("全部", "已发送", "未发送");
    private boolean isDesign;
    private LinearLayout mEmptyView;
    private RelativeLayout mTabVisibility;

    public static void jumpTo(Context context, boolean isDesign) {
        Intent intent = new Intent(context, DcRecommendActivity.class);
        intent.putExtra("isDesign", isDesign);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_recommend;
    }

    @Override
    protected void initView() {
        super.initView();
        isDesign = getIntent().getBooleanExtra("isDesign", false);
        setTitleBarView();
        mTabLayout = (TabLayout) findViewById(R.id.tab_recommend_title);
        mViewPager = (ViewPager) findViewById(R.id.vpr_recommend_view);
        mEmptyView = (LinearLayout) findViewById(R.id.empty_view);
        mTabVisibility = (RelativeLayout) findViewById(R.id.rlt_tab_view);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    private void setTitleBarView() {
        mRightTextView = (TextView) findViewById(R.id.nav_right_textView);
        //区分消费者和设计师
        mRightTextView.setVisibility(isDesign ? View.VISIBLE : View.INVISIBLE);
        setTitleForNavbar(UIUtils.getString(isDesign ? R.string.personal_recommend : R.string.recommend_listing));
        mRightTextView.setTextColor(UIUtils.getColor(R.color.color_blue_0084ff));
        mRightTextView.setText("新建清单");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        CustomProgress.show(this, "", false, null);
        RecommendLogicImpl recommendLogic = new RecommendLogicImpl(this);
        recommendLogic.onLoadRecommendListData(isDesign, 0, 20, 0);
        for (int i = 0; i < 3; i++) {
            mFragments.add(RecommendFragment.newInstance(isDesign, i));
        }
        FragmentTabAdapter adapter = new FragmentTabAdapter(getSupportFragmentManager(), mFragments, tabs);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mRightTextView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_right_textView:
                Intent intent = new Intent(DcRecommendActivity.this, NewInventoryActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onLoadDataSuccess(int offset, RecommendBean entity) {
        CustomProgress.cancelDialog();
        if (entity.getItems() != null && entity.getItems().size() > 0) {
            mEmptyView.setVisibility(View.GONE);
            mTabVisibility.setVisibility(View.VISIBLE);
            mTabVisibility.setBackgroundColor(UIUtils.getColor(R.color.white));
        } else {
            mTabVisibility.setVisibility(View.INVISIBLE);
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoadFailer() {
        CustomProgress.cancelDialog();
    }


}
