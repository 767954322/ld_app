package com.autodesk.shejijia.consumer.personalcenter.workflow.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.bidhall.activity.BiddingHallDetailActivity;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.manager.WkTemplateConstants;
import com.autodesk.shejijia.consumer.manager.constants.JsonConstants;
import com.autodesk.shejijia.consumer.personalcenter.workflow.adapter.WkFlowStateAdapter;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.TipWorkFlowTemplateBean;
import com.autodesk.shejijia.consumer.utils.MPStatusMachine;
import com.autodesk.shejijia.consumer.utils.WkFlowStateMap;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.appglobal.UrlMessagesContants;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.MyToast;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.common.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.im.activity.ChatRoomActivity;
import com.autodesk.shejijia.shared.framework.AdskApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.finalteam.loadingviewfinal.ListViewFinal;
import cn.finalteam.loadingviewfinal.OnDefaultRefreshListener;
import cn.finalteam.loadingviewfinal.PtrClassicFrameLayout;
import cn.finalteam.loadingviewfinal.PtrFrameLayout;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 16-6-7
 * @file WkFlowStateActivity.java  .
 * @brief 全流程状态机类（就是六个状态类） .
 */
public class WkFlowStateActivity extends BaseWorkFlowActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_designer_common_meal_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        context = this;
        mListView = (ListViewFinal) findViewById(R.id.lv_designer_meal_detail);
        tvDesignerName = (TextView) findViewById(R.id.tv_designer_name);
        tvCreateDate = (TextView) findViewById(R.id.tv_create_date);

        mPtrLayout = (PtrClassicFrameLayout) findViewById(R.id.ptr_layout);
        polygonImageView = (PolygonImageView) findViewById(R.id.piv_consumer_order_photo_01);
        btnStopDemand = (Button) findViewById(R.id.btn_stop_demand);
        rl_piv = (RelativeLayout) findViewById(R.id.rl_piv);
        rlStopContract = (RelativeLayout) findViewById(R.id.rl_stop_contract);
        ibFlowChart = (ImageButton) findViewById(R.id.ib_flow_chart);

        //右上角三个按钮设置；
        right_contain = (LinearLayout) findViewById(R.id.right_contain);
        View view = LayoutInflater.from(this).inflate(R.layout.addview_wkflow_state, null);
        right_contain.addView(view);
        right_contain.setVisibility(View.VISIBLE);

        demandDetails = (ImageView) right_contain.findViewById(R.id.demand_details);
        projectInformation = (ImageView) right_contain.findViewById(R.id.project_information);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (Constant.UerInfoKey.CONSUMER_TYPE.equals(memberEntity.getMember_type())) {
            rl_piv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        bid_status = bundle.getBoolean(Constant.DemandDetailBundleKey.DEMAND_BID_STATUS);
        demand_type = bundle.getString(Constant.DemandDetailBundleKey.DEMAND_TYPE);

    }

    /**
     * 监听方法
     */
    @Override
    protected void initListener() {
        super.initListener();
        projectInformation.setOnClickListener(this);
        demandDetails.setOnClickListener(this);
        btnStopDemand.setOnClickListener(this);
        mListView.setOnItemClickListener(this);
        ibFlowChart.setOnClickListener(this);
        mPtrLayout.setLastUpdateTimeRelateObject(this);
        mPtrLayout.setLastUpdateTimeRelateObject(this);

        mPtrLayout.setOnRefreshListener(new OnDefaultRefreshListener() {
                                            @Override
                                            public void onRefreshBegin(PtrFrameLayout frame) {
                                                getOrderDetailsInfo(needs_id, designer_id);
                                            }
                                        }
        );
    }

    @Override
    protected void leftNavButtonClicked(View view) {
        refreshWkFlowState();
        super.leftNavButtonClicked(view);
    }

    //监听标题栏三个按钮
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ib_flow_chart:
                MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
                Intent intentChart = new Intent(context, ChatRoomActivity.class);
                String acs_member_id = mMemberEntity.getAcs_member_id();
                String member_type = mMemberEntity.getMember_type();
                intentChart.putExtra(ChatRoomActivity.RECIEVER_USER_NAME, mBiddersEntity.getUser_name());
                intentChart.putExtra(ChatRoomActivity.THREAD_ID, mThreead_id);
                intentChart.putExtra(ChatRoomActivity.ASSET_ID, needs_id);
                intentChart.putExtra(ChatRoomActivity.MEMBER_TYPE, member_type);
                intentChart.putExtra(ChatRoomActivity.RECIEVER_USER_ID, designer_id);
                intentChart.putExtra(ChatRoomActivity.ACS_MEMBER_ID, acs_member_id);
                context.startActivity(intentChart);

                break;
            case R.id.btn_stop_demand:
                isStopFlow();

                break;
            case R.id.demand_details:
                /**
                 * 需求详情 .
                 */
                Intent intent = new Intent(WkFlowStateActivity.this, BiddingHallDetailActivity.class);
                intent.putExtra(Constant.DemandDetailBundleKey.DEMAND_NEEDS_ID, needs_id);
                intent.putExtra(Constant.DemandDetailBundleKey.DEMAND_TYPE, demand_type);
                intent.putExtra(Constant.DemandDetailBundleKey.DEMAND_BID_STATUS, bid_status);
                startActivity(intent);

                break;

            case R.id.project_information:

                /**
                 * 点击右上角资料进行的操作
                 *
                 * @param view 右上角的控件
                 */
                //designer_id = bundle.getString(Constant.SeekDesignerDetailKey.DESIGNER_ID);
                Intent maIntent = new Intent(WkFlowStateActivity.this, ProjectMaterialActivity.class);      /// 跳转项目资料界面 .
                maIntent.putExtra(Constant.SeekDesignerDetailKey.DESIGNER_ID, designer_id);
                maIntent.putExtra(Constant.SeekDesignerDetailKey.NEEDS_ID, needs_id);
                startActivity(maIntent);

                break;

        }
    }

    private void isStopFlow() {

        final View view = LayoutInflater.from(this).inflate(R.layout.stop_cooperation_, null);
        alertView = new AlertView(UIUtils.getString(R.string.tip_stop), null, null, null,
                new String[]{UIUtils.getString(R.string.sure), UIUtils.getString(R.string.chatroom_audio_recording_erroralert_cancel)}, this,
                AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object object, int position) {
                if (position == 0) {
                    CustomProgress.show(WkFlowStateActivity.this, UIUtils.getString(R.string.data_submission), false, null);
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put(JsonConstants.JSON_MEASURE_FORM_DESIGNER_ID, designer_id);
                        jsonObject.put(JsonConstants.ASSET_ID, needs_id);

                        RadioButton rbServiced = (RadioButton) view.findViewById(R.id.rb_serviced);
                        if (rbServiced.isChecked()) {
                            jsonObject.put(JsonConstants.MEASUREMENT, 0);
                        } else {
                            jsonObject.put(JsonConstants.MEASUREMENT, 1);
                        }

                        sendStopFlow(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        alertView.addExtView(view).show();
    }

    /// 终止合同
    private void sendStopFlow(JSONObject jsonObject) {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MyToast.show(WkFlowStateActivity.this, "网络链接失败");
                CustomProgress.cancelDialog();
            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                finish();

            }
        };

        MPServerHttpManager.getInstance().sendStopFlow(jsonObject, okResponseCallback);
    }


    /**
     * 点击每个条目执行的操作
     */
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mBiddersEntity == null) {
            return;
        }
        int wk_cur_sub_node_idi = Integer.valueOf(wk_cur_sub_node_id); /// String转int .

        if (memberEntity != null) {
            strMemberType = memberEntity.getMember_type();
        }
        if (WorkFlowTemplateStep() == 4) {
            onEliteItemClick(position, wk_cur_sub_node_idi, view);
        } else {
            onOrdinaryItemClick(position, wk_cur_sub_node_idi, view);
        }
    }

    private void onEliteItemClick(int position, int wk_cur_sub_node_idi, View view) {
        switch (position) {
            case 0://量房
//                showNewActivity(FlowMeasureFormActivity.class,-1);
                break;
            case 1://设计合同 or 量房交付物
                eliteEstablishContract(wk_cur_sub_node_idi, view);
                break;
            case 2://设计首款
                firstPay(wk_cur_sub_node_idi, view);
                break;
            case 3://设计尾款
                lastPay(wk_cur_sub_node_idi, view);
                break;
            case 4://接收设计交付物
                deliver(wk_cur_sub_node_idi, view);
                break;
            default:
                break;
        }
    }

    private void onOrdinaryItemClick(int position, int wk_cur_sub_node_idi, View view) {
        switch (position) {
            case 0://量房
                if(Constant.UerInfoKey.DESIGNER_TYPE.equals(strMemberType)){
                    showNewActivity(FlowMeasureFormActivity.class, -1);//IOS消费者不让点击的
                }
                break;
            case 1://支付量房费
                costMeasureFeeNode(wk_cur_sub_node_idi, view);
                break;
            case 2://设计合同 or 量房交付物
                establishContract(wk_cur_sub_node_idi, view);
                break;
            case 3://设计首款
                firstPay(wk_cur_sub_node_idi, view);
                break;
            case 4://设计尾款
                lastPay(wk_cur_sub_node_idi, view);
                break;
            case 5://接收设计交付物
                deliver(wk_cur_sub_node_idi, view);
                break;
            default:
                break;
        }
    }

    /**
     * 点击支付量房费用（或者接收量房费）
     *
     * @param wk_cur_sub_node_idi
     * @param view
     */
    private void costMeasureFeeNode(int wk_cur_sub_node_idi, View view) {

        if (Constant.UerInfoKey.DESIGNER_TYPE.equals(strMemberType)) {
            if (wk_cur_sub_node_idi == 21) {
                showNewActivity(FlowMeasureCostActivity.class, MPStatusMachine.NODE__MEANSURE_PAY);
            }else if(wk_cur_sub_node_idi == 13){
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.waiting_cons_uploaded_room_deliverable), null, new String[]{UIUtils.getString(R.string.sure)}, null, WkFlowStateActivity.this,
                        AlertView.Style.Alert, null).show();
            }else{
                view.setClickable(false);
            }
//            if (WorkFlowTemplateStep() == 1) {     // 应标
//                if (wk_cur_sub_node_idi == 21) {
//                    showNewActivity(FlowMeasureCostActivity.class, MPStatusMachine.NODE__MEANSURE_PAY);
//                } else if (wk_cur_sub_node_idi == 21 ) {
//                    view.setClickable(false);
//                }
//            } else if (WorkFlowTemplateStep() == 2) {    /// 自选量房阶段 .
//                if (wk_cur_sub_node_idi == 11 || wk_cur_sub_node_idi == 14) {
//                    view.setClickable(false);
//                } else if (wk_cur_sub_node_idi == 13 || wk_cur_sub_node_idi > 14) {
//                    showNewActivity(FlowMeasureCostActivity.class, MPStatusMachine.NODE__MEANSURE_PAY);
//                }
//            }
            return;
        }
        if (wk_cur_sub_node_idi == 13) {
            showNewActivity(FlowMeasureCostActivity.class, MPStatusMachine.NODE__MEANSURE_PAY);

        }else{
            view.setClickable(false);
        }

//        if (WorkFlowTemplateStep() == 1) { //竞优
//            if (wk_cur_sub_node_idi == 13) {
//                showNewActivity(FlowMeasureCostActivity.class, MPStatusMachine.NODE__MEANSURE_PAY);
//
//            } else {
//                view.setClickable(false);
//            }
//        } else if (WorkFlowTemplateStep() == 2) { // 套餐
//            if (wk_cur_sub_node_idi >= 13 && wk_cur_sub_node_idi != 14) {
//                showNewActivity(FlowMeasureCostActivity.class, MPStatusMachine.NODE__MEANSURE_PAY);
//            } else {
//                view.setClickable(false);
//            }
//        }


    }

    /**
     * 精选创建设计合同(或者接受设计合同)
     *
     * @param wk_cur_sub_node_idi
     * @param view
     */
    private void eliteEstablishContract(int wk_cur_sub_node_idi, View view) {
        if (Constant.UerInfoKey.DESIGNER_TYPE.equals(strMemberType)) {
            if (wk_cur_sub_node_idi == 11  ||wk_cur_sub_node_idi == 31 ) { /// 设计合同 .
                showNewActivity(FlowEstablishContractActivity.class, -1);
            } else if(wk_cur_sub_node_idi == 24 ||wk_cur_sub_node_idi == 33){
                showNewActivity(FlowUploadDeliveryActivity.class, -1);
            } else {
                view.setClickable(false);
            }
            return;
        }
        if (Integer.parseInt(wk_cur_sub_node_id) == 11) {
            new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.please_wait_designer_send_contract), null, new String[]{UIUtils.getString(R.string.sure)}, null, WkFlowStateActivity.this,
                    AlertView.Style.Alert, null).show();
        } else if (Integer.parseInt(wk_cur_sub_node_id) == 31) {
            showNewActivity(FlowEstablishContractActivity.class, -1);
        }else if(wk_cur_sub_node_idi == 24 || wk_cur_sub_node_idi == 33){
            showNewActivity(FlowUploadDeliveryActivity.class, -1);
        } else {
            view.setClickable(false);
        }


    }


    /**
     * 点击接受量房、设计交付物(或者签订设计合同)
     *
     * @param wk_cur_sub_node_idi
     * @param view
     */
    private void establishContract(int wk_cur_sub_node_idi, View view) {
        if (Constant.UerInfoKey.DESIGNER_TYPE.equals(strMemberType)) {
            if (wk_cur_sub_node_idi == 21 && wk_cur_sub_node_idi != 33) { /// 设计合同 .
                showNewActivity(FlowEstablishContractActivity.class, -1);
            } else if (wk_cur_sub_node_idi == 33) { /// 量房交付物 .
                showNewActivity(FlowUploadDeliveryActivity.class, -1);

            } else {
                view.setClickable(false);
            }
            return;
        }
        if (Integer.parseInt(wk_cur_sub_node_id) == 21 || Integer.parseInt(wk_cur_sub_node_id) == 22) {
            new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.please_wait_designer_send_contract), null, new String[]{UIUtils.getString(R.string.sure)}, null, WkFlowStateActivity.this,
                    AlertView.Style.Alert, null).show();
        } else if (Integer.parseInt(wk_cur_sub_node_id) == 31 && Integer.parseInt(wk_cur_sub_node_id) != 33) {
            showNewActivity(FlowEstablishContractActivity.class, -1);
        } else {
            view.setClickable(false);
        }


    }

    /**
     * 点击 支付设计首款(或者 接收设计首款)
     *
     * @param wk_cur_sub_node_idi
     * @param view
     */
    private void firstPay(int wk_cur_sub_node_idi, View view) {
        if (Constant.UerInfoKey.DESIGNER_TYPE.equals(strMemberType)) {
            if (wk_cur_sub_node_idi == 32) {
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.please_wait_consumer_send_design_first), null, new String[]{UIUtils.getString(R.string.sure)}, null, WkFlowStateActivity.this,
                        AlertView.Style.Alert, null).show();
            } else {
                view.setClickable(false);
            }
            return;
        }
        if (wk_cur_sub_node_idi == 32) {
            showNewActivity(FlowFirstDesignActivity.class, MPStatusMachine.NODE__DESIGN_FIRST_PAY);
        } else {
            view.setClickable(false);
        }

    }

    /**
     * 点击支付设计尾款(或者 接收接受设计尾款)
     *
     * @param wk_cur_sub_node_idi
     * @param view
     */
    private void lastPay(int wk_cur_sub_node_idi, View view) {
        if (Constant.UerInfoKey.DESIGNER_TYPE.equals(strMemberType)) {
            if (wk_cur_sub_node_idi == 41 || wk_cur_sub_node_idi == 42) {
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.please_wait_consumer_send_design_end), null, new String[]{UIUtils.getString(R.string.sure)}, null, WkFlowStateActivity.this, AlertView.Style.Alert, null).show();
            } else {
                view.setClickable(false);
            }
            return;
        }
        if (wk_cur_sub_node_idi == 41) {
            showNewActivity(FlowLastDesignActivity.class, MPStatusMachine.NODE__DESIGN_BALANCE_PAY);
        }

    }

    /**
     * 点击接收设计交付物(或者 设计交付物)
     *
     * @param wk_cur_sub_node_idi
     * @param view
     */
    private void deliver(int wk_cur_sub_node_idi, View view) {
        if (Constant.UerInfoKey.DESIGNER_TYPE.equals(strMemberType)) {
            if (wk_cur_sub_node_idi >= 51 && wk_cur_sub_node_idi != 63) {
                showNewActivity(FlowUploadDeliveryActivity.class, -1);
            }
            return;
        }
        if (wk_cur_sub_node_idi >= 51&& wk_cur_sub_node_idi != 63) {
            showNewActivity(FlowUploadDeliveryActivity.class, -1);

        }

    }

    /**
     * 打开每个节点对于的Activity
     */
    private void showNewActivity(Class activity, int pay) {
        Intent mIntent = new Intent(WkFlowStateActivity.this, activity);
        mIntent.putExtra(Constant.SeekDesignerDetailKey.NEEDS_ID, needs_id);
        mIntent.putExtra(Constant.SeekDesignerDetailKey.DESIGNER_ID, WkFlowStateActivity.this.designer_id);
        if (pay != -1) {
            mIntent.putExtra(Constant.BundleKey.TEMPDATE_ID, pay);
        }
        startActivity(mIntent);
    }


//    /**
//     * 点击右上角资料进行的操作
//     *
//     * @param view 右上角的控件
//     */
//    @Override
//    protected void rightNavButtonClicked(View view) {
//        Intent maIntent = new Intent(WkFlowStateActivity.this, ProjectMaterialActivity.class);      /// 跳转项目资料界面 .
//        maIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_IM);
//        maIntent.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_DESIGNER_ID, designer_id);
//        maIntent.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_NEEDS_ID, needs_id);
//        startActivity(maIntent);
//    }

    @Override
    protected void onWorkFlowData() {
        super.onWorkFlowData();
        setTitleForNavbar(contacts_name + "/" + community_name); /// 设置标题 .
        tvCreateDate.setText(UIUtils.getString(R.string.create_date) + requirement.getPublish_time());
        mPtrLayout.onRefreshComplete();
        if (Constant.UerInfoKey.CONSUMER_TYPE.equals(memberEntity.getMember_type())) {
            int sub_node_id = wk_cur_sub_node_id != null ? Integer.parseInt(wk_cur_sub_node_id) : -1;

            if (WkTemplateConstants.IS_FOUR == wk_cur_template_id) {
                rlStopContract.setVisibility(View.VISIBLE);
            } else {
                rlStopContract.setVisibility(View.GONE);
            }

            if (sub_node_id >= 11 && sub_node_id < 41 && sub_node_id != 24 && sub_node_id != 33) {
                btnStopDemand.setVisibility(View.VISIBLE);
            }
        }

        List<TipWorkFlowTemplateBean> tipWorkFlowTemplateBeanMap = WkFlowStateMap.sWkFlowBeans;
        for (TipWorkFlowTemplateBean tipWorkFlowTemplateBean : tipWorkFlowTemplateBeanMap) {
            if (tipWorkFlowTemplateBean.getTempdate_id() == WorkFlowTemplateStep()) {
                this.tipWorkFlowTemplateBean = tipWorkFlowTemplateBean;
            }
        }

        mAdapter = new WkFlowStateAdapter(context, memberEntity.getMember_type(), mBiddersEntity, tipWorkFlowTemplateBean, WorkFlowTemplateStep());
        mListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        ImageUtils.displayAvatarImage(mBiddersEntity.getAvatar(), polygonImageView);
        tvDesignerName.setText(mBiddersEntity.getUser_name());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPtrLayout.autoRefresh();
    }

    private void refreshWkFlowState() {
        if (TextUtils.isEmpty(wk_cur_sub_node_id)) {
            return;
        }
        boolean numeric = StringUtils.isNumeric(wk_cur_sub_node_id);
        if (!numeric) {
            return;
        }
        intent.putExtra(Constant.BundleKey.BUNDLE_SUB_NODE_ID, wk_cur_sub_node_id);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        refreshWkFlowState();
    }

    private ListViewFinal mListView;
    private PtrClassicFrameLayout mPtrLayout;
    private TipWorkFlowTemplateBean tipWorkFlowTemplateBean;
    private LinearLayout right_contain;
    private ImageView demandDetails;
    private ImageView projectInformation;
    private ImageButton ibFlowChart;
    private RelativeLayout rl_piv;
    private RelativeLayout rlStopContract;
    private PolygonImageView polygonImageView;
    private Button btnStopDemand;
    private TextView tvDesignerName, tvCreateDate;
    private String demand_type;
    private boolean bid_status;
    private AlertView alertView;
    private String strMemberType = null;
    private Intent intent;
    private Context context;
    private WkFlowStateAdapter mAdapter;
}