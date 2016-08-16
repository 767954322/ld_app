package com.autodesk.shejijia.consumer.personalcenter.resdecoration.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.entity.DecorationBiddersBean;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.appglobal.UrlMessagesContants;
import com.autodesk.shejijia.shared.components.common.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.im.activity.ChatRoomActivity;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.adapter.CommonAdapter;
import com.autodesk.shejijia.shared.framework.adapter.CommonViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-8-15 .
 * @file DecorationBidderAdapter.java .
 * @brief 应标人数页面适配器 .
 */
public class DecorationBidderAdapter extends CommonAdapter<DecorationBiddersBean> implements
        View.OnClickListener {

    private Activity mActivity;
    private String mNeeds_id;
    private DecorationBiddersBean biddersBean;
    private ArrayList<DecorationBiddersBean> mBidders;

    public DecorationBidderAdapter(Activity activity, List<DecorationBiddersBean> datas, String needs_id) {
        super(activity, datas, R.layout.item_decoration_bidder_list);
        mActivity = activity;
        mBidders = (ArrayList<DecorationBiddersBean>) datas;
        mNeeds_id = needs_id;
    }


    @Override
    public void convert(CommonViewHolder holder, DecorationBiddersBean biddersBean) {
        this.biddersBean = biddersBean;
        String avatar = biddersBean.getAvatar();
        String nick_name = biddersBean.getUser_name();
        nick_name = TextUtils.isEmpty(nick_name) ? "暂无数据" : nick_name;
        avatar = TextUtils.isEmpty(avatar) ? "" : avatar;

        PolygonImageView polygonImageView = holder.getView(R.id.piv_designer_photo);
        ImageUtils.displayAvatarImage(avatar, polygonImageView);

        String design_price_min = biddersBean.getDesign_price_min();
        String design_price_max = biddersBean.getDesign_price_max();
        String be_good_at_prefix = UIUtils.getString(R.string.content_designer_good_at);
        boolean costBoolean = TextUtils.isEmpty(design_price_max) || TextUtils.isEmpty(design_price_min);
        if (costBoolean) {
            holder.setText(R.id.tv_designer_cost, UIUtils.getString(R.string.has_yet_to_fill_out));
        } else {
            holder.setText(R.id.tv_designer_cost, design_price_min + "-" + design_price_max + "元/m²");
        }
        holder.setText(R.id.tv_designer_name, nick_name);
        holder.setText(R.id.tv_designer_production, "作品：0");
        holder.setText(R.id.tv_designer_good, be_good_at_prefix + "暂无数据");
        holder.setText(R.id.tv_attention_num, "关注人数：" + "0");

        holder.setOnClickListener(R.id.img_designer_chat, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_designer_chat:
                Toast.makeText(mActivity, "聊天", Toast.LENGTH_SHORT).show();
                openChatRoom();
                break;
        }
    }

    /**
     * 打开聊天室
     */
    private void openChatRoom() {
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        String member_id = memberEntity.getAcs_member_id();
        String memType = memberEntity.getMember_type();
        String designer_thread_id = biddersBean.getDesign_thread_id();
        String userName = biddersBean.getUser_name();
        String designer_id = biddersBean.getDesigner_id();

        if (TextUtils.isEmpty(designer_thread_id)) {
            designer_thread_id = "";
        }

        Intent intent = new Intent(mActivity, ChatRoomActivity.class);
        intent.putExtra(ChatRoomActivity.THREAD_ID, designer_thread_id);
        intent.putExtra(ChatRoomActivity.RECIEVER_USER_ID, designer_id);
        intent.putExtra(ChatRoomActivity.RECIEVER_USER_NAME, userName);
        intent.putExtra(ChatRoomActivity.ASSET_ID, mNeeds_id);
        intent.putExtra(ChatRoomActivity.ACS_MEMBER_ID, member_id);
        intent.putExtra(ChatRoomActivity.MEMBER_TYPE, memType);
        intent.putExtra(ChatRoomActivity.MEDIA_TYPE, UrlMessagesContants.mediaIdProject);
        mActivity.startActivity(intent);
    }
}
