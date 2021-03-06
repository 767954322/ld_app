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
    private int count;
    private int limit;
    private int offset;

    public List<DesignerListEntity> getDesigner_list() {
        return designer_list;
    }

    public static class DesignerListEntity implements Serializable {
        private String address;
        private String avatar;
        private String birthday;
        private String city;

        private String district;
        private String email;
        private String first_name;
        private String gender;
        private String hitachi_account;
        private String hs_uid;
        private String is_order_sms;
        private String is_validated_by_mobile;
        private String member_id;
        private String mobile_number;
        private String nick_name;
        private String province;
        private String register_date;
        private String user_name;
        private String zip_code;

        private DesignerInfoBean designer;
        private RealNameBean real_name;
        private List<CasesListEntity> cases_list;

        private String last_name;
        private String home_phone;
        private String city_name;
        private String province_name;
        private String district_name;
        private String is_email_binding;
        private String has_secreted;
        public boolean is_following;
        private String evalution_avg_scores;
        private String evalution_count;
        public String following_count;

        /**
         * 筛选新增字段
         */
        private SortBean sort;
        private String follows;
        private String region;
        private String seq;
        private String followingCount;

        public void setFollows(String follows) {
            this.follows = follows;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getSeq() {
            return seq;
        }

        public void setSeq(String seq) {
            this.seq = seq;
        }

        public SortBean getSort() {
            return sort;
        }

        public void setSort(SortBean sort) {
            this.sort = sort;
        }

        public String getFollowingCount() {
            return followingCount;
        }

        public void setFollowingCount(String followingCount) {
            this.followingCount = followingCount;
        }

        public String getFollows() {
            return follows;
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

        public String getFollowing_count() {
            return following_count;
        }

        public void setFollowing_count(String following_count) {
            this.following_count = following_count;
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

        public DesignerInfoBean getDesigner() {
            return designer;
        }

        public void setDesigner(DesignerInfoBean designer) {
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


        public void setMobile_number(String mobile_number) {
            this.mobile_number = mobile_number;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public RealNameBean getReal_name() {
            return real_name;
        }

        public void setReal_name(RealNameBean real_name) {
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

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public void setHitachi_account(String hitachi_account) {
            this.hitachi_account = hitachi_account;
        }

        public void setHs_uid(String hs_uid) {
            this.hs_uid = hs_uid;
        }

        public String getIs_order_sms() {
            return is_order_sms;
        }

        public void setIs_order_sms(String is_order_sms) {
            this.is_order_sms = is_order_sms;
        }

        public String getIs_validated_by_mobile() {
            return is_validated_by_mobile;
        }

        public void setIs_validated_by_mobile(String is_validated_by_mobile) {
            this.is_validated_by_mobile = is_validated_by_mobile;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
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


        public String getHitachi_account() {
            return hitachi_account;
        }

        public String getHs_uid() {
            return hs_uid;
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

        public static class CasesListEntity implements Serializable {
            private String case_id;
            private String city;
            private String community_name;
            private String custom_string_area;
            private String custom_string_status;
            private String custom_string_style;
            private String designer_id;

            private String district;
            private String favorite_count;
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
            private String thumbnails_ext;
            private String title;
            private DesignerInfoEntity designer_info;
            private List<ImagesBean> images;

            private String bedroom;
            private String description;
            private String restroom;
            private String weight;
            private String audit_desc;
            private String case_color;
            private String case_type;
            private String city_name;
            private String click_number;
            private String create_date;
            private String decoration_type;
            private String district_name;
            private String house_type;
            private String is_recommended;
            private String prj_price;
            private String province_name;
            private String room_type;
            private String search_tag;

            public String getCase_id() {
                return case_id;
            }

            public void setCase_id(String case_id) {
                this.case_id = case_id;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCommunity_name() {
                return community_name;
            }

            public void setCommunity_name(String community_name) {
                this.community_name = community_name;
            }

            public String getCustom_string_area() {
                return custom_string_area;
            }

            public void setCustom_string_area(String custom_string_area) {
                this.custom_string_area = custom_string_area;
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

            public String getDesigner_id() {
                return designer_id;
            }

            public void setDesigner_id(String designer_id) {
                this.designer_id = designer_id;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getFavorite_count() {
                return favorite_count;
            }

            public void setFavorite_count(String favorite_count) {
                this.favorite_count = favorite_count;
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

            public String getRoom_area() {
                return room_area;
            }

            public void setRoom_area(String room_area) {
                this.room_area = room_area;
            }

            public String getThumbnails_ext() {
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

            public DesignerInfoEntity getDesigner_info() {
                return designer_info;
            }

            public void setDesigner_info(DesignerInfoEntity designer_info) {
                this.designer_info = designer_info;
            }

            public List<ImagesBean> getImages() {
                return images;
            }

            public void setImages(List<ImagesBean> images) {
                this.images = images;
            }

            public String getBedroom() {
                return bedroom;
            }

            public void setBedroom(String bedroom) {
                this.bedroom = bedroom;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getRestroom() {
                return restroom;
            }

            public void setRestroom(String restroom) {
                this.restroom = restroom;
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

            public String getCreate_date() {
                return create_date;
            }

            public void setCreate_date(String create_date) {
                this.create_date = create_date;
            }

            public String getDecoration_type() {
                return decoration_type;
            }

            public void setDecoration_type(String decoration_type) {
                this.decoration_type = decoration_type;
            }

            public String getDistrict_name() {
                return district_name;
            }

            public void setDistrict_name(String district_name) {
                this.district_name = district_name;
            }

            public String getHouse_type() {
                return house_type;
            }

            public void setHouse_type(String house_type) {
                this.house_type = house_type;
            }

            public String getIs_recommended() {
                return is_recommended;
            }

            public void setIs_recommended(String is_recommended) {
                this.is_recommended = is_recommended;
            }

            public String getPrj_price() {
                return prj_price;
            }

            public void setPrj_price(String prj_price) {
                this.prj_price = prj_price;
            }

            public String getProvince_name() {
                return province_name;
            }

            public void setProvince_name(String province_name) {
                this.province_name = province_name;
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

            public static class DesignerInfoEntity implements Serializable {
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

                public static class DesignerEntity implements Serializable {
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

        }
    }


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


    public static class SortBean implements Serializable {
        String region;
        String seq;

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getSeq() {
            return seq;
        }

        public void setSeq(String seq) {
            this.seq = seq;
        }
    }
}
