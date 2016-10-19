package com.autodesk.shejijia.enterprise.common.entity.microbean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by t_xuz on 10/19/16.
 * version 1.0 update 10/19
 * Task 里 forms字段
 */
public class Form implements Serializable{

    private String category;
    @SerializedName("template_id")
    private String templateId;
    @SerializedName("form_id")
    private String formId;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }
}
