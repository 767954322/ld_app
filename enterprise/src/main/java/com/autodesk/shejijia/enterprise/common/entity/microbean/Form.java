package com.autodesk.shejijia.enterprise.common.entity.microbean;

import java.io.Serializable;

/**
 * Created by t_xuz on 10/19/16.
 * version 1.0 update 10/19
 * Task 里 forms字段
 */
public class Form implements Serializable{

    private String category;
    private String template_id;
    private String form_id;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getForm_id() {
        return form_id;
    }

    public void setForm_id(String form_id) {
        this.form_id = form_id;
    }
}
