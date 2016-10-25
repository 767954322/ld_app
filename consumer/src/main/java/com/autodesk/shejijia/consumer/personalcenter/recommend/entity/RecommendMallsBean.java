package com.autodesk.shejijia.consumer.personalcenter.recommend.entity;

/**
 * {门店列表:[{门店编码:11;门店名称:'丽泽店'},{门店编码:11;门店名称:'丽泽店'}]}
 *
 * @author liuhea
 *         created at 16-10-25
 */
public class RecommendMallsBean {
    private String mall_number;
    private String mall_name;

    public String getMall_number() {
        return mall_number;
    }

    public void setMall_number(String mall_number) {
        this.mall_number = mall_number;
    }

    public String getMall_name() {
        return mall_name;
    }

    public void setMall_name(String mall_name) {
        this.mall_name = mall_name;
    }
}
