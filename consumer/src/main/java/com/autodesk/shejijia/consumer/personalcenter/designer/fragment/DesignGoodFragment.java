package com.autodesk.shejijia.consumer.personalcenter.designer.fragment;

import android.view.View;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.manager.constants.JsonConstants;
import com.autodesk.shejijia.consumer.personalcenter.designer.adapter.EliteAdapter;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.OrderCommonEntity;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.finalteam.loadingviewfinal.ListViewFinal;
import cn.finalteam.loadingviewfinal.OnDefaultRefreshListener;
import cn.finalteam.loadingviewfinal.OnLoadMoreListener;
import cn.finalteam.loadingviewfinal.PtrClassicFrameLayout;
import cn.finalteam.loadingviewfinal.PtrFrameLayout;

/**
 * Created by zjl on 16-9-6.
 */
public class DesignGoodFragment extends BaseFragment {

    private RelativeLayout mRlEmpty;
    private ListViewFinal mListView;
    private PtrClassicFrameLayout ptrLayoutElite;

    private HashMap<String, Object> map;
    private ArrayList<OrderCommonEntity.OrderListEntity> orders = new ArrayList<>();

    private int model;
    private int LIMIT = 10;
    private int OFFSET = 0;
    private EliteAdapter eliteAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_elite;
    }

    @Override
    protected void initView() {
        if (getArguments() != null)
            model = getArguments().getInt("model");

        mListView = (ListViewFinal) rootView.findViewById(R.id.lv_designer_elite);
        ptrLayoutElite = (PtrClassicFrameLayout) rootView.findViewById(R.id.ptr_layout_elite);
        mRlEmpty = (RelativeLayout) rootView.findViewById(R.id.rl_empty);
    }

    @Override
    public void onFragmentShown() {
        onLoad2Refresh2Api();
    }


    @Override
    protected void initData() {
        MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (mMemberEntity == null) {
            return;
        }

        String acs_member_id = mMemberEntity.getAcs_member_id();
        map = new HashMap<>();
        map.put(JsonConstants.JSON_DEMAND_LIST_OFFSET, OFFSET);
        map.put(JsonConstants.JSON_DEMAND_LIST_LIMIT, LIMIT);
        map.put(JsonConstants.JSON_MEASURE_FORM_DESIGNER_ID, acs_member_id);

        eliteAdapter = new EliteAdapter(getActivity(), orders, R.layout.item_lv_designer_slite_order, acs_member_id);
        mListView.setAdapter(eliteAdapter);
//        onLoad2Refresh2Api();
    }


    private void onLoad2Refresh2Api() {
        ptrLayoutElite.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                connectOrder2Api(map, 0, LIMIT);
            }
        });
        ptrLayoutElite.setLastUpdateTimeRelateObject(this);
        mListView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                connectOrder2Api(map, OFFSET, LIMIT);
            }
        });
        ptrLayoutElite.autoRefresh();
    }

    private void connectOrder2Api(HashMap<String, Object> map, final int offset, int limit) {
        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String orderInfo = GsonUtil.jsonToString(jsonObject);
                OrderCommonEntity entity = GsonUtil.jsonToBean(orderInfo, OrderCommonEntity.class);
                if (offset == 0)
                    orders.clear();

                OFFSET = offset + 10;
                orders.addAll(entity.getOrder_list());
                if (entity.getOrder_list().size() < LIMIT)
                    mListView.setHasLoadMore(false);
                else
                    mListView.setHasLoadMore(true);

                if (offset == 0)
                    ptrLayoutElite.onRefreshComplete();
                else
                    mListView.onLoadMoreComplete();

                mRlEmpty.setVisibility(entity == null || entity.getOrder_list().size() == 0 ? View.VISIBLE : View.GONE);

                eliteAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        };
        MPServerHttpManager.getInstance().getSliteOder(map, callback);
    }


}
