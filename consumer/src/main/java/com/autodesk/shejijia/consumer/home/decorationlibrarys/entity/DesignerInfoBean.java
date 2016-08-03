package com.autodesk.shejijia.consumer.home.decorationlibrarys.entity;

import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.DesignerBean;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.RealNameBean;

import java.io.Serializable;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-8-3 .
 * @file DesignerInfoBean.java .
 * @brief 案例库首页获取的设计师基本信息 .
 */
public class DesignerInfoBean implements Serializable {
    private DesignerBean designer;
    private String email;
    private String avatar;
    private int gender;
    private String birthday;
    private String province;
    private String city;
    private String district;
    private String address;
    private String member_id;
    private String hs_uid;
    private String first_name;
    private String last_name;
    private String home_phone;
    private String zip_code;
    private String hitachi_account;
    private String user_name;
    private String register_date;
    private String mobile_number;
    private String city_name;
    private String province_name;
    private String district_name;
    private int is_order_sms;
    private String nick_name;
    private int is_validated_by_mobile;
    private int is_email_binding;
    private int has_secreted;
    public boolean is_following;

    /**
     * 老数据，有这一字段
     */
    private RealNameBean real_name;

    public RealNameBean getReal_name() {
        return real_name;
    }

    public void setReal_name(RealNameBean real_name) {
        this.real_name = real_name;
    }

    public DesignerBean getDesigner() {
        return designer;
    }

    public void setDesigner(DesignerBean designer) {
        this.designer = designer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getHs_uid() {
        return hs_uid;
    }

    public void setHs_uid(String hs_uid) {
        this.hs_uid = hs_uid;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getHome_phone() {
        return home_phone;
    }

    public void setHome_phone(String home_phone) {
        this.home_phone = home_phone;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getHitachi_account() {
        return hitachi_account;
    }

    public void setHitachi_account(String hitachi_account) {
        this.hitachi_account = hitachi_account;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getRegister_date() {
        return register_date;
    }

    public void setRegister_date(String register_date) {
        this.register_date = register_date;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public int getIs_order_sms() {
        return is_order_sms;
    }

    public void setIs_order_sms(int is_order_sms) {
        this.is_order_sms = is_order_sms;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public int getIs_validated_by_mobile() {
        return is_validated_by_mobile;
    }

    public void setIs_validated_by_mobile(int is_validated_by_mobile) {
        this.is_validated_by_mobile = is_validated_by_mobile;
    }

    public int getIs_email_binding() {
        return is_email_binding;
    }

    public void setIs_email_binding(int is_email_binding) {
        this.is_email_binding = is_email_binding;
    }

    public int getHas_secreted() {
        return has_secreted;
    }

    public void setHas_secreted(int has_secreted) {
        this.has_secreted = has_secreted;
    }

    public boolean is_following() {
        return is_following;
    }

    public void setIs_following(boolean is_following) {
        this.is_following = is_following;
    }
}
