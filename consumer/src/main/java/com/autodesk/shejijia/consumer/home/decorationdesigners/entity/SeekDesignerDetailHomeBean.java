package com.autodesk.shejijia.consumer.home.decorationdesigners.entity;

import java.io.Serializable;

/**
 *
 * @author    DongXueQiu .
 * @version   1.0 .
 * @date       2016/4/22 0022 16:26 .
 *  @file     SeekDesignerDetailHomeBean  .
 *  @brief    查看设计师设计过的房子的bean.
 */
public class SeekDesignerDetailHomeBean implements Serializable{
    private String address;
    private String avatar;
    private String birthday;
    private String city;
    private String city_name;
    private DesignerEntity designer;
    private String district;
    private String district_name;
    private String email;
    private String first_name;
    private int gender;
    private String hitachi_account;
    private String hs_uid;
    private int is_email_binding;
    private int is_order_sms;
    private int is_validated_by_mobile;
    private int member_id;
    private String mobile_number;
    private String nick_name;
    private String province;
    private String province_name;

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

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public void setDesigner(DesignerEntity designer) {
        this.designer = designer;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
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

    public void setHs_uid(String hs_uid) {
        this.hs_uid = hs_uid;
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

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
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

    public String getCity_name() {
        return city_name;
    }

    public DesignerEntity getDesigner() {
        return designer;
    }

    public String getDistrict() {
        return district;
    }

    public String getDistrict_name() {
        return district_name;
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

    public String getHs_uid() {
        return hs_uid;
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

    public String getProvince_name() {
        return province_name;
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

    public static class DesignerEntity {
        private String acs_member_id;
        private int case_count;
        private String design_price_max;
        private String design_price_min;
        private int experience;
        private String graduate_from;
        private String introduction;
        private int is_loho;
        private int is_real_name;
        private String measurement_price;
        private String personal_honour;
        private String style_long_names;
        private String style_names;
        private String styles;

        public void setAcs_member_id(String acs_member_id) {
            this.acs_member_id = acs_member_id;
        }

        public void setCase_count(int case_count) {
            this.case_count = case_count;
        }

        public void setDesign_price_max(String design_price_max) {
            this.design_price_max = design_price_max;
        }

        public void setDesign_price_min(String design_price_min) {
            this.design_price_min = design_price_min;
        }

        public void setExperience(int experience) {
            this.experience = experience;
        }

        public void setGraduate_from(String graduate_from) {
            this.graduate_from = graduate_from;
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

        public void setMeasurement_price(String measurement_price) {
            this.measurement_price = measurement_price;
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

        public String getAcs_member_id() {
            return acs_member_id;
        }

        public int getCase_count() {
            return case_count;
        }

        public String getDesign_price_max() {
            return design_price_max;
        }

        public String getDesign_price_min() {
            return design_price_min;
        }

        public int getExperience() {
            return experience;
        }

        public String getGraduate_from() {
            return graduate_from;
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

        public String getMeasurement_price() {
            return measurement_price;
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
    }

    public static class RealNameEntity {
        private String certificate_no;
        private String mobile_number;
        private String real_name;

        public void setCertificate_no(String certificate_no) {
            this.certificate_no = certificate_no;
        }

        public void setMobile_number(String mobile_number) {
            this.mobile_number = mobile_number;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getCertificate_no() {
            return certificate_no;
        }

        public String getMobile_number() {
            return mobile_number;
        }

        public String getReal_name() {
            return real_name;
        }
    }
}
