package com.autodesk.shejijia.consumer.personalcenter.consumer.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.activity.SeekDesignerDetailActivity;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.manager.MPWkFlowManager;
import com.autodesk.shejijia.consumer.manager.constants.JsonConstants;
import com.autodesk.shejijia.consumer.personalcenter.consumer.activity.AmendDemandActivity;
import com.autodesk.shejijia.consumer.personalcenter.consumer.activity.AppraiseDesignerActivity;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.AmendDemandBean;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.DecorationBiddersBean;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.DecorationNeedsListBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.activity.FlowMeasureFormActivity;
import com.autodesk.shejijia.consumer.personalcenter.workflow.activity.FlowUploadDeliveryActivity;
import com.autodesk.shejijia.consumer.personalcenter.workflow.activity.WkFlowStateActivity;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPBidderBean;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.appglobal.UrlMessagesContants;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.ListViewForScrollView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.utility.ConvertUtils;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.im.activity.ChatRoomActivity;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.adapter.CommonAdapter;
import com.autodesk.shejijia.shared.framework.adapter.CommonViewHolder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * @author he.liu .
 * @version 1.0 .
 * @date 16-6-7 上午11:23
 * @file DecorationFragment.java  .
 * @brief 消费者：我的装修项目中的普通订单展示.
 */
public class DecorationFragment extends Fragment implements View.OnClickListener, OnItemClickListener {

    public DecorationFragment() {
        super();
    }

    /**
     * 得到当前普通订单，方便DecorationActivity调用
     */

    public static final Fragment getInstance(DecorationNeedsListBean NeedsListBean) {
        Fragment fragment = new DecorationFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.DecorationBundleKey.DECORATION_NEEDS_KEY, NeedsListBean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutResId(), container, false);
        initView();
        initListener();
        initData();
        return mRootView;
    }

    private int getLayoutResId() {
        return R.layout.pager_consumer_decoration;
    }

    private void initView() {
        mLlItemConsumerDecoration = (LinearLayout) mRootView.findViewById(R.id.ll_item_consumer_decoration);
        mListView = (ListViewForScrollView) mRootView.findViewById(R.id.lv_decoration_bid);
        mScrollView = (ScrollView) mRootView.findViewById(R.id.scrollView_decoration);
        mTvCommunityName = (TextView) mRootView.findViewById(R.id.tv_consumer_decoration_tag);
        mTvBidState = (TextView) mRootView.findViewById(R.id.tv_consumer_decoration_state);
        mTvNeeds_id = (TextView) mRootView.findViewById(R.id.tv_consumer_decoration_needs_id);
        mTVHomeAddress = (TextView) mRootView.findViewById(R.id.tv_consumer_address);
        mTvBidderCount = (TextView) mRootView.findViewById(R.id.tv_decoration_designer_count);
        mTvProjectAddress = (TextView) mRootView.findViewById(R.id.tv_issue_address);
        mTvDemandPrice = (TextView) mRootView.findViewById(R.id.tv_decoration_issue_demand_price);
        mTvDesignBudget = (TextView) mRootView.findViewById(R.id.tv_decoration_design_budget);
        mTvEndDay = (TextView) mRootView.findViewById(R.id.tv_decoration_endday);
        mTvStyle = (TextView) mRootView.findViewById(R.id.tv_issue_style);
        mTvHouseType = (TextView) mRootView.findViewById(R.id.tv_consumer_decoration_house_type);
        mTvBuildTime = (TextView) mRootView.findViewById(R.id.consumer_decoration_buildtime);
        mIbnDecorationShow = (ImageButton) mRootView.findViewById(R.id.ibn_decoration_show);
        mIbnDecorationModify = (ImageButton) mRootView.findViewById(R.id.ibn_decoration_modify);
        /**
         * 立即评价
         */
        mLlEvaluate = (LinearLayout) mRootView.findViewById(R.id.ll_evaluate_tip);
        mTvEvaluate = (TextView) mRootView.findViewById(R.id.tv_appraise_designer);
    }

    private void initData() {
        mScrollView.smoothScrollTo(0, 0);
        EventBus.getDefault().registerSticky(this);
        if (mNeedsListEntity == null) {
            return;
        }

        bidders = (ArrayList<DecorationBiddersBean>) mNeedsListEntity.getBidders();
        wk_template_id = mNeedsListEntity.getWk_template_id();

        getJsonFileReader();
        getDataFromNeed();
        convertEn2Cn();

        if (IS_PUBLIC.equals(is_public)) {
            mIbnDecorationModify.setVisibility(View.GONE);
            mIbnDecorationModify.setOnClickListener(null);
        }
        updateViewFromData();
        initAlertView();
    }

    private void initListener() {
        mIbnDecorationShow.setOnClickListener(this);
        mTvEvaluate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibn_decoration_show:  ///查看家装订单详情 .
                if (mLlItemConsumerDecoration.getVisibility() == View.VISIBLE) {// true ,current show
                    mLlItemConsumerDecoration.setVisibility(View.GONE);
                } else {
                    /// false ,current hide .
                    mLlItemConsumerDecoration.setVisibility(View.VISIBLE);
                }
                isShowStub = !isShowStub;
                if (isShowStub) {
                    mIbnDecorationShow.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_close));
                } else {
                    mIbnDecorationShow.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_unfold));
                }
                break;

            case R.id.ibn_decoration_modify: /// 修改需求 .
                Intent intent = new Intent(getActivity(), AmendDemandActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.ConsumerDecorationFragment.NEED_ID, needs_id);
                intent.putExtras(bundle);
                this.startActivityForResult(intent, RequestCode);
                break;

            case R.id.tv_appraise_designer: /// 立即评价 .
                Intent evaluateIntent = new Intent(getActivity(), AppraiseDesignerActivity.class);
                evaluateIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, needs_id);
                evaluateIntent.putExtra(FlowUploadDeliveryActivity.BIDDER_ENTITY, mMPBidderBean);
                evaluateIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, designer_id_evaluate);
                startActivityForResult(evaluateIntent, EVALUATE_STATE);
                break;
        }
    }

    /**
     * 弹出应标中的dialog
     */
    @Override
    public void onItemClick(Object obj, int position) {
        if (obj == mAlertViewRefuse && position != AlertView.CANCELPOSITION) {
            /**
             * 拒绝量房
             */
            if (mMemberEntity != null) {
                CustomProgress.show(getActivity(), "", false, null);
                refuseDesignerMeasure(needs_id, designer_id_biding);
            }
        }
    }

    /**
     * 应标设计师列表适配器
     */
    private class MyDecorationAdapter extends CommonAdapter<DecorationBiddersBean> {
        ArrayList<DecorationBiddersBean> biddersEntities;

        public MyDecorationAdapter(Context context, ArrayList<DecorationBiddersBean> biddersEntities, int layoutId) {
            super(context, biddersEntities, layoutId);
            this.biddersEntities = biddersEntities;
        }


        public void convert(final CommonViewHolder holder, DecorationBiddersBean bidder) {

            final String designer_id = bidder.getDesigner_id();
            final String bidderUid = bidder.getUid();
            String design_fee_max = bidder.getDesign_price_max();
            String design_fee_min = bidder.getDesign_price_min();

            design_fee_max = TextUtils.isEmpty(design_fee_max) ? "" : design_fee_max;
            design_fee_min = TextUtils.isEmpty(design_fee_min) ? "" : design_fee_min;
            design_fee = design_fee_min + "-" + design_fee_max;
            /**
             * 设计师名字
             */
            final String user_name = bidder.getUser_name();
            if (TextUtils.isEmpty(user_name)) {
                holder.setText(R.id.tv_designer_name, "");
            } else {
                holder.setText(R.id.tv_designer_name, user_name);
            }

            /**
             * 显示头像
             */
            PolygonImageView polygonImageView = holder.getView(R.id.piv_consumer_order_photo);
            final String url = bidder.getAvatar();
            ImageUtils.displayAvatarImage(url, polygonImageView);
            polygonImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, SeekDesignerDetailActivity.class);
                    intent.putExtra(Constant.ConsumerDecorationFragment.designer_id, designer_id);
                    intent.putExtra(Constant.ConsumerDecorationFragment.hs_uid, bidderUid);
                    startActivity(intent);
                }
            });
            /**
             *  全流程状态 .
             */
            final String wk_cur_sub_node_id = bidder.getWk_cur_sub_node_id();
            String wkSubNodeName = MPWkFlowManager.getWkSubNodeName(getActivity(), wk_template_id, wk_cur_sub_node_id);
            holder.setText(R.id.tv_decoration_mesure, wkSubNodeName);

            /**
             * 审核通过，但是在应标中，可以修改需求.
             */
            if (!TextUtils.isEmpty(is_public)) {
                setBidState(is_public);
            }
            final String style_names = bidder.getStyle_names();
            RelativeLayout rl_item_decoration = holder.getView(R.id.rl_item_decoration);

            /**
             * 设计师的designer_id
             */
            if (TextUtils.isEmpty(design_budget)) {
                return;
            }
            rl_item_decoration.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          mPosition = holder.getPosition();
                                                          ///根据应标状态，跳转相应的页面 .
                                                          selectNodeId(wk_cur_sub_node_id, user_name, style_names, url, designer_id, design_fee, bidderUid);
                                                      }
                                                  }
            );
        }

        /**
         * 点击设计师条目,弹出页面或者执行全流程操作
         *
         * @param wk_cur_sub_node_id 当前节点
         * @param user_name          设计师名字
         * @param style_names        设计风格
         * @param avatarUrl          头像的地址
         * @param designer_id        设计师id
         * @param designer_Fee       设计费
         * @param bidderUid          设计师的uid
         */
        private void selectNodeId(String wk_cur_sub_node_id, final String user_name, String style_names, String avatarUrl, String designer_id, String designer_Fee, String bidderUid) {
            switch (wk_cur_sub_node_id) {
                case Constant.NumKey._ONE:
                    bidingDialog(user_name, style_names, avatarUrl, designer_id, designer_Fee, bidderUid);
                    break;

                default:
                    if (!TextUtils.isEmpty(designer_id)) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), WkFlowStateActivity.class);
                        intent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, needs_id);
                        intent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, designer_id);
                        intent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_DECORATION);
                        startActivityForResult(intent, WK_FLOW_STATE);
                    }
                    break;
            }
        }

        /**
         * 点击应标中,弹出的页面
         */
        private void bidingDialog(String user_name, String style_names, String avatarUrl, final String designer_idd, String designFee, final String bidderUid) {
            final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            View window = View.inflate(getActivity(), R.layout.view_dialog_decoration_choose, null);
            /// initView .
            TextView tv_decoration_name_choose = (TextView) window.findViewById(R.id.tv_decoration_name_choose);
            TextView tv_decoration_designer_expens = (TextView) window.findViewById(R.id.tv_decoration_designer_expens);
            ImageView img_dialog_certification = (ImageView) window.findViewById(R.id.img_dialog_certification);
            PolygonImageView polygonImageView = (PolygonImageView) window.findViewById(R.id.ployimg_decoration_choose);
            Button ibn_decoration_measure_choose = (Button) window.findViewById(R.id.ibn_decoration_measure_choose);
            Button ibn_decoration_chat_choose = (Button) window.findViewById(R.id.ibn_decoration_chat_choose);
            Button ibn_decoration_refuse_choose = (Button) window.findViewById(R.id.ibn_decoration_refuse_choose);
            ImageView decoration_cancel = (ImageView) window.findViewById(R.id.decoration_cancel);
            TextView tv_decoration_designer_introduce = (TextView) window.findViewById(R.id.tv_decoration_designer_introduce);
            designer_id_biding = designer_idd;
            user_name_biding = user_name;
            style_names_biding = style_names;
            design_fee = designFee;
            if (null != mMemberEntity) {
                String member_type = mMemberEntity.getMember_type();
                if (member_type.equals((Constant.UerInfoKey.CONSUMER_TYPE))) {
                    img_dialog_certification.setVisibility(View.VISIBLE);
                } else {
                    img_dialog_certification.setVisibility(View.GONE);
                }
            }
            /**
             * 设计师描述
             */
            if (TextUtils.isEmpty(user_name_biding)) {
                user_name_biding = UIUtils.getString(R.string.no_data);
            }
            if (TextUtils.isEmpty(style_names_biding)) {
                style_names_biding = UIUtils.getString(R.string.has_yet_to_fill_out);
            }
            if (TextUtils.isEmpty(design_fee) || "null".equals(design_fee)) {
                design_fee = "0.00";
                tv_decoration_designer_expens.setText(UIUtils.getString(R.string.measurement_fee) + UIUtils.getString(R.string.has_yet_to_fill_out));
            } else {
                tv_decoration_designer_expens.setText(UIUtils.getString(R.string.measurement_fee) + design_fee + UIUtils.getString(R.string.measurement));
            }
            tv_decoration_name_choose.setText(user_name_biding);
            tv_decoration_designer_introduce.setText(UIUtils.getNodataIfEmpty(style_names_biding));

            /**
             * IM
             */
            ibn_decoration_chat_choose.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
                    String member_id = memberEntity.getAcs_member_id();
                    String memType = memberEntity.getMember_type();
                    String designer_thread_id = mNeedsListEntity.getBidders().get(mPosition).getDesign_thread_id();
                    String userName = mNeedsListEntity.getConsumer_name();
                    String needs_id = mNeedsListEntity.getNeeds_id() + "";

                    if (TextUtils.isEmpty(designer_thread_id)) {
                        designer_thread_id = "";
                    }

                    Intent intent = new Intent(getActivity(), ChatRoomActivity.class);
                    intent.putExtra(ChatRoomActivity.THREAD_ID, designer_thread_id);
                    intent.putExtra(ChatRoomActivity.RECIEVER_USER_ID, designer_idd);
                    intent.putExtra(ChatRoomActivity.RECIEVER_USER_NAME, userName);
                    intent.putExtra(ChatRoomActivity.ASSET_ID, needs_id);
                    intent.putExtra(ChatRoomActivity.ACS_MEMBER_ID, member_id);
                    intent.putExtra(ChatRoomActivity.MEMBER_TYPE, memType);
                    intent.putExtra(ChatRoomActivity.MEDIA_TYPE, UrlMessagesContants.mediaIdProject);
                    getActivity().startActivity(intent);
                }

            });

            /**
             *
             * 选TA量房
             */
            ibn_decoration_measure_choose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    if (!TextUtils.isEmpty(needs_id) && !TextUtils.isEmpty(designer_id_biding)) {
                        Intent intent = new Intent(getActivity(), FlowMeasureFormActivity.class);
                        intent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, needs_id);
                        intent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, designer_id_biding);
                        /// 从这个页面进入，量房时间为空，必须重新选择量房时间 .
                        intent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_DECORATION);
                        startActivityForResult(intent, BIDING_STATE);
                    }
                }
            });
            /**
             *
             * 设计师详情
             */
            polygonImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, SeekDesignerDetailActivity.class);
                    intent.putExtra(Constant.ConsumerDecorationFragment.designer_id, designer_idd);
                    intent.putExtra(Constant.ConsumerDecorationFragment.hs_uid, bidderUid);
                    startActivity(intent);
                    alertDialog.dismiss();
                }
            });

            /**
             * 拒绝
             */
            ibn_decoration_refuse_choose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    mAlertViewRefuse.show();
                }
            });

            decoration_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

            ImageUtils.displayAvatarImage(avatarUrl, polygonImageView);
            /// alertDialog.setCanceledOnTouchOutside(false);/// don't dismiss the dialog .
            alertDialog.setView(window, 0, 0, 0, 0);
            alertDialog.show();
        }
    }

    /**
     * 消费者拒绝设计师量房
     */
    public void refuseDesignerMeasure(String needs_id, String designer_id) {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (mPosition != -1) {
                    bidders.remove(mPosition);
                    if (StringUtils.isNumeric(bidder_count)) {
                        int s = Integer.valueOf(bidder_count);
                        mTvBidderCount.setText(s - 1 + UIUtils.getString(R.string.designer_much));/// bidder_count .
                    }
                }
                mDecorationAdapter.notifyDataSetChanged();
                CustomProgress.cancelDialog();
                String userInfo = GsonUtil.jsonToString(jsonObject);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, getActivity(),
                        AlertView.Style.Alert, null).show();
            }
        };
        MPServerHttpManager.getInstance().refuseDesignerMeasure(needs_id, designer_id, okResponseCallback);
    }

    /**
     * 判断家装订单状态
     * [1]审核中：修改显示，可以修改
     * [2]审核通过：重新提交按钮不可点击， 有人应标，修改按钮隐藏
     * wk_template_id：
     * 1		应标
     * 2		自选
     * 3		北舒
     */
    private void setBidState(String is_public) {
        String approveState = "";
        custom_string_status = mNeedsListEntity.getCustom_string_status();

        if (TextUtils.isEmpty(is_public)) {
            return;
        }

        if (Constant.NumKey.ONE.equals(is_public)) {
            /**
             * is_public=1,隐藏修改需求
             */
            approveState = UIUtils.getString(R.string.canceled);
            mTvEndDay.setText(UIUtils.getString(R.string.end_day));
            mIbnDecorationModify.setVisibility(View.GONE);
            mIbnDecorationModify.setClickable(false);

        } else {
            if (Constant.NumKey.TWO.equals(wk_template_id)) {
                /**
                 * 自选，显示审核通过，隐藏修改需求.
                 */
                approveState = UIUtils.getString(R.string.approve);
                mIbnDecorationModify.setVisibility(View.GONE);
                mIbnDecorationModify.setClickable(false);

            } else if (Constant.NumKey.ONE.equals(wk_template_id)) {
                /**
                 * 应标
                 */
                if (Constant.NumKey.ONE.equals(custom_string_status)
                        || Constant.NumKey.ZERO_ONE.equals(custom_string_status)) {
                    /**
                     * 审核中,可以修改需求
                     */
                    approveState = UIUtils.getString(R.string.checking);
                    mIbnDecorationModify.setVisibility(View.VISIBLE);
                    mIbnDecorationModify.setOnClickListener(this);
                } else if (Constant.NumKey.TWO.equals(custom_string_status)
                        || Constant.NumKey.ZERO_TWO.equals(custom_string_status)) {
                    /**
                     * 审核未通过,可以修改需求表单.
                     */
                    approveState = UIUtils.getString(R.string.disapprove);
                    mIbnDecorationModify.setVisibility(View.VISIBLE);
                    mIbnDecorationModify.setOnClickListener(this);

                } else if (Constant.NumKey.THREE.equals(custom_string_status)
                        || Constant.NumKey.ZERO_THREE.equals(custom_string_status)) {
                    /**
                     * 审核通过,但是有设计师应标，隐藏修改需求.
                     */
                    approveState = "应标中";
                    mIbnDecorationModify.setVisibility(View.VISIBLE);
                    mIbnDecorationModify.setClickable(true);
                }
            }
        }
        if (bidders != null && bidders.size() > 0) {

            ArrayList<Integer> mWk_cur_node_id_array = new ArrayList<>();
            for (DecorationBiddersBean bidder : bidders) {
                if (!TextUtils.isEmpty(bidder.getWk_cur_sub_node_id()) && StringUtils.isNumeric(bidder.getWk_cur_sub_node_id())) {
                    mWk_cur_node_id_array.add(Integer.parseInt(bidder.getWk_cur_sub_node_id()));
                }
            }
            if (mWk_cur_node_id_array.size() > 0) {
                Integer max = Collections.max(mWk_cur_node_id_array);
                if (max < 41) {
                    approveState = "应标中";
                }
                if (max >= 41) {
                    approveState = "设计中";
                }

                if (max >= 63 && max != 64) {
                    approveState = "项目完成";
                }

                if (max == 64) {
                    approveState = "设计中";
                }

                if (max == 63) {

                    mLlEvaluate.setVisibility(View.VISIBLE);
                    for (DecorationBiddersBean biddersBean : bidders) {
                        if ((max + "").equals(biddersBean.getWk_cur_sub_node_id())) {
                            designer_id_evaluate = biddersBean.getDesigner_id();
                            mMPBidderBean.setAvatar(biddersBean.getAvatar());
                            mMPBidderBean.setUser_name(biddersBean.getUser_name());
                            mMPBidderBean.setDesigner_id(biddersBean.getDesigner_id());
                        }
                    }
                    if (is_evaluated) {
                        mLlEvaluate.setVisibility(View.GONE);
                    }
                } else {
                    mLlEvaluate.setVisibility(View.GONE);
                }
                mIbnDecorationModify.setVisibility(View.GONE);
                mIbnDecorationModify.setClickable(false);
            }
        } else if (bidders == null || bidders.size() < 1) {
            mIbnDecorationModify.setVisibility(View.VISIBLE);
            mIbnDecorationModify.setOnClickListener(this);
        }
        if (Constant.NumKey.ONE.equals(is_public)) {
            /**
             * is_public=1,隐藏修改需求
             */
            mTvEndDay.setText(UIUtils.getString(R.string.end_day));
            mIbnDecorationModify.setVisibility(View.GONE);
        }
        mTvBidState.setText(approveState);
    }

    /**
     * 判断是否终止了当前家装订单
     *
     * @param is_public 值为１，隐藏修改需求
     */
    public void onEvent(String is_public) {
        if (TextUtils.isEmpty(is_public)) {
            return;
        }
        if (mNeedsListEntity != null) {
            mNeedsListEntity.setIs_beishu(is_public);
            setBidState(is_public);
        }
    }

//    /**
//     * 修改需求回显的数据
//     *
//     * @param amendDemandBean 　回显数据的实体类
//     */
    public void echoData(AmendDemandBean amendDemandBean) {
        if (null == amendDemandBean) {
            return;
        }

        String design_budget = amendDemandBean.getDesign_budget();
        String community_name = amendDemandBean.getCommunity_name();
        String province_name = amendDemandBean.getProvince_name();
        String city_name = amendDemandBean.getCity_name();
        String district_name = amendDemandBean.getDistrict_name();
        String house_area = amendDemandBean.getHouse_area();
        String decoration_budget = amendDemandBean.getDecoration_budget();
        district_name = TextUtils.isEmpty(district_name) || district_name.equals(NONE) ? "" : district_name;
        String simple_address = city_name + district_name;
        String address = province_name + city_name + district_name;
        living_room = amendDemandBean.getLiving_room();
        room = amendDemandBean.getRoom();
        toilet = amendDemandBean.getToilet();
        decoration_style = amendDemandBean.getDecoration_style();
        house_type = amendDemandBean.getHouse_type();
        custom_string_status = amendDemandBean.getCustom_string_status() + "";
        if (Constant.NumKey.ONE.equals(custom_string_status) || Constant.NumKey.ZERO_ONE.equals(custom_string_status)) {
            /**
             * 审核中,可以修改需求
             */
            mTvBidState.setText(UIUtils.getString(R.string.checking));
        }
        convertEn2Cn();

        livingRoom_room_toilet = UIUtils.getNodataIfEmpty(room_convert) + UIUtils.getNodataIfEmpty(living_room_convert) + UIUtils.getNodataIfEmpty(toilet_convert);
        mTvCommunityName.setText(community_name);
        mTVHomeAddress.setText(simple_address + " " + livingRoom_room_toilet + "  " + house_area + "㎡");
        mTvProjectAddress.setText(address);
        mTvHouseType.setText(house_type_convert);
        mTvDemandPrice.setText(UIUtils.getNodataIfEmpty(decoration_budget));
        mTvDesignBudget.setText(UIUtils.getNodataIfEmpty(UIUtils.getNodataIfEmpty(design_budget)));
        mTvStyle.setText(UIUtils.getNodataIfEmpty(decoration_style_convert));
    }

    /**
     * 使用获取的数据，更新页面
     */
    private void updateViewFromData() {
        district_name = TextUtils.isEmpty(district_name) || NONE.equals(district_name) || NONE.equals(mDistrict) || TextUtils.isEmpty(mDistrict) ? "" : district_name;
        String simple_address = /*province_name + */ UIUtils.getNodataIfEmpty(city_name) + district_name;
        /// 项目地址
        String project_address = province_name + UIUtils.getNodataIfEmpty(city_name) + district_name;
        //户型
        livingRoom_room_toilet = UIUtils.getNodataIfEmpty(room_convert) + UIUtils.getNodataIfEmpty(living_room_convert) + UIUtils.getNodataIfEmpty(toilet_convert);
        ///小区名称 .
        mTvCommunityName.setText(UIUtils.getNodataIfEmpty(community_name));
        //项目编号
        mTvNeeds_id.setText(needs_id);
        /// 房屋地址及室卫厅 .
        mTVHomeAddress.setText(simple_address + " " + livingRoom_room_toilet + " " + UIUtils.getNodataIfEmpty(house_area) + "㎡");
        mTvHouseType.setText(house_type_convert);
        mTvBidderCount.setText(bidder_count + UIUtils.getString(R.string.designer_much));/// bidder_count .
        mTvProjectAddress.setText(project_address);
        mTvDemandPrice.setText(UIUtils.getNodataIfEmpty(this.decoration_budget));
        mTvDesignBudget.setText(UIUtils.getNodataIfEmpty(UIUtils.getNodataIfEmpty(design_budget)));
        mTvEndDay.setText(end_day + UIUtils.getString(R.string.day_much));
        mTvDemandPrice.setText(decoration_budget);
        mTvStyle.setText(UIUtils.getNodataIfEmpty(decoration_style_convert));
        mTvBuildTime.setText(publish_time);

        refreshListView();
        is_public = mNeedsListEntity.getIs_public();
        setBidState(is_public);
    }

    /**
     * 刷新当前设计师列表状态
     */
    private void refreshListView() {
        if (bidders != null && bidders.size() > 0) {
            mDecorationAdapter = new MyDecorationAdapter(getActivity(), bidders, R.layout.item_lv_decoration);
            mListView.setAdapter(mDecorationAdapter);
        }
    }

    /**
     * 获取的当前订单相应字段中值
     */
    private void getDataFromNeed() {
        needs_id = mNeedsListEntity.getNeeds_id();
        bidder_count = mNeedsListEntity.getBidder_count();
        community_name = mNeedsListEntity.getCommunity_name();
        end_day = mNeedsListEntity.getEnd_day();
        //装修预算
        decoration_budget = mNeedsListEntity.getDecoration_budget();
        design_budget = mNeedsListEntity.getDesign_budget();
        publish_time = mNeedsListEntity.getPublish_time();
        is_public = mNeedsListEntity.getIs_public();
        /// 房屋面积 .
        house_area = mNeedsListEntity.getHouse_area();
        /// 房屋类型.
        house_type = mNeedsListEntity.getHouse_type();
        /// 风格 .
        decoration_style = mNeedsListEntity.getDecoration_style();
        room = mNeedsListEntity.getRoom();
        living_room = mNeedsListEntity.getLiving_room();
        toilet = mNeedsListEntity.getToilet();
        province_name = mNeedsListEntity.getProvince_name();
        city_name = mNeedsListEntity.getCity_name();
        district_name = mNeedsListEntity.getDistrict_name();
        mDistrict = mNeedsListEntity.getDistrict();
    }

    /**
     * 读取室卫厅转化json对象
     */
    private void getJsonFileReader() {
        roomJson = AppJsonFileReader.getRoomHall(getActivity());
        toiletJson = AppJsonFileReader.getToilet(getActivity());
        livingRoomJson = AppJsonFileReader.getLivingRoom(getActivity());
        style = AppJsonFileReader.getStyle(getActivity());
        houseJson = AppJsonFileReader.getSpace(getActivity());
    }

    /**
     * 将英文转换为汉字
     */
    private void convertEn2Cn() {
        room_convert = ConvertUtils.getConvert2CN(roomJson, room);
        living_room_convert = ConvertUtils.getConvert2CN(livingRoomJson, living_room);
        toilet_convert = ConvertUtils.getConvert2CN(toiletJson, toilet);
        house_type_convert = ConvertUtils.getConvert2CN(houseJson, house_type);
        decoration_style_convert = ConvertUtils.getConvert2CN(style, decoration_style);
    }

    /**
     * 初始化AlertView
     */
    private void initAlertView() {
        mAlertViewRefuse = new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.select_Ta_refuse), UIUtils.getString(R.string.cancel), new String[]{UIUtils.getString(R.string.sure)}, null, getActivity(), AlertView.Style.Alert, this).setCancelable(false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        mNeedsListEntity = (DecorationNeedsListBean) getArguments().getSerializable(Constant.DecorationBundleKey.DECORATION_NEEDS_KEY);
        if (mNeedsListEntity == null) {
            return;
        }
        isShowStub = false;
        mMemberEntity = AdskApplication.getInstance().getMemberEntity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    /// 接收反回来的数据.

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null){
            return;
        }
        if(resultCode == AmendDemandActivity.ResultCode){
            Bundle bundle = data.getExtras();
            AmendDemandBean amendDemandBean = (AmendDemandBean) bundle.getSerializable(JsonConstants.AMENDEMANDBEAN);
            if(mNeedsListEntity.getNeeds_id().equals(amendDemandBean.getNeeds_id()) ){

                changeBean(amendDemandBean);
            }
            echoData(amendDemandBean);

        }


        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case WK_FLOW_STATE:
                case BIDING_STATE:
                    String mwk_cur_sub_node_id = data.getStringExtra(Constant.BundleKey.BUNDLE_SUB_NODE_ID);
                    if (!TextUtils.isEmpty(mwk_cur_sub_node_id)) {
                        bidders.get(mPosition).setWk_cur_sub_node_id(mwk_cur_sub_node_id);
                    }
                    boolean rmTag = false;

                    for (DecorationBiddersBean biddersEntity : bidders) {
                        /// 如果有人支付了首款，就把没有支付量房费的应标者从当前列表中删除 .
                        String wk_cur_sub_node_id = biddersEntity.getWk_cur_sub_node_id();
                        // wk_cur_sub_node_id
                        // 11	invite_measure	邀请量房
                        // 12	decline_measure	消费者拒绝设计师
                        // 13	confirm_measure 设计师同意量房
                        //  14	decline_invite_measure 设计师拒绝量房
                        //  21	pay_for_measure 支付量房费
                        //  22	open_3d_design 打开3d工具
                        // 31	author_send_contract 设计师发送合同
                        // 33	deliver_measure_file  上传量房交付物
                        //  41	pay_for_first_fee 支付设计首款
                        if (StringUtils.isNumeric(wk_cur_sub_node_id) && Integer.valueOf(wk_cur_sub_node_id) >= 41) {
                            rmTag = true;
                        }
                    }
                    if (rmTag) {
                        int size = bidders.size();
                        for (int i = size - 1; i >= 0; i--) {
                            String wk_cur_sub_node_id = bidders.get(i).getWk_cur_sub_node_id();
                            if (!TextUtils.isEmpty(wk_cur_sub_node_id) && StringUtils.isNumeric(wk_cur_sub_node_id) && Integer.valueOf(wk_cur_sub_node_id) <= 13) {
                                bidders.remove(i);
                            }
                        }
                    }
                    mDecorationAdapter.notifyDataSetChanged();
                    break;

                case EVALUATE_STATE:
                    is_evaluated = data.getBooleanExtra(AppraiseDesignerActivity.IS_EVALUATE, false);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 修改后的项目信息与之前的进行替换更新
     * @param amendDemandBean
     */
    private void changeBean(AmendDemandBean amendDemandBean) {
        mNeedsListEntity.setCommunity_name(amendDemandBean.getCommunity_name());
        mNeedsListEntity.setCity(amendDemandBean.getCity());
        mNeedsListEntity.setCity_name(amendDemandBean.getCity_name());
        mNeedsListEntity.setClick_number(amendDemandBean.getClick_number());
        mNeedsListEntity.setConsumer_mobile(amendDemandBean.getConsumer_mobile());
        mNeedsListEntity.setConsumer_name(amendDemandBean.getConsumer_name());
        mNeedsListEntity.setContacts_mobile(amendDemandBean.getContacts_mobile());
        mNeedsListEntity.setContacts_name(amendDemandBean.getContacts_name());
        mNeedsListEntity.setDecoration_style(amendDemandBean.getDecoration_style());
        mNeedsListEntity.setDecoration_budget(amendDemandBean.getDecoration_budget());
        mNeedsListEntity.setDesign_budget(amendDemandBean.getDesign_budget());
        mNeedsListEntity.setDetail_desc(amendDemandBean.getDetail_desc());
        mNeedsListEntity.setDistrict_name(amendDemandBean.getDistrict_name());
        mNeedsListEntity.setDistrict(amendDemandBean.getDistrict());
        mNeedsListEntity.setHouse_area(amendDemandBean.getHouse_area());
        mNeedsListEntity.setHouse_type(amendDemandBean.getHouse_type());
        mNeedsListEntity.setLiving_room(amendDemandBean.getLiving_room());
        mNeedsListEntity.setProvince_name(amendDemandBean.getProvince_name());
        mNeedsListEntity.setProvince(amendDemandBean.getProvince());
        mNeedsListEntity.setToilet(amendDemandBean.getToilet());
        mNeedsListEntity.setRoom(amendDemandBean.getRoom());
    }

    private static final int WK_FLOW_STATE = 0;
    private static final int BIDING_STATE = 1;
    private static final int EVALUATE_STATE = 2;
    private static final String IS_PUBLIC = "1";
    private static final String NONE = "none";


    /**
     * this context activity
     */
    protected Activity activity;
    /**
     * root view
     */
    protected View mRootView;
    private LinearLayout mLlItemConsumerDecoration;
    private TextView mTvDesignBudget;
    private TextView mTvProjectAddress;
    private TextView mTvDemandPrice;
    private TextView mTvEndDay;
    private TextView mTvStyle;
    private TextView mTvBuildTime;
    private TextView mTvCommunityName, mTvBidState, mTvNeeds_id;
    private TextView mTVHomeAddress;
    private TextView mTvBidderCount;
    private TextView mTvHouseType;
    private ListView mListView;
    private ScrollView mScrollView;
    private ImageButton mIbnDecorationShow;
    private ImageButton mIbnDecorationModify;
    private AlertView mAlertViewRefuse;

    private LinearLayout mLlEvaluate;
    private TextView mTvEvaluate;
    private String designer_id_evaluate; /// 进行设计交付的设计师的designer_id .
    private MPBidderBean mMPBidderBean = new MPBidderBean();
    private boolean is_evaluated = false;

    private int mPosition; /// 获取对应的item的position .
    private boolean isShowStub;
    private String province_name, city_name, district_name;
    private String community_name;
    private String house_type;
    private String house_type_convert;
    private String design_budget;
    private String living_room, room, toilet;
    private String living_room_convert, room_convert, toilet_convert;
    private String house_area;
    private String bidder_count;
    private String needs_id;
    private String end_day;
    private String is_public;       /// cancel .
    private String designer_id_biding;
    private String user_name_biding;
    private String style_names_biding;
    private String design_fee;
    private String livingRoom_room_toilet = "";
    private String custom_string_status;/// state .
    private String decoration_budget;
    private String publish_time;
    private String decoration_style;
    private String decoration_style_convert;
    private String wk_template_id;
    private String mDistrict;
    private Map<String, String> style;
    private Map<String, String> houseJson;
    private Map<String, String> livingRoomJson;
    private Map<String, String> roomJson;
    private Map<String, String> toiletJson;

    private MyDecorationAdapter mDecorationAdapter;
    private MemberEntity mMemberEntity;
    private DecorationNeedsListBean mNeedsListEntity;
    private ArrayList<DecorationBiddersBean> bidders;
    private int RequestCode = 101;
}