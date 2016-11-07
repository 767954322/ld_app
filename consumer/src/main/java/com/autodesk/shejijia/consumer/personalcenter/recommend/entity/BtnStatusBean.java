package com.autodesk.shejijia.consumer.personalcenter.recommend.entity;

import java.io.Serializable;

/**
 * Created by yaoxuehua on 16-10-21.
 */

public class BtnStatusBean implements Serializable {

    private int countOffset;//用于判断是第几个按钮
    private int singleClickOrDoubleBtnCount;//该按钮的状态
    private String oneTag;//一级品类的标志
    private String twoTag;//二级品类的标志

    public int getCountOffset() {
        return countOffset;
    }

    public void setCountOffset(int countOffset) {
        this.countOffset = countOffset;
    }

    public int getSingleClickOrDoubleBtnCount() {
        return singleClickOrDoubleBtnCount;
    }

    public void setSingleClickOrDoubleBtnCount(int singleClickOrDoubleBtnCount) {
        this.singleClickOrDoubleBtnCount = singleClickOrDoubleBtnCount;
    }

    public String getOneTag() {
        return oneTag;
    }

    public void setOneTag(String oneTag) {
        this.oneTag = oneTag;
    }

    public String getTwoTag() {
        return twoTag;
    }

    public void setTwoTag(String twoTag) {
        this.twoTag = twoTag;
    }
}
