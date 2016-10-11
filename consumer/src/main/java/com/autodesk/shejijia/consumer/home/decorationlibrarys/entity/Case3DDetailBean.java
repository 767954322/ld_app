package com.autodesk.shejijia.consumer.home.decorationlibrarys.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 16-8-22
 * @file Case3DDetailBean.java  .
 * @brief 3D案例详情 .
 */
public class Case3DDetailBean implements Serializable {

    /**
     * bedroom : four
     * city : null
     * conception : null
     * district : null
     * province : null
     * restroom : three
     * design_asset_id : 1589057
     * community_name : æµè¯
     * custom_string_area : one
     * custom_string_bedroom : four
     * custom_string_form : cesh
     * custom_string_name : cesh
     * custom_string_restroom : three
     * custom_string_style : null
     * custom_string_type : four
     * design_file : [{"id":"18131564","link":"https://sherpapreview-standard.s3.cn-north-1.amazonaws.com.cn/Preview/Beta/2016/07/28__00_00_51/e9d46a02ba5f44a4a80a7773d61a2343.jpg1c6783bf-5574-11e6-942f-021e730a7faa","name":null,"source":0,"status":0,"type":"10","extended_data":null,"source_id":null,"is_primary":false},{"id":"18131565","link":"https://sherpapreview-standard.s3.cn-north-1.amazonaws.com.cn/Preview/Beta/2016/07/28__00_00_51/0aea90df313947c5be900a46f1171441.jpg1ed2cb0a-5574-11e6-942f-021e730a7faa","name":null,"source":0,"status":0,"type":"8","extended_data":null,"source_id":null,"is_primary":true}]
     * design_name : cesh
     * hs_designer_uid : 7c93ba0c-0647-4dcd-bd54-a3e03bf12d0e
     * designer_info : {"designer":{"studio":null,"styles":null,"introduction":null,"experience":null,"acs_member_id":20736524,"style_names":null,"style_long_names":null,"measurement_price":null,"design_price_min":null,"design_price_max":null,"graduate_from":null,"personal_honour":null,"diy_count":null,"case_count":null,"is_loho":0,"is_real_name":2,"theme_pic":"4"},"email":"ld_sjs_01@sina.com","avatar":"","gender":2,"birthday":"1993-01-01","province":"å\u008c\u0097äº¬","city":"å\u008c\u0097äº¬å¸\u0082","district":"ä¸\u009cå\u009f\u008eå\u008cº","address":null,"member_id":1399544,"hs_uid":"7c93ba0c-0647-4dcd-bd54-a3e03bf12d0e","first_name":"å¾\u008bå\u0085¸è®¾è®¡å¸\u008801","last_name":"å¾\u008bå\u0085¸è®¾è®¡å¸\u008801","home_phone":"","zip_code":"","hitachi_account":"ld_sjs_01","user_name":null,"register_date":"2016-04-11 14:39:39","mobile_number":null,"city_name":"å\u008c\u0097äº¬å¸\u0082","province_name":"å\u008c\u0097äº¬","district_name":"ä¸\u009cå\u009f\u008eå\u008cº","is_order_sms":0,"nick_name":"å¾\u008bå\u0085¸è®¾è®¡å¸\u008801","is_validated_by_mobile":0,"is_email_binding":1,"has_secreted":0,"is_following":null,"following_count":0,"evalution_avg_scores":0,"evalution_count":0}
     * designer_id : null
     * hs_design_id : 10ead789-5bac-4b93-9fd8-c08ab710c75a
     * project_style : null
     * project_type : null
     * publish_status : 1
     * room_area : 17
     * room_type : four
     * favorite_count : 0
     */

    private String bedroom;
    private String city;
    private String conception;
    private String district;
    private String province;
    private String restroom;
    private String  design_asset_id;
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

    public String getThumbnailMainPath() {
        return thumbnailMainPath;
    }

    public void setThumbnailMainPath(String thumbnailMainPath) {
        this.thumbnailMainPath = thumbnailMainPath;
    }

    private String thumbnailMainPath;
    /**
     * designer : {"studio":null,"styles":null,"introduction":null,"experience":null,"acs_member_id":20736524,"style_names":null,"style_long_names":null,"measurement_price":null,"design_price_min":null,"design_price_max":null,"graduate_from":null,"personal_honour":null,"diy_count":null,"case_count":null,"is_loho":0,"is_real_name":2,"theme_pic":"4"}
     * email : ld_sjs_01@sina.com
     * avatar :
     * gender : 2
     * birthday : 1993-01-01
     * province : åäº¬
     * city : åäº¬å¸
     * district : ä¸ååº
     * address : null
     * member_id : 1399544
     * hs_uid : 7c93ba0c-0647-4dcd-bd54-a3e03bf12d0e
     * first_name : å¾å¸è®¾è®¡å¸01
     * last_name : å¾å¸è®¾è®¡å¸01
     * home_phone :
     * zip_code :
     * hitachi_account : ld_sjs_01
     * user_name : null
     * register_date : 2016-04-11 14:39:39
     * mobile_number : null
     * city_name : åäº¬å¸
     * province_name : åäº¬
     * district_name : ä¸ååº
     * is_order_sms : 0
     * nick_name : å¾å¸è®¾è®¡å¸01
     * is_validated_by_mobile : 0
     * is_email_binding : 1
     * has_secreted : 0
     * is_following : null
     * following_count : 0
     * evalution_avg_scores : 0
     * evalution_count : 0
     */

    private DesignerInfoBean designer_info;
    private String designer_id;
    private String hs_design_id;
    private String project_style;
    private String project_type;
    private int publish_status;
    private String room_area;
    private String room_type;
    private int favorite_count;
    /**
     * id : 18131564
     * link : https://sherpapreview-standard.s3.cn-north-1.amazonaws.com.cn/Preview/Beta/2016/07/28__00_00_51/e9d46a02ba5f44a4a80a7773d61a2343.jpg1c6783bf-5574-11e6-942f-021e730a7faa
     * name : null
     * source : 0
     * status : 0
     * type : 10
     * extended_data : null
     * source_id : null
     * is_primary : false
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

    public String getDesigner_id() {
        return designer_id;
    }

    public void setDesigner_id(String designer_id) {
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

    public int getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(int favorite_count) {
        this.favorite_count = favorite_count;
    }

    public List<DesignFileBean> getDesign_file() {
        return design_file;
    }

    public void setDesign_file(List<DesignFileBean> design_file) {
        this.design_file = design_file;
    }

    public static class DesignerInfoBean implements Serializable {
        /**
         * studio : null
         * styles : null
         * introduction : null
         * experience : null
         * acs_member_id : 20736524
         * style_names : null
         * style_long_names : null
         * measurement_price : null
         * design_price_min : null
         * design_price_max : null
         * graduate_from : null
         * personal_honour : null
         * diy_count : null
         * case_count : null
         * is_loho : 0
         * is_real_name : 2
         * theme_pic : 4
         */

        private DesignerBean designer;
        private String email;
        private String avatar;
        private int gender;
        private String birthday;
        private String province;
        private String city;
        private String district;
        private String address;
        private int member_id;
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
        private int is_order_sms;
        private String nick_name;
        private int is_validated_by_mobile;
        private int is_email_binding;
        private int has_secreted;
        public boolean is_following;
        private int following_count;
        private int evalution_avg_scores;
        private int evalution_count;

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

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
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

        public int getMember_id() {
            return member_id;
        }

        public void setMember_id(int member_id) {
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

        public int getIs_order_sms() {
            return is_order_sms;
        }

        public void setIs_order_sms(int is_order_sms) {
            this.is_order_sms = is_order_sms;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public int getIs_validated_by_mobile() {
            return is_validated_by_mobile;
        }

        public void setIs_validated_by_mobile(int is_validated_by_mobile) {
            this.is_validated_by_mobile = is_validated_by_mobile;
        }

        public int getIs_email_binding() {
            return is_email_binding;
        }

        public void setIs_email_binding(int is_email_binding) {
            this.is_email_binding = is_email_binding;
        }

        public int getHas_secreted() {
            return has_secreted;
        }

        public void setHas_secreted(int has_secreted) {
            this.has_secreted = has_secreted;
        }

        public boolean getIs_following() {
            return is_following;
        }

        public void setIs_following(boolean is_following) {
            this.is_following = is_following;
        }

        public int getFollowing_count() {
            return following_count;
        }

        public void setFollowing_count(int following_count) {
            this.following_count = following_count;
        }

        public int getEvalution_avg_scores() {
            return evalution_avg_scores;
        }

        public void setEvalution_avg_scores(int evalution_avg_scores) {
            this.evalution_avg_scores = evalution_avg_scores;
        }

        public int getEvalution_count() {
            return evalution_count;
        }

        public void setEvalution_count(int evalution_count) {
            this.evalution_count = evalution_count;
        }

        public static class DesignerBean implements Serializable {
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
            private int is_loho;
            private int is_real_name;
            private String theme_pic;

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
        }
    }

    public static class DesignFileBean implements Serializable {
        private String id;
        private String link;
        private String name;
        private int source;
        private int status;
        private String type;
        private String extended_data;
        private String source_id;

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        private String cover;
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

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
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
