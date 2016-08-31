package com.autodesk.shejijia.consumer.personalcenter.designer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.bidhall.activity.BiddingHallDetailActivity;
import com.autodesk.shejijia.consumer.manager.MPWkFlowManager;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.OrderCommonEntity;
import com.autodesk.shejijia.consumer.personalcenter.workflow.activity.WkFlowStateActivity;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.utility.ConvertUtils;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.im.activity.ChatRoomActivity;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.adapter.CommonAdapter;
import com.autodesk.shejijia.shared.framework.adapter.CommonViewHolder;

import java.util.List;
import java.util.Map;

/**
 * Created by luchongbin on 16-8-24.
 */
public class EliteAdapter extends CommonAdapter<OrderCommonEntity.OrderListEntity> {

    private Map<String, String> style;
    private Map<String, String> houseJson;
    private String designer_id;
    private Context context;
    public EliteAdapter(Context context, List<OrderCommonEntity.OrderListEntity> orderListEntities, int layoutId,String designer_id) {
        super(context, orderListEntities, layoutId);
        this.context = context;
        style = AppJsonFileReader.getStyle((Activity) context);
        houseJson = AppJsonFileReader.getSpace((Activity) context);
        this.designer_id = designer_id;
    }

    @Override
    public void convert(CommonViewHolder holder,final OrderCommonEntity.OrderListEntity orderListEntity) {
        if (orderListEntity == null) {
            return;
        }
        String order_number = orderListEntity.getNeeds_id();
        String community_name = orderListEntity.getCommunity_name();
        String province_name = orderListEntity.getProvince_name();
        String city_name = orderListEntity.getCity_name();
        String district_name = orderListEntity.getDistrict_name();
        String house_type = orderListEntity.getHouse_type();
        String decoration_style = orderListEntity.getDecoration_style();
        String decoration_style_convert = ConvertUtils.getConvert2CN(style, decoration_style);
        String house_type_convert = ConvertUtils.getConvert2CN(houseJson, house_type);
        district_name = TextUtils.isEmpty(district_name) || "none".equals(district_name) ? "" : district_name;
        province_name = TextUtils.isEmpty(province_name) ? "" : province_name;
        city_name = TextUtils.isEmpty(city_name) ? "" : city_name;
        String address = province_name + city_name + district_name;
        final String beishu_thread_id = orderListEntity.getBeishu_thread_id();
        final String contacts_name= orderListEntity.getContacts_name();
        String status =  getEliteNeedsState(orderListEntity.getCustom_string_status());
        holder.setText(R.id.tv_decoration_state, status);
        holder.setText(R.id.tv_decoration_needs_id, order_number);
        holder.setText(R.id.tv_decoration_address, address);
        holder.setText(R.id.tv_decoration_house_type, house_type_convert);
        holder.setText(R.id.tv_decoration_style, decoration_style_convert);

        holder.setText(R.id.tv_decoration_name, contacts_name+"/"+community_name);
        holder.setText(R.id.tv_decoration_mobile, orderListEntity.getContacts_mobile());

        PolygonImageView polygonImageView = holder.getView(R.id.piv_consumer_slite_photo);
        ImageUtils.displayAvatarImage(orderListEntity.getAvatar(), polygonImageView);
        holder.setText(R.id.tv_customer_name, contacts_name);

        holder.getView(R.id.bt_designer_projectdetail).setOnClickListener(/*this);*/
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /**
                         * 项目进度．
                         */
                        Intent intentOrder = new Intent(context, WkFlowStateActivity.class);
                        intentOrder.putExtra(Constant.SeekDesignerDetailKey.NEEDS_ID, orderListEntity.getNeeds_id());
                        intentOrder.putExtra(Constant.SeekDesignerDetailKey.DESIGNER_ID, designer_id);

                        intentOrder.putExtra(Constant.DemandDetailBundleKey.DEMAND_NEEDS_ID, orderListEntity.getNeeds_id());
                        intentOrder.putExtra(Constant.DemandDetailBundleKey.DEMAND_TYPE, Constant.DemandDetailBundleKey.TYPE_DESIGNERORDER_ACTIVITY);
                        intentOrder.putExtra(Constant.DemandDetailBundleKey.DEMAND_BID_STATUS, true);

                        context.startActivity(intentOrder);
                    }
                }
        );
        holder.getView(R.id.ll_designer_elite_needdetail).setOnClickListener(/*this);*/new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 需求详情 .
                 */
                Intent intent = new Intent(context, BiddingHallDetailActivity.class);
                intent.putExtra(Constant.DemandDetailBundleKey.DEMAND_NEEDS_ID, orderListEntity.getNeeds_id());
                intent.putExtra(Constant.DemandDetailBundleKey.DEMAND_TYPE, Constant.DemandDetailBundleKey.TYPE_DESIGNERORDER_ACTIVITY);
                intent.putExtra(Constant.DemandDetailBundleKey.DEMAND_BID_STATUS, true);
                context.startActivity(intent);
            }
        });


        holder.getView(R.id.bt_online_communication).setOnClickListener(/*this);*/new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 在线聊天 .
                 */

                MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
                Intent intent = new Intent(context, ChatRoomActivity.class);
                String acs_member_id = mMemberEntity.getAcs_member_id();
                String member_type = mMemberEntity.getMember_type();
                intent.putExtra(ChatRoomActivity.RECIEVER_USER_NAME, contacts_name);
                intent.putExtra(ChatRoomActivity.THREAD_ID, beishu_thread_id);
                intent.putExtra(ChatRoomActivity.ASSET_ID, "");
                intent.putExtra(ChatRoomActivity.MEMBER_TYPE, member_type);
//                intent.putExtra(ChatRoomActivity.RECIEVER_USER_ID, customer_id);
                intent.putExtra(ChatRoomActivity.ACS_MEMBER_ID, acs_member_id);
                context.startActivity(intent);


            }
        });

    }
    /**
     * 获取精选状态信息
     * @param custom_string_status
     * @param
     * @return
     */
    private String getEliteNeedsState(int custom_string_status) {
        String needsState = UIUtils.getString(R.string.content_others);
        switch (String.valueOf(custom_string_status)) {
            case Constant.NumKey.TWENTY_ONE://21:审核中
                needsState = UIUtils.getString(R.string.checking);
                break;
            case Constant.NumKey.TWENTY_TWO://22:审核未通过
                needsState = UIUtils.getString(R.string.disapprove);
                break;
            case Constant.NumKey.TWENTY_THREE://23:审核通过(设计中)
                needsState = UIUtils.getString(R.string.designing);
                break;
            default:
                break;
        }

        return needsState;
    }
}