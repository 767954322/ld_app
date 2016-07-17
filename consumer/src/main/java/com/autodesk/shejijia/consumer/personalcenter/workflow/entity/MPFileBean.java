package com.autodesk.shejijia.consumer.personalcenter.workflow.entity;

import java.io.Serializable;

/**
 * <p>Description:全流程文件的实体类</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: http://www.leediancn.com</p>
 *
 * @author he.liu .
 * @date 2016-07-17.
 */
public class MPFileBean implements Serializable {
    /**
     * url : http://sherpapreview-standard.s3.cn-north-1.amazonaws.com.cn/Preview/Public/Beta/18122433.png
     * type : 1
     * filed_id : 18122433
     * usage_type : 2
     * filed_name : Errormessage.png
     * submitted_date : null
     */
    private String url;
    private String type;
    private String filed_id;
    private String usage_type;
    private String filed_name;
    private String submitted_date;

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
}
