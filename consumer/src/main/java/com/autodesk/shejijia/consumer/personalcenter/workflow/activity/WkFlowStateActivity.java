package com.autodesk.shejijia.consumer.personalcenter.workflow.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.consumer.personalcenter.workflow.adapter.WkFlowStateAdapter;
import com.autodesk.shejijia.consumer.utils.MPStatusMachine;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;

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
public class WkFlowStateActivity extends BaseWorkFlowActivity implements AdapterView.OnItemClickListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_designer_common_meal_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        context = this;
        mListView = (ListViewFinal) findViewById(R.id.lv_designer_meal_detail);
        mPtrLayout = (PtrClassicFrameLayout) findViewById(R.id.ptr_layout);
        setImageForNavButton(ButtonType.RIGHT, R.drawable.icon_project_material); /// 设置公用得头式图得标题 .
        setVisibilityForNavButton(ButtonType.RIGHT, true); /// 公用得头式图将右边的图片显示 .
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        updateView();
        updateViewFromData();
    }

    /**
     * 监听方法
     */
    @Override
    protected void initListener() {
        super.initListener();
        mListView.setOnItemClickListener(this);
        mPtrLayout.setOnRefreshListener(new OnDefaultRefreshListener() {
                                            @Override
                                            public void onRefreshBegin(PtrFrameLayout frame) {
                                                getOrderDetailsInfo(needs_id, designer_id);
                                            }
                                        }
        );
        mPtrLayout.setLastUpdateTimeRelateObject(this);
    }

    @Override
    protected void leftNavButtonClicked(View view) {
        refreshWkFlowState();
        super.leftNavButtonClicked(view);
    }

    /**
     * 点击每个条目执行的操作
     */
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mBiddersEntity == null) {
            return;
        }
        int wk_cur_sub_node_idi = Integer.valueOf(wk_cur_sub_node_id); /// String转int .
        int wk_template_idi = Integer.valueOf(wk_template_id); /// 模板标签；应标，自选，北舒 .

        if (memberEntity != null) {
            strMemberType = memberEntity.getMember_type();
        }

        if (Constant.UerInfoKey.DESIGNER_TYPE.equals(strMemberType)) { /// 设计师 .
            switch (position) {
                /**
                 * 量房
                 */
                case 0:
                    if (wk_cur_sub_node_idi >= 11) {
                        Intent mIntent = new Intent(WkFlowStateActivity.this, FlowMeasureFormActivity.class);
                        mIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                        mIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                        mIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                        startActivity(mIntent);
                    }
                    break;
                /**
                 * 支付量房费
                 */
                case 1:
                    if (wk_template_idi == 1) {     // 应标
                        if (wk_cur_sub_node_idi == 11 || wk_cur_sub_node_idi == 12 || wk_cur_sub_node_idi == 14) {
                            view.setClickable(false);
                        } else if (wk_cur_sub_node_idi == 13 || wk_cur_sub_node_idi > 14) {
                            Intent mcIntent = new Intent(WkFlowStateActivity.this, FlowMeasureCostActivity.class);
                            mcIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                            mcIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                            mcIntent.putExtra(Constant.BundleKey.BUNDLE_ACTION_NODE_ID, MPStatusMachine.NODE__MEANSURE_PAY);
                            mcIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                            startActivity(mcIntent);
                        }
                    } else if (wk_template_idi == 2) {    /// 自选量房阶段 .

                        /// 12	confirm_measure	设计师同意量房 .
                        /// 13	decline_invite_measure 设计师拒绝量房.
                        if (wk_cur_sub_node_idi >= 12 && wk_cur_sub_node_idi != 13) {
                            Intent mcIntent = new Intent(WkFlowStateActivity.this, FlowMeasureCostActivity.class);
                            mcIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                            mcIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                            mcIntent.putExtra(Constant.BundleKey.BUNDLE_ACTION_NODE_ID, MPStatusMachine.NODE__MEANSURE_PAY);
                            mcIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                            startActivity(mcIntent);
                        } else {
                            view.setClickable(false);
                        }
                    }
                    break;

                /**
                 * 设计合同 or 量房交付物
                 */
                case 2:
                    if (wk_cur_sub_node_idi >= 21 && wk_cur_sub_node_idi != 33) { /// 设计合同 .
                        Intent ecIntent = new Intent(WkFlowStateActivity.this, FlowEstablishContractActivity.class);
                        ecIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                        ecIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                        ecIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                        startActivity(ecIntent);
                    } else if (wk_cur_sub_node_idi == 33) { /// 量房交付物 .
                        Intent planIntent = new Intent(WkFlowStateActivity.this, FlowUploadDeliveryActivity.class);
                        planIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                        planIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                        planIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                        startActivity(planIntent);
                    } else {
                        view.setClickable(false);
                    }
                    break;

                /**
                 * 设计首款
                 */
                case 3:
                    if (wk_cur_sub_node_idi == 31) {
                        new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.please_wait_consumer_send_design_first), null, new String[]{UIUtils.getString(R.string.sure)}, null, WkFlowStateActivity.this,
                                AlertView.Style.Alert, null).show();
                    } else if (wk_cur_sub_node_idi == 33) {
                        view.setClickable(false);
                    } else if (wk_cur_sub_node_idi >= 41) {
                        Intent fdIntent = new Intent(WkFlowStateActivity.this, FlowFirstDesignActivity.class);
                        fdIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                        fdIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                        fdIntent.putExtra(Constant.BundleKey.BUNDLE_ACTION_NODE_ID, MPStatusMachine.NODE__DESIGN_FIRST_PAY);
                        fdIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                        startActivity(fdIntent);
                    } else {
                        view.setClickable(false);
                    }
                    break;

                /**
                 * 设计尾款
                 */
                case 4:
                    if (wk_cur_sub_node_idi == 41 || wk_cur_sub_node_idi == 42) {
                        new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.please_wait_consumer_send_design_end), null, new String[]{UIUtils.getString(R.string.sure)}, null, WkFlowStateActivity.this, AlertView.Style.Alert, null).show();
                    } else if (wk_cur_sub_node_idi >= 51) {
                        Intent pldIntent = new Intent(WkFlowStateActivity.this, FlowLastDesignActivity.class);
                        pldIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                        pldIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                        pldIntent.putExtra(Constant.BundleKey.BUNDLE_ACTION_NODE_ID, MPStatusMachine.NODE__DESIGN_BALANCE_PAY);
                        pldIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                        startActivity(pldIntent);
                    } else {
                        view.setClickable(false);
                    }
                    break;
                /**
                 *接收设计交付物
                 */
                case 5:
                    if (wk_cur_sub_node_idi >= 51) {
                        Intent planIntent = new Intent(WkFlowStateActivity.this, FlowUploadDeliveryActivity.class);
                        planIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                        planIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                        planIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                        startActivity(planIntent);
                    }
                    break;
            }
        } else if (Constant.UerInfoKey.CONSUMER_TYPE.equals(strMemberType)) { // 消费者
            switch (position) {
                /**
                 * 量房
                 */
                case 0:
                    Intent mIntent = new Intent(WkFlowStateActivity.this, FlowMeasureFormActivity.class);
                    mIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                    mIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                    mIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                    startActivity(mIntent);
                    break;
                /**
                 * 支付量房费
                 */
                case 1:
                    if (wk_template_idi == 1) { // 应标
                        /// 应标状态：
                        ///12decline_measure	消费者拒绝设计师.
                        ///13	confirm_measure 设计师同意量房.
                        ///14	decline_invite_measure 设计师拒绝量房.
                        if (wk_cur_sub_node_idi >= 13 && wk_cur_sub_node_idi != 14) {
                            Intent mcIntent = new Intent(WkFlowStateActivity.this, FlowMeasureCostActivity.class);
                            mcIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                            mcIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                            mcIntent.putExtra(Constant.BundleKey.BUNDLE_ACTION_NODE_ID, MPStatusMachine.NODE__MEANSURE_PAY);
                            mcIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                            startActivity(mcIntent);
                        } else {
                            view.setClickable(false);
                        }
                    } else if (wk_template_idi == 2) {
                        // 自选或北舒
                        /// 12	confirm_measure	设计师同意量房 .
                        /// 13	decline_invite_measure 设计师拒绝量房.
                        if (wk_cur_sub_node_idi >= 12 && wk_cur_sub_node_idi != 13) {
                            Intent mcIntent = new Intent(WkFlowStateActivity.this, FlowMeasureCostActivity.class);
                            mcIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                            mcIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                            mcIntent.putExtra(Constant.BundleKey.BUNDLE_ACTION_NODE_ID, MPStatusMachine.NODE__MEANSURE_PAY);
                            mcIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                            startActivity(mcIntent);
                        } else {
                            view.setClickable(false);
                        }
                    }
                    break;
                /**
                 * 设计合同 or 量房交付物
                 */
                case 2:
                    if (Integer.parseInt(wk_cur_sub_node_id) == 21 || Integer.parseInt(wk_cur_sub_node_id) == 22) {
                        new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.please_wait_designer_send_contract), null, new String[]{UIUtils.getString(R.string.sure)}, null, WkFlowStateActivity.this,
                                AlertView.Style.Alert, null).show();
                    } else if (Integer.parseInt(wk_cur_sub_node_id) >= 31 && Integer.parseInt(wk_cur_sub_node_id) != 33) {
                        Intent ecIntent = new Intent(WkFlowStateActivity.this, FlowEstablishContractActivity.class);
                        ecIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                        ecIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                        ecIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                        startActivity(ecIntent);
                    } else if (Integer.parseInt(wk_cur_sub_node_id) == 33) {
                        /**
                         * 上传量房交付物
                         */
                        Intent planIntent = new Intent(WkFlowStateActivity.this, FlowUploadDeliveryActivity.class);
                        planIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                        planIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                        planIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                        startActivity(planIntent);
                    } else {
                        view.setClickable(false);
                    }
                    break;

                /**
                 * 设计首款
                 */
                case 3:
                    if (wk_cur_sub_node_idi >= 31 && wk_cur_sub_node_idi != 33) {
                        Intent fdIntent = new Intent(WkFlowStateActivity.this, FlowFirstDesignActivity.class);
                        fdIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                        fdIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                        fdIntent.putExtra(Constant.BundleKey.BUNDLE_ACTION_NODE_ID, MPStatusMachine.NODE__DESIGN_FIRST_PAY);
                        fdIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                        startActivity(fdIntent);
                    } else {
                        view.setClickable(false);
                    }
                    break;
                /**
                 * 设计尾款
                 */
                case 4:
                    if (wk_cur_sub_node_idi >= 41) {
                        Intent pldIntent = new Intent(WkFlowStateActivity.this, FlowLastDesignActivity.class);
                        pldIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                        pldIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                        pldIntent.putExtra(Constant.BundleKey.BUNDLE_ACTION_NODE_ID, MPStatusMachine.NODE__DESIGN_BALANCE_PAY);
                        pldIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                        startActivity(pldIntent);
                    }
                    break;
                /**
                 *接收设计交付物
                 */
                case 5:
                    if (wk_cur_sub_node_idi >= 51) {
                        Intent planIntent = new Intent(WkFlowStateActivity.this, FlowUploadDeliveryActivity.class);
                        planIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                        planIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                        planIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                        startActivity(planIntent);
                    }
                    break;
            }
        }
    }

    /**
     * 点击右上角资料进行的操作
     *
     * @param view 右上角的控件
     */
    @Override
    protected void rightNavButtonClicked(View view) {
        Intent maIntent = new Intent(WkFlowStateActivity.this, ProjectMaterialActivity.class);      /// 跳转项目资料界面 .
        maIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_IM);
        maIntent.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_DESIGNER_ID, designer_id);
        maIntent.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_NEEDS_ID, needs_id);
        startActivity(maIntent);
    }

    @Override
    protected void onWorkFlowData() {
        super.onWorkFlowData();

        setTitleForNavbar(community_name); /// 设置标题 .
        mPtrLayout.onRefreshComplete();
        mAdapter = new WkFlowStateAdapter(context, memberEntity.getMember_type(), mBiddersEntity, wk_template_id);
        mListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
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

    private void updateViewFromData() {
        intent = getIntent();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (mBiddersEntity == null) {
                    return;
                }

                int wk_cur_sub_node_idi = Integer.valueOf(wk_cur_sub_node_id); /// String转int .
                int wk_template_idi = Integer.valueOf(wk_template_id); /// 模板标签；应标，自选，北舒 .

                if (memberEntity != null)
                    strMemberType = memberEntity.getMember_type();

                if (Constant.UerInfoKey.DESIGNER_TYPE.equals(strMemberType)) { /// 设计师 .
                    switch (position) {
                        /**
                         * 量房
                         */
                        case 0:
                            if (wk_cur_sub_node_idi >= 11) {
                                Intent mIntent = new Intent(WkFlowStateActivity.this, FlowMeasureFormActivity.class);
                                mIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                                mIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                                mIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                                startActivity(mIntent);
                            }
                            break;
                        /**
                         * 支付量房费
                         */
                        case 1:
                            if (wk_template_idi == 1) {                 // 应标
                                if (wk_cur_sub_node_idi == 11 || wk_cur_sub_node_idi == 12 || wk_cur_sub_node_idi == 14) {
                                    view.setClickable(false);
                                } else if (wk_cur_sub_node_idi == 13 || wk_cur_sub_node_idi > 14) {
                                    Intent mcIntent = new Intent(WkFlowStateActivity.this, FlowMeasureCostActivity.class);
                                    mcIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                                    mcIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                                    mcIntent.putExtra(Constant.BundleKey.BUNDLE_ACTION_NODE_ID, MPStatusMachine.NODE__MEANSURE_PAY);
                                    mcIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                                    startActivity(mcIntent);
                                }
                            } else if (wk_template_idi == 2) {                                         // 自选或北舒
                                if (wk_cur_sub_node_idi == 11 || wk_cur_sub_node_idi == 13) {
                                    view.setClickable(false);
                                } else if (wk_cur_sub_node_idi == 12 || wk_cur_sub_node_idi > 13) {
                                    Intent mcIntent = new Intent(WkFlowStateActivity.this, FlowMeasureCostActivity.class);
                                    mcIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                                    mcIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                                    mcIntent.putExtra(Constant.BundleKey.BUNDLE_ACTION_NODE_ID, MPStatusMachine.NODE__MEANSURE_PAY);
                                    mcIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                                    startActivity(mcIntent);
                                }
                            }
                            break;

                        /**
                         * 设计合同 or 量房交付物
                         */
                        case 2:
                            if (wk_cur_sub_node_idi >= 21 && wk_cur_sub_node_idi != 33) { /// 设计合同 .
                                Intent ecIntent = new Intent(WkFlowStateActivity.this, FlowEstablishContractActivity.class);
                                ecIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                                ecIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                                ecIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                                startActivity(ecIntent);
                            } else if (wk_cur_sub_node_idi == 33) { /// 量房交付物 .
                                Intent planIntent = new Intent(WkFlowStateActivity.this, FlowUploadDeliveryActivity.class);
                                planIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                                planIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                                planIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                                startActivity(planIntent);
                            } else {
                                view.setClickable(false);
                            }
                            break;

                        /**
                         * 设计首款
                         */
                        case 3:
                            if (wk_cur_sub_node_idi == 31) {
                                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.please_wait_consumer_send_design_first), null, new String[]{UIUtils.getString(R.string.sure)}, null, WkFlowStateActivity.this,
                                        AlertView.Style.Alert, null).show();
                            } else if (wk_cur_sub_node_idi == 33) {
                                view.setClickable(false);
                            } else if (wk_cur_sub_node_idi >= 41) {
                                Intent fdIntent = new Intent(WkFlowStateActivity.this, FlowFirstDesignActivity.class);
                                fdIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                                fdIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                                fdIntent.putExtra(Constant.BundleKey.BUNDLE_ACTION_NODE_ID, MPStatusMachine.NODE__DESIGN_FIRST_PAY);
                                fdIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                                startActivity(fdIntent);
                            } else {
                                view.setClickable(false);
                            }
                            break;

                        /**
                         * 设计尾款
                         */
                        case 4:
                            if (wk_cur_sub_node_idi == 41 || wk_cur_sub_node_idi == 42) {
                                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.please_wait_consumer_send_design_end), null, new String[]{UIUtils.getString(R.string.sure)}, null, WkFlowStateActivity.this,
                                        AlertView.Style.Alert, null).show();
                            } else if (wk_cur_sub_node_idi >= 51) {
                                Intent pldIntent = new Intent(WkFlowStateActivity.this, FlowLastDesignActivity.class);
                                pldIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                                pldIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                                pldIntent.putExtra(Constant.BundleKey.BUNDLE_ACTION_NODE_ID, MPStatusMachine.NODE__DESIGN_BALANCE_PAY);
                                pldIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                                startActivity(pldIntent);
                            } else {
                                view.setClickable(false);
                            }
                            break;
                        /**
                         *接收设计交付物
                         */
                        case 5:
                            if (wk_cur_sub_node_idi >= 51) {
                                Intent planIntent = new Intent(WkFlowStateActivity.this, FlowUploadDeliveryActivity.class);
                                planIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                                planIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                                planIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                                startActivity(planIntent);
                            }
                            break;
                    }
                } else if (Constant.UerInfoKey.CONSUMER_TYPE.equals(strMemberType)) { // 消费者
                    switch (position) {
                        /**
                         * 量房
                         */
                        case 0:
                            Intent mIntent = new Intent(WkFlowStateActivity.this, FlowMeasureFormActivity.class);
                            mIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                            mIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                            mIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                            startActivity(mIntent);
                            break;
                        /**
                         * 支付量房费
                         */
                        case 1:
                            if (wk_template_idi == 1) { // 应标
                                if (wk_cur_sub_node_idi >= 13 && wk_cur_sub_node_idi != 14) {
                                    Intent mcIntent = new Intent(WkFlowStateActivity.this, FlowMeasureCostActivity.class);
                                    mcIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                                    mcIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                                    mcIntent.putExtra(Constant.BundleKey.BUNDLE_ACTION_NODE_ID, MPStatusMachine.NODE__MEANSURE_PAY);
                                    mcIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                                    startActivity(mcIntent);
                                } else {
                                    view.setClickable(false);
                                }
                            } else { // 自选或北舒
                                if (wk_cur_sub_node_idi >= 12 && wk_cur_sub_node_idi != 13) {
                                    Intent mcIntent = new Intent(WkFlowStateActivity.this, FlowMeasureCostActivity.class);
                                    mcIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                                    mcIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                                    mcIntent.putExtra(Constant.BundleKey.BUNDLE_ACTION_NODE_ID, MPStatusMachine.NODE__MEANSURE_PAY);
                                    mcIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                                    startActivity(mcIntent);
                                } else {
                                    view.setClickable(false);
                                }
                            }
                            break;
                        /**
                         * 设计合同 or 量房交付物
                         */
                        case 2:
                            if (Integer.parseInt(wk_cur_sub_node_id) == 21 || Integer.parseInt(wk_cur_sub_node_id) == 22) {
                                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.please_wait_designer_send_contract), null, new String[]{UIUtils.getString(R.string.sure)}, null, WkFlowStateActivity.this,
                                        AlertView.Style.Alert, null).show();
                            } else if (Integer.parseInt(wk_cur_sub_node_id) >= 31 && Integer.parseInt(wk_cur_sub_node_id) != 33) {
                                Intent ecIntent = new Intent(WkFlowStateActivity.this, FlowEstablishContractActivity.class);
                                ecIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                                ecIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                                ecIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                                startActivity(ecIntent);
                            } else if (Integer.parseInt(wk_cur_sub_node_id) == 33) {
                                /**
                                 * 上传量房交付物
                                 */
                                Intent planIntent = new Intent(WkFlowStateActivity.this, FlowUploadDeliveryActivity.class);
                                planIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                                planIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                                planIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                                startActivity(planIntent);
                            } else {
                                view.setClickable(false);
                            }
                            break;

                        /**
                         * 设计首款
                         */
                        case 3:
                            if (wk_cur_sub_node_idi >= 31 && wk_cur_sub_node_idi != 33) {
                                Intent fdIntent = new Intent(WkFlowStateActivity.this, FlowFirstDesignActivity.class);
                                fdIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                                fdIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                                fdIntent.putExtra(Constant.BundleKey.BUNDLE_ACTION_NODE_ID, MPStatusMachine.NODE__DESIGN_FIRST_PAY);
                                fdIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                                startActivity(fdIntent);
                            } else {
                                view.setClickable(false);
                            }
                            break;
                        /**
                         * 设计尾款
                         */
                        case 4:
                            if (wk_cur_sub_node_idi >= 41) {
                                Intent pldIntent = new Intent(WkFlowStateActivity.this, FlowLastDesignActivity.class);
                                pldIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                                pldIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                                pldIntent.putExtra(Constant.BundleKey.BUNDLE_ACTION_NODE_ID, MPStatusMachine.NODE__DESIGN_BALANCE_PAY);
                                pldIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                                startActivity(pldIntent);
                            }
                            break;
                        /**
                         *接收设计交付物
                         */
                        case 5:
                            if (wk_cur_sub_node_idi >= 51) {
                                Intent planIntent = new Intent(WkFlowStateActivity.this, FlowUploadDeliveryActivity.class);
                                planIntent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, WkFlowStateActivity.this.needs_id);
                                planIntent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, WkFlowStateActivity.this.designer_id);
                                planIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                                startActivity(planIntent);
                            }
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        refreshWkFlowState();
    }

    private void updateView() {
        mPtrLayout.setOnRefreshListener(new OnDefaultRefreshListener() {
                                            @Override
                                            public void onRefreshBegin(PtrFrameLayout frame) {
                                                getOrderDetailsInfo(needs_id, designer_id);
                                            }
                                        }
        );
        mPtrLayout.setLastUpdateTimeRelateObject(this);
    }

    private ListViewFinal mListView;
    private PtrClassicFrameLayout mPtrLayout;

    private String strMemberType = null;
    private Intent intent;
    private Context context;
    private WkFlowStateAdapter mAdapter;
}