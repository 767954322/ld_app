package com.autodesk.shejijia.consumer.home.decorationdesigners.entity;

import java.io.Serializable;

/**
 * Created by xueqiudong on 16-8-11.
 */
public class DesignerFiltrateBean implements Serializable {
    /**
     * 工作年限
     */
    private String  workingYears;
    /**
     * 风格
     */
    private String style;
    /**
     * 价格
     */
    private String price;

    private int yearIndex = 0;
    private int styleIndex = 0;
    private int priceIndex = 0;

    public String getWorkingYears() {
        return workingYears;
    }

    public void setWorkingYears(String workingYears) {
        this.workingYears = workingYears;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getYearIndex() {
        return yearIndex;
    }

    public void setYearIndex(int yearIndex) {
        this.yearIndex = yearIndex;
    }

    public int getStyleIndex() {
        return styleIndex;
    }

    public void setStyleIndex(int styleIndex) {
        this.styleIndex = styleIndex;
    }

    public int getPriceIndex() {
        return priceIndex;
    }

    public void setPriceIndex(int priceIndex) {
        this.priceIndex = priceIndex;
    }
}
