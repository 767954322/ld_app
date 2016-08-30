package com.autodesk.shejijia.consumer.codecorationBase.grandmaster.entity;

import java.io.Serializable;

/**
 * Created by allengu on 16-8-26.
 */
public class MasterInfo implements Serializable {


    private Designer designer;
    private String english_name;//大师英文名称
    private String nick_name;//大师中文名称
    private String hs_uid;//大师中文名称


    public MasterInfo() {
    }

    public MasterInfo(Designer designer, String english_name, String nick_name, String hs_uid) {
        this.designer = designer;
        this.english_name = english_name;
        this.nick_name = nick_name;
        this.hs_uid = hs_uid;
    }

    public String getHs_uid() {
        return hs_uid;
    }

    public void setHs_uid(String hs_uid) {
        this.hs_uid = hs_uid;
    }

    public Designer getDesigner() {
        return designer;
    }

    public void setDesigner(Designer designer) {
        this.designer = designer;
    }

    public String getEnglish_name() {
        return english_name;
    }

    public void setEnglish_name(String english_name) {
        this.english_name = english_name;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    @Override
    public String toString() {
        return "MasterInfo{" +
                "designer=" + designer +
                ", english_name='" + english_name + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", hs_uid='" + hs_uid + '\'' +
                '}';
    }
}
