package com.autodesk.shejijia.consumer.personalcenter.resdecoration.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPWkFlowManager;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.entity.DecorationBiddersBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.activity.WkFlowStateActivity;
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

    private Activity mActivity;
    private ArrayList<DecorationBiddersBean> biddersEntities;

    private String mNeedsId;


    public DecorationDesignerListAdapter(Activity activity, List<DecorationBiddersBean> datas, String needs_id) {
        super(activity, datas, R.layout.item_decoration_designer_list);
        this.biddersEntities = (ArrayList<DecorationBiddersBean>) datas;
        mActivity = activity;
        mNeedsId = needs_id;
    }

    @Override
    public void convert(CommonViewHolder holder, DecorationBiddersBean bidder) {

        final String designerId = bidder.getDesigner_id();
        String bidderUid = bidder.getUid();
        String user_name = bidder.getUser_name();
        String avatarUrl = bidder.getAvatar();

        String wk_cur_sub_node_id = bidder.getWk_cur_sub_node_id();
        String wkSubNodeName = MPWkFlowManager.getWkSubNodeName(mActivity, null, wk_cur_sub_node_id);
        holder.setText(R.id.tv_decoration_mesure, wkSubNodeName);


        holder.setText(R.id.tv_designer_name, user_name);
        PolygonImageView polygonImageView = holder.getView(R.id.piv_consumer_order_photo);
        ImageUtils.displayAvatarImage(avatarUrl, polygonImageView);

        holder.setOnClickListener(R.id.rl_item_decoration, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mActivity, WkFlowStateActivity.class);
                intent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, mNeedsId);
                intent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, designerId);
                intent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_DECORATION);
                mActivity.startActivityForResult(intent, 0);
            }
        });

        holder.setOnClickListener(R.id.piv_consumer_order_photo, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mActivity, "设计师主页", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
