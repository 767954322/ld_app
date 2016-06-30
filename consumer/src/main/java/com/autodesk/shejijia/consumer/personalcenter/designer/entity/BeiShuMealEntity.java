package com.autodesk.shejijia.consumer.personalcenter.designer.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dongxueqiu on 16-6-18.
 */
public class BeiShuMealEntity implements Serializable{

    /**
     * city_name : åäº¬å¸
     * wk_template_id : 3
     * community_name : jjk
     * needs_id : 1583925
     * contract : null
     * workflow_step_id : null
     * design_asset_id : null
     * contacts_mobile : 15566666988
     * bidder_list : [{"uid":"a25fd718-348b-4021-becc-f3c5d0399141","wk_cur_sub_node_id":"14","declaration":null,"payment":null,"status":"1","wk_current_step_id":null,"measure_time":null,"wk_steps":null,"selected_time":null,"wk_id":"13056","designer_id":20730187,"wk_cur_node_id":"1","refused_time":null,"design_contract":null,"measurement_fee":"0","join_time":"2016-06-18 18:28:17","orders":null,"design_thread_id":null,"current_actions":null,"wk_next_possible_sub_node_ids":null}]
     * avatar : null
     * city : 110100
     * province_name : åäº¬
     * publish_time : 2016-06-18 18:28:16
     * order : null
     * district_name : ä¸ååº
     * hs_design_id : null
     * workflow_id : null
     * province : 110000
     * beishu_thread_id : T64O7BQIPL0TX6A
     * district : 110101
     * is_beishu : 0
     * customer_id : 20730177
     * contacts_name : hsingchiang.lin
     */

    private String city_name;
    private String wk_template_id;
    private String community_name;
    private String needs_id;
    private Object contract;
    private Object workflow_step_id;
    private Object design_asset_id;
    private String contacts_mobile;
    private Object avatar;
    private String city;
    private String province_name;
    private String publish_time;
    private Object order;
    private String district_name;
    private String hs_design_id;
    private Object workflow_id;
    private String province;
    private String beishu_thread_id;
    private String district;
    private String is_beishu;
    private String customer_id;
    private String contacts_name;
    /**
     * uid : a25fd718-348b-4021-becc-f3c5d0399141
     * wk_cur_sub_node_id : 14
     * declaration : null
     * payment : null
     * status : 1
     * wk_current_step_id : null
     * measure_time : null
     * wk_steps : null
     * selected_time : null
     * wk_id : 13056
     * designer_id : 20730187
     * wk_cur_node_id : 1
     * refused_time : null
     * design_contract : null
     * measurement_fee : 0
     * join_time : 2016-06-18 18:28:17
     * orders : null
     * design_thread_id : null
     * current_actions : null
     * wk_next_possible_sub_node_ids : null
     */

    private List<BidderListBean> bidder_list;

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
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

    public String getNeeds_id() {
        return needs_id;
    }

    public void setNeeds_id(String needs_id) {
        this.needs_id = needs_id;
    }

    public Object getContract() {
        return contract;
    }

    public void setContract(Object contract) {
        this.contract = contract;
    }

    public Object getWorkflow_step_id() {
        return workflow_step_id;
    }

    public void setWorkflow_step_id(Object workflow_step_id) {
        this.workflow_step_id = workflow_step_id;
    }

    public Object getDesign_asset_id() {
        return design_asset_id;
    }

    public void setDesign_asset_id(Object design_asset_id) {
        this.design_asset_id = design_asset_id;
    }

    public String getContacts_mobile() {
        return contacts_mobile;
    }

    public void setContacts_mobile(String contacts_mobile) {
        this.contacts_mobile = contacts_mobile;
    }

    public Object getAvatar() {
        return avatar;
    }

    public void setAvatar(Object avatar) {
        this.avatar = avatar;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public Object getOrder() {
        return order;
    }

    public void setOrder(Object order) {
        this.order = order;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getHs_design_id() {
        return hs_design_id;
    }

    public void setHs_design_id(String hs_design_id) {
        this.hs_design_id = hs_design_id;
    }

    public Object getWorkflow_id() {
        return workflow_id;
    }

    public void setWorkflow_id(Object workflow_id) {
        this.workflow_id = workflow_id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getBeishu_thread_id() {
        return beishu_thread_id;
    }

    public void setBeishu_thread_id(String beishu_thread_id) {
        this.beishu_thread_id = beishu_thread_id;
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

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getContacts_name() {
        return contacts_name;
    }

    public void setContacts_name(String contacts_name) {
        this.contacts_name = contacts_name;
    }

    public List<BidderListBean> getBidder_list() {
        return bidder_list;
    }

    public void setBidder_list(List<BidderListBean> bidder_list) {
        this.bidder_list = bidder_list;
    }

    public static class BidderListBean {
        private String uid;
        private String wk_cur_sub_node_id;
        private Object declaration;
        private Object payment;
        private String status;
        private Object wk_current_step_id;
        private Object measure_time;
        private Object wk_steps;
        private Object selected_time;
        private String wk_id;
        private String designer_id;
        private String wk_cur_node_id;
        private Object refused_time;
        private Object design_contract;
        private String measurement_fee;
        private String join_time;
        private Object orders;
        private Object design_thread_id;
        private Object current_actions;
        private Object wk_next_possible_sub_node_ids;

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

        public Object getDeclaration() {
            return declaration;
        }

        public void setDeclaration(Object declaration) {
            this.declaration = declaration;
        }

        public Object getPayment() {
            return payment;
        }

        public void setPayment(Object payment) {
            this.payment = payment;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getWk_current_step_id() {
            return wk_current_step_id;
        }

        public void setWk_current_step_id(Object wk_current_step_id) {
            this.wk_current_step_id = wk_current_step_id;
        }

        public Object getMeasure_time() {
            return measure_time;
        }

        public void setMeasure_time(Object measure_time) {
            this.measure_time = measure_time;
        }

        public Object getWk_steps() {
            return wk_steps;
        }

        public void setWk_steps(Object wk_steps) {
            this.wk_steps = wk_steps;
        }

        public Object getSelected_time() {
            return selected_time;
        }

        public void setSelected_time(Object selected_time) {
            this.selected_time = selected_time;
        }

        public String getWk_id() {
            return wk_id;
        }

        public void setWk_id(String wk_id) {
            this.wk_id = wk_id;
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

        public Object getRefused_time() {
            return refused_time;
        }

        public void setRefused_time(Object refused_time) {
            this.refused_time = refused_time;
        }

        public Object getDesign_contract() {
            return design_contract;
        }

        public void setDesign_contract(Object design_contract) {
            this.design_contract = design_contract;
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

        public Object getOrders() {
            return orders;
        }

        public void setOrders(Object orders) {
            this.orders = orders;
        }

        public Object getDesign_thread_id() {
            return design_thread_id;
        }

        public void setDesign_thread_id(Object design_thread_id) {
            this.design_thread_id = design_thread_id;
        }

        public Object getCurrent_actions() {
            return current_actions;
        }

        public void setCurrent_actions(Object current_actions) {
            this.current_actions = current_actions;
        }

        public Object getWk_next_possible_sub_node_ids() {
            return wk_next_possible_sub_node_ids;
        }

        public void setWk_next_possible_sub_node_ids(Object wk_next_possible_sub_node_ids) {
            this.wk_next_possible_sub_node_ids = wk_next_possible_sub_node_ids;
        }
    }
}
