package com.autodesk.shejijia.consumer.base.bean;

import java.io.Serializable;

/**
 * @author   he.liu .
 * @version  v1.0 .
 * @date       2016-6-12 .
 * @file          AreaBean.java .
 * @brief       DongXueQiu .
 */
public class AreaBean implements Serializable {

    /**
     * one : 60㎡以下
     * two : 60-80㎡
     * three : 80-120㎡
     * five : 120㎡以上
     */

    private String one;
    private String two;
    private String three;
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

    public String getFive() {
        return five;
    }

    @Override
    public String toString() {
        return "AreaBean{" +
                "one='" + one + '\'' +
                ", two='" + two + '\'' +
                ", three='" + three + '\'' +
                ", five='" + five + '\'' +
                '}';
    }
}
