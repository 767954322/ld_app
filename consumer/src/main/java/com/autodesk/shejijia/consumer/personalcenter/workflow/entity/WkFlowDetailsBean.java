package com.autodesk.shejijia.consumer.personalcenter.workflow.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 16-6-7
 * @file WkFlowDetailsBean.java  .
 * @brief .
 */
public class WkFlowDetailsBean implements Serializable {

    private RequirementEntity requirement;

    public void setRequirement(RequirementEntity requirement) {
        this.requirement = requirement;
    }

    public RequirementEntity getRequirement() {
        return requirement;
    }

    public static class RequirementEntity implements Serializable {
        private String after_bidding_status;
        private String beishu_thread_id;
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
        private Object delivery;
        private String design_budget;
        private String detail_desc;
        private String district;
        private String end_day;
        private String house_area;
        private String house_type;
        private String is_beishu;
        private String is_public;
        private String living_room;
        private String needs_id;
        private String province;
        private String publish_time;
        private String room;
        private String toilet;
        private String wk_template_id;

        private List<BiddersEntity> bidders;

        public void setAfter_bidding_status(String after_bidding_status) {
            this.after_bidding_status = after_bidding_status;
        }

        public void setBeishu_thread_id(String beishu_thread_id) {
            this.beishu_thread_id = beishu_thread_id;
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

        public void setDelivery(Object delivery) {
            this.delivery = delivery;
        }

        public void setDesign_budget(String design_budget) {
            this.design_budget = design_budget;
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

        public void setNeeds_id(String needs_id) {
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

        public String getAfter_bidding_status() {
            return after_bidding_status;
        }

        public String getBeishu_thread_id() {
            return beishu_thread_id;
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

        public Object getDelivery() {
            return delivery;
        }

        public Object getDesign_budget() {
            return design_budget;
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

        public String getNeeds_id() {
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

        public BiddersEntity getOrderBidder() {

            List<BiddersEntity> bidders = this.getBidders();
            if (null == bidders || bidders.size() < 1) {
                return null;
            }
            WkFlowDetailsBean.RequirementEntity.BiddersEntity biddersEntity = bidders.get(0);
            return biddersEntity;
        }

        public static class BiddersEntity implements Serializable {
            private Object current_actions;
            private String declaration;
            private int designer_id;
            private String join_time;
            private String measure_time;
            private String measurement_fee;
            private PaymentBean payment;
            private String refused_time;
            private String selected_time;
            private String status;
            private String uid;
            private String wk_cur_node_id;
            private String wk_cur_sub_node_id;
            private String wk_current_step_id;
            private String wk_id;

            private List<DesignContractEntity> design_contract;
            private List<OrdersEntity> orders;
            private List<WkNextPossibleSubNodeIdsEntity> wk_next_possible_sub_node_ids;
            private List<WkStepsEntity> wk_steps;

            public static class PaymentBean implements Serializable {
                private String create_date;
                private String measurement_fee;
                private String paid_fee;
                private String total_fee;
                private String unpaid_fee;

                public String getCreate_date() {
                    return create_date;
                }

                public void setCreate_date(String create_date) {
                    this.create_date = create_date;
                }

                public String getMeasurement_fee() {
                    return measurement_fee;
                }

                public void setMeasurement_fee(String measurement_fee) {
                    this.measurement_fee = measurement_fee;
                }

                public String getPaid_fee() {
                    return paid_fee;
                }

                public void setPaid_fee(String paid_fee) {
                    this.paid_fee = paid_fee;
                }

                public String getTotal_fee() {
                    return total_fee;
                }

                public void setTotal_fee(String total_fee) {
                    this.total_fee = total_fee;
                }

                public String getUnpaid_fee() {
                    return unpaid_fee;
                }

                public void setUnpaid_fee(String unpaid_fee) {
                    this.unpaid_fee = unpaid_fee;
                }
            }

            public void setCurrent_actions(Object current_actions) {
                this.current_actions = current_actions;
            }

            public void setDeclaration(String declaration) {
                this.declaration = declaration;
            }

            public void setDesign_contract(List<DesignContractEntity> design_contract) {
                this.design_contract = design_contract;
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

            public void setPayment(PaymentBean payment) {
                this.payment = payment;
            }

            public void setRefused_time(String refused_time) {
                this.refused_time = refused_time;
            }

            public void setSelected_time(String selected_time) {
                this.selected_time = selected_time;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public void setWk_cur_node_id(String wk_cur_node_id) {
                this.wk_cur_node_id = wk_cur_node_id;
            }

            public void setWk_cur_sub_node_id(String wk_cur_sub_node_id) {
                this.wk_cur_sub_node_id = wk_cur_sub_node_id;
            }

            public void setWk_current_step_id(String wk_current_step_id) {
                this.wk_current_step_id = wk_current_step_id;
            }

            public void setWk_id(String wk_id) {
                this.wk_id = wk_id;
            }

            public void setOrders(List<OrdersEntity> orders) {
                this.orders = orders;
            }

            public void setWk_next_possible_sub_node_ids(List<WkNextPossibleSubNodeIdsEntity> wk_next_possible_sub_node_ids) {
                this.wk_next_possible_sub_node_ids = wk_next_possible_sub_node_ids;
            }

            public void setWk_steps(List<WkStepsEntity> wk_steps) {
                this.wk_steps = wk_steps;
            }

            public Object getCurrent_actions() {
                return current_actions;
            }

            public String getDeclaration() {
                return declaration;
            }

            public List<DesignContractEntity> getDesign_contract() {
                return design_contract;
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

            public PaymentBean getPayment() {
                return payment;
            }

            public String getRefused_time() {
                return refused_time;
            }

            public String getSelected_time() {
                return selected_time;
            }

            public String getStatus() {
                return status;
            }

            public String getUid() {
                return uid;
            }

            public String getWk_cur_node_id() {
                return wk_cur_node_id;
            }

            public String getWk_cur_sub_node_id() {
                return wk_cur_sub_node_id;
            }

            public String getWk_current_step_id() {
                return wk_current_step_id;
            }

            public String getWk_id() {
                return wk_id;
            }

            public List<OrdersEntity> getOrders() {
                return orders;
            }

            public List<WkNextPossibleSubNodeIdsEntity> getWk_next_possible_sub_node_ids() {
                return wk_next_possible_sub_node_ids;
            }

            public List<WkStepsEntity> getWk_steps() {
                return wk_steps;
            }

            public static class DesignContractEntity implements Serializable {
                private String contract_charge;
                private long contract_create_date;
                private String contract_data;
                private String contract_first_charge;

                private String contract_no;
                private int contract_status;
                private String contract_template_url;
                private int contract_type;
                private Object contract_update_date;
                private int designer_id;

                public void setContract_charge(String contract_charge) {
                    this.contract_charge = contract_charge;
                }

                public void setContract_create_date(long contract_create_date) {
                    this.contract_create_date = contract_create_date;
                }

                public void setContract_data(String contract_data) {
                    this.contract_data = contract_data;
                }

                public void setContract_first_charge(String contract_first_charge) {
                    this.contract_first_charge = contract_first_charge;
                }

                public void setContract_no(String contract_no) {
                    this.contract_no = contract_no;
                }

                public void setContract_status(int contract_status) {
                    this.contract_status = contract_status;
                }

                public void setContract_template_url(String contract_template_url) {
                    this.contract_template_url = contract_template_url;
                }

                public void setContract_type(int contract_type) {
                    this.contract_type = contract_type;
                }

                public void setContract_update_date(Object contract_update_date) {
                    this.contract_update_date = contract_update_date;
                }

                public void setDesigner_id(int designer_id) {
                    this.designer_id = designer_id;
                }

                public String getContract_charge() {
                    return contract_charge;
                }

                public long getContract_create_date() {
                    return contract_create_date;
                }

                public String getContract_data() {
                    return contract_data;
                }

                public String getContract_first_charge() {
                    return contract_first_charge;
                }

                public String getContract_no() {
                    return contract_no;
                }

                public int getContract_status() {
                    return contract_status;
                }

                public String getContract_template_url() {
                    return contract_template_url;
                }

                public int getContract_type() {
                    return contract_type;
                }

                public Object getContract_update_date() {
                    return contract_update_date;
                }

                public int getDesigner_id() {
                    return designer_id;
                }
            }

            public static class OrdersEntity implements Serializable {
                private String designer_id;
                private String order_line_no;
                private String order_no;
                private String order_status;
                private String order_type;

                public void setDesigner_id(String designer_id) {
                    this.designer_id = designer_id;
                }

                public void setOrder_line_no(String order_line_no) {
                    this.order_line_no = order_line_no;
                }

                public void setOrder_no(String order_no) {
                    this.order_no = order_no;
                }

                public void setOrder_status(String order_status) {
                    this.order_status = order_status;
                }

                public void setOrder_type(String order_type) {
                    this.order_type = order_type;
                }

                public String getDesigner_id() {
                    return designer_id;
                }

                public String getOrder_line_no() {
                    return order_line_no;
                }

                public String getOrder_no() {
                    return order_no;
                }

                public String getOrder_status() {
                    return order_status;
                }

                public String getOrder_type() {
                    return order_type;
                }
            }

            public static class WkNextPossibleSubNodeIdsEntity implements Serializable {
                private int actionId;
                private String name;

                public void setId(int actionId) {
                    this.actionId = actionId;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getId() {
                    return actionId;
                }

                public String getName() {
                    return name;
                }
            }

            public static class WkStepsEntity implements Serializable {
                private Object files;
                private int status;
                private String thread_id;
                private int wk_step_id;

                public void setFiles(Object files) {
                    this.files = files;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public void setThread_id(String thread_id) {
                    this.thread_id = thread_id;
                }

                public void setWk_step_id(int wk_step_id) {
                    this.wk_step_id = wk_step_id;
                }

                public Object getFiles() {
                    return files;
                }

                public int getStatus() {
                    return status;
                }

                public String getThread_id() {
                    return thread_id;
                }

                public int getWk_step_id() {
                    return wk_step_id;
                }
            }
        }
    }

}
