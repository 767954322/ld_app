package com.autodesk.shejijia.consumer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.SeekDesignerDetailHomeBean;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.consumer.activity.MeasureFormActivity;
import com.autodesk.shejijia.consumer.personalcenter.workflow.activity.FlowEstablishContractActivity;
import com.autodesk.shejijia.consumer.personalcenter.workflow.activity.FlowFirstDesignActivity;
import com.autodesk.shejijia.consumer.personalcenter.workflow.activity.FlowLastDesignActivity;
import com.autodesk.shejijia.consumer.personalcenter.workflow.activity.FlowMeasureCostActivity;
import com.autodesk.shejijia.consumer.personalcenter.workflow.activity.FlowMeasureFormActivity;
import com.autodesk.shejijia.consumer.personalcenter.workflow.activity.FlowUploadDeliveryActivity;
import com.autodesk.shejijia.consumer.personalcenter.workflow.activity.ProjectMaterialActivity;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.WkFlowDetailsBean;
import com.autodesk.shejijia.consumer.utils.MPStatusMachine;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.im.IWorkflowDelegate;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatCommandInfo;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by kubern on 01/07/16.
 */
public class DesignPlatformDelegate implements IWorkflowDelegate {


    public void getProjectInfo(String assetId, String memberId, OkJsonRequest.OKResponseCallback callback) {
        MPServerHttpManager.getInstance().getOrderDetailsInfoData(assetId, memberId, callback);
    }


    public void onCommandCellClicked(Context context, MPChatCommandInfo mpChatCommandInfo) {
        int subNodeId = Integer.parseInt(mpChatCommandInfo.sub_node_id);

        switch (subNodeId) {
            case 13:
                jumpToOtherProcessesFour(context, FlowMeasureCostActivity.class, mpChatCommandInfo);

                break;

            case 11:
            case 12:
            case 14:
                jumpToOtherProcessesThree(context, FlowMeasureFormActivity.class, mpChatCommandInfo);

                break;

            case 21:
            case 22:
            case 31:
                jumpToOtherProcessesThree(context, FlowEstablishContractActivity.class, mpChatCommandInfo);

                break;

            case 33:
                jumpToOtherProcessesThree(context, FlowUploadDeliveryActivity.class, mpChatCommandInfo);

                break;

            case 41:
            case 42:
                jumpToOtherProcessesFour(context, FlowLastDesignActivity.class, mpChatCommandInfo);

                break;

            case 51:
            case 52:
            case 61:
            case 62:
                jumpToOtherProcessesThree(context, FlowUploadDeliveryActivity.class, mpChatCommandInfo);

                break;

            default:
                break;
        }

    }

    public int getImageForProjectInfo(String OrderDetailsInfo, boolean ifIsDesiner) {
        return getWorkflowButtonIco(OrderDetailsInfo, ifIsDesiner);
    }

    public void onChatRoomSupplementryButtonClicked(Context context, String assetId, String recieverId) {
        Intent maIntent = new Intent(context, ProjectMaterialActivity.class);
        maIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_IM);
        maIntent.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_DESIGNER_ID, recieverId);
        maIntent.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_NEEDS_ID, assetId);
        context.startActivity(maIntent);

    }


    public void onChatRoomWorkflowButtonClicked(Context context, int wk_cur_sub_node_idi, String assetId, String recieverId, String receiverUserName, String designerId) {

        if (wk_cur_sub_node_idi != 0) {
            if (wk_cur_sub_node_idi == 11 || wk_cur_sub_node_idi == -1) {
                jumpToOtherProcessesThree(context, FlowMeasureFormActivity.class, assetId, recieverId);

            } else if (wk_cur_sub_node_idi == 13) {
                jumpToOtherProcessesFour(context, FlowMeasureCostActivity.class, assetId, recieverId);

            } else if (wk_cur_sub_node_idi == 12 || wk_cur_sub_node_idi == 14) {
                jumpToOtherProcessesFour(context, FlowMeasureFormActivity.class, assetId, recieverId);

            } else if (wk_cur_sub_node_idi == 21) {
                jumpToOtherProcessesThree(context, FlowEstablishContractActivity.class, assetId, recieverId);

            } else if (wk_cur_sub_node_idi == 31) {
                jumpToOtherProcessesFour(context, FlowFirstDesignActivity.class, assetId, recieverId);

            } else if (wk_cur_sub_node_idi == 33 || wk_cur_sub_node_idi == 51) {
                jumpToOtherProcessesThree(context, FlowUploadDeliveryActivity.class, assetId, recieverId);

            } else if (wk_cur_sub_node_idi == 41) {
                jumpToOtherProcessesFour(context, FlowLastDesignActivity.class, assetId, recieverId);

            }
        } else {

            String spStr[] = new String[0];
            if (receiverUserName != null && !receiverUserName.equals("")) {
                spStr = receiverUserName.split("_");
            }
            String flow_hs_uid = spStr[1];

            getSeekDesignerDetailHomeData(context, designerId, flow_hs_uid, assetId, recieverId);

        }
    }


    /**
     * 获取设计师的从业信息，及费用
     *
     * @param designer_id 设计师的id
     * @param hsUid
     */
    public void getSeekDesignerDetailHomeData(
            final Context context, final String designer_id, final String hsUid, final String assetId, final String recieverId) {

        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {

                String info = GsonUtil.jsonToString(jsonObject);
                SeekDesignerDetailHomeBean seekDesignerDetailHomeBean = GsonUtil.jsonToBean(info, SeekDesignerDetailHomeBean.class);
                final String measureFee = seekDesignerDetailHomeBean.getDesigner().getMeasurement_price();

                Intent intent = new Intent(context, MeasureFormActivity.class);
                intent.putExtra(Constant.SeekDesignerDetailKey.NEEDS_ID, assetId);
                intent.putExtra(Constant.SeekDesignerDetailKey.DESIGNER_ID, designer_id);
                intent.putExtra(Constant.SeekDesignerDetailKey.FLOW_STATE, Constant.WorkFlowStateKey.STEP_IM);
                intent.putExtra(Constant.SeekDesignerDetailKey.HS_UID, hsUid);
                intent.putExtra(Constant.SeekDesignerDetailKey.MEASURE_FREE, measureFee);
                context.startActivity(intent);

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("test", volleyError.toString());
            }
        };
        MPServerHttpManager.getInstance().getSeekDesignerDetailHomeData(designer_id, hsUid, okResponseCallback);
    }

    private void jumpToOtherProcessesThree(Context context, Class activity, MPChatCommandInfo info) {

        Intent intent_check_room = new Intent(context, activity);
        intent_check_room.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_DESIGNER_ID, info.designer_id);
        intent_check_room.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_NEEDS_ID, info.need_id);
        intent_check_room.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_IM);
        context.startActivity(intent_check_room);
    }

    private void jumpToOtherProcessesFour(Context context, Class activity, MPChatCommandInfo info) {

        Intent intent_measure_room_cost = new Intent(context, activity);
        intent_measure_room_cost.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_DESIGNER_ID, info.designer_id);
        intent_measure_room_cost.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_NEEDS_ID, info.need_id);
        intent_measure_room_cost.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_IM);
        intent_measure_room_cost.putExtra(Constant.BundleKey.BUNDLE_ACTION_NODE_ID, MPStatusMachine.NODE__MEANSURE_PAY);
        context.startActivity(intent_measure_room_cost);
    }

    private void jumpToOtherProcessesThree(Context context, Class activity, String assetId, String recieverId) {
        Intent intent = new Intent(context, activity);
        intent.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_NEEDS_ID, assetId);
        intent.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_DESIGNER_ID, recieverId);
        intent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_IM);
        context.startActivity(intent);
    }

    private void jumpToOtherProcessesFour(Context context, Class activity, String assetId, String recieverId) {
        Intent intent = new Intent(context, activity);
        intent.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_NEEDS_ID, assetId);
        intent.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_DESIGNER_ID, recieverId);
        intent.putExtra(Constant.BundleKey.BUNDLE_ACTION_NODE_ID, MPStatusMachine.NODE__MEANSURE_PAY);
        intent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_IM);
        context.startActivity(intent);
    }


    private int getWorkflowButtonIco(String OrderDetailsInfo, boolean ifIsDesiner) {
        int wk_cur_sub_node_idi;
        String Wk_template_id;
        String Wk_cur_node_id;

        WkFlowDetailsBean wkFlowDetailsBean = new Gson().fromJson(OrderDetailsInfo, WkFlowDetailsBean.class);
        Log.d("test", "Wk_template_id:" + wkFlowDetailsBean.toString());
        String Wk_cur_sub_node_id = wkFlowDetailsBean.getRequirement().getBidders().get(0).getWk_cur_sub_node_id();
        wk_cur_sub_node_idi = Integer.valueOf(Wk_cur_sub_node_id);
        Wk_template_id = wkFlowDetailsBean.getRequirement().getWk_template_id();
        Wk_cur_node_id = wkFlowDetailsBean.getRequirement().getBidders().get(0).getWk_cur_node_id();
        Log.d("test", "Wk_cur_node_id:" + Wk_cur_node_id);


        if (Wk_template_id.equals("1")) {

            if (!Wk_cur_node_id.equals("-1")) {

                switch (wk_cur_sub_node_idi) {
                    case -1:
                    case 11: // 量房
                    case 12: // 消费者拒绝设计师
                    case 14: // 设计师拒绝量房

                        return (com.autodesk.shejijia.shared.R.drawable.amount_room_ico);

                    case 13: // 支付
                        return (com.autodesk.shejijia.shared.R.drawable.pay_ico);

                    case 21: // 合同
                    case 22: // 打开3D工具
                        return (com.autodesk.shejijia.shared.R.drawable.icon_design_contract);

                    case 31: // 首款
                        return (com.autodesk.shejijia.shared.R.drawable.pay_ico);

                    case 33: // 量房交付物
                        return (com.autodesk.shejijia.shared.R.drawable.icon_design_drawings);

                    case 41: // 支付设计首款
                    case 42: // 打开3D工具
                        return (com.autodesk.shejijia.shared.R.drawable.pay_ico);

                    case 51: // 支付尾款
                    case 52: // 打开3D工具
                        return (com.autodesk.shejijia.shared.R.drawable.icon_design_drawings);

                    case 61: // 上传支付交付物
                    case 62: // 编辑交付物
                        return (com.autodesk.shejijia.shared.R.drawable.icon_design_drawings);

                    default:
                        return -1;
                }
            } else {

                if (!ifIsDesiner) {
                    return (com.autodesk.shejijia.shared.R.drawable.amount_room_ico);
                } else {
                    return -1;
                }
            }

        } else if (Wk_template_id.equals("2")) {
            switch (wk_cur_sub_node_idi) {
                case -1:
                case 11: // 量房
                    return (com.autodesk.shejijia.shared.R.drawable.amount_room_ico);

                case 12: // 消费者同意设计师
                    return (com.autodesk.shejijia.shared.R.drawable.pay_ico);

                case 13: // 消费者拒绝设计师
                    return -1;

                case 21: // 合同
                case 22: // 打开3D工具
                    return (com.autodesk.shejijia.shared.R.drawable.icon_design_contract);

                case 31: // 首款
                    return (com.autodesk.shejijia.shared.R.drawable.pay_ico);


                case 33: // 量房交付物
                    return (com.autodesk.shejijia.shared.R.drawable.icon_design_drawings);

                case 41: // 支付设计首款
                case 42: // 打开3D工具
                    return (com.autodesk.shejijia.shared.R.drawable.pay_ico);

                case 51: // 支付尾款
                case 52: // 打开3D工具
                    return (com.autodesk.shejijia.shared.R.drawable.icon_design_drawings);

                case 61: // 上传支付交付物
                case 62: // 编辑交付物
                    return (com.autodesk.shejijia.shared.R.drawable.icon_design_drawings);

                default:
                    return -1;

            }

        } else if (Wk_template_id.equals("3")) {

        }


        return -1;
    }


}
