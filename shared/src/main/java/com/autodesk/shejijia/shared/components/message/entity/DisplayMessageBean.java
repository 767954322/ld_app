package com.autodesk.shejijia.shared.components.message.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by luchongbin on 2016/12/9.
 */


public  class DisplayMessageBean implements Serializable {
    private String summary;
    private List<String> detail_items;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<String> getDetail_items() {
        return detail_items;
    }

    public void setDetail_items(List<String> detail_items) {
        this.detail_items = detail_items;
    }
}
