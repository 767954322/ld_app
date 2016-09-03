package com.autodesk.shejijia.consumer.utils;

import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.TipWorkFlowTemplateBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.WkFlowStateBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaoxuehua .
 * @version v1.0 .
 * @date 2016-8-3 .
 * @file WkFlowStateBean.java .
 * @brief 全流程状态节点信息map.
 */
public class WkFlowStateMap {

    public static Map<String,WkFlowStateBean> map = new HashMap<String, WkFlowStateBean>();
    /**
     * 1:
     * 2:
     * 3:
     * 4: 精选全流程
     */
    public static List<TipWorkFlowTemplateBean> sWkFlowBeans = new ArrayList<>();

}
