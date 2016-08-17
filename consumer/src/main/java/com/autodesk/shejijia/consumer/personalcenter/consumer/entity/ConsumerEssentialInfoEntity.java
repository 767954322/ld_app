package com.autodesk.shejijia.consumer.personalcenter.consumer.entity;

import java.io.Serializable;
/**
 * @author  Malidong .
 * @version 1.0 .
 * @date    16-6-7 上午11:19
 * @file    ConsumerEssentialInfoEntity.java  .
 * @brief   消费者个人信息的实体类.
 */
public class ConsumerEssentialInfoEntity implements Serializable {
    /**
     * address : 北京大学6号楼
     * avatar : http://image.juranzx.com.cn:8082/img/56d53d89e4b07d88ca9079aa.img
     * birthday : 1990-02-11
     * city : 北京市
     * district : 石景山区
     * email : hsingchiang.lin@leediancn.com
     * first_name : dudu
     * gender : 2
     * has_secreted : 0
     * hitachi_account : hsingchiang.lin
     * home_phone : 18510990180
     * is_email_binding : 1
     * is_order_sms : 0
     * is_validated_by_mobile : 0
     * last_name : dudu
     * member_id : 1398796
     * mobile_number : null
     * nick_name : dudu
     * province : 北京市
     * register_time : 2016-02-18 18:49:48
     * user_name : hsingchiang.lin
     * zip_code : 100078
     */
    private String address;
    private String avatar;
    private String birthday;
    private String city;
    private String district;
    private String email;
    private String first_name;
    private int gender;
    private int has_secreted;
    private String hitachi_account;
    private String home_phone;
    private int is_email_binding;
    private int is_order_sms;
    private int is_validated_by_mobile;
    private String last_name;
    private int member_id;
    private String mobile_number;
    private String nick_name;
    private String province;
    private String register_time;
    private String user_name;
    private String zip_code;
    private String province_name;
    private String city_name;
    private String district_name;

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

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setHas_secreted(int has_secreted) {
        this.has_secreted = has_secreted;
    }

    public void setHitachi_account(String hitachi_account) {
        this.hitachi_account = hitachi_account;
    }

    public void setHome_phone(String home_phone) {
        this.home_phone = home_phone;
    }

    public void setIs_email_binding(int is_email_binding) {
        this.is_email_binding = is_email_binding;
    }

    public void setIs_order_sms(int is_order_sms) {
        this.is_order_sms = is_order_sms;
    }

    public void setIs_validated_by_mobile(int is_validated_by_mobile) {
        this.is_validated_by_mobile = is_validated_by_mobile;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getAddress() {
        return address;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public int getGender() {
        return gender;
    }

    public int getHas_secreted() {
        return has_secreted;
    }

    public String getHitachi_account() {
        return hitachi_account;
    }

    public String getHome_phone() {
        return home_phone;
    }

    public int getIs_email_binding() {
        return is_email_binding;
    }

    public int getIs_order_sms() {
        return is_order_sms;
    }

    public int getIs_validated_by_mobile() {
        return is_validated_by_mobile;
    }

    public String getLast_name() {
        return last_name;
    }

    public int getMember_id() {
        return member_id;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public String getNick_name() {
        return nick_name;
    }

    public String getProvince() {
        return province;
    }

    public String getRegister_time() {
        return register_time;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getZip_code() {
        return zip_code;
    }

}
