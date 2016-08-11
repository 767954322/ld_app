package com.autodesk.shejijia.consumer.personalcenter.workflow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPBidderBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPDeliveryBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPOrderBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.WkFlowDetailsBean;
import com.autodesk.shejijia.consumer.utils.MPStatusMachine;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONObject;

import java.util.List;


/**
 * @author Malidong .
 * @version 1.0 .
 * @date 16-6-7
 * @file BaseWorkFlowActivity.java  .
 * @brief 全流程状态控制类的基类.
 */
public abstract class BaseWorkFlowActivity extends NavigationBarActivity {

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

//        state = intent.getIntExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE,-1);
        state = (int) intent.getExtras().get(Constant.WorkFlowStateKey.JUMP_FROM_STATE);
        /// 状态标签，全流程、IM、资料项目 .
        if (state == Constant.WorkFlowStateKey.STEP_DECORATION || state == Constant.WorkFlowStateKey.STEP_FLOW) { /// 全流程 .
            designer_id = bundle.getString(Constant.BundleKey.BUNDLE_DESIGNER_ID);
            needs_id = bundle.getString(Constant.BundleKey.BUNDLE_ASSET_NEED_ID);
        } else if (state == Constant.WorkFlowStateKey.STEP_IM || state == Constant.WorkFlowStateKey.STEP_MATERIAL) { /// IM、资料项目 .
            designer_id = bundle.getString(Constant.ProjectMaterialKey.IM_TO_FLOW_DESIGNER_ID);
            needs_id = bundle.getString(Constant.ProjectMaterialKey.IM_TO_FLOW_NEEDS_ID);
        }
        wk_cur_ActionNode_id = bundle.getInt(Constant.BundleKey.BUNDLE_ACTION_NODE_ID);                                         /// 获取wk_cur_ActionNode_id以此来判断状态 .
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (memberEntity != null) {
            mMemberType = memberEntity.getMember_type();
        }
        getOrderDetailsInfo(needs_id, designer_id);

    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mCurrentWorkFlowDetail = (WkFlowDetailsBean) msg.obj;
            requirement = mCurrentWorkFlowDetail.getRequirement();
            if (requirement == null) {
                return;
            }
            mBidders = requirement.getBidders();
            if (null == mBidders) {
                return;
            }
            if (mBidders.size() > 0) {
                MPBidderBean biddersEntity = mBidders.get(0);
                if (null != biddersEntity) {
                    hs_uid = biddersEntity.getUid();
                    mDeliveryBean = biddersEntity.getDelivery();
                }
            }
            mBiddersEntity = requirement.getOrderBidder();
            community_name = requirement.getCommunity_name();
            if (mBiddersEntity == null) {
                return;
            }
            wk_cur_sub_node_id = mBiddersEntity.getWk_cur_sub_node_id();
            if (!TextUtils.isEmpty(wk_cur_sub_node_id) && StringUtils.isNumeric(wk_cur_sub_node_id)) {
                wk_template_id = requirement.getWk_template_id();
                onWorkFlowData();
            }
        }
    };

    /**
     * 获取订单详细信息
     *
     * @param needs_id    　项目订单编号
     * @param designer_id 　设计师的id
     */
    public void getOrderDetailsInfo(String needs_id, String designer_id) {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(final JSONObject jsonObject) {
                String userInfo = GsonUtil.jsonToString(jsonObject);
                mCurrentWorkFlowDetail = GsonUtil.jsonToBean(userInfo, WkFlowDetailsBean.class);
                if (null != mCurrentWorkFlowDetail) {
                    Message msg = Message.obtain();
                    msg.obj = mCurrentWorkFlowDetail;
                    handler.sendMessage(msg);
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CustomProgress.cancelDialog();
                MPNetworkUtils.logError(TAG, volleyError);
            }
        };
        MPServerHttpManager.getInstance().getOrderDetailsInfoData(needs_id, designer_id, okResponseCallback);
    }

    public MPOrderBean getOrderEntityByStep(final int step) {
        String order_type;
        String order_status;

        for (MPOrderBean order : mBiddersEntity.getOrders()) {
            order_type = order.getOrder_type();
            if (Constant.NumKey.ZERO.equals(order_type) && step == MPStatusMachine.NODE__MEANSURE_PAY) {            /// 支付量房费 .
                return order;
            }
            if (Constant.NumKey.ONE.equals(order_type)) {
                order_status = order.getOrder_status();
                if (Constant.NumKey.FIVE.equals(order_status) && step == MPStatusMachine.NODE__DESIGN_FIRST_PAY) { /// 支付设计首款 .
                    return order;
                }

                if (Constant.NumKey.SIX.equals(order_status) && step == MPStatusMachine.NODE__DESIGN_BALANCE_PAY) { /// 支付设计尾款 .
                    return order;
                }
            }
        }
        return null;
    }

    protected void onWorkFlowData() {
    }

    protected int state;
    protected int wk_cur_ActionNode_id;
    protected String hs_uid;
    protected String designer_id;
    protected String wk_template_id;
    protected String community_name;
    protected String wk_cur_sub_node_id;
    protected String needs_id;

    protected MemberEntity memberEntity;
    protected WkFlowDetailsBean mCurrentWorkFlowDetail;
    protected WkFlowDetailsBean.RequirementEntity requirement;
    protected MPBidderBean mBiddersEntity;
    protected List<MPBidderBean> mBidders;
    protected MPDeliveryBean mDeliveryBean;
    protected String mMemberType;

}
