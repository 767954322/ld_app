package com.autodesk.shejijia.consumer.personalcenter.recommend.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xueqiudong on 16-10-27.
 */

public class SelectProjectEntity implements Serializable {

    /**
     * city_name : 沈阳市
     * community_name : 阳光小区
     * consumer_mobile : 18610662477
     * community_address : 沈阳市胡龙村113号楼
     * consumer_name : 许飞
     * consumer_id : 20735668
     * consumer_uid : 7ddb0714-a4da-4d96-a9a0-325d86d44b86
     * city : 210100
     * province_name : 辽宁省
     * design_project_id : 201415
     * district_name : 沈河区
     * province : 210000
     * district : 210103
     * consumer_zid : 147852
     */

    private List<DesignerProjectsBean> designerProjects;

    public List<DesignerProjectsBean> getDesignerProjects() {
        return designerProjects;
    }

    public void setDesignerProjects(List<DesignerProjectsBean> designerProjects) {
        this.designerProjects = designerProjects;
    }

    public static class DesignerProjectsBean implements Serializable{
        private String city_name;
        private String community_name;
        private String consumer_mobile;
        private String community_address;
        private String consumer_name;
        private String consumer_id;
        private String consumer_uid;
        private String city;
        private String province_name;
        private int design_project_id;
        private int main_project_id;
        private String district_name;
        private String province;
        private String district;
        private String consumer_zid;

        public int getMain_project_id() {
            return main_project_id;
        }

        public void setMain_project_id(int main_project_id) {
            this.main_project_id = main_project_id;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getCommunity_name() {
            return community_name;
        }

        public void setCommunity_name(String community_name) {
            this.community_name = community_name;
        }

        public String getConsumer_mobile() {
            return consumer_mobile;
        }

        public void setConsumer_mobile(String consumer_mobile) {
            this.consumer_mobile = consumer_mobile;
        }

        public String getCommunity_address() {
            return community_address;
        }

        public void setCommunity_address(String community_address) {
            this.community_address = community_address;
        }

        public String getConsumer_name() {
            return consumer_name;
        }

        public void setConsumer_name(String consumer_name) {
            this.consumer_name = consumer_name;
        }

        public String getConsumer_id() {
            return consumer_id;
        }

        public void setConsumer_id(String consumer_id) {
            this.consumer_id = consumer_id;
        }

        public String getConsumer_uid() {
            return consumer_uid;
        }

        public void setConsumer_uid(String consumer_uid) {
            this.consumer_uid = consumer_uid;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getProvince_name() {
            return province_name;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }

        public int getDesign_project_id() {
            return design_project_id;
        }

        public void setDesign_project_id(int design_project_id) {
            this.design_project_id = design_project_id;
        }

        public String getDistrict_name() {
            return district_name;
        }

        public void setDistrict_name(String district_name) {
            this.district_name = district_name;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getConsumer_zid() {
            return consumer_zid;
        }

        public void setConsumer_zid(String consumer_zid) {
            this.consumer_zid = consumer_zid;
        }
    }
}
