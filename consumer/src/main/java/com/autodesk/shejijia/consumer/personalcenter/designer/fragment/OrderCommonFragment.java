package com.autodesk.shejijia.consumer.personalcenter.designer.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.manager.MPWkFlowManager;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.OrderCommonBean;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.activity.DecorationDetailActivity;
import com.autodesk.shejijia.consumer.personalcenter.workflow.activity.WkFlowStateActivity;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPMeasureFormBean;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.appglobal.UrlMessagesContants;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.utility.ConvertUtils;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.im.activity.ChatRoomActivity;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.consumer.base.adapter.CommonAdapter;
import com.autodesk.shejijia.consumer.base.adapter.CommonViewHolder;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

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
 * @file OrderCommonFragment.java .
 * @brief 竟优.
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

        mCommonOrderAdapter = new MyCommonOrderAdapter(UIUtils.getContext(), commonOrderListEntities, R.layout.item_designer_order_list);
        mListView.setAdapter(mCommonOrderAdapter);

        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (null != memberEntity && Constant.UerInfoKey.DESIGNER_TYPE.equalsIgnoreCase(memberEntity.getMember_type())) {
            setSwipeRefreshInfo();
        }
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

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onResume() {
        super.onResume();

    }
//
    // fixme 这个方法当check到我的项目时候进行回调．
    @Override
    public void onFragmentShown() {
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (null != memberEntity && Constant.UerInfoKey.DESIGNER_TYPE.equalsIgnoreCase(memberEntity.getMember_type())) {
            setSwipeRefreshInfo();
        }
    }

    /**
     * 获取普通订单数据
     */
    public void getDesignerOder(String Member_Type,
                                String designer_id, final int offset, int limit) {
        MPServerHttpManager.getInstance().getDesignerOrder(Member_Type, designer_id, offset, limit, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String userInfo = GsonUtil.jsonToString(jsonObject);
                LogUtils.i(TAG, "onResponse: userInfo" + userInfo);
                mOrderCommonEntity = GsonUtil.jsonToBean(userInfo, OrderCommonBean.class);
                LogUtils.i(TAG, userInfo);
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
                } else {
                    mRlEmpty.setVisibility(View.GONE);
                }

                Message msg = Message.obtain();
                msg.obj = offset;
                handler.sendMessage(msg);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                ApiStatusUtil.getInstance().apiStatuError(volleyError, getActivity());
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
    public class MyCommonOrderAdapter extends CommonAdapter<OrderCommonBean.OrderListBean> {

        private Map<String, String> style = AppJsonFileReader.getStyle(getActivity());
        private Map<String, String> houseJson = AppJsonFileReader.getSpace(getActivity());

        public MyCommonOrderAdapter(Context context, List<OrderCommonBean.OrderListBean> orderListEntities, int layoutId) {
            super(context, orderListEntities, layoutId);
        }

        @Override
        public void convert(final CommonViewHolder holder, final OrderCommonBean.OrderListBean orderListEntity) {
            if (orderListEntity == null) {
                return;
            }
            final String needs_id = orderListEntity.getNeeds_id();
            String consumer_name = orderListEntity.getContacts_name();
            String consumer_mobile = orderListEntity.getContacts_mobile();
            String province_name = orderListEntity.getProvince_name();
            String city_name = orderListEntity.getCity_name();
            String district_name = orderListEntity.getDistrict_name();
            String community_name = orderListEntity.getCommunity_name();
            String house_type = orderListEntity.getHouse_type();
            String decoration_style = orderListEntity.getDecoration_style();

            String decoration_style_convert = ConvertUtils.getConvert2CN(style, decoration_style);
            String house_type_convert = ConvertUtils.getConvert2CN(houseJson, house_type);

            district_name = UIUtils.getNoStringIfEmpty(district_name);
            province_name = UIUtils.getNoStringIfEmpty(province_name);
            city_name = UIUtils.getNoStringIfEmpty(city_name);

            String address = province_name + city_name + district_name;
            final String wk_template_id = orderListEntity.getWk_template_id();
            List<MPMeasureFormBean.BiddersBean> bidders = orderListEntity.getBidders();
            MPMeasureFormBean.BiddersBean biddersBean = null;

            if (bidders != null && bidders.size() > 0) {
                biddersBean = bidders.get(0);

                String wk_cur_sub_node_id = biddersBean.getWk_cur_sub_node_id();
                String avatar = orderListEntity.getAvatar();
                avatar = TextUtils.isEmpty(avatar) ? "" : avatar;
                PolygonImageView polygonImageView = holder.getView(R.id.piv_consumer_slite_photo);
                ImageUtils.displayAvatarImage(avatar, polygonImageView);

                holder.setText(R.id.tv_designer_order_state,
                        MPWkFlowManager.getWkSubNodeName(getActivity(), wk_template_id, wk_cur_sub_node_id));

            } else {
                holder.setText(R.id.tv_designer_order_state, UIUtils.getString(R.string.no_data));
            }
            holder.setText(R.id.common_order_number, needs_id);
            holder.setText(R.id.tv_decoration_phone, consumer_mobile);
            holder.setText(R.id.tv_designer_order_house_type, house_type_convert);
            holder.setText(R.id.tv_designer_order_house_style, decoration_style_convert);
            holder.setText(R.id.tv_designer_order_address, UIUtils.getNoStringIfEmpty(consumer_name) + "/" + community_name);
            holder.setText(R.id.tv_address, address);
            holder.setText(R.id.tv_customer_name, UIUtils.getNoStringIfEmpty(consumer_name));

            /**
             * 项目进度．
             */
            holder.getView(R.id.bt_designer_projectdetail).setOnClickListener(/*this);*/
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

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

            /**
             * 需求详情 .
             */
            holder.getView(R.id.btn_designer_order_projectdetail).setOnClickListener(/*this);*/new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent = new Intent(getActivity(), BiddingHallDetailActivity.class);
//                    intent.putExtra(Constant.DemandDetailBundleKey.DEMAND_NEEDS_ID, orderListEntity.getNeeds_id());
//                    intent.putExtra(Constant.DemandDetailBundleKey.DEMAND_TYPE, Constant.DemandDetailBundleKey.TYPE_DESIGNERORDER_ACTIVITY);
//                    intent.putExtra(Constant.DemandDetailBundleKey.DEMAND_BID_STATUS, true);
//                    getActivity().startActivity(intent);
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.ConsumerDecorationFragment.WK_TEMPLATE_ID, wk_template_id);
                    bundle.putString(Constant.ConsumerDecorationFragment.NEED_ID, orderListEntity.getNeeds_id());
                    DecorationDetailActivity.jumpTo(getActivity(), bundle);
                }
            });

            /**
             * IM
             */
            final MPMeasureFormBean.BiddersBean finalBiddersBean = biddersBean;
            holder.getView(R.id.bt_online_communication).setOnClickListener(/*this);*/new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null == finalBiddersBean) {
                        return;
                    }
                    MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
                    String member_id = memberEntity.getAcs_member_id();
                    String memType = memberEntity.getMember_type();
                    String designer_thread_id = finalBiddersBean.getDesign_thread_id();
                    String userName = finalBiddersBean.getUser_name();
                    String designer_id = finalBiddersBean.getDesigner_id();

                    if (TextUtils.isEmpty(designer_thread_id)) {
                        designer_thread_id = "";
                    }

                    Intent intent = new Intent(getActivity(), ChatRoomActivity.class);
                    intent.putExtra(ChatRoomActivity.THREAD_ID, designer_thread_id);
                    intent.putExtra(ChatRoomActivity.RECIEVER_USER_ID, designer_id);
                    intent.putExtra(ChatRoomActivity.RECIEVER_USER_NAME, userName);
                    intent.putExtra(ChatRoomActivity.ASSET_ID, needs_id);
                    intent.putExtra(ChatRoomActivity.ACS_MEMBER_ID, member_id);
                    intent.putExtra(ChatRoomActivity.MEMBER_TYPE, memType);
                    intent.putExtra(ChatRoomActivity.MEDIA_TYPE, UrlMessagesContants.mediaIdProject);
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

    private MyCommonOrderAdapter mCommonOrderAdapter;
    private OrderCommonBean mOrderCommonEntity;
    private ArrayList<OrderCommonBean.OrderListBean> commonOrderListEntities = new ArrayList<>();
}
