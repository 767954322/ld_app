package com.autodesk.shejijia.consumer.personalcenter.designer.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 16-6-7
 * @file MyBidBean.java  .
 * @brief 我的应标.
 */
public class MyBidBean implements Serializable {

    private int count;
    private int limit;
    private int offset;
    private List<BiddingNeedsListEntity> bidding_needs_list;

    public void setCount(int count) {
        this.count = count;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setBidding_needs_list(List<BiddingNeedsListEntity> bidding_needs_list) {
        this.bidding_needs_list = bidding_needs_list;
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

    public List<BiddingNeedsListEntity> getBidding_needs_list() {
        return bidding_needs_list;
    }

    public static class BiddingNeedsListEntity implements Serializable {
        private String acs_member_id;
        /**
         * avatar : http://image.juranzaixian.com.cn:8082/img/570dbbb1e4b07d88ca9082ce.img
         * declaration :
         * delivery : []
         * design_contract : [{"contract_charge":"0.05","contract_create_date":1460514217677,"contract_data":"{@jr@name@jr@:@jr@LIUH@jr@,@jr@mobile@jr@:@jr@13718601763@jr@,@jr@zip@jr@:@jr@100001@jr@,@jr@email@jr@:@jr@26843683@qq.com@jr@,@jr@addr@jr@:@jr@北京市 北京市 东城区@jr@,@jr@addrDe@jr@:@jr@Android0413001@jr@,@jr@design_sketch@jr@:@jr@6@jr@,@jr@render_map@jr@:@jr@8@jr@,@jr@design_sketch_plus@jr@:@jr@0.01@jr@}","contract_first_charge":"0.04","contract_no":"LWEC-16041300002","contract_template_url":"www.baidu.com","contract_type":0,"designer_id":20730187}]
         * design_price_max : 60
         * design_price_min : 30
         * designer_id : 20730187
         * join_time : 2016-04-13 09:59:43
         * measure_time : 2016-04-13 23:05:00
         * measurement_fee : 0.01
         * orders : [{"designer_id":20730187,"order_line_no":"1917","order_no":"1245","order_status":"5","order_type":"1"},{"designer_id":20730187,"order_line_no":"1918","order_no":"1245","order_status":"6","order_type":"1"},{"designer_id":20730187,"order_line_no":"1913","order_no":"1245","order_status":"1","order_type":"0"}]
         * payment : {"create_date":"2016-04-11 10:34:19","measurement_fee":"0.04","paid_fee":"0.06","total_fee":"0.05","unpaid_fee":"0.0"}
         * selected_time : 2016-04-13 10:01:37
         * status : 1
         * style_names : 地中海,东南亚,古典,欧式,混搭
         * uid : c6efcfee-dfa2-475e-9f03-f943324a1a98
         * user_name : 白眉丽王
         * wk_cur_node_id : 5
         * wk_cur_sub_node_id : 51
         * wk_current_step_id : 60733
         * wk_id : 5791
         * wk_next_possible_sub_node_ids : []
         * wk_steps : [{"status":1,"thread_id":"T0QTJ2YIMY8SPRG","wk_step_id":60733}]
         */
        private BidderEntity bidder;
        private int bidder_count;
        private String decoration_budget;
        private String end_day;
        private String end_time;
        private String house_area;
        private String house_type;
        private String is_beishu;
        private String living_room;
        private String needs_id;
        private String needs_name;
        private String publish_time;
        private String renovation_budget;
        private String renovation_style;
        private String room;
        private String toilet;
        private String user_name;

        public void setAcs_member_id(String acs_member_id) {
            this.acs_member_id = acs_member_id;
        }

        public void setBidder(BidderEntity bidder) {
            this.bidder = bidder;
        }

        public void setBidder_count(int bidder_count) {
            this.bidder_count = bidder_count;
        }

        public void setDecoration_budget(String decoration_budget) {
            this.decoration_budget = decoration_budget;
        }

        public void setEnd_day(String end_day) {
            this.end_day = end_day;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
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

        public void setLiving_room(String living_room) {
            this.living_room = living_room;
        }

        public void setNeeds_id(String needs_id) {
            this.needs_id = needs_id;
        }

        public void setNeeds_name(String needs_name) {
            this.needs_name = needs_name;
        }

        public void setPublish_time(String publish_time) {
            this.publish_time = publish_time;
        }

        public void setRenovation_budget(String renovation_budget) {
            this.renovation_budget = renovation_budget;
        }

        public void setRenovation_style(String renovation_style) {
            this.renovation_style = renovation_style;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        public void setToilet(String toilet) {
            this.toilet = toilet;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getAcs_member_id() {
            return acs_member_id;
        }

        public BidderEntity getBidder() {
            return bidder;
        }

        public int getBidder_count() {
            return bidder_count;
        }

        public String getDecoration_budget() {
            return decoration_budget;
        }

        public String getEnd_day() {
            return end_day;
        }

        public String getEnd_time() {
            return end_time;
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

        public String getLiving_room() {
            return living_room;
        }

        public String getNeeds_id() {
            return needs_id;
        }

        public String getNeeds_name() {
            return needs_name;
        }

        public String getPublish_time() {
            return publish_time;
        }

        public String getRenovation_budget() {
            return renovation_budget;
        }

        public String getRenovation_style() {
            return renovation_style;
        }

        public String getRoom() {
            return room;
        }

        public String getToilet() {
            return toilet;
        }

        public String getUser_name() {
            return user_name;
        }

        @Override
        public String toString() {
            return "BiddingNeedsListEntity{" +
                    "acs_member_id='" + acs_member_id + '\'' +
                    ", bidder=" + bidder +
                    ", bidder_count=" + bidder_count +
                    ", decoration_budget='" + decoration_budget + '\'' +
                    ", end_day='" + end_day + '\'' +
                    ", end_time='" + end_time + '\'' +
                    ", house_area='" + house_area + '\'' +
                    ", house_type='" + house_type + '\'' +
                    ", is_beishu='" + is_beishu + '\'' +
                    ", living_room='" + living_room + '\'' +
                    ", needs_id=" + needs_id +
                    ", needs_name='" + needs_name + '\'' +
                    ", publish_time='" + publish_time + '\'' +
                    ", renovation_budget='" + renovation_budget + '\'' +
                    ", renovation_style='" + renovation_style + '\'' +
                    ", room='" + room + '\'' +
                    ", toilet='" + toilet + '\'' +
                    ", user_name='" + user_name + '\'' +
                    '}';
        }

        public static class BidderEntity implements Serializable {
            private String avatar;
            private String declaration;
            private int design_price_max;
            private int design_price_min;
            private int designer_id;
            private String design_thread_id;

            public String getDesign_thread_id() {
                return design_thread_id;
            }

            public void setDesign_thread_id(String design_thread_id) {
                this.design_thread_id = design_thread_id;
            }

            private String join_time;
            private String measure_time;
            private String measurement_fee;
            /**
             * create_date : 2016-04-11 10:34:19
             * measurement_fee : 0.04
             * paid_fee : 0.06
             * total_fee : 0.05
             * unpaid_fee : 0.0
             */

            private PaymentEntity payment;
            private String selected_time;
            private String status;
            private String style_names;
            private String uid;
            private String user_name;
            private String wk_cur_node_id;
            private String wk_cur_sub_node_id;
            private String wk_current_step_id;
            private String wk_id;
            /**
             * contract_charge : 0.05
             * contract_create_date : 1460514217677
             * contract_data : {@jr@name@jr@:@jr@LIUH@jr@,@jr@mobile@jr@:@jr@13718601763@jr@,@jr@zip@jr@:@jr@100001@jr@,@jr@email@jr@:@jr@26843683@qq.com@jr@,@jr@addr@jr@:@jr@北京市 北京市 东城区@jr@,@jr@addrDe@jr@:@jr@Android0413001@jr@,@jr@design_sketch@jr@:@jr@6@jr@,@jr@render_map@jr@:@jr@8@jr@,@jr@design_sketch_plus@jr@:@jr@0.01@jr@}
             * contract_first_charge : 0.04
             * contract_no : LWEC-16041300002
             * contract_template_url : www.baidu.com
             * contract_type : 0
             * designer_id : 20730187
             */

            private List<DesignContractEntity> design_contract;
            /**
             * designer_id : 20730187
             * order_line_no : 1917
             * order_no : 1245
             * order_status : 5
             * order_type : 1
             */

            private List<OrdersEntity> orders;
            /**
             * status : 1
             * thread_id : T0QTJ2YIMY8SPRG
             * wk_step_id : 60733
             */

            private List<WkStepsEntity> wk_steps;

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public void setDeclaration(String declaration) {
                this.declaration = declaration;
            }

            public void setDesign_price_max(int design_price_max) {
                this.design_price_max = design_price_max;
            }

            public void setDesign_price_min(int design_price_min) {
                this.design_price_min = design_price_min;
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

            public void setPayment(PaymentEntity payment) {
                this.payment = payment;
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

            public void setWk_current_step_id(String wk_current_step_id) {
                this.wk_current_step_id = wk_current_step_id;
            }

            public void setWk_id(String wk_id) {
                this.wk_id = wk_id;
            }


            public void setDesign_contract(List<DesignContractEntity> design_contract) {
                this.design_contract = design_contract;
            }

            public void setOrders(List<OrdersEntity> orders) {
                this.orders = orders;
            }


            public void setWk_steps(List<WkStepsEntity> wk_steps) {
                this.wk_steps = wk_steps;
            }

            public String getAvatar() {
                return avatar;
            }

            public String getDeclaration() {
                return declaration;
            }

            public int getDesign_price_max() {
                return design_price_max;
            }

            public int getDesign_price_min() {
                return design_price_min;
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

            public PaymentEntity getPayment() {
                return payment;
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

            public String getWk_current_step_id() {
                return wk_current_step_id;
            }

            public String getWk_id() {
                return wk_id;
            }


            public List<DesignContractEntity> getDesign_contract() {
                return design_contract;
            }

            public List<OrdersEntity> getOrders() {
                return orders;
            }

            public List<WkStepsEntity> getWk_steps() {
                return wk_steps;
            }

            public static class PaymentEntity implements Serializable {
                private String create_date;
                private String measurement_fee;
                private String paid_fee;
                private String total_fee;
                private String unpaid_fee;

                public void setCreate_date(String create_date) {
                    this.create_date = create_date;
                }

                public void setMeasurement_fee(String measurement_fee) {
                    this.measurement_fee = measurement_fee;
                }

                public void setPaid_fee(String paid_fee) {
                    this.paid_fee = paid_fee;
                }

                public void setTotal_fee(String total_fee) {
                    this.total_fee = total_fee;
                }

                public void setUnpaid_fee(String unpaid_fee) {
                    this.unpaid_fee = unpaid_fee;
                }

                public String getCreate_date() {
                    return create_date;
                }

                public String getMeasurement_fee() {
                    return measurement_fee;
                }

                public String getPaid_fee() {
                    return paid_fee;
                }

                public String getTotal_fee() {
                    return total_fee;
                }

                public String getUnpaid_fee() {
                    return unpaid_fee;
                }
            }

            public static class DesignContractEntity implements Serializable {
                private String contract_charge;
                private long contract_create_date;
                private String contract_data;
                private String contract_first_charge;
                private String contract_no;
                private String contract_template_url;
                private int contract_type;
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

                public void setContract_template_url(String contract_template_url) {
                    this.contract_template_url = contract_template_url;
                }

                public void setContract_type(int contract_type) {
                    this.contract_type = contract_type;
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

                public String getContract_template_url() {
                    return contract_template_url;
                }

                public int getContract_type() {
                    return contract_type;
                }

                public int getDesigner_id() {
                    return designer_id;
                }
            }

            public static class OrdersEntity implements Serializable {
                private int designer_id;
                private String order_line_no;
                private String order_no;
                private String order_status;
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

                public void setOrder_status(String order_status) {
                    this.order_status = order_status;
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

                public String getOrder_status() {
                    return order_status;
                }

                public String getOrder_type() {
                    return order_type;
                }
            }

            public static class WkStepsEntity implements Serializable {
                private int status;
                private String thread_id;
                private int wk_step_id;

                public void setStatus(int status) {
                    this.status = status;
                }

                public void setThread_id(String thread_id) {
                    this.thread_id = thread_id;
                }

                public void setWk_step_id(int wk_step_id) {
                    this.wk_step_id = wk_step_id;
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

            @Override
            public String toString() {
                return "BidderEntity{" +
                        "avatar='" + avatar + '\'' +
                        ", declaration='" + declaration + '\'' +
                        ", design_price_max=" + design_price_max +
                        ", design_price_min=" + design_price_min +
                        ", designer_id=" + designer_id +
                        ", join_time='" + join_time + '\'' +
                        ", measure_time='" + measure_time + '\'' +
                        ", measurement_fee='" + measurement_fee + '\'' +
                        ", payment=" + payment +
                        ", selected_time='" + selected_time + '\'' +
                        ", status='" + status + '\'' +
                        ", style_names='" + style_names + '\'' +
                        ", uid='" + uid + '\'' +
                        ", user_name='" + user_name + '\'' +
                        ", wk_cur_node_id='" + wk_cur_node_id + '\'' +
                        ", wk_cur_sub_node_id='" + wk_cur_sub_node_id + '\'' +
                        ", wk_current_step_id='" + wk_current_step_id + '\'' +
                        ", wk_id='" + wk_id + '\'' +
                        ",  design_contract=" + design_contract +
                        ", orders=" + orders +
                        ", wk_steps=" + wk_steps +
                        '}';
            }
        }
    }
}
