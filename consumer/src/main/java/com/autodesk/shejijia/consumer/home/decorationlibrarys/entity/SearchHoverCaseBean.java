package com.autodesk.shejijia.consumer.home.decorationlibrarys.entity;

import java.io.Serializable;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 2016/2/25 0025 18:51 .
 * @file user SearchHoverCaseBean .
 * @brief 搜索内容bean .
 */
public class SearchHoverCaseBean implements Serializable {
    int type;
    String key;
    String value; /// name .
    String code;    /// code .
    String description; /// 单位.

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}