package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter.BaseCommonRvAdapter;
import com.autodesk.shejijia.consumer.manager.constants.JsonConstants;
import com.autodesk.shejijia.consumer.personalcenter.recommend.adapter.ViewCategoryAdater;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendBrandsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendSCFDBean;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import java.io.Serializable;
import java.util.List;

/**
 * @User: 蜡笔小新
 * @date: 16-11-7
 * @GitHub: https://github.com/meikoz
 */

public class ViewCategoryActivity extends NavigationBarActivity implements BaseCommonRvAdapter.OnItemClickListener {

    private List<RecommendSCFDBean> mRecommendSCFDList;

    public static void jumpTo(Activity context, List<RecommendSCFDBean> SCFDList, int position) {
        Intent intent = new Intent(context, ViewCategoryActivity.class);
        intent.putExtra(JsonConstants.RECOMMENDBRANDSCFDBEAN, (Serializable)SCFDList);
        intent.putExtra(JsonConstants.LOCATION, position);
        context.startActivityForResult(intent, 23);
    }

    private RecyclerView mRcv_category_view;
    private int mPosition;
    private ViewCategoryAdater mAdater;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_view_category;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mAdater = new ViewCategoryAdater(this, R.layout.item_view_category, mRecommendSCFDList, mPosition);
        mRcv_category_view.setAdapter(mAdater);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        Intent intent = getIntent();
        mRecommendSCFDList = (List<RecommendSCFDBean>) intent.getSerializableExtra(JsonConstants.RECOMMENDBRANDSCFDBEAN);
        mPosition = intent.getIntExtra(JsonConstants.LOCATION, 0);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleForNavbar(UIUtils.getString(R.string.view_category));
        mRcv_category_view = (RecyclerView) findViewById(R.id.rcv_category_view);
        GridLayoutManager glm = new GridLayoutManager(this, 3);
        mRcv_category_view.setLayoutManager(glm);
    }

    @Override
    protected void initListener() {
        super.initListener();
        if (mAdater != null)
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
        intent.putExtra(JsonConstants.LOCATION, position == 0 ? position : selection);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
        return false;
    }
}
