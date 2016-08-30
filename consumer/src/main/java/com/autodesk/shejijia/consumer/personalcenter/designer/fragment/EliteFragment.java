package com.autodesk.shejijia.consumer.personalcenter.designer.fragment;

import android.os.Message;
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
import com.autodesk.shejijia.shared.components.common.uielements.MyToast;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
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
 * 精选
 */
public class EliteFragment extends BaseFragment {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_elite;
    }

    @Override
    protected void initView() {
        mListView = (ListViewFinal) rootView.findViewById(R.id.lv_designer_elite);
        ptrLayoutElite = (PtrClassicFrameLayout) rootView.findViewById(R.id.ptr_layout_elite);
        mRlEmpty = (RelativeLayout) rootView.findViewById(R.id.rl_empty);
    }

    @Override
    protected void initData() {
        MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (mMemberEntity == null) {
            return;
        }
        designer_id = mMemberEntity.getAcs_member_id();
        map = new HashMap<>();
        map.put(JsonConstants.JSON_DEMAND_LIST_OFFSET,OFFSET);
        map.put(JsonConstants.JSON_DEMAND_LIST_LIMIT,LIMIT);
        map.put(JsonConstants.JSON_MEASURE_FORM_DESIGNER_ID,designer_id);

        eliteAdapter = new EliteAdapter(getActivity(), commonOrderListEntities, R.layout.item_lv_designer_slite_order,designer_id);
        mListView.setAdapter(eliteAdapter);
        setSwipeRefreshInfo();

    }
    /**
     * 获取数据，刷新页面
     */
    private void setSwipeRefreshInfo() {
        ptrLayoutElite.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getSliteOder(map,0, LIMIT);
            }
        });
        ptrLayoutElite.setLastUpdateTimeRelateObject(this);
        mListView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                getSliteOder(map, OFFSET, LIMIT);
            }
        });
        ptrLayoutElite.autoRefresh();
    }

    private void getSliteOder(HashMap<String,Object > map,final int offset, int limit){
        MPServerHttpManager.getInstance().getSliteOder(map,new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String userInfo = GsonUtil.jsonToString(jsonObject);
                mOrderCommonBean = GsonUtil.jsonToBean(userInfo, OrderCommonEntity.class);
                if (offset == 0) {
                    commonOrderListEntities.clear();
                }
                OFFSET = offset + 10;
                commonOrderListEntities.addAll(mOrderCommonBean.getOrder_list());
                if (mOrderCommonBean.getOrder_list().size() < LIMIT) {
                    mListView.setHasLoadMore(false);
                } else {
                    mListView.setHasLoadMore(true);
                }

                if (null == commonOrderListEntities || commonOrderListEntities.size() == 0) {
                    mRlEmpty.setVisibility(View.VISIBLE);
                }else{
                    mRlEmpty.setVisibility(View.GONE);
                }

                Message msg = Message.obtain();
                msg.obj = offset;
                handler.sendMessage(msg);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        });

    }
    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int offset = (int) msg.obj;
            if (offset == 0) {
                ptrLayoutElite.onRefreshComplete();
            } else {
                mListView.onLoadMoreComplete();
            }
            eliteAdapter.notifyDataSetChanged();
        }
    };

    private RelativeLayout mRlEmpty;
    private ListViewFinal mListView;
    private PtrClassicFrameLayout ptrLayoutElite;
    private String designer_id;
    private  OrderCommonEntity mOrderCommonBean;
    private EliteAdapter eliteAdapter;
    private ArrayList<OrderCommonEntity.OrderListEntity> commonOrderListEntities = new ArrayList<>();
    private HashMap<String,Object > map;
    private int LIMIT = 10;
    private int OFFSET = 0;
}
