package com.autodesk.shejijia.consumer.base.bean;

/**
 * @author   DongXueQiu .
 * @version  v1.0 .
 * @date       2016-6-12 .
 * @file          LivingRoomBean1.java .
 * @brief        .
 */
public class RoomBean {

    /**
     "one": "一室",
     "two": "两室",
     "three": "三室",
     "four": "四室",
     "five": "五室",
     "loft": "LOFT",
     "multiple": "复式",
     "villa": "别墅",
     "other": "其他"
     * other: "其他",
     */

    private String one;
    private String two;
    private String three;
    private String four;
    private String five;
    private String loft;
    private String multiple;
    private String villa;
    private String other;

    public void setOther(String other) {this.other = other;}
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

    public void setLoft(String loft) {
        this.loft = loft;
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple;
    }

    public void setVilla(String villa) {
        this.villa = villa;
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

    public String getLoft() {
        return loft;
    }

    public String getMultiple() {
        return multiple;
    }

    public String getVilla() {
        return villa;
    }

    public String getOther() {
        return other;
    }

}
