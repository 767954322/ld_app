package com.autodesk.shejijia.consumer.home.decorationdesigners.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 2016/2/27 0027 10:36 .
 * @file SeekDesignerBean  .
 * @brief 查看设计师数据bean.
 */
public class SeekDesignerBean implements Serializable {


    private List<DesignerListEntity> designer_list;

    public void setCount(int count) {
        this.count = count;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setDesigner_list(List<DesignerListEntity> designer_list) {
        this.designer_list = designer_list;
    }

    public int getCount() {
        return count;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public List<DesignerListEntity> getDesigner_list() {
        return designer_list;
    }

    public static class DesignerListEntity implements Serializable{
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
        private String hs_uid;
        private int is_order_sms;
        private int is_validated_by_mobile;
        private int member_id;
        private String mobile_number;
        private String nick_name;
        private String province;
        private RealNameEntity real_name;
        private String register_date;
        private String user_name;
        private String zip_code;

        private List<CasesListEntity> cases_list;

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

        public void setHs_uid(String hs_uid) {
            this.hs_uid = hs_uid;
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

        public void setCases_list(List<CasesListEntity> cases_list) {
            this.cases_list = cases_list;
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

        public String getHs_uid() {
            return hs_uid;
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

        public List<CasesListEntity> getCases_list() {
            return cases_list;
        }

        public static class DesignerEntity implements Serializable{
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

        public static class RealNameEntity implements Serializable{
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

        public static class CasesListEntity implements Serializable{
            private String case_id;
            private String city;
            private String community_name;
            private String custom_string_area;
            private String custom_string_status;
            private String custom_string_style;
            private String designer_id;

            private DesignerInfoEntity designer_info;
            private String district;
            private int favorite_count;
            private String hs_designer_uid;
            private int id;
            private double prj_base_price;
            private double prj_furniture_price;
            private double prj_hidden_price;
            private double prj_material_price;
            private double prj_other_price;
            private String project_style;
            private double protocol_price;
            private String province;
            private String room_area;
            private String thumbnails_ext;
            private String title;


            private List<ImagesEntity> images;

            public void setCase_id(String case_id) {
                this.case_id = case_id;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public void setCommunity_name(String community_name) {
                this.community_name = community_name;
            }

            public void setCustom_string_area(String custom_string_area) {
                this.custom_string_area = custom_string_area;
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

            public void setDesigner_info(DesignerInfoEntity designer_info) {
                this.designer_info = designer_info;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public void setFavorite_count(int favorite_count) {
                this.favorite_count = favorite_count;
            }

            public void setHs_designer_uid(String hs_designer_uid) {
                this.hs_designer_uid = hs_designer_uid;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setPrj_base_price(double prj_base_price) {
                this.prj_base_price = prj_base_price;
            }

            public void setPrj_furniture_price(double prj_furniture_price) {
                this.prj_furniture_price = prj_furniture_price;
            }

            public void setPrj_hidden_price(double prj_hidden_price) {
                this.prj_hidden_price = prj_hidden_price;
            }

            public void setPrj_material_price(double prj_material_price) {
                this.prj_material_price = prj_material_price;
            }

            public void setPrj_other_price(double prj_other_price) {
                this.prj_other_price = prj_other_price;
            }

            public void setProject_style(String project_style) {
                this.project_style = project_style;
            }

            public void setProtocol_price(double protocol_price) {
                this.protocol_price = protocol_price;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public void setRoom_area(String room_area) {
                this.room_area = room_area;
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

            public String getCase_id() {
                return case_id;
            }

            public String getCity() {
                return city;
            }

            public String getCommunity_name() {
                return community_name;
            }

            public String getCustom_string_area() {
                return custom_string_area;
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

            public DesignerInfoEntity getDesigner_info() {
                return designer_info;
            }

            public String getDistrict() {
                return district;
            }

            public int getFavorite_count() {
                return favorite_count;
            }

            public String getHs_designer_uid() {
                return hs_designer_uid;
            }

            public int getId() {
                return id;
            }

            public double getPrj_base_price() {
                return prj_base_price;
            }

            public double getPrj_furniture_price() {
                return prj_furniture_price;
            }

            public double getPrj_hidden_price() {
                return prj_hidden_price;
            }

            public double getPrj_material_price() {
                return prj_material_price;
            }

            public double getPrj_other_price() {
                return prj_other_price;
            }

            public String getProject_style() {
                return project_style;
            }

            public double getProtocol_price() {
                return protocol_price;
            }

            public String getProvince() {
                return province;
            }

            public String getRoom_area() {
                return room_area;
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

            public static class DesignerInfoEntity implements Serializable{
                private String avatar;
                private String birthday;
                private DesignerEntity designer;
                private String email;
                private String first_name;
                private int gender;
                private String hitachi_account;
                private int is_order_sms;
                private int is_validated_by_mobile;
                private int member_id;
                private String mobile_number;
                private String nick_name;
                private RealNameEntity real_name;
                private String register_date;
                private String user_name;
                private String zip_code;

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }

                public void setBirthday(String birthday) {
                    this.birthday = birthday;
                }

                public void setDesigner(DesignerEntity designer) {
                    this.designer = designer;
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

                public String getAvatar() {
                    return avatar;
                }

                public String getBirthday() {
                    return birthday;
                }

                public DesignerEntity getDesigner() {
                    return designer;
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

                public static class DesignerEntity implements Serializable{
                    private int case_count;
                    private double design_price_max;
                    private double design_price_min;
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

                    public void setCase_count(int case_count) {
                        this.case_count = case_count;
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

                    public int getCase_count() {
                        return case_count;
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

                public static class RealNameEntity implements Serializable{
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

            public static class ImagesEntity implements Serializable{
                private String file_id;
                private String file_name;
                private String file_url;
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

                public boolean isIs_primary() {
                    return is_primary;
                }
            }
        }
    }
    private int count;
    private int limit;
    private int offset;

}
