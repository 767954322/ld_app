package com.autodesk.shejijia.consumer.personalcenter.recommend.entity;

import java.util.List;

/**
 * @User: 蜡笔小新
 * @date: 16-10-27
 * @GitHub: https://github.com/meikoz
 */

public class MallAddressEntity {

    private List<MallAddressesBean> juran_storefront_info;

    public List<MallAddressesBean> getJuran_storefront_info() {
        return juran_storefront_info;
    }

    public void setJuran_storefront_info(List<MallAddressesBean> juran_storefront_info) {
        this.juran_storefront_info = juran_storefront_info;
    }

    public static class MallAddressesBean {
        private String storefront_address;
        private String detailed_address;
        private String business_hours;
        private String ride_route;
        private String booth_phone;
        private String storefront_name;

        public String getStorefront_address() {
            return storefront_address;
        }

        public void setStorefront_address(String storefront_address) {
            this.storefront_address = storefront_address;
        }

        public String getDetailed_address() {
            return detailed_address;
        }

        public void setDetailed_address(String detailed_address) {
            this.detailed_address = detailed_address;
        }

        public String getBusiness_hours() {
            return business_hours;
        }

        public void setBusiness_hours(String business_hours) {
            this.business_hours = business_hours;
        }

        public String getRide_route() {
            return ride_route;
        }

        public void setRide_route(String ride_route) {
            this.ride_route = ride_route;
        }

        public String getBooth_phone() {
            return booth_phone;
        }

        public void setBooth_phone(String booth_phone) {
            this.booth_phone = booth_phone;
        }

        public String getStorefront_name() {
            return storefront_name;
        }

        public void setStorefront_name(String storefront_name) {
            this.storefront_name = storefront_name;
        }
    }
}
