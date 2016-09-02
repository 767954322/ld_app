package com.autodesk.shejijia.consumer.personalcenter.resdecoration.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.activity.SeekDesignerDetailActivity;
import com.autodesk.shejijia.consumer.manager.WkTemplateConstants;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.DecorationNeedsListBean;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.entity.DecorationBiddersBean;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.listviewdelegate.ItemViewDelegate;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.listviewdelegate.MultiItemViewHolder;
import com.autodesk.shejijia.shared.components.common.appglobal.ApiManager;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.appglobal.UrlMessagesContants;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.common.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.im.activity.ChatRoomActivity;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThread;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThreads;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatUtility;
import com.autodesk.shejijia.shared.components.im.manager.MPChatHttpManager;
import com.autodesk.shejijia.shared.framework.AdskApplication;

import java.util.List;

/**
 * <p>Description:家装订单,北舒套餐布局 </p>
 *
 * @author liuhea
 * @date 16/8/16
 *  
 */
public class DecorationBeiShuDelegate implements ItemViewDelegate<DecorationNeedsListBean> {

    ///is_beishu:0 北舒套餐 1 非北舒.
    private static final String NONE = "none";

    private Activity mActivity;

    public DecorationBeiShuDelegate(Activity activity) {
        mActivity = activity;

    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_decoration_beishu;
    }

    @Override
    public boolean isForViewType(DecorationNeedsListBean needsListBean, int position) {
        String wk_template_id = needsListBean.getWk_template_id();

        return TextUtils.isEmpty(wk_template_id) || WkTemplateConstants.IS_BEISHU.equals(wk_template_id);
    }

    @Override
    public void convert(final MultiItemViewHolder holder, final DecorationNeedsListBean decorationNeedsListBean, final int position) {
        String community_name = decorationNeedsListBean.getCommunity_name();
        String needs_id = decorationNeedsListBean.getNeeds_id();
        String contacts_name = decorationNeedsListBean.getContacts_name();
        String contacts_mobile = decorationNeedsListBean.getContacts_mobile();

        String province_name = decorationNeedsListBean.getProvince_name();
        String city_name = decorationNeedsListBean.getCity_name();

        String district = decorationNeedsListBean.getDistrict();
        String district_name = decorationNeedsListBean.getDistrict_name();

        holder.setText(R.id.tv_decoration_beishu_name, community_name);
        holder.setText(R.id.tv_decoration_beishu_needs_id, needs_id);
        holder.setText(R.id.tv_decoration_beishu_consumer_name, contacts_name);
        holder.setText(R.id.tv_decoration_beishu_phone, contacts_mobile);
        holder.setText(R.id.tv_decoration_beishu_address, province_name + city_name + district_name);
        holder.setText(R.id.tv_decoration_beishu_community_name, community_name);
        holder.setText(R.id.tv_decoration_beishu_consumer_name, UIUtils.getNoDataIfEmpty(contacts_name));
        holder.setText(R.id.tv_decoration_beishu_phone, UIUtils.getNoDataIfEmpty(contacts_mobile));

        district_name = TextUtils.isEmpty(district_name) || NONE.equals(district_name) || NONE.equals(district) || TextUtils.isEmpty(district) ? "" : district_name;
        String address = province_name + city_name + district_name;
        if (TextUtils.isEmpty(city_name)) {
            holder.setText(R.id.tv_decoration_beishu_address, UIUtils.getString(R.string.nodata));
        } else {
            holder.setText(R.id.tv_decoration_beishu_address, address);
        }
        holder.setText(R.id.tv_decoration_beishu_community_name, UIUtils.getNoDataIfEmpty(community_name));

        List<DecorationBiddersBean> bidders = decorationNeedsListBean.getBidders();
        if (null != bidders && bidders.size() > 0) {

            final DecorationBiddersBean decorationBiddersBean = bidders.get(0);
            String avatar = decorationBiddersBean.getAvatar();
            final String user_name = decorationBiddersBean.getUser_name();
            final String uid = decorationBiddersBean.getUid();
            final String designer_id = decorationBiddersBean.getDesigner_id();
            final String beishu_thread_id = decorationNeedsListBean.getBeishu_thread_id();

            PolygonImageView polygonImageView = holder.getView(R.id.poly_designer_photo_beishu);
            ImageUtils.displayAvatarImage(avatar, polygonImageView);
            holder.setText(R.id.tv_designer_name_beishu, user_name);

            holder.setOnClickListener(R.id.img_decoration_beishu_chat, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openChatRoom(uid, user_name, designer_id, beishu_thread_id);
                }
            });

            holder.setOnClickListener(R.id.poly_designer_photo_beishu, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mActivity, SeekDesignerDetailActivity.class);
                    intent.putExtra(Constant.ConsumerDecorationFragment.designer_id, designer_id);
                    intent.putExtra(Constant.ConsumerDecorationFragment.hs_uid, uid);
                    mActivity.startActivity(intent);
                }
            });
        }

    }

    /**
     * 进入聊天室
     *
     * @param uid
     * @param user_name
     * @param designer_id
     * @param beishu_thread_id
     */
    private void openChatRoom(String uid, String user_name, final String designer_id, final String beishu_thread_id) {
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (memberEntity != null) {
            final String member_id = memberEntity.getAcs_member_id();
            final String hs_uid = uid;
            final String mMemberType = memberEntity.getMember_type();
            final String receiver_name = user_name;
            final String recipient_ids = member_id + "," + designer_id + "," + ApiManager.getAdmin_User_Id();
            MPChatHttpManager.getInstance().retrieveMultipleMemberThreads(recipient_ids, 0, 10, new OkStringRequest.OKResponseCallback() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                }

                @Override
                public void onResponse(String s) {
                    MPChatThreads mpChatThreads = MPChatThreads.fromJSONString(s);

                    Intent intent = new Intent(mActivity, ChatRoomActivity.class);
                    intent.putExtra(ChatRoomActivity.RECIEVER_USER_ID, designer_id);
                    intent.putExtra(ChatRoomActivity.RECIEVER_USER_NAME, receiver_name);
                    intent.putExtra(ChatRoomActivity.MEMBER_TYPE, mMemberType);
                    intent.putExtra(ChatRoomActivity.ACS_MEMBER_ID, member_id);


                    if (mpChatThreads != null && mpChatThreads.threads.size() > 0) {
                        MPChatThread mpChatThread = mpChatThreads.threads.get(0);

                        int assetId = MPChatUtility.getAssetIdFromThread(mpChatThread);
                        intent.putExtra(ChatRoomActivity.THREAD_ID, beishu_thread_id);
                        intent.putExtra(ChatRoomActivity.ASSET_ID, assetId + "");
                        intent.putExtra(ChatRoomActivity.MEDIA_TYPE, UrlMessagesContants.mediaIdProject);
                    } else {
                        intent.putExtra(ChatRoomActivity.RECIEVER_HS_UID, hs_uid);
                        intent.putExtra(ChatRoomActivity.ASSET_ID, "");
                    }
                    mActivity.startActivity(intent);
                }
            });

        } else {
            AdskApplication.getInstance().doLogin(mActivity);
        }
    }
}
