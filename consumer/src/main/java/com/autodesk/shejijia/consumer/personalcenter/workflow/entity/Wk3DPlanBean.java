package com.autodesk.shejijia.consumer.personalcenter.workflow.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-6-12 .
 * @file Wk3DPlanBean.java .
 * @brief 获取与装修项目相关联的3D方案列表.
 */
public class Wk3DPlanBean implements Serializable {
    private int count;
    private int limit;
    private int offset;

    private List<MPThreeDimensBean> three_dimensionals;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public List<MPThreeDimensBean> getThree_dimensionals() {
        return three_dimensionals;
    }

    public void setThree_dimensionals(List<MPThreeDimensBean> three_dimensionals) {
        this.three_dimensionals = three_dimensionals;
    }

}
