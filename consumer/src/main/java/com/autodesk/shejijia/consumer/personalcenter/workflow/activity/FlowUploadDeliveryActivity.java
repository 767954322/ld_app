package com.autodesk.shejijia.consumer.personalcenter.workflow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.consumer.activity.AppraiseDesignerActivity;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.DeliveryDelayBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPDesignFileBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPFileBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPThreeDimensBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.Wk3DPlanBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.Wk3DPlanDelivery;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.Wk3DPlanListBean;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.DeliverySelector;
import com.autodesk.shejijia.shared.components.common.uielements.MyToast;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author he.liu .
 * @version 1.0 .
 * @date 16-6-7
 * @file FlowUploadDeliveryActivity.java  .
 * @brief 上传量房、设计交付物 .
 */
public class FlowUploadDeliveryActivity extends BaseWorkFlowActivity implements View.OnClickListener, OnItemClickListener {

    public static final int PAY_FOR_MEASURE = 21;
    public static final int OPEN_3D_DESIGN = 22;
    public static final int DELIVER_MEASURE_FILE = 23; //  精选，消费者：等待提交量房交付物　设计师：提交量房交付物
    public static final int DELIVER_MEASURE_FILE_1 = 33; // 精选-竞优-套餐，消费者：订单结束, 设计师：订单结束 未上传量房交付物
    public static final int PAY_FOR_FIRST_FEE = 41; // 消费者：未支付设计尾款　设计师：等待客户支付设计师尾款 ．
    public static final int NO_UPLOAD_DELIVERY = 51; // 消费者：等待设计师上传设计交付物　设计师：未上传设计交付物
    public static final int DOWN_DELIVERY_UPLOADED_DELIVERY = 61;// 消费者：未确认设计交付物　设计师：等待客户确认设计交付物．
    public static final int CONSUMER_AFFIRM_DELIVERY = 63; // 设计完成．
    public static final int CONSUMER_DELAY_DELIVERY = 64;//消费者：未确认设计交付物　设计师：客户已延期确认设计交付.
    public static final int DELAY_ESTIMATE = 72;//设计完成　评价.
    public static final int ESTIMATE = 71;//设计完成　评价.


    private List<MPFileBean> mFiles;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_flow_upload_deliverables;
    }

    @Override
    protected void initView() {
        super.initView();
        mLl3DPlan = (LinearLayout) findViewById(R.id.ll_3d_plan);
        mLlDesignApply = (LinearLayout) findViewById(R.id.ll_design_apply);
        mLlDesignPager = (LinearLayout) findViewById(R.id.ll_design_pager);
        mLlMaterialList = (LinearLayout) findViewById(R.id.ll_material_list);
        mBtnUploadSubmit3DPlan = (Button) findViewById(R.id.btn_upload_submit_3dplan);
        mTvCommunityName = (TextView) findViewById(R.id.tv_community_name);
        mTvDelivery = (TextView) findViewById(R.id.tv_delivery);

        mTvInstruction = (TextView) findViewById(R.id.tv_delayed_instruction);                 ///延期说明 .
        mTvDelayedDays = (TextView) findViewById(R.id.tv_delayed_day);                          /// 延期天数.
        mBtnDeliverySure = (Button) findViewById(R.id.btn_delivery_consumer_sure);     /// 确认.
        mLinerDelayedShow = (LinearLayout) findViewById(R.id.ll_delayed_show);           /// 控制显示延期或者确认按钮.

        /// 交付物的几种类型 .
        mIv3DPlan = (ImageView) findViewById(R.id.iv_3d_plan);
        mIvDesignApply = (ImageView) findViewById(R.id.iv_design_apply);
        mIvDesignPager = (ImageView) findViewById(R.id.iv_design_pager);
        mIvMaterialList = (ImageView) findViewById(R.id.iv_material_list);
        mBtnDelay = (Button) findViewById(R.id.btn_flow_upload_deliverable_delay);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        CustomProgress.show(this, "", false, null);

        initAlertView();

    }

    /**
     * 获取订单信息，之后执行的操作
     */
    @Override
    protected void onWorkFlowData() {
        super.onWorkFlowData();
        CustomProgress.cancelDialog();

        wk_sub_node_id_int = Integer.parseInt(wk_cur_sub_node_id);
        community_name = requirement.getCommunity_name();
        mTvCommunityName.setText(community_name);

        /// [1]节点33，设计师尚未上传交付物，mDeliveryBean为null，提示：消费者等待，设计师上传 .
        /// [2]如果mDeliveryBean不为null，说明已经设计师已经上传了交付物，消费者可以查看，需要隐藏延期及确认按钮.
        boolean isMeasureDelivery = isMeasureDelivery(wk_sub_node_id_int);
        if (isMeasureDelivery) {
            setTitleForNavbar(UIUtils.getString(R.string.deliver_measure_consumer));
            mTvDelivery.setText(UIUtils.getString(R.string.deliver_measure_consumer));
            show3DAndHideLevel();
            handleMeasureDelivery();

        } else {
            setTitleForNavbar(UIUtils.getString(R.string.deliver_consumer));
            mTvDelivery.setText(UIUtils.getString(R.string.three_plan));
            showAllLevel();
            handleDesignDelivery();
            /**
             * 延期时间判断
             */
            if (wk_sub_node_id_int > 51) {
                getFlowUploadDeliveryDelayDate(needs_id, designer_id);
            }
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mLl3DPlan.setOnClickListener(FlowUploadDeliveryActivity.this);
        mBtnDelay.setOnClickListener(FlowUploadDeliveryActivity.this);
        mTvInstruction.setOnClickListener(FlowUploadDeliveryActivity.this);
        mBtnDeliverySure.setOnClickListener(FlowUploadDeliveryActivity.this);
    }


    /**
     * 处理设计交付的逻辑
     */
    private void handleDesignDelivery() {
        Wk3DPlanDelivery delivery = new Wk3DPlanDelivery();
        /// 尚未上传设计交付物的情况考虑 .
        if (wk_sub_node_id_int == NO_UPLOAD_DELIVERY) {
            handleDeliveryState(wk_sub_node_id_int, delivery);
        } else {
            /// 已经上传设计交付物，包含重复提交的情况考虑 .
            if (null == mDeliveryBean) {
                return;
            }
            mFiles = mDeliveryBean.getFiles();
            if (null == mFiles) {
                return;
            }
            delivery.setDeliveryFiles(mFiles);
            handleDeliveryState(wk_sub_node_id_int, delivery);
        }
    }

    /**
     * 处理量房交付的逻辑
     */
    private void handleMeasureDelivery() {
        Wk3DPlanDelivery delivery = new Wk3DPlanDelivery();
        if (mDeliveryBean == null) {
            get3DPlan(needs_id, designer_id);
        } else {
            mFiles = mDeliveryBean.getFiles();
            if (null == mFiles) {
                return;
            }

            delivery.setDeliveryFiles(mFiles);
            deliveryFilesFormat(delivery);
        }
    }

    /**
     * 处理设计交付的不同状态
     * wk_sub_node_id
     * 33: 量房交付物
     * <p/>
     * <61 : 消费者，等待设计师上传设计交付物
     * 设计师，上传设计交付物
     * <p/>
     * =61: 消费者，确认或者延期的状态，消费者未操作 .
     * 　　  设计师：可以重新发送设计交付物（显示发送按钮）
     * =63:
     * 　　消费者进行了确认操作
     * =64
     * 　　消费者做了延期操作
     * >64(71,72)
     * 　　消费者需要进行评价
     */
    private void handleDeliveryState(int wk_sub_node_id_int, Wk3DPlanDelivery delivery) {
        if (wk_sub_node_id_int < DOWN_DELIVERY_UPLOADED_DELIVERY) {
            switch (GetRoleType()) {
                case Constant.UerInfoKey.CONSUMER_TYPE:
                    mBtnUploadSubmit3DPlan.setVisibility(View.GONE);
                    mLinerDelayedShow.setVisibility(View.GONE);
                    mAlertViewDesignConsumerDelivery.show();
                    break;

                case Constant.UerInfoKey.DESIGNER_TYPE:
                    mBtnUploadSubmit3DPlan.setVisibility(View.VISIBLE);
                    mLinerDelayedShow.setVisibility(View.GONE);
                    get3DPlan(needs_id, designer_id);
                    break;
            }
        } else if (DOWN_DELIVERY_UPLOADED_DELIVERY == wk_sub_node_id_int) {
            switch (GetRoleType()) {
                case Constant.UerInfoKey.CONSUMER_TYPE:
                    mBtnUploadSubmit3DPlan.setVisibility(View.GONE);
                    mLinerDelayedShow.setVisibility(View.VISIBLE);
                    deliveryFilesFormat(delivery);

                    break;

                case Constant.UerInfoKey.DESIGNER_TYPE:
                    mBtnUploadSubmit3DPlan.setVisibility(View.VISIBLE);
                    mLinerDelayedShow.setVisibility(View.GONE);
                    get3DPlan(needs_id, designer_id);
                    break;
            }
        } else if (wk_sub_node_id_int == CONSUMER_DELAY_DELIVERY) {
            switch (GetRoleType()) {
                case Constant.UerInfoKey.CONSUMER_TYPE:
                    mBtnUploadSubmit3DPlan.setVisibility(View.GONE);
                    mLinerDelayedShow.setVisibility(View.VISIBLE);
                    mBtnDelay.setEnabled(false);
                    mBtnDelay.setBackgroundResource(R.drawable.bg_common_btn_pressed);
                    mBtnDelay.setTextColor(UIUtils.getColor(R.color.white));

                    deliveryFilesFormat(delivery);
                    break;

                case Constant.UerInfoKey.DESIGNER_TYPE:
                    mBtnUploadSubmit3DPlan.setVisibility(View.VISIBLE);
                    mLinerDelayedShow.setVisibility(View.GONE);
                    get3DPlan(needs_id, designer_id);
                    break;
            }
        } else if (wk_sub_node_id_int == CONSUMER_AFFIRM_DELIVERY
                || wk_sub_node_id_int > CONSUMER_DELAY_DELIVERY) {
            mLinerDelayedShow.setVisibility(View.GONE);
            mBtnUploadSubmit3DPlan.setVisibility(View.GONE);
            deliveryFilesFormat(delivery);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_flow_upload_deliverable_delay:   /// 延期按钮 . // TODO 执行延期交付的操作.
                mDelayAlertView.show();
                break;

            case R.id.tv_delayed_instruction:             /// 延期说明 .
                Intent intent = new Intent(FlowUploadDeliveryActivity.this, DeliveryDelayedInstructionsActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_delivery_consumer_sure:       /// 确认交付 .　// TODO 执行确认交付的操作.
                makeSureDelivery();
                break;

            default:
                Intent intent3DPlan = new Intent(this, Wk3DPlanActivity.class);
                /**
                 * 交付状态进行中
                 */
                if (mFiles == null || mFiles.size() < 1) {
                    if (Integer.valueOf(wk_cur_sub_node_id) >= PAY_FOR_MEASURE
                            && Integer.valueOf(wk_cur_sub_node_id) < PAY_FOR_FIRST_FEE) {
                        /**
                         * 量房交付中
                         */
                        doingMeasureDelivery(v, intent3DPlan);
                    } else if (Integer.valueOf(wk_cur_sub_node_id) >= NO_UPLOAD_DELIVERY) {
                        /**
                         * 设计交付中
                         */
                        doingDesignDelivery(v, intent3DPlan);
                    }
                } else {
                    /**
                     *交付完成
                     */
                    if (Integer.valueOf(wk_cur_sub_node_id) >= OPEN_3D_DESIGN
                            && Integer.valueOf(wk_cur_sub_node_id) < PAY_FOR_FIRST_FEE) {
                        /**
                         * 量房交付完成
                         */
                        doneMeasureDelivery(v, intent3DPlan);
                    } else if (Integer.valueOf(wk_cur_sub_node_id) >= DOWN_DELIVERY_UPLOADED_DELIVERY) {
                        switch (GetRoleType()) {
                            case Constant.UerInfoKey.CONSUMER_TYPE:
                                /**
                                 * 设计师已经上传了设计交付物
                                 */
                                doneDesignDelivery(v, intent3DPlan);
                                break;

                            case Constant.UerInfoKey.DESIGNER_TYPE:
                                if (DOWN_DELIVERY_UPLOADED_DELIVERY == wk_sub_node_id_int
                                        || wk_sub_node_id_int == CONSUMER_DELAY_DELIVERY) {
                                    /**
                                     * 设计师
                                     */
                                    doingDesignDelivery(v, intent3DPlan);
                                } else {
                                    /**
                                     * 消费者已经确认交付物
                                     */
                                    doneDesignDelivery(v, intent3DPlan);
                                }
                                break;
                        }
                    }
                }
                break;
        }
    }

    @Override
    protected void leftNavButtonClicked(View view) {
        DeliverySelector.select_design_asset_id = "";
        DeliverySelector.select_design_file_id_map.clear();
        finish();
    }

    @Override
    public void onItemClick(Object object, int position) {
        if (object == mAlertViewExt
                || object == mAlertViewDesignConsumerDelivery
                || object == mAlertViewMeasureDelivery
                || object == mAlertViewMeasureConsumerDelivery
                || object == mAlertViewDesignDelivery
                && position != AlertView.CANCELPOSITION) {
            FlowUploadDeliveryActivity.this.finish();
        }
        /**
         * 确定延期
         */
        if (object == mDelayAlertView && position != AlertView.CANCELPOSITION) {
            delayDelivery(needs_id, designer_id);
        }
        /**
         * 延期成功
         */
        if (object == mDelaySuccessAlertView && position != AlertView.CANCELPOSITION) {
            mBtnDelay.setEnabled(false);
            mBtnDelay.setBackgroundResource(R.drawable.bg_common_btn_pressed);
            mBtnDelay.setTextColor(UIUtils.getColor(R.color.white));
        }

        if (object == mDeliverySureAlertView && position != AlertView.CANCELPOSITION) {
            mLinerDelayedShow.setVisibility(View.GONE);
            Intent intent = new Intent(this, AppraiseDesignerActivity.class);
            intent.putExtra(BIDDER_ENTITY, mBiddersEntity);
            intent.putExtra(Constant.SeekDesignerDetailKey.DESIGNER_ID, designer_id);
            intent.putExtra(Constant.SeekDesignerDetailKey.NEEDS_ID, needs_id);
            startActivityForResult(intent, BIDDER_ENTITY_TAG);
        } else if (object == mDeliverySureAlertView) {
            mLinerDelayedShow.setVisibility(View.GONE);
        }
    }

    /**
     * 量房交付完成
     */
    private void doneMeasureDelivery(View v, Intent intent3DPlan) {
        switch (v.getId()) {
            case R.id.ll_3d_plan:
                if (mDeliveryFilesEntitiesMeasure != null) {
                    Bundle bundle = new Bundle();
                    putBundleValue(4, 0, bundle);
                    bundle.putSerializable(Constant.DeliveryBundleKey.DELIVERY_ENTITY, mDeliveryFilesEntitiesMeasure);
                    intent3DPlan.putExtra(Constant.DeliveryBundleKey.LEVEL_BUNDLE, bundle);
                    startActivity(intent3DPlan);
                }
                break;
        }
    }

    /**
     * 设计交付完成
     */
    private void doneDesignDelivery(View v, Intent intent3DPlan) {
        Bundle bundle;
        switch (v.getId()) {
            case R.id.ll_3d_plan:
                if (null != mDeliveryFilesEntityArrayList
                        && mDeliveryFilesEntityArrayList.size() > 0) {
                    bundle = new Bundle();
                    ArrayList<MPFileBean> deliveryFilesEntitiesThreePlanRemove = new ArrayList<>();
                    deliveryFilesEntitiesThreePlanRemove.addAll(mDeliveryFilesEntityArrayList);
                    if (deliveryFilesEntitiesThreePlanRemove.size() > 0) {
                        mDeliveryFilesEntityArrayList.clear();
                        MPFileBean mpFileBean = deliveryFilesEntitiesThreePlanRemove.get(0);
                        String name = mpFileBean.getFiled_name();
                        name = TextUtils.isEmpty(name) ? "未命名" : name;
                        mpFileBean.setName(name);
                        mDeliveryFilesEntityArrayList.add(mpFileBean);
                    }
                    putBundleValue(0, 0, bundle);
                    bundle.putSerializable(Constant.DeliveryBundleKey.DELIVERY_ENTITY, mDeliveryFilesEntityArrayList);
                    intent3DPlan.putExtra(Constant.DeliveryBundleKey.LEVEL_BUNDLE, bundle);
                    startActivity(intent3DPlan);
                }
                break;

            case R.id.ll_design_apply:  /// 渲染图设计 .
                if (null != mDeliveryFilesEntitiesRendering
                        && mDeliveryFilesEntitiesRendering.size() > 0) {
                    bundle = new Bundle();
                    putBundleValue(1, 0, bundle);
                    bundle.putSerializable(Constant.DeliveryBundleKey.DELIVERY_ENTITY, mDeliveryFilesEntitiesRendering);
                    intent3DPlan.putExtra(Constant.DeliveryBundleKey.LEVEL_BUNDLE, bundle);
                    startActivity(intent3DPlan);
                }
                break;

            case R.id.ll_design_pager: /// 设计图纸 .
                if (null != mDeliveryFilesEntitiesDesignBlueprint
                        && mDeliveryFilesEntitiesDesignBlueprint.size() > 0) {
                    bundle = new Bundle();
                    putBundleValue(2, 0, bundle);
                    bundle.putSerializable(Constant.DeliveryBundleKey.DELIVERY_ENTITY, mDeliveryFilesEntitiesDesignBlueprint);
                    intent3DPlan.putExtra(Constant.DeliveryBundleKey.LEVEL_BUNDLE, bundle);
                    startActivity(intent3DPlan);
                }
                break;

            case R.id.ll_material_list: /// 材料清单 .
                if (null != mDeliveryFilesEntitiesMaterialBill && mDeliveryFilesEntitiesMaterialBill.size() > 0) {
                    bundle = new Bundle();
                    putBundleValue(3, 0, bundle);
                    bundle.putSerializable(Constant.DeliveryBundleKey.DELIVERY_ENTITY, mDeliveryFilesEntitiesMaterialBill);
                    intent3DPlan.putExtra(Constant.DeliveryBundleKey.LEVEL_BUNDLE, bundle);
                    startActivity(intent3DPlan);
                }
                break;
        }
    }

    /**
     * 设计交付中
     */
    private void doingDesignDelivery(View v, Intent intent3DPlan) {
        Bundle bundle;
        switch (v.getId()) {
            case R.id.ll_3d_plan:                 /// 3d方案 .
                /**
                 *  进入3D方案，选择其中一个，并返回选中的3d_asset_id
                 */
                if (null != mWk3DPlanListBeanArrayList
                        && mWk3DPlanListBeanArrayList.size() > 0) {
                    bundle = new Bundle();
                    putBundleValue(0, 1, bundle);
                    bundle.putSerializable(Constant.DeliveryBundleKey.THREE_PLAN_ALL, mWk3DPlanListBeanArrayList);
                    intent3DPlan.putExtra(Constant.DeliveryBundleKey.LEVEL_BUNDLE, bundle);
                    startActivityForResult(intent3DPlan, 0);
                }
                break;

            case R.id.ll_design_apply:          /// 渲染图设计 .
                if (null != mDesignFileEntities3DPlanRendering
                        && mDesignFileEntities3DPlanRendering.size() > 0) {
                    bundle = new Bundle();
                    putBundleValue(1, 1, bundle);
                    bundle.putSerializable(Constant.DeliveryBundleKey.THREE_PLAN, mDesignFileEntities3DPlanRendering);
                    intent3DPlan.putExtra(Constant.DeliveryBundleKey.LEVEL_BUNDLE, bundle);
                    startActivityForResult(intent3DPlan, 1);
                }
                break;

            case R.id.ll_design_pager:          /// 设计图纸 .
                if (null != mDesignFileEntities3DPlanDesignBlueprint
                        && mDesignFileEntities3DPlanDesignBlueprint.size() > 0) {
                    bundle = new Bundle();
                    putBundleValue(2, 1, bundle);
                    bundle.putSerializable(Constant.DeliveryBundleKey.THREE_PLAN, mDesignFileEntities3DPlanDesignBlueprint);
                    intent3DPlan.putExtra(Constant.DeliveryBundleKey.LEVEL_BUNDLE, bundle);
                    startActivityForResult(intent3DPlan, 2);
                }
                break;

            case R.id.ll_material_list:         /// 材料清单 .
                if (null != mDesignFileEntities3DPlanMaterialBill
                        && mDesignFileEntities3DPlanMaterialBill.size() > 0) {
                    bundle = new Bundle();
                    putBundleValue(3, 1, bundle);
                    bundle.putSerializable(Constant.DeliveryBundleKey.THREE_PLAN, mDesignFileEntities3DPlanMaterialBill);
                    intent3DPlan.putExtra(Constant.DeliveryBundleKey.LEVEL_BUNDLE, bundle);
                    startActivityForResult(intent3DPlan, 3);
                }
                break;

            case R.id.btn_upload_submit_3dplan:
                if (TextUtils.isEmpty(design_asset_id)) {
                    return;
                }
                StringBuilder builder = new StringBuilder();
                for (Map.Entry<Integer, ArrayList<String>> entry : DeliverySelector.select_design_file_id_map.entrySet()) {
                    for (String s : entry.getValue()) {
                        builder.append(s + ',');
                    }
                }
                type = "1";

                String design_file_ids = builder.toString();
                KLog.d(TAG, design_file_ids);
                postDelivery(design_asset_id, needs_id, designer_id, design_file_ids, type);
                break;
        }
    }

    /**
     * 量房交付中
     */
    private void doingMeasureDelivery(View view, Intent intent3DPlan) {
        switch (view.getId()) {
            case R.id.ll_3d_plan:
                if (mDesignFileEntitiesMeasure != null) {
                    Bundle bundle = new Bundle();
                    putBundleValue(4, 1, bundle);
                    bundle.putSerializable(Constant.DeliveryBundleKey.THREE_PLAN, mDesignFileEntitiesMeasure);
                    intent3DPlan.putExtra(Constant.DeliveryBundleKey.LEVEL_BUNDLE, bundle);
                    startActivityForResult(intent3DPlan, 4);
                }
                break;

            case R.id.btn_upload_submit_3dplan:
                ArrayList<String> design_file_id_measure_arrayList = DeliverySelector.select_design_file_id_map.get(4);
                StringBuilder stringBuilder = new StringBuilder();
                for (String s : design_file_id_measure_arrayList) {
                    stringBuilder.append(s + ',');
                }
                KLog.d(TAG, "design_file_id_measure_arrayList:" + design_file_id_measure_arrayList);
                type = "0";
                if (TextUtils.isEmpty(design_asset_id_measure)) {
                    return;
                }
                postDelivery(design_asset_id_measure, needs_id, designer_id, stringBuilder.toString(), type);
                break;
        }
    }

    /**
     * 为Bundle传递值
     *
     * @param level          　第几个交付物类型
     * @param delivery_state 　交付状态
     *                       ０　交付完成
     *                       １　正在交付中
     * @param bundle         要传值的bundle对象
     */
    private void putBundleValue(int level, int delivery_state, Bundle bundle) {
        bundle.putInt(Constant.DeliveryBundleKey.LEVEL, level);
        bundle.putInt(Constant.DeliveryBundleKey.DELIVERY_STATE, delivery_state);
    }

    /**
     * [1]获取与装修项目相关联的3D方案列表
     *
     * @param needs_id    　项目编号
     * @param designer_id 　用户id
     */
    private void get3DPlan(final String needs_id, String designer_id) {
        CustomProgress.show(FlowUploadDeliveryActivity.this, "", false, null);
        MPServerHttpManager.getInstance().get3DPlanInfoData(needs_id, designer_id, new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                String userInfo = GsonUtil.jsonToString(jsonObject);
                Wk3DPlanBean wk3DPlanBean = GsonUtil.jsonToBean(userInfo, Wk3DPlanBean.class);
                updateViewFrom3DPlanData(wk3DPlanBean);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError, true);
                CustomProgress.cancelDialog();
                ApiStatusUtil.getInstance().apiStatuError(volleyError, FlowUploadDeliveryActivity.this);
            }
        });
    }

    /**
     * [2]获取3D方案的文件列表
     *
     * @param needs_id        　项目编号
     * @param design_asset_id 通过3d_asset_id获取相应的3D方案
     */
    private void get3DPlanList(String needs_id, final String design_asset_id) {
        MPServerHttpManager.getInstance().get3DPlanList(needs_id, design_asset_id, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject != null) {
                        String userInfo = GsonUtil.jsonToString(jsonObject);
                        KLog.json(TAG, userInfo);
                        mWk3DPlanListBean = GsonUtil.jsonToBean(userInfo, Wk3DPlanListBean.class);
                        updateViewFrom3DPlanList(design_asset_id);
                    } else {
                        new AlertView(" ",
                                UIUtils.getString(R.string.fanganflow),
                                null, null,
                                new String[]{UIUtils.getString(R.string.sure)},
                                FlowUploadDeliveryActivity.this,
                                AlertView.Style.Alert,
                                FlowUploadDeliveryActivity.this).show();

                    }

                } catch (Exception e) {

                    Toast.makeText(FlowUploadDeliveryActivity.this, "e:" + e, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError, true);
                CustomProgress.cancelDialog();
                ApiStatusUtil.getInstance().apiStatuError(volleyError, FlowUploadDeliveryActivity.this);

            }
        });
    }

    /**
     * 提交交付物[量房、设计交付物]
     *
     * @param needs_id        　项目订单编号
     * @param design_asset_id 　3d方案的assets_id
     */
    private void postDelivery(String design_asset_id, String needs_id, String designer_id, String file_ids, String type) {
        CustomProgress.show(FlowUploadDeliveryActivity.this, UIUtils.getString(R.string.in_design_deliverable_zero), false, null);
        MPServerHttpManager.getInstance().postDelivery(needs_id, designer_id, file_ids, design_asset_id, type, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                mAlertViewExt.show();
                String userInfo = GsonUtil.jsonToString(jsonObject);
                KLog.json(TAG, userInfo);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError, true);
                CustomProgress.cancelDialog();
                ApiStatusUtil.getInstance().apiStatuError(volleyError, FlowUploadDeliveryActivity.this);

            }
        });
    }

    /**
     * 获取延期时间
     */
    private void getFlowUploadDeliveryDelayDate(String demands_id, String designer_id) {
        CustomProgress.showDefaultProgress(FlowUploadDeliveryActivity.this);
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                String jsonToString = GsonUtil.jsonToString(jsonObject);
                DeliveryDelayBean deliveryDelayBean = GsonUtil.jsonToBean(jsonToString, DeliveryDelayBean.class);
                mTvDelayedDays.setText(deliveryDelayBean.remain_days + "天");
                if (!"0".equals(deliveryDelayBean.delayCount)) {
                    mBtnDelay.setEnabled(false);
                    mBtnDelay.setBackgroundResource(R.drawable.bg_common_btn_pressed);
                    mBtnDelay.setTextColor(UIUtils.getColor(R.color.white));
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CustomProgress.cancelDialog();
                MPNetworkUtils.logError(TAG, volleyError, true);
                ApiStatusUtil.getInstance().apiStatuError(volleyError, FlowUploadDeliveryActivity.this);

            }
        };

        MPServerHttpManager.getInstance().getFlowUploadDeliveryDelayDate(demands_id, designer_id, okResponseCallback);
    }

    /**
     * 消费者发送延期交付
     */
    public void delayDelivery(String demands_id, String designer_id) {
        CustomProgress.show(FlowUploadDeliveryActivity.this, "", false, null);
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                mDelaySuccessAlertView.show();
                String jsonToString = GsonUtil.jsonToString(jsonObject);
                DeliveryDelayBean deliveryDelayBean = GsonUtil.jsonToBean(jsonToString, DeliveryDelayBean.class);
                mTvDelayedDays.setText(deliveryDelayBean.remain_days + "天");
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CustomProgress.cancelDialog();
                MPNetworkUtils.logError(TAG, volleyError, true);
                ApiStatusUtil.getInstance().apiStatuError(volleyError, FlowUploadDeliveryActivity.this);

            }
        };
        MPServerHttpManager.getInstance().getFlowUploadDeliveryDelay(demands_id, designer_id, okResponseCallback);
    }

    /**
     * 消费者发送确认交付
     */
    private void makeSureDelivery() {
        //     needs_id  对应于　demands_id
        CustomProgress.show(FlowUploadDeliveryActivity.this, "", false, null);
        MPServerHttpManager.getInstance().makeSureDelivery(needs_id, designer_id, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                /// TODO 九月份任务，暂时屏蔽评价入口 .
//                mLinerDelayedShow.setVisibility(View.GONE);
                mDeliverySureAlertView.show();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) { /// 204 .
                CustomProgress.cancelDialog();
                ApiStatusUtil.getInstance().apiStatuError(volleyError, FlowUploadDeliveryActivity.this);
                MPNetworkUtils.logError(TAG, volleyError, true);
            }
        });
    }


    /**
     * 解析获取到的当前所有3D方案，若有多个3D方案，其中每个3D方案的{3d_asset_id},即：design_asset_id 不同
     *
     * @param wk3DPlanBean 3D设计方案的实体类
     */
    private void updateViewFrom3DPlanData(Wk3DPlanBean wk3DPlanBean) {
        String design_asset_id;

        ArrayList<MPThreeDimensBean> threeDimensionalEntities =
                (ArrayList<MPThreeDimensBean>) wk3DPlanBean.getThree_dimensionals();

        if (null == threeDimensionalEntities || threeDimensionalEntities.size() < 1) {
            CustomProgress.cancelDialog();
            alertMeasureOrDesign();
            return;
        }

        /**
         * 每个3D方案的3d_asset_id
         */

        if (wk_sub_node_id_int >= PAY_FOR_MEASURE
                && wk_sub_node_id_int < PAY_FOR_FIRST_FEE) {
            /**
             * 量房交付的列表
             */
            design_asset_id = threeDimensionalEntities.get(0).getDesign_asset_id();
            get3DPlanList(needs_id, design_asset_id);
        } else {
            /**
             * 设计交付列表
             */
            for (MPThreeDimensBean threeDimensionalEntity : threeDimensionalEntities) {
                design_asset_id = threeDimensionalEntity.getDesign_asset_id();
                get3DPlanList(needs_id, design_asset_id);
            }
        }
    }

    /**
     * 根据选中的某个3Ｄ方案，更新UI
     *
     * @param design_asset_id
     */
    private void updateViewFrom3DPlanList(String design_asset_id) {
        String type;
        /**
         *为其传递3d_asset_id
         */
        mWk3DPlanListBean.setDesign_asset_id(design_asset_id);
        ArrayList<MPDesignFileBean> design_file = (ArrayList<MPDesignFileBean>) mWk3DPlanListBean.getDesign_file();

        if (null == design_file || design_file.size() < 1) {
            /**
             * [1]提示网页提交量房或者设计交付物
             */
            CustomProgress.cancelDialog();
            alertMeasureOrDesign();
            return;

        } else {
            /**
             * 量房交付
             */
            CustomProgress.cancelDialog();
            mLl3DPlan.setOnClickListener(FlowUploadDeliveryActivity.this);
            if (wk_sub_node_id_int >= PAY_FOR_MEASURE && wk_sub_node_id_int < PAY_FOR_FIRST_FEE) {
                mLl3DPlan.setVisibility(View.VISIBLE);
                mIv3DPlan.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_measure_select));
                for (MPDesignFileBean designFileEntity : design_file) {
                    type = designFileEntity.getType();
                    if (Constant.DeliveryTypeBundleKey.USAGE_TYPE_DESIGN_BLUEPRINT_DELIVERY.equals(type)) {
                        mDesignFileEntitiesMeasure.add(designFileEntity);
                    }
                }
                mBtnUploadSubmit3DPlan.setVisibility(View.VISIBLE);
                design_asset_id_measure = design_asset_id;
            } else {
                clickLevel();
                showAllLevel();
                /**
                 * 设计交付,判断是否有type为10 的情况
                 */
                for (MPDesignFileBean designFileEntity : design_file) {
                    type = designFileEntity.getType();
                    if (Constant.DeliveryTypeBundleKey.USAGE_TYPE_THREE_PLAN_DELIVERY.equals(type)) {
                        /**
                         * 将type为10的designFile的link取出
                         */
                        mWk3DPlanListBean.setLink(designFileEntity.getLink());
                        mWk3DPlanListBeanArrayList.add(mWk3DPlanListBean);
                    }
                }
                if (mWk3DPlanListBeanArrayList.size() < 1) {
                    mAlertViewDesignDelivery.show();
                }
            }
        }
    }

    /**
     * 弹窗提示量房还是设计交付
     */
    private void alertMeasureOrDesign() {
        if (wk_sub_node_id_int >= PAY_FOR_MEASURE
                && wk_sub_node_id_int < PAY_FOR_FIRST_FEE) {
            switch (GetRoleType()) {
                case Constant.UerInfoKey.CONSUMER_TYPE:
                    mAlertViewMeasureConsumerDelivery.show();
                    break;

                case Constant.UerInfoKey.DESIGNER_TYPE:
                    mAlertViewMeasureDelivery.show();
                    break;
                default:
                    break;
            }
        } else if (wk_sub_node_id_int >= 42
                && wk_sub_node_id_int <= NO_UPLOAD_DELIVERY) {
            switch (GetRoleType()) {
                case Constant.UerInfoKey.CONSUMER_TYPE:
                    mAlertViewDesignConsumerDelivery.show();
                    break;

                case Constant.UerInfoKey.DESIGNER_TYPE:
                    mAlertViewDesignDelivery.show();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 交付物分类
     *
     * @param delivery
     */

    private void deliveryFilesFormat(Wk3DPlanDelivery delivery) {
        String type;
        String usage_type;
        for (MPFileBean fileBean : delivery.getDeliveryFiles()) {
            if (null == fileBean) {
                continue;
            }
            type = fileBean.getType();
            usage_type = fileBean.getUsage_type();
            if (Constant.DeliveryTypeBundleKey.TYPE_MEASURE_DELIVERY.equals(type)) {
                /**
                 * 量房订单
                 */
                show3DAndHideLevel();
                mIv3DPlan.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_measure_select));
                if (Constant.DeliveryTypeBundleKey.USAGE_TYPE_DESIGN_BLUEPRINT_DELIVERY.equals(usage_type)) {
                    mDeliveryFilesEntitiesMeasure.add(fileBean);
                }
            } else if (Constant.DeliveryTypeBundleKey.TYPE_DESIGN_DELIVERY.equals(type)) {
                /**
                 * 设计交付
                 */
                showAllLevel();
                setSelectIcon();
                clickLevel();

                if (Constant.DeliveryTypeBundleKey.USAGE_TYPE_THREE_PLAN_DELIVERY.equals(usage_type)) {
                    mDeliveryFilesEntityArrayList.add(fileBean);
                } else if (Constant.DeliveryTypeBundleKey.USAGE_TYPE_RENDERING_DESIGN_DELIVERY_0.equals(usage_type)
                        || Constant.DeliveryTypeBundleKey.USAGE_TYPE_READERING_DESIGN_DELIVERY_4.equals(usage_type)) {
                    mDeliveryFilesEntitiesRendering.add(fileBean);
                } else if (Constant.DeliveryTypeBundleKey.USAGE_TYPE_DESIGN_BLUEPRINT_DELIVERY.equals(usage_type)) {
                    mDeliveryFilesEntitiesDesignBlueprint.add(fileBean);
                } else if (Constant.DeliveryTypeBundleKey.USAGE_TYPE_MATERIAL_BILL_DELIVERY.equals(usage_type)) {
                    mDeliveryFilesEntitiesMaterialBill.add(fileBean);
                }
            }
        }
    }

    /**
     * 量房交付
     * 判断量房交付时候确认按钮问题
     */
    private void changeSubmitOkFromMeasureDelivery() {
        if (wk_sub_node_id_int >= PAY_FOR_MEASURE
                && wk_sub_node_id_int < PAY_FOR_FIRST_FEE) {
            ArrayList<String> design_file_id_measure_arrayList = DeliverySelector.select_design_file_id_map.get(4);
            if (design_file_id_measure_arrayList != null && design_file_id_measure_arrayList.size() > 0) {
                sureSubmit();
            } else {
                cancelSubmit();
            }
        }

        if (wk_sub_node_id_int >= NO_UPLOAD_DELIVERY) {
            changeSubmitOkState();
        }
    }

    /**
     * 防止多次选中出现数据叠加问题
     */
    private void clearDesignFileEntitiesLevel() {
        mDesignFileEntities3DPlanRendering.clear();
        mDesignFileEntities3DPlanDesignBlueprint.clear();
        mDesignFileEntities3DPlanMaterialBill.clear();
    }

    /**
     * 判断是否可以提交
     */
    private void changeSubmitOkState() {
        cancelSubmit();
        if (DeliverySelector.select_design_file_id_map.size() < 4) {
            return;
        }
        m3DStrings = DeliverySelector.select_design_file_id_map.get(0);
        mRenderingStrings = DeliverySelector.select_design_file_id_map.get(1);
        mBlueprintStrings = DeliverySelector.select_design_file_id_map.get(2);
        mMaterialBillStrings = DeliverySelector.select_design_file_id_map.get(3);

        if (m3DStrings != null && m3DStrings.size() > 0
                && mRenderingStrings != null && mRenderingStrings.size() > 0
                && mBlueprintStrings != null && mBlueprintStrings.size() > 0
                && mMaterialBillStrings != null && mMaterialBillStrings.size() > 0) {
            sureSubmit();
        } else {
            cancelSubmit();
        }
    }

    /**
     * 判断是否可以点击选择
     */
    private void changeItemClickState() {

        if (TextUtils.isEmpty(DeliverySelector.select_design_asset_id)) {
            cancelOnClick();
            DeliverySelector.select_design_file_id_map.clear();
            clearDesignFileEntitiesLevel();
        } else {
            if (mDesignFileEntities3DPlanRendering == null || mDesignFileEntities3DPlanRendering.size() < 1) {
                mIvDesignApply.setImageDrawable(UIUtils.getDrawable(R.drawable.default_rendering_design_ico));
                mLlDesignApply.setOnClickListener(null);
            } else {
                mIvDesignApply.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_flow_rendering_press));
                mLlDesignApply.setOnClickListener(this);
            }

            if (mDesignFileEntities3DPlanDesignBlueprint == null || mDesignFileEntities3DPlanDesignBlueprint.size() < 1) {
                mIvDesignPager.setImageDrawable(UIUtils.getDrawable(R.drawable.default_design_drawings_ico));
                mLlDesignPager.setOnClickListener(null);
            } else {
                mIvDesignPager.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_flow_drawing_press));
                mLlDesignPager.setOnClickListener(this);
            }

            if (mDesignFileEntities3DPlanMaterialBill == null
                    || mDesignFileEntities3DPlanMaterialBill.size() < 1) {
                mIvMaterialList.setImageDrawable(UIUtils.getDrawable(R.drawable.default_materials_list_ico));
                mLlMaterialList.setOnClickListener(null);
            } else {
                mIvMaterialList.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_flow_inventory_press));
                mLlMaterialList.setOnClickListener(this);
            }
        }
    }

    /**
     * 不可以点击
     */
    private void cancelOnClick() {
        setDefaultIcon();
        cancelClick();
    }

    /**
     * 可发送状态
     */
    private void sureSubmit() {
        mBtnUploadSubmit3DPlan.setVisibility(View.VISIBLE);
        mBtnUploadSubmit3DPlan.setBackgroundResource(R.drawable.bg_common_btn_blue);
        mBtnUploadSubmit3DPlan.setOnClickListener(this);
    }

    /**
     * 不可发送状态
     */
    private void cancelSubmit() {
        mBtnUploadSubmit3DPlan.setVisibility(View.VISIBLE);
        mBtnUploadSubmit3DPlan.setBackgroundResource(R.drawable.bg_common_btn_pressed);
        mBtnUploadSubmit3DPlan.setOnClickListener(null);
    }

    /**
     * 交付完成以后各个条目显示的图片
     */
    private void setSelectIcon() {
        mIv3DPlan.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_flow_3d_press));
        mIvDesignApply.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_flow_rendering_press));
        mIvDesignPager.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_flow_drawing_press));
        mIvMaterialList.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_flow_inventory_press));
    }

    /**
     * 各个条目默认显示的图片
     */
    private void setDefaultIcon() {
        mIv3DPlan.setImageDrawable(UIUtils.getDrawable(R.drawable.default_design_scheme_ico));
        mIvDesignApply.setImageDrawable(UIUtils.getDrawable(R.drawable.default_rendering_design_ico));
        mIvDesignPager.setImageDrawable(UIUtils.getDrawable(R.drawable.default_design_drawings_ico));
        mIvMaterialList.setImageDrawable(UIUtils.getDrawable(R.drawable.default_materials_list_ico));
    }

    /**
     * 初始化弹窗
     */
    private void initAlertView() {
        mAlertViewExt = showAlertView(commonTip, UIUtils.getString(R.string.successful_delivery));
        mAlertViewDesignDelivery = showAlertView(commonTip, UIUtils.getString(R.string.please_enter_web_page_submitted_design_deliverable));
        mAlertViewDesignConsumerDelivery = showAlertView(commonTip, UIUtils.getString(R.string.waiting_designer_upload_design_deliverable));
        mAlertViewMeasureDelivery = showAlertView(commonTip, UIUtils.getString(R.string.please_enter_web_page_submitted_room_deliverable));
        mAlertViewMeasureConsumerDelivery = showAlertView(commonTip, UIUtils.getString(R.string.waiting_designer_uploaded_room_deliverable));

        mDelayAlertView = new AlertView(UIUtils.getString(R.string.flow_upload_delivery_delay), UIUtils.getString(R.string.flow_upload_delivery_delay_only),
                UIUtils.getString(R.string.cancel), null, new String[]{UIUtils.getString(R.string.sure)}, this, AlertView.Style.Alert, this);
        mDelaySuccessAlertView = new AlertView(UIUtils.getString(R.string.flow_upload_delivery_delay_success), UIUtils.getString(R.string.flow_upload_delivery_delay_contact_designer),
                null, null, new String[]{UIUtils.getString(R.string.sure)}, this, AlertView.Style.Alert, this);
        mDeliverySureAlertView = new AlertView(UIUtils.getString(R.string.delivery_sure_success), UIUtils.getString(R.string.delivery_sure_success_content),
                UIUtils.getString(R.string.delivery_later_evaluation), null, new String[]{UIUtils.getString(R.string.delivery_immediate_evaluation)}, this, AlertView.Style.Alert, this);
    }

    /**
     * 显示所有需要提交的item
     */
    private void showAllLevel() {
        mLl3DPlan.setVisibility(View.VISIBLE);
        mLlDesignApply.setVisibility(View.VISIBLE);
        mLlDesignPager.setVisibility(View.VISIBLE);
        mLlMaterialList.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏除了3D设计方案的item
     */
    private void show3DAndHideLevel() {
        mLl3DPlan.setVisibility(View.VISIBLE);
        mLlDesignApply.setVisibility(View.GONE);
        mLlDesignPager.setVisibility(View.GONE);
        mLlMaterialList.setVisibility(View.GONE);
    }

    /**
     * 取消可点击
     */
    private void cancelClick() {
        mLlDesignApply.setOnClickListener(null);
        mLlDesignPager.setOnClickListener(null);
        mLlMaterialList.setOnClickListener(null);
    }

    /**
     * 设置可点击
     */
    private void clickLevel() {
        mLlDesignApply.setOnClickListener(this);
        mLlDesignPager.setOnClickListener(this);
        mLlMaterialList.setOnClickListener(this);
    }

    /**
     * 获取 AlertView
     *
     * @param tip     提示标题
     * @param content 提示的内容
     * @return 　AlertView
     */
    private AlertView showAlertView(String tip, String content) {
        mCommonAlertView = new AlertView(tip, content, null, null, sureString, FlowUploadDeliveryActivity.this, AlertView.Style.Alert, FlowUploadDeliveryActivity.this);
        return mCommonAlertView;
    }

    /**
     * 根据选中的3D方案（需要的得到它的3d_asset_id）,联动接下来渲染设计、设计图纸和材料清单
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle;
        /**
         * 获取选中返回的3d方案id,然后将选中的方案的具体细节发送
         */
        if (resultCode == RESULT_OK && data != null) {
            bundle = data.getBundleExtra(Constant.DeliveryResponseBundleKey.RESPONSE_BUNDLE);
            switch (requestCode) {
                case 0:
                    // 选中的3D方案的design_asset_id .
                    design_asset_id = bundle.getString(Constant.DeliveryResponseBundleKey.DESIGN_ASSET_ID);
                    fileLink = bundle.getString(Constant.DeliveryResponseBundleKey.FILE_LINK);
                    if (!TextUtils.isEmpty(design_asset_id)) {
                        mIv3DPlan.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_flow_3d_press));
                    }

                    /// 当重复选择某个3D方案时候，清空历史记录 .
                    if (!TextUtils.isEmpty(design_asset_id) || !DeliverySelector.select_design_asset_id.equals(design_asset_id)) {
                        clearDesignFileEntitiesLevel();
                    }
                    DeliverySelector.select_design_asset_id = design_asset_id;
                    ArrayList arrayList = new ArrayList();
                    /// 选中的某一套3D方案  .
                    for (Wk3DPlanListBean wk3DPlanListBean : mWk3DPlanListBeanArrayList) {
                        if (wk3DPlanListBean.getDesign_asset_id().equals(design_asset_id)) {
                            this.mWk3DPlanListBean = wk3DPlanListBean;
                            List<MPDesignFileBean> designFileEntityList = wk3DPlanListBean.getDesign_file();
                            for (MPDesignFileBean designFileEntity : designFileEntityList) {
                                if (designFileEntity.getLink().equals(fileLink)) {
                                    arrayList.add(designFileEntity.getId());
                                }
                            }
                        }
                    }
                    DeliverySelector.select_design_file_id_map.put(0, arrayList);
                    /**
                     * 当前3D方案的详情
                     */
                    ArrayList<MPDesignFileBean> design_file = (ArrayList<MPDesignFileBean>) mWk3DPlanListBean.getDesign_file();
                    for (MPDesignFileBean designFileEntity : design_file) {
                        type = designFileEntity.getType();
                        if (Constant.DeliveryTypeBundleKey.USAGE_TYPE_RENDERING_DESIGN_DELIVERY_0.equals(type)
                                || Constant.DeliveryTypeBundleKey.USAGE_TYPE_READERING_DESIGN_DELIVERY_4.equals(type)) {
                            mDesignFileEntities3DPlanRendering.add(designFileEntity);
                        } else if (Constant.DeliveryTypeBundleKey.USAGE_TYPE_DESIGN_BLUEPRINT_DELIVERY.equals(type)) {
                            mDesignFileEntities3DPlanDesignBlueprint.add(designFileEntity);
                        } else if (Constant.DeliveryTypeBundleKey.USAGE_TYPE_MATERIAL_BILL_DELIVERY.equals(type)) {
                            mDesignFileEntities3DPlanMaterialBill.add(designFileEntity);
                        }
                    }
                    changeItemClickState();
                    break;

                case BIDDER_ENTITY_TAG: /// 评价完成或者取消评价，隐藏评价 .
                    mLinerDelayedShow.setVisibility(View.GONE);
                    break;
                case 4:
                default:
                    break;
            }
            changeSubmitOkFromMeasureDelivery();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            DeliverySelector.select_design_asset_id = "";
            DeliverySelector.select_design_file_id_map.clear();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!TextUtils.isEmpty(wk_cur_sub_node_id) && StringUtils.isNumeric(wk_cur_sub_node_id)) {
            if (Integer.valueOf(wk_cur_sub_node_id) >= PAY_FOR_MEASURE
                    && Integer.valueOf(wk_cur_sub_node_id) < PAY_FOR_FIRST_FEE) {
                /**
                 * 量房交付
                 */
                KLog.d(TAG, "design_file_id_arrayList:" + DeliverySelector.select_design_file_id_map.get(4));
                ArrayList<String> strings = DeliverySelector.select_design_file_id_map.get(4);
                if (strings != null && strings.size() > 0) {
                    sureSubmit();
                } else {
                    cancelSubmit();
                }

            } else if (Integer.valueOf(wk_cur_sub_node_id) >= NO_UPLOAD_DELIVERY) {
                /**
                 * 设计交付
                 */
                boolean isFinish = wk_sub_node_id_int > CONSUMER_DELAY_DELIVERY
                        || wk_sub_node_id_int == CONSUMER_AFFIRM_DELIVERY; /// 已经确认量房的状态 .
                if (isFinish) {
                    return;
                }
                if (isRoleDesigner()) {
                    changeItemClickState();
                    changeSubmitOkState();
                }
            }
        }
        if (null != mDeliveryFilesEntitiesMeasure && mDeliveryFilesEntitiesMeasure.size() > 0) {
            mBtnUploadSubmit3DPlan.setVisibility(View.GONE);
        }
        if (null != mDeliveryFilesEntityArrayList && mDeliveryFilesEntityArrayList.size() > 0) {
            mBtnUploadSubmit3DPlan.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != CustomProgress.dialog && CustomProgress.dialog.isShowing()) {
            CustomProgress.cancelDialog();
        }
    }

    public static final String BIDDER_ENTITY = "mBiddersEntity";
    public static final int BIDDER_ENTITY_TAG = 2;
    public static String fileLink;

    private TextView mTvDelivery;
    private TextView mTvCommunityName;
    private ImageView mIv3DPlan;
    private ImageView mIvDesignApply;
    private ImageView mIvDesignPager;
    private ImageView mIvMaterialList;
    private Button mBtnUploadSubmit3DPlan;  /// 发送 .
    private TextView mTvInstruction;
    private TextView mTvDelayedDays;
    private Button mBtnDeliverySure;
    private LinearLayout mLinerDelayedShow;

    private Button mBtnDelay;
    private AlertView mDelayAlertView;
    private AlertView mDeliverySureAlertView;
    private AlertView mDelaySuccessAlertView;

    private LinearLayout mLl3DPlan;
    private LinearLayout mLlDesignApply;
    private LinearLayout mLlDesignPager;
    private LinearLayout mLlMaterialList;

    private AlertView mAlertViewExt;
    private AlertView mAlertViewDesignDelivery;
    private AlertView mAlertViewMeasureDelivery;
    private AlertView mAlertViewDesignConsumerDelivery;
    private AlertView mAlertViewMeasureConsumerDelivery;
    private AlertView mCommonAlertView;
    private Wk3DPlanListBean mWk3DPlanListBean; //获取到当前3D方案

    private ArrayList<String> m3DStrings;
    private ArrayList<String> mRenderingStrings;
    private ArrayList<String> mBlueprintStrings;
    private ArrayList<String> mMaterialBillStrings;
    /**
     * 用于展示的当前3D的不同条目实体类.
     */
    private ArrayList<Wk3DPlanListBean> mWk3DPlanListBeanArrayList = new ArrayList<>();
    private ArrayList<MPFileBean> mDeliveryFilesEntityArrayList = new ArrayList<>();
    private ArrayList<MPFileBean> mDeliveryFilesEntitiesRendering = new ArrayList<>();
    private ArrayList<MPFileBean> mDeliveryFilesEntitiesDesignBlueprint = new ArrayList<>();
    private ArrayList<MPFileBean> mDeliveryFilesEntitiesMaterialBill = new ArrayList<>();
    private ArrayList<MPFileBean> mDeliveryFilesEntitiesMeasure = new ArrayList<>();
    /**
     * 选择的当前3D方案的不同条目设计图 .
     */
    private ArrayList<MPDesignFileBean> mDesignFileEntities3DPlanRendering = new ArrayList<>();
    private ArrayList<MPDesignFileBean> mDesignFileEntities3DPlanDesignBlueprint = new ArrayList<>();
    private ArrayList<MPDesignFileBean> mDesignFileEntities3DPlanMaterialBill = new ArrayList<>();
    private ArrayList<MPDesignFileBean> mDesignFileEntitiesMeasure = new ArrayList<>();

    private String design_asset_id;                                 /// 用于记录选中的设计图的id .
    private String community_name;
    private String design_asset_id_measure;

    private String type;                                             /// 交付类型:0：量房交付,1： 设计交付 .
    private String commonTip = UIUtils.getString(R.string.tip);
    private String[] sureString = new String[]{UIUtils.getString(R.string.sure)};
    private int wk_sub_node_id_int;                                   /// 当前wk_sub_node_id .

    /**
     * 是否是量房交付
     * true 是量房交付
     */
    public boolean isMeasureDelivery(int wk_sub_node_id_int) {
        if (DELIVER_MEASURE_FILE == wk_sub_node_id_int
                || DELIVER_MEASURE_FILE_1 == wk_sub_node_id_int) {
            return true;
        } else {
            return false;
        }
    }
}