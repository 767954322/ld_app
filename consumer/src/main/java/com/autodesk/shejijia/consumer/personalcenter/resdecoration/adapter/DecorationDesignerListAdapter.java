package com.autodesk.shejijia.consumer.personalcenter.resdecoration.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.activity.SeekDesignerDetailActivity;
import com.autodesk.shejijia.consumer.manager.MPWkFlowManager;
import com.autodesk.shejijia.consumer.personalcenter.consumer.activity.AppraiseDesignerActivity;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.entity.DecorationBiddersBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.activity.FlowUploadDeliveryActivity;
import com.autodesk.shejijia.consumer.personalcenter.workflow.activity.WkFlowStateActivity;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPBidderBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPDeliveryBean;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.consumer.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.consumer.base.adapter.CommonAdapter;
import com.autodesk.shejijia.consumer.base.adapter.CommonViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-8-15 .
 * @file DecorationDesignerListAdapter.java .
 * @brief 消费者家装订单设计师列表适配器.
 */
public class DecorationDesignerListAdapter extends CommonAdapter<DecorationBiddersBean> {

    private String mNeedsId;
    private Activity mActivity;
    private String wk_template_id;
    private MPBidderBean mMPBidderBean = new MPBidderBean();
    private ArrayList<DecorationBiddersBean> biddersEntities;

    public DecorationDesignerListAdapter(Activity activity, List<DecorationBiddersBean> datas, String needs_id, String wk_template_id) {
        super(activity, datas, R.layout.item_decoration_designer_list);
        this.biddersEntities = (ArrayList<DecorationBiddersBean>) datas;
        this.mActivity = activity;
        this.mNeedsId = needs_id;
        this.wk_template_id = wk_template_id;
    }

    @Override
    public void convert(CommonViewHolder holder, DecorationBiddersBean bidder) {

        final String designerId = bidder.getDesigner_id();
        final String bidderUid = bidder.getUid();
        final String user_name = bidder.getUser_name();
        final String avatarUrl = bidder.getAvatar();
        final String mThread_id = bidder.getDesign_thread_id();
        MPDeliveryBean mpDeliveryBean = bidder.getDelivery();

        String wk_cur_sub_node_id = bidder.getWk_cur_sub_node_id();

        String wkSubNodeName = MPWkFlowManager.getWkSubNodeName(mActivity, wk_template_id, wk_cur_sub_node_id, mpDeliveryBean);
        holder.setText(R.id.tv_decoration_mesure, wkSubNodeName);

        boolean falg = StringUtils.isNumeric(wk_cur_sub_node_id) && Integer.valueOf(wk_cur_sub_node_id) == 63;
        if (falg) {
            holder.setText(R.id.tv_decoration_mesure, UIUtils.getString(R.string.evaluation));
            holder.getView(R.id.tv_decoration_mesure).setBackgroundResource(R.drawable.bg_text_filtrate_pressed);
        } else {
            holder.setText(R.id.tv_decoration_mesure, wkSubNodeName);
            holder.getView(R.id.tv_decoration_mesure).setBackgroundResource(R.drawable.bg_actionsheet_cancel);
        }


        holder.setText(R.id.tv_designer_name, user_name);

        holder.setTag(R.id.piv_consumer_order_photo, avatarUrl);
        PolygonImageView polygonImageView = holder.getView(R.id.piv_consumer_order_photo);

        if (avatarUrl.equalsIgnoreCase((String) polygonImageView.getTag())) {
            if (StringUtils.isEmpty(avatarUrl)) {
                polygonImageView.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_default_avator));
            } else {
                ImageUtils.loadUserAvatar1(polygonImageView, avatarUrl);
            }
        }

        /**
         * 判断进入全流程逻辑还是进入评价页面
         * 节点63,进入评价页面;其它节点，进入全流程逻辑
         */
        if (falg) {
            /**
             * 进入评价页面
             */
            holder.setOnClickListener(R.id.tv_decoration_mesure, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMPBidderBean.setAvatar(avatarUrl);
                    mMPBidderBean.setUser_name(user_name);
                    mMPBidderBean.setDesigner_id(designerId);

                    Intent evaluateIntent = new Intent(mActivity, AppraiseDesignerActivity.class);
                    evaluateIntent.putExtra(Constant.SeekDesignerDetailKey.NEEDS_ID, mNeedsId);
                    evaluateIntent.putExtra(FlowUploadDeliveryActivity.BIDDER_ENTITY, mMPBidderBean);
                    evaluateIntent.putExtra(Constant.SeekDesignerDetailKey.DESIGNER_ID, designerId);
                    mActivity.startActivity(evaluateIntent);
                }
            });
            /**
             * 全流程
             */
            holder.setOnClickListener(R.id.tv_designer_name, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int template_id = Integer.parseInt(wk_template_id);
                    startWkFlowStateActivity(mNeedsId, designerId, template_id, mThread_id);
                }
            });
        } else {
            /**
             * 进入全流程逻辑
             */
            holder.setOnClickListener(R.id.rl_item_decoration, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int template_id = Integer.parseInt(wk_template_id);
                    startWkFlowStateActivity(mNeedsId, designerId, template_id, mThread_id);
                }
            });
        }


        /**
         * 设计师主页
         */
        holder.setOnClickListener(R.id.piv_consumer_order_photo, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, SeekDesignerDetailActivity.class);
                intent.putExtra(Constant.ConsumerDecorationFragment.designer_id, designerId);
                intent.putExtra(Constant.ConsumerDecorationFragment.hs_uid, bidderUid);
                mActivity.startActivity(intent);
            }
        });
    }

    private void startWkFlowStateActivity(String needsId, String designerId, int template_id, String thread_id) {
        Intent intent = new Intent();
        intent.setClass(mActivity, WkFlowStateActivity.class);
        intent.putExtra(Constant.SeekDesignerDetailKey.NEEDS_ID, needsId);
        intent.putExtra(Constant.SeekDesignerDetailKey.DESIGNER_ID, designerId);
        intent.putExtra(Constant.BundleKey.TEMPDATE_ID, template_id);
        intent.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_THREAD_ID, thread_id);
        mActivity.startActivity(intent);
    }
}
