package com.autodesk.shejijia.consumer.personalcenter.workflow.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by luchongbin on 16-8-28.
 */
public class TipWorkFlowTemplateBean implements Serializable {

    private int tempdate_id;
    private List<TipWorkflowInfoBean> tip_workflow_infos;

    public int getTempdate_id() {
        return tempdate_id;
    }

    public void setTempdate_id(int tempdate_id) {
        this.tempdate_id = tempdate_id;
    }

    public List<TipWorkflowInfoBean> getTip_workflow_infos() {
        return tip_workflow_infos;
    }

    public void setTip_workflow_infos(List<TipWorkflowInfoBean> tip_workflow_infos) {
        this.tip_workflow_infos = tip_workflow_infos;
    }
}
