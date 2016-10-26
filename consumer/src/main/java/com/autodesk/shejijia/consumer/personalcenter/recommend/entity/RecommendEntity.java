package com.autodesk.shejijia.consumer.personalcenter.recommend.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @User: 蜡笔小新
 * @date: 16-10-24
 * @GitHub: https://github.com/meikoz
 */

public class RecommendEntity implements Serializable{

    private int total;
    private int offset;
    private int limit;
    private List<ItemsBean> items;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean implements Serializable{
        private String province;
        private String city;
        private String district;
        private String scfd;
        private Object scfc;
        private String status;
        private Object type;
        private String source;
        private int main_project_id;
        private int design_project_id;
        private int consumer_id;
        private int consumer_zid;
        private String consumer_uid;
        private String designer_id;
        private String designer_uid;
        private String consumer_name;
        private String consumer_mobile;
        private String province_name;
        private String city_name;
        private String district_name;
        private String community_name;
        private int effective_time;
        private int expiration_time;
        private int modified_count;
        private String community_address;
        private int asset_id;
        private String date_submitted;

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getScfd() {
            return scfd;
        }

        public void setScfd(String scfd) {
            this.scfd = scfd;
        }

        public Object getScfc() {
            return scfc;
        }

        public void setScfc(Object scfc) {
            this.scfc = scfc;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public int getMain_project_id() {
            return main_project_id;
        }

        public void setMain_project_id(int main_project_id) {
            this.main_project_id = main_project_id;
        }

        public int getDesign_project_id() {
            return design_project_id;
        }

        public void setDesign_project_id(int design_project_id) {
            this.design_project_id = design_project_id;
        }

        public int getConsumer_id() {
            return consumer_id;
        }

        public void setConsumer_id(int consumer_id) {
            this.consumer_id = consumer_id;
        }

        public int getConsumer_zid() {
            return consumer_zid;
        }

        public void setConsumer_zid(int consumer_zid) {
            this.consumer_zid = consumer_zid;
        }

        public String getConsumer_uid() {
            return consumer_uid;
        }

        public void setConsumer_uid(String consumer_uid) {
            this.consumer_uid = consumer_uid;
        }

        public String getDesigner_id() {
            return designer_id;
        }

        public void setDesigner_id(String designer_id) {
            this.designer_id = designer_id;
        }

        public String getDesigner_uid() {
            return designer_uid;
        }

        public void setDesigner_uid(String designer_uid) {
            this.designer_uid = designer_uid;
        }

        public String getConsumer_name() {
            return consumer_name;
        }

        public void setConsumer_name(String consumer_name) {
            this.consumer_name = consumer_name;
        }

        public String getConsumer_mobile() {
            return consumer_mobile;
        }

        public void setConsumer_mobile(String consumer_mobile) {
            this.consumer_mobile = consumer_mobile;
        }

        public String getProvince_name() {
            return province_name;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getDistrict_name() {
            return district_name;
        }

        public void setDistrict_name(String district_name) {
            this.district_name = district_name;
        }

        public String getCommunity_name() {
            return community_name;
        }

        public void setCommunity_name(String community_name) {
            this.community_name = community_name;
        }

        public int getEffective_time() {
            return effective_time;
        }

        public void setEffective_time(int effective_time) {
            this.effective_time = effective_time;
        }

        public int getExpiration_time() {
            return expiration_time;
        }

        public void setExpiration_time(int expiration_time) {
            this.expiration_time = expiration_time;
        }

        public int getModified_count() {
            return modified_count;
        }

        public void setModified_count(int modified_count) {
            this.modified_count = modified_count;
        }

        public String getCommunity_address() {
            return community_address;
        }

        public void setCommunity_address(String community_address) {
            this.community_address = community_address;
        }

        public int getAsset_id() {
            return asset_id;
        }

        public void setAsset_id(int asset_id) {
            this.asset_id = asset_id;
        }

        public String getDate_submitted() {
            return date_submitted;
        }

        public void setDate_submitted(String date_submitted) {
            this.date_submitted = date_submitted;
        }
    }
}
