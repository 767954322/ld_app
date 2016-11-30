package com.autodesk.shejijia.shared.components.form.common.entity;

/**
 * Created by t_aij on 16/11/29.
 */

public class ItemCell {
    private String title;   //标题
    private String result; //结果
    private int reinspectionNum;   //需要复检的数目
    private boolean isShow; //是否显示错误信息

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getReinspectionNum() {
        return reinspectionNum;
    }

    public void setReinspectionNum(int reinspectionNum) {
        this.reinspectionNum = reinspectionNum;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
