package com.autodesk.shejijia.consumer.personalcenter.consumer.entity;

import java.io.Serializable;
/**
 * @author  DongXueQiu .
 * @version 1.0 .
 * @date    16-6-7 上午11:19
 * @file    AmendDemandBean.java  .
 * @brief    修改需求的实体类.
 */
public class AmendDemandBean implements Serializable {

    /**
     * city : 110100
     * city_name : 北京市
     * click_number : 0
     * community_name : she's
     * consumer_mobile : 11012011900
     * consumer_name : APP端发布需求-此字段不用
     * contacts_mobile : 13718601763
     * contacts_name : 发广告
     * custom_string_status : 1
     * decoration_budget : 5万以下
     * decoration_style : 日式
     * detail_desc : desc
     * district : she's
     * district_name : 东城区
     * house_area : 12334
     * house_type : 住宅空间
     * is_beishu : 1
     * is_deleted : 0
     * is_public : 0
     * living_room : 一室
     * province : 110000
     * province_name : 北京
     * publish_time : 1458870895137
     * room : 一厅
     * toilet : 一卫
     */
    private String city;
    private String city_name;
    private int click_number;
    private String community_name;
    private String consumer_mobile;
    private String consumer_name;
    private String contacts_mobile;
    private String contacts_name;
    private String custom_string_status;
    private String decoration_budget;
    private String decoration_style;
    private String detail_desc;
    private String district;
    private String district_name;
    private String house_area;
    private String house_type;
    private String is_beishu;
    private String is_deleted;
    private String is_public;
    private String living_room;
    private String province;
    private String province_name;
    private String publish_time;
    private String room;
    private String toilet;
    /**
     * design_budget : 3000以下
     */

    private String design_budget;
    /**
     * needs_id : null
     */

    private Object needs_id;

    public void setCity(String city) {
        this.city = city;
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

    public void setCustom_string_status(String custom_string_status) {
        this.custom_string_status = custom_string_status;
    }

    public void setDecoration_budget(String decoration_budget) {
        this.decoration_budget = decoration_budget;
    }

    public void setDecoration_style(String decoration_style) {
        this.decoration_style = decoration_style;
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

    public void setHouse_area(String house_area) {
        this.house_area = house_area;
    }

    public void setHouse_type(String house_type) {
        this.house_type = house_type;
    }

    public void setIs_beishu(String is_beishu) {
        this.is_beishu = is_beishu;
    }

    public void setIs_deleted(String is_deleted) {
        this.is_deleted = is_deleted;
    }

    public void setIs_public(String is_public) {
        this.is_public = is_public;
    }

    public void setLiving_room(String living_room) {
        this.living_room = living_room;
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

    public String getCustom_string_status() {
        return custom_string_status;
    }

    public String getDecoration_budget() {
        return decoration_budget;
    }

    public String getDecoration_style() {
        return decoration_style;
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

    public String getHouse_area() {
        return house_area;
    }

    public String getHouse_type() {
        return house_type;
    }

    public String getIs_beishu() {
        return is_beishu;
    }

    public String getIs_deleted() {
        return is_deleted;
    }

    public String getIs_public() {
        return is_public;
    }

    public String getLiving_room() {
        return living_room;
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

    @Override
    public String toString() {
        return "AmendDemandBean{" +
                "city='" + city + '\'' +
                ", city_name='" + city_name + '\'' +
                ", click_number=" + click_number +
                ", community_name='" + community_name + '\'' +
                ", consumer_mobile='" + consumer_mobile + '\'' +
                ", consumer_name='" + consumer_name + '\'' +
                ", contacts_mobile='" + contacts_mobile + '\'' +
                ", contacts_name='" + contacts_name + '\'' +
                ", custom_string_status='" + custom_string_status + '\'' +
                ", decoration_budget='" + decoration_budget + '\'' +
                ", decoration_style='" + decoration_style + '\'' +
                ", detail_desc='" + detail_desc + '\'' +
                ", district='" + district + '\'' +
                ", district_name='" + district_name + '\'' +
                ", house_area='" + house_area + '\'' +
                ", house_type='" + house_type + '\'' +
                ", is_beishu='" + is_beishu + '\'' +
                ", is_deleted='" + is_deleted + '\'' +
                ", is_public='" + is_public + '\'' +
                ", living_room='" + living_room + '\'' +
                ", province='" + province + '\'' +
                ", province_name='" + province_name + '\'' +
                ", publish_time='" + publish_time + '\'' +
                ", room='" + room + '\'' +
                ", toilet='" + toilet + '\'' +
                '}';
    }

    public String getDesign_budget() {
        return design_budget;
    }

    public void setDesign_budget(String design_budget) {
        this.design_budget = design_budget;
    }
}
