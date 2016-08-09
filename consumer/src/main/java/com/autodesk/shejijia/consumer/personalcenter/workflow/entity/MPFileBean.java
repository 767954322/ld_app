package com.autodesk.shejijia.consumer.personalcenter.workflow.entity;

import java.io.Serializable;

/**
 * @author he.liu .
 * @version 1.0 .
 * @date 2016-07-17
 * @file MPFileBean.java  .
 * @brief 全流程文件的实体类.
 */
public class MPFileBean implements Serializable {
    private String url;
    private String type;
    private String filed_id;
    private String usage_type;
    private String filed_name;
    private String submitted_date;

    private String id;
    private String name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFiled_id() {
        return filed_id;
    }

    public void setFiled_id(String filed_id) {
        this.filed_id = filed_id;
    }

    public String getUsage_type() {
        return usage_type;
    }

    public void setUsage_type(String usage_type) {
        this.usage_type = usage_type;
    }

    public String getFiled_name() {
        return filed_name;
    }

    public void setFiled_name(String filed_name) {
        this.filed_name = filed_name;
    }

    public String getSubmitted_date() {
        return submitted_date;
    }

    public void setSubmitted_date(String submitted_date) {
        this.submitted_date = submitted_date;
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
}
