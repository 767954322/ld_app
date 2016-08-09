package com.autodesk.shejijia.consumer.personalcenter.workflow.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-6-12 .
 * @file Wk3DPlanDelivery.java .
 * @brief 某一套设计方案的实体类 .
 */
public class Wk3DPlanDelivery implements Serializable {

    private int count;

    private List<MPFileBean> deliveryFiles;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<MPFileBean> getDeliveryFiles() {
        return deliveryFiles;
    }

    public void setDeliveryFiles(List<MPFileBean> deliveryFiles) {
        this.deliveryFiles = deliveryFiles;
    }
}
