package com.autodesk.shejijia.consumer.personalcenter.recommend.entity;

import java.util.List;

/**
 * 推荐清单草稿实体类( recommend categories for designer)
 *
 * @author liuhea
 *         created at 16-10-25
 */
public class RecommendSCFDBean {

    private String sub_category_3d_id;
    private String category_3d_name;
    private String source;
    private String sub_category_3d_name;
    private String category_3d_id;
    private List<RecommendBrandsBean> brands; // 推荐品牌．

    public String getSub_category_3d_id() {
        return sub_category_3d_id;
    }

    public void setSub_category_3d_id(String sub_category_3d_id) {
        this.sub_category_3d_id = sub_category_3d_id;
    }

    public String getCategory_3d_name() {
        return category_3d_name;
    }

    public void setCategory_3d_name(String category_3d_name) {
        this.category_3d_name = category_3d_name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSub_category_3d_name() {
        return sub_category_3d_name;
    }

    public void setSub_category_3d_name(String sub_category_3d_name) {
        this.sub_category_3d_name = sub_category_3d_name;
    }

    public String getCategory_3d_id() {
        return category_3d_id;
    }

    public void setCategory_3d_id(String category_3d_id) {
        this.category_3d_id = category_3d_id;
    }

    public List<RecommendBrandsBean> getBrands() {
        return brands;
    }

    public void setBrands(List<RecommendBrandsBean> brands) {
        this.brands = brands;
    }
}
