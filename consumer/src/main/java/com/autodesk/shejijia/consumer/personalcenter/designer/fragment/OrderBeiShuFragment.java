package com.autodesk.shejijia.consumer.personalcenter.designer.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.OrderBeiShutEntity;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.im.activity.ChatRoomActivity;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.adapter.CommonAdapter;
import com.autodesk.shejijia.shared.framework.adapter.CommonViewHolder;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.loadingviewfinal.ListViewFinal;
import cn.finalteam.loadingviewfinal.OnDefaultRefreshListener;
import cn.finalteam.loadingviewfinal.OnLoadMoreListener;
import cn.finalteam.loadingviewfinal.PtrClassicFrameLayout;
import cn.finalteam.loadingviewfinal.PtrFrameLayout;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-6-7 .
 * @file OrderBeiShuFragment.java .
 * @brief 我的订单:北舒订单页面 .
 */
public class OrderBeiShuFragment extends BaseFragment {

    public OrderBeiShuFragment() {
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_beishu_meal;
    }

    @Override
    protected void initView() {
        mListView = (ListViewFinal) rootView.findViewById(R.id.lv_designer_beishu_order);
        mPtrLayout = (PtrClassicFrameLayout) rootView.findViewById(R.id.ptr_layout);

        mMyBeiShuMealAdapter = new MyBeiShuMealAdapter(UIUtils.getContext(), mBeiShuNeedsOrderListEntities, R.layout.item_lv_designer_beishu_order);
        mListView.setAdapter(mMyBeiShuMealAdapter);
        setSwipeRefreshInfo();
    }

    @Override
    protected void initData() {
        MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (mMemberEntity == null) {
            return;
        }
        designer_id = mMemberEntity.getAcs_member_id();
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
            mMyBeiShuMealAdapter.notifyDataSetChanged();
        }
    };

    private void setSwipeRefreshInfo() {
        mPtrLayout.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getDesignerBeiShuOrder(1, designer_id, 0, LIMIT);
            }
        });
        mPtrLayout.setLastUpdateTimeRelateObject(this);
        mListView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                getDesignerBeiShuOrder(2, designer_id, OFFSET, LIMIT);
            }
        });
        mPtrLayout.autoRefresh();
    }

    /**
     * 北舒套餐订单列表适配器
     */
    private class MyBeiShuMealAdapter extends CommonAdapter<OrderBeiShutEntity.BeishuNeedsOrderListEntity> {
        String customer_id;
        public MyBeiShuMealAdapter(Context context, List<OrderBeiShutEntity.BeishuNeedsOrderListEntity> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void convert(CommonViewHolder holder, OrderBeiShutEntity.BeishuNeedsOrderListEntity beishuNeedsOrderListEntity) {
            String community_name = beishuNeedsOrderListEntity.getCommunity_name();
            int needs_id = beishuNeedsOrderListEntity.getNeeds_id();
            String contacts_mobile = beishuNeedsOrderListEntity.getContacts_mobile();
            final String contacts_name = beishuNeedsOrderListEntity.getContacts_name();
            province_name = beishuNeedsOrderListEntity.getProvince_name();
            city_name = beishuNeedsOrderListEntity.getCity_name();
            district_name = beishuNeedsOrderListEntity.getDistrict_name();
            district_name = TextUtils.isEmpty(district_name) || district_name.equals("none") ? "" : district_name;
            avatar = beishuNeedsOrderListEntity.getAvatar();
            customer_id = beishuNeedsOrderListEntity.getCustomer_id();
            if (avatar == null || TextUtils.isEmpty(avatar)) {
                holder.setImageDrawable(R.id.ib_personal_b_photo_beishu, getResources().getDrawable(R.drawable.icon_default_avator));
            } else {
                ImageUtils.displayAvatarImage(avatar, (PolygonImageView) holder.getView(R.id.ib_personal_b_photo_beishu));
            }
            final String beishu_thread_id = beishuNeedsOrderListEntity.getBeishu_thread_id();
            String community_name_cut = community_name.replace("北舒套餐 - ", "");/// 去除北舒套餐标志 .
            holder.setText(R.id.tv_desinger_beishuorder_address, community_name_cut);
            holder.setText(R.id.tv_desinger_beishuorder_projectid, needs_id + "");
            holder.setText(R.id.tv_desinger_beishuorder_contact_name, contacts_name);
            holder.setText(R.id.tv_desinger_beishuorder_phone, contacts_mobile);

            String address = UIUtils.getNoDataIfEmpty(province_name) + " " + UIUtils.getNoDataIfEmpty(city_name) + " " + district_name;
            holder.setText(R.id.tv_desinger_beishuorder_projectaddress, address);
            holder.setText(R.id.tv_desinger_beishuorder_homeaddress, community_name_cut);
            holder.setText(R.id.tv_designer_name_beishu, contacts_name);

            holder.getView(R.id.img_decoration_beishu_chat).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
                    Intent intent = new Intent(getActivity(), ChatRoomActivity.class);
//                    beishu_thread_id、consumer_id、contacts_name、acs_member_id
                    String acs_member_id = mMemberEntity.getAcs_member_id();
                    String member_type = mMemberEntity.getMember_type();
                    intent.putExtra(ChatRoomActivity.RECIEVER_USER_NAME, contacts_name);
                    intent.putExtra(ChatRoomActivity.THREAD_ID, beishu_thread_id);
                    intent.putExtra(ChatRoomActivity.ASSET_ID, "");
                    intent.putExtra(ChatRoomActivity.MEMBER_TYPE, member_type);
                    intent.putExtra(ChatRoomActivity.RECIEVER_USER_ID, customer_id);
                    intent.putExtra(ChatRoomActivity.ACS_MEMBER_ID, acs_member_id);
                    startActivity(intent);
                }
            });
        }
    }

    /**
     * 得到北舒套餐订单列表
     *
     * @param state
     * @param designer_id
     * @param offset
     * @param limit
     */
    public void getDesignerBeiShuOrder(final int state, String designer_id, final int offset, int limit) {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String userInfo = GsonUtil.jsonToString(jsonObject);
                mOrderBeiShutEntity = GsonUtil.jsonToBean(userInfo, OrderBeiShutEntity.class);
                if (offset == 0) {
                    mBeiShuNeedsOrderListEntities.clear();
                }
                OFFSET = offset + 10;
                mBeiShuNeedsOrderListEntities.addAll(mOrderBeiShutEntity.getBeishu_needs_order_list());
                if (mOrderBeiShutEntity.getBeishu_needs_order_list().size() < LIMIT) {
                    mListView.setHasLoadMore(false);
                } else {
                    mListView.setHasLoadMore(true);
                }
                Message msg = Message.obtain();
                msg.obj = offset;
                handler.sendMessage(msg);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                if (getActivity() != null) {
//                    new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, getActivity(),
//                            AlertView.Style.Alert, null).show();
                    ApiStatusUtil.getInstance().apiStatuError(volleyError,getActivity());
                }
            }
        };
        MPServerHttpManager.getInstance().getDesignerBeiShuOrder(
                designer_id, offset, limit, okResponseCallback);
    }

    private PtrClassicFrameLayout mPtrLayout;
    private ListViewFinal mListView;

    private String province_name;
    private String city_name;
    private String district_name;
    private String avatar;
    private String designer_id;
    private int LIMIT = 10;
    private int OFFSET = 0;
    private MyBeiShuMealAdapter mMyBeiShuMealAdapter;
    private OrderBeiShutEntity mOrderBeiShutEntity;
    private ArrayList<OrderBeiShutEntity.BeishuNeedsOrderListEntity> mBeiShuNeedsOrderListEntities = new ArrayList<>();
}
