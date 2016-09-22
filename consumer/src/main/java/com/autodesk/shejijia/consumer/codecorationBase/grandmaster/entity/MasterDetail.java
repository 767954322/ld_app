package com.autodesk.shejijia.consumer.codecorationBase.grandmaster.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by allengu on 16-8-29.
 */
public class MasterDetail implements Serializable {


    private String hs_uid;
    private String nick_name;
    private String english_name;
    private List<DatailCase> cases_list;
    private DesignerDetail designer;

    public MasterDetail() {
    }

    public MasterDetail(String hs_uid, String nick_name, String english_name, List<DatailCase> cases_list, DesignerDetail designer) {
        this.hs_uid = hs_uid;
        this.nick_name = nick_name;
        this.english_name = english_name;
        this.cases_list = cases_list;
        this.designer = designer;
    }

    public String getHs_uid() {
        return hs_uid;
    }

    public void setHs_uid(String hs_uid) {
        this.hs_uid = hs_uid;
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

    public List<DatailCase> getCases_list() {
        return cases_list;
    }

    public void setCases_list(List<DatailCase> cases_list) {
        this.cases_list = cases_list;
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
                "hs_uid='" + hs_uid + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", english_name='" + english_name + '\'' +
                ", cases_list=" + cases_list +
                ", designer=" + designer +
                '}';
    }
}
