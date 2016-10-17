package com.autodesk.shejijia.consumer.personalcenter.resdecoration.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.activity.SeekDesignerDetailActivity;
import com.autodesk.shejijia.consumer.manager.WkTemplateConstants;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.DecorationNeedsListBean;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.entity.DecorationBiddersBean;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.listviewdelegate.ItemViewDelegate;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.listviewdelegate.MultiItemViewHolder;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.tools.chatroom.JumpBean;
import com.autodesk.shejijia.shared.components.common.tools.chatroom.JumpToChatRoom;
import com.autodesk.shejijia.consumer.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.LoginUtils;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
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

        return StringUtils.isEmpty(wk_template_id) || WkTemplateConstants.IS_BEISHU.equals(wk_template_id);
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

        holder.setText(R.id.tv_decoration_beishu_name, contacts_name);
        holder.setText(R.id.tv_decoration_community_name, "/" + community_name);
        holder.setText(R.id.tv_decoration_beishu_needs_id, needs_id);
        holder.setText(R.id.tv_decoration_beishu_consumer_name, contacts_name);
        holder.setText(R.id.tv_decoration_beishu_phone, contacts_mobile);
        holder.setText(R.id.tv_decoration_beishu_address, province_name + city_name + district_name);
        holder.setText(R.id.tv_decoration_beishu_community_name, community_name);
        holder.setText(R.id.tv_decoration_beishu_consumer_name, UIUtils.getNoDataIfEmpty(contacts_name));
        holder.setText(R.id.tv_decoration_beishu_phone, UIUtils.getNoDataIfEmpty(contacts_mobile));

        district_name = StringUtils.isEmpty(district_name) ? "" : district_name;
        String address = province_name + city_name + district_name;
        if (StringUtils.isEmpty(province_name)) {
            holder.setText(R.id.tv_decoration_beishu_address, UIUtils.getString(R.string.no_select));
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

            holder.setTag(R.id.poly_designer_photo_beishu, avatar);
            PolygonImageView polygonImageView = holder.getView(R.id.poly_designer_photo_beishu);
            if (avatar.equalsIgnoreCase((String) polygonImageView.getTag())) {
                ImageUtils.displayAvatarImage(avatar, polygonImageView);
            }

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

            JumpBean jumpBean = new JumpBean();
            jumpBean.setReciever_hs_uid(hs_uid);
            jumpBean.setReciever_user_id(designer_id);
            jumpBean.setReciever_user_name(receiver_name);
            jumpBean.setThread_id(beishu_thread_id);
            jumpBean.setAcs_member_id(member_id);
            jumpBean.setMember_type(mMemberType);
            JumpToChatRoom.getChatRoom(mActivity, jumpBean);

        } else {
            LoginUtils.doLogin(mActivity);
        }
    }
}
