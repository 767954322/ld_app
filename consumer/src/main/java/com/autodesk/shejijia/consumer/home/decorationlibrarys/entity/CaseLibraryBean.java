package com.autodesk.shejijia.consumer.home.decorationlibrarys.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 2016/2/25 0025 18:51 .
 * @file user CaseLibraryBean .
 * @brief 案例库 .
 */
public class CaseLibraryBean implements Serializable {

    private int count;
    private long date;
    private int limit;
    private int offset;
    private List<CasesEntity> cases;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
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

    public List<CasesEntity> getCases() {
        return cases;
    }

    public void setCases(List<CasesEntity> cases) {
        this.cases = cases;
    }

    public static class CasesEntity implements Serializable {
        private Object audit_desc;
        private String bedroom;
        private String case_color;
        private String case_type;
        private String city;
        private String city_name;
        private int click_number;
        private String community_name;
        private String create_date;
        private String custom_string_area;
        private String custom_string_bedroom;
        private String custom_string_form;
        private String custom_string_keywords;
        private String custom_string_restroom;
        private String custom_string_status;
        private String custom_string_style;
        private String custom_string_type;
        private Object decoration_type;
        private String description;
        private String designer_id;
        private DesignerInfoEntity designer_info;
        private String district;
        private String district_name;
        private int favorite_count;
        private String house_type;
        private String hs_designer_uid;
        private String id;
        private String is_recommended;
        private String prj_base_price;
        private String prj_furniture_price;
        private String prj_hidden_price;
        private String prj_material_price;
        private String prj_other_price;
        private String prj_price;
        private String project_style;
        private String protocol_price;
        private String province;
        private String province_name;
        private String restroom;
        private String room_area;
        private String room_type;
        private String search_tag;
        private String thumbnails_ext;
        private String title;
        private String weight;
        private List<ImagesEntity> images;

        public static class ImagesEntity implements Serializable {
            private String file_id;
            private String file_name;
            private String file_url;
            private boolean is_delete;
            private boolean is_primary;

            public void setFile_id(String file_id) {
                this.file_id = file_id;
            }

            public void setFile_name(String file_name) {
                this.file_name = file_name;
            }

            public void setFile_url(String file_url) {
                this.file_url = file_url;
            }

            public void setIs_delete(boolean is_delete) {
                this.is_delete = is_delete;
            }

            public void setIs_primary(boolean is_primary) {
                this.is_primary = is_primary;
            }

            public String getFile_id() {
                return file_id;
            }

            public String getFile_name() {
                return file_name;
            }

            public String getFile_url() {
                return file_url;
            }

            public boolean isIs_delete() {
                return is_delete;
            }

            public boolean isIs_primary() {
                return is_primary;
            }
        }

        public Object getAudit_desc() {
            return audit_desc;
        }

        public void setAudit_desc(Object audit_desc) {
            this.audit_desc = audit_desc;
        }

        public String getBedroom() {
            return bedroom;
        }

        public void setBedroom(String bedroom) {
            this.bedroom = bedroom;
        }

        public String getCase_color() {
            return case_color;
        }

        public void setCase_color(String case_color) {
            this.case_color = case_color;
        }

        public Object getCase_type() {
            return case_type;
        }

        public void setCase_type(String case_type) {
            this.case_type = case_type;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public int getClick_number() {
            return click_number;
        }

        public void setClick_number(int click_number) {
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

        public String getCustom_string_area() {
            return custom_string_area;
        }

        public void setCustom_string_area(String custom_string_area) {
            this.custom_string_area = custom_string_area;
        }

        public String getCustom_string_bedroom() {
            return custom_string_bedroom;
        }

        public void setCustom_string_bedroom(String custom_string_bedroom) {
            this.custom_string_bedroom = custom_string_bedroom;
        }

        public String getCustom_string_form() {
            return custom_string_form;
        }

        public void setCustom_string_form(String custom_string_form) {
            this.custom_string_form = custom_string_form;
        }

        public String getCustom_string_keywords() {
            return custom_string_keywords;
        }

        public void setCustom_string_keywords(String custom_string_keywords) {
            this.custom_string_keywords = custom_string_keywords;
        }

        public String getCustom_string_restroom() {
            return custom_string_restroom;
        }

        public void setCustom_string_restroom(String custom_string_restroom) {
            this.custom_string_restroom = custom_string_restroom;
        }

        public String getCustom_string_status() {
            return custom_string_status;
        }

        public void setCustom_string_status(String custom_string_status) {
            this.custom_string_status = custom_string_status;
        }

        public String getCustom_string_style() {
            return custom_string_style;
        }

        public void setCustom_string_style(String custom_string_style) {
            this.custom_string_style = custom_string_style;
        }

        public String getCustom_string_type() {
            return custom_string_type;
        }

        public void setCustom_string_type(String custom_string_type) {
            this.custom_string_type = custom_string_type;
        }

        public Object getDecoration_type() {
            return decoration_type;
        }

        public void setDecoration_type(Object decoration_type) {
            this.decoration_type = decoration_type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDesigner_id() {
            return designer_id;
        }

        public void setDesigner_id(String designer_id) {
            this.designer_id = designer_id;
        }

        public DesignerInfoEntity getDesigner_info() {
            return designer_info;
        }

        public void setDesigner_info(DesignerInfoEntity designer_info) {
            this.designer_info = designer_info;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getDistrict_name() {
            return district_name;
        }

        public void setDistrict_name(String district_name) {
            this.district_name = district_name;
        }

        public int getFavorite_count() {
            return favorite_count;
        }

        public void setFavorite_count(int favorite_count) {
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getProvince_name() {
            return province_name;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }

        public String getRestroom() {
            return restroom;
        }

        public void setRestroom(String restroom) {
            this.restroom = restroom;
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

        public Object getSearch_tag() {
            return search_tag;
        }

        public void setSearch_tag(String search_tag) {
            this.search_tag = search_tag;
        }

        public Object getThumbnails_ext() {
            return thumbnails_ext;
        }

        public void setThumbnails_ext(String thumbnails_ext) {
            this.thumbnails_ext = thumbnails_ext;
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

        public List<ImagesEntity> getImages() {
            return images;
        }

        public void setImages(List<ImagesEntity> images) {
            this.images = images;
        }

        public static class DesignerInfoEntity implements Serializable {
            private String address;
            private String avatar;
            private String birthday;
            private Object cases_list;
            private String city;
            private String city_name;
            /**
             * acs_member_id : 20735688
             * case_count : 3
             * design_price_max : 60
             * design_price_min : 30
             * diy_count : null
             * experience : 12
             * graduate_from : null
             * introduction : 4444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444
             * is_loho : 1
             * is_real_name : 2
             * measurement_price : 0.01
             * personal_honour : 88888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888555555555555555555555555555555555
             * work_room_detail_six_shap : null
             * style_long_names : 日式风格,韩式风格,混搭风格,欧式风格,新古典风格
             * style_names : 古典,欧式,混搭,韩式,日式
             * styles : newClassical,european,mashup,kora,japanese
             * theme_pic : 3
             */

            private DesignerEntity designer;
            private String district;
            private String district_name;
            private String email;
            private String first_name;
            private int gender;
            private Object has_secreted;
            private String hitachi_account;
            private String home_phone;
            private String hs_uid;
            private Object is_email_binding;
            private int is_order_sms;
            private int is_validated_by_mobile;
            private String last_name;
            private String member_id;
            private String mobile_number;
            private String nick_name;
            private String province;
            private String province_name;
            /**
             * audit_date : null
             * audit_status : null
             * auditor : null
             * auditor_opinion : null
             * birthday : null
             * certificate_no : 370786199103056221
             * certificate_type : null
             * mobile_number : null
             * photo_back_end : null
             * photo_front_end : null
             * photo_in_hand : null
             * real_name : 习大大
             */

            private RealNameEntity real_name;
            private String register_date;
            private String user_name;
            private String zip_code;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public Object getCases_list() {
                return cases_list;
            }

            public void setCases_list(Object cases_list) {
                this.cases_list = cases_list;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCity_name() {
                return city_name;
            }

            public void setCity_name(String city_name) {
                this.city_name = city_name;
            }

            public DesignerEntity getDesigner() {
                return designer;
            }

            public void setDesigner(DesignerEntity designer) {
                this.designer = designer;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getDistrict_name() {
                return district_name;
            }

            public void setDistrict_name(String district_name) {
                this.district_name = district_name;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getFirst_name() {
                return first_name;
            }

            public void setFirst_name(String first_name) {
                this.first_name = first_name;
            }

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public Object getHas_secreted() {
                return has_secreted;
            }

            public void setHas_secreted(Object has_secreted) {
                this.has_secreted = has_secreted;
            }

            public String getHitachi_account() {
                return hitachi_account;
            }

            public void setHitachi_account(String hitachi_account) {
                this.hitachi_account = hitachi_account;
            }

            public String getHome_phone() {
                return home_phone;
            }

            public void setHome_phone(String home_phone) {
                this.home_phone = home_phone;
            }

            public Object getHs_uid() {
                return hs_uid;
            }

            public void setHs_uid(String hs_uid) {
                this.hs_uid = hs_uid;
            }

            public Object getIs_email_binding() {
                return is_email_binding;
            }

            public void setIs_email_binding(Object is_email_binding) {
                this.is_email_binding = is_email_binding;
            }

            public int getIs_order_sms() {
                return is_order_sms;
            }

            public void setIs_order_sms(int is_order_sms) {
                this.is_order_sms = is_order_sms;
            }

            public int getIs_validated_by_mobile() {
                return is_validated_by_mobile;
            }

            public void setIs_validated_by_mobile(int is_validated_by_mobile) {
                this.is_validated_by_mobile = is_validated_by_mobile;
            }

            public String getLast_name() {
                return last_name;
            }

            public void setLast_name(String last_name) {
                this.last_name = last_name;
            }

            public String getMember_id() {
                return member_id;
            }

            public void setMember_id(String member_id) {
                this.member_id = member_id;
            }

            public Object getMobile_number() {
                return mobile_number;
            }

            public void setMobile_number(String mobile_number) {
                this.mobile_number = mobile_number;
            }

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getProvince_name() {
                return province_name;
            }

            public void setProvince_name(String province_name) {
                this.province_name = province_name;
            }

            public RealNameEntity getReal_name() {
                return real_name;
            }

            public void setReal_name(RealNameEntity real_name) {
                this.real_name = real_name;
            }

            public String getRegister_date() {
                return register_date;
            }

            public void setRegister_date(String register_date) {
                this.register_date = register_date;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getZip_code() {
                return zip_code;
            }

            public void setZip_code(String zip_code) {
                this.zip_code = zip_code;
            }

            public static class DesignerEntity implements Serializable {
                private String acs_member_id;
                private int case_count;
                private String design_price_max;
                private String design_price_min;
                private String diy_count;
                private int experience;
                private Object graduate_from;
                private String introduction;
                private int is_loho;
                private int is_real_name;
                private String measurement_price;
                private String personal_honour;
                private String studio;
                private String style_long_names;
                private String style_names;
                private String styles;
                private String theme_pic;

                public String getAcs_member_id() {
                    return acs_member_id;
                }

                public void setAcs_member_id(String acs_member_id) {
                    this.acs_member_id = acs_member_id;
                }

                public int getCase_count() {
                    return case_count;
                }

                public void setCase_count(int case_count) {
                    this.case_count = case_count;
                }

                public String getDesign_price_max() {
                    return design_price_max;
                }

                public void setDesign_price_max(String design_price_max) {
                    this.design_price_max = design_price_max;
                }

                public String getDesign_price_min() {
                    return design_price_min;
                }

                public void setDesign_price_min(String design_price_min) {
                    this.design_price_min = design_price_min;
                }

                public String getDiy_count() {
                    return diy_count;
                }

                public void setDiy_count(String diy_count) {
                    this.diy_count = diy_count;
                }

                public int getExperience() {
                    return experience;
                }

                public void setExperience(int experience) {
                    this.experience = experience;
                }

                public Object getGraduate_from() {
                    return graduate_from;
                }

                public void setGraduate_from(Object graduate_from) {
                    this.graduate_from = graduate_from;
                }

                public String getIntroduction() {
                    return introduction;
                }

                public void setIntroduction(String introduction) {
                    this.introduction = introduction;
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

                public String getMeasurement_price() {
                    return measurement_price;
                }

                public void setMeasurement_price(String measurement_price) {
                    this.measurement_price = measurement_price;
                }

                public String getPersonal_honour() {
                    return personal_honour;
                }

                public void setPersonal_honour(String personal_honour) {
                    this.personal_honour = personal_honour;
                }

                public String getStudio() {
                    return studio;
                }

                public void setStudio(String studio) {
                    this.studio = studio;
                }

                public String getStyle_long_names() {
                    return style_long_names;
                }

                public void setStyle_long_names(String style_long_names) {
                    this.style_long_names = style_long_names;
                }

                public String getStyle_names() {
                    return style_names;
                }

                public void setStyle_names(String style_names) {
                    this.style_names = style_names;
                }

                public String getStyles() {
                    return styles;
                }

                public void setStyles(String styles) {
                    this.styles = styles;
                }

                public String getTheme_pic() {
                    return theme_pic;
                }

                public void setTheme_pic(String theme_pic) {
                    this.theme_pic = theme_pic;
                }
            }

            public static class RealNameEntity implements Serializable {
                private String audit_date;
                private String audit_status;
                private String auditor;
                private String auditor_opinion;
                private String birthday;
                private String certificate_no;
                private String certificate_type;
                private String mobile_number;
                private String photo_back_end;
                private String photo_front_end;
                private String photo_in_hand;
                private String real_name;

                public String getAudit_date() {
                    return audit_date;
                }

                public void setAudit_date(String audit_date) {
                    this.audit_date = audit_date;
                }

                public String getAudit_status() {
                    return audit_status;
                }

                public void setAudit_status(String audit_status) {
                    this.audit_status = audit_status;
                }

                public String getAuditor() {
                    return auditor;
                }

                public void setAuditor(String auditor) {
                    this.auditor = auditor;
                }

                public String getAuditor_opinion() {
                    return auditor_opinion;
                }

                public void setAuditor_opinion(String auditor_opinion) {
                    this.auditor_opinion = auditor_opinion;
                }

                public String getBirthday() {
                    return birthday;
                }

                public void setBirthday(String birthday) {
                    this.birthday = birthday;
                }

                public String getCertificate_no() {
                    return certificate_no;
                }

                public void setCertificate_no(String certificate_no) {
                    this.certificate_no = certificate_no;
                }

                public String getCertificate_type() {
                    return certificate_type;
                }

                public void setCertificate_type(String certificate_type) {
                    this.certificate_type = certificate_type;
                }

                public String getMobile_number() {
                    return mobile_number;
                }

                public void setMobile_number(String mobile_number) {
                    this.mobile_number = mobile_number;
                }

                public String getPhoto_back_end() {
                    return photo_back_end;
                }

                public void setPhoto_back_end(String photo_back_end) {
                    this.photo_back_end = photo_back_end;
                }

                public String getPhoto_front_end() {
                    return photo_front_end;
                }

                public void setPhoto_front_end(String photo_front_end) {
                    this.photo_front_end = photo_front_end;
                }

                public String getPhoto_in_hand() {
                    return photo_in_hand;
                }

                public void setPhoto_in_hand(String photo_in_hand) {
                    this.photo_in_hand = photo_in_hand;
                }

                public String getReal_name() {
                    return real_name;
                }

                public void setReal_name(String real_name) {
                    this.real_name = real_name;
                }
            }
        }
    }
}
