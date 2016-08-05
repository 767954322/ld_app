package com.autodesk.shejijia.consumer.manager;

import android.content.Context;

import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.WkFlowStateBean;
import com.autodesk.shejijia.consumer.utils.WkFlowStateMap;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
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
        String memType = null;

        if (WkFlowStateMap.map != null) {
            mapWkFlowState = WkFlowStateMap.map;

            MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
            if (null != memberEntity) {
                memType = memberEntity.getMember_type();
            }
            switch (memType) {
                case Constant.UerInfoKey.CONSUMER_TYPE:
                    return mapWkFlowState.get(wk_cur_sub_node_id).getConsumerMessage();
                case Constant.UerInfoKey.DESIGNER_TYPE:
                    return mapWkFlowState.get(wk_cur_sub_node_id).getDesignerMessage();
                default:
                    return "未知状态";
            }
        } else {
            return "未知状态";
        }
    }
}
