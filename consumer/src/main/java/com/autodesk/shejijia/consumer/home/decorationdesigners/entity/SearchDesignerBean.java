package com.autodesk.shejijia.consumer.home.decorationdesigners.entity;

import java.io.Serializable;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 2016/2/25 0025 18:51 .
 * @file 　FiltrateContentBean .
 * @brief 筛选内容bean .
 */
public class SearchDesignerBean implements Serializable {
    /**
     * 户型
     */
    private String workTime;
    /**
     * 设计费
     */
    private String design_price_code;
    /**
     * 风格
     */
    private String style;

    private int styleIndex = 0;
    private int houseIndex = 0;
    private int areaIndex = 0;

    public int getHouseIndex() {
        return houseIndex;
    }

    public void setHouseIndex(int houseIndex) {
        this.houseIndex = houseIndex;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getDesign_price_code() {
        return design_price_code;
    }

    public void setDesign_price_code(String design_price_code) {
        this.design_price_code = design_price_code;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public int getStyleIndex() {
        return styleIndex;
    }

    public void setStyleIndex(int styleIndex) {
        this.styleIndex = styleIndex;
    }

    public int getAreaIndex() {
        return areaIndex;
    }

    public void setAreaIndex(int areaIndex) {
        this.areaIndex = areaIndex;
    }
}
