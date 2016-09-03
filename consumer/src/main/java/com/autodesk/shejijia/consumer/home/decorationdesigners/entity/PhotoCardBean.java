package com.autodesk.shejijia.consumer.home.decorationdesigners.entity;

import java.io.Serializable;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-8-22 .
 * @file PhotoCardBean.java .
 * @brief 身份证信息实体类  .
 */
public class PhotoCardBean implements Serializable {
    private String file_id;
    private String file_name;
    private String file_url;

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }
}
