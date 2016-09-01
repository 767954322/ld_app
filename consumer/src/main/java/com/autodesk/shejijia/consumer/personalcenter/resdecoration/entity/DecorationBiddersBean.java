package com.autodesk.shejijia.consumer.personalcenter.resdecoration.entity;

import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPDeliveryBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPDesignContractBean;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Description:家装订单设计师实体类</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: http://www.leediancn.com</p>
 *
 * @author he.liu .
 * @date 2016-07-17.
 */
public class DecorationBiddersBean implements Serializable {
    private String avatar;
    private String declaration;
    private String status;
    private String uid;
    private String designer_id;
    private String design_thread_id;
    private String join_time;
    private String measurement_fee;
    private String measurement_status;
    private String measure_time;
    private String refused_time;
    private String selected_time;
    private String user_name;
    private String wk_cur_node_id;
    private String wk_current_step_id;
    private String wk_cur_sub_node_id;  //  11,12
    private String wk_id;
    private String style_names;
    private String design_price_max;
    private String design_price_min;
    public String following_count;

    private PaymentBean payment;
    private MPDeliveryBean delivery;
    private List<MPDesignContractBean> design_contract;
    private List<OrdersBean> orders;
    private List<WkNextPossibleSubNodeIdsBean> wk_next_possible_sub_node_ids;
    private List<WkStepsEntity> wk_steps;

    public String getMeasurement_status() {
        return measurement_status;
    }

    public void setMeasurement_status(String measurement_status) {
        this.measurement_status = measurement_status;
    }

    public String getFollowing_count() {
        return following_count;
    }

    public void setFollowing_count(String following_count) {
        this.following_count = following_count;
    }

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

    public MPDeliveryBean getDelivery() {
        return delivery;
    }

    public void setDelivery(MPDeliveryBean delivery) {
        this.delivery = delivery;
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

    public String getWk_current_step_id() {
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

    public PaymentBean getPayment() {
        return payment;
    }

    public void setPayment(PaymentBean payment) {
        this.payment = payment;
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

    public List<WkStepsEntity> getWk_steps() {
        return wk_steps;
    }

    public void setWk_steps(List<WkStepsEntity> wk_steps) {
        this.wk_steps = wk_steps;
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

    public static class OrdersBean implements Serializable {
        private String order_line_no;
        private String order_no;
        private String order_status;
        private String order_type;
        private String designer_id;

        public String getDesigner_id() {
            return designer_id;
        }

        public void setDesigner_id(String designer_id) {
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

    public static class WkStepsEntity implements Serializable {
        private Object files;
        private int status;
        private String thread_id;
        private String previous_lastest_msg_id;
        private String timestam_end;
        private String timestamp_start;
        private String wk_step_id;

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

        public void setFiles(Object files) {
            this.files = files;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setThread_id(String thread_id) {
            this.thread_id = thread_id;
        }

        public void setWk_step_id(String wk_step_id) {
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

        public String getWk_step_id() {
            return wk_step_id;
        }
    }
}
