package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter.BaseCommonRvAdapter;
import com.autodesk.shejijia.consumer.personalcenter.recommend.adapter.ViewCategoryAdater;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendBrandsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendSCFDBean;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * @User: 蜡笔小新
 * @date: 16-11-7
 * @GitHub: https://github.com/meikoz
 */

public class ViewCategoryActivity extends NavigationBarActivity implements BaseCommonRvAdapter.OnItemClickListener {

    private List<RecommendSCFDBean> mRecommendSCFDList;

    public static void jumpTo(Activity context, String scfd, int position) {
        Intent intent = new Intent(context, ViewCategoryActivity.class);
        intent.putExtra("scfd", scfd);
        intent.putExtra("position", position);
        context.startActivityForResult(intent, 0);
    }

    private RecyclerView mRcv_category_view;
    private String mScfd;
    private int mPosition;
    private ViewCategoryAdater mAdater;
    public static String LOCATION;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_view_category;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (!TextUtils.isEmpty(mScfd)) {
            mRecommendSCFDList = new Gson()
                    .fromJson(mScfd, new TypeToken<List<RecommendSCFDBean>>() {
                    }.getType());
            if (mRecommendSCFDList != null && mRecommendSCFDList.size() > 0) {
                mAdater = new ViewCategoryAdater(this, R.layout.item_view_category, mRecommendSCFDList, mPosition);
                mRcv_category_view.setAdapter(mAdater);
            }
        }
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        mScfd = getIntent().getStringExtra("scfd");
        mPosition = getIntent().getIntExtra("position", 0);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleForNavbar("查看品类");
        mRcv_category_view = (RecyclerView) findViewById(R.id.rcv_category_view);
        GridLayoutManager glm = new GridLayoutManager(this, 3);
        mRcv_category_view.setLayoutManager(glm);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdater.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(ViewGroup parent, View view, Object o, int position) {
        int selection = 0;
        for (int i = 0; i < position; i++) {
            List<RecommendBrandsBean> brands = mRecommendSCFDList.get(i).getBrands();
            selection += brands.size() + 1;
        }
        Intent intent = new Intent(ViewCategoryActivity.this, RecommendListDetailActivity.class);
        intent.putExtra(LOCATION, position == 0 ? position : selection);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
        return false;
    }
}
