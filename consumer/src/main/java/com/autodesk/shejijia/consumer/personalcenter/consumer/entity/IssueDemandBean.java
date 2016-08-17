package com.autodesk.shejijia.consumer.personalcenter.consumer.entity;

import java.io.Serializable;

/**
 * @author luchongbin .
 * @version 1.0 .
 * @date 16-6-7 上午11:21
 * @file IssueDemandBean.java  .
 * @brief 发布需求成功后返回json转换的实体类.
 */
public class IssueDemandBean implements Serializable {

    /**
     * needs_id : 133475
     * needs_name : 天乐园1室1厅1卫房间装修设计
     * status : 01
     */
    private String needs_id;
    private String needs_name;
    private String status;

    public void setNeeds_id(String needs_id) {
        this.needs_id = needs_id;
    }

    public void setNeeds_name(String needs_name) {
        this.needs_name = needs_name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNeeds_id() {
        return needs_id;
    }

    public String getNeeds_name() {
        return needs_name;
    }

    public String getStatus() {
        return status;
    }
}
