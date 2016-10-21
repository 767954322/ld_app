package com.autodesk.shejijia.consumer.personalcenter.recommend.fragment;

import android.os.Bundle;
import android.os.Handler;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.recommend.adapter.RecommendAdapter;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.finalteam.loadingviewfinal.ListViewFinal;
import cn.finalteam.loadingviewfinal.OnDefaultRefreshListener;
import cn.finalteam.loadingviewfinal.OnLoadMoreListener;
import cn.finalteam.loadingviewfinal.PtrClassicFrameLayout;
import cn.finalteam.loadingviewfinal.PtrFrameLayout;

/**
 * @User: 蜡笔小新
 * @date: 16-10-21
 * @GitHub: https://github.com/meikoz
 */

public class RecommendFragment extends BaseFragment implements OnLoadMoreListener {

    public static RecommendFragment newInstance(int type) {
        RecommendFragment fragment = new RecommendFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    private List<String> mRecommends = new ArrayList<>();
    private ListViewFinal mListView;
    private RecommendAdapter mAdapter;
    private PtrClassicFrameLayout mFrameLayout;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_recommend_list;
    }

    @Override
    protected void initListener() {
        super.initListener();
        mListView.setOnLoadMoreListener(this);
        mFrameLayout.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mFrameLayout.onRefreshComplete();
                    }
                }, 2000);
            }
        });
    }

    @Override
    protected void initView() {
        int type = getArguments().getInt("type");
        mListView = (ListViewFinal) rootView.findViewById(R.id.lv_recommend);
        mFrameLayout = (PtrClassicFrameLayout) rootView.findViewById(R.id.ptr_layout);
        mAdapter = new RecommendAdapter(getActivity(), mRecommends, R.layout.item_recommend, type == 1);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        mRecommends.addAll(Arrays.asList("西山一号", "国际大厦", "紫光发展大厦"));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mListView.onLoadMoreComplete();
            }
        }, 2000);
    }

}
