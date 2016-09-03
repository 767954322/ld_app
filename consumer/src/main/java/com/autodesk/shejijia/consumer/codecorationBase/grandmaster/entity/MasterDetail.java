package com.autodesk.shejijia.consumer.codecorationBase.grandmaster.entity;

import java.io.Serializable;

/**
 * Created by allengu on 16-8-29.
 */
public class MasterDetail implements Serializable {


    private String nick_name;
    private String english_name;
    private DesignerDetail designer;

    public MasterDetail(String nick_name, String english_name, DesignerDetail designer) {
        this.nick_name = nick_name;
        this.english_name = english_name;
        this.designer = designer;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getEnglish_name() {
        return english_name;
    }

    public void setEnglish_name(String english_name) {
        this.english_name = english_name;
    }

    public DesignerDetail getDesigner() {
        return designer;
    }

    public void setDesigner(DesignerDetail designer) {
        this.designer = designer;
    }

    @Override
    public String toString() {
        return "MasterDetail{" +
                "nick_name='" + nick_name + '\'' +
                ", english_name='" + english_name + '\'' +
                ", designer=" + designer +
                '}';
    }
}
