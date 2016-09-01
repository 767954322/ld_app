package com.autodesk.shejijia.consumer.codecorationBase.studio.entity;

import java.io.Serializable;


public class Designer implements Serializable {


    /**
     * acs的标识
     */
    private String acs_member_id;

    /**
     * 工作室
     */
    private String studio;

    /**
     * 擅长的风格
     */
    private String styles;

    private String style_names;

    private String style_long_names;

    /**
     * 自我介绍
     */
    private String introduction;

    /**
     * 工作经验
     */
    private String experience;

    /**
     * 量房费用
     */
    private String measurement_price;

    /**
     * 设计费用最小值
     */
    private String design_price_min;

    /**
     * 设计费用最大值
     */
    private String design_price_max;

    /**
     * 毕业院校
     */
    private String graduate_from;

    /**
     * 个人荣誉
     */
    private String personal_honour;

    /**
     * 3D DIY案例总数
     */
    private String diy_count;

    /**
     * 3D案例总数
     */
    private String case_count;

    /**
     * 设计师类型:0:社会化设计师,1:快屋设计师,2:乐屋设计师,3:元洲设计师,4:顶层设计师
     */
    private String is_loho;


    private String is_real_name;

    private String theme_pic;

    /**
     * 类型编码
     */
    private String type_code;

    /**
     * 类型名称
     */
    private String type_name;

    /**
     * 店面编码
     */
    private String store_id;

    /**
     * 店面名称
     */
    private String store_name;

    /**
     * 装修公司编码
     */
    private String company_id;

    /**
     * 装修公司名称
     */
    private String company_name;

    /**
     * @Fields designPriceCode 设计师设计费code
     */
    private String design_price_code;

    /**
     * @Fields evalutionAvgScores 综合评价分数
     */
    private String evalution_avg_scores;

    /**
     * @Fields evalutionCount 评价分数的次数
     */
    private String evalution_count;

    public String getAcs_member_id() {
        return acs_member_id;
    }

    public void setAcs_member_id(String acs_member_id) {
        this.acs_member_id = acs_member_id;
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
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

    public String getCase_count() {
        return case_count;
    }

    public void setCase_count(String case_count) {
        this.case_count = case_count;
    }

    public String getIs_loho() {
        return is_loho;
    }

    public void setIs_loho(String is_loho) {
        this.is_loho = is_loho;
    }

    public String getIs_real_name() {
        return is_real_name;
    }

    public void setIs_real_name(String is_real_name) {
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
}
