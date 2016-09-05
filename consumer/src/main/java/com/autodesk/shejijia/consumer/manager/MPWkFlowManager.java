package com.autodesk.shejijia.consumer.manager;

import android.content.Context;
import android.util.Log;

import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.SubNodes;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.TipWorkFlowTemplateBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.TipWorkflowInfoBean;
import com.autodesk.shejijia.consumer.utils.WkFlowStateMap;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;

import java.util.List;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-6-7 .
 * @file MPWkFlowManager.java .
 * @brief 全流程状态显示管理类  .
 */
public class MPWkFlowManager {

    // tempdate_id
    private static final int BIDING = 1; // 应标
    private static final int CHOOSE = 2; // 自选
    private static final int BEISHU = 3; // 北舒
    private static final int ELITE = 4; // 精选

    public static final int START_NODE = -1;               // 应标中、自选中或者派单中
    private static final String MEASURE = "1";              // 量房
    private static final String PAYMENT_OF_MEASURE = "2";   //支付量房费
    private static final String CONFIRM_CONTRACT = "3";     //创建设计合同
    public static final String PAYMENT_OF_FIRST_FEE = "4"; //支付首款
    private static final String PAYMENT_OF_LAST_FEE = "5";  //支付尾款
    private static final String DELIVERY = "6";             //设计交付物
    private static final String DELIVERY_ESTIMATE = "7";    //评价

    private MPWkFlowManager() {
    }

    /**
     * 返回不同节点模版的节点的名称
     *
     * @param context            上下文对象
     * @param wk_template_id     节点模版类型
     * @param wk_cur_sub_node_id 节点模版
     * @return
     */
    public static String getWkSubNodeName(Context context, String wk_template_id, String wk_cur_sub_node_id) {
        String wkSubNodeName = "未知状态";
        int wk_cur_sub_node_id_int;
        int wk_template_id_int;
        MemberEntity memberEntity;
        String memType = null;

        TipWorkFlowTemplateBean tipWkFlowTemplateBean;
        List<TipWorkFlowTemplateBean> wkFlowBeans;
        List<TipWorkflowInfoBean> workflowInFos;

        memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (null != memberEntity) {
            memType = memberEntity.getMember_type();
        } else {
            return wkSubNodeName;
        }

        if (!StringUtils.isNumeric(wk_template_id)) {
            return wkSubNodeName;
        }

        if (!StringUtils.isNumeric(wk_cur_sub_node_id)) {
            return wkSubNodeName;
        }

        if (null == WkFlowStateMap.sWkFlowBeans) {
            return wkSubNodeName;
        }

        wk_cur_sub_node_id_int = Integer.parseInt(wk_cur_sub_node_id);
        wk_template_id_int = Integer.parseInt(wk_template_id);
        wkFlowBeans = WkFlowStateMap.sWkFlowBeans;

        if (wk_template_id_int < 1) {
            return wkSubNodeName;
        }

        tipWkFlowTemplateBean = wkFlowBeans.get(wk_template_id_int - 1);
        workflowInFos = tipWkFlowTemplateBean.getTip_workflow_infos();

        switch (wk_template_id_int) {
            case BIDING: // 应标
            case CHOOSE: // 自选
            case ELITE:  // 精选 .
                wkSubNodeName = handleSubNodeId(memType, wk_cur_sub_node_id_int, workflowInFos);
                break;

            case BEISHU: // 北舒 .
            default:
                wkSubNodeName = handleBeiShu();
                break;

        }
        return wkSubNodeName;

    }

    /**
     * 处理节点提示逻辑
     *
     * @param memType                用户类型
     * @param wk_cur_sub_node_id_int 当前节点
     * @param tipWorkflowInfoBeen    节点信息
     * @return 当前节点的名字
     */
    private static String handleSubNodeId(String memType,
                                          int wk_cur_sub_node_id_int,
                                          List<TipWorkflowInfoBean> tipWorkflowInfoBeen) {
        String wkSubNodeName = "未知状态";
        List<SubNodes> sub_nodes;
        int subNodeId;

        for (TipWorkflowInfoBean infoBean : tipWorkflowInfoBeen) {
            switch (wk_cur_sub_node_id_int) {
                case START_NODE:
                    switch (memType) {
                        case Constant.UerInfoKey.CONSUMER_TYPE:
                            wkSubNodeName = infoBean.getTip_for_consumer();
                            break;

                        case Constant.UerInfoKey.DESIGNER_TYPE:
                            wkSubNodeName = infoBean.getTip_for_designer();
                            break;
                    }
                    break;

                default:
                    sub_nodes = infoBean.getSub_nodes();
                    Log.d("MPWkFlowManager", "sub_nodes:" + sub_nodes);
                    if (null != sub_nodes) {
                        for (SubNodes subNode : sub_nodes) {
                            subNodeId = subNode.getId();
                            if (wk_cur_sub_node_id_int == subNodeId) {
                                switch (memType) {
                                    case Constant.UerInfoKey.CONSUMER_TYPE:
                                        wkSubNodeName = subNode.getTip_for_consumer();
                                        break;

                                    case Constant.UerInfoKey.DESIGNER_TYPE:
                                        wkSubNodeName = subNode.getTip_for_designer();
                                        break;
                                }
                            }
                        }
                    }
                    break;
            }
        }
        return wkSubNodeName;
    }

    /**
     * 处理套餐项目逻辑
     */
    private static String handleBeiShu() {
        return null;
    }
}
