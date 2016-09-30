package com.autodesk.shejijia.consumer.personalcenter.workflow.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author he.liu .
 * @version 1.0 .
 * @date 16-6-7
 * @file MPMeasureFormBean.java  .
 * @brief 消费者发送量房表单后返回的实体类.
 */
public class MPMeasureFormBean implements Serializable {
    private String city_name;
    private int needs_id;
    private String consumer_mobile;
    private String consumer_name;
    private String contacts_mobile;
    private String living_room;
    private int bidder_count;
    private String city;
    private String decoration_style;
    private String house_type;
    private String toilet;
    private String is_public;
    private String beishu_thread_id;
    private String after_bidding_status;
    private String province;
    private int custom_string_status;
    private String district;
    private String is_beishu;
    private boolean bidding_status;
    private String wk_template_id;
    private String community_name;
    private String decoration_budget;
    private String avatar;
    private String consumer_uid;
    private String province_name;
    private String detail_desc;
    private String publish_time;
    private String district_name;
    private int click_number;
    private String end_day;
    private String design_budget;
    private String house_area;
    private String contacts_name;
    private String room;

    private List<BiddersBean> bidders;

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public int getNeeds_id() {
        return needs_id;
    }

    public void setNeeds_id(int needs_id) {
        this.needs_id = needs_id;
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

    public String getLiving_room() {
        return living_room;
    }

    public void setLiving_room(String living_room) {
        this.living_room = living_room;
    }

    public int getBidder_count() {
        return bidder_count;
    }

    public void setBidder_count(int bidder_count) {
        this.bidder_count = bidder_count;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDecoration_style() {
        return decoration_style;
    }

    public void setDecoration_style(String decoration_style) {
        this.decoration_style = decoration_style;
    }

    public String getHouse_type() {
        return house_type;
    }

    public void setHouse_type(String house_type) {
        this.house_type = house_type;
    }

    public String getToilet() {
        return toilet;
    }

    public void setToilet(String toilet) {
        this.toilet = toilet;
    }

    public String getIs_public() {
        return is_public;
    }

    public void setIs_public(String is_public) {
        this.is_public = is_public;
    }

    public Object getBeishu_thread_id() {
        return beishu_thread_id;
    }

    public void setBeishu_thread_id(String beishu_thread_id) {
        this.beishu_thread_id = beishu_thread_id;
    }

    public String getAfter_bidding_status() {
        return after_bidding_status;
    }

    public void setAfter_bidding_status(String after_bidding_status) {
        this.after_bidding_status = after_bidding_status;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getCustom_string_status() {
        return custom_string_status;
    }

    public void setCustom_string_status(int custom_string_status) {
        this.custom_string_status = custom_string_status;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getIs_beishu() {
        return is_beishu;
    }

    public void setIs_beishu(String is_beishu) {
        this.is_beishu = is_beishu;
    }

    public boolean isBidding_status() {
        return bidding_status;
    }

    public void setBidding_status(boolean bidding_status) {
        this.bidding_status = bidding_status;
    }

    public String getWk_template_id() {
        return wk_template_id;
    }

    public void setWk_template_id(String wk_template_id) {
        this.wk_template_id = wk_template_id;
    }

    public String getCommunity_name() {
        return community_name;
    }

    public void setCommunity_name(String community_name) {
        this.community_name = community_name;
    }

    public String getDecoration_budget() {
        return decoration_budget;
    }

    public void setDecoration_budget(String decoration_budget) {
        this.decoration_budget = decoration_budget;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getConsumer_uid() {
        return consumer_uid;
    }

    public void setConsumer_uid(String consumer_uid) {
        this.consumer_uid = consumer_uid;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public String getDetail_desc() {
        return detail_desc;
    }

    public void setDetail_desc(String detail_desc) {
        this.detail_desc = detail_desc;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public int getClick_number() {
        return click_number;
    }

    public void setClick_number(int click_number) {
        this.click_number = click_number;
    }

    public String getEnd_day() {
        return end_day;
    }

    public void setEnd_day(String end_day) {
        this.end_day = end_day;
    }

    public String getDesign_budget() {
        return design_budget;
    }

    public void setDesign_budget(String design_budget) {
        this.design_budget = design_budget;
    }

    public String getHouse_area() {
        return house_area;
    }

    public void setHouse_area(String house_area) {
        this.house_area = house_area;
    }

    public String getContacts_name() {
        return contacts_name;
    }

    public void setContacts_name(String contacts_name) {
        this.contacts_name = contacts_name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public List<BiddersBean> getBidders() {
        return bidders;
    }

    public void setBidders(List<BiddersBean> bidders) {
        this.bidders = bidders;
    }

    public static class BiddersBean implements Serializable {
        private String uid;
        private String wk_cur_sub_node_id;
        private String declaration;
        private String status;
        private String wk_current_step_id;
        private String design_price_max;
        private String design_price_min;
        private String style_names;
        private String measure_time;
        private String avatar;
        private String selected_time;
        private String wk_id;
        private String user_name;
        private String designer_id;
        private String wk_cur_node_id;
        private String refused_time;
        private String measurement_fee;
        private String join_time;
        private String design_thread_id;

        private PaymentBean payment;
        private MPDeliveryBean delivery;
        private List<WkStepsBean> wk_steps;
        private List<MPDesignContractBean> design_contract;
        private List<WkNextPossibleSubNodeIdsBean> wk_next_possible_sub_node_ids;
        private List<OrdersBean> orders;


        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getWk_cur_sub_node_id() {
            return wk_cur_sub_node_id;
        }

        public void setWk_cur_sub_node_id(String wk_cur_sub_node_id) {
            this.wk_cur_sub_node_id = wk_cur_sub_node_id;
        }

        public String getDeclaration() {
            return declaration;
        }

        public void setDeclaration(String declaration) {
            this.declaration = declaration;
        }

        public PaymentBean getPayment() {
            return payment;
        }

        public void setPayment(PaymentBean payment) {
            this.payment = payment;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getWk_current_step_id() {
            return wk_current_step_id;
        }

        public void setWk_current_step_id(String wk_current_step_id) {
            this.wk_current_step_id = wk_current_step_id;
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

        public String getStyle_names() {
            return style_names;
        }

        public void setStyle_names(String style_names) {
            this.style_names = style_names;
        }

        public String getMeasure_time() {
            return measure_time;
        }

        public void setMeasure_time(String measure_time) {
            this.measure_time = measure_time;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getSelected_time() {
            return selected_time;
        }

        public void setSelected_time(String selected_time) {
            this.selected_time = selected_time;
        }

        public String getWk_id() {
            return wk_id;
        }

        public void setWk_id(String wk_id) {
            this.wk_id = wk_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getDesigner_id() {
            return designer_id;
        }

        public void setDesigner_id(String designer_id) {
            this.designer_id = designer_id;
        }

        public String getWk_cur_node_id() {
            return wk_cur_node_id;
        }

        public void setWk_cur_node_id(String wk_cur_node_id) {
            this.wk_cur_node_id = wk_cur_node_id;
        }

        public String getRefused_time() {
            return refused_time;
        }

        public void setRefused_time(String refused_time) {
            this.refused_time = refused_time;
        }

        public String getMeasurement_fee() {
            return measurement_fee;
        }

        public void setMeasurement_fee(String measurement_fee) {
            this.measurement_fee = measurement_fee;
        }

        public String getJoin_time() {
            return join_time;
        }

        public void setJoin_time(String join_time) {
            this.join_time = join_time;
        }

        public String getDesign_thread_id() {
            return design_thread_id;
        }

        public void setDesign_thread_id(String design_thread_id) {
            this.design_thread_id = design_thread_id;
        }

        public MPDeliveryBean getDelivery() {
            return delivery;
        }

        public void setDelivery(MPDeliveryBean delivery) {
            this.delivery = delivery;
        }
        //        public DeliveryBean getDelivery() {
//            return delivery;
//        }
//
//        public void setDelivery(DeliveryBean delivery) {
//            this.delivery = delivery;
//        }

        public List<WkStepsBean> getWk_steps() {
            return wk_steps;
        }

        public void setWk_steps(List<WkStepsBean> wk_steps) {
            this.wk_steps = wk_steps;
        }

        public List<MPDesignContractBean> getDesign_contract() {
            return design_contract;
        }

        public void setDesign_contract(List<MPDesignContractBean> design_contract) {
            this.design_contract = design_contract;
        }

        public List<OrdersBean> getOrders() {
            return orders;
        }

        public void setOrders(List<OrdersBean> orders) {
            this.orders = orders;
        }

        public List<WkNextPossibleSubNodeIdsBean> getWk_next_possible_sub_node_ids() {
            return wk_next_possible_sub_node_ids;
        }

        public void setWk_next_possible_sub_node_ids(List<WkNextPossibleSubNodeIdsBean> wk_next_possible_sub_node_ids) {
            this.wk_next_possible_sub_node_ids = wk_next_possible_sub_node_ids;
        }

        public static class WkNextPossibleSubNodeIdsBean implements Serializable {
            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class WkStepsBean implements Serializable {
            private Object files;
            private int status;
            private String thread_id;
            private String previous_lastest_msg_id;
            private String timestam_end;
            private String timestamp_start;
            private String wk_step_id;

            public Object getFiles() {
                return files;
            }

            public void setFiles(Object files) {
                this.files = files;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getThread_id() {
                return thread_id;
            }

            public void setThread_id(String thread_id) {
                this.thread_id = thread_id;
            }

            public String getPrevious_lastest_msg_id() {
                return previous_lastest_msg_id;
            }

            public void setPrevious_lastest_msg_id(String previous_lastest_msg_id) {
                this.previous_lastest_msg_id = previous_lastest_msg_id;
            }

            public String getTimestam_end() {
                return timestam_end;
            }

            public void setTimestam_end(String timestam_end) {
                this.timestam_end = timestam_end;
            }

            public String getTimestamp_start() {
                return timestamp_start;
            }

            public void setTimestamp_start(String timestamp_start) {
                this.timestamp_start = timestamp_start;
            }

            public String getWk_step_id() {
                return wk_step_id;
            }

            public void setWk_step_id(String wk_step_id) {
                this.wk_step_id = wk_step_id;
            }
        }

        public static class PaymentBean implements Serializable {
            private String paid_fee;
            private String measurement_fee;
            private String unpaid_fee;
            private String create_date;
            private String total_fee;

            public String getPaid_fee() {
                return paid_fee;
            }

            public void setPaid_fee(String paid_fee) {
                this.paid_fee = paid_fee;
            }

            public String getMeasurement_fee() {
                return measurement_fee;
            }

            public void setMeasurement_fee(String measurement_fee) {
                this.measurement_fee = measurement_fee;
            }

            public String getUnpaid_fee() {
                return unpaid_fee;
            }

            public void setUnpaid_fee(String unpaid_fee) {
                this.unpaid_fee = unpaid_fee;
            }

            public String getCreate_date() {
                return create_date;
            }

            public void setCreate_date(String create_date) {
                this.create_date = create_date;
            }

            public String getTotal_fee() {
                return total_fee;
            }

            public void setTotal_fee(String total_fee) {
                this.total_fee = total_fee;
            }
        }
//
//        public static class DeliveryBean implements Serializable {
//            private int type;
//            private String designer_id;
//            private List<MPFileBean> files;
//
//            public int getType() {
//                return type;
//            }
//
//            public void setType(int type) {
//                this.type = type;
//            }
//
//            public String getDesigner_id() {
//                return designer_id;
//            }
//
//            public void setDesigner_id(String designer_id) {
//                this.designer_id = designer_id;
//            }
//
//            public List<MPFileBean> getFiles() {
//                return files;
//            }
//
//            public void setFiles(List<MPFileBean> files) {
//                this.files = files;
//            }
//        }

        public static class OrdersBean implements Serializable {
            private String order_line_no;
            private String designer_id;
            private String order_status;
            private String order_no;
            private String order_type;

            public String getOrder_line_no() {
                return order_line_no;
            }

            public void setOrder_line_no(String order_line_no) {
                this.order_line_no = order_line_no;
            }

            public String getDesigner_id() {
                return designer_id;
            }

            public void setDesigner_id(String designer_id) {
                this.designer_id = designer_id;
            }

            public String getOrder_status() {
                return order_status;
            }

            public void setOrder_status(String order_status) {
                this.order_status = order_status;
            }

            public String getOrder_no() {
                return order_no;
            }

            public void setOrder_no(String order_no) {
                this.order_no = order_no;
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
