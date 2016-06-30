package com.autodesk.shejijia.consumer.personalcenter.workflow.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author he.liu .
 * @version 1.0 .
 * @date 16-6-7
 * @file Wk3DPlanListBean.java  .
 * @brief 某一套3D方案的实体类.
 */
public class Wk3DPlanListBean implements Serializable {

    private String link;
    private String conception;
    private String hs_designer_uid;
    private String acs_project_id;
    private String community_name;
    private String custom_string_name;
    private String custom_string_bedroom;
    private String custom_string_form;
    private String design_asset_id;
    private String restroom;
    private String project_type;
    private String room_type;
    private String bedroom;
    private String city;
    private String custom_string_type;
    private String custom_string_style;
    private String hs_design_id;
    private String province;
    private String custom_string_restroom;
    private String custom_string_area;
    private String project_style;
    private String district;
    private String design_name;
    private String room_area;
    private List<DesignFileEntity> design_file;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getConception() {
        return conception;
    }

    public void setConception(String conception) {
        this.conception = conception;
    }

    public String getHs_designer_uid() {
        return hs_designer_uid;
    }

    public void setHs_designer_uid(String hs_designer_uid) {
        this.hs_designer_uid = hs_designer_uid;
    }

    public String getAcs_project_id() {
        return acs_project_id;
    }

    public void setAcs_project_id(String acs_project_id) {
        this.acs_project_id = acs_project_id;
    }

    public String getCommunity_name() {
        return community_name;
    }

    public void setCommunity_name(String community_name) {
        this.community_name = community_name;
    }

    public String getCustom_string_name() {
        return custom_string_name;
    }

    public void setCustom_string_name(String custom_string_name) {
        this.custom_string_name = custom_string_name;
    }

    public String getCustom_string_bedroom() {
        return custom_string_bedroom;
    }

    public void setCustom_string_bedroom(String custom_string_bedroom) {
        this.custom_string_bedroom = custom_string_bedroom;
    }

    public String getCustom_string_form() {
        return custom_string_form;
    }

    public void setCustom_string_form(String custom_string_form) {
        this.custom_string_form = custom_string_form;
    }

    public String getDesign_asset_id() {
        return design_asset_id;
    }

    public void setDesign_asset_id(String design_asset_id) {
        this.design_asset_id = design_asset_id;
    }

    public String getRestroom() {
        return restroom;
    }

    public void setRestroom(String restroom) {
        this.restroom = restroom;
    }

    public String getProject_type() {
        return project_type;
    }

    public void setProject_type(String project_type) {
        this.project_type = project_type;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getBedroom() {
        return bedroom;
    }

    public void setBedroom(String bedroom) {
        this.bedroom = bedroom;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCustom_string_type() {
        return custom_string_type;
    }

    public void setCustom_string_type(String custom_string_type) {
        this.custom_string_type = custom_string_type;
    }

    public String getCustom_string_style() {
        return custom_string_style;
    }

    public void setCustom_string_style(String custom_string_style) {
        this.custom_string_style = custom_string_style;
    }

    public String getHs_design_id() {
        return hs_design_id;
    }

    public void setHs_design_id(String hs_design_id) {
        this.hs_design_id = hs_design_id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCustom_string_restroom() {
        return custom_string_restroom;
    }

    public void setCustom_string_restroom(String custom_string_restroom) {
        this.custom_string_restroom = custom_string_restroom;
    }

    public String getCustom_string_area() {
        return custom_string_area;
    }

    public void setCustom_string_area(String custom_string_area) {
        this.custom_string_area = custom_string_area;
    }

    public String getProject_style() {
        return project_style;
    }

    public void setProject_style(String project_style) {
        this.project_style = project_style;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDesign_name() {
        return design_name;
    }

    public void setDesign_name(String design_name) {
        this.design_name = design_name;
    }

    public String getRoom_area() {
        return room_area;
    }

    public void setRoom_area(String room_area) {
        this.room_area = room_area;
    }

    public List<DesignFileEntity> getDesign_file() {
        return design_file;
    }

    public void setDesign_file(List<DesignFileEntity> design_file) {
        this.design_file = design_file;
    }

    public static class DesignFileEntity implements Serializable {
        private String id;
        private int source;
        private int status;
        private String source_id;
        private String extended_data;
        private String link;
        private String name;
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getSource_id() {
            return source_id;
        }

        public void setSource_id(String source_id) {
            this.source_id = source_id;
        }

        public String getExtended_data() {
            return extended_data;
        }

        public void setExtended_data(String extended_data) {
            this.extended_data = extended_data;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
