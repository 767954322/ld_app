package com.autodesk.shejijia.consumer.base.bean;

import java.io.Serializable;

/**
 * @author   DongXueQiu .
 * @version  v1.0 .
 * @date       2016-6-12 .
 * @file          ToiletBean.java .
 * @brief        .
 */
public class ToiletBean implements Serializable {

    /**
     * one : 一卫
     * two : 两卫
     * three : 三卫
     * four : 四卫
     * five : 五卫
     */

    private String one;
    private String two;
    private String three;
    private String four;
    private String five;

    public void setOne(String one) {
        this.one = one;
    }

    public void setTwo(String two) {
        this.two = two;
    }

    public void setThree(String three) {
        this.three = three;
    }

    public void setFour(String four) {
        this.four = four;
    }

    public void setFive(String five) {
        this.five = five;
    }

    public String getOne() {
        return one;
    }

    public String getTwo() {
        return two;
    }

    public String getThree() {
        return three;
    }

    public String getFour() {
        return four;
    }

    public String getFive() {
        return five;
    }
}
