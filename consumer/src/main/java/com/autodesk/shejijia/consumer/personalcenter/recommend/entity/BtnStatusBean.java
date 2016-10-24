package com.autodesk.shejijia.consumer.personalcenter.recommend.entity;

/**
 * Created by yaoxuehua on 16-10-21.
 */

public class BtnStatusBean {

    private int countOffset;//用于判断是第几个按钮
    private int singleClickOrDoubleBtnCount;//该按钮的状态

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
}
