package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * @User: 蜡笔小新
 * @date: 16-11-7
 * @GitHub: https://github.com/meikoz
 */

public class DetailsViewCategoryActivity extends NavigationBarActivity implements BaseCommonRvAdapter.OnItemClickListener {

    private List<RecommendSCFDBean> mRecommendSCFDList;
    private String mLocalClassName;

    public static void jumpTo(Activity context, String scfd, int position, String className) {
        Intent intent = new Intent(context, DetailsViewCategoryActivity.class);
        intent.putExtra(JsonConstants.SCFD, scfd);
        intent.putExtra(JsonConstants.LOCATION, position);
        intent.putExtra(JsonConstants.CLASSNAME, className);
        context.startActivityForResult(intent, 25);
    }

    private RecyclerView mRcv_category_view;
    private String mScfd;
    private int mPosition;
    private ViewCategoryAdater mAdater;

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
        mLocalClassName = getIntent().getStringExtra("className");
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
        Intent intent = new Intent(DetailsViewCategoryActivity.this,
                mLocalClassName.equals(JsonConstants.CLASSNAME) ?
                        DcRecommendDetailsActivity.class : CsRecommendDetailsActivity.class);
        intent.putExtra(JsonConstants.LOCATION, position);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
        return false;
    }
}
