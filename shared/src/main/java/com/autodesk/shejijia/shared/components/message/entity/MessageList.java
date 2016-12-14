package com.autodesk.shejijia.shared.components.message.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by luchongbin on 2016/12/6.
 */
public class MessageList implements Serializable {
    private int total;
    private int limit;
    private int offset;
    @SerializedName("data")
    private ArrayList<MessageItemBean> messageItemBean;

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

    public ArrayList<MessageItemBean> getMessageItemBean() {
        return messageItemBean;
    }

    public void setMessageItemBean(ArrayList<MessageItemBean> messageItemBean) {
        this.messageItemBean = messageItemBean;
    }
}
