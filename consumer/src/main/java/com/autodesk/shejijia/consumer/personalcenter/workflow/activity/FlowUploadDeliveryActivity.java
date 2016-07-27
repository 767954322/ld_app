package com.autodesk.shejijia.consumer.personalcenter.workflow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.Wk3DPlanBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.Wk3DPlanDelivery;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.Wk3DPlanListBean;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.DeliverySelector;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
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
        mBtnDelay = (Button) findViewById(R.id.flow_upload_deliverable_delay);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        initAlertView();

        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (memberEntity != null) {
            mMemberType = memberEntity.getMember_type();
        }
    }

    /**
     * 获取订单信息，之后执行的操作
     */
    @Override
    protected void onWorkFlowData() {
        super.onWorkFlowData();
        community_name = requirement.getCommunity_name();
        mTvCommunityName.setText(community_name);
        /**
         * 判断是不是已经有交付物
         */
        CustomProgress.showDefaultProgress(FlowUploadDeliveryActivity.this);

        getDeliveredFile(needs_id, designer_id);

        KLog.d(TAG, "needs_id:" + needs_id + "##designer_id:" + designer_id);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mLl3DPlan.setOnClickListener(FlowUploadDeliveryActivity.this);
        mBtnDelay.setOnClickListener(FlowUploadDeliveryActivity.this);
        mTvInstruction.setOnClickListener(FlowUploadDeliveryActivity.this);
        mBtnDeliverySure.setOnClickListener(FlowUploadDeliveryActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.flow_upload_deliverable_delay:    /// 延期按钮 .        // TODO 执行延期交付的操作.
                mDelayAlertView.show();
                break;

            case R.id.tv_delayed_instruction:                /// 延期说明 .
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
                if (mDeliveryFilesEntities == null || mDeliveryFilesEntities.size() < 1) {
                    if (Integer.valueOf(wk_cur_sub_node_id) >= 21 && Integer.valueOf(wk_cur_sub_node_id) < 41) {
                        /**
                         * 量房交付中
                         */
                        doingMeasureDelivery(v, intent3DPlan);
                    } else if (Integer.valueOf(wk_cur_sub_node_id) >= 51) {
                        /**
                         * 设计交付中
                         */
                        doingDesignDelivery(v, intent3DPlan);
                    }
                } else {
                    /**
                     *交付完成
                     */
                    if (Integer.valueOf(wk_cur_sub_node_id) >= 22 && Integer.valueOf(wk_cur_sub_node_id) < 41) {
                        /**
                         * 量房交付完成
                         */
                        doneMeasureDelivery(v, intent3DPlan);
                        /// TODO 原来条件是>=61 .
                    } else if (Integer.valueOf(wk_cur_sub_node_id) >= 61) {
                        /**
                         * 设计交付完成
                         */
                        doneDesignDelivery(v, intent3DPlan);
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
                && position != AlertView.CANCELPOSITION) {
            FlowUploadDeliveryActivity.this.finish();
        }

        if (object == mDelayAlertView && position != AlertView.CANCELPOSITION) {
            Toast.makeText(this, "延期", Toast.LENGTH_SHORT).show();
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
                if (null != mDeliveryFilesEntityArrayList && mDeliveryFilesEntityArrayList.size() > 0) {
                    bundle = new Bundle();
                    ArrayList<Wk3DPlanDelivery.DeliveryFilesEntity> deliveryFilesEntitiesThreePlanRemove = new ArrayList<>();
                    deliveryFilesEntitiesThreePlanRemove.addAll(mDeliveryFilesEntityArrayList);
                    if (deliveryFilesEntitiesThreePlanRemove.size() > 0) {
                        mDeliveryFilesEntityArrayList.clear();
                        Wk3DPlanDelivery.DeliveryFilesEntity deliveryFilesEntity = deliveryFilesEntitiesThreePlanRemove.get(0);
                        mDesign_name = TextUtils.isEmpty(mDesign_name) ? community_name : mDesign_name;
                        deliveryFilesEntity.setName(mDesign_name);
                        mDeliveryFilesEntityArrayList.add(deliveryFilesEntity);
                    }
                    putBundleValue(0, 0, bundle);
                    bundle.putSerializable(Constant.DeliveryBundleKey.DELIVERY_ENTITY, mDeliveryFilesEntityArrayList);
                    intent3DPlan.putExtra(Constant.DeliveryBundleKey.LEVEL_BUNDLE, bundle);
                    startActivity(intent3DPlan);
                }
                break;

            case R.id.ll_design_apply:  /// 渲染图设计 .
                if (null != mDeliveryFilesEntitiesRendering && mDeliveryFilesEntitiesRendering.size() > 0) {
                    bundle = new Bundle();
                    putBundleValue(1, 0, bundle);
                    bundle.putSerializable(Constant.DeliveryBundleKey.DELIVERY_ENTITY, mDeliveryFilesEntitiesRendering);
                    intent3DPlan.putExtra(Constant.DeliveryBundleKey.LEVEL_BUNDLE, bundle);
                    startActivity(intent3DPlan);
                }
                break;

            case R.id.ll_design_pager: /// 设计图纸 .

                if (null != mDeliveryFilesEntitiesDesignBlueprint && mDeliveryFilesEntitiesDesignBlueprint.size() > 0) {
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
            case R.id.ll_3d_plan:                   /// 3d方案 .
                /**
                 *  进入3D方案，选择其中一个，并返回选中的3d_asset_id
                 */
                if (null != mWk3DPlanListBeanArrayList && mWk3DPlanListBeanArrayList.size() > 0) {
                    bundle = new Bundle();
                    putBundleValue(0, 1, bundle);
                    bundle.putSerializable(Constant.DeliveryBundleKey.THREE_PLAN_ALL, mWk3DPlanListBeanArrayList);
                    intent3DPlan.putExtra(Constant.DeliveryBundleKey.LEVEL_BUNDLE, bundle);
                    startActivityForResult(intent3DPlan, 0);
                }
                break;
            case R.id.ll_design_apply:      /// 渲染图设计 .
                if (null == mWk3DPlanListBeanArrayList || mWk3DPlanListBeanArrayList.size() == 0) {
                    showAlertView(commonTip, UIUtils.getString(R.string.please_select_3d_design)).show();
                }
                if (null != mDesignFileEntities3DPlanRendering) {
                    bundle = new Bundle();
                    putBundleValue(1, 1, bundle);
                    bundle.putSerializable(Constant.DeliveryBundleKey.THREE_PLAN, mDesignFileEntities3DPlanRendering);
                    intent3DPlan.putExtra(Constant.DeliveryBundleKey.LEVEL_BUNDLE, bundle);
                    startActivityForResult(intent3DPlan, 1);
                }
                break;

            case R.id.ll_design_pager:      /// 设计图纸 .
                if (null != mDesignFileEntities3DPlanDesignBlueprint) {
                    bundle = new Bundle();
                    putBundleValue(2, 1, bundle);
                    bundle.putSerializable(Constant.DeliveryBundleKey.THREE_PLAN, mDesignFileEntities3DPlanDesignBlueprint);
                    intent3DPlan.putExtra(Constant.DeliveryBundleKey.LEVEL_BUNDLE, bundle);
                    startActivityForResult(intent3DPlan, 2);
                }
                break;

            case R.id.ll_material_list:     /// 材料清单 .
                if (null != mDesignFileEntities3DPlanMaterialBill) {
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
                CustomProgress.show(FlowUploadDeliveryActivity.this, UIUtils.getString(R.string.in_design_deliverable_zero), false, null);
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
                CustomProgress.show(FlowUploadDeliveryActivity.this, UIUtils.getString(R.string.volume_room_deliver), false, null);
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

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                /**
                 * 交付完成
                 */
                case DELIVERED_STATE_FINISH:
                    doneDelivery(msg);
                    break;
                /**
                 * 交付状态进行中
                 */
                case DELIVERED_STATE_UN_FINISH:
                    uploadingDelivery();
                    break;
            }
        }
    };

    /**
     * [1]获取与装修项目相关联的3D方案列表
     *
     * @param needs_id        　项目编号
     * @param designer_id     　用户id
     * @param deliveredFinish 交付状态
     */
    private void get3DPlan(final String needs_id, String designer_id, final int deliveredFinish, final String memType) {
        MPServerHttpManager.getInstance().get3DPlanInfoData(needs_id, designer_id, new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                String userInfo = GsonUtil.jsonToString(jsonObject);
                Wk3DPlanBean wk3DPlanBean = GsonUtil.jsonToBean(userInfo, Wk3DPlanBean.class);
                updateViewFrom3DPlanData(wk3DPlanBean, deliveredFinish, memType);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError, true);
                CustomProgress.cancelDialog();
            }
        });
    }

    /**
     * 查看自己的设计方案
     *
     * @param needs_id    　项目编号
     * @param designer_id 　用户的id
     */
    private void getDeliveredFile(final String needs_id, String designer_id) {
        MPServerHttpManager.getInstance().getDeliveredFile(needs_id, designer_id, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String userInfo = GsonUtil.jsonToString(jsonObject);

                Wk3DPlanDelivery wk3DPlanDelivery = GsonUtil.jsonToBean(userInfo, Wk3DPlanDelivery.class);
                ArrayList<Wk3DPlanDelivery.DeliveryFilesEntity> deliveryFiles = (ArrayList<Wk3DPlanDelivery.DeliveryFilesEntity>) wk3DPlanDelivery.getDeliveryFiles();
                updateViewFromDeliveryFile(deliveryFiles);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError, true);
                CustomProgress.cancelDialog();
            }
        });
    }

    /**
     * [2]获取3D方案的文件列表
     *
     * @param needs_id        　项目编号
     * @param design_asset_id 通过3d_asset_id获取相应的3D方案
     * @param deliveredFinish 　是否已经交付完成
     * @param memType         　用户类型
     */
    private void get3DPlanList(String needs_id, final String design_asset_id, final int deliveredFinish, final String memType) {
        MPServerHttpManager.getInstance().get3DPlanList(needs_id, design_asset_id, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                String userInfo = GsonUtil.jsonToString(jsonObject);

                KLog.json(TAG, userInfo);

                mWk3DPlanListBean = GsonUtil.jsonToBean(userInfo, Wk3DPlanListBean.class);
                updateViewFrom3DPlanList(deliveredFinish, memType, design_asset_id);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError, true);
                CustomProgress.cancelDialog();
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
            }
        });
    }

    /**
     * 消费者发送确认交付
     */
    private void makeSureDelivery() {
        //     needs_id  对应于　demands_id
        MPServerHttpManager.getInstance().makeSureDelivery(needs_id, designer_id, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError, true);
            }
        });
    }

    /**
     * 判断是否已经提交了交付物
     *
     * @param deliveryFiles 　网页上传的交付数据
     */
    private void updateViewFromDeliveryFile(ArrayList<Wk3DPlanDelivery.DeliveryFilesEntity> deliveryFiles) {
        if (deliveryFiles == null || deliveryFiles.size() < 1) {
            /**
             * 尚未完成交付
             */
            Message msg = Message.obtain();
            msg.what = DELIVERED_STATE_UN_FINISH;
            mHandler.sendMessage(msg);
        } else {
            /**
             * 已完成交付
             */
            Message msg = Message.obtain();
            msg.what = DELIVERED_STATE_FINISH;
            msg.obj = deliveryFiles;
            mHandler.sendMessage(msg);
        }
    }

    /**
     * 解析获取到的当前所有3D方案，若有多个3D方案，其中每个3D方案的{3d_asset_id},即：design_asset_id 不同
     *
     * @param wk3DPlanBean    3D设计方案的实体类
     * @param deliveredFinish 是否已经交付完成
     * @param memType         　当前用户类型
     */
    private void updateViewFrom3DPlanData(Wk3DPlanBean wk3DPlanBean, int deliveredFinish, String memType) {
        String design_asset_id;

        ArrayList<Wk3DPlanBean.ThreeDimensionalEntity> threeDimensionalEntities = (ArrayList<Wk3DPlanBean.ThreeDimensionalEntity>) wk3DPlanBean.getThree_dimensionals();
        if (null == threeDimensionalEntities || threeDimensionalEntities.size() < 1) {
            if (Constant.UerInfoKey.DESIGNER_TYPE.equals(memType)) {
                if (Integer.valueOf(wk_cur_sub_node_id) >= 21 && Integer.valueOf(wk_cur_sub_node_id) < 41) {
                    mAlertViewMeasureDelivery.show();
                } else if (Integer.valueOf(wk_cur_sub_node_id) >= 42) {
                    mAlertViewDesignDelivery.show();
                }
            } else {
                mBtnUploadSubmit3DPlan.setVisibility(View.GONE);
            }
            return;
        }

        /**
         * 每个3D方案的3d_asset_id
         */
        if (DELIVERED_STATE_FINISH == deliveredFinish) {
            design_asset_id = threeDimensionalEntities.get(0).getDesign_asset_id();
            get3DPlanList(needs_id, design_asset_id, deliveredFinish, memType);
        } else {
            if (Integer.valueOf(wk_cur_sub_node_id) >= 21 && Integer.valueOf(wk_cur_sub_node_id) < 41) {
                /**
                 * 量房交付的列表
                 */
                design_asset_id = threeDimensionalEntities.get(0).getDesign_asset_id();
                get3DPlanList(needs_id, design_asset_id, DELIVERED_STATE_UN_FINISH, Constant.UerInfoKey.DESIGNER_TYPE);
            } else {
                /**
                 * 设计交付列表
                 */
                for (Wk3DPlanBean.ThreeDimensionalEntity threeDimensionalEntity : threeDimensionalEntities) {
                    design_asset_id = threeDimensionalEntity.getDesign_asset_id();
                    get3DPlanList(needs_id, design_asset_id, DELIVERED_STATE_UN_FINISH, Constant.UerInfoKey.DESIGNER_TYPE);
                }
            }
        }
    }

    /**
     * 根据选中的某个3Ｄ方案，更新UI
     *
     * @param deliveredFinish 　是否已经交付完成
     * @param design_asset_id
     * @param memType         　 当前用户类型
     */
    private void updateViewFrom3DPlanList(int deliveredFinish, String memType, String design_asset_id) {
        /**
         *为其传递3d_asset_id
         */
        mWk3DPlanListBean.setDesign_asset_id(design_asset_id);
        ArrayList<Wk3DPlanListBean.DesignFileEntity> design_file = (ArrayList<Wk3DPlanListBean.DesignFileEntity>) mWk3DPlanListBean.getDesign_file();

        if (null == design_file || design_file.size() < 1) {
            /**
             * [1]提示网页提交量房或者设计交付物
             */
            if (Constant.UerInfoKey.DESIGNER_TYPE.equals(memType)) {
                if (Integer.valueOf(wk_cur_sub_node_id) >= 21 && Integer.valueOf(wk_cur_sub_node_id) < 41) {
                    mAlertViewMeasureDelivery.show();
                } else if (Integer.valueOf(wk_cur_sub_node_id) >= 42) {
                    mAlertViewDesignDelivery.show();
                }
            } else {
                mBtnUploadSubmit3DPlan.setVisibility(View.GONE);
            }
            return;
        } else {
            if (DELIVERED_STATE_FINISH == deliveredFinish) {
                mDesign_name = mWk3DPlanListBean.getDesign_name();
            } else {
                /**
                 * 量房交付
                 */
                if (Integer.valueOf(wk_cur_sub_node_id) >= 21 && Integer.valueOf(wk_cur_sub_node_id) < 41) {
                    String type;
                    mLl3DPlan.setVisibility(View.VISIBLE);
                    mLl3DPlan.setOnClickListener(FlowUploadDeliveryActivity.this);
                    mIv3DPlan.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_measure_select));
                    for (Wk3DPlanListBean.DesignFileEntity designFileEntity : design_file) {
                        type = designFileEntity.getType();
                        if (Constant.DeliveryTypeBundleKey.USAGE_TYPE_DESIGN_BLUEPRINT_DELIVERY.equals(type)) {
                            mDesignFileEntitiesMeasure.add(designFileEntity);
                        }
                    }
                    mBtnUploadSubmit3DPlan.setVisibility(View.VISIBLE);
                    design_asset_id_measure = design_asset_id;

                } else {
                    /**
                     * [2]设计交付,判断是否有type为10 的情况
                     */
                    String type;
                    mWk3DPlanListBeanArrayList = new ArrayList<>();
                    for (Wk3DPlanListBean.DesignFileEntity designFileEntity : design_file) {
                        type = designFileEntity.getType();
                        if (Constant.DeliveryTypeBundleKey.USAGE_TYPE_THREE_PLAN_DELIVERY.equals(type)) {
                            /**
                             * 将type为10的designFile的link取出
                             */
                            mWk3DPlanListBean.setLink(designFileEntity.getLink());
                            mWk3DPlanListBeanArrayList.add(mWk3DPlanListBean);
                        }
                    }
                    /**
                     * 判断是否有type为10的类型
                     */
                    if (mWk3DPlanListBeanArrayList.size() < 1) {
                        if (Constant.UerInfoKey.CONSUMER_TYPE.equals(mMemberType)) {
                            mAlertViewDesignConsumerDelivery.show();
                        } else {
                            mAlertViewDesignDelivery.show();
                        }
                        return;
                    }
                    mBtnUploadSubmit3DPlan.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /**
     * 上传交付物完成
     *
     * @param msg 传递是否交付完成的消息
     */
    private void doneDelivery(Message msg) {
        String type;
        String usage_type;
        CustomProgress.cancelDialog();
        mBtnUploadSubmit3DPlan.setVisibility(View.GONE);
        showAllLevel();
        mDeliveryFilesEntities = (ArrayList<Wk3DPlanDelivery.DeliveryFilesEntity>) msg.obj;
        /**
         * 获取某一个3D方案的名称
         */
        get3DPlan(needs_id, designer_id, DELIVERED_STATE_FINISH, mMemberType);

        for (Wk3DPlanDelivery.DeliveryFilesEntity deliveryFilesEntity : mDeliveryFilesEntities) {
            type = deliveryFilesEntity.getType();
            if (Constant.DeliveryTypeBundleKey.TYPE_MEASURE_DELIVERY.equals(type)) {
                /**
                 * 量房订单
                 */
                setTitleForNavbar(UIUtils.getString(R.string.deliver_measure_consumer));
                mTvDelivery.setText(UIUtils.getString(R.string.deliver_measure_consumer));
                show3DAndHideLevel();
                mIv3DPlan.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_measure_select));
                usage_type = deliveryFilesEntity.getUsage_type();
                if (Constant.DeliveryTypeBundleKey.USAGE_TYPE_DESIGN_BLUEPRINT_DELIVERY.equals(usage_type)) {
                    mDeliveryFilesEntitiesMeasure.add(deliveryFilesEntity);
                }
            } else if (Constant.DeliveryTypeBundleKey.TYPE_DESIGN_DELIVERY.equals(type)) {
                usage_type = deliveryFilesEntity.getUsage_type();
                /**
                 * 设计交付
                 */
                setTitleForNavbar(UIUtils.getString(R.string.deliver_consumer));
                mTvDelivery.setText(UIUtils.getString(R.string.three_plan));
                showAllLevel();
                setSelectIcon();
                clickLevel();

                if (Constant.DeliveryTypeBundleKey.USAGE_TYPE_THREE_PLAN_DELIVERY.equals(usage_type)) {
                    mDeliveryFilesEntityArrayList.add(deliveryFilesEntity);
                } else if (Constant.DeliveryTypeBundleKey.USAGE_TYPE_RENDERING_DESIGN_DELIVERY_0.equals(usage_type)
                        || Constant.DeliveryTypeBundleKey.USAGE_TYPE_READERING_DESIGN_DELIVERY_4.equals(usage_type)) {
                    mDeliveryFilesEntitiesRendering.add(deliveryFilesEntity);
                } else if (Constant.DeliveryTypeBundleKey.USAGE_TYPE_DESIGN_BLUEPRINT_DELIVERY.equals(usage_type)) {
                    mDeliveryFilesEntitiesDesignBlueprint.add(deliveryFilesEntity);
                } else if (Constant.DeliveryTypeBundleKey.USAGE_TYPE_MATERIAL_BILL_DELIVERY.equals(usage_type)) {
                    mDeliveryFilesEntitiesMaterialBill.add(deliveryFilesEntity);
                }
            }
        }
        doDeliveryDelayed();
    }

    /**
     * 处理设计交付的不同状态
     * wk_sub_node_id
     * <61 : 消费者，等待设计师上传设计交付物
     * 设计师，上传设计交付物
     * =61: 消费者，确认或者延期的操作 .
     * 　　设计师：可以重新发送设计交付物（显示发送按钮）
     * >61(=63):
     * 消费者和设计师：查看设计交付
     */
    private void doDeliveryDelayed() {
        int wk_sub_node_id_int = Integer.parseInt(wk_cur_sub_node_id);
        mLinerDelayedShow.setVisibility(View.VISIBLE);
        if (61 == wk_sub_node_id_int) {
            switch (mMemberType) {
                case Constant.UerInfoKey.CONSUMER_TYPE:
                    mBtnUploadSubmit3DPlan.setVisibility(View.GONE);
                    mLinerDelayedShow.setVisibility(View.VISIBLE);

                    break;

                case Constant.UerInfoKey.DESIGNER_TYPE:
                    mLinerDelayedShow.setVisibility(View.GONE);
//                    mBtnUploadSubmit3DPlan.setVisibility(View.VISIBLE);
//                    sureSubmit();
                    break;
            }
        } else if (wk_sub_node_id_int > 61) {
            mLinerDelayedShow.setVisibility(View.GONE);
            mBtnUploadSubmit3DPlan.setVisibility(View.GONE);
        }
    }


    /**
     * 正在上传量房或者设计交付物
     */
    private void uploadingDelivery() {
        /**
         * [0]量房交付
         */
        if (Integer.valueOf(wk_cur_sub_node_id) >= 21 && Integer.valueOf(wk_cur_sub_node_id) < 41) {
            mIv3DPlan.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_measure_unselect));

            setTitleForNavbar(UIUtils.getString(R.string.deliver_designer));
            mTvDelivery.setText(UIUtils.getString(R.string.deliver_measure_consumer));

            if (Constant.UerInfoKey.CONSUMER_TYPE.equals(mMemberType)) {
                mBtnUploadSubmit3DPlan.setVisibility(View.GONE);
                CustomProgress.cancelDialog();
                mAlertViewMeasureConsumerDelivery.show();
                return;
            } else if (Constant.UerInfoKey.DESIGNER_TYPE.equals(mMemberType)) {
                /**
                 * 设计师上传量房交付物
                 */
                cancelSubmit();
                get3DPlan(needs_id, designer_id, DELIVERED_STATE_UN_FINISH, Constant.UerInfoKey.DESIGNER_TYPE);
            }
        } else if (Integer.valueOf(wk_cur_sub_node_id) >= 42) {
            /**
             * [1]设计交付
             */
            showAllLevel();
            if (Constant.UerInfoKey.CONSUMER_TYPE.equals(mMemberType)) {
                mBtnUploadSubmit3DPlan.setVisibility(View.GONE);
                setTitleForNavbar(UIUtils.getString(R.string.deliver_consumer));
            } else {
                cancelSubmit();
                setTitleForNavbar(UIUtils.getString(R.string.deliver_designer));
            }
            mTvDelivery.setText(UIUtils.getString(R.string.flow_3d));

            if (Integer.valueOf(wk_cur_sub_node_id) < 61) {
                if (Constant.UerInfoKey.CONSUMER_TYPE.equals(mMemberType)) {
                    mBtnUploadSubmit3DPlan.setVisibility(View.GONE);
                    CustomProgress.cancelDialog();
                    mAlertViewDesignConsumerDelivery.show();
                } else if (Constant.UerInfoKey.DESIGNER_TYPE.equals(mMemberType)) {
                    /**
                     * 设计师上传设计交付物
                     */
                    cancelSubmit();
                    get3DPlan(needs_id, designer_id, DELIVERED_STATE_UN_FINISH, Constant.UerInfoKey.DESIGNER_TYPE);
                }
            }
        }
    }

    /**
     * 量房交付
     */
    private void measureDelivery() {
        if (Integer.valueOf(wk_cur_sub_node_id) >= 21 && Integer.valueOf(wk_cur_sub_node_id) < 41) {
            ArrayList<String> design_file_id_measure_arrayList = DeliverySelector.select_design_file_id_map.get(4);
            if (design_file_id_measure_arrayList != null && design_file_id_measure_arrayList.size() > 0) {
                sureSubmit();
            } else {
                cancelSubmit();
            }
        }

        if (Integer.valueOf(wk_cur_sub_node_id) >= 51) {
            canSubmitOk();
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
    private void canSubmitOk() {
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
    private void canClickOk() {

        if (TextUtils.isEmpty(DeliverySelector.select_design_asset_id)) {
            cancelOnClick();
            DeliverySelector.select_design_file_id_map.clear();
            clearDesignFileEntitiesLevel();
        } else {
            if (mDesignFileEntities3DPlanRendering == null || mDesignFileEntities3DPlanRendering.size() < 1) {
                mIvDesignApply.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_flow_rendering));
                mLlDesignApply.setOnClickListener(null);
            } else {
                mIvDesignApply.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_flow_rendering_press));
                mLlDesignApply.setOnClickListener(this);
            }

            if (mDesignFileEntities3DPlanDesignBlueprint == null || mDesignFileEntities3DPlanDesignBlueprint.size() < 1) {
                mIvDesignPager.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_flow_drawing));
                mLlDesignPager.setOnClickListener(null);
            } else {
                mIvDesignPager.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_flow_drawing_press));
                mLlDesignPager.setOnClickListener(this);
            }

            if (mDesignFileEntities3DPlanMaterialBill == null || mDesignFileEntities3DPlanMaterialBill.size() < 1) {
                mIvMaterialList.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_flow_inventory));
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
        mIv3DPlan.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_flow_3d));
        mIvDesignApply.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_flow_rendering));
        mIvDesignPager.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_flow_drawing));
        mIvMaterialList.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_flow_inventory));
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
        mDelayAlertView = new AlertView(UIUtils.getString(R.string.flow_upload_delivery_delay), UIUtils.getString(R.string.flow_upload_delivery_delay_only), UIUtils.getString(R.string.cancel), null, new String[]{UIUtils.getString(R.string.sure)}, this, AlertView.Style.Alert, this);
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
                    canClickOk();
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
                            List<Wk3DPlanListBean.DesignFileEntity> designFileEntityList = wk3DPlanListBean.getDesign_file();
                            for (Wk3DPlanListBean.DesignFileEntity designFileEntity : designFileEntityList) {
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
                    ArrayList<Wk3DPlanListBean.DesignFileEntity> design_file = (ArrayList<Wk3DPlanListBean.DesignFileEntity>) mWk3DPlanListBean.getDesign_file();
                    for (Wk3DPlanListBean.DesignFileEntity designFileEntity : design_file) {
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
                    break;
                case 4:
                default:
                    break;
            }
            measureDelivery();
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
            if (Integer.valueOf(wk_cur_sub_node_id) >= 21 && Integer.valueOf(wk_cur_sub_node_id) < 41) {
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

            } else if (Integer.valueOf(wk_cur_sub_node_id) >= 42) {
                /**
                 * 设计交付
                 */
                if (mDeliveryFilesEntities == null) {
                    canClickOk();
                    canSubmitOk();
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

    private static final int DELIVERED_STATE_FINISH = 0; /// 已完成交付 .
    private static final int DELIVERED_STATE_UN_FINISH = 1;/// 尚未完成交付 .
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
     * 用于存储有不同design_asset_id的实体类的集合
     */
    private ArrayList<Wk3DPlanListBean> mWk3DPlanListBeanArrayList;
    private ArrayList<Wk3DPlanDelivery.DeliveryFilesEntity> mDeliveryFilesEntities;
    private ArrayList<Wk3DPlanDelivery.DeliveryFilesEntity> mDeliveryFilesEntityArrayList = new ArrayList<>();
    private ArrayList<Wk3DPlanDelivery.DeliveryFilesEntity> mDeliveryFilesEntitiesRendering = new ArrayList<>();
    private ArrayList<Wk3DPlanDelivery.DeliveryFilesEntity> mDeliveryFilesEntitiesDesignBlueprint = new ArrayList<>();
    private ArrayList<Wk3DPlanDelivery.DeliveryFilesEntity> mDeliveryFilesEntitiesMaterialBill = new ArrayList<>();
    private ArrayList<Wk3DPlanDelivery.DeliveryFilesEntity> mDeliveryFilesEntitiesMeasure = new ArrayList<>();
    /**
     * 当前3D方案的所有设计图
     */
    private ArrayList<Wk3DPlanListBean.DesignFileEntity> mDesignFileEntities3DPlanRendering = new ArrayList<>();
    private ArrayList<Wk3DPlanListBean.DesignFileEntity> mDesignFileEntities3DPlanDesignBlueprint = new ArrayList<>();
    private ArrayList<Wk3DPlanListBean.DesignFileEntity> mDesignFileEntities3DPlanMaterialBill = new ArrayList<>();
    private ArrayList<Wk3DPlanListBean.DesignFileEntity> mDesignFileEntitiesMeasure = new ArrayList<>();

    private String design_asset_id; /// 用于记录选中的设计图的id .
    private String community_name;
    private String design_asset_id_measure;
    private String mDesign_name;
    private String mMemberType;
    private String type;                                             /// 交付类型:0：量房交付,1： 设计交付 .
    private String commonTip = UIUtils.getString(R.string.tip);
    private String[] sureString = new String[]{UIUtils.getString(R.string.sure)};
}