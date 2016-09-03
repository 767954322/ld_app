package com.autodesk.shejijia.consumer.personalcenter.workflow.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-07-17 .
 * @file MPDeliveryBean.java .
 * @brief 交付物实体类 .
 */
public class MPDeliveryBean implements Serializable {
    private List<MPFileBean> files;
    private String type;
    private String designer_id;

    public List<MPFileBean> getFiles() {
        return files;
    }

    public void setFiles(List<MPFileBean> files) {
        this.files = files;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesigner_id() {
        return designer_id;
    }

    public void setDesigner_id(String designer_id) {
        this.designer_id = designer_id;
    }
}
