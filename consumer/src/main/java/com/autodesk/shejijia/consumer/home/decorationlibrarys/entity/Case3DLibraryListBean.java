package com.autodesk.shejijia.consumer.home.decorationlibrarys.entity;

import java.util.List;

/**
 * Created by it on 16-8-24.
 */
public class Case3DLibraryListBean {

    /**
     * count : 156
     * date : 1472005079000
     * limit : 50
     * offset : 0
     * cases : [{"bedroom":null,"city":null,"conception":null,"district":null,"province":null,"restroom":null,"design_asset_id":1596041,"community_name":null,"custom_string_area":"one","custom_string_bedroom":null,"custom_string_form":"设计1","custom_string_name":"设计1","custom_string_restroom":null,"custom_string_style":null,"custom_string_type":null,"design_file":[{"id":null,"link":"https://sherpapreview-standard.s3.cn-north-1.amazonaws.com.cn/Preview/Beta/2016/08/19__06_24_36/0a6a05affa544021809b76fb5cbde091.jpgb48329d2-6687-11e6-942f-021e730a7faa","name":"mainImage","source":null,"status":null,"type":null,"extended_data":null,"source_id":null,"is_primary":true}],"design_name":"设计1","hs_designer_uid":"7db9447f-a024-4689-867b-1909ee16c04d","designer_info":{"designer":{"studio":null,"styles":null,"introduction":null,"experience":null,"acs_member_id":20730531,"style_names":null,"style_long_names":null,"measurement_price":null,"design_price_min":null,"design_price_max":null,"graduate_from":null,"personal_honour":null,"diy_count":null,"case_count":null,"is_loho":null,"is_real_name":null,"theme_pic":null,"type_code":null,"type_name":null,"store_id":null,"store_name":null,"company_id":null,"company_name":null,"design_price_code":null,"evalution_avg_scores":0,"evalution_count":null},"email":null,"avatar":"https://sherpapreview-standard.s3.cn-north-1.amazonaws.com.cn/Preview/Beta/2016/08/23__10_37_36/57b87127ed502bce12abfcfc.img1a2dfeb4-e46a-4ee3-a90f-aa2f51d4a6d2.jpg","gender":null,"birthday":null,"province":null,"city":null,"district":null,"address":null,"member_id":null,"hs_uid":"7db9447f-a024-4689-867b-1909ee16c04d","first_name":null,"last_name":null,"home_phone":null,"zip_code":null,"hitachi_account":null,"user_name":null,"register_date":null,"mobile_number":null,"city_name":null,"province_name":null,"district_name":null,"is_order_sms":null,"nick_name":"UAT设计师44","is_validated_by_mobile":null,"is_email_binding":null,"has_secreted":null,"is_following":null,"following_count":null},"designer_id":20730531,"hs_design_id":"a8a9935b-0acf-4f9e-a252-a209441b954d","project_style":null,"project_type":null,"publish_status":1,"room_area":17,"room_type":null,"favorite_count":0}]
     * _link : null
     */

    private int count;
    private long date;
    private int limit;
    private int offset;
    private String _link;
    /**
     * bedroom : null
     * city : null
     * conception : null
     * district : null
     * province : null
     * restroom : null
     * design_asset_id : 1596041
     * community_name : null
     * custom_string_area : one
     * custom_string_bedroom : null
     * custom_string_form : 设计1
     * custom_string_name : 设计1
     * custom_string_restroom : null
     * custom_string_style : null
     * custom_string_type : null
     * design_file : [{"id":null,"link":"https://sherpapreview-standard.s3.cn-north-1.amazonaws.com.cn/Preview/Beta/2016/08/19__06_24_36/0a6a05affa544021809b76fb5cbde091.jpgb48329d2-6687-11e6-942f-021e730a7faa","name":"mainImage","source":null,"status":null,"type":null,"extended_data":null,"source_id":null,"is_primary":true}]
     * design_name : 设计1
     * hs_designer_uid : 7db9447f-a024-4689-867b-1909ee16c04d
     * designer_info : {"designer":{"studio":null,"styles":null,"introduction":null,"experience":null,"acs_member_id":20730531,"style_names":null,"style_long_names":null,"measurement_price":null,"design_price_min":null,"design_price_max":null,"graduate_from":null,"personal_honour":null,"diy_count":null,"case_count":null,"is_loho":null,"is_real_name":null,"theme_pic":null,"type_code":null,"type_name":null,"store_id":null,"store_name":null,"company_id":null,"company_name":null,"design_price_code":null,"evalution_avg_scores":0,"evalution_count":null},"email":null,"avatar":"https://sherpapreview-standard.s3.cn-north-1.amazonaws.com.cn/Preview/Beta/2016/08/23__10_37_36/57b87127ed502bce12abfcfc.img1a2dfeb4-e46a-4ee3-a90f-aa2f51d4a6d2.jpg","gender":null,"birthday":null,"province":null,"city":null,"district":null,"address":null,"member_id":null,"hs_uid":"7db9447f-a024-4689-867b-1909ee16c04d","first_name":null,"last_name":null,"home_phone":null,"zip_code":null,"hitachi_account":null,"user_name":null,"register_date":null,"mobile_number":null,"city_name":null,"province_name":null,"district_name":null,"is_order_sms":null,"nick_name":"UAT设计师44","is_validated_by_mobile":null,"is_email_binding":null,"has_secreted":null,"is_following":null,"following_count":null}
     * designer_id : 20730531
     * hs_design_id : a8a9935b-0acf-4f9e-a252-a209441b954d
     * project_style : null
     * project_type : null
     * publish_status : 1
     * room_area : 17
     * room_type : null
     * favorite_count : 0
     */

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

    public String get_link() {
        return _link;
    }

    public void set_link(String _link) {
        this._link = _link;
    }

    public List<CasesBean> getCases() {
        return cases;
    }

    public void setCases(List<CasesBean> cases) {
        this.cases = cases;
    }

    public static class CasesBean {
        private String bedroom;
        private String city;
        private String conception;
        private String district;
        private String province;
        private String restroom;
        private String design_asset_id;
        private String community_name;
        private String custom_string_area;
        private String custom_string_bedroom;
        private String custom_string_form;
        private String custom_string_name;
        private String custom_string_restroom;
        private String custom_string_style;
        private String custom_string_type;
        private String design_name;
        private String hs_designer_uid;

        public String getOriginal_avatar() {
            return original_avatar;
        }

        public void setOriginal_avatar(String original_avatar) {
            this.original_avatar = original_avatar;
        }

        private String original_avatar;

        /**
         * designer : {"studio":null,"styles":null,"introduction":null,"experience":null,"acs_member_id":20730531,"style_names":null,"style_long_names":null,"measurement_price":null,"design_price_min":null,"design_price_max":null,"graduate_from":null,"personal_honour":null,"diy_count":null,"case_count":null,"is_loho":null,"is_real_name":null,"theme_pic":null,"type_code":null,"type_name":null,"store_id":null,"store_name":null,"company_id":null,"company_name":null,"design_price_code":null,"evalution_avg_scores":0,"evalution_count":null}
         * email : null
         * avatar : https://sherpapreview-standard.s3.cn-north-1.amazonaws.com.cn/Preview/Beta/2016/08/23__10_37_36/57b87127ed502bce12abfcfc.img1a2dfeb4-e46a-4ee3-a90f-aa2f51d4a6d2.jpg
         * gender : null
         * birthday : null
         * province : null
         * city : null
         * district : null
         * address : null
         * member_id : null
         * hs_uid : 7db9447f-a024-4689-867b-1909ee16c04d
         * first_name : null
         * last_name : null
         * home_phone : null
         * zip_code : null
         * hitachi_account : null
         * user_name : null
         * register_date : null
         * mobile_number : null
         * city_name : null
         * province_name : null
         * district_name : null
         * is_order_sms : null
         * nick_name : UAT设计师44
         * is_validated_by_mobile : null
         * is_email_binding : null
         * has_secreted : null
         * is_following : null
         * following_count : null
         */

        private DesignerInfoBean designer_info;
        private int designer_id;
        private String hs_design_id;
        private String project_style;
        private String project_type;
        private int publish_status;
        private String room_area;
        private String room_type;
        private String favorite_count;
        /**
         * id : null
         * link : https://sherpapreview-standard.s3.cn-north-1.amazonaws.com.cn/Preview/Beta/2016/08/19__06_24_36/0a6a05affa544021809b76fb5cbde091.jpgb48329d2-6687-11e6-942f-021e730a7faa
         * name : mainImage
         * source : null
         * status : null
         * type : null
         * extended_data : null
         * source_id : null
         * is_primary : true
         */

        private List<DesignFileBean> design_file;

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

        public String getConception() {
            return conception;
        }

        public void setConception(String conception) {
            this.conception = conception;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
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

        public String getDesign_asset_id() {
            return design_asset_id;
        }

        public void setDesign_asset_id(String design_asset_id) {
            this.design_asset_id = design_asset_id;
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

        public String getCustom_string_name() {
            return custom_string_name;
        }

        public void setCustom_string_name(String custom_string_name) {
            this.custom_string_name = custom_string_name;
        }

        public String getCustom_string_restroom() {
            return custom_string_restroom;
        }

        public void setCustom_string_restroom(String custom_string_restroom) {
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

        public String getHs_designer_uid() {
            return hs_designer_uid;
        }

        public void setHs_designer_uid(String hs_designer_uid) {
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

        public String getHs_design_id() {
            return hs_design_id;
        }

        public void setHs_design_id(String hs_design_id) {
            this.hs_design_id = hs_design_id;
        }

        public String getProject_style() {
            return project_style;
        }

        public void setProject_style(String project_style) {
            this.project_style = project_style;
        }

        public String getProject_type() {
            return project_type;
        }

        public void setProject_type(String project_type) {
            this.project_type = project_type;
        }

        public int getPublish_status() {
            return publish_status;
        }

        public void setPublish_status(int publish_status) {
            this.publish_status = publish_status;
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

        public String getFavorite_count() {
            return favorite_count;
        }

        public void setFavorite_count(String favorite_count) {
            this.favorite_count = favorite_count;
        }

        public List<DesignFileBean> getDesign_file() {
            return design_file;
        }

        public void setDesign_file(List<DesignFileBean> design_file) {
            this.design_file = design_file;
        }

        public static class DesignerInfoBean {
            /**
             * studio : null
             * styles : null
             * introduction : null
             * experience : null
             * acs_member_id : 20730531
             * style_names : null
             * style_long_names : null
             * measurement_price : null
             * design_price_min : null
             * design_price_max : null
             * graduate_from : null
             * personal_honour : null
             * diy_count : null
             * case_count : null
             * is_loho : null
             * is_real_name : null
             * theme_pic : null
             * type_code : null
             * type_name : null
             * store_id : null
             * store_name : null
             * company_id : null
             * company_name : null
             * design_price_code : null
             * evalution_avg_scores : 0
             * evalution_count : null
             */

            private DesignerBean designer;
            private String email;
            private String avatar;
            private String gender;
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
            private String is_order_sms;
            private String nick_name;
            private String is_validated_by_mobile;
            private String is_email_binding;
            private String has_secreted;
            private boolean is_following;
            private String following_count;

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

            public boolean getIs_following() {
                return is_following;
            }

            public void setIs_following(boolean is_following) {
                this.is_following = is_following;
            }

            public String getFollowing_count() {
                return following_count;
            }

            public void setFollowing_count(String following_count) {
                this.following_count = following_count;
            }

            public static class DesignerBean {
                private String studio;
                private String styles;
                private String introduction;
                private String experience;
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
                private String is_loho;
                private String is_real_name;
                private String theme_pic;
                private String type_code;
                private String type_name;
                private String store_id;
                private String store_name;
                private String company_id;
                private String company_name;
                private String design_price_code;
                private int evalution_avg_scores;
                private String evalution_count;

                public String getStudio() {
                    return studio;
                }

                public void setStudio(String studio) {
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

                public String getExperience() {
                    return experience;
                }

                public void setExperience(String experience) {
                    this.experience = experience;
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

                public String getIs_loho() {
                    return is_loho;
                }

                public void setIs_loho(String is_loho) {
                    this.is_loho = is_loho;
                }

                public String getIs_real_name() {
                    return is_real_name;
                }

                public void setIs_real_name(String is_real_name) {
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

                public int getEvalution_avg_scores() {
                    return evalution_avg_scores;
                }

                public void setEvalution_avg_scores(int evalution_avg_scores) {
                    this.evalution_avg_scores = evalution_avg_scores;
                }

                public String getEvalution_count() {
                    return evalution_count;
                }

                public void setEvalution_count(String evalution_count) {
                    this.evalution_count = evalution_count;
                }
            }
        }

        public static class DesignFileBean {
            private String id;
            private String link;
            private String name;
            private String source;
            private String status;
            private String type;
            private String extended_data;
            private String source_id;
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

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getExtended_data() {
                return extended_data;
            }

            public void setExtended_data(String extended_data) {
                this.extended_data = extended_data;
            }

            public String getSource_id() {
                return source_id;
            }

            public void setSource_id(String source_id) {
                this.source_id = source_id;
            }

            public boolean isIs_primary() {
                return is_primary;
            }

            public void setIs_primary(boolean is_primary) {
                this.is_primary = is_primary;
            }
        }
    }
}
