package com.autodesk.shejijia.consumer.personalcenter.recommend.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.homepage.activity.DesignerPersonalCenterActivity;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.BeiShuMealEntity;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.DesignerInfoDetails;
import com.autodesk.shejijia.consumer.personalcenter.recommend.adapter.RecommendAdapter;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendEntity;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import org.json.JSONObject;

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

public class RecommendFragment extends CustomBaseFragment implements OnLoadMoreListener, AdapterView.OnItemClickListener {

    public static RecommendFragment newInstance(int type, int status) {
        RecommendFragment fragment = new RecommendFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("status", status);
        fragment.setArguments(bundle);
        return fragment;
    }

    private List<RecommendEntity.ItemsBean> mRecommends = new ArrayList<>();
    private ListViewFinal mListView;
    private RecommendAdapter mAdapter;
    private PtrClassicFrameLayout mFrameLayout;
    private int mStatus;
    private static int OFFSET = 0;
    private static final int LIMIT = 10;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_recommend_list;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    protected void initListener() {
        super.initListener();
        mListView.setOnLoadMoreListener(this);
        mListView.setOnItemClickListener(this);
        mFrameLayout.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getRecommendList(0, LIMIT, mStatus);
                mFrameLayout.onRefreshComplete();
            }
        });
    }

    @Override
    protected void initView() {
        int type = getArguments().getInt("type");
        mStatus = getArguments().getInt("status");
        mListView = (ListViewFinal) rootView.findViewById(R.id.lv_recommend);
        mFrameLayout = (PtrClassicFrameLayout) rootView.findViewById(R.id.ptr_layout);
        mAdapter = new RecommendAdapter(getActivity(), mRecommends, R.layout.item_recommend, type == 1);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void loadMore() {
        OFFSET += mRecommends.size();
        getRecommendList(OFFSET, LIMIT, mStatus);
    }

    public void getRecommendList(int offset, int limit, int status) {
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (memberEntity == null) {
            return;
        }
        String member_id = memberEntity.getAcs_member_id();
        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonString = GsonUtil.jsonToString(jsonObject);
                Log.d("RecommendFragment", jsonString);
                RecommendEntity entity = GsonUtil.jsonToBean(jsonString, RecommendEntity.class);
                updateViewFromApi(entity.getItems());
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
            }
        };
        // 20730531 测试id after reset member_id
        MPServerHttpManager.getInstance().getRecommendList("20730531", offset, limit, status, callback);
    }

    private void updateViewFromApi(List<RecommendEntity.ItemsBean> items) {
        if (items != null && items.size() > 0) {
            if (OFFSET == 0) {
                mRecommends.clear();
                mFrameLayout.onRefreshComplete();
            } else
                mListView.onLoadMoreComplete();
            mRecommends.addAll(items);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void loadData2Remote() {
        getRecommendList(0, LIMIT, mStatus);
    }
}
