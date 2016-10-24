package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.recommend.adapter.FragmentTabAdapter;
import com.autodesk.shejijia.consumer.personalcenter.recommend.fragment.RecommendFragment;
import com.autodesk.shejijia.consumer.utils.ToastUtil;
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

public class MyRecommendActivity extends NavigationBarActivity implements View.OnClickListener {

    public static int CONSUMER = 0;
    public static int DESIGNER = 1;
    private TextView mRightTextView;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> tabs = Arrays.asList("全部", "已发送", "未发送");
    private int mType;

    public static void jumpTo(Context context, int type) {
        Intent intent = new Intent(context, MyRecommendActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_recommend;
    }

    @Override
    protected void initView() {
        super.initView();
        mType = getIntent().getIntExtra("type", 0);
        setTitleBarView();
        mTabLayout = (TabLayout) findViewById(R.id.tab_recommend_title);
        mViewPager = (ViewPager) findViewById(R.id.vpr_recommend_view);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        for (int i = 0; i < 3; i++) {
            mFragments.add(RecommendFragment.newInstance(mType, i));
        }
        FragmentTabAdapter adapter = new FragmentTabAdapter(getSupportFragmentManager(), mFragments, tabs);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setTitleBarView() {
        mRightTextView = (TextView) findViewById(R.id.nav_right_textView);
        //区分消费者和设计师
        mRightTextView.setVisibility(mType == 0 ? View.INVISIBLE : View.VISIBLE);
        setTitleForNavbar(UIUtils.getString(mType == 0 ? R.string.recommend_listing : R.string.personal_recommend));
        mRightTextView.setTextColor(UIUtils.getColor(R.color.color_blue_0084ff));
        mRightTextView.setText("新建清单");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

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
                Intent intent = new Intent(MyRecommendActivity.this,NewInventoryActivity.class);
                startActivity(intent);
                break;
        }
    }
}
