package com.autodesk.shejijia.consumer.personalcenter.resdecoration.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.DecorationNeedsListBean;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.entity.DecorationBiddersBean;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.listviewdelegate.ItemViewDelegate;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.listviewdelegate.MultiItemViewHolder;
import com.autodesk.shejijia.shared.components.common.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;

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
    private static final String IS_NOT_BEI_SHU = "1";
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
        return !IS_NOT_BEI_SHU.equals(needsListBean.getIs_beishu());
    }

    @Override
    public void convert(MultiItemViewHolder holder, DecorationNeedsListBean decorationNeedsListBean, int position) {
        String community_name = decorationNeedsListBean.getCommunity_name();
        String needs_id = decorationNeedsListBean.getNeeds_id();
        String contacts_name = decorationNeedsListBean.getContacts_name();
        String contacts_mobile = decorationNeedsListBean.getContacts_mobile();

        String province_name = decorationNeedsListBean.getProvince_name();
        String city_name = decorationNeedsListBean.getCity_name();
        String district_name = TextUtils.isEmpty(decorationNeedsListBean.getDistrict_name()) ? "" : decorationNeedsListBean.getDistrict_name();


        holder.setText(R.id.tv_decoration_beishu_name, community_name);
        holder.setText(R.id.tv_decoration_beishu_needs_id, needs_id);
        holder.setText(R.id.tv_decoration_beishu_consumer_name, contacts_name);
        holder.setText(R.id.tv_decoration_beishu_phone, contacts_mobile);
        holder.setText(R.id.tv_decoration_beishu_address, province_name + city_name + district_name);
        holder.setText(R.id.tv_decoration_beishu_community_name, community_name);

        List<DecorationBiddersBean> bidders = decorationNeedsListBean.getBidders();
        if (null != bidders && bidders.size() > 0) {

            DecorationBiddersBean decorationBiddersBean = bidders.get(0);
            String avatar = decorationBiddersBean.getAvatar();
            String user_name = decorationBiddersBean.getUser_name();

            PolygonImageView polygonImageView = holder.getView(R.id.poly_designer_photo_beishu);
            ImageUtils.displayAvatarImage(avatar, polygonImageView);
            holder.setText(R.id.tv_designer_name_beishu, user_name);

            holder.setOnClickListener(R.id.img_decoration_beishu_chat, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mActivity, "聊天", Toast.LENGTH_SHORT).show();
                }
            });

            holder.setOnClickListener(R.id.poly_designer_photo_beishu, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mActivity, "设计师主页", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
