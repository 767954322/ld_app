package com.autodesk.shejijia.consumer.personalcenter.recommend.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.recommend.activity.DcRecommendDetailsActivity;
import com.autodesk.shejijia.consumer.personalcenter.recommend.activity.RecommendLogicImpl;
import com.autodesk.shejijia.consumer.personalcenter.recommend.adapter.RecommendAdapter;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendDetailsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RefreshEvent;
import com.autodesk.shejijia.consumer.personalcenter.recommend.view.RecommendView;

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

public class RecommendFragment extends CustomBaseFragment implements RecommendView, OnLoadMoreListener, AdapterView.OnItemClickListener, RecommendAdapter.OnRevokeCallback {

    private LinearLayout mEmptyView;
    private RecommendLogicImpl mRecommendLogic;

    public static RecommendFragment newInstance(boolean isDesign, int status) {
        RecommendFragment fragment = new RecommendFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isDesign", isDesign);
        bundle.putInt("status", status);
        fragment.setArguments(bundle);
        return fragment;
    }

    private List<RecommendDetailsBean> mRecommends = new ArrayList<>();
    private ListViewFinal mListView;
    private RecommendAdapter mAdapter;
    private PtrClassicFrameLayout mFrameLayout;
    private int mStatus;
    private boolean isDesign;
    private static int OFFSET = 0;
    private static final int LIMIT = 10;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_recommend_list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RecommendDetailsBean item = (RecommendDetailsBean) parent.getAdapter().getItem(position);
        if (item != null)
            DcRecommendDetailsActivity.jumpTo(getActivity(), item.getAsset_id() + "", "canceled".equals(item.getStatus()) || "refused".equals(item.getStatus()));
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
                mRecommendLogic.onLoadRecommendListData(true, 0, LIMIT, mStatus);
                mFrameLayout.onRefreshComplete();
            }
        });
    }

    @Override
    protected void initView() {
        mListView = (ListViewFinal) rootView.findViewById(R.id.lv_recommend);
        mFrameLayout = (PtrClassicFrameLayout) rootView.findViewById(R.id.ptr_layout);
        mEmptyView = (LinearLayout) rootView.findViewById(R.id.empty_view);
        mAdapter = new RecommendAdapter(getActivity(), mRecommends, R.layout.item_recommend, true);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
    }

    @Override
    public void loadMore() {
        OFFSET = mRecommends.size();
        mRecommendLogic.onLoadRecommendListData(true, OFFSET, LIMIT, mStatus);
    }

    private void updateViewFromApi(int offset, List<RecommendDetailsBean> items) {
        if (items.size() < LIMIT) {
            mListView.setHasLoadMore(false);
        } else {
            mListView.setHasLoadMore(true);
            mListView.setNoLoadMoreHideView(true);
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
            mListView.onLoadMoreComplete();
            if (offset == 0)
                mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void loadData2Remote() {
        isDesign = getArguments().getBoolean("isDesign", false);
        mStatus = getArguments().getInt("status");
        mRecommendLogic = new RecommendLogicImpl(this);
        mRecommendLogic.onLoadRecommendListData(true, 0, LIMIT, mStatus);
    }

    @Override
    public void onLoadDataSuccess(int offset, RecommendBean entity) {
        updateViewFromApi(offset, entity.getItems());
    }

    @Override
    public void onLoadFailed() {
        mFrameLayout.onRefreshComplete();
        mListView.onLoadMoreComplete();
    }

    @Override
    public void onRevokeSuccessFul() {
        mRecommendLogic.onLoadRecommendListData(isDesign, 0, LIMIT, mStatus);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRevokeFailer() {
        Toast.makeText(activity, "删除失败", Toast.LENGTH_SHORT).show();
    }

    /**
     * 用于刷新
     *
     * @param event
     */
    public void onEventMainThread(RefreshEvent event) {
        if (mRecommends.size() >= 10) {
            OFFSET = mRecommends.size();
        }
        if (mRecommendLogic != null)
            mRecommendLogic.onLoadRecommendListData(true, 0, OFFSET, mStatus);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

}
