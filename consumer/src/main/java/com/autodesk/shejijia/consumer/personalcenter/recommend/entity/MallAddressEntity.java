package com.autodesk.shejijia.consumer.personalcenter.recommend.entity;

import java.util.List;

/**
 * @User: 蜡笔小新
 * @date: 16-10-27
 * @GitHub: https://github.com/meikoz
 */

public class MallAddressEntity {

    private List<MallAddressesBean> mall_addresses;

    public List<MallAddressesBean> getMall_addresses() {
        return mall_addresses;
    }

    public void setMall_addresses(List<MallAddressesBean> mall_addresses) {
        this.mall_addresses = mall_addresses;
    }

    public static class MallAddressesBean {
        private String store_address;
        private String mall_address;
        private String business_hours;
        private String route_line;
        private String mall_phone;
        private String store_name;

        public String getStore_address() {
            return store_address;
        }

        public void setStore_address(String store_address) {
            this.store_address = store_address;
        }

        public String getMall_address() {
            return mall_address;
        }

        public void setMall_address(String mall_address) {
            this.mall_address = mall_address;
        }

        public String getBusiness_hours() {
            return business_hours;
        }

        public void setBusiness_hours(String business_hours) {
            this.business_hours = business_hours;
        }

        public String getRoute_line() {
            return route_line;
        }

        public void setRoute_line(String route_line) {
            this.route_line = route_line;
        }

        public String getMall_phone() {
            return mall_phone;
        }

        public void setMall_phone(String mall_phone) {
            this.mall_phone = mall_phone;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }
    }
}
