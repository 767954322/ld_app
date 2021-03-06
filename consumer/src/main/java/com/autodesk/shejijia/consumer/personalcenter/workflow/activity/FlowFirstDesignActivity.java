package com.autodesk.shejijia.consumer.personalcenter.workflow.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.DesignerInfoDetails;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPAliPayBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPDesignContractBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPOrderBean;
import com.autodesk.shejijia.consumer.utils.AliPayService;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.consumer.utils.SplitStringUtils;
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

import java.text.DecimalFormat;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 16-6-7
 * @file FlowFirstDesignActivity.java  .
 * @brief 全流程设计首款 .
 */
public class FlowFirstDesignActivity extends BaseWorkFlowActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_flow_first_design;
    }

    @Override
    protected void initView() {
        super.initView();

        tv_flow_first_design_contract_no = (TextView) findViewById(R.id.tv_flow_first_design_contract_no);
        tv_flow_first_design_name = (TextView) findViewById(R.id.tv_flow_first_design_name);
        tv_flow_first_design_phone = (TextView) findViewById(R.id.tv_flow_first_design_phone);
        tv_flow_first_design_aggregate_amount = (TextView) findViewById(R.id.tv_flow_first_design_aggregate_amount);
        tv_flow_first_design_first = (TextView) findViewById(R.id.tv_flow_first_design_first);
        tv_flow_first_design_last = (TextView) findViewById(R.id.tv_flow_first_design_last);
        tv_flow_first_design_should_first = (TextView) findViewById(R.id.tv_flow_first_design_should_first);
        ll_flow_first_design_send = (LinearLayout) findViewById(R.id.ll_flow_first_design_send);
        btn_flow_first_design_send = (Button) findViewById(R.id.btn_flow_first_design_send);
        tv_flow_first_design_deduct_measure_cost = (TextView) findViewById(R.id.tv_flow_first_design_deduct_measure_cost);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        CustomProgress.show(this, "", false, null);

        setTitleForNavbar(getResources().getString(R.string.flow_pay_detail)); /// 设置标题 .
    }

    /**
     * 当点击返回按键后调用节点优化接口，使原31节点变成32节点
     *
     * @param view
     */
    @Override
    protected void leftNavButtonClicked(View view) {
        promptDialog();
    }

    // 捕获返回键的方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            promptDialog();
        }
        return false;
    }

    private void promptDialog() {
        new AlertView(UIUtils.getString(R.string.tip), "确定放弃本次支付吗？", null, new String[]{UIUtils.getString(R.string.sure), UIUtils.getString(R.string.pickerview_cancel)}, null, this,
                AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object object, int position) {
                switch (position) {
                    case 0:
                        getNodeLock(needs_id, designer_id, contract_no);
                        break;
                    default:
                        break;
                }
            }
        }).show();
    }

    @Override
    protected void onWorkFlowData() {
        super.onWorkFlowData();

        updateViewFromData();
        restgetDesignerInfoData(designer_id, hs_uid, new commonJsonResponseCallback() {
            @Override
            public void onJsonResponse(String jsonResponse) {
                CustomProgress.cancelDialog();
                designerInfoList = new Gson().fromJson(jsonResponse, DesignerInfoDetails.class);
                updateViewFromInfoData();
            }

            @Override
            public void onError(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                ApiStatusUtil.getInstance().apiStatuError(volleyError, FlowFirstDesignActivity.this);

            }
        });
    }

    /**
     * @param needs_id
     * @param designer_id
     * @param contract_no
     * @brief 节点变动接口31变动成32 .
     */
    public void getNodeLock(String needs_id, String designer_id, String contract_no) {
        MPServerHttpManager.getInstance().getNodeLock(needs_id, designer_id, contract_no, new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                finish();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                finish();
            }
        });
    }

    private void updateViewFromData() {
        MPDesignContractBean designContractEntity = mBidders.get(0).getDesign_contract();
        if (null == designContractEntity) {
            return;
        }
        tv_flow_first_design_contract_no.setText(designContractEntity.getContract_no());


        tv_flow_first_design_first.setText(designContractEntity.getContract_first_charge());

        Double totalCost = Double.parseDouble(designContractEntity.getContract_charge());
        Double firstCost = Double.parseDouble(designContractEntity.getContract_first_charge());

        DecimalFormat df = new DecimalFormat("#.##"); // 保留小数点后两位
        tv_flow_first_design_last.setText(df.format(totalCost - firstCost)); // 设计尾款

        if (Integer.valueOf(wk_cur_sub_node_id) == 31 || Integer.valueOf(wk_cur_sub_node_id) == 32) {
            ll_flow_first_design_send.setVisibility(View.VISIBLE);
            btn_flow_first_design_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MPOrderBean order = null;
                    order = getOrderEntityByStep(FlowFirstDesignActivity.this.nodeState);
                    if (order == null)
                        return;
                    String order_line_no = order.getOrder_line_no();
                    String order_no = order.getOrder_no();

//                    if (isLock) {
                        getAliPayDetailInfo(order_no, order_line_no);
//                        isLock = false;
//                    } else {

//                    }
                }
            });
        }
    }

    private void updateViewFromInfoData() {
        if (!designerInfoList.equals(null)) {
            tv_flow_first_design_name.setText(designerInfoList.getReal_name().getReal_name());
            tv_flow_first_design_phone.setText(designerInfoList.getReal_name().getMobile_number().toString());

            String measurement_price = mBidders.get(0).getMeasurement_fee();
            if (TextUtils.isEmpty(measurement_price) || "0".equals(measurement_price) || "0.0".equals(measurement_price)) {
                measurement_price = "0.00";
            }
            measurement_price = SplitStringUtils.splitStringDot(measurement_price);
            tv_flow_first_design_deduct_measure_cost.setText(measurement_price + getResources().getString(R.string.flow_monad_rmb)); // 已扣除量房费
            MPDesignContractBean designContractEntity = mBidders.get(0).getDesign_contract();
            if (null == designContractEntity) {
                return;
            }
            tv_flow_first_design_aggregate_amount.setText(designContractEntity.getContract_charge());
            Double firstCost = Double.parseDouble(designContractEntity.getContract_first_charge());
            Double measureCost = Double.parseDouble(mBidders.get(0).getMeasurement_fee());
            DecimalFormat df = new DecimalFormat("#.##"); // 保留小数点后两位
            tv_flow_first_design_should_first.setText(df.format(firstCost - measureCost) + getResources().getString(R.string.flow_monad_rmb)); // 本次应付设计首款
        }
    }

    /**
     * @param order_no
     * @param order_line_no
     * @brief 获取支付宝详细信息 .
     */
    private void getAliPayDetailInfo(String order_no, String order_line_no) {

        MPServerHttpManager.getInstance().getAlipayDetailInfo(order_no, order_line_no, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String userInfo = GsonUtil.jsonToString(jsonObject);
                LogUtils.i("FlowFirstDesignActivity", userInfo);
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
                PayService.DoPayment(FlowFirstDesignActivity.this);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                new AlertView(UIUtils.getString(R.string.tip), "暂时不能支付，请稍后重试", null, null, new String[]{UIUtils.getString(R.string.sure)}, FlowFirstDesignActivity.this,
                        AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (position != AlertView.CANCELPOSITION) {
                            finish();
                        }
                    }
                }).setCancelable(true).show();
            }
        });
    }

    AliPayService.AliPayActionStatus AliCallBack = new AliPayService.AliPayActionStatus() {

        public void onOK() {
            openAlertView(UIUtils.getString(R.string.pay_success), 0);
        }

        public void onFail() {
            openAlertView(UIUtils.getString(R.string.pay_failed), 1);
        }
    };

    private void openAlertView(String content, final int isSuccess) {
        new AlertView(UIUtils.getString(R.string.tip), content, null, null, new String[]{UIUtils.getString(R.string.chatroom_audio_recording_erroralert_ok)}, this,
                AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object object, int position) {
                if (position == 0 && isSuccess == 0) {
                    setResult(FirstForContract);
                    finish();
                }
//                else if (position == 0 && isSuccess == 1) {
//                    isLock = true;
//                } else {
//                }
            }
        }).show();
    }

    private LinearLayout ll_flow_first_design_send;
    private TextView tv_flow_first_design_name;
    private TextView tv_flow_first_design_phone;
    private TextView tv_flow_first_design_first;
    private TextView tv_flow_first_design_last;
    private TextView tv_flow_first_design_contract_no;
    private TextView tv_flow_first_design_should_first;
    private TextView tv_flow_first_design_aggregate_amount;
    private TextView tv_flow_first_design_deduct_measure_cost;
    private Button btn_flow_first_design_send;
    private int FirstForContract = 1; // 首款调到设计合同

    protected DesignerInfoDetails designerInfoList;
//    private boolean isLock = true; // 是否锁定按键
    private AlertView UIAlert;
}
