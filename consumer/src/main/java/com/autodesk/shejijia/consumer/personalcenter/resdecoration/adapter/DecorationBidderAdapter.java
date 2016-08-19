package com.autodesk.shejijia.consumer.personalcenter.resdecoration.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.activity.SeekDesignerDetailActivity;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.manager.MPWkFlowManager;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.entity.DecorationBiddersBean;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.appglobal.UrlMessagesContants;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.im.activity.ChatRoomActivity;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.adapter.CommonAdapter;
import com.autodesk.shejijia.shared.framework.adapter.CommonViewHolder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-8-15 .
 * @file DecorationBidderAdapter.java .
 * @brief 应标人数页面适配器 .
 */
public class DecorationBidderAdapter extends CommonAdapter<DecorationBiddersBean> {

    /// 节点11,邀请量房状态 .
    private static final int IS_BIDING = 11;

    private String mNeeds_id;
    private Activity mActivity;
    private DecorationBiddersBean biddersBean;
    private ArrayList<DecorationBiddersBean> mBidders;

    public DecorationBidderAdapter(Activity activity, List<DecorationBiddersBean> datas, String needs_id) {
        super(activity, datas, R.layout.item_decoration_bidder_list);
        mActivity = activity;
        mBidders = (ArrayList<DecorationBiddersBean>) datas;
        mNeeds_id = needs_id;
    }

    @Override
    public void convert(CommonViewHolder holder, final DecorationBiddersBean biddersBean) {
        this.biddersBean = biddersBean;
        final String designer_id = biddersBean.getDesigner_id();
        final String uid = biddersBean.getUid();

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
        holder.setText(R.id.tv_designer_good, be_good_at_prefix + "其它");
        holder.setText(R.id.tv_attention_num, "关注人数：" + "0");

        /**
         *控制显示拒绝、选TA量房，还是显示全流程状态
         */
        String wk_cur_sub_node_id = biddersBean.getWk_cur_sub_node_id();
        boolean isNotBiding = isNOtBiding(wk_cur_sub_node_id);
        if (isNotBiding) {
            holder.setVisible(R.id.ll_bidder_after_biding, true);
            holder.setVisible(R.id.ll_bidder_biding, false);
        } else {
            holder.setVisible(R.id.ll_bidder_biding, true);
            holder.setVisible(R.id.ll_bidder_after_biding, false);
        }

        holder.setText(R.id.tv_workflow_state, MPWkFlowManager.getWkSubNodeName(mActivity, null, wk_cur_sub_node_id));

        /// 聊天 .
        holder.setOnClickListener(R.id.img_designer_chat, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChatRoom();
            }
        });

        /// 设计师主页 .
        holder.setOnClickListener(R.id.piv_designer_photo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, SeekDesignerDetailActivity.class);
                intent.putExtra(Constant.ConsumerDecorationFragment.designer_id, designer_id);
                intent.putExtra(Constant.ConsumerDecorationFragment.hs_uid, uid);
                mActivity.startActivity(intent);
            }
        });

        /// 拒绝量房 .
        holder.setOnClickListener(R.id.btn_designer_refuse, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertView(UIUtils.getString(R.string.tip),
                        UIUtils.getString(R.string.select_Ta_refuse),
                        UIUtils.getString(R.string.cancel),
                        new String[]{UIUtils.getString(R.string.sure)}, null, mActivity,
                        AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object object, int position) {
                        if (position != AlertView.CANCELPOSITION) {
                            refuseDesignerMeasure(mNeeds_id, designer_id);
                        }
                    }
                }).setCancelable(false).show();
            }
        });

        /// 选TA量房 .
        holder.setOnClickListener(R.id.btn_designer_measure, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnItemViewClickCallback) {
                    mOnItemViewClickCallback.setOnItemViewClickCallback(designer_id);
                }
            }
        });
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

    /**
     * 消费者拒绝设计师量房
     */
    public void refuseDesignerMeasure(String needs_id, String designer_id) {
        CustomProgress.show(mActivity, "", false, null);
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                mActivity.finish();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CustomProgress.cancelDialog();
                new AlertView(UIUtils.getString(R.string.tip),
                        UIUtils.getString(R.string.network_error),
                        null, new String[]{UIUtils.getString(R.string.sure)}, null, mActivity,
                        AlertView.Style.Alert, null).show();
            }
        };
        MPServerHttpManager.getInstance().refuseDesignerMeasure(needs_id, designer_id, okResponseCallback);
    }

    /**
     * 当前订单
     *
     * @param wk_cur_sub_node_id 当前全流程节点
     * @return 如果为true, 表示进入全流程逻辑;如果为false,表示在应标进行中
     */
    public boolean isNOtBiding(String wk_cur_sub_node_id) {
        boolean isNotBiding = StringUtils.isNumeric(wk_cur_sub_node_id) && Integer.valueOf(wk_cur_sub_node_id) >= IS_BIDING;
        if (isNotBiding) {
            return true;
        } else {
            return false;
        }
    }


    public interface OnItemViewClickCallback {
        void setOnItemViewClickCallback(String designer_id);
    }

    private OnItemViewClickCallback mOnItemViewClickCallback;

    public void setOnItemViewClickCallback(OnItemViewClickCallback onItemViewClickCallback) {
        mOnItemViewClickCallback = onItemViewClickCallback;
    }
}

