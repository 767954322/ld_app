package com.autodesk.shejijia.consumer.personalcenter.recommend.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 推荐清单草稿实体类( recommend categories for designer)
 *
 * @author liuhea
 *         created at 16-10-25
 */
public class RecommendSCFDBean implements Serializable {

    private String category_3d_id;
    private String category_3d_name;
    private String category_o2o_id;
    private String category_o2o_name;
    private String category_online_id;
    private String category_online_name;
    private String sub_category_3d_id;
    private String sub_category_3d_name;
    private String sub_category_o2o_id;
    private String sub_category_o2o_name;
    private String sub_category_online_id;
    private String sub_category_online_name;
    private List<RecommendBrandsBean> brands; // 推荐品牌．
    private String source;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCategory_3d_id() {
        return category_3d_id;
    }

    public void setCategory_3d_id(String category_3d_id) {
        this.category_3d_id = category_3d_id;
    }

    public String getCategory_3d_name() {
        return category_3d_name;
    }

    public void setCategory_3d_name(String category_3d_name) {
        this.category_3d_name = category_3d_name;
    }

    public String getCategory_o2o_id() {
        return category_o2o_id;
    }

    public void setCategory_o2o_id(String category_o2o_id) {
        this.category_o2o_id = category_o2o_id;
    }

    public String getCategory_o2o_name() {
        return category_o2o_name;
    }

    public void setCategory_o2o_name(String category_o2o_name) {
        this.category_o2o_name = category_o2o_name;
    }

    public String getCategory_online_id() {
        return category_online_id;
    }

    public void setCategory_online_id(String category_online_id) {
        this.category_online_id = category_online_id;
    }

    public String getCategory_online_name() {
        return category_online_name;
    }

    public void setCategory_online_name(String category_online_name) {
        this.category_online_name = category_online_name;
    }

    public String getSub_category_3d_id() {
        return sub_category_3d_id;
    }

    public void setSub_category_3d_id(String sub_category_3d_id) {
        this.sub_category_3d_id = sub_category_3d_id;
    }

    public String getSub_category_3d_name() {
        return sub_category_3d_name;
    }

    public void setSub_category_3d_name(String sub_category_3d_name) {
        this.sub_category_3d_name = sub_category_3d_name;
    }

    public String getSub_category_o2o_id() {
        return sub_category_o2o_id;
    }

    public void setSub_category_o2o_id(String sub_category_o2o_id) {
        this.sub_category_o2o_id = sub_category_o2o_id;
    }

    public String getSub_category_o2o_name() {
        return sub_category_o2o_name;
    }

    public void setSub_category_o2o_name(String sub_category_o2o_name) {
        this.sub_category_o2o_name = sub_category_o2o_name;
    }

    public String getSub_category_online_id() {
        return sub_category_online_id;
    }

    public void setSub_category_online_id(String sub_category_online_id) {
        this.sub_category_online_id = sub_category_online_id;
    }

    public String getSub_category_online_name() {
        return sub_category_online_name;
    }

    public void setSub_category_online_name(String sub_category_online_name) {
        this.sub_category_online_name = sub_category_online_name;
    }

    public List<RecommendBrandsBean> getBrands() {
        return brands;
    }

    public void setBrands(List<RecommendBrandsBean> brands) {
        this.brands = brands;
    }
}
