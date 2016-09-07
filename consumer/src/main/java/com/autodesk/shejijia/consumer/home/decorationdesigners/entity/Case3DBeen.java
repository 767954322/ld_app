package com.autodesk.shejijia.consumer.home.decorationdesigners.entity;

import com.autodesk.shejijia.consumer.codecorationBase.grandmaster.entity.Designer;

import java.util.List;

/**
 * Created by yaoxuehua on 16-9-6.
 */
public class Case3DBeen {

    private int count;
    private long date;
    private int limit;
    private int offset;
    private Object _link;

    private List<CasesBean> cases;

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

    public Object get_link() {
        return _link;
    }

    public void set_link(Object _link) {
        this._link = _link;
    }

    public List<CasesBean> getCases() {
        return cases;
    }

    public void setCases(List<CasesBean> cases) {
        this.cases = cases;
    }

    public static class CasesBean {
        private Object bedroom;
        private String city;
        private Object conception;
        private Object district;
        private Object province;
        private Object restroom;
        private Object sort;
        private int design_asset_id;
        private Object community_name;
        private String custom_string_area;
        private Object custom_string_bedroom;
        private Object custom_string_form;
        private Object custom_string_name;
        private Object custom_string_restroom;
        private String custom_string_style;
        private String custom_string_type;
        private String design_name;
        private Object hs_designer_uid;

        private DesignerInfoBean designer_info;
        private int designer_id;
        private Object hs_design_id;
        private Object project_style;
        private Object project_type;
        private Object publish_status;
        private int room_area;
        private Object room_type;
        private int favorite_count;
        private Object custom_string_is_storage;
        private Object custom_string_sort_type;
        private Object custom_string_publish_status;
        private Object custom_string_keywords;
        private Object original_avatar;
        private List<DesignerFileBeen> design_file;

        public Object getBedroom() {
            return bedroom;
        }

        public void setBedroom(Object bedroom) {
            this.bedroom = bedroom;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public Object getConception() {
            return conception;
        }

        public void setConception(Object conception) {
            this.conception = conception;
        }

        public Object getDistrict() {
            return district;
        }

        public void setDistrict(Object district) {
            this.district = district;
        }

        public Object getProvince() {
            return province;
        }

        public void setProvince(Object province) {
            this.province = province;
        }

        public Object getRestroom() {
            return restroom;
        }

        public void setRestroom(Object restroom) {
            this.restroom = restroom;
        }

        public Object getSort() {
            return sort;
        }

        public void setSort(Object sort) {
            this.sort = sort;
        }

        public int getDesign_asset_id() {
            return design_asset_id;
        }

        public void setDesign_asset_id(int design_asset_id) {
            this.design_asset_id = design_asset_id;
        }

        public Object getCommunity_name() {
            return community_name;
        }

        public void setCommunity_name(Object community_name) {
            this.community_name = community_name;
        }

        public String getCustom_string_area() {
            return custom_string_area;
        }

        public void setCustom_string_area(String custom_string_area) {
            this.custom_string_area = custom_string_area;
        }

        public Object getCustom_string_bedroom() {
            return custom_string_bedroom;
        }

        public void setCustom_string_bedroom(Object custom_string_bedroom) {
            this.custom_string_bedroom = custom_string_bedroom;
        }

        public Object getCustom_string_form() {
            return custom_string_form;
        }

        public void setCustom_string_form(Object custom_string_form) {
            this.custom_string_form = custom_string_form;
        }

        public Object getCustom_string_name() {
            return custom_string_name;
        }

        public void setCustom_string_name(Object custom_string_name) {
            this.custom_string_name = custom_string_name;
        }

        public Object getCustom_string_restroom() {
            return custom_string_restroom;
        }

        public void setCustom_string_restroom(Object custom_string_restroom) {
            this.custom_string_restroom = custom_string_restroom;
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

        public String getDesign_name() {
            return design_name;
        }

        public void setDesign_name(String design_name) {
            this.design_name = design_name;
        }

        public Object getHs_designer_uid() {
            return hs_designer_uid;
        }

        public void setHs_designer_uid(Object hs_designer_uid) {
            this.hs_designer_uid = hs_designer_uid;
        }

        public DesignerInfoBean getDesigner_info() {
            return designer_info;
        }

        public void setDesigner_info(DesignerInfoBean designer_info) {
            this.designer_info = designer_info;
        }

        public int getDesigner_id() {
            return designer_id;
        }

        public void setDesigner_id(int designer_id) {
            this.designer_id = designer_id;
        }

        public Object getHs_design_id() {
            return hs_design_id;
        }

        public void setHs_design_id(Object hs_design_id) {
            this.hs_design_id = hs_design_id;
        }

        public Object getProject_style() {
            return project_style;
        }

        public void setProject_style(Object project_style) {
            this.project_style = project_style;
        }

        public Object getProject_type() {
            return project_type;
        }

        public void setProject_type(Object project_type) {
            this.project_type = project_type;
        }

        public Object getPublish_status() {
            return publish_status;
        }

        public void setPublish_status(Object publish_status) {
            this.publish_status = publish_status;
        }

        public int getRoom_area() {
            return room_area;
        }

        public void setRoom_area(int room_area) {
            this.room_area = room_area;
        }

        public Object getRoom_type() {
            return room_type;
        }

        public void setRoom_type(Object room_type) {
            this.room_type = room_type;
        }

        public int getFavorite_count() {
            return favorite_count;
        }

        public void setFavorite_count(int favorite_count) {
            this.favorite_count = favorite_count;
        }

        public Object getCustom_string_is_storage() {
            return custom_string_is_storage;
        }

        public void setCustom_string_is_storage(Object custom_string_is_storage) {
            this.custom_string_is_storage = custom_string_is_storage;
        }

        public Object getCustom_string_sort_type() {
            return custom_string_sort_type;
        }

        public void setCustom_string_sort_type(Object custom_string_sort_type) {
            this.custom_string_sort_type = custom_string_sort_type;
        }

        public Object getCustom_string_publish_status() {
            return custom_string_publish_status;
        }

        public void setCustom_string_publish_status(Object custom_string_publish_status) {
            this.custom_string_publish_status = custom_string_publish_status;
        }

        public Object getCustom_string_keywords() {
            return custom_string_keywords;
        }

        public void setCustom_string_keywords(Object custom_string_keywords) {
            this.custom_string_keywords = custom_string_keywords;
        }

        public Object getOriginal_avatar() {
            return original_avatar;
        }

        public void setOriginal_avatar(Object original_avatar) {
            this.original_avatar = original_avatar;
        }

        public List<DesignerFileBeen> getDesign_file() {
            return design_file;
        }

        public void setDesign_file(List<DesignerFileBeen> design_file) {
            this.design_file = design_file;
        }


        public static class DesignerFileBeen{

            private String id;
            private String link;
            private String name;
            private Object source;
            private Object status;
            private Object type;
            private Object extended_data;
            private Object source_id;
            private boolean is_primary;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getSource() {
                return source;
            }

            public void setSource(Object source) {
                this.source = source;
            }

            public Object getStatus() {
                return status;
            }

            public void setStatus(Object status) {
                this.status = status;
            }

            public Object getType() {
                return type;
            }

            public void setType(Object type) {
                this.type = type;
            }

            public Object getExtended_data() {
                return extended_data;
            }

            public void setExtended_data(Object extended_data) {
                this.extended_data = extended_data;
            }

            public Object getSource_id() {
                return source_id;
            }

            public void setSource_id(Object source_id) {
                this.source_id = source_id;
            }

            public boolean is_primary() {
                return is_primary;
            }

            public void setIs_primary(boolean is_primary) {
                this.is_primary = is_primary;
            }

            @Override
            public String toString() {
                return "DesignerFileBeen{" +
                        "id='" + id + '\'' +
                        ", link='" + link + '\'' +
                        ", name='" + name + '\'' +
                        ", source=" + source +
                        ", status=" + status +
                        ", type=" + type +
                        ", extended_data=" + extended_data +
                        ", source_id=" + source_id +
                        ", is_primary=" + is_primary +
                        '}';
            }
        }



        public static class DesignerInfoBean {

            private DesignerBean designer;
            private Object email;
            private String avatar;
            private Object gender;
            private Object birthday;
            private Object province;
            private Object city;
            private Object district;
            private Object address;
            private Object follows;
            private Object member_id;
            private Object hs_uid;
            private Object first_name;
            private Object last_name;
            private Object home_phone;
            private Object zip_code;
            private Object hitachi_account;
            private Object user_name;
            private Object register_date;
            private Object mobile_number;
            private Object city_name;
            private Object province_name;
            private Object district_name;
            private Object is_order_sms;
            private String nick_name;
            private Object is_validated_by_mobile;
            private Object is_email_binding;
            private Object has_secreted;
            private Object is_following;

            public DesignerBean getDesigner() {
                return designer;
            }

            public void setDesigner(DesignerBean designer) {
                this.designer = designer;
            }

            public Object getEmail() {
                return email;
            }

            public void setEmail(Object email) {
                this.email = email;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public Object getGender() {
                return gender;
            }

            public void setGender(Object gender) {
                this.gender = gender;
            }

            public Object getBirthday() {
                return birthday;
            }

            public void setBirthday(Object birthday) {
                this.birthday = birthday;
            }

            public Object getProvince() {
                return province;
            }

            public void setProvince(Object province) {
                this.province = province;
            }

            public Object getCity() {
                return city;
            }

            public void setCity(Object city) {
                this.city = city;
            }

            public Object getDistrict() {
                return district;
            }

            public void setDistrict(Object district) {
                this.district = district;
            }

            public Object getAddress() {
                return address;
            }

            public void setAddress(Object address) {
                this.address = address;
            }

            public Object getFollows() {
                return follows;
            }

            public void setFollows(Object follows) {
                this.follows = follows;
            }

            public Object getMember_id() {
                return member_id;
            }

            public void setMember_id(Object member_id) {
                this.member_id = member_id;
            }

            public Object getHs_uid() {
                return hs_uid;
            }

            public void setHs_uid(Object hs_uid) {
                this.hs_uid = hs_uid;
            }

            public Object getFirst_name() {
                return first_name;
            }

            public void setFirst_name(Object first_name) {
                this.first_name = first_name;
            }

            public Object getLast_name() {
                return last_name;
            }

            public void setLast_name(Object last_name) {
                this.last_name = last_name;
            }

            public Object getHome_phone() {
                return home_phone;
            }

            public void setHome_phone(Object home_phone) {
                this.home_phone = home_phone;
            }

            public Object getZip_code() {
                return zip_code;
            }

            public void setZip_code(Object zip_code) {
                this.zip_code = zip_code;
            }

            public Object getHitachi_account() {
                return hitachi_account;
            }

            public void setHitachi_account(Object hitachi_account) {
                this.hitachi_account = hitachi_account;
            }

            public Object getUser_name() {
                return user_name;
            }

            public void setUser_name(Object user_name) {
                this.user_name = user_name;
            }

            public Object getRegister_date() {
                return register_date;
            }

            public void setRegister_date(Object register_date) {
                this.register_date = register_date;
            }

            public Object getMobile_number() {
                return mobile_number;
            }

            public void setMobile_number(Object mobile_number) {
                this.mobile_number = mobile_number;
            }

            public Object getCity_name() {
                return city_name;
            }

            public void setCity_name(Object city_name) {
                this.city_name = city_name;
            }

            public Object getProvince_name() {
                return province_name;
            }

            public void setProvince_name(Object province_name) {
                this.province_name = province_name;
            }

            public Object getDistrict_name() {
                return district_name;
            }

            public void setDistrict_name(Object district_name) {
                this.district_name = district_name;
            }

            public Object getIs_order_sms() {
                return is_order_sms;
            }

            public void setIs_order_sms(Object is_order_sms) {
                this.is_order_sms = is_order_sms;
            }

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }

            public Object getIs_validated_by_mobile() {
                return is_validated_by_mobile;
            }

            public void setIs_validated_by_mobile(Object is_validated_by_mobile) {
                this.is_validated_by_mobile = is_validated_by_mobile;
            }

            public Object getIs_email_binding() {
                return is_email_binding;
            }

            public void setIs_email_binding(Object is_email_binding) {
                this.is_email_binding = is_email_binding;
            }

            public Object getHas_secreted() {
                return has_secreted;
            }

            public void setHas_secreted(Object has_secreted) {
                this.has_secreted = has_secreted;
            }

            public Object getIs_following() {
                return is_following;
            }

            public void setIs_following(Object is_following) {
                this.is_following = is_following;
            }

            public static class DesignerBean {
                private Object studio;
                private Object styles;
                private Object introduction;
                private Object experience;
                private int acs_member_id;
                private Object style_names;
                private Object style_long_names;
                private Object measurement_price;
                private Object design_price_min;
                private Object design_price_max;
                private Object graduate_from;
                private Object personal_honour;
                private Object diy_count;
                private Object case_count;
                private Object is_loho;
                private Object is_real_name;
                private Object theme_pic;
                private Object type_code;
                private Object type_name;
                private Object store_id;
                private Object store_name;
                private Object company_id;
                private Object company_name;
                private Object design_price_code;
                private int evalution_avg_scores;
                private Object evalution_count;

                public Object getStudio() {
                    return studio;
                }

                public void setStudio(Object studio) {
                    this.studio = studio;
                }

                public Object getStyles() {
                    return styles;
                }

                public void setStyles(Object styles) {
                    this.styles = styles;
                }

                public Object getIntroduction() {
                    return introduction;
                }

                public void setIntroduction(Object introduction) {
                    this.introduction = introduction;
                }

                public Object getExperience() {
                    return experience;
                }

                public void setExperience(Object experience) {
                    this.experience = experience;
                }

                public int getAcs_member_id() {
                    return acs_member_id;
                }

                public void setAcs_member_id(int acs_member_id) {
                    this.acs_member_id = acs_member_id;
                }

                public Object getStyle_names() {
                    return style_names;
                }

                public void setStyle_names(Object style_names) {
                    this.style_names = style_names;
                }

                public Object getStyle_long_names() {
                    return style_long_names;
                }

                public void setStyle_long_names(Object style_long_names) {
                    this.style_long_names = style_long_names;
                }

                public Object getMeasurement_price() {
                    return measurement_price;
                }

                public void setMeasurement_price(Object measurement_price) {
                    this.measurement_price = measurement_price;
                }

                public Object getDesign_price_min() {
                    return design_price_min;
                }

                public void setDesign_price_min(Object design_price_min) {
                    this.design_price_min = design_price_min;
                }

                public Object getDesign_price_max() {
                    return design_price_max;
                }

                public void setDesign_price_max(Object design_price_max) {
                    this.design_price_max = design_price_max;
                }

                public Object getGraduate_from() {
                    return graduate_from;
                }

                public void setGraduate_from(Object graduate_from) {
                    this.graduate_from = graduate_from;
                }

                public Object getPersonal_honour() {
                    return personal_honour;
                }

                public void setPersonal_honour(Object personal_honour) {
                    this.personal_honour = personal_honour;
                }

                public Object getDiy_count() {
                    return diy_count;
                }

                public void setDiy_count(Object diy_count) {
                    this.diy_count = diy_count;
                }

                public Object getCase_count() {
                    return case_count;
                }

                public void setCase_count(Object case_count) {
                    this.case_count = case_count;
                }

                public Object getIs_loho() {
                    return is_loho;
                }

                public void setIs_loho(Object is_loho) {
                    this.is_loho = is_loho;
                }

                public Object getIs_real_name() {
                    return is_real_name;
                }

                public void setIs_real_name(Object is_real_name) {
                    this.is_real_name = is_real_name;
                }

                public Object getTheme_pic() {
                    return theme_pic;
                }

                public void setTheme_pic(Object theme_pic) {
                    this.theme_pic = theme_pic;
                }

                public Object getType_code() {
                    return type_code;
                }

                public void setType_code(Object type_code) {
                    this.type_code = type_code;
                }

                public Object getType_name() {
                    return type_name;
                }

                public void setType_name(Object type_name) {
                    this.type_name = type_name;
                }

                public Object getStore_id() {
                    return store_id;
                }

                public void setStore_id(Object store_id) {
                    this.store_id = store_id;
                }

                public Object getStore_name() {
                    return store_name;
                }

                public void setStore_name(Object store_name) {
                    this.store_name = store_name;
                }

                public Object getCompany_id() {
                    return company_id;
                }

                public void setCompany_id(Object company_id) {
                    this.company_id = company_id;
                }

                public Object getCompany_name() {
                    return company_name;
                }

                public void setCompany_name(Object company_name) {
                    this.company_name = company_name;
                }

                public Object getDesign_price_code() {
                    return design_price_code;
                }

                public void setDesign_price_code(Object design_price_code) {
                    this.design_price_code = design_price_code;
                }

                public int getEvalution_avg_scores() {
                    return evalution_avg_scores;
                }

                public void setEvalution_avg_scores(int evalution_avg_scores) {
                    this.evalution_avg_scores = evalution_avg_scores;
                }

                public Object getEvalution_count() {
                    return evalution_count;
                }

                public void setEvalution_count(Object evalution_count) {
                    this.evalution_count = evalution_count;
                }
            }
        }
    }
}
