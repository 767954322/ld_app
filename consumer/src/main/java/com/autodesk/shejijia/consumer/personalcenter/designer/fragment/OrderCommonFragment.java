package com.autodesk.shejijia.consumer.personalcenter.designer.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.bidhall.activity.BiddingHallDetailActivity;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.manager.MPWkFlowManager;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.OrderCommonEntity;
import com.autodesk.shejijia.consumer.personalcenter.workflow.activity.WkFlowStateActivity;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.ConvertUtils;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.adapter.CommonAdapter;
import com.autodesk.shejijia.shared.framework.adapter.CommonViewHolder;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.finalteam.loadingviewfinal.ListViewFinal;
import cn.finalteam.loadingviewfinal.OnDefaultRefreshListener;
import cn.finalteam.loadingviewfinal.OnLoadMoreListener;
import cn.finalteam.loadingviewfinal.PtrClassicFrameLayout;
import cn.finalteam.loadingviewfinal.PtrFrameLayout;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-6-7 .
 * @file OrderCommonFragment.java .
 * @brief 我的订单:普通订单页面 .
 */
public class OrderCommonFragment extends BaseFragment {

    private String mMemberType;

    public OrderCommonFragment() {
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_common_meal;
    }

    @Override
    protected void initView() {
        mListView = (ListViewFinal) rootView.findViewById(R.id.lv_designer_common_order);
        mPtrLayout = (PtrClassicFrameLayout) rootView.findViewById(R.id.ptr_layout);
        mRlEmpty = (RelativeLayout) rootView.findViewById(R.id.rl_empty);
    }

    @Override
    protected void initData() {
        MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (mMemberEntity == null) {
            return;
        }
        designer_id = mMemberEntity.getAcs_member_id();
        mMemberType = mMemberEntity.getMember_type();

        mCommonOrderAdapter = new MyCommonOrderAdapter(UIUtils.getContext(), commonOrderListEntities, R.layout.item_lv_designer_common_order);
        mListView.setAdapter(mCommonOrderAdapter);
        setSwipeRefreshInfo();
    }

    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int offset = (int) msg.obj;
            if (offset == 0) {
                mPtrLayout.onRefreshComplete();
            } else {
                mListView.onLoadMoreComplete();
            }
            mCommonOrderAdapter.notifyDataSetChanged();
        }
    };

    /**
     * 获取普通订单数据
     *
     * @param Member_Type
     * @param designer_id
     * @param limit
     */
    public void getDesignerOder(String Member_Type,
                                String designer_id, final int offset, int limit) {
        MPServerHttpManager.getInstance().getDesignerOrder(Member_Type, designer_id, offset, limit, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String userInfo = GsonUtil.jsonToString(jsonObject);
                mOrderCommonEntity = GsonUtil.jsonToBean(userInfo, OrderCommonEntity.class);
                KLog.json(TAG, userInfo);
                if (offset == 0) {
                    commonOrderListEntities.clear();
                }
                OFFSET = offset + 10;
                commonOrderListEntities.addAll(mOrderCommonEntity.getOrder_list());
                if (mOrderCommonEntity.getOrder_list().size() < LIMIT) {
                    mListView.setHasLoadMore(false);
                } else {
                    mListView.setHasLoadMore(true);
                }

                if (null == commonOrderListEntities || commonOrderListEntities.size() == 0) {
                    mRlEmpty.setVisibility(View.VISIBLE);
                }

                Message msg = Message.obtain();
                msg.obj = offset;
                handler.sendMessage(msg);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
//                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, getActivity(), AlertView.Style.Alert, null).show();
                ApiStatusUtil.getInstance().apiStatuError(volleyError,getActivity());
            }
        });
    }

    /**
     * 获取数据，刷新页面
     */
    private void setSwipeRefreshInfo() {
        mPtrLayout.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getDesignerOder(mMemberType, designer_id, 0, LIMIT);
            }
        });
        mPtrLayout.setLastUpdateTimeRelateObject(this);
        mListView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                getDesignerOder(mMemberType, designer_id, OFFSET, LIMIT);
            }
        });
        mPtrLayout.autoRefresh();
    }

    /**
     * 适配器
     */
    public class MyCommonOrderAdapter extends CommonAdapter<OrderCommonEntity.OrderListEntity> {

        private String community_name;
        private String order_number;
        private String house_type;
        private String decoration_style;
        private String house_area;
        private String room;
        private String living_room;
        private String toilet;
        private String province_name;
        private String city_name;
        private String district_name;
        private String wk_cur_sub_node_id;
        private String decoration_style_convert;
        private String house_type_convert;
        private Map<String, String> livingRoomJson = AppJsonFileReader.getLivingRoom(getActivity());/// 室 .
        private Map<String, String> roomJson = AppJsonFileReader.getRoomHall(getActivity());    /// 厅 .
        private Map<String, String> toiletJson = AppJsonFileReader.getToilet(getActivity());    /// 卫 .
        private Map<String, String> style = AppJsonFileReader.getStyle(getActivity());
        private Map<String, String> houseJson = AppJsonFileReader.getSpace(getActivity());

        public MyCommonOrderAdapter(Context context, List<OrderCommonEntity.OrderListEntity> orderListEntities, int layoutId) {
            super(context, orderListEntities, layoutId);
        }

        @Override
        public void convert(final CommonViewHolder holder, final OrderCommonEntity.OrderListEntity orderListEntity) {
            if (orderListEntity == null) {
                return;
            }
            order_number = orderListEntity.getNeeds_id();
            room = orderListEntity.getRoom(); /// 室 .
            living_room = orderListEntity.getLiving_room();/// 卫 .
            toilet = orderListEntity.getToilet(); /// 厅 .
            house_area = orderListEntity.getHouse_area();
            community_name = orderListEntity.getCommunity_name();
            province_name = orderListEntity.getProvince_name();
            city_name = orderListEntity.getCity_name();
            district_name = orderListEntity.getDistrict_name();
            house_type = orderListEntity.getHouse_type();
            decoration_style = orderListEntity.getDecoration_style();
            room_convert = ConvertUtils.getConvert2CN(roomJson, room);
            toilet_convert = ConvertUtils.getConvert2CN(toiletJson, toilet);
            living_room_convert = ConvertUtils.getConvert2CN(livingRoomJson, living_room);
            decoration_style_convert = ConvertUtils.getConvert2CN(style, decoration_style);
            house_type_convert = ConvertUtils.getConvert2CN(houseJson, house_type);

            district_name = TextUtils.isEmpty(district_name) || "none".equals(district_name) ? "" : district_name;
            province_name = TextUtils.isEmpty(province_name) ? "" : province_name;
            city_name = TextUtils.isEmpty(city_name) ? "" : city_name;

            String livingRoom_Room_Toilet = room_convert + living_room_convert + toilet_convert;
            String address = province_name + city_name + district_name;
            final String wk_template_id = orderListEntity.getWk_template_id();
            List<OrderCommonEntity.OrderListEntity.BiddersBean> bidders = orderListEntity.getBidders();
            if (bidders != null && bidders.size() > 0) {
                wk_cur_sub_node_id = bidders.get(0).getWk_cur_sub_node_id();
                holder.setText(R.id.tv_designer_order_state, MPWkFlowManager.getWkSubNodeName(getActivity(), wk_template_id, wk_cur_sub_node_id));
            } else {
                holder.setText(R.id.tv_designer_order_state, UIUtils.getString(R.string.no_data));
            }
            holder.setText(R.id.common_order_number, order_number);
            holder.setText(R.id.tv_designer_order_house_type, house_type_convert);
            holder.setText(R.id.tv_designer_order_house_style, decoration_style_convert);
            holder.setText(R.id.tv_designer_order_address, community_name);
            holder.setText(R.id.tv_common_order_house_address, address + " " + livingRoom_Room_Toilet + " " + house_area + "㎡");

            holder.getView(R.id.btn_designer_order_projectdetail).setOnClickListener(/*this);*/
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            /**
                             * 项目进度．
                             */
                            int template_id = Integer.parseInt(wk_template_id);
                            Intent intentOrder = new Intent(getActivity(), WkFlowStateActivity.class);
                            intentOrder.putExtra(Constant.SeekDesignerDetailKey.NEEDS_ID, orderListEntity.getNeeds_id());
                            intentOrder.putExtra(Constant.SeekDesignerDetailKey.DESIGNER_ID, designer_id);
                            intentOrder.putExtra(Constant.BundleKey.TEMPDATE_ID, template_id);
                            intentOrder.putExtra(Constant.DemandDetailBundleKey.DEMAND_NEEDS_ID, orderListEntity.getNeeds_id());
                            intentOrder.putExtra(Constant.DemandDetailBundleKey.DEMAND_TYPE, Constant.DemandDetailBundleKey.TYPE_DESIGNERORDER_ACTIVITY);
                            intentOrder.putExtra(Constant.DemandDetailBundleKey.DEMAND_BID_STATUS, true);

                            getActivity().startActivity(intentOrder);
                        }
                    }
            );
            holder.getView(R.id.btn_designer_order_needdetail).setOnClickListener(/*this);*/new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /**
                     * 需求详情 .
                     */
                    Intent intent = new Intent(getActivity(), BiddingHallDetailActivity.class);
                    intent.putExtra(Constant.DemandDetailBundleKey.DEMAND_NEEDS_ID, orderListEntity.getNeeds_id());
                    intent.putExtra(Constant.DemandDetailBundleKey.DEMAND_TYPE, Constant.DemandDetailBundleKey.TYPE_DESIGNERORDER_ACTIVITY);
                    intent.putExtra(Constant.DemandDetailBundleKey.DEMAND_BID_STATUS, true);
                    getActivity().startActivity(intent);
                }
            });
        }
    }

    private RelativeLayout mRlEmpty;
    private ListViewFinal mListView;
    private PtrClassicFrameLayout mPtrLayout;

    private int LIMIT = 10;
    private int OFFSET = 0;
    private String designer_id;
    private String living_room_convert, room_convert, toilet_convert;

    private MyCommonOrderAdapter mCommonOrderAdapter;
    private OrderCommonEntity mOrderCommonEntity;
    private ArrayList<OrderCommonEntity.OrderListEntity> commonOrderListEntities = new ArrayList<>();
}
