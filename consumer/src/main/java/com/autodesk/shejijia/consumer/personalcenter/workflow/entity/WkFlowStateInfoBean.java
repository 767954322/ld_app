package com.autodesk.shejijia.consumer.personalcenter.workflow.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by luchongbin on 16-8-28.
 */
public class WkFlowStateInfoBean implements Serializable {

    private List<TipWorkFlowTemplateBean> tip_work_flow_template;

    public List<TipWorkFlowTemplateBean> getTip_work_flow_template() {
        return tip_work_flow_template;
    }

    public void setTip_work_flow_template(List<TipWorkFlowTemplateBean> tip_work_flow_template) {
        this.tip_work_flow_template = tip_work_flow_template;
    }


}

