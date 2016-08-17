package com.autodesk.shejijia.consumer.personalcenter.consumer.entity;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 16-6-7 上午11:20
 * @file DemandDetailBean.java  .
 * @brief 修改需求成功后返回数据的实体类 .
 */
public class DemandDetailBean implements Serializable {

    private Object after_bidding_status;
    private Object beishu_thread_id;
    private int bidder_count;
    private Object bidders;
    private boolean bidding_status;
    private String city;
    private String city_name;
    private int click_number;
    private String community_name;
    private String consumer_mobile;
    private String consumer_name;
    private String contacts_mobile;
    private String contacts_name;
    private Object contract;
    private String custom_string_status;
    private String decoration_budget;
    private String decoration_style;
    private String design_budget;
    private String detail_desc;
    private String district;
    private String district_name;
    private String end_day;
    private String house_area;
    private String house_type;
    private String is_beishu;
    private String is_public;
    private String living_room;
    private int needs_id;
    private String province;
    private String province_name;
    private String publish_time;
    private String room;
    private String toilet;
    private String wk_template_id;
    private List<?> delivery;

    public void setAfter_bidding_status(Object after_bidding_status) {
        this.after_bidding_status = after_bidding_status;
    }

    public void setBeishu_thread_id(Object beishu_thread_id) {
        this.beishu_thread_id = beishu_thread_id;
    }

    public void setBidder_count(int bidder_count) {
        this.bidder_count = bidder_count;
    }

    public void setBidders(Object bidders) {
        this.bidders = bidders;
    }

    public void setBidding_status(boolean bidding_status) {
        this.bidding_status = bidding_status;
    }

    public void setCity(String city) {
        this.city = TextUtils.isEmpty(city) ? "" : city;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public void setClick_number(int click_number) {
        this.click_number = click_number;
    }

    public void setCommunity_name(String community_name) {
        this.community_name = community_name;
    }

    public void setConsumer_mobile(String consumer_mobile) {
        this.consumer_mobile = consumer_mobile;
    }

    public void setConsumer_name(String consumer_name) {
        this.consumer_name = consumer_name;
    }

    public void setContacts_mobile(String contacts_mobile) {
        this.contacts_mobile = contacts_mobile;
    }

    public void setContacts_name(String contacts_name) {
        this.contacts_name = contacts_name;
    }

    public void setContract(Object contract) {
        this.contract = contract;
    }

    public void setCustom_string_status(String custom_string_status) {
        this.custom_string_status = custom_string_status;
    }

    public void setDecoration_budget(String decoration_budget) {
        this.decoration_budget = decoration_budget;
    }

    public void setDecoration_style(String decoration_style) {
        this.decoration_style = decoration_style;
    }

    public void setDesign_budget(String design_budget) {
        this.design_budget = design_budget;
    }

    public void setDetail_desc(String detail_desc) {
        this.detail_desc = detail_desc;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public void setEnd_day(String end_day) {
        this.end_day = end_day;
    }

    public void setHouse_area(String house_area) {
        this.house_area = house_area;
    }

    public void setHouse_type(String house_type) {
        this.house_type = house_type;
    }

    public void setIs_beishu(String is_beishu) {
        this.is_beishu = is_beishu;
    }

    public void setIs_public(String is_public) {
        this.is_public = is_public;
    }

    public void setLiving_room(String living_room) {
        this.living_room = living_room;
    }

    public void setNeeds_id(int needs_id) {
        this.needs_id = needs_id;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setToilet(String toilet) {
        this.toilet = toilet;
    }

    public void setWk_template_id(String wk_template_id) {
        this.wk_template_id = wk_template_id;
    }

    public void setDelivery(List<?> delivery) {
        this.delivery = delivery;
    }

    public Object getAfter_bidding_status() {
        return after_bidding_status;
    }

    public Object getBeishu_thread_id() {
        return beishu_thread_id;
    }

    public int getBidder_count() {
        return bidder_count;
    }

    public Object getBidders() {
        return bidders;
    }


    public boolean isBidding_status() {
        return bidding_status;
    }

    public String getCity() {
        return city;
    }

    public String getCity_name() {
        return city_name;
    }

    public int getClick_number() {
        return click_number;
    }

    public String getCommunity_name() {
        return community_name;
    }

    public String getConsumer_mobile() {
        return consumer_mobile;
    }

    public String getConsumer_name() {
        return consumer_name;
    }

    public String getContacts_mobile() {
        return contacts_mobile;
    }

    public String getContacts_name() {
        return contacts_name;
    }

    public Object getContract() {
        return contract;
    }

    public String getCustom_string_status() {
        return custom_string_status;
    }

    public String getDecoration_budget() {
        return decoration_budget;
    }

    public String getDecoration_style() {
        return decoration_style;
    }

    public String getDesign_budget() {
        return design_budget;
    }

    public String getDetail_desc() {
        return detail_desc;
    }

    public String getDistrict() {
        return district;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public String getEnd_day() {
        return end_day;
    }

    public String getHouse_area() {
        return house_area;
    }

    public String getHouse_type() {
        return house_type;
    }

    public String getIs_beishu() {
        return is_beishu;
    }

    public String getIs_public() {
        return is_public;
    }

    public String getLiving_room() {
        return living_room;
    }

    public int getNeeds_id() {
        return needs_id;
    }

    public String getProvince() {
        return province;
    }

    public String getProvince_name() {
        return province_name;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public String getRoom() {
        return room;
    }

    public String getToilet() {
        return toilet;
    }

    public String getWk_template_id() {
        return wk_template_id;
    }

    public List<?> getDelivery() {
        return delivery;
    }

    @Override
    public String toString() {
        return "DemandDetailBean{" +
                "after_bidding_status=" + after_bidding_status +
                ", beishu_thread_id=" + beishu_thread_id +
                ", bidder_count=" + bidder_count +
                ", bidders=" + bidders +
                ", bidding_status=" + bidding_status +
                ", city='" + city + '\'' +
                ", city_name='" + city_name + '\'' +
                ", click_number=" + click_number +
                ", community_name='" + community_name + '\'' +
                ", consumer_mobile='" + consumer_mobile + '\'' +
                ", consumer_name='" + consumer_name + '\'' +
                ", contacts_mobile='" + contacts_mobile + '\'' +
                ", contacts_name='" + contacts_name + '\'' +
                ", contract=" + contract +
                ", custom_string_status='" + custom_string_status + '\'' +
                ", decoration_budget='" + decoration_budget + '\'' +
                ", decoration_style='" + decoration_style + '\'' +
                ", design_budget='" + design_budget + '\'' +
                ", detail_desc='" + detail_desc + '\'' +
                ", district='" + district + '\'' +
                ", district_name='" + district_name + '\'' +
                ", end_day='" + end_day + '\'' +
                ", house_area='" + house_area + '\'' +
                ", house_type='" + house_type + '\'' +
                ", is_beishu='" + is_beishu + '\'' +
                ", is_public='" + is_public + '\'' +
                ", living_room='" + living_room + '\'' +
                ", needs_id=" + needs_id +
                ", province='" + province + '\'' +
                ", province_name='" + province_name + '\'' +
                ", publish_time='" + publish_time + '\'' +
                ", room='" + room + '\'' +
                ", toilet='" + toilet + '\'' +
                ", wk_template_id='" + wk_template_id + '\'' +
                ", delivery=" + delivery +
                '}';
    }
}
