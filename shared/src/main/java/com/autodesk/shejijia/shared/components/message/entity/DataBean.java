package com.autodesk.shejijia.shared.components.message.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by luchongbin on 2016/12/9.
 */
public  class DataBean implements Serializable {
    private DisplayMessageBean display_message;
    private CustomDataBean custom_data;
    private String sender_role;
    private String sender_avatar;
    private String sent_time;
    public DisplayMessageBean getDisplay_message() {
        return display_message;
    }

    public void setDisplay_message(DisplayMessageBean display_message) {
        this.display_message = display_message;
    }

    public CustomDataBean getCustom_data() {
        return custom_data;
    }

    public void setCustom_data(CustomDataBean custom_data) {
        this.custom_data = custom_data;
    }

    public String getSender_role() {
        return sender_role;
    }

    public void setSender_role(String sender_role) {
        this.sender_role = sender_role;
    }

    public String getSender_avatar() {
        return sender_avatar;
    }

    public void setSender_avatar(String sender_avatar) {
        this.sender_avatar = sender_avatar;
    }

    public String getSent_time() {
        return sent_time;
    }

    public void setSent_time(String sent_time) {
        this.sent_time = sent_time;
    }

}