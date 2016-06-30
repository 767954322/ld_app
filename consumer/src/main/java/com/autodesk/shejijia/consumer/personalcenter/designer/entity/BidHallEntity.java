package com.autodesk.shejijia.consumer.personalcenter.designer.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 16-6-7
 * @file BidHallEntity.java  .
 * @brief .
 */
public class BidHallEntity implements Serializable {

    private int count;
    private int limit;
    private int offset;

    private List<NeedsListEntity> needs_list;

    public void setCount(int count) {
        this.count = count;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setNeeds_list(List<NeedsListEntity> needs_list) {
        this.needs_list = needs_list;
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

    public List<NeedsListEntity> getNeeds_list() {
        return needs_list;
    }

    public static class NeedsListEntity implements Serializable {
        private int bidder_count;
        private boolean bidding_status;
        private String city;
        private int click_number;
        private String community_name;
        private String consumer_mobile;
        private String contacts_mobile;
        private String contacts_name;
        private String custom_string_status;
        private String decoration_budget;
        private String decoration_style;
        private String design_budget;
        private String district;
        private String end_day;
        private String house_area;
        private String house_type;
        private String is_beishu;
        private String is_public;
        private String living_room;
        private int needs_id;
        private String province;
        private String publish_time;
        private String room;
        private String toilet;
        private String wk_template_id;
        /**
         * avatar : http://image.juranzaixian.com.cn:8082/img/56dbc42de4b07d88ca907a51.img
         * delivery : []
         * design_contract : []
         * designer_id : 20730187
         * join_time : 2016-03-08 14:41:05
         * measure_time : 2016-01-01 01:00:00
         * measurement_fee : 200.0
         * selected_time : 2016-03-08 14:44:42
         * status : 1
         * style_names : 古典
         * uid : a25fd718-348b-4021-becc-f3c5d0399141
         * user_name : li.yang
         * wk_cur_node_id : 1
         * wk_cur_sub_node_id : 13
         * wk_id : 1993
         */

        private List<BiddersEntity> bidders;

        public void setBidder_count(int bidder_count) {
            this.bidder_count = bidder_count;
        }

        public void setBidding_status(boolean bidding_status) {
            this.bidding_status = bidding_status;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setClick_number(int click_number) {
            this.click_number = click_number;
        }

        public void setCommunity_name(String community_name) {
            this.community_name = community_name;
        }

        public void setConsumer_mobile(String consumer_mobile) {
            this.consumer_mobile = consumer_mobile;
        }

        public void setContacts_mobile(String contacts_mobile) {
            this.contacts_mobile = contacts_mobile;
        }

        public void setContacts_name(String contacts_name) {
            this.contacts_name = contacts_name;
        }

        public void setCustom_string_status(String custom_string_status) {
            this.custom_string_status = custom_string_status;
        }

        public void setDecoration_budget(String decoration_budget) {
            this.decoration_budget = decoration_budget;
        }

        public void setDecoration_style(String decoration_style) {
            this.decoration_style = decoration_style;
        }

        public void setDesign_budget(String design_budget) {
            this.design_budget = design_budget;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public void setEnd_day(String end_day) {
            this.end_day = end_day;
        }

        public void setHouse_area(String house_area) {
            this.house_area = house_area;
        }

        public void setHouse_type(String house_type) {
            this.house_type = house_type;
        }

        public void setIs_beishu(String is_beishu) {
            this.is_beishu = is_beishu;
        }

        public void setIs_public(String is_public) {
            this.is_public = is_public;
        }

        public void setLiving_room(String living_room) {
            this.living_room = living_room;
        }

        public void setNeeds_id(int needs_id) {
            this.needs_id = needs_id;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public void setPublish_time(String publish_time) {
            this.publish_time = publish_time;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        public void setToilet(String toilet) {
            this.toilet = toilet;
        }

        public void setWk_template_id(String wk_template_id) {
            this.wk_template_id = wk_template_id;
        }

        public void setBidders(List<BiddersEntity> bidders) {
            this.bidders = bidders;
        }

        public int getBidder_count() {
            return bidder_count;
        }

        public boolean isBidding_status() {
            return bidding_status;
        }

        public String getCity() {
            return city;
        }

        public int getClick_number() {
            return click_number;
        }

        public String getCommunity_name() {
            return community_name;
        }

        public String getConsumer_mobile() {
            return consumer_mobile;
        }

        public String getContacts_mobile() {
            return contacts_mobile;
        }

        public String getContacts_name() {
            return contacts_name;
        }

        public String getCustom_string_status() {
            return custom_string_status;
        }

        public String getDecoration_budget() {
            return decoration_budget;
        }

        public String getDecoration_style() {
            return decoration_style;
        }

        public String getDesign_budget() {
            return design_budget;
        }

        public String getDistrict() {
            return district;
        }

        public String getEnd_day() {
            return end_day;
        }

        public String getHouse_area() {
            return house_area;
        }

        public String getHouse_type() {
            return house_type;
        }

        public String getIs_beishu() {
            return is_beishu;
        }

        public String getIs_public() {
            return is_public;
        }

        public String getLiving_room() {
            return living_room;
        }

        public int getNeeds_id() {
            return needs_id;
        }

        public String getProvince() {
            return province;
        }

        public String getPublish_time() {
            return publish_time;
        }

        public String getRoom() {
            return room;
        }

        public String getToilet() {
            return toilet;
        }

        public String getWk_template_id() {
            return wk_template_id;
        }

        public List<BiddersEntity> getBidders() {
            return bidders;
        }

        public static class BiddersEntity implements Serializable {
            private String avatar;
            private int designer_id;
            private String join_time;
            private String measure_time;
            private String measurement_fee;
            private String selected_time;
            private String status;
            private String style_names;
            private String uid;
            private String user_name;
            private String wk_cur_node_id;
            private String wk_cur_sub_node_id;
            private String wk_id;
            private List<?> delivery;
            private List<?> design_contract;

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public void setDesigner_id(int designer_id) {
                this.designer_id = designer_id;
            }

            public void setJoin_time(String join_time) {
                this.join_time = join_time;
            }

            public void setMeasure_time(String measure_time) {
                this.measure_time = measure_time;
            }

            public void setMeasurement_fee(String measurement_fee) {
                this.measurement_fee = measurement_fee;
            }

            public void setSelected_time(String selected_time) {
                this.selected_time = selected_time;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public void setStyle_names(String style_names) {
                this.style_names = style_names;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public void setWk_cur_node_id(String wk_cur_node_id) {
                this.wk_cur_node_id = wk_cur_node_id;
            }

            public void setWk_cur_sub_node_id(String wk_cur_sub_node_id) {
                this.wk_cur_sub_node_id = wk_cur_sub_node_id;
            }

            public void setWk_id(String wk_id) {
                this.wk_id = wk_id;
            }

            public void setDelivery(List<?> delivery) {
                this.delivery = delivery;
            }

            public void setDesign_contract(List<?> design_contract) {
                this.design_contract = design_contract;
            }

            public String getAvatar() {
                return avatar;
            }

            public int getDesigner_id() {
                return designer_id;
            }

            public String getJoin_time() {
                return join_time;
            }

            public String getMeasure_time() {
                return measure_time;
            }

            public String getMeasurement_fee() {
                return measurement_fee;
            }

            public String getSelected_time() {
                return selected_time;
            }

            public String getStatus() {
                return status;
            }

            public String getStyle_names() {
                return style_names;
            }

            public String getUid() {
                return uid;
            }

            public String getUser_name() {
                return user_name;
            }

            public String getWk_cur_node_id() {
                return wk_cur_node_id;
            }

            public String getWk_cur_sub_node_id() {
                return wk_cur_sub_node_id;
            }

            public String getWk_id() {
                return wk_id;
            }

            public List<?> getDelivery() {
                return delivery;
            }

            public List<?> getDesign_contract() {
                return design_contract;
            }
        }
    }
}
