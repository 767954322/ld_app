package com.autodesk.shejijia.consumer.codecorationBase.grandmaster.entity;

import java.io.Serializable;

/**
 * Created by allengu on 16-8-29.
 */
public class DesignerDetail implements Serializable {

    private String occupational_en;
    private String occupational_cn;
    private String introduction;
    private String acs_member_id;
    private DetailCover designer_detail_cover;
    private DetailCoverApp designer_detail_cover_app;

    public DesignerDetail() {
    }

    public DesignerDetail(String occupational_en, String occupational_cn, String introduction, String acs_member_id, DetailCover designer_detail_cover, DetailCoverApp designer_detail_cover_app) {
        this.occupational_en = occupational_en;
        this.occupational_cn = occupational_cn;
        this.introduction = introduction;
        this.acs_member_id = acs_member_id;
        this.designer_detail_cover = designer_detail_cover;
        this.designer_detail_cover_app = designer_detail_cover_app;
    }

    public String getAcs_member_id() {
        return acs_member_id;
    }

    public void setAcs_member_id(String acs_member_id) {
        this.acs_member_id = acs_member_id;
    }

    public String getOccupational_en() {
        return occupational_en;
    }

    public void setOccupational_en(String occupational_en) {
        this.occupational_en = occupational_en;
    }

    public String getOccupational_cn() {
        return occupational_cn;
    }

    public void setOccupational_cn(String occupational_cn) {
        this.occupational_cn = occupational_cn;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public DetailCover getDesigner_detail_cover() {
        return designer_detail_cover;
    }

    public void setDesigner_detail_cover(DetailCover designer_detail_cover) {
        this.designer_detail_cover = designer_detail_cover;
    }

    public DetailCoverApp getDesigner_detail_cover_app() {
        return designer_detail_cover_app;
    }

    public void setDesigner_detail_cover_app(DetailCoverApp designer_detail_cover_app) {
        this.designer_detail_cover_app = designer_detail_cover_app;
    }

    @Override
    public String toString() {
        return "DesignerDetail{" +
                "occupational_en='" + occupational_en + '\'' +
                ", occupational_cn='" + occupational_cn + '\'' +
                ", introduction='" + introduction + '\'' +
                ", acs_member_id='" + acs_member_id + '\'' +
                ", designer_detail_cover=" + designer_detail_cover +
                ", designer_detail_cover_app=" + designer_detail_cover_app +
                '}';
    }
}
