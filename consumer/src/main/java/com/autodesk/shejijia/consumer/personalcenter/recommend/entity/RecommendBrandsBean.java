package com.autodesk.shejijia.consumer.personalcenter.recommend.entity;

import java.io.Serializable;
import java.util.List;


/**
 * 清单编辑页推荐品牌
 *
 * @author liuhea
 *         created at 16-10-25
 */
public class RecommendBrandsBean implements Serializable {


    private String brand_name;      // 品牌名称．
    private String name;
    private String dimension;       // 规格．o
    private String remarks;         // 备注（最大不超过150个中文字符）．
    private String code;
    private String source;          // 来源, 是否来自3d  0：空白方案 1：3d．
    private String apartment;       // 空间．
    private String logo_url;
    private String amountAndUnit;   // 数量（单位）．
    private List<RecommendMallsBean> malls;
    private List<?> commoditys;      // sku列表．

    //    public String getName() {
//        return name;
//    }
//
    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
//
//    public void setBrand_name(String brand_name) {
//        this.brand_name = brand_name;
//    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getAmountAndUnit() {
        return amountAndUnit;
    }

    public void setAmountAndUnit(String amountAndUnit) {
        this.amountAndUnit = amountAndUnit;
    }

    public List<RecommendMallsBean> getMalls() {
        return malls;
    }

    public void setMalls(List<RecommendMallsBean> malls) {
        this.malls = malls;
    }

    public List<?> getCommoditys() {
        return commoditys;
    }

    public void setCommoditys(List<?> commoditys) {
        this.commoditys = commoditys;
    }
}
