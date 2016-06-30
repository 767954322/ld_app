package com.autodesk.shejijia.consumer.personalcenter.workflow.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 16-6-7
 * @file AgreeMeasureHouse.java  .
 * @brief .
 */
public class AgreeMeasureHouse implements Serializable {

    private String after_bidding_status;
    private int bidder_count;
    private boolean bidding_status;
    private String city;
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
    private String detail_desc;
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
    private List<BiddersEntity> bidders;
    private List<?> delivery;

    public void setAfter_bidding_status(String after_bidding_status) {
        this.after_bidding_status = after_bidding_status;
    }

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

    public void setConsumer_name(String consumer_name) {
        this.consumer_name = consumer_name;
    }

    public void setContacts_mobile(String contacts_mobile) {
        this.contacts_mobile = contacts_mobile;
    }

    public void setContacts_name(String contacts_name) {
        this.contacts_name = contacts_name;
    }

    public void setContract(Object contract) {
        this.contract = contract;
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

    public void setDetail_desc(String detail_desc) {
        this.detail_desc = detail_desc;
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

    public void setDelivery(List<?> delivery) {
        this.delivery = delivery;
    }

    public String getAfter_bidding_status() {
        return after_bidding_status;
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

    public String getConsumer_name() {
        return consumer_name;
    }

    public String getContacts_mobile() {
        return contacts_mobile;
    }

    public String getContacts_name() {
        return contacts_name;
    }

    public Object getContract() {
        return contract;
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

    public String getDetail_desc() {
        return detail_desc;
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

    public List<?> getDelivery() {
        return delivery;
    }

    public static class BiddersEntity {
        private Object current_actions;
        private String declaration;
        private Object design_contract;
        private int designer_id;
        private String join_time;
        private Object measure_time;
        private Object refused_time;
        private String selected_time;
        private String status;
        private String user_name;
        private Object wk_cur_main_step_id;
        private String wk_cur_sub_node_id;
        private String wk_id;
        private Object wk_steps;
        private String wk_template_cur_node_id;
        private List<OrdersEntity> orders;
        private List<?> wk_main_steps_executed;

        public void setCurrent_actions(Object current_actions) {
            this.current_actions = current_actions;
        }

        public void setDeclaration(String declaration) {
            this.declaration = declaration;
        }

        public void setDesign_contract(Object design_contract) {
            this.design_contract = design_contract;
        }

        public void setDesigner_id(int designer_id) {
            this.designer_id = designer_id;
        }

        public void setJoin_time(String join_time) {
            this.join_time = join_time;
        }

        public void setMeasure_time(Object measure_time) {
            this.measure_time = measure_time;
        }

        public void setRefused_time(Object refused_time) {
            this.refused_time = refused_time;
        }

        public void setSelected_time(String selected_time) {
            this.selected_time = selected_time;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public void setWk_cur_main_step_id(Object wk_cur_main_step_id) {
            this.wk_cur_main_step_id = wk_cur_main_step_id;
        }

        public void setWk_cur_sub_node_id(String wk_cur_sub_node_id) {
            this.wk_cur_sub_node_id = wk_cur_sub_node_id;
        }

        public void setWk_id(String wk_id) {
            this.wk_id = wk_id;
        }

        public void setWk_steps(Object wk_steps) {
            this.wk_steps = wk_steps;
        }

        public void setWk_template_cur_node_id(String wk_template_cur_node_id) {
            this.wk_template_cur_node_id = wk_template_cur_node_id;
        }

        public void setOrders(List<OrdersEntity> orders) {
            this.orders = orders;
        }

        public void setWk_main_steps_executed(List<?> wk_main_steps_executed) {
            this.wk_main_steps_executed = wk_main_steps_executed;
        }

        public Object getCurrent_actions() {
            return current_actions;
        }

        public String getDeclaration() {
            return declaration;
        }

        public Object getDesign_contract() {
            return design_contract;
        }

        public int getDesigner_id() {
            return designer_id;
        }

        public String getJoin_time() {
            return join_time;
        }

        public Object getMeasure_time() {
            return measure_time;
        }

        public Object getRefused_time() {
            return refused_time;
        }

        public String getSelected_time() {
            return selected_time;
        }

        public String getStatus() {
            return status;
        }

        public String getUser_name() {
            return user_name;
        }

        public Object getWk_cur_main_step_id() {
            return wk_cur_main_step_id;
        }

        public String getWk_cur_sub_node_id() {
            return wk_cur_sub_node_id;
        }

        public String getWk_id() {
            return wk_id;
        }

        public Object getWk_steps() {
            return wk_steps;
        }

        public String getWk_template_cur_node_id() {
            return wk_template_cur_node_id;
        }

        public List<OrdersEntity> getOrders() {
            return orders;
        }

        public List<?> getWk_main_steps_executed() {
            return wk_main_steps_executed;
        }

        public static class OrdersEntity {
            private int designer_id;
            private String order_line_no;
            private String order_no;
            private String order_type;

            public void setDesigner_id(int designer_id) {
                this.designer_id = designer_id;
            }

            public void setOrder_line_no(String order_line_no) {
                this.order_line_no = order_line_no;
            }

            public void setOrder_no(String order_no) {
                this.order_no = order_no;
            }

            public void setOrder_type(String order_type) {
                this.order_type = order_type;
            }

            public int getDesigner_id() {
                return designer_id;
            }

            public String getOrder_line_no() {
                return order_line_no;
            }

            public String getOrder_no() {
                return order_no;
            }

            public String getOrder_type() {
                return order_type;
            }
        }
    }
}
