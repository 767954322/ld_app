package com.autodesk.shejijia.consumer.personalcenter.workflow.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author he.liu .
 * @version 1.0 .
 * @date 16-6-7
 * @file AgreeResponseBean.java  .
 * @brief .
 */
public class AgreeResponseBean implements Serializable {

    private String after_bidding_status;
    private Object audit_desc;
    private Object beishu_thread_id;
    private int bidder_count;
    private boolean bidding_status;
    private String city;
    private String city_name;
    private int click_number;
    private String community_name;
    private String consumer_mobile;
    private String consumer_name;
    private String contacts_mobile;
    private String contacts_name;
    private Object contract;
    private String custom_string_status;
    private String decoration_budget;
    private String decoration_style;
    private Object delivery;
    private String design_budget;
    private String detail_desc;
    private String district;
    private String district_name;
    private String end_day;
    private String house_area;
    private String house_type;
    private String is_beishu;
    private String is_public;
    private String living_room;
    private String needs_id;
    private String province;
    private String province_name;
    private String publish_time;
    private String room;
    private String toilet;
    private String wk_template_id;
    private List<BiddersEntity> bidders;

    public String getAfter_bidding_status() {
        return after_bidding_status;
    }

    public void setAfter_bidding_status(String after_bidding_status) {
        this.after_bidding_status = after_bidding_status;
    }

    public Object getAudit_desc() {
        return audit_desc;
    }

    public void setAudit_desc(Object audit_desc) {
        this.audit_desc = audit_desc;
    }

    public Object getBeishu_thread_id() {
        return beishu_thread_id;
    }

    public void setBeishu_thread_id(Object beishu_thread_id) {
        this.beishu_thread_id = beishu_thread_id;
    }

    public int getBidder_count() {
        return bidder_count;
    }

    public void setBidder_count(int bidder_count) {
        this.bidder_count = bidder_count;
    }

    public boolean isBidding_status() {
        return bidding_status;
    }

    public void setBidding_status(boolean bidding_status) {
        this.bidding_status = bidding_status;
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

    public String getConsumer_mobile() {
        return consumer_mobile;
    }

    public void setConsumer_mobile(String consumer_mobile) {
        this.consumer_mobile = consumer_mobile;
    }

    public String getConsumer_name() {
        return consumer_name;
    }

    public void setConsumer_name(String consumer_name) {
        this.consumer_name = consumer_name;
    }

    public String getContacts_mobile() {
        return contacts_mobile;
    }

    public void setContacts_mobile(String contacts_mobile) {
        this.contacts_mobile = contacts_mobile;
    }

    public String getContacts_name() {
        return contacts_name;
    }

    public void setContacts_name(String contacts_name) {
        this.contacts_name = contacts_name;
    }

    public Object getContract() {
        return contract;
    }

    public void setContract(Object contract) {
        this.contract = contract;
    }

    public String getCustom_string_status() {
        return custom_string_status;
    }

    public void setCustom_string_status(String custom_string_status) {
        this.custom_string_status = custom_string_status;
    }

    public void setNeeds_id(String needs_id) {
        this.needs_id = needs_id;
    }

    public String getDecoration_budget() {
        return decoration_budget;
    }

    public void setDecoration_budget(String decoration_budget) {
        this.decoration_budget = decoration_budget;
    }

    public String getDecoration_style() {
        return decoration_style;
    }

    public void setDecoration_style(String decoration_style) {
        this.decoration_style = decoration_style;
    }

    public Object getDelivery() {
        return delivery;
    }

    public void setDelivery(Object delivery) {
        this.delivery = delivery;
    }

    public String getDesign_budget() {
        return design_budget;
    }

    public void setDesign_budget(String design_budget) {
        this.design_budget = design_budget;
    }

    public String getDetail_desc() {
        return detail_desc;
    }

    public void setDetail_desc(String detail_desc) {
        this.detail_desc = detail_desc;
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

    public String getEnd_day() {
        return end_day;
    }

    public void setEnd_day(String end_day) {
        this.end_day = end_day;
    }

    public String getHouse_area() {
        return house_area;
    }

    public void setHouse_area(String house_area) {
        this.house_area = house_area;
    }

    public String getHouse_type() {
        return house_type;
    }

    public void setHouse_type(String house_type) {
        this.house_type = house_type;
    }

    public String getIs_beishu() {
        return is_beishu;
    }

    public void setIs_beishu(String is_beishu) {
        this.is_beishu = is_beishu;
    }

    public String getIs_public() {
        return is_public;
    }

    public void setIs_public(String is_public) {
        this.is_public = is_public;
    }

    public String getLiving_room() {
        return living_room;
    }

    public void setLiving_room(String living_room) {
        this.living_room = living_room;
    }

    public String getNeeds_id() {
        return needs_id;
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

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getToilet() {
        return toilet;
    }

    public void setToilet(String toilet) {
        this.toilet = toilet;
    }

    public String getWk_template_id() {
        return wk_template_id;
    }

    public void setWk_template_id(String wk_template_id) {
        this.wk_template_id = wk_template_id;
    }

    public List<BiddersEntity> getBidders() {
        return bidders;
    }

    public void setBidders(List<BiddersEntity> bidders) {
        this.bidders = bidders;
    }

    public static class BiddersEntity implements Serializable {
        private String avatar;
        private String current_actions;
        private String declaration;
        private String design_price_max;
        private String design_price_min;
        private String design_thread_id;
        private String designer_id;
        private String join_time;
        private String measure_time;
        private String measurement_fee;
        private String payment;
        private String refused_time;
        private String selected_time;
        private String status;
        private String style_names;
        private String uid;
        private String user_name;
        private String wk_cur_node_id;
        private String wk_cur_sub_node_id;
        private String wk_current_step_id;
        private String wk_id;
        private String wk_next_possible_sub_node_ids;
        private String wk_steps;
        private List<?> delivery;
        private List<?> design_contract;

        private List<OrdersEntity> orders;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public Object getCurrent_actions() {
            return current_actions;
        }


        public String getDeclaration() {
            return declaration;
        }

        public void setDeclaration(String declaration) {
            this.declaration = declaration;
        }


        public String getDesign_thread_id() {
            return design_thread_id;
        }

        public void setDesign_thread_id(String design_thread_id) {
            this.design_thread_id = design_thread_id;
        }


        public String getJoin_time() {
            return join_time;
        }

        public void setJoin_time(String join_time) {
            this.join_time = join_time;
        }

        public String getMeasure_time() {
            return measure_time;
        }

        public void setMeasure_time(String measure_time) {
            this.measure_time = measure_time;
        }

        public String getMeasurement_fee() {
            return measurement_fee;
        }

        public void setMeasurement_fee(String measurement_fee) {
            this.measurement_fee = measurement_fee;
        }

        public Object getPayment() {
            return payment;
        }

        public void setSelected_time(String selected_time) {
            this.selected_time = selected_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getStyle_names() {
            return style_names;
        }


        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getWk_cur_node_id() {
            return wk_cur_node_id;
        }

        public void setWk_cur_node_id(String wk_cur_node_id) {
            this.wk_cur_node_id = wk_cur_node_id;
        }

        public String getWk_cur_sub_node_id() {
            return wk_cur_sub_node_id;
        }

        public void setWk_cur_sub_node_id(String wk_cur_sub_node_id) {
            this.wk_cur_sub_node_id = wk_cur_sub_node_id;
        }

        public Object getWk_current_step_id() {
            return wk_current_step_id;
        }


        public String getWk_id() {
            return wk_id;
        }

        public void setWk_id(String wk_id) {
            this.wk_id = wk_id;
        }

        public Object getWk_next_possible_sub_node_ids() {
            return wk_next_possible_sub_node_ids;
        }


        public Object getWk_steps() {
            return wk_steps;
        }

        public void setCurrent_actions(String current_actions) {
            this.current_actions = current_actions;
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

        public String getDesigner_id() {
            return designer_id;
        }

        public void setDesigner_id(String designer_id) {
            this.designer_id = designer_id;
        }

        public void setPayment(String payment) {
            this.payment = payment;
        }

        public String getRefused_time() {
            return refused_time;
        }

        public void setRefused_time(String refused_time) {
            this.refused_time = refused_time;
        }

        public String getSelected_time() {
            return selected_time;
        }

        public void setStyle_names(String style_names) {
            this.style_names = style_names;
        }

        public void setWk_current_step_id(String wk_current_step_id) {
            this.wk_current_step_id = wk_current_step_id;
        }

        public void setWk_next_possible_sub_node_ids(String wk_next_possible_sub_node_ids) {
            this.wk_next_possible_sub_node_ids = wk_next_possible_sub_node_ids;
        }

        public void setWk_steps(String wk_steps) {
            this.wk_steps = wk_steps;
        }

        public List<?> getDelivery() {
            return delivery;
        }

        public void setDelivery(List<?> delivery) {
            this.delivery = delivery;
        }

        public List<?> getDesign_contract() {
            return design_contract;
        }

        public void setDesign_contract(List<?> design_contract) {
            this.design_contract = design_contract;
        }

        public List<OrdersEntity> getOrders() {
            return orders;
        }

        public void setOrders(List<OrdersEntity> orders) {
            this.orders = orders;
        }

        public static class OrdersEntity implements Serializable {
            private int designer_id;
            private String order_line_no;
            private String order_no;
            private String order_status;
            private String order_type;

            public int getDesigner_id() {
                return designer_id;
            }

            public void setDesigner_id(int designer_id) {
                this.designer_id = designer_id;
            }

            public String getOrder_line_no() {
                return order_line_no;
            }

            public void setOrder_line_no(String order_line_no) {
                this.order_line_no = order_line_no;
            }

            public String getOrder_no() {
                return order_no;
            }

            public void setOrder_no(String order_no) {
                this.order_no = order_no;
            }

            public String getOrder_status() {
                return order_status;
            }

            public void setOrder_status(String order_status) {
                this.order_status = order_status;
            }

            public String getOrder_type() {
                return order_type;
            }

            public void setOrder_type(String order_type) {
                this.order_type = order_type;
            }
        }
    }
}
