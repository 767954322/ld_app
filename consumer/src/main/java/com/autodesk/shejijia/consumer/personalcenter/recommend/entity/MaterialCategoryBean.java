package com.autodesk.shejijia.consumer.personalcenter.recommend.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author yaoxuehua .
 * @version v1.0 .
 * @date 16-11-3 .
 * @file MaterialCategoryBean.java .
 * @brief 一二级品类信息BEAN.
 */

public class MaterialCategoryBean implements Serializable{

    /**
     * category_3d_id : 1df3e3a8-15b0-4647-bd69-e64350cab281
     * category_3d_name : 硬装
     * sub_category : [{"sub_category_3d_id":"3c79b519-10c2-4f3c-9b78-7378c832316c","sub_category_3d_name":"背景墙"},{"sub_category_3d_id":"50ec0298-47f9-4d91-ae03-579423ae6fc6","sub_category_3d_name":"吊顶"},{"sub_category_3d_id":"ccb93c54-00cf-4db4-b858-f3aabe44f3e6","sub_category_3d_name":"梁/柱"},{"sub_category_3d_id":"df6f234c-c1bb-4e4a-8d64-8ef5bc1d8073","sub_category_3d_name":"墙洞"},{"sub_category_3d_id":"670cc193-5734-4506-9d44-a26382c32047","sub_category_3d_name":"装饰线条"},{"sub_category_3d_id":"b6c01c27-802d-4c9b-8e45-f56bbc19f16a","sub_category_3d_name":"护墙板"},{"sub_category_3d_id":"93d3d1ff-aab3-4041-aa15-bd6ac7ef961a","sub_category_3d_name":"地台"},{"sub_category_3d_id":"0b922760-8c77-4a68-beef-86d8b3bb3289","sub_category_3d_name":"门窗套线"},{"sub_category_3d_id":"8e901adf-8e4e-43d5-9f51-9a6cac5b6923","sub_category_3d_name":"踢脚线"},{"sub_category_3d_id":"2944eb20-5072-4457-a64d-a0a1d6e2e858","sub_category_3d_name":"石膏线"}]
     */

    private List<Categories3dBean> categories_3d;

    public List<Categories3dBean> getCategories_3d() {
        return categories_3d;
    }

    public void setCategories_3d(List<Categories3dBean> categories_3d) {
        this.categories_3d = categories_3d;
    }

    public static class Categories3dBean implements Serializable{
        private String category_3d_id;
        private String category_3d_name;
        /**
         * sub_category_3d_id : 3c79b519-10c2-4f3c-9b78-7378c832316c
         * sub_category_3d_name : 背景墙
         */

        private List<SubCategoryBean> sub_category;

        public String getCategory_3d_id() {
            return category_3d_id;
        }

        public void setCategory_3d_id(String category_3d_id) {
            this.category_3d_id = category_3d_id;
        }

        public String getCategory_3d_name() {
            return category_3d_name;
        }

        public void setCategory_3d_name(String category_3d_name) {
            this.category_3d_name = category_3d_name;
        }

        public List<SubCategoryBean> getSub_category() {
            return sub_category;
        }

        public void setSub_category(List<SubCategoryBean> sub_category) {
            this.sub_category = sub_category;
        }

        public static class SubCategoryBean implements Serializable{
            private String sub_category_3d_id;
            private String sub_category_3d_name;

            public String getSub_category_3d_id() {
                return sub_category_3d_id;
            }

            public void setSub_category_3d_id(String sub_category_3d_id) {
                this.sub_category_3d_id = sub_category_3d_id;
            }

            public String getSub_category_3d_name() {
                return sub_category_3d_name;
            }

            public void setSub_category_3d_name(String sub_category_3d_name) {
                this.sub_category_3d_name = sub_category_3d_name;
            }
        }
    }
}
