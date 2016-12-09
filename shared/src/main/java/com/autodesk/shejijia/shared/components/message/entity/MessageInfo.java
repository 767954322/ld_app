package com.autodesk.shejijia.shared.components.message.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by luchongbin on 2016/12/6.
 */
public class MessageInfo implements Serializable {
    private int total;
    private int limit;
    private int offset;
    private List<DataBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }
}
