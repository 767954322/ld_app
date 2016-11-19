package com.autodesk.shejijia.shared.components.common.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_xuz on 10/19/16.
 * update 10/19
 * 项目列表接口返回的json解析对应的bean
 */
public class ProjectList implements Serializable{

    private int total;
    private int limit;
    private int offset;
    private List<ProjectInfo> data;

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

    public List<ProjectInfo> getData() {
        return data;
    }

    public void setData(List<ProjectInfo> data) {
        this.data = data;
    }
}
