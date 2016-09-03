package com.autodesk.shejijia.consumer.personalcenter.workflow.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by luchongbin on 16-8-28.
 */
public class TipWorkflowInfoBean implements Serializable {
    private String id;
    private String name;
    private String description;
    private String workflowTemplateId;
    private String tip_for_designer;
    private String tip_for_consumer;

    private List<SubNodes> sub_nodes;

    public List<SubNodes> getSub_nodes() {
        return sub_nodes;
    }

    public void setSub_nodes(List<SubNodes> sub_nodes) {
        this.sub_nodes = sub_nodes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWorkflowTemplateId() {
        return workflowTemplateId;
    }

    public void setWorkflowTemplateId(String workflowTemplateId) {
        this.workflowTemplateId = workflowTemplateId;
    }

    public String getTip_for_designer() {
        return tip_for_designer;
    }

    public void setTip_for_designer(String tip_for_designer) {
        this.tip_for_designer = tip_for_designer;
    }

    public String getTip_for_consumer() {
        return tip_for_consumer;
    }

    public void setTip_for_consumer(String tip_for_consumer) {
        this.tip_for_consumer = tip_for_consumer;
    }
}
