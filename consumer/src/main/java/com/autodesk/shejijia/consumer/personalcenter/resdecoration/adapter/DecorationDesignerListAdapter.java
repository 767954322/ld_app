package com.autodesk.shejijia.consumer.personalcenter.resdecoration.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.activity.SeekDesignerDetailActivity;
import com.autodesk.shejijia.consumer.manager.MPWkFlowManager;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.entity.DecorationBiddersBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.activity.WkFlowStateActivity;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPBidderBean;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.framework.adapter.CommonAdapter;
import com.autodesk.shejijia.shared.framework.adapter.CommonViewHolder;

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
    private  String wk_template_id;
    private MPBidderBean mMPBidderBean = new MPBidderBean();
    private ArrayList<DecorationBiddersBean> biddersEntities;

    public DecorationDesignerListAdapter(Activity activity, List<DecorationBiddersBean> datas, String needs_id,String wk_template_id) {
        super(activity, datas, R.layout.item_decoration_designer_list);
        this.biddersEntities = (ArrayList<DecorationBiddersBean>) datas;
        mActivity = activity;
        mNeedsId = needs_id;
        this.wk_template_id = wk_template_id;
    }

    @Override
    public void convert(CommonViewHolder holder, DecorationBiddersBean bidder) {

        final String designerId = bidder.getDesigner_id();
        final String bidderUid = bidder.getUid();
        final String user_name = bidder.getUser_name();
        final String avatarUrl = bidder.getAvatar();

        String wk_cur_sub_node_id = bidder.getWk_cur_sub_node_id();

        String wkSubNodeName = MPWkFlowManager.getWkSubNodeName(mActivity, wk_template_id, wk_cur_sub_node_id);
        holder.setText(R.id.tv_decoration_mesure, wkSubNodeName);


        holder.setText(R.id.tv_designer_name, user_name);
        PolygonImageView polygonImageView = holder.getView(R.id.piv_consumer_order_photo);
        ImageUtils.displayAvatarImage(avatarUrl, polygonImageView);

        /**
         * TODO 九月份内容，暂时屏蔽
         * 判断进入全流程逻辑还是进入评价页面
         * 节点63,进入评价页面;其它节点，进入全流程逻辑
         */
//        if (StringUtils.isNumeric(wk_cur_sub_node_id) && Integer.valueOf(wk_cur_sub_node_id) == 63) {
//            /**
//             * 进入评价页面
//             */
//            holder.setOnClickListener(R.id.rl_item_decoration, new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mMPBidderBean.setAvatar(avatarUrl);
//                    mMPBidderBean.setUser_name(user_name);
//                    mMPBidderBean.setDesigner_id(designerId);
//
//                    Intent evaluateIntent = new Intent(mActivity, AppraiseDesignerActivity.class);
//                    evaluateIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, mNeedsId);
//                    evaluateIntent.putExtra(FlowUploadDeliveryActivity.BIDDER_ENTITY, mMPBidderBean);
//                    evaluateIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, designerId);
//                    mActivity.startActivity(evaluateIntent);
//                }
//            });
//        } else {
        /**
         * 进入全流程逻辑
         */
        holder.setOnClickListener(R.id.rl_item_decoration, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int template_id = Integer.parseInt(wk_template_id);
                Intent intent = new Intent();
                intent.setClass(mActivity, WkFlowStateActivity.class);
                intent.putExtra(Constant.SeekDesignerDetailKey.NEEDS_ID, mNeedsId);
                intent.putExtra(Constant.SeekDesignerDetailKey.DESIGNER_ID, designerId);
                intent.putExtra(Constant.BundleKey.TEMPDATE_ID, template_id);
                mActivity.startActivity(intent);
            }
        });
//        }


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
}