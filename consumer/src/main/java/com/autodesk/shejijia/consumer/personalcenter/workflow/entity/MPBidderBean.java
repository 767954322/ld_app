package com.autodesk.shejijia.consumer.personalcenter.workflow.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author   he.liu .
 * @version  v1.0 .
 * @date       2016-6-23 .
 * @file          MPBidderBean.java .
 * @brief       全流程设计师信息实体类 .
 */
public class MPBidderBean implements Serializable {
    private String avatar;
    private String declaration;
    private String status;
    private String uid;
    private String designer_id;
    private String design_thread_id;
    private String join_time;
    private String measurement_fee;
    private String measure_time;
    private String refused_time;
    private String user_name;
    private String selected_time;
    private String wk_cur_node_id;
    private String wk_cur_sub_node_id;
    private String wk_current_step_id;
    private String wk_id;
    private String style_names;
    private String design_price_max;
    private String design_price_min;

    public int getIs_real_name() {
        return is_real_name;
    }

    public void setIs_real_name(int is_real_name) {
        this.is_real_name = is_real_name;
    }

    private int is_real_name;

    private MPPaymentBean payment;
    private MPDeliveryBean delivery;
    private MPDesignContractBean design_contract;
    private List<MPOrderBean> orders;
    private List<MPWkNextPossibleSubNodeIdBean> wk_next_possible_sub_node_ids;
    private List<MPWkStepBean> wk_steps;

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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getSelected_time() {
        return selected_time;
    }

    public void setSelected_time(String selected_time) {
        this.selected_time = selected_time;
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

    public String getWk_current_step_id() {
        return wk_current_step_id;
    }

    public void setWk_current_step_id(String wk_current_step_id) {
        this.wk_current_step_id = wk_current_step_id;
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

    public MPPaymentBean getPayment() {
        return payment;
    }

    public void setPayment(MPPaymentBean payment) {
        this.payment = payment;
    }

    public MPDeliveryBean getDelivery() {
        return delivery;
    }

    public void setDelivery(MPDeliveryBean delivery) {
        this.delivery = delivery;
    }

    public List<MPOrderBean> getOrders() {
        return orders;
    }

    public void setOrders(List<MPOrderBean> orders) {
        this.orders = orders;
    }

    public MPDesignContractBean getDesign_contract() {
        return design_contract;
    }

    public void setDesign_contract(MPDesignContractBean design_contract) {
        this.design_contract = design_contract;
    }

    public List<MPWkNextPossibleSubNodeIdBean> getWk_next_possible_sub_node_ids() {
        return wk_next_possible_sub_node_ids;
    }

    public void setWk_next_possible_sub_node_ids(List<MPWkNextPossibleSubNodeIdBean> wk_next_possible_sub_node_ids) {
        this.wk_next_possible_sub_node_ids = wk_next_possible_sub_node_ids;
    }

    public List<MPWkStepBean> getWk_steps() {
        return wk_steps;
    }

    public void setWk_steps(List<MPWkStepBean> wk_steps) {
        this.wk_steps = wk_steps;
    }
}
