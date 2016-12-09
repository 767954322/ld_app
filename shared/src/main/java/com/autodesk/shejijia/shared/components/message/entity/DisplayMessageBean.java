package com.autodesk.shejijia.shared.components.message.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Created by luchongbin on 2016/12/9.
 */


public  class DisplayMessageBean implements Serializable {
    private String summary;
    @SerializedName("detail_items")
    private ArrayList<String> detailItems;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public ArrayList<String> getDetailItems() {
        return detailItems;
    }

    public void setDetailItems(ArrayList<String> detailItems) {
        this.detailItems = detailItems;
    }
}
