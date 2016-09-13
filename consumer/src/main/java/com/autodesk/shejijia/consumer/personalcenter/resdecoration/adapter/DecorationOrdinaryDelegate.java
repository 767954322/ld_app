package com.autodesk.shejijia.consumer.personalcenter.resdecoration.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.codecorationBase.coelite.activity.SelectDesignerActivity;
import com.autodesk.shejijia.consumer.manager.WkTemplateConstants;
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

    /// 支付了设计首款的节点 .
    private static final int PAYED_FIRST_COST = 41;
    /**
     * is_public=1,表示终止了需求
     */
    private static final String IS_PUBLIC = "1";

    private static final String NONE = "none";

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
        String wk_template_id = needsListBean.getWk_template_id();
        return (!StringUtils.isEmpty(wk_template_id)) && (!WkTemplateConstants.IS_BEISHU.equals(wk_template_id));
    }

    @Override
    public void convert(MultiItemViewHolder holder, final DecorationNeedsListBean decorationNeedsListBean, int position) {

        final ArrayList<DecorationBiddersBean> mBidders =
                (ArrayList<DecorationBiddersBean>) decorationNeedsListBean.getBidders();

        final String mNeeds_id = decorationNeedsListBean.getNeeds_id();
        String contacts_name = decorationNeedsListBean.getContacts_name();
        String community_name = decorationNeedsListBean.getCommunity_name();
        String province_name = decorationNeedsListBean.getProvince_name();
        String city_name = decorationNeedsListBean.getCity_name();
        String district_name = decorationNeedsListBean.getDistrict_name();
        String district = decorationNeedsListBean.getDistrict();
        String bidder_count = decorationNeedsListBean.getBidder_count();
        String is_public = decorationNeedsListBean.getIs_public();
        final String wk_template_id = decorationNeedsListBean.getWk_template_id();
        String custom_string_status = decorationNeedsListBean.getCustom_string_status();

        holder.setText(R.id.tv_decoration_name, UIUtils.getNoDataIfEmpty(contacts_name) + "/" + community_name);
        holder.setText(R.id.tv_decoration_needs_id, decorationNeedsListBean.getNeeds_id());

        String house_type = decorationNeedsListBean.getHouse_type();

        if (spaceMap.containsKey(house_type)) {
            holder.setText(R.id.tv_decoration_house_type, spaceMap.get(house_type));
        } else {
            holder.setText(R.id.tv_decoration_house_type, TextUtils.isEmpty(house_type) ? UIUtils.getString(R.string.no_select) : house_type);
        }

        district_name = TextUtils.isEmpty(district_name) || NONE.equals(district_name) || NONE.equals(district) || TextUtils.isEmpty(district) ? "" : district_name;
        String address = province_name + city_name + district_name;

        /// 项目地址
        if (TextUtils.isEmpty(city_name)) {
            holder.setText(R.id.tv_decoration_address, UIUtils.getString(R.string.no_select));
        } else {
            holder.setText(R.id.tv_decoration_address, address);
        }

        holder.setText(R.id.tv_decoration_address, province_name + city_name + district_name);
        holder.setText(R.id.tv_decoration_phone, decorationNeedsListBean.getContacts_mobile());

        String decoration_style = decorationNeedsListBean.getDecoration_style();

        if (styleMap.containsKey(decoration_style)) {
            holder.setText(R.id.tv_decoration_style, styleMap.get(decoration_style));
        } else {
            holder.setText(R.id.tv_decoration_style, TextUtils.isEmpty(decoration_style) ? UIUtils.getString(R.string.no_select) : decoration_style);

        }
        holder.setVisible(R.id.tv_show_persion_tip, true);
        holder.setTextColor(R.id.tv_bidder_count,
                Integer.parseInt(bidder_count) >= 1 ? UIUtils.getColor(R.color.comment_blue)
                        : UIUtils.getColor(R.color.mybid_text_color_light));
        holder.setText(R.id.tv_bidder_count, bidder_count);
        holder.setText(R.id.tv_decoration_end_day, " " + decorationNeedsListBean.getEnd_day());

        /**
         * 当前家装订单状态
         */
        Integer wk_cur_node_id_max = getWk_cur_sub_node_id_max(mBidders);
        String needsState = getNeedsState(is_public, wk_template_id, custom_string_status, wk_cur_node_id_max, decorationNeedsListBean.getNeeds_id());

        holder.setText(R.id.tv_decoration_state, needsState);

        /**
         * 如果是精选项目订单，则显示派单人数
         */
        if (WkTemplateConstants.IS_ELITE.equals(wk_template_id)) {
            if (mBidders.size() > 0) {
                holder.setVisible(R.id.rl_select_designer, true);
            } else {
                holder.setVisible(R.id.rl_select_designer, false);
            }
            holder.setVisible(R.id.rl_bidder_count, false);
        } else {
            /**
             * 如果处于审核状态或者有人支付了设计首款，隐藏应标人数布局
             */
            holder.setVisible(R.id.rl_select_designer, false);
            /**
             * 如果处于审核状态或者有人支付了设计首款，隐藏应标人数布局
             */
            boolean isBidding = isBiding(custom_string_status);
            if (IS_PUBLIC.equals(is_public)) {
                holder.setVisible(R.id.rl_bidder_count, false);
            } else {
                holder.setVisible(R.id.rl_bidder_count, true);
                if (isBidding) {
                    holder.setVisible(R.id.rl_bidder_count, true);
                } else {
                    holder.setVisible(R.id.rl_bidder_count, false);
                }
            }
        }
        /**
         * 应标设计师列表
         */
        ArrayList<DecorationBiddersBean> biddersShow = new ArrayList<>();
        if (WkTemplateConstants.IS_ELITE.equalsIgnoreCase(wk_template_id)) {
            biddersShow = removeUnSelectedDesigner(mBidders);
            holder.setTextColor(R.id.tv_send_num,
                    mBidders.size() >= 1 ? UIUtils.getColor(R.color.comment_blue)
                            : UIUtils.getColor(R.color.mybid_text_color_light));

            holder.setText(R.id.tv_send_num, mBidders.size() + "");
        } else {
            biddersShow = removeBidingDesigner(mBidders);
        }
        ListViewForScrollView mDesignerListView = holder.getView(R.id.lv_decoration_bid);
        DecorationDesignerListAdapter mDecorationDesignerListAdapter = new DecorationDesignerListAdapter(mActivity, biddersShow, mNeeds_id, wk_template_id);
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
                    mIntent.putExtra(DecorationBidderActivity.WK_TEMPLE_ID, wk_template_id);
                    mIntent.putExtra(Constant.ConsumerDecorationFragment.NEED_ID, mNeeds_id);
                    mActivity.startActivity(mIntent);
                }
            });
        } else {
            holder.setOnClickListener(R.id.rl_bidder_count, null);
        }

        holder.setOnClickListener(R.id.rl_select_designer, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, SelectDesignerActivity.class);
                intent.putExtra(Constant.ConsumerDecorationFragment.DECORATIONbIDDERBEAN, decorationNeedsListBean);
                mActivity.startActivityForResult(intent, 105);
            }
        });

        /**
         * 需求详情页面
         */
        holder.setOnClickListener(R.id.tv_decoration_detail, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = new Intent(mActivity, DecorationDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.ConsumerDecorationFragment.WK_TEMPLATE_ID, wk_template_id);
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
        boolean isBiding = false;
        if (!TextUtils.isEmpty(custom_string_status)) {
            switch (custom_string_status) {
                case Constant.NumKey.CERTIFIED_PASS:
                case Constant.NumKey.CERTIFIED_PASS_1:
                    isBiding = true;
                    break;
                default:
                    isBiding = false;
                    break;
            }
        }
        return isBiding;
    }


    /**
     * 返回当前订单的状态信息
     *
     * @param is_public              当前订单是否终止,为1时,当前需求终止。
     * @param wk_template_id         模版节点，1-应标;2-自选;3-北舒;4-精选
     * @param custom_string_status   审核状态判断字段
     * @param wk_cur_sub_node_id_int 当前应标设计师中wk_cur_sub_node_id的最大值
     * @return 节点状态
     */
    private String getNeedsState(String is_public, String wk_template_id, String custom_string_status,
                                 int wk_cur_sub_node_id_int, String needs_id) {
        String needsState = UIUtils.getString(R.string.error_state);
        switch (wk_template_id) {
            case WkTemplateConstants.IS_ELITE:
                needsState = getEliteNeedsState(wk_cur_sub_node_id_int, needsState, custom_string_status);
                return needsState;

            default:
                //下面是竟优或者时其他的逻辑
                if (IS_PUBLIC.equals(is_public)) {
                    /**
                     * is_public=1,需求终止
                     */
                    needsState = UIUtils.getString(R.string.project_stop);
                } else {
                    /**
                     * 自选:wk_template_id=2,直接审核通过
                     * 应标:wk_template_id=1
                     */
                    if (WkTemplateConstants.IS_AVERAGE.equals(wk_template_id)) {
                        switch (custom_string_status) {
                            case Constant.NumKey.CERTIFIED_CHECKING:
                            case Constant.NumKey.CERTIFIED_CHECKING_1:
                                /**
                                 * 审核中,可以修改需求
                                 */
                                needsState = UIUtils.getString(R.string.checking);
                                break;

                            case Constant.NumKey.CERTIFIED_FAILED:
                            case Constant.NumKey.CERTIFIED_FAILED_1:
                                /**
                                 * 审核失败,可以修改需求.
                                 */
                                needsState = UIUtils.getString(R.string.disapprove);
                                break;

                            case Constant.NumKey.CERTIFIED_PASS:
                            case Constant.NumKey.CERTIFIED_PASS_1:
                                /**
                                 * 审核通过,但是没有设计师应标.
                                 */
                                needsState = UIUtils.getString(R.string.project_biding);
                                break;
                        }
                    }

                    if (wk_cur_sub_node_id_int >= WkTemplateConstants.INVITE_MEASURE) {
                        if (wk_cur_sub_node_id_int < WkTemplateConstants.PAY_FOR_FIRST_FEE) {
                            needsState = UIUtils.getString(R.string.project_biding);
                        }
                        if (wk_cur_sub_node_id_int >= WkTemplateConstants.PAY_FOR_FIRST_FEE) {
                            needsState = UIUtils.getString(R.string.project_designing);
                        }

                        if (wk_cur_sub_node_id_int >= WkTemplateConstants.CONFIRM_DESIGN_RESULTS
                                && wk_cur_sub_node_id_int != WkTemplateConstants.DELAY_CONFIRM_DESIGN_RESULTS) {
                            needsState = UIUtils.getString(R.string.project_finish);
                        }

                        if (wk_cur_sub_node_id_int == WkTemplateConstants.DELAY_CONFIRM_DESIGN_RESULTS) {
                            needsState = UIUtils.getString(R.string.project_designing);
                        }
                    }
                }
        }
        return needsState;
    }

    /**
     * 获取精选状态信息
     *
     * @param needsState
     * @param custom_string_status
     * @return
     */
    private String getEliteNeedsState(int wk_cur_sub_node_id_int, String needsState, String custom_string_status) {
        needsState = UIUtils.getString(R.string.checking);
        if (wk_cur_sub_node_id_int >= WkTemplateConstants.CONFIRM_DESIGN_RESULTS
                && wk_cur_sub_node_id_int != WkTemplateConstants.DELAY_CONFIRM_DESIGN_RESULTS) {
            needsState = UIUtils.getString(R.string.project_finish);
            return needsState;
        }
        switch (custom_string_status) {
            case Constant.NumKey.TWENTY_ONE://21:审核中
                needsState = UIUtils.getString(R.string.checking);
                break;
            case Constant.NumKey.TWENTY_TWO://22:审核未通过
                needsState = UIUtils.getString(R.string.disapprove);
                break;
            case Constant.NumKey.TWENTY_THREE://23:审核通过(设计中)
                needsState = UIUtils.getString(R.string.designing);
                break;
            default:
                break;
        }

        return needsState;
    }


    /**
     * 移除未被选中的设计师
     *
     * @param mBidders 应标列表结合
     * @return
     */
    private ArrayList<DecorationBiddersBean> removeUnSelectedDesigner(ArrayList<DecorationBiddersBean> mBidders) {
        ArrayList<DecorationBiddersBean> selectedDesigner = new ArrayList<>();
        for (DecorationBiddersBean decorationBiddersBean : mBidders) {
            String wk_cur_sub_node_id = decorationBiddersBean.getWk_cur_sub_node_id();
            int i = Integer.parseInt(wk_cur_sub_node_id != null ? wk_cur_sub_node_id : "-1");
            if (i >= 11) {
                selectedDesigner.add(decorationBiddersBean);
            }
            // Merge feature-find-designer-4.1
            //for (DecorationBiddersBean decorationBiddersBean : mBidders) {
            //  selectedDesigner.add(decorationBiddersBean);
            //}
        }
        return selectedDesigner;
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
