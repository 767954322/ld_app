package com.autodesk.shejijia.consumer.base.bean;

import java.io.Serializable;

/**
 * @author    DongXueQiu .
 * @version  v1.0 .
 * @date       2016-6-12 .
 * @file          StyleBean.java .
 * @brief         .
 */
public class StyleBean implements Serializable {

    /**
     * Japan : 日式
     * korea : 韩式
     * Mashup : 混搭
     * european : 欧式
     * chinese : 中式
     * newClassical : 新古典
     * ASAN : 东南亚
     * US : 美式
     * countryside : 田园
     * modern : 现代
     * mediterranean : 地中海
     * other : 其他
     */

    private String Japan;
    private String korea;
    private String Mashup;
    private String european;
    private String chinese;
    private String neoclassical;
    private String ASAN;
    private String US;

    public String getNeoclassical() {
        return neoclassical;
    }

    public void setNeoclassical(String neoclassical) {
        this.neoclassical = neoclassical;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    private String country;
    private String modern;
    private String mediterranean;
    private String other;

    public void setJapan(String Japan) {
        this.Japan = Japan;
    }

    public void setKorea(String korea) {
        this.korea = korea;
    }

    public void setMashup(String Mashup) {
        this.Mashup = Mashup;
    }

    public void setEuropean(String european) {
        this.european = european;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }


    public void setASAN(String ASAN) {
        this.ASAN = ASAN;
    }

    public void setUS(String US) {
        this.US = US;
    }


    public void setModern(String modern) {
        this.modern = modern;
    }

    public void setMediterranean(String mediterranean) {
        this.mediterranean = mediterranean;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getJapan() {
        return Japan;
    }

    public String getKorea() {
        return korea;
    }

    public String getMashup() {
        return Mashup;
    }

    public String getEuropean() {
        return european;
    }

    public String getChinese() {
        return chinese;
    }


    public String getASAN() {
        return ASAN;
    }

    public String getUS() {
        return US;
    }


    public String getModern() {
        return modern;
    }

    public String getMediterranean() {
        return mediterranean;
    }

    public String getOther() {
        return other;
    }
}
