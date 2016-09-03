package com.autodesk.shejijia.consumer.personalcenter.designer.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 16-6-7
 * @file BidHallEntity.java  .
 * @brief .
 */
public class BidHallEntity implements Serializable {

    private Object _link;
    private int limit;
    private int offset;
    private long date;
    private int count;

    private List<NeedsListBean> needs_list;

    public Object get_link() {
        return _link;
    }

    public void set_link(Object _link) {
        this._link = _link;
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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<NeedsListBean> getNeeds_list() {
        return needs_list;
    }

    public void setNeeds_list(List<NeedsListBean> needs_list) {
        this.needs_list = needs_list;
    }

    public static class NeedsListBean {
        private String city_name;
        private int needs_id;
        private String consumer_mobile;
        private String consumer_name;
        private String contacts_mobile;
        private String living_room;
        private int bidder_count;
        private String city;
        private String decoration_style;
        private String house_type;
        private String toilet;
        private String is_public;
        private Object beishu_thread_id;
        private Object after_bidding_status;
        private String province;
        private int custom_string_status;
        private String district;
        private String is_beishu;
        private boolean bidding_status;
        private String wk_template_id;
        private String community_name;
        private String decoration_budget;
        private Object bidders;
        private Object avatar;
        private String consumer_uid;
        private String province_name;
        private String detail_desc;
        private String publish_time;
        private String district_name;
        private int click_number;
        private String end_day;
        private String design_budget;
        private String house_area;
        private String contacts_name;
        private String room;

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public int getNeeds_id() {
            return needs_id;
        }

        public void setNeeds_id(int needs_id) {
            this.needs_id = needs_id;
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

        public String getLiving_room() {
            return living_room;
        }

        public void setLiving_room(String living_room) {
            this.living_room = living_room;
        }

        public int getBidder_count() {
            return bidder_count;
        }

        public void setBidder_count(int bidder_count) {
            this.bidder_count = bidder_count;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDecoration_style() {
            return decoration_style;
        }

        public void setDecoration_style(String decoration_style) {
            this.decoration_style = decoration_style;
        }

        public String getHouse_type() {
            return house_type;
        }

        public void setHouse_type(String house_type) {
            this.house_type = house_type;
        }

        public String getToilet() {
            return toilet;
        }

        public void setToilet(String toilet) {
            this.toilet = toilet;
        }

        public String getIs_public() {
            return is_public;
        }

        public void setIs_public(String is_public) {
            this.is_public = is_public;
        }

        public Object getBeishu_thread_id() {
            return beishu_thread_id;
        }

        public void setBeishu_thread_id(Object beishu_thread_id) {
            this.beishu_thread_id = beishu_thread_id;
        }

        public Object getAfter_bidding_status() {
            return after_bidding_status;
        }

        public void setAfter_bidding_status(Object after_bidding_status) {
            this.after_bidding_status = after_bidding_status;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public int getCustom_string_status() {
            return custom_string_status;
        }

        public void setCustom_string_status(int custom_string_status) {
            this.custom_string_status = custom_string_status;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getIs_beishu() {
            return is_beishu;
        }

        public void setIs_beishu(String is_beishu) {
            this.is_beishu = is_beishu;
        }

        public boolean isBidding_status() {
            return bidding_status;
        }

        public void setBidding_status(boolean bidding_status) {
            this.bidding_status = bidding_status;
        }

        public String getWk_template_id() {
            return wk_template_id;
        }

        public void setWk_template_id(String wk_template_id) {
            this.wk_template_id = wk_template_id;
        }

        public String getCommunity_name() {
            return community_name;
        }

        public void setCommunity_name(String community_name) {
            this.community_name = community_name;
        }

        public String getDecoration_budget() {
            return decoration_budget;
        }

        public void setDecoration_budget(String decoration_budget) {
            this.decoration_budget = decoration_budget;
        }

        public Object getBidders() {
            return bidders;
        }

        public void setBidders(Object bidders) {
            this.bidders = bidders;
        }

        public Object getAvatar() {
            return avatar;
        }

        public void setAvatar(Object avatar) {
            this.avatar = avatar;
        }

        public String getConsumer_uid() {
            return consumer_uid;
        }

        public void setConsumer_uid(String consumer_uid) {
            this.consumer_uid = consumer_uid;
        }

        public String getProvince_name() {
            return province_name;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }

        public String getDetail_desc() {
            return detail_desc;
        }

        public void setDetail_desc(String detail_desc) {
            this.detail_desc = detail_desc;
        }

        public String getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(String publish_time) {
            this.publish_time = publish_time;
        }

        public String getDistrict_name() {
            return district_name;
        }

        public void setDistrict_name(String district_name) {
            this.district_name = district_name;
        }

        public int getClick_number() {
            return click_number;
        }

        public void setClick_number(int click_number) {
            this.click_number = click_number;
        }

        public String getEnd_day() {
            return end_day;
        }

        public void setEnd_day(String end_day) {
            this.end_day = end_day;
        }

        public String getDesign_budget() {
            return design_budget;
        }

        public void setDesign_budget(String design_budget) {
            this.design_budget = design_budget;
        }

        public String getHouse_area() {
            return house_area;
        }

        public void setHouse_area(String house_area) {
            this.house_area = house_area;
        }

        public String getContacts_name() {
            return contacts_name;
        }

        public void setContacts_name(String contacts_name) {
            this.contacts_name = contacts_name;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }
    }
}
