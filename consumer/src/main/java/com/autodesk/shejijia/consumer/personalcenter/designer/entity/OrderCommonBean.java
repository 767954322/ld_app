package com.autodesk.shejijia.consumer.personalcenter.designer.entity;

import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPMeasureFormBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by he.liu  on 16-9-3.
 * <p/>
 * v2接口的设计师家订单的实体类
 */
public class OrderCommonBean implements Serializable {


    private String count;
    private String date;
    private int limit;
    private int offset;
    private Object _link;

    private List<OrderListBean> order_list;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public Object get_link() {
        return _link;
    }

    public void set_link(Object _link) {
        this._link = _link;
    }

    public List<OrderListBean> getOrder_list() {
        return order_list;
    }

    public void setOrder_list(List<OrderListBean> order_list) {
        this.order_list = order_list;
    }

    public static class OrderListBean implements Serializable {
        private String auditer;
        private String city;
        private String district;
        private String province;
        private String room;
        private String toilet;
        private String avatar;
        private String type;
        private String address;
        private String credentials;
        private String elite;
        private String pkg;
        private String after_bidding_status;
        private String audit_desc;
        private String consumer_uid;
        private String beishu_thread_id;
        private String bidder_count;
        private boolean bidding_status;
        private String city_name;
        private String click_number;
        private String community_name;
        private String consumer_mobile;
        private String consumer_name;
        private String contacts_mobile;
        private String contacts_name;
        private String custom_string_status;
        private String decoration_budget;
        private String decoration_style;
        private String design_budget;
        private String detail_desc;
        private String district_name;
        private String end_day;
        private String house_area;
        private String house_type;
        private String is_beishu;
        private String is_public;
        private String living_room;
        private String needs_id;
        private String province_name;
        private String publish_time;
        private String wk_template_id;
        private String project_status;
        private String design_style;
        private String main_demand;
        private String project_type;
        private String project_start_time;
        private String customer_id;
        private String hs_uid;
        private String member_id;
        private String designer_name;
        private String pkg_name;
        private String custom_string_pkg_type;

        private List<MPMeasureFormBean.BiddersBean> bidders;

        public String getAuditer() {
            return auditer;
        }

        public void setAuditer(String auditer) {
            this.auditer = auditer;
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

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        public String getToilet() {
            return toilet;
        }

        public void setToilet(String toilet) {
            this.toilet = toilet;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCredentials() {
            return credentials;
        }

        public void setCredentials(String credentials) {
            this.credentials = credentials;
        }

        public String getElite() {
            return elite;
        }

        public void setElite(String elite) {
            this.elite = elite;
        }

        public String getPkg() {
            return pkg;
        }

        public void setPkg(String pkg) {
            this.pkg = pkg;
        }

        public String getAfter_bidding_status() {
            return after_bidding_status;
        }

        public void setAfter_bidding_status(String after_bidding_status) {
            this.after_bidding_status = after_bidding_status;
        }

        public String getAudit_desc() {
            return audit_desc;
        }

        public void setAudit_desc(String audit_desc) {
            this.audit_desc = audit_desc;
        }

        public String getConsumer_uid() {
            return consumer_uid;
        }

        public void setConsumer_uid(String consumer_uid) {
            this.consumer_uid = consumer_uid;
        }

        public String getBeishu_thread_id() {
            return beishu_thread_id;
        }

        public void setBeishu_thread_id(String beishu_thread_id) {
            this.beishu_thread_id = beishu_thread_id;
        }

        public String getBidder_count() {
            return bidder_count;
        }

        public void setBidder_count(String bidder_count) {
            this.bidder_count = bidder_count;
        }

        public boolean isBidding_status() {
            return bidding_status;
        }

        public void setBidding_status(boolean bidding_status) {
            this.bidding_status = bidding_status;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getClick_number() {
            return click_number;
        }

        public void setClick_number(String click_number) {
            this.click_number = click_number;
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

        public String getConsumer_name() {
            return consumer_name;
        }

        public void setConsumer_name(String consumer_name) {
            this.consumer_name = consumer_name;
        }

        public String getContacts_mobile() {
            return contacts_mobile;
        }

        public void setContacts_mobile(String contacts_mobile) {
            this.contacts_mobile = contacts_mobile;
        }

        public String getContacts_name() {
            return contacts_name;
        }

        public void setContacts_name(String contacts_name) {
            this.contacts_name = contacts_name;
        }

        public String getCustom_string_status() {
            return custom_string_status;
        }

        public void setCustom_string_status(String custom_string_status) {
            this.custom_string_status = custom_string_status;
        }

        public String getDecoration_budget() {
            return decoration_budget;
        }

        public void setDecoration_budget(String decoration_budget) {
            this.decoration_budget = decoration_budget;
        }

        public String getDecoration_style() {
            return decoration_style;
        }

        public void setDecoration_style(String decoration_style) {
            this.decoration_style = decoration_style;
        }

        public String getDesign_budget() {
            return design_budget;
        }

        public void setDesign_budget(String design_budget) {
            this.design_budget = design_budget;
        }

        public String getDetail_desc() {
            return detail_desc;
        }

        public void setDetail_desc(String detail_desc) {
            this.detail_desc = detail_desc;
        }

        public String getDistrict_name() {
            return district_name;
        }

        public void setDistrict_name(String district_name) {
            this.district_name = district_name;
        }

        public String getEnd_day() {
            return end_day;
        }

        public void setEnd_day(String end_day) {
            this.end_day = end_day;
        }

        public String getHouse_area() {
            return house_area;
        }

        public void setHouse_area(String house_area) {
            this.house_area = house_area;
        }

        public String getHouse_type() {
            return house_type;
        }

        public void setHouse_type(String house_type) {
            this.house_type = house_type;
        }

        public String getIs_beishu() {
            return is_beishu;
        }

        public void setIs_beishu(String is_beishu) {
            this.is_beishu = is_beishu;
        }

        public String getIs_public() {
            return is_public;
        }

        public void setIs_public(String is_public) {
            this.is_public = is_public;
        }

        public String getLiving_room() {
            return living_room;
        }

        public void setLiving_room(String living_room) {
            this.living_room = living_room;
        }

        public String getNeeds_id() {
            return needs_id;
        }

        public void setNeeds_id(String needs_id) {
            this.needs_id = needs_id;
        }

        public String getProvince_name() {
            return province_name;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }

        public String getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(String publish_time) {
            this.publish_time = publish_time;
        }

        public String getWk_template_id() {
            return wk_template_id;
        }

        public void setWk_template_id(String wk_template_id) {
            this.wk_template_id = wk_template_id;
        }

        public String getProject_status() {
            return project_status;
        }

        public void setProject_status(String project_status) {
            this.project_status = project_status;
        }

        public String getDesign_style() {
            return design_style;
        }

        public void setDesign_style(String design_style) {
            this.design_style = design_style;
        }

        public String getMain_demand() {
            return main_demand;
        }

        public void setMain_demand(String main_demand) {
            this.main_demand = main_demand;
        }

        public String getProject_type() {
            return project_type;
        }

        public void setProject_type(String project_type) {
            this.project_type = project_type;
        }

        public String getProject_start_time() {
            return project_start_time;
        }

        public void setProject_start_time(String project_start_time) {
            this.project_start_time = project_start_time;
        }

        public String getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(String customer_id) {
            this.customer_id = customer_id;
        }

        public String getHs_uid() {
            return hs_uid;
        }

        public void setHs_uid(String hs_uid) {
            this.hs_uid = hs_uid;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getDesigner_name() {
            return designer_name;
        }

        public void setDesigner_name(String designer_name) {
            this.designer_name = designer_name;
        }

        public String getPkg_name() {
            return pkg_name;
        }

        public void setPkg_name(String pkg_name) {
            this.pkg_name = pkg_name;
        }

        public String getCustom_string_pkg_type() {
            return custom_string_pkg_type;
        }

        public void setCustom_string_pkg_type(String custom_string_pkg_type) {
            this.custom_string_pkg_type = custom_string_pkg_type;
        }

        public List<MPMeasureFormBean.BiddersBean> getBidders() {
            return bidders;
        }

        public void setBidders(List<MPMeasureFormBean.BiddersBean> bidders) {
            this.bidders = bidders;
        }
    }
}
