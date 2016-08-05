package com.autodesk.shejijia.consumer.manager;

import android.content.Context;
import android.text.TextUtils;

import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.WkFlowStateBean;
import com.autodesk.shejijia.consumer.utils.WkFlowStateMap;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-6-7 .
 * @file MPWkFlowManager.java .
 * @brief 全流程状态显示管理类  .
 */
public class MPWkFlowManager {

    private MPWkFlowManager() {
    }

    /**
     * 获取当前节点的名字
     *
     * @param context            上下文对象
     * @param wk_cur_sub_node_id 当前节点
     * @return 当前节点的名字
     */
    public static String getWkSubNodeName(Context context, String wk_template_id, String wk_cur_sub_node_id) {
        Map<String, WkFlowStateBean> mapWkFlowState = new HashMap<>();
        WkFlowStateBean wkFlowStateBean = null;
        String wkSubNodeName = "未知状态";
        String memType = null;

        if (!StringUtils.isNumeric(wk_cur_sub_node_id)) {
            return wkSubNodeName;
        }
        int wk_cur_sub_node_id_int = Integer.parseInt(wk_cur_sub_node_id);

        if (null != WkFlowStateMap.map) {
            mapWkFlowState = WkFlowStateMap.map;
            MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
            if (null != memberEntity) {
                memType = memberEntity.getMember_type();
                /**
                 * 项目完成之前的逻辑
                 */
                wkFlowStateBean = mapWkFlowState.get(wk_cur_sub_node_id);
                if (null != wkFlowStateBean) {
                    switch (memType) {
                        case Constant.UerInfoKey.CONSUMER_TYPE:

                            if (TextUtils.isEmpty(wkFlowStateBean.getConsumerMessage())) {
                                return wkFlowStateBean.getDescription();
                            }
                            return wkFlowStateBean.getConsumerMessage();

                        case Constant.UerInfoKey.DESIGNER_TYPE:
                            if (TextUtils.isEmpty(wkFlowStateBean.getDesignerMessage())) {
                                return wkFlowStateBean.getDescription();
                            }
                            return wkFlowStateBean.getDesignerMessage();
                        default:
                            return wkSubNodeName;
                    }
                } else {
                    /**
                     * 超过64节点，进入评价环节
                     */
                    if (wk_cur_sub_node_id_int > 64) {
                        wkFlowStateBean = WkFlowStateMap.map.get("63");
                        if (null != wkFlowStateBean){
                            switch (memType) {
                                case Constant.UerInfoKey.CONSUMER_TYPE:

                                    if (TextUtils.isEmpty(wkFlowStateBean.getConsumerMessage())) {
                                        return wkFlowStateBean.getDescription();
                                    }
                                    return wkFlowStateBean.getConsumerMessage();

                                case Constant.UerInfoKey.DESIGNER_TYPE:
                                    if (TextUtils.isEmpty(wkFlowStateBean.getDesignerMessage())) {
                                        return wkFlowStateBean.getDescription();
                                    }
                                    return wkFlowStateBean.getDesignerMessage();
                                default:
                                    return wkSubNodeName;
                            }
                        }
                    }
                }
            }
        }
        return wkSubNodeName;
    }
}
