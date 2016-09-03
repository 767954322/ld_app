package com.autodesk.shejijia.consumer.bidhall.entity;

import java.io.Serializable;

/**
 * @author  DongXueQiu .
 * @version 1.0 .
 * @date    16-6-7
 * @file    RealNameEntity.java  .
 * @brief    实名认证.
 */
public class RealNameBean implements Serializable {

    private String address;
    private String avatar;
    private String birthday;
    private String city;
    private DesignerEntity designer;
    private String district;
    private String email;
    private String first_name;
    private int gender;
    private String hitachi_account;
    private String home_phone;
    private int is_order_sms;
    private int is_validated_by_mobile;
    private String last_name;
    private int member_id;
    private String nick_name;
    private String province;
    private RealNameEntity real_name;
    private String register_date;
    private String user_name;
    private String zip_code;

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

    public void setDesigner(DesignerEntity designer) {
        this.designer = designer;
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

    public void setHitachi_account(String hitachi_account) {
        this.hitachi_account = hitachi_account;
    }

    public void setHome_phone(String home_phone) {
        this.home_phone = home_phone;
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

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setReal_name(RealNameEntity real_name) {
        this.real_name = real_name;
    }

    public void setRegister_date(String register_date) {
        this.register_date = register_date;
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

    public DesignerEntity getDesigner() {
        return designer;
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

    public String getHitachi_account() {
        return hitachi_account;
    }

    public String getHome_phone() {
        return home_phone;
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

    public String getNick_name() {
        return nick_name;
    }

    public String getProvince() {
        return province;
    }

    public RealNameEntity getReal_name() {
        return real_name;
    }

    public String getRegister_date() {
        return register_date;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getZip_code() {
        return zip_code;
    }

    public static class DesignerEntity implements Serializable {
        private int acs_member_id;
        private double design_price_max;
        private double design_price_min;
        private int experience;
        private String introduction;
        private int is_loho;
        private int is_real_name;
        private String measurement_price;
        private String personal_honour;
        private String style_long_names;
        private String style_names;
        private String styles;
        private String theme_pic;

        public String getMeasurement_price() {
            return measurement_price;
        }

        public void setMeasurement_price(String measurement_price) {
            this.measurement_price = measurement_price;
        }

        public void setAcs_member_id(int acs_member_id) {
            this.acs_member_id = acs_member_id;
        }

        public void setDesign_price_max(double design_price_max) {
            this.design_price_max = design_price_max;
        }

        public void setDesign_price_min(double design_price_min) {
            this.design_price_min = design_price_min;
        }

        public void setExperience(int experience) {
            this.experience = experience;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public void setIs_loho(int is_loho) {
            this.is_loho = is_loho;
        }

        public void setIs_real_name(int is_real_name) {
            this.is_real_name = is_real_name;
        }


        public void setPersonal_honour(String personal_honour) {
            this.personal_honour = personal_honour;
        }

        public void setStyle_long_names(String style_long_names) {
            this.style_long_names = style_long_names;
        }

        public void setStyle_names(String style_names) {
            this.style_names = style_names;
        }

        public void setStyles(String styles) {
            this.styles = styles;
        }

        public void setTheme_pic(String theme_pic) {
            this.theme_pic = theme_pic;
        }

        public int getAcs_member_id() {
            return acs_member_id;
        }

        public double getDesign_price_max() {
            return design_price_max;
        }

        public double getDesign_price_min() {
            return design_price_min;
        }

        public int getExperience() {
            return experience;
        }

        public String getIntroduction() {
            return introduction;
        }

        public int getIs_loho() {
            return is_loho;
        }

        public int getIs_real_name() {
            return is_real_name;
        }



        public String getPersonal_honour() {
            return personal_honour;
        }

        public String getStyle_long_names() {
            return style_long_names;
        }

        public String getStyle_names() {
            return style_names;
        }

        public String getStyles() {
            return styles;
        }

        public String getTheme_pic() {
            return theme_pic;
        }
    }

    public static class RealNameEntity implements Serializable {
        private String certificate_no;
        private String real_name;

        public void setCertificate_no(String certificate_no) {
            this.certificate_no = certificate_no;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getCertificate_no() {
            return certificate_no;
        }

        public String getReal_name() {
            return real_name;
        }
    }
}
