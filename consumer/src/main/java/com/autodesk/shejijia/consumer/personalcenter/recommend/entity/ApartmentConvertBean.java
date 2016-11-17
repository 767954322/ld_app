package com.autodesk.shejijia.consumer.personalcenter.recommend.entity;

import java.util.List;

/**
 * 空间转换实体类
 *
 * @author liuhea
 *         created at 16-11-17
 */

public class ApartmentConvertBean {

    private List<ApartmentListBean> apartment_list;

    public List<ApartmentListBean> getApartment_list() {
        return apartment_list;
    }

    public void setApartment_list(List<ApartmentListBean> apartment_list) {
        this.apartment_list = apartment_list;
    }

    public static class ApartmentListBean {
        /**
         * id : 01
         * name : 客厅
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
