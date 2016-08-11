package com.autodesk.shejijia.consumer.home.decorationlibrarys.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 2016/3/4 0004 16:26 .
 * @file user HomeCaseDetailBean .
 * @brief 悬浮按钮案例 .
 */
public class CaseDetailBean implements Serializable {

    private String city;
    private String custom_string_area;
    private String custom_string_form;
    private String custom_string_keywords;
    private String custom_string_status;
    private String custom_string_style;
    private String designer_id;
    private String description;
    private DesignerInfoEntity designer_info;
    private String district;
    private String hs_designer_uid;
    private String id;
    private String prj_base_price;
    private String prj_furniture_price;
    private String prj_hidden_price;
    private String prj_material_price;
    private String prj_other_price;
    private String project_style;
    private String protocol_price;
    private String province;
    private String room_area;
    private String room_type;
    private String thumbnails_ext;
    private String title;

    public int getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(int favorite_count) {
        this.favorite_count = favorite_count;
    }

    private int favorite_count;
    private List<ImagesEntity> images;

    public void setCity(String city) {
        this.city = city;
    }

    public void setCustom_string_area(String custom_string_area) {
        this.custom_string_area = custom_string_area;
    }

    public void setCustom_string_form(String custom_string_form) {
        this.custom_string_form = custom_string_form;
    }

    public void setCustom_string_keywords(String custom_string_keywords) {
        this.custom_string_keywords = custom_string_keywords;
    }

    public void setCustom_string_status(String custom_string_status) {
        this.custom_string_status = custom_string_status;
    }

    public void setCustom_string_style(String custom_string_style) {
        this.custom_string_style = custom_string_style;
    }

    public void setDesigner_id(String designer_id) {
        this.designer_id = designer_id;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public void setDesigner_info(DesignerInfoEntity designer_info) {
        this.designer_info = designer_info;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setHs_designer_uid(String hs_designer_uid) {
        this.hs_designer_uid = hs_designer_uid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPrj_base_price(String prj_base_price) {
        this.prj_base_price = prj_base_price;
    }

    public void setPrj_furniture_price(String prj_furniture_price) {
        this.prj_furniture_price = prj_furniture_price;
    }

    public void setPrj_hidden_price(String prj_hidden_price) {
        this.prj_hidden_price = prj_hidden_price;
    }

    public void setPrj_material_price(String prj_material_price) {
        this.prj_material_price = prj_material_price;
    }

    public void setPrj_other_price(String prj_other_price) {
        this.prj_other_price = prj_other_price;
    }

    public void setProject_style(String project_style) {
        this.project_style = project_style;
    }

    public void setProtocol_price(String protocol_price) {
        this.protocol_price = protocol_price;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setRoom_area(String room_area) {
        this.room_area = room_area;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public void setThumbnails_ext(String thumbnails_ext) {
        this.thumbnails_ext = thumbnails_ext;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImages(List<ImagesEntity> images) {
        this.images = images;
    }

    public String getCity() {
        return city;
    }

    public String getCustom_string_area() {
        return custom_string_area;
    }

    public String getCustom_string_form() {
        return custom_string_form;
    }

    public String getCustom_string_keywords() {
        return custom_string_keywords;
    }

    public String getCustom_string_status() {
        return custom_string_status;
    }

    public String getCustom_string_style() {
        return custom_string_style;
    }

    public String getDesigner_id() {
        return designer_id;
    }

    public String getDescription() {
        return description;
    }

    public DesignerInfoEntity getDesigner_info() {
        return designer_info;
    }

    public String getDistrict() {
        return district;
    }

    public String getHs_designer_uid() {
        return hs_designer_uid;
    }

    public String getId() {
        return id;
    }

    public String getPrj_base_price() {
        return prj_base_price;
    }

    public String getPrj_furniture_price() {
        return prj_furniture_price;
    }

    public String getPrj_hidden_price() {
        return prj_hidden_price;
    }

    public String getPrj_material_price() {
        return prj_material_price;
    }

    public String getPrj_other_price() {
        return prj_other_price;
    }

    public String getProject_style() {
        return project_style;
    }

    public String getProtocol_price() {
        return protocol_price;
    }

    public String getProvince() {
        return province;
    }

    public String getRoom_area() {
        return room_area;
    }

    public String getRoom_type() {
        return room_type;
    }

    public String getThumbnails_ext() {
        return thumbnails_ext;
    }

    public String getTitle() {
        return title;
    }

    public List<ImagesEntity> getImages() {
        return images;
    }

    public static class DesignerInfoEntity implements Serializable {
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
        private String gender;
        private String hitachi_account;
        private String is_order_sms;
        private String is_validated_by_mobile;
        private String member_id;
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

        public void setGender(String gender) {
            this.gender = gender;
        }

        public void setHitachi_account(String hitachi_account) {
            this.hitachi_account = hitachi_account;
        }

        public void setIs_order_sms(String is_order_sms) {
            this.is_order_sms = is_order_sms;
        }

        public void setIs_validated_by_mobile(String is_validated_by_mobile) {
            this.is_validated_by_mobile = is_validated_by_mobile;
        }

        public void setMember_id(String member_id) {
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

        public String getGender() {
            return gender;
        }

        public String getHitachi_account() {
            return hitachi_account;
        }

        public String getIs_order_sms() {
            return is_order_sms;
        }

        public String getIs_validated_by_mobile() {
            return is_validated_by_mobile;
        }

        public String getMember_id() {
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

        public static class DesignerEntity implements Serializable {
            private String acs_member_id;
            private int case_count;
            private String design_price_max;
            private String design_price_min;
            private int experience;
            private String graduate_from;
            private String introduction;
            private String is_loho;
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

            public void setIs_loho(String is_loho) {
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

            public String getIs_loho() {
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
}
