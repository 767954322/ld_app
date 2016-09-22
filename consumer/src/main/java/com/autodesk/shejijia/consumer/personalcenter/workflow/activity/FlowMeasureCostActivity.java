package com.autodesk.shejijia.consumer.personalcenter.workflow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.DesignerInfoDetails;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPAliPayBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPOrderBean;
import com.autodesk.shejijia.consumer.utils.AliPayService;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 16-6-7
 * @file FlowMeasureCostActivity.java  .
 * @brief 支付量房费 .
 */
public class FlowMeasureCostActivity extends BaseWorkFlowActivity implements View.OnClickListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_flow_measure_cost;
    }

    @Override
    protected void initView() {
        super.initView();
        tv_name = (TextView) findViewById(R.id.tv_flow_measure_cost_name);
        tv_phone = (TextView) findViewById(R.id.tv_flow_measure_cost_phone);
        tv_house_cost = (TextView) findViewById(R.id.tv_flow_measure_house_cost);
        btn_pay_measure = (Button) findViewById(R.id.btn_pay_measure);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        CustomProgress.show(this, "", false, null);
        super.initData(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pay_measure: /// 支付量房费 .
                Intent intent = getIntent();
                String order_line_id = intent.getStringExtra(Constant.SeekDesignerDetailKey.ORDER_LINE_ID);
                String order_id = intent.getStringExtra(Constant.SeekDesignerDetailKey.ORDER_ID);
                MPOrderBean order = getOrderEntityByStep(this.nodeState);
                if (order == null) {
                    return;
                }
                if (order_line_id != null && order_id != null) {
                    if (isLock) {
                        getAliPayDetailsInfo(order_id, order_line_id);
                        isLock = false;
                    }
                } else {
                    String order_line_no = order.getOrder_line_no();
                    String order_no = order.getOrder_no();
                    if (isLock) {
                        getAliPayDetailsInfo(order_no, order_line_no);
                        isLock = false;
                    }
                }


                break;
            default:
                break;
        }
    }

    @Override
    protected void onWorkFlowData() {
        super.onWorkFlowData();

        restgetDesignerInfoData(designer_id, hs_uid, new commonJsonResponseCallback() {
            @Override
            public void onJsonResponse(String jsonResponse) {

                designerInfoList = new Gson().fromJson(jsonResponse, DesignerInfoDetails.class);
                if (null != designerInfoList) {
                    DesignerInfoDetails.RealNameBean real_name = designerInfoList.getReal_name();
                    CustomProgress.cancelDialog();
                    tv_name.setText(real_name.getReal_name());
                    tv_phone.setText(real_name.getMobile_number().toString());
                }
            }

            @Override
            public void onError(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                ApiStatusUtil.getInstance().apiStatuError(volleyError, FlowMeasureCostActivity.this);

            }
        });
        updateViewFromData();
    }

    private void updateViewFromData() {
        String measurement_fee = mBiddersEntity.getMeasurement_fee();
        if (TextUtils.isEmpty(measurement_fee) || "0.0".equals(measurement_fee)) {
            measurement_fee = "0.00";
        }
        if (measureFee != null && measureFee.length() > 0) {
            tv_house_cost.setText(measureFee);
        } else {
            tv_house_cost.setText(measurement_fee);
        }
        if (Constant.UerInfoKey.CONSUMER_TYPE.equals(memberEntity.getMember_type())) {
            setTitleForNavbar(getResources().getString(R.string.i_want_to_pay));
            if (Integer.valueOf(this.wk_cur_sub_node_id) >= 21) {                         /// 如果是支付量房费状态及后续状态，就隐藏支付按钮 .
                btn_pay_measure.setVisibility(View.GONE);
                btn_pay_measure.setOnClickListener(null);

            } else {
                if (payOk == true) {
                    btn_pay_measure.setVisibility(View.GONE);
                    btn_pay_measure.setOnClickListener(null);
                } else {
                    btn_pay_measure.setVisibility(View.VISIBLE);
                    btn_pay_measure.setOnClickListener(this);
                }
            }
        } else if (memberEntity.getMember_type().equals(Constant.UerInfoKey.DESIGNER_TYPE)) {
            setTitleForNavbar(getResources().getString(R.string.flow_cost_detail));
            btn_pay_measure.setVisibility(View.GONE);
            btn_pay_measure.setOnClickListener(null);
        }
    }

    /**
     * @param order_no
     * @param order_line_no
     * @biref 获取支付宝详细信息 .
     */
    private void getAliPayDetailsInfo(String order_no, String order_line_no) {
        MPServerHttpManager.getInstance().getAlipayDetailInfo(order_no, order_line_no, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String userInfo = GsonUtil.jsonToString(jsonObject);
                LogUtils.i(TAG, userInfo);

                MPAliPayBean MPAliPayBean = GsonUtil.jsonToBean(userInfo, MPAliPayBean.class);

                String amount;
                String notifyURL;
                String tradeNO;
                String productName;
                String Seller;
                String Partner;

                Seller = MPAliPayBean.getSeller();
                Partner = MPAliPayBean.getPartner();
                productName = MPAliPayBean.getProductName();
                amount = MPAliPayBean.getAmount();
                notifyURL = MPAliPayBean.getNotifyURL();
                tradeNO = MPAliPayBean.getTradeNO();

                AliPayService PayService = new AliPayService(Seller, Partner, productName, amount, notifyURL, productName, tradeNO);
                PayService.SetCallBack(AliCallBack);
                PayService.DoPayment(FlowMeasureCostActivity.this);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
            }
        });
    }

    AliPayService.AliPayActionStatus AliCallBack = new AliPayService.AliPayActionStatus() {
        public void onOK() {
//            Toast toast = Toast.makeText(FlowMeasureCostActivity.this, UIUtils.getString(R.string.pay_success), Toast.LENGTH_SHORT);
//            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
//            toast.show();
            openAlertView(UIUtils.getString(R.string.pay_success),0);

        }

        public void onFail() {
//            Toast toast = Toast.makeText(FlowMeasureCostActivity.this, UIUtils.getString(R.string.pay_failed), Toast.LENGTH_SHORT);
//            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
//            toast.show();
            openAlertView(UIUtils.getString(R.string.pay_failed),1);

        }
    };
    private void openAlertView(String content,final int isSuccess) {
        new AlertView(UIUtils.getString(R.string.tip), content, null, null, new String[]{UIUtils.getString(R.string.chatroom_audio_recording_erroralert_ok)}, this,
                AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object object, int position) {
                if (position == 0 && isSuccess == 0) {
                    payOk = true;
                    setResult(RESULT_CODE, new Intent());
                    finish();
                } else if (position == 0 && isSuccess == 1) {
                    payOk = false;
                    isLock = true;
                    Intent intent = new Intent();
                    intent.putExtra(Constant.SixProductsFragmentKey.SELECTION, Constant.SixProductsFragmentKey.ISELITE);
                    setResult(RESULT_CODE, intent);
                } else {
                }
            }
        }).show();
    }

    private String order_line_id;
    private String order_id;

    private TextView tv_name;
    private TextView tv_phone;
    private TextView tv_house_cost;
    private Button btn_pay_measure;
    public static final int RESULT_CODE = 1011010;
    protected DesignerInfoDetails designerInfoList;
    private boolean payOk = false;
    private boolean isLock = true; // 是否锁定按键
}
