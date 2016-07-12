package com.autodesk.shejijia.consumer.personalcenter.consumer.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author he.liu .
 * @version 1.0 .
 * @date 16-6-7 上午11:20
 * @file DecorationListEntity.java  .
 * @brief 家装订单的实体类 .
 */
public class DecorationListEntity implements Serializable {
    private int count;
    private String date;
    private int limit;
    private int offset;
    private String _link;

    private List<NeedsListEntity> needs_list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public List<NeedsListEntity> getNeeds_list() {
        return needs_list;
    }

    public void setNeeds_list(List<NeedsListEntity> needs_list) {
        this.needs_list = needs_list;
    }

    public static class NeedsListEntity implements Serializable {
        /**
         * 删除了
         * private String contract;
         * private Object delivery;
         * <p/>
         * 增加了
         * private String avatar;
         */
        private String city;
        private String district;
        private String province;
        private String room;
        private String toilet;
        private String avatar;
        private String after_bidding_status;
        private String beishu_thread_id;
        private String bidder_count;
        private boolean bidding_status;
        private String city_name;
        private int click_number;
        private String community_name;
        private String consumer_mobile;
        private String consumer_name;
        private String contacts_mobile;
        private String contacts_name;
        private String custom_string_status;
        private String decoration_budget;
        private String decoration_style;
        private String design_budget;
        private String detail_desc;
        private String district_name;
        private String end_day;
        private String house_area;
        private String house_type;
        private String is_beishu;
        private String is_public;
        private String living_room;
        private String needs_id;
        private String province_name;
        private String publish_time;
        private String wk_template_id;
        private String contract;

        public String getContract() {
            return contract;
        }

        public void setContract(String contract) {
            this.contract = contract;
        }

        private List<BiddersBean> bidders;

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

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public Object getAfter_bidding_status() {
            return after_bidding_status;
        }

        public void setAfter_bidding_status(String after_bidding_status) {
            this.after_bidding_status = after_bidding_status;
        }

        public String getBeishu_thread_id() {
            return beishu_thread_id;
        }

        public void setBeishu_thread_id(String beishu_thread_id) {
            this.beishu_thread_id = beishu_thread_id;
        }

        public String getBidder_count() {
            return bidder_count;
        }

        public void setBidder_count(String bidder_count) {
            this.bidder_count = bidder_count;
        }

        public boolean isBidding_status() {
            return bidding_status;
        }

        public void setBidding_status(boolean bidding_status) {
            this.bidding_status = bidding_status;
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

        public String getCustom_string_status() {
            return custom_string_status;
        }

        public void setCustom_string_status(String custom_string_status) {
            this.custom_string_status = custom_string_status;
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

        public void setNeeds_id(String needs_id) {
            this.needs_id = needs_id;
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

        public String getWk_template_id() {
            return wk_template_id;
        }

        public void setWk_template_id(String wk_template_id) {
            this.wk_template_id = wk_template_id;
        }

        public List<BiddersBean> getBidders() {
            return bidders;
        }

        public void setBidders(List<BiddersBean> bidders) {
            this.bidders = bidders;
        }

        public static class BiddersBean implements Serializable {
            private String avatar;
            private String declaration;
            private Object delivery;
            private String status;
            private String uid;
            private String designer_id;
            private String design_thread_id;
            private String join_time;
            private String measurement_fee;
            private String measure_time;
            private String refused_time;
            private String selected_time;
            private String user_name;
            private String wk_cur_node_id;
            private String wk_current_step_id;
            private String wk_cur_sub_node_id;
            private String wk_id;
            private String style_names;
            private String design_price_max;
            private String design_price_min;

            private PaymentBean payment;
            private DesignContractBean design_contract;
            private List<OrdersBean> orders;
            /**
             * actionId : 21
             * name : pay_for_measure
             */

            private List<WkNextPossibleSubNodeIdsBean> wk_next_possible_sub_node_ids;
            private List<?> wk_steps;

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getDeclaration() {
                return declaration;
            }

            public void setDeclaration(String declaration) {
                this.declaration = declaration;
            }

            public Object getDelivery() {
                return delivery;
            }

            public void setDelivery(Object delivery) {
                this.delivery = delivery;
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

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public DesignContractBean getDesign_contract() {
                return design_contract;
            }

            public void setDesign_contract(DesignContractBean design_contract) {
                this.design_contract = design_contract;
            }

            public String getDesigner_id() {
                return designer_id;
            }

            public void setDesigner_id(String designer_id) {
                this.designer_id = designer_id;
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

            public String getMeasurement_fee() {
                return measurement_fee;
            }

            public void setMeasurement_fee(String measurement_fee) {
                this.measurement_fee = measurement_fee;
            }

            public String getMeasure_time() {
                return measure_time;
            }

            public void setMeasure_time(String measure_time) {
                this.measure_time = measure_time;
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

            public void setSelected_time(String selected_time) {
                this.selected_time = selected_time;
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

            public Object getWk_current_step_id() {
                return wk_current_step_id;
            }

            public void setWk_current_step_id(String wk_current_step_id) {
                this.wk_current_step_id = wk_current_step_id;
            }

            public String getWk_cur_sub_node_id() {
                return wk_cur_sub_node_id;
            }

            public void setWk_cur_sub_node_id(String wk_cur_sub_node_id) {
                this.wk_cur_sub_node_id = wk_cur_sub_node_id;
            }

            public String getWk_id() {
                return wk_id;
            }

            public void setWk_id(String wk_id) {
                this.wk_id = wk_id;
            }

            public String getStyle_names() {
                return style_names;
            }

            public void setStyle_names(String style_names) {
                this.style_names = style_names;
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

            public List<?> getWk_steps() {
                return wk_steps;
            }

            public void setWk_steps(List<?> wk_steps) {
                this.wk_steps = wk_steps;
            }

            public static class DesignContractBean implements Serializable {
                private String contract_charge;
                private String contract_create_date;
                // "contract_data": "{@jr@zip@jr@:@jr@@jr@,@jr@addrDe@jr@:@jr@猴子雨预谋小我了@jr@,@jr@email@jr@:@jr@@jr@,@jr@name@jr@:@jr@hetest001@jr@,@jr@render_map@jr@:@jr@1@jr@,@jr@addr@jr@:@jr@北京 北京市 东城区@jr@,@jr@design_sketch@jr@:@jr@1@jr@,@jr@design_sketch_plus@jr@:@jr@0.5@jr@,@jr@mobile@jr@:@jr@15298989898@jr@}",
                private String contract_data;
                private String contract_first_charge;
                private String contract_no;
                private String contract_status;
                private String contract_template_url;
                private String contract_type;
                private String contract_update_date;

                public String getContract_charge() {
                    return contract_charge;
                }

                public void setContract_charge(String contract_charge) {
                    this.contract_charge = contract_charge;
                }

                public String getContract_create_date() {
                    return contract_create_date;
                }

                public void setContract_create_date(String contract_create_date) {
                    this.contract_create_date = contract_create_date;
                }

                public String getContract_data() {
                    return contract_data;
                }

                public void setContract_data(String contract_data) {
                    this.contract_data = contract_data;
                }

                public String getContract_first_charge() {
                    return contract_first_charge;
                }

                public void setContract_first_charge(String contract_first_charge) {
                    this.contract_first_charge = contract_first_charge;
                }

                public String getContract_no() {
                    return contract_no;
                }

                public void setContract_no(String contract_no) {
                    this.contract_no = contract_no;
                }

                public String getContract_status() {
                    return contract_status;
                }

                public void setContract_status(String contract_status) {
                    this.contract_status = contract_status;
                }

                public String getContract_template_url() {
                    return contract_template_url;
                }

                public void setContract_template_url(String contract_template_url) {
                    this.contract_template_url = contract_template_url;
                }

                public String getContract_type() {
                    return contract_type;
                }

                public void setContract_type(String contract_type) {
                    this.contract_type = contract_type;
                }

                public String getContract_update_date() {
                    return contract_update_date;
                }

                public void setContract_update_date(String contract_update_date) {
                    this.contract_update_date = contract_update_date;
                }
            }

            public static class PaymentBean implements Serializable {
                private String create_date;
                private String measurement_fee;
                private String paid_fee;
                private String total_fee;
                private String unpaid_fee;

                public Object getCreate_date() {
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

            public static class OrdersBean  implements Serializable {
                private String order_line_no;
                private String order_no;
                private String order_status;
                private String order_type;

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

            public static class WkNextPossibleSubNodeIdsBean implements Serializable {
                private int actionId;
                private String name;

                public int getActionId() {
                    return actionId;
                }

                public void setActionId(int actionId) {
                    this.actionId = actionId;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }
    }
}
