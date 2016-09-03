package com.autodesk.shejijia.consumer.home.decorationdesigners.entity;

import java.io.Serializable;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-8-2 .
 * @file DesignerInfoBean.java .
 * @brief 设计师首页详情designer实体类 .
 */
public class DesignerInfoBean implements Serializable {
    private String studio;
    private String styles;
    private String introduction;
    private int experience;
    private String acs_member_id;
    private String style_names;
    private String style_long_names;
    private String measurement_price;
    private String design_price_min;
    private String design_price_max;
    private String graduate_from;
    private String personal_honour;
    private String diy_count;
    private int case_count;
    private String is_loho;
    private int is_real_name;
    private String theme_pic;
    private String type_code;
    private String type_name;
    private String store_id;
    private String store_name;
    private String company_id;
    private String company_name;

    /**
     * 筛选新增字段
     */
    private String design_price_code;
    private String evalution_avg_scores;
    private String evalution_count;

    public String getDesign_price_code() {
        return design_price_code;
    }

    public void setDesign_price_code(String design_price_code) {
        this.design_price_code = design_price_code;
    }

    public String getEvalution_avg_scores() {
        return evalution_avg_scores;
    }

    public void setEvalution_avg_scores(String evalution_avg_scores) {
        this.evalution_avg_scores = evalution_avg_scores;
    }

    public String getEvalution_count() {
        return evalution_count;
    }

    public void setEvalution_count(String evalution_count) {
        this.evalution_count = evalution_count;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getStyles() {
        return styles;
    }

    public void setStyles(String styles) {
        this.styles = styles;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getAcs_member_id() {
        return acs_member_id;
    }

    public void setAcs_member_id(String acs_member_id) {
        this.acs_member_id = acs_member_id;
    }

    public String getStyle_names() {
        return style_names;
    }

    public void setStyle_names(String style_names) {
        this.style_names = style_names;
    }

    public String getStyle_long_names() {
        return style_long_names;
    }

    public void setStyle_long_names(String style_long_names) {
        this.style_long_names = style_long_names;
    }

    public String getMeasurement_price() {
        return measurement_price;
    }

    public void setMeasurement_price(String measurement_price) {
        this.measurement_price = measurement_price;
    }

    public String getDesign_price_min() {
        return design_price_min;
    }

    public void setDesign_price_min(String design_price_min) {
        this.design_price_min = design_price_min;
    }

    public String getDesign_price_max() {
        return design_price_max;
    }

    public void setDesign_price_max(String design_price_max) {
        this.design_price_max = design_price_max;
    }

    public String getGraduate_from() {
        return graduate_from;
    }

    public void setGraduate_from(String graduate_from) {
        this.graduate_from = graduate_from;
    }

    public String getPersonal_honour() {
        return personal_honour;
    }

    public void setPersonal_honour(String personal_honour) {
        this.personal_honour = personal_honour;
    }

    public String getDiy_count() {
        return diy_count;
    }

    public void setDiy_count(String diy_count) {
        this.diy_count = diy_count;
    }

    public int getCase_count() {
        return case_count;
    }

    public void setCase_count(int case_count) {
        this.case_count = case_count;
    }

    public String getIs_loho() {
        return is_loho;
    }

    public void setIs_loho(String is_loho) {
        this.is_loho = is_loho;
    }

    public int getIs_real_name() {
        return is_real_name;
    }

    public void setIs_real_name(int is_real_name) {
        this.is_real_name = is_real_name;
    }

    public String getTheme_pic() {
        return theme_pic;
    }

    public void setTheme_pic(String theme_pic) {
        this.theme_pic = theme_pic;
    }

    public String getType_code() {
        return type_code;
    }

    public void setType_code(String type_code) {
        this.type_code = type_code;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }
}
