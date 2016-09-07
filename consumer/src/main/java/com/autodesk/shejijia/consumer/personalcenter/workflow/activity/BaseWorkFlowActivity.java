package com.autodesk.shejijia.consumer.personalcenter.workflow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.manager.constants.JsonConstants;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPBidderBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPDeliveryBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPOrderBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.WkFlowDetailsBean;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.consumer.utils.MPStatusMachine;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
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
        needs_id = bundle.getString(Constant.SeekDesignerDetailKey.NEEDS_ID);
        contract_no = intent.getStringExtra(Constant.SeekDesignerDetailKey.CONTRACT_NO);
        designer_id = bundle.getString(Constant.SeekDesignerDetailKey.DESIGNER_ID);
        measureFee = intent.getStringExtra(JsonConstants.JSON_MEASURE_FORM_AMOUNT);
        measureFee = intent.getStringExtra(JsonConstants.JSON_MEASURE_FORM_AMOUNT);

        measureFee = intent.getStringExtra(JsonConstants.JSON_MEASURE_FORM_AMOUNT);
        contract_no = bundle.getString(Constant.SeekDesignerDetailKey.CONTRACT_NO);
        mThreead_id = bundle.getString(Constant.ProjectMaterialKey.IM_TO_FLOW_THREAD_ID);//thread_id
        nodeState = bundle.getInt(Constant.BundleKey.TEMPDATE_ID);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        memberEntity = AdskApplication.getInstance().getMemberEntity();

        getOrderDetailsInfo(needs_id, designer_id);
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    public int WorkFlowTemplateStep() {


        return Integer.valueOf(wk_cur_template_id);

    }

    public int WorkFlowSubNodeStep() {
        return Integer.valueOf(wk_cur_sub_node_id);

    }

    public String GetRoleType() {
        if (memberEntity != null) {
            return memberEntity.getMember_type();
        }
        return "";
    }

    public Boolean isRoleDesigner() {
        return (Constant.UerInfoKey.DESIGNER_TYPE.equals(GetRoleType()));
    }

    public Boolean isRoleCustomer() {
        return (Constant.UerInfoKey.CONSUMER_TYPE.equals(GetRoleType()));
    }


    private final android.os.Handler handler = new android.os.Handler() {
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
            contacts_name = requirement.getContacts_name();
            if (mBiddersEntity == null) {
                return;
            }
            wk_cur_sub_node_id = mBiddersEntity.getWk_cur_sub_node_id();
            wk_cur_template_id = Integer.parseInt(requirement.getWk_template_id());

            onWorkFlowData();
            /*if (!TextUtils.isEmpty(wk_cur_sub_node_id) && StringUtils.isNumeric(wk_cur_sub_node_id)) {
                wk_cur_template_id = Integer.parseInt(requirement.getWk_template_id());


            }*/
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
                ApiStatusUtil.getInstance().apiStatuError(volleyError, BaseWorkFlowActivity.this);
            }
        };
        MPServerHttpManager.getInstance().getOrderDetailsInfoData(needs_id, designer_id, okResponseCallback);
    }

    public MPOrderBean getOrderEntityByStep(final int step) {
        String order_type;
        String order_status;

        for (MPOrderBean order : mBiddersEntity.getOrders()) {
            order_type = order.getOrder_type();
            if ((Constant.NumKey.ZERO.equals(order_type) && step == MPStatusMachine.NODE__MEANSURE_PAY)) {            /// 支付量房费 .
                return order;
            }
            if (Constant.NumKey.CERTIFIED_CHECKING.equals(order_type)) {
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

    public interface commonJsonResponseCallback {
        public void onJsonResponse(String jsonResponse);

        public void onError(VolleyError volleyError);
    }


    // Method when child class need some extra data from app-server
    public void restgetDesignerInfoData(String designer_id, String hs_uid, final commonJsonResponseCallback callBack) {
        MPServerHttpManager.getInstance().getDesignerInfoData(designer_id, hs_uid, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonString = GsonUtil.jsonToString(jsonObject);
                callBack.onJsonResponse(jsonString);

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError, true);
                callBack.onError(volleyError);
            }
        });
    }


    protected int state;
    protected int nodeState;
    protected int wk_cur_template_id;
    protected String wk_cur_sub_node_id;
    protected String hs_uid;
    protected String designer_id;
    protected String mThreead_id;
    protected String community_name;
    protected String contacts_name;

    protected String needs_id;
    protected String measureFee;
    protected MemberEntity memberEntity;
    protected WkFlowDetailsBean mCurrentWorkFlowDetail;
    protected WkFlowDetailsBean.RequirementEntity requirement;
    protected MPBidderBean mBiddersEntity;
    protected List<MPBidderBean> mBidders;
    protected MPDeliveryBean mDeliveryBean;
    protected String contract_no; // 设计合同编号
}
