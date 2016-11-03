package com.autodesk.shejijia.consumer.personalcenter.recommend.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @User: 蜡笔小新
 * @date: 16-10-24
 * @GitHub: https://github.com/meikoz
 */

public class RecommendBean implements Serializable{

    private int total;
    private int offset;
    private int limit;
    private List<RecommendDetailsBean> items;
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<RecommendDetailsBean> getItems() {
        return items;
    }

    public void setItems(List<RecommendDetailsBean> items) {
        this.items = items;
    }
}
