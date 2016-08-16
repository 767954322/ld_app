package com.autodesk.shejijia.consumer.personalcenter.consumer.adapter;

import android.app.Activity;
import android.view.View;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPWkFlowManager;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.DecorationBiddersBean;
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
 * @file ConsumerDecorationAdapter.java .
 * @brief 消费者家装订单设计师列表.
 */
public class DecorationDesignerListAdapter extends CommonAdapter<DecorationBiddersBean> implements
        View.OnClickListener {

    private Activity mActivity;
    private ArrayList<DecorationBiddersBean> biddersEntities;

    public DecorationDesignerListAdapter(Activity activity, List<DecorationBiddersBean> datas) {
        super(activity, datas, R.layout.item_lv_decoration);
        mActivity = activity;
        this.biddersEntities = (ArrayList<DecorationBiddersBean>) datas;
    }

    @Override
    public void convert(CommonViewHolder holder, DecorationBiddersBean bidder) {

        String designer_id = bidder.getDesigner_id();
        String bidderUid = bidder.getUid();
        String user_name = bidder.getUser_name();
        String avatarUrl = bidder.getAvatar();

        String wk_cur_sub_node_id = bidder.getWk_cur_sub_node_id();
        String wkSubNodeName = MPWkFlowManager.getWkSubNodeName(mActivity, null, wk_cur_sub_node_id);
        holder.setText(R.id.tv_decoration_mesure,wkSubNodeName);




        holder.setText(R.id.tv_designer_name, user_name);
        PolygonImageView polygonImageView = holder.getView(R.id.piv_consumer_order_photo);
        ImageUtils.displayAvatarImage(avatarUrl, polygonImageView);



    }

    @Override
    public void onClick(View v) {

    }
}
