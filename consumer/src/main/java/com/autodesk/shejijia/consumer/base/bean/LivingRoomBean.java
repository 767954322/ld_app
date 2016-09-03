package com.autodesk.shejijia.consumer.base.bean;

import java.io.Serializable;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @file Room2  .
 * @brief Room .
 */
public class LivingRoomBean implements Serializable {

    /**
     * one : 一厅
     * two : 两厅
     * three : 三厅
     * four : 四厅
     * five : 五厅
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
