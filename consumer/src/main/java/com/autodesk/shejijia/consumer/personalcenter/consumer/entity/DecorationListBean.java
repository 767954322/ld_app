package com.autodesk.shejijia.consumer.personalcenter.consumer.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author he.liu .
 * @version 1.0 .
 * @date 16-6-7 上午11:20
 * @file DecorationListBean.java  .
 * @brief 家装订单的实体类 .
 */
public class DecorationListBean implements Serializable {
    private int count;
    private String date;
    private int limit;
    private int offset;
    private String _link;

    private List<DecorationNeedsListBean> needs_list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String get_link() {
        return _link;
    }

    public void set_link(String _link) {
        this._link = _link;
    }

    public List<DecorationNeedsListBean> getNeeds_list() {
        return needs_list;
    }

    public void setNeeds_list(List<DecorationNeedsListBean> needs_list) {
        this.needs_list = needs_list;
    }
}
