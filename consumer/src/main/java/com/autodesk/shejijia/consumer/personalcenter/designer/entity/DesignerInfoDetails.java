package com.autodesk.shejijia.consumer.personalcenter.designer.entity;

import java.io.Serializable;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 16-6-7
 * @file DesignerInfoDetails.java  .
 * @brief 设计师信息.
 */
public class DesignerInfoDetails implements Serializable {

    private Object address;
    private String avatar;
    private String birthday;
    private String city;
    /**
     * case_count : null
     * design_price_max : null
     * design_price_min : null
     * diy_count : null
     * experience : null
     * graduate_from : null
     * introduction : null
     * is_loho : null
     * is_real_name : 0
     * measurement_price : null
     * personal_honour : null
     * work_room_detail_six_shap : null
     * style_long_names : 欧式风格,日式风格
     * style_names : 日式,欧式
     * styles : japanese,european
     */

    private DesignerEntity designer;
    private Object district;
    private String email;
    private Object first_name;

    public String getContract_no() {
        return contract_no;
    }

    public void setContract_no(String contract_no) {
        this.contract_no = contract_no;
    }

    private String contract_no;
    private int gender;
    private Object has_secreted;
    private String hitachi_account;
    private String home_phone;
    private String hs_uid;
    private Object is_email_binding;
    private int is_order_sms;
    private int is_validated_by_mobile;
    private Object last_name;
    private int member_id;
    private Object mobile_number;
    private String nick_name;
    private Object province;
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

    /**
     * audit_date : null
     * audit_status : null
     * auditor : null
     * auditor_opinion : null
     * birthday : null
     * certificate_no : 123456789987654321
     * certificate_type : null
     * mobile_number : null
     * photo_back_end : null
     * photo_front_end : null
     * photo_in_hand : null
     * real_name : Hugo.li
     */

    private RealNameEntity real_name;
    private Object register_date;
    private Object user_name;
    private String zip_code;

    public void setAddress(Object address) {
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

    public void setDistrict(Object district) {
        this.district = district;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirst_name(Object first_name) {
        this.first_name = first_name;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setHas_secreted(Object has_secreted) {
        this.has_secreted = has_secreted;
    }

    public void setHitachi_account(String hitachi_account) {
        this.hitachi_account = hitachi_account;
    }

    public void setHome_phone(String home_phone) {
        this.home_phone = home_phone;
    }

    public void setHs_uid(String hs_uid) {
        this.hs_uid = hs_uid;
    }

    public void setIs_email_binding(Object is_email_binding) {
        this.is_email_binding = is_email_binding;
    }

    public void setIs_order_sms(int is_order_sms) {
        this.is_order_sms = is_order_sms;
    }

    public void setIs_validated_by_mobile(int is_validated_by_mobile) {
        this.is_validated_by_mobile = is_validated_by_mobile;
    }

    public void setLast_name(Object last_name) {
        this.last_name = last_name;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public void setMobile_number(Object mobile_number) {
        this.mobile_number = mobile_number;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public void setProvince(Object province) {
        this.province = province;
    }

    public void setReal_name(RealNameEntity real_name) {
        this.real_name = real_name;
    }

    public void setRegister_date(Object register_date) {
        this.register_date = register_date;
    }

    public void setUser_name(Object user_name) {
        this.user_name = user_name;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public Object getAddress() {
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

    public Object getDistrict() {
        return district;
    }

    public String getEmail() {
        return email;
    }

    public Object getFirst_name() {
        return first_name;
    }

    public int getGender() {
        return gender;
    }

    public Object getHas_secreted() {
        return has_secreted;
    }

    public String getHitachi_account() {
        return hitachi_account;
    }

    public String getHome_phone() {
        return home_phone;
    }

    public String getHs_uid() {
        return hs_uid;
    }

    public Object getIs_email_binding() {
        return is_email_binding;
    }

    public int getIs_order_sms() {
        return is_order_sms;
    }

    public int getIs_validated_by_mobile() {
        return is_validated_by_mobile;
    }

    public Object getLast_name() {
        return last_name;
    }

    public int getMember_id() {
        return member_id;
    }

    public Object getMobile_number() {
        return mobile_number;
    }

    public String getNick_name() {
        return nick_name;
    }

    public Object getProvince() {
        return province;
    }

    public RealNameEntity getReal_name() {
        return real_name;
    }

    public Object getRegister_date() {
        return register_date;
    }

    public Object getUser_name() {
        return user_name;
    }

    public String getZip_code() {
        return zip_code;
    }

    public static class DesignerEntity implements Serializable {
        private Object case_count;
        private String design_price_max;
        private String design_price_min;
        private Object diy_count;
        private String experience;
        private Object graduate_from;
        private Object introduction;
        private int is_loho;
        private int is_real_name;
        private String measurement_price;
        private Object personal_honour;
        private Object studio;
        private String style_long_names;
        private String style_names;
        private String styles;

        public String getTheme_pic() {
            return theme_pic;
        }

        public void setTheme_pic(String theme_pic) {
            this.theme_pic = theme_pic;
        }

        private String theme_pic;

        public void setCase_count(Object case_count) {
            this.case_count = case_count;
        }

        public void setDesign_price_max(String design_price_max) {
            this.design_price_max = design_price_max;
        }

        public void setDesign_price_min(String design_price_min) {
            this.design_price_min = design_price_min;
        }

        public void setDiy_count(Object diy_count) {
            this.diy_count = diy_count;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public void setGraduate_from(Object graduate_from) {
            this.graduate_from = graduate_from;
        }

        public void setIntroduction(Object introduction) {
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

        public void setPersonal_honour(Object personal_honour) {
            this.personal_honour = personal_honour;
        }

        public void setStudio(Object studio) {
            this.studio = studio;
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

        public Object getCase_count() {
            return case_count;
        }

        public Object getDesign_price_max() {
            return design_price_max;
        }

        public String getDesign_price_min() {
            return design_price_min;
        }

        public Object getDiy_count() {
            return diy_count;
        }

        public String getExperience() {
            return experience;
        }

        public Object getGraduate_from() {
            return graduate_from;
        }

        public Object getIntroduction() {
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

        public Object getPersonal_honour() {
            return personal_honour;
        }

        public Object getStudio() {
            return studio;
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

    public static class RealNameEntity implements Serializable {
        private Object audit_date;
        private Object audit_status;
        private Object auditor;
        private Object auditor_opinion;
        private Object birthday;
        private String certificate_no;
        private Object certificate_type;
        private Object mobile_number;
        private Object photo_back_end;
        private Object photo_front_end;
        private Object photo_in_hand;
        private String real_name;

        public void setAudit_date(Object audit_date) {
            this.audit_date = audit_date;
        }

        public void setAudit_status(Object audit_status) {
            this.audit_status = audit_status;
        }

        public void setAuditor(Object auditor) {
            this.auditor = auditor;
        }

        public void setAuditor_opinion(Object auditor_opinion) {
            this.auditor_opinion = auditor_opinion;
        }

        public void setBirthday(Object birthday) {
            this.birthday = birthday;
        }

        public void setCertificate_no(String certificate_no) {
            this.certificate_no = certificate_no;
        }

        public void setCertificate_type(Object certificate_type) {
            this.certificate_type = certificate_type;
        }

        public void setMobile_number(Object mobile_number) {
            this.mobile_number = mobile_number;
        }

        public void setPhoto_back_end(Object photo_back_end) {
            this.photo_back_end = photo_back_end;
        }

        public void setPhoto_front_end(Object photo_front_end) {
            this.photo_front_end = photo_front_end;
        }

        public void setPhoto_in_hand(Object photo_in_hand) {
            this.photo_in_hand = photo_in_hand;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public Object getAudit_date() {
            return audit_date;
        }

        public Object getAudit_status() {
            return audit_status;
        }

        public Object getAuditor() {
            return auditor;
        }

        public Object getAuditor_opinion() {
            return auditor_opinion;
        }

        public Object getBirthday() {
            return birthday;
        }

        public String getCertificate_no() {
            return certificate_no;
        }

        public Object getCertificate_type() {
            return certificate_type;
        }

        public Object getMobile_number() {
            return mobile_number;
        }

        public Object getPhoto_back_end() {
            return photo_back_end;
        }

        public Object getPhoto_front_end() {
            return photo_front_end;
        }

        public Object getPhoto_in_hand() {
            return photo_in_hand;
        }

        public String getReal_name() {
            return real_name;
        }
    }

}
