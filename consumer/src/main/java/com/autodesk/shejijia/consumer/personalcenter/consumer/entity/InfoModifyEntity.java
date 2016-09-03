package com.autodesk.shejijia.consumer.personalcenter.consumer.entity;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 16-6-7 上午11:20
 * @file InfoModifyEntity.java  .
 * @brief 修改个人中心信息回传实体类.
 */
public class InfoModifyEntity {
    private String pTag;
    private String mMsg;

    public String getpTag() {
        return pTag;
    }

    public void setpTag(String pTag) {
        this.pTag = pTag;
    }

    public String getmMsg() {
        return mMsg;
    }

    public void setmMsg(String mMsg) {
        this.mMsg = mMsg;
    }

    public InfoModifyEntity(String pTag, String mMsg) {
        this.pTag = pTag;
        this.mMsg = mMsg;
    }
}
