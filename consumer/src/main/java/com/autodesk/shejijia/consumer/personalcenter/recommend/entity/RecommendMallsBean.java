package com.autodesk.shejijia.consumer.personalcenter.recommend.entity;

import java.io.Serializable;

/**
 * {门店列表:[{门店编码:11;门店名称:'丽泽店'},{门店编码:11;门店名称:'丽泽店'}]}
 *
 * @author liuhea
 *         created at 16-10-25
 */
public class RecommendMallsBean implements Serializable {
    private String mall_number;
    private String mall_name;
    private Object booth_number;
    private Object booth_name;

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

    @Override
    public String toString() {
        return "{" +
                "\"mall_number\":\"" + mall_number + '"' +
                ", \"mall_name\":\"" + mall_name + '"' +
                ", \"booth_number\":" + booth_number +
                ", \"booth_name\":" + booth_name +
                '}';
    }
}
