package com.autodesk.shejijia.consumer.personalcenter.designer.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 16-6-7
 * @file DesignerInfoDetails.java  .
 * @brief 设计师信息.
 */
public class DesignerInfoDetails implements Serializable {

    private DesignerBean designer;
    private String email;
    private String avatar;
    private String gender;
    private String birthday;
    private String province;
    private String city;
    private String district;
    private String address;
    private String follows;
    private Object sort;

    private RealNameBean real_name;
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
    private String is_order_sms;
    private String nick_name;
    private String english_name;
    private String is_validated_by_mobile;
    private String is_email_binding;
    private String has_secreted;
    private boolean is_following;
    private String audit_status;
    private String main_designers;
    private String last_login_date;
    private String member_status;
    private String mobile_binding_status;
    private String user_email;
    private String mobile_phone;
    private List<CasesListBean> cases_list;

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
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


    public Object getSort() {
        return sort;
    }

    public void setSort(Object sort) {
        this.sort = sort;
    }

    public RealNameBean getReal_name() {
        return real_name;
    }

    public void setReal_name(RealNameBean real_name) {
        this.real_name = real_name;
    }

    public String getFollows() {
        return follows;
    }

    public void setFollows(String follows) {
        this.follows = follows;
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

    public String getIs_order_sms() {
        return is_order_sms;
    }

    public void setIs_order_sms(String is_order_sms) {
        this.is_order_sms = is_order_sms;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getEnglish_name() {
        return english_name;
    }

    public void setEnglish_name(String english_name) {
        this.english_name = english_name;
    }

    public String getIs_validated_by_mobile() {
        return is_validated_by_mobile;
    }

    public void setIs_validated_by_mobile(String is_validated_by_mobile) {
        this.is_validated_by_mobile = is_validated_by_mobile;
    }

    public String getIs_email_binding() {
        return is_email_binding;
    }

    public void setIs_email_binding(String is_email_binding) {
        this.is_email_binding = is_email_binding;
    }

    public String getHas_secreted() {
        return has_secreted;
    }

    public void setHas_secreted(String has_secreted) {
        this.has_secreted = has_secreted;
    }

    public boolean is_following() {
        return is_following;
    }

    public void setIs_following(boolean is_following) {
        this.is_following = is_following;
    }

    public String getAudit_status() {
        return audit_status;
    }

    public void setAudit_status(String audit_status) {
        this.audit_status = audit_status;
    }

    public String getMain_designers() {
        return main_designers;
    }

    public void setMain_designers(String main_designers) {
        this.main_designers = main_designers;
    }

    public String getLast_login_date() {
        return last_login_date;
    }

    public void setLast_login_date(String last_login_date) {
        this.last_login_date = last_login_date;
    }

    public String getMember_status() {
        return member_status;
    }

    public void setMember_status(String member_status) {
        this.member_status = member_status;
    }

    public String getMobile_binding_status() {
        return mobile_binding_status;
    }

    public void setMobile_binding_status(String mobile_binding_status) {
        this.mobile_binding_status = mobile_binding_status;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public List<CasesListBean> getCases_list() {
        return cases_list;
    }

    public void setCases_list(List<CasesListBean> cases_list) {
        this.cases_list = cases_list;
    }

    public static class DesignerBean implements Serializable {
        private Object studio;
        private String styles;
        private String introduction;
        private String experience;
        private String summary;
        private String signature;
        private String acs_member_id;
        private String style_names;
        private String style_long_names;
        private String measurement_price;
        private String design_price_min;
        private String design_price_max;
        private String graduate_from;
        private String personal_honour;
        private String diy_count;
        private String case_count;
        private int is_loho;
        private int is_real_name;
        private String theme_pic;
        private String type_code;
        private String type_name;
        private String store_id;
        private String store_name;
        private String company_id;
        private String company_name;
        private String design_price_code;
        private String evalution_avg_scores;
        private String evalution_count;
        private String occupational_en;
        private String occupational_cn;
        private String designer_profile_cover;
        private String designer_detail_cover;
        private String designer_profile_cover_app;
        private String designer_detail_cover_app;

        public Object getStudio() {
            return studio;
        }

        public void setStudio(Object studio) {
            this.studio = studio;
        }

        public String getStyles() {
            return styles;
        }

        public void setStyles(String styles) {
            this.styles = styles;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public Object getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getAcs_member_id() {
            return acs_member_id;
        }

        public void setAcs_member_id(String acs_member_id) {
            this.acs_member_id = acs_member_id;
        }

        public String getStyle_names() {
            return style_names;
        }

        public void setStyle_names(String style_names) {
            this.style_names = style_names;
        }

        public String getStyle_long_names() {
            return style_long_names;
        }

        public void setStyle_long_names(String style_long_names) {
            this.style_long_names = style_long_names;
        }

        public String getMeasurement_price() {
            return measurement_price;
        }

        public void setMeasurement_price(String measurement_price) {
            this.measurement_price = measurement_price;
        }

        public String getDesign_price_min() {
            return design_price_min;
        }

        public void setDesign_price_min(String design_price_min) {
            this.design_price_min = design_price_min;
        }

        public String getDesign_price_max() {
            return design_price_max;
        }

        public void setDesign_price_max(String design_price_max) {
            this.design_price_max = design_price_max;
        }

        public String getGraduate_from() {
            return graduate_from;
        }

        public void setGraduate_from(String graduate_from) {
            this.graduate_from = graduate_from;
        }

        public String getPersonal_honour() {
            return personal_honour;
        }

        public void setPersonal_honour(String personal_honour) {
            this.personal_honour = personal_honour;
        }

        public String getDiy_count() {
            return diy_count;
        }

        public void setDiy_count(String diy_count) {
            this.diy_count = diy_count;
        }

        public String getCase_count() {
            return case_count;
        }

        public void setCase_count(String case_count) {
            this.case_count = case_count;
        }

        public int getIs_loho() {
            return is_loho;
        }

        public void setIs_loho(int is_loho) {
            this.is_loho = is_loho;
        }

        public int getIs_real_name() {
            return is_real_name;
        }

        public void setIs_real_name(int is_real_name) {
            this.is_real_name = is_real_name;
        }

        public String getTheme_pic() {
            return theme_pic;
        }

        public void setTheme_pic(String theme_pic) {
            this.theme_pic = theme_pic;
        }

        public String getType_code() {
            return type_code;
        }

        public void setType_code(String type_code) {
            this.type_code = type_code;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getDesign_price_code() {
            return design_price_code;
        }

        public void setDesign_price_code(String design_price_code) {
            this.design_price_code = design_price_code;
        }

        public String getEvalution_avg_scores() {
            return evalution_avg_scores;
        }

        public void setEvalution_avg_scores(String evalution_avg_scores) {
            this.evalution_avg_scores = evalution_avg_scores;
        }

        public String getEvalution_count() {
            return evalution_count;
        }

        public void setEvalution_count(String evalution_count) {
            this.evalution_count = evalution_count;
        }

        public String getOccupational_en() {
            return occupational_en;
        }

        public void setOccupational_en(String occupational_en) {
            this.occupational_en = occupational_en;
        }

        public String getOccupational_cn() {
            return occupational_cn;
        }

        public void setOccupational_cn(String occupational_cn) {
            this.occupational_cn = occupational_cn;
        }

        public String getDesigner_profile_cover() {
            return designer_profile_cover;
        }

        public void setDesigner_profile_cover(String designer_profile_cover) {
            this.designer_profile_cover = designer_profile_cover;
        }

        public String getDesigner_detail_cover() {
            return designer_detail_cover;
        }

        public void setDesigner_detail_cover(String designer_detail_cover) {
            this.designer_detail_cover = designer_detail_cover;
        }

        public String getDesigner_profile_cover_app() {
            return designer_profile_cover_app;
        }

        public void setDesigner_profile_cover_app(String designer_profile_cover_app) {
            this.designer_profile_cover_app = designer_profile_cover_app;
        }

        public String getDesigner_detail_cover_app() {
            return designer_detail_cover_app;
        }

        public void setDesigner_detail_cover_app(String designer_detail_cover_app) {
            this.designer_detail_cover_app = designer_detail_cover_app;
        }
    }

    public static class RealNameBean implements Serializable {
        private String birthday;
        private String auditor;
        private String real_name;
        private String certificate_type;
        private String certificate_no;
        private Object photo_front_end;
        private Object photo_back_end;
        private Object photo_in_hand;
        private String audit_status;
        private String mobile_number;
        private String audit_date;
        private String auditor_opinion;
        /**
         * status : 2
         * apply_date : 2016-08-31 09:21:49
         * modified_date : 2016-09-01 11:02:29
         */

        private HighLevelAuditBean high_level_audit;

        public Object getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getAuditor() {
            return auditor;
        }

        public void setAuditor(String auditor) {
            this.auditor = auditor;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getCertificate_type() {
            return certificate_type;
        }

        public void setCertificate_type(String certificate_type) {
            this.certificate_type = certificate_type;
        }

        public String getCertificate_no() {
            return certificate_no;
        }

        public void setAudit_status(String audit_status) {
            this.audit_status = audit_status;
        }

        public void setAudit_date(String audit_date) {
            this.audit_date = audit_date;
        }

        public void setAuditor_opinion(String auditor_opinion) {
            this.auditor_opinion = auditor_opinion;
        }

        public void setCertificate_no(String certificate_no) {
            this.certificate_no = certificate_no;
        }

        public Object getPhoto_front_end() {
            return photo_front_end;
        }

        public void setPhoto_front_end(Object photo_front_end) {
            this.photo_front_end = photo_front_end;
        }

        public Object getPhoto_back_end() {
            return photo_back_end;
        }

        public void setPhoto_back_end(Object photo_back_end) {
            this.photo_back_end = photo_back_end;
        }

        public Object getPhoto_in_hand() {
            return photo_in_hand;
        }

        public void setPhoto_in_hand(Object photo_in_hand) {
            this.photo_in_hand = photo_in_hand;
        }

        public Object getAudit_status() {
            return audit_status;
        }

        public String getMobile_number() {
            return mobile_number;
        }

        public void setMobile_number(String mobile_number) {
            this.mobile_number = mobile_number;
        }

        public String getAudit_date() {
            return audit_date;
        }

        public String getAuditor_opinion() {
            return auditor_opinion;
        }

        public HighLevelAuditBean getHigh_level_audit() {
            return high_level_audit;
        }

        public void setHigh_level_audit(HighLevelAuditBean high_level_audit) {
            this.high_level_audit = high_level_audit;
        }

        public static class HighLevelAuditBean implements Serializable {
            private int status;
            private String apply_date;
            private String modified_date;

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getApply_date() {
                return apply_date;
            }

            public void setApply_date(String apply_date) {
                this.apply_date = apply_date;
            }

            public String getModified_date() {
                return modified_date;
            }

            public void setModified_date(String modified_date) {
                this.modified_date = modified_date;
            }
        }
    }

    public static class CasesListBean implements Serializable {
        private String bedroom;
        private String city;
        private String description;
        private String district;
        private String id;
        private String province;
        private String restroom;
        private String title;
        private String weight;
        private String audit_desc;
        private String case_color;
        private String case_type;
        private String city_name;
        private String click_number;
        private String community_name;
        private String create_date;
        private String custom_string_status;
        private Object decoration_type;
        private String designer_id;
        private String district_name;
        private String favorite_count;
        private String house_type;
        private String hs_designer_uid;
        private String is_recommended;
        private String prj_base_price;
        private String prj_furniture_price;
        private String prj_hidden_price;
        private String prj_material_price;
        private String prj_other_price;
        private String prj_price;
        private String project_style;
        private String protocol_price;
        private String province_name;
        private String room_area;
        private String room_type;
        private String search_tag;

        private List<ImagesBean> images;

        public String getBedroom() {
            return bedroom;
        }

        public void setBedroom(String bedroom) {
            this.bedroom = bedroom;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getRestroom() {
            return restroom;
        }

        public void setRestroom(String restroom) {
            this.restroom = restroom;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getAudit_desc() {
            return audit_desc;
        }

        public void setAudit_desc(String audit_desc) {
            this.audit_desc = audit_desc;
        }

        public String getCase_color() {
            return case_color;
        }

        public void setCase_color(String case_color) {
            this.case_color = case_color;
        }

        public String getCase_type() {
            return case_type;
        }

        public void setCase_type(String case_type) {
            this.case_type = case_type;
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

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getCustom_string_status() {
            return custom_string_status;
        }

        public void setCustom_string_status(String custom_string_status) {
            this.custom_string_status = custom_string_status;
        }

        public Object getDecoration_type() {
            return decoration_type;
        }

        public void setDecoration_type(Object decoration_type) {
            this.decoration_type = decoration_type;
        }

        public String getDesigner_id() {
            return designer_id;
        }

        public void setDesigner_id(String designer_id) {
            this.designer_id = designer_id;
        }

        public String getDistrict_name() {
            return district_name;
        }

        public void setDistrict_name(String district_name) {
            this.district_name = district_name;
        }

        public String getFavorite_count() {
            return favorite_count;
        }

        public void setFavorite_count(String favorite_count) {
            this.favorite_count = favorite_count;
        }

        public String getHouse_type() {
            return house_type;
        }

        public void setHouse_type(String house_type) {
            this.house_type = house_type;
        }

        public String getHs_designer_uid() {
            return hs_designer_uid;
        }

        public void setHs_designer_uid(String hs_designer_uid) {
            this.hs_designer_uid = hs_designer_uid;
        }

        public String getIs_recommended() {
            return is_recommended;
        }

        public void setIs_recommended(String is_recommended) {
            this.is_recommended = is_recommended;
        }

        public String getPrj_base_price() {
            return prj_base_price;
        }

        public void setPrj_base_price(String prj_base_price) {
            this.prj_base_price = prj_base_price;
        }

        public String getPrj_furniture_price() {
            return prj_furniture_price;
        }

        public void setPrj_furniture_price(String prj_furniture_price) {
            this.prj_furniture_price = prj_furniture_price;
        }

        public String getPrj_hidden_price() {
            return prj_hidden_price;
        }

        public void setPrj_hidden_price(String prj_hidden_price) {
            this.prj_hidden_price = prj_hidden_price;
        }

        public String getPrj_material_price() {
            return prj_material_price;
        }

        public void setPrj_material_price(String prj_material_price) {
            this.prj_material_price = prj_material_price;
        }

        public String getPrj_other_price() {
            return prj_other_price;
        }

        public void setPrj_other_price(String prj_other_price) {
            this.prj_other_price = prj_other_price;
        }

        public String getPrj_price() {
            return prj_price;
        }

        public void setPrj_price(String prj_price) {
            this.prj_price = prj_price;
        }

        public String getProject_style() {
            return project_style;
        }

        public void setProject_style(String project_style) {
            this.project_style = project_style;
        }

        public String getProtocol_price() {
            return protocol_price;
        }

        public void setProtocol_price(String protocol_price) {
            this.protocol_price = protocol_price;
        }

        public String getProvince_name() {
            return province_name;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }

        public String getRoom_area() {
            return room_area;
        }

        public void setRoom_area(String room_area) {
            this.room_area = room_area;
        }

        public String getRoom_type() {
            return room_type;
        }

        public void setRoom_type(String room_type) {
            this.room_type = room_type;
        }

        public String getSearch_tag() {
            return search_tag;
        }

        public void setSearch_tag(String search_tag) {
            this.search_tag = search_tag;
        }

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        public static class ImagesBean implements Serializable {
            private String file_id;
            private String file_name;
            private String file_url;
            private boolean is_primary;

            public Object getFile_id() {
                return file_id;
            }

            public void setFile_id(String file_id) {
                this.file_id = file_id;
            }

            public boolean is_primary() {
                return is_primary;
            }

            public String getFile_name() {
                return file_name;
            }

            public void setFile_name(String file_name) {
                this.file_name = file_name;
            }

            public String getFile_url() {
                return file_url;
            }

            public void setFile_url(String file_url) {
                this.file_url = file_url;
            }

        }
    }
}