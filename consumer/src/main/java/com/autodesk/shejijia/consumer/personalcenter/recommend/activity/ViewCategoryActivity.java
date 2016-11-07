package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter.BaseCommonRvAdapter;
import com.autodesk.shejijia.consumer.personalcenter.recommend.adapter.ViewCategoryAdater;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @User: 蜡笔小新
 * @date: 16-11-7
 * @GitHub: https://github.com/meikoz
 */

public class ViewCategoryActivity extends NavigationBarActivity {

    public static void jumpTo(Context context) {
        Intent intent = new Intent(context, ViewCategoryActivity.class);
        context.startActivity(intent);
    }

    private RecyclerView mRcv_category_view;
    private List<String> categorys = new ArrayList<>();
    private ViewCategoryAdater mAdater;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_view_category;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        categorys.add("瓷砖");
        categorys.add("壁纸");
        categorys.add("石材");
        categorys.add("木郁闷");
        categorys.add("天梯");
        categorys.add("墙砖");
        categorys.add("木郁闷");
        categorys.add("天梯");
        categorys.add("墙砖");
        mAdater.notifyDataSetChanged();
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleForNavbar("查看品类");
        mRcv_category_view = (RecyclerView) findViewById(R.id.rcv_category_view);
        GridLayoutManager glm = new GridLayoutManager(this, 3);
        mAdater = new ViewCategoryAdater(this, R.layout.item_view_category, categorys);
        mRcv_category_view.setLayoutManager(glm);
        mRcv_category_view.setAdapter(mAdater);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdater.setOnItemClickListener(new BaseCommonRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                //TODO　点击定位功能

            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
    }
}
