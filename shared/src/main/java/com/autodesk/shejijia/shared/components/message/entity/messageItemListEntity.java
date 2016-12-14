package com.autodesk.shejijia.shared.components.message.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
/**
 * Created by luchongbin on 2016/12/9.
 */
public  class messageItemListEntity implements Serializable {
    @SerializedName("display_message")
    private DisplayMessageEntity displayMessage;
    @SerializedName("custom_data")
    private CustomDataEntity customDataBean;
    @SerializedName("sender_role")
    private String senderRole;
    @SerializedName("sender_avatar")
    private String senderAvatar;
    @SerializedName("sent_time")
    private String sentTime;

    public DisplayMessageEntity getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(DisplayMessageEntity displayMessage) {
        this.displayMessage = displayMessage;
    }

    public String getSentTime() {
        return sentTime;
    }

    public void setSentTime(String sentTime) {
        this.sentTime = sentTime;
    }

    public String getSenderAvatar() {
        return senderAvatar;
    }

    public void setSenderAvatar(String senderAvatar) {
        this.senderAvatar = senderAvatar;
    }

    public String getSenderRole() {
        return senderRole;
    }

    public void setSenderRole(String senderRole) {
        this.senderRole = senderRole;
    }

    public CustomDataEntity getCustomDataBean() {
        return customDataBean;
    }

    public void setCustomDataBean(CustomDataEntity customDataBean) {
        this.customDataBean = customDataBean;
    }
}