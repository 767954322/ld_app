package com.autodesk.shejijia.consumer.codecorationBase.studio.entity;

import java.io.Serializable;


public class MainDesigners implements Serializable {
    /**
     *
     */
    private String tag_name;
    private String avatar;

    public MainDesigners(String tagName, String avatar) {
        super();
        this.tag_name = tagName;
        this.avatar = avatar;
    }

    public MainDesigners() {
        super();
        // TODO Auto-generated constructor stub
    }



    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }



}
