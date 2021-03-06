package com.autodesk.shejijia.consumer.codecorationBase.grandmaster.entity;

import java.io.Serializable;

/**
 * Created by allengu on 16-8-26.
 */
public class Designer implements Serializable {

    private String introduction;//大师详情描述
    private String type_name;//社会化设计师
    private String type_code;//社会化设计师
    private String acs_member_id;//大师acsid
    private ProfileCoverApp designer_profile_cover_app;//大师列表图

    public Designer() {
    }

    public Designer(String introduction, String type_name, String type_code, String acs_member_id, ProfileCoverApp designer_profile_cover_app) {
        this.introduction = introduction;
        this.type_name = type_name;
        this.type_code = type_code;
        this.acs_member_id = acs_member_id;
        this.designer_profile_cover_app = designer_profile_cover_app;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getType_code() {
        return type_code;
    }

    public void setType_code(String type_code) {
        this.type_code = type_code;
    }

    public String getAcs_member_id() {
        return acs_member_id;
    }

    public void setAcs_member_id(String acs_member_id) {
        this.acs_member_id = acs_member_id;
    }

    public ProfileCoverApp getDesigner_profile_cover_app() {
        return designer_profile_cover_app;
    }

    public void setDesigner_profile_cover_app(ProfileCoverApp designer_profile_cover_app) {
        this.designer_profile_cover_app = designer_profile_cover_app;
    }

    @Override
    public String toString() {
        return "Designer{" +
                "introduction='" + introduction + '\'' +
                ", type_name='" + type_name + '\'' +
                ", type_code='" + type_code + '\'' +
                ", acs_member_id='" + acs_member_id + '\'' +
                ", designer_profile_cover_app=" + designer_profile_cover_app +
                '}';
    }
}
