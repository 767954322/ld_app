package com.autodesk.shejijia.consumer;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.DesignerDetailHomeBean;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.consumer.activity.MeasureFormActivity;
import com.autodesk.shejijia.consumer.personalcenter.workflow.activity.FlowEstablishContractActivity;
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


    //聊天内容里按钮坚挺
    public void onCommandCellClicked(Context context, MPChatCommandInfo mpChatCommandInfo, String mThreadId) {
        int subNodeId = Integer.parseInt(mpChatCommandInfo.sub_node_id);

//        if (subNodeId > 62) {
//            subNodeId = 62;
//        }

        if (subNodeId == 32) {
            subNodeId = 21;
        }

        switch (subNodeId) {
            case 13:
                jumpToOtherProcessesFour(context, FlowMeasureCostActivity.class, mpChatCommandInfo, MPStatusMachine.NODE__MEANSURE_PAY, mThreadId);

                break;

            case 11:
            case 12:
            case 14:
                jumpToOtherProcessesThree(context, FlowMeasureFormActivity.class, mpChatCommandInfo, mThreadId);

                break;

            case 21:
            case 22:
            case 31:
                jumpToOtherProcessesThree(context, FlowEstablishContractActivity.class, mpChatCommandInfo, mThreadId);

                break;

            case 33:
                jumpToOtherProcessesThree(context, FlowUploadDeliveryActivity.class, mpChatCommandInfo, mThreadId);

                break;

            case 41:
            case 42:
                jumpToOtherProcessesFour(context, FlowLastDesignActivity.class, mpChatCommandInfo, MPStatusMachine.NODE__DESIGN_BALANCE_PAY, mThreadId);

                break;

            case 51:
            case 52:
            case 61:
            case 62:
            case 63:
            case 64:
            case 71:
            case 72:
                jumpToOtherProcessesThree(context, FlowUploadDeliveryActivity.class, mpChatCommandInfo, mThreadId);

                break;

            default:
                break;
        }

    }

    public int getImageForProjectInfo(String OrderDetailsInfo, boolean ifIsDesiner) {
        return getWorkflowButtonIco(OrderDetailsInfo, ifIsDesiner);
    }

    @Override
    public String getTextForProjectInfo(String OrderDetailsInfo, boolean ifIsDesiner) {
        return getWorkflowText(OrderDetailsInfo, ifIsDesiner);
    }

    public void onChatRoomSupplementryButtonClicked(Context context, String assetId, String designerId) {
        Intent maIntent = new Intent(context, ProjectMaterialActivity.class);
        maIntent.putExtra(Constant.SeekDesignerDetailKey.DESIGNER_ID, designerId);
        maIntent.putExtra(Constant.SeekDesignerDetailKey.NEEDS_ID, assetId);
        context.startActivity(maIntent);

    }
    //全流程，聊天下面小图标
    public void onChatRoomWorkflowButtonClicked(Context context, int wk_cur_sub_node_idi, String assetId, String recieverId, String receiverUserName, String designerId, String hs_uid, String mThreadId, boolean ifIsDesiner) {

        if (wk_cur_sub_node_idi != 0) {
            if (wk_cur_sub_node_idi == 11 || wk_cur_sub_node_idi == -1) {
                jumpToOtherProcessesThree(context, FlowMeasureFormActivity.class, assetId, designerId, mThreadId);

            } else if (wk_cur_sub_node_idi == 13) {
                jumpToOtherProcessesFour(context, FlowMeasureCostActivity.class, assetId, designerId, MPStatusMachine.NODE__MEANSURE_PAY, mThreadId);

            } else if (wk_cur_sub_node_idi == 12 || wk_cur_sub_node_idi == 14) {
                jumpToOtherProcessesFour(context, FlowMeasureFormActivity.class, assetId, designerId, MPStatusMachine.NODE__MEANSURE_PAY, mThreadId);

            } else if (wk_cur_sub_node_idi == 21 || wk_cur_sub_node_idi == 22) {
                jumpToOtherProcessesThree(context, FlowEstablishContractActivity.class, assetId, designerId, mThreadId);

            } else if (wk_cur_sub_node_idi == 31) {
                jumpToOtherProcessesFour(context, FlowEstablishContractActivity.class, assetId, designerId, MPStatusMachine.NODE__MEANSURE_PAY, mThreadId);

            } else if (wk_cur_sub_node_idi == 61 || wk_cur_sub_node_idi == 62 || wk_cur_sub_node_idi == 64 ||
                    wk_cur_sub_node_idi == 33 || wk_cur_sub_node_idi == 51 || wk_cur_sub_node_idi == 52 ||
                    wk_cur_sub_node_idi == 63 || wk_cur_sub_node_idi == 71 || wk_cur_sub_node_idi == 72
                    ) {
                //设计师，发送设计交付物！消费者查看设计交付物
                jumpToOtherProcessesThree(context, FlowUploadDeliveryActivity.class, assetId, designerId, mThreadId);

            }
//            else if (wk_cur_sub_node_idi == 63 || wk_cur_sub_node_idi == 71 || wk_cur_sub_node_idi == 72) {
//
//                //查看设计交付物
//                jumpToOtherProcessesThree(context, FlowUploadDeliveryActivity.class, assetId, designerId, mThreadId);
//
//            }
            else if (wk_cur_sub_node_idi == 41 || wk_cur_sub_node_idi == 42) {
                jumpToOtherProcessesFour(context, FlowLastDesignActivity.class, assetId, designerId, MPStatusMachine.NODE__DESIGN_BALANCE_PAY, mThreadId);

            }
        } else {

            String flow_hs_uid;

            if (null == hs_uid || "".equals(hs_uid)) {
                String spStr[] = new String[0];
                if (receiverUserName != null && !receiverUserName.equals("")) {
                    spStr = receiverUserName.split("_");
                }
                flow_hs_uid = spStr[1];
            } else {
                flow_hs_uid = hs_uid;
            }


            getSeekDesignerDetailHomeData(context, designerId, flow_hs_uid, assetId, mThreadId);

        }
    }


    /**
     * 获取设计师的从业信息，及费用
     *
     * @param designer_id 设计师的id
     * @param hsUid
     */
    public void getSeekDesignerDetailHomeData(
            final Context context, final String designer_id, final String hsUid, final String assetId, final String mThreadId) {

        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {

                String info = GsonUtil.jsonToString(jsonObject);
                DesignerDetailHomeBean seekDesignerDetailHomeBean = GsonUtil.jsonToBean(info, DesignerDetailHomeBean.class);
                String measureFee = seekDesignerDetailHomeBean.getDesigner().getMeasurement_price();

                if(TextUtils.isEmpty(measureFee)){
                    measureFee = "0.00";
                }
                Intent intent = new Intent(context, MeasureFormActivity.class);
                intent.putExtra(Constant.SeekDesignerDetailKey.NEEDS_ID, assetId);
                intent.putExtra(Constant.SeekDesignerDetailKey.DESIGNER_ID, designer_id);
                intent.putExtra(Constant.SeekDesignerDetailKey.FLOW_STATE, Constant.WorkFlowStateKey.STEP_IM);
                intent.putExtra(Constant.SeekDesignerDetailKey.HS_UID, hsUid);
                intent.putExtra(Constant.SeekDesignerDetailKey.MEASURE_FREE, measureFee);
                intent.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_THREAD_ID, mThreadId);
                context.startActivity(intent);

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("test", volleyError.toString());
            }
        };
        MPServerHttpManager.getInstance().getSeekDesignerDetailHomeData(designer_id, hsUid, okResponseCallback);
    }

    private void jumpToOtherProcessesThree(Context context, Class activity, MPChatCommandInfo info, String mThreadId) {

        Intent intent_check_room = new Intent(context, activity);

        intent_check_room.putExtra(Constant.SeekDesignerDetailKey.DESIGNER_ID, info.designer_id);
        intent_check_room.putExtra(Constant.SeekDesignerDetailKey.NEEDS_ID, info.need_id);
        intent_check_room.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_THREAD_ID, mThreadId);
        context.startActivity(intent_check_room);
    }

    private void jumpToOtherProcessesFour(Context context, Class activity, MPChatCommandInfo info, int pay_state, String mThreadId) {

        Intent intent_measure_room_cost = new Intent(context, activity);
        intent_measure_room_cost.putExtra(Constant.SeekDesignerDetailKey.DESIGNER_ID, info.designer_id);
        intent_measure_room_cost.putExtra(Constant.SeekDesignerDetailKey.NEEDS_ID, info.need_id);
        intent_measure_room_cost.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_THREAD_ID, mThreadId);
        intent_measure_room_cost.putExtra(Constant.BundleKey.TEMPDATE_ID, pay_state);
        context.startActivity(intent_measure_room_cost);
    }

    private void jumpToOtherProcessesThree(Context context, Class activity, String assetId, String recieverId, String mThreadId) {
        Intent intent = new Intent(context, activity);
        intent.putExtra(Constant.SeekDesignerDetailKey.NEEDS_ID, assetId);
        intent.putExtra( Constant.SeekDesignerDetailKey.DESIGNER_ID, recieverId);
        intent.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_THREAD_ID, mThreadId);
        context.startActivity(intent);
    }

    private void jumpToOtherProcessesFour(Context context, Class activity, String assetId, String recieverId, int pay_state, String mThreadId) {
        Intent intent = new Intent(context, activity);
        intent.putExtra( Constant.SeekDesignerDetailKey.NEEDS_ID, assetId);
        intent.putExtra(Constant.SeekDesignerDetailKey.DESIGNER_ID, recieverId);
        intent.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_THREAD_ID, mThreadId);
        intent.putExtra(Constant.BundleKey.TEMPDATE_ID, pay_state);
        context.startActivity(intent);
    }


    private int getWorkflowButtonIco(String OrderDetailsInfo, boolean ifIsDesiner) {
        int wk_cur_sub_node_idi;
        String Wk_template_id;
        String Wk_cur_node_id;

        WkFlowDetailsBean wkFlowDetailsBean = new Gson().fromJson(OrderDetailsInfo, WkFlowDetailsBean.class);
        String Wk_cur_sub_node_id = wkFlowDetailsBean.getRequirement().getBidders().get(0).getWk_cur_sub_node_id();
        wk_cur_sub_node_idi = Integer.valueOf(Wk_cur_sub_node_id);
        Wk_template_id = wkFlowDetailsBean.getRequirement().getWk_template_id();
        Wk_cur_node_id = wkFlowDetailsBean.getRequirement().getBidders().get(0).getWk_cur_node_id();

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
                        return (com.autodesk.shejijia.shared.R.drawable.jiaofu);

                    case 31: // 首款
                        return (com.autodesk.shejijia.shared.R.drawable.jiaofu);

                    case 33: // 量房交付物
                        return (com.autodesk.shejijia.shared.R.drawable.jiaofg);

                    case 41: // 支付设计首款
                    case 42: // 打开3D工具
                        return (com.autodesk.shejijia.shared.R.drawable.pay_ico);

                    case 51: // 支付尾款
                    case 52: // 打开3D工具
                        return (com.autodesk.shejijia.shared.R.drawable.jiaofg);

                    case 61: // 上传支付交付物
                    case 62: // 编辑交付物
                    case 63: // 交付曲确认
                    case 64: // 交付曲延期
                    case 71: // 评价
                    case 72: // 稍后评价
                        return (com.autodesk.shejijia.shared.R.drawable.jiaofg);

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

                case 13: // 消费者同意设计师
                    return (com.autodesk.shejijia.shared.R.drawable.pay_ico);

                case 14: // 消费者拒绝设计师
                    return (com.autodesk.shejijia.shared.R.drawable.amount_room_ico);

                case 21: // 合同
                case 22: // 打开3D工具
                    return (com.autodesk.shejijia.shared.R.drawable.jiaofu);

                case 31: // 首款
                    return (com.autodesk.shejijia.shared.R.drawable.jiaofu);


                case 33: // 量房交付物
                    return (com.autodesk.shejijia.shared.R.drawable.jiaofg);

                case 41: // 支付设计首款
                case 42: // 打开3D工具
                    return (com.autodesk.shejijia.shared.R.drawable.pay_ico);

                case 51: // 支付尾款
                case 52: // 打开3D工具
                    return (com.autodesk.shejijia.shared.R.drawable.jiaofg);

                case 61: // 上传支付交付物
                case 62: // 编辑交付物
                case 63: // 交付曲确认
                case 64: // 交付曲延期
                case 71: // 评价
                case 72: // 稍后评价
                    return (com.autodesk.shejijia.shared.R.drawable.jiaofg);

                default:
                    return -1;

            }

        } else if (Wk_template_id.equals("3")) {

        }


        return -1;
    }


    private String getWorkflowText(String OrderDetailsInfo, boolean ifIsDesiner) {
        int wk_cur_sub_node_idi;
        String Wk_template_id;
        String Wk_cur_node_id;

        WkFlowDetailsBean wkFlowDetailsBean = new Gson().fromJson(OrderDetailsInfo, WkFlowDetailsBean.class);
        String Wk_cur_sub_node_id = wkFlowDetailsBean.getRequirement().getBidders().get(0).getWk_cur_sub_node_id();
        wk_cur_sub_node_idi = Integer.valueOf(Wk_cur_sub_node_id);
        Wk_template_id = wkFlowDetailsBean.getRequirement().getWk_template_id();
        Wk_cur_node_id = wkFlowDetailsBean.getRequirement().getBidders().get(0).getWk_cur_node_id();

        if (Wk_template_id.equals("1")) {

            if (!Wk_cur_node_id.equals("-1")) {

                switch (wk_cur_sub_node_idi) {
                    case -1:
                        return "选TA量房";
                    case 11: // 量房
                        if (ifIsDesiner) {
                            return "确认量房";
                        }
                        return "量房表单";

                    case 12: // 消费者拒绝设计师
                    case 14: // 设计师拒绝量房
                        return "量房表单";
                    case 13: // 支付
                        if (ifIsDesiner) {
                            return "接收量房费";
                        } else {
                            return "支付量房费";
                        }

                    case 21: // 合同
                    case 22: // 打开3D工具
                        if (ifIsDesiner) {
                            return "创建设计合同";
                        } else {
                            return "接收设计合同";
                        }
                    case 31: // 首款

                        if (ifIsDesiner) {
                            return "修改合同";
                        } else {
                            return "支付首款";
                        }
                    case 33: // 量房交付物
                        if (ifIsDesiner) {
                            return "上传量房交付物";
                        } else {
                            return "接受量房交付物";
                        }

                    case 41: // 支付设计首款
                    case 42: // 打开3D工具
                        if (ifIsDesiner) {
                            return "接收尾款";
                        } else {
                            return "支付尾款";
                        }

                    case 51: // 支付尾款
                    case 52: // 打开3D工具
                        if (ifIsDesiner) {
                            return "上传设计交付物";
                        } else {
                            return "接收设计交付物";
                        }

                    case 61: // 上传支付交付物
                    case 62: // 编辑交付物
                    case 64: // 交付曲延期
                        if (ifIsDesiner) {
                            return "修改设计交付物";
                        } else {
                            return "查看设计交付物";
                        }


                    case 63: // 交付曲确认
                    case 71: // 评价
                    case 72: // 稍后评价
                        return "查看设计交付物";

                    default:
                        return "";
                }
            } else {

                if (!ifIsDesiner) {
                    return "选TA量房";
                } else {
                    return "";
                }
            }

        } else if (Wk_template_id.equals("2")) {

            switch (wk_cur_sub_node_idi) {
                case 11: // 量房
                    if (ifIsDesiner) {
                        return "确认量房";
                    }
                    return "量房表单";
                case 14: // 设计师拒绝量房
                    return "量房表单";
                case 13: // 支付
                    if (ifIsDesiner) {
                        return "接收量房费";
                    } else {
                        return "支付量房费";
                    }

                case 21: // 合同
                case 22: // 打开3D工具
                    if (ifIsDesiner) {
                        return "创建设计合同";
                    } else {
                        return "接收设计合同";
                    }
                case 31: // 首款

                    if (ifIsDesiner) {
                        return "接收首款";
                    } else {
                        return "支付首款";
                    }
                case 33: // 量房交付物
                    if (ifIsDesiner) {
                        return "上传量房交付物";
                    } else {
                        return "接受量房交付物";
                    }

                case 41: // 支付设计首款
                case 42: // 打开3D工具
                    if (ifIsDesiner) {
                        return "接收尾款";
                    } else {
                        return "支付尾款";
                    }

                case 51: // 支付尾款
                case 52: // 打开3D工具
                    if (ifIsDesiner) {
                        return "上传设计交付物";
                    } else {
                        return "接收设计交付物";
                    }

                case 61: // 上传支付交付物
                case 62: // 编辑交付物
                case 64: // 交付曲延期

                    if (ifIsDesiner) {
                        return "修改设计交付物";
                    } else {
                        return "查看设计交付物";
                    }
                case 63: // 交付曲确认
                case 71: // 评价
                case 72: // 稍后评价
                    return "查看设计交付物";

                default:
                    if (!ifIsDesiner) {
                        return "选TA量房";
                    } else {
                        return "";
                    }
            }

        } else if (Wk_template_id.equals("3")) {
            return "";
        }


        return "";
    }

    public void getCloudFilesAsync(final String X_Token, final String assetId, final String memberId,
                                   final OkJsonRequest.OKResponseCallback callback) {
        MPServerHttpManager.getInstance().getCloudFiles(X_Token, assetId, memberId, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onErrorResponse(volleyError);
            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                callback.onResponse(jsonObject);
            }
        });
    }


}