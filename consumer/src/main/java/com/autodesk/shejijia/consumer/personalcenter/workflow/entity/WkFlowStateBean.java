package com.autodesk.shejijia.consumer.personalcenter.workflow.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author yaoxuehua .
 * @version v1.0 .
 * @date 2016-8-3 .
 * @file WkFlowStateBean.java .
 * @brief 全流程状态节点信息bean.
 */
public class WkFlowStateBean implements Serializable {
    private String id;//code
    private String name;//节点名
    private String description;//节点描述
    private String consumerMessage;//消费者端节点状态描述
    private String designerMessage;//设计师端节点状态描述

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

    public String getConsumerMessage() {
        return consumerMessage;
    }

    public void setConsumerMessage(String consumerMessage) {
        this.consumerMessage = consumerMessage;
    }

    public String getDesignerMessage() {
        return designerMessage;
    }

    public void setDesignerMessage(String designerMessage) {
        this.designerMessage = designerMessage;
    }

    @Override
    public String toString() {
        return "WkFlowStateBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", consumerMessage='" + consumerMessage + '\'' +
                ", designerMessage='" + designerMessage + '\'' +
                '}';
    }
}
