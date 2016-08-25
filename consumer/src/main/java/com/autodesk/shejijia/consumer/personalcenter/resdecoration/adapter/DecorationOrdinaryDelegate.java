package com.autodesk.shejijia.consumer.personalcenter.resdecoration.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.DecorationNeedsListBean;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.activity.DecorationBidderActivity;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.activity.DecorationDetailActivity;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.entity.DecorationBiddersBean;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.listviewdelegate.ItemViewDelegate;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.listviewdelegate.MultiItemViewHolder;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.uielements.ListViewForScrollView;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * <p>Description:家装订单:普通家装订单 </p>
 *
 * @author liuhea
 * @date 16/8/16
 *  
 */
public class DecorationOrdinaryDelegate implements ItemViewDelegate<DecorationNeedsListBean> {

    ///is_beishu:0 北舒套餐 1 非北舒.
    private static final String IS_NOT_BEI_SHU = "1";
    /// 支付了设计首款的节点 .
    private static final int PAYED_FIRST_COST = 41;
    /**
     * is_public=1,表示终止了需求
     */
    private static final String IS_PUBLIC = "1";

    private Intent mIntent;
    private Activity mActivity;
    private Map<String, String> spaceMap;
    private Map<String, String> styleMap;

    public DecorationOrdinaryDelegate(Activity activity) {
        mActivity = activity;
        spaceMap = AppJsonFileReader.getSpace(activity);
        styleMap = AppJsonFileReader.getStyle(activity);
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_decoration_list;
    }

    @Override
    public boolean isForViewType(DecorationNeedsListBean needsListBean, int position) {
        return IS_NOT_BEI_SHU.equals(needsListBean.getIs_beishu());
    }

    @Override
    public void convert(MultiItemViewHolder holder, DecorationNeedsListBean decorationNeedsListBean, int position) {

        final ArrayList<DecorationBiddersBean> mBidders =
                (ArrayList<DecorationBiddersBean>) decorationNeedsListBean.getBidders();

        final String mNeeds_id = decorationNeedsListBean.getNeeds_id();
        String contacts_name = decorationNeedsListBean.getContacts_name();
        String community_name = decorationNeedsListBean.getCommunity_name();
        String province_name = decorationNeedsListBean.getProvince_name();
        String city_name = decorationNeedsListBean.getCity_name();
        String district_name = decorationNeedsListBean.getDistrict_name();
        String bidder_count = decorationNeedsListBean.getBidder_count();
        String is_public = decorationNeedsListBean.getIs_public();
        String wk_template_id = decorationNeedsListBean.getWk_template_id();
        String custom_string_status = decorationNeedsListBean.getCustom_string_status();

        holder.setText(R.id.tv_decoration_name, contacts_name + "/" + community_name);
        holder.setText(R.id.tv_decoration_needs_id, decorationNeedsListBean.getNeeds_id());

        String house_type = decorationNeedsListBean.getHouse_type();

        if (spaceMap.containsKey(house_type)) {
            holder.setText(R.id.tv_decoration_house_type, spaceMap.get(house_type));
        } else {
            holder.setText(R.id.tv_decoration_house_type, house_type);
        }


        holder.setText(R.id.tv_decoration_address, province_name + city_name + district_name);
        holder.setText(R.id.tv_decoration_phone, decorationNeedsListBean.getContacts_mobile());

        String decoration_style = decorationNeedsListBean.getDecoration_style();

        if (styleMap.containsKey(decoration_style)) {
            holder.setText(R.id.tv_decoration_style, styleMap.get(decoration_style));
        } else {
            holder.setText(R.id.tv_decoration_style, decoration_style);
        }
        holder.setText(R.id.tv_bidder_count, bidder_count + "人");
        holder.setText(R.id.tv_decoration_end_day, " " + decorationNeedsListBean.getEnd_day() + " 天");

        /**
         * 当前家装订单状态
         */
        Integer wk_cur_node_id_max = getWk_cur_sub_node_id_max(mBidders);
        String needsState = getNeedsState(is_public, wk_template_id, custom_string_status, wk_cur_node_id_max);
        holder.setText(R.id.tv_decoration_state, needsState);

        /**
         * 如果处于审核状态或者有人支付了设计首款，隐藏应标人数布局
         */
        boolean isBidding = isBiding(custom_string_status);
        if (IS_PUBLIC.equals(is_public)) {
            holder.setVisible(R.id.rl_bidder_count, false);
        } else {
            if (isBidding) {
                if (wk_cur_node_id_max >= PAYED_FIRST_COST) {
                    holder.setVisible(R.id.rl_bidder_count, false);
                } else {
                    holder.setVisible(R.id.rl_bidder_count, true);
                }
            } else {
                holder.setVisible(R.id.rl_bidder_count, false);
            }
        }


        /**
         * 应标设计师列表
         */
        ArrayList<DecorationBiddersBean> biddersShow = removeBidingDesigner(mBidders);
        ListViewForScrollView mDesignerListView = holder.getView(R.id.lv_decoration_bid);
        DecorationDesignerListAdapter mDecorationDesignerListAdapter = new DecorationDesignerListAdapter(mActivity, biddersShow, mNeeds_id);
        mDesignerListView.setAdapter(mDecorationDesignerListAdapter);

        /**
         * 应标人数详情页面
         *
         * 只有应标人数大于0时，才能进入应标人数详情页面
         */
        boolean isNumeric = StringUtils.isNumeric(bidder_count);
        if (isNumeric && Integer.valueOf(bidder_count) > 0) {
            holder.setOnClickListener(R.id.rl_bidder_count, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIntent = new Intent(mActivity, DecorationBidderActivity.class);
                    mIntent.putExtra(DecorationBidderActivity.BIDDER_KEY, mBidders);
                    mIntent.putExtra(Constant.ConsumerDecorationFragment.NEED_ID, mNeeds_id);
                    mActivity.startActivity(mIntent);
                }
            });
        } else {
            holder.setOnClickListener(R.id.rl_bidder_count, null);
        }

        /**
         * 需求详情页面
         */
        holder.setOnClickListener(R.id.tv_decoration_detail, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = new Intent(mActivity, DecorationDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.ConsumerDecorationFragment.NEED_ID, mNeeds_id);
                mIntent.putExtras(bundle);
                mActivity.startActivityForResult(mIntent, 0);
            }
        });
    }

    /**
     * 获取应标设计师中流程节点的最大值
     *
     * @param mBidders 应标设计师列表
     * @return wk_cur_node_id_max最大值
     */
    private Integer getWk_cur_sub_node_id_max(ArrayList<DecorationBiddersBean> mBidders) {
        Integer wk_cur_node_id_max = -1;
        if (mBidders != null && mBidders.size() > 0) {

            ArrayList<Integer> mWk_cur_node_id_array = new ArrayList<>();
            for (DecorationBiddersBean bidder : mBidders) {
                if (!TextUtils.isEmpty(bidder.getWk_cur_sub_node_id()) && StringUtils.isNumeric(bidder.getWk_cur_sub_node_id())) {
                    mWk_cur_node_id_array.add(Integer.parseInt(bidder.getWk_cur_sub_node_id()));
                }
            }
            if (mWk_cur_node_id_array.size() > 0) {
                wk_cur_node_id_max = Collections.max(mWk_cur_node_id_array);
            }
        }
        return wk_cur_node_id_max;
    }

    /**
     * 当前订单是否处于应标状态
     *
     * @param custom_string_status 审核状态判断字段
     * @return 已经进入订单的应标、设计、项目完成阶段，返回true
     */
    private boolean isBiding(String custom_string_status) {
        boolean isBiding;
        switch (custom_string_status) {
            case Constant.NumKey.THREE:
            case Constant.NumKey.ZERO_THREE:
                isBiding = true;
                break;
            default:
                isBiding = false;
                break;
        }
        return isBiding;
    }


    /**
     * 返回当前订单的状态信息
     *
     * @param is_public              当前订单是否终止,为1时,当前需求终止。
     * @param wk_template_id         模版节点，1-应标;2-自选;3-北舒
     * @param custom_string_status   审核状态判断字段
     * @param wk_cur_sub_node_id_int 当前应标设计师中wk_cur_sub_node_id的最大值
     * @return 节点状态
     */
    private String getNeedsState(String is_public, String wk_template_id, String custom_string_status, int wk_cur_sub_node_id_int) {
        String needsState = "未知状态";

        if (Constant.NumKey.ONE.equals(is_public)) {
            /**
             * is_public=1,需求终止
             */
            needsState = UIUtils.getString(R.string.canceled);
        } else {
            if (Constant.NumKey.ONE.equals(wk_template_id)) {
                /**
                 * 自选:wk_template_id=2,直接审核通过
                 * 应标:wk_template_id=1
                 */
                switch (custom_string_status) {
                    case Constant.NumKey.ONE:
                    case Constant.NumKey.ZERO_ONE:
                        /**
                         * 审核中,可以修改需求
                         */
                        needsState = UIUtils.getString(R.string.checking);
                        break;

                    case Constant.NumKey.TWO:
                    case Constant.NumKey.ZERO_TWO:
                        /**
                         * 审核失败,可以修改需求.
                         */
                        needsState = UIUtils.getString(R.string.disapprove);
                        break;

                    case Constant.NumKey.THREE:
                    case Constant.NumKey.ZERO_THREE:
                        /**
                         * 审核通过,但是没有设计师应标.??
                         */
                        needsState = "应标中";
                        break;
                }
            }

            if (wk_cur_sub_node_id_int >= 11) {
                if (wk_cur_sub_node_id_int < 41) {
                    needsState = "应标中";
                }
                if (wk_cur_sub_node_id_int >= 41) {
                    needsState = "设计中";
                }

                if (wk_cur_sub_node_id_int >= 63 && wk_cur_sub_node_id_int != 64) {
                    needsState = "项目完成";
                }

                if (wk_cur_sub_node_id_int == 64) {
                    needsState = "设计中";
                }
            }
        }
        return needsState;
    }

    /**
     * 移除设计师列表中处于应标中的设计师
     *
     * @param mBidders 应标列表结合
     * @return 筛选后的应标列表
     */
    private ArrayList<DecorationBiddersBean> removeBidingDesigner(ArrayList<DecorationBiddersBean> mBidders) {
        ArrayList<DecorationBiddersBean> biddersShow = new ArrayList<>();
        biddersShow.addAll(mBidders);

        if (biddersShow != null && biddersShow.size() > 0) {
            for (int i = biddersShow.size() - 1; i >= 0; i--) {
                /**
                 * 控制是否在当前ListView显示当前设计师
                 */
                if (!showCurrentDesigner(biddersShow.get(i).getWk_cur_sub_node_id())) {
                    biddersShow.remove(i);
                }
            }
        }
        return biddersShow;
    }

    /**
     * 是否已经选择了当前设计师量房
     *
     * @param wk_cur_sub_node_id 流程节点,如果为-1，审核通过，但是尚未选TA量房，处于应标状态
     * @return 如果已经选择该设计师应标，返回true
     */
    private boolean showCurrentDesigner(String wk_cur_sub_node_id) {
        if (StringUtils.isNumeric(wk_cur_sub_node_id) && Integer.valueOf(wk_cur_sub_node_id) > -1) {
            return true;
        } else {
            return false;
        }
    }
}
