package com.autodesk.shejijia.consumer.home.decorationlibrarys.entity;

import java.io.Serializable;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 2016/2/25 0025 18:51 .
 * @file 　FiltrateContentBean .
 * @brief 筛选内容bean .
 */
public class FiltrateContentBean implements Serializable {
    /**
     * 户型
     */
    private String housingType;
    /**
     * 面积
     */
    private String area;
    /**
     * 风格
     */
    private String space;
    /**
     * 空间
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

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public int getAreaIndex() {
        return areaIndex;
    }

    public void setAreaIndex(int areaIndex) {
        this.areaIndex = areaIndex;
    }

    public int getStyleIndex() {
        return styleIndex;
    }

    public void setStyleIndex(int styleIndex) {
        this.styleIndex = styleIndex;
    }

    public void setHousingType(String housingType) {
        this.housingType = housingType;
    }

    public String getHousingType() {
        return housingType;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyle() {
        return style;
    }

}
