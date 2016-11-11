package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.recommend.adapter.RecommendAdapter;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendDetailsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RefreshEvent;
import com.autodesk.shejijia.consumer.personalcenter.recommend.view.RecommendView;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.loadingviewfinal.ListViewFinal;
import cn.finalteam.loadingviewfinal.OnDefaultRefreshListener;
import cn.finalteam.loadingviewfinal.OnLoadMoreListener;
import cn.finalteam.loadingviewfinal.PtrClassicFrameLayout;
import cn.finalteam.loadingviewfinal.PtrFrameLayout;
import de.greenrobot.event.EventBus;

/**
 * @User: 蜡笔小新
 * @date: 16-10-21
 * @GitHub: https://github.com/meikoz
 */

public class CsRecommendActivity extends NavigationBarActivity implements RecommendView, OnLoadMoreListener, AdapterView.OnItemClickListener, RecommendAdapter.OnRevokeCallback {

    private LinearLayout mEmptyView;
    private RecommendLogicImpl mRecommendLogic;

    public static void jumpTo(Context context, int status) {
        Intent intent = new Intent(context, CsRecommendActivity.class);
        context.startActivity(intent);
    }

    private List<RecommendDetailsBean> mRecommends = new ArrayList<>();
    private ListViewFinal mListView;
    private RecommendAdapter mAdapter;
    private PtrClassicFrameLayout mFrameLayout;
    private static int OFFSET = 0;
    private static final int LIMIT = 10;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_recommend_list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RecommendDetailsBean item = (RecommendDetailsBean) parent.getAdapter().getItem(position);
        CsRecommendDetailsActivity.jumpTo(this, item.getAsset_id() + "", item.getCommunity_name());
    }

    @Override
    protected void initListener() {
        super.initListener();
        mListView.setOnLoadMoreListener(this);
        mListView.setOnItemClickListener(this);
        mAdapter.setOnRevokeCallback(this);
        mFrameLayout.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                OFFSET = 0;
                mRecommendLogic.onLoadRecommendListData(false, 0, LIMIT, 0);
                mFrameLayout.onRefreshComplete();
            }
        });
    }

    @Override
    protected void initView() {
        setTitleForNavbar(UIUtils.getString(R.string.recommend_listing));
        mListView = (ListViewFinal) findViewById(R.id.lv_recommend);
        mFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.ptr_layout);
        mEmptyView = (LinearLayout) findViewById(R.id.empty_view);
        mAdapter = new RecommendAdapter(this, mRecommends, R.layout.item_recommend, false);
        mListView.setAdapter(mAdapter);
        mRecommendLogic = new RecommendLogicImpl(this);
        mRecommendLogic.onLoadRecommendListData(false, 0, LIMIT, 0);
        CustomProgress.show(this, "", false, null);
    }

    @Override
    public void loadMore() {
        OFFSET += mRecommends.size();
        mRecommendLogic.onLoadRecommendListData(false, OFFSET, LIMIT, 0);
    }

    private void updateViewFromApi(int offset, List<RecommendDetailsBean> items) {
        if (items.size() < LIMIT) {
            mListView.setHasLoadMore(false);
        } else {
            mListView.setHasLoadMore(true);
        }
        if (items != null && items.size() > 0) {
            if (offset == 0) {
                mRecommends.clear();
                mFrameLayout.onRefreshComplete();
            } else
                mListView.onLoadMoreComplete();
            mEmptyView.setVisibility(View.GONE);
            mRecommends.addAll(items);
            mAdapter.notifyDataSetChanged();
        } else {
            if (offset == 0)
                mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoadDataSuccess(int offset, RecommendBean entity) {
        CustomProgress.cancelDialog();
        updateViewFromApi(offset, entity.getItems());
    }

    @Override
    public void onLoadFailer() {
        CustomProgress.cancelDialog();
        mFrameLayout.onRefreshComplete();
        mListView.onLoadMoreComplete();
    }

    @Override
    public void onRevokeSuccessFul() {
        mRecommendLogic.onLoadRecommendListData(false, 0, LIMIT, 0);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRevokeFailer() {
        Toast.makeText(this, "退回失败", Toast.LENGTH_SHORT).show();
    }


    /**
     * 用于刷新
     *
     * @param event
     */
    public void onEventMainThread(RefreshEvent event) {
        mListView.clearFocus();
        mListView.setSelection(0);
        mRecommendLogic.onLoadRecommendListData(false, 0, LIMIT, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
