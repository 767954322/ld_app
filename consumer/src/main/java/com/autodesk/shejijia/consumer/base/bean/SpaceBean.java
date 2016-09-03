package com.autodesk.shejijia.consumer.base.bean;

import java.io.Serializable;

/**
 * @author   DongXueQiu .
 * @version  v1.0 .
 * @date       2016-6-12 .
 * @file          SpaceBean.java .
 * @brief       .
 */
public class SpaceBean implements Serializable {

    /**
     * house : 住宅空间
     * catering : 餐饮空间
     * office : 办公空间
     * hotel : 酒店空间
     * business : 商业展示
     * entertainment : 娱乐空间
     * leisure : 休闲场所
     * culture : 文化空间
     * healthcare :  医疗机构
     * sale :  售楼中心
     * finace : 金融场所
     * sport :  运动场所
     * education : 教育机构
     */

    private String house;
    private String catering;
    private String office;
    private String hotel;
    private String business;
    private String entertainment;
    private String leisure;
    private String culture;
    private String healthcare;
    private String sale;
    private String finace;
    private String sport;
    private String education;
    private String other;

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public void setCatering(String catering) {
        this.catering = catering;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public void setEntertainment(String entertainment) {
        this.entertainment = entertainment;
    }

    public void setLeisure(String leisure) {
        this.leisure = leisure;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public void setHealthcare(String healthcare) {
        this.healthcare = healthcare;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public void setFinace(String finace) {
        this.finace = finace;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getHouse() {
        return house;
    }

    public String getCatering() {
        return catering;
    }

    public String getOffice() {
        return office;
    }

    public String getHotel() {
        return hotel;
    }

    public String getBusiness() {
        return business;
    }

    public String getEntertainment() {
        return entertainment;
    }

    public String getLeisure() {
        return leisure;
    }

    public String getCulture() {
        return culture;
    }

    public String getHealthcare() {
        return healthcare;
    }

    public String getSale() {
        return sale;
    }

    public String getFinace() {
        return finace;
    }

    public String getSport() {
        return sport;
    }

    public String getEducation() {
        return education;
    }

    @Override
    public String toString() {
        return "SpaceBean{" +
                "house='" + house + '\'' +
                ", catering='" + catering + '\'' +
                ", office='" + office + '\'' +
                ", hotel='" + hotel + '\'' +
                ", business='" + business + '\'' +
                ", entertainment='" + entertainment + '\'' +
                ", leisure='" + leisure + '\'' +
                ", culture='" + culture + '\'' +
                ", healthcare='" + healthcare + '\'' +
                ", sale='" + sale + '\'' +
                ", finace='" + finace + '\'' +
                ", sport='" + sport + '\'' +
                ", education='" + education + '\'' +
                '}';
    }
}
