package com.autodesk.shejijia.consumer.personalcenter.recommend.entity;

import java.util.List;

/**
 * @User: 蜡笔小新
 * @date: 16-10-25
 * @GitHub: https://github.com/meikoz
 */

public class ScfdEntity {

    private String category_3d_id;
    private String category_3d_name;
    private String sub_category_3d_id;
    private String sub_category_3d_name;
    private String source;
    private String $$hashKey;

    private List<BrandsBean> brands;

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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String get$$hashKey() {
        return $$hashKey;
    }

    public void set$$hashKey(String $$hashKey) {
        this.$$hashKey = $$hashKey;
    }

    public List<BrandsBean> getBrands() {
        return brands;
    }

    public void setBrands(List<BrandsBean> brands) {
        this.brands = brands;
    }

    public static class BrandsBean {
        private String brand_name;
        private String code;
        private String logo_url;
        private String $$hashKey;
        private String remarks;
        private String amountAndUnit;
        private String dimension;
        private String apartment;
        /**
         * mall_name : ????
         * mall_number : DS2
         * $$hashKey : 03G
         */

        private List<MallsBean> malls;

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getLogo_url() {
            return logo_url;
        }

        public void setLogo_url(String logo_url) {
            this.logo_url = logo_url;
        }

        public String get$$hashKey() {
            return $$hashKey;
        }

        public void set$$hashKey(String $$hashKey) {
            this.$$hashKey = $$hashKey;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getAmountAndUnit() {
            return amountAndUnit;
        }

        public void setAmountAndUnit(String amountAndUnit) {
            this.amountAndUnit = amountAndUnit;
        }

        public String getDimension() {
            return dimension;
        }

        public void setDimension(String dimension) {
            this.dimension = dimension;
        }

        public String getApartment() {
            return apartment;
        }

        public void setApartment(String apartment) {
            this.apartment = apartment;
        }

        public List<MallsBean> getMalls() {
            return malls;
        }

        public void setMalls(List<MallsBean> malls) {
            this.malls = malls;
        }

        public static class MallsBean {
            private String mall_name;
            private String mall_number;
            private String $$hashKey;

            public String getMall_name() {
                return mall_name;
            }

            public void setMall_name(String mall_name) {
                this.mall_name = mall_name;
            }

            public String getMall_number() {
                return mall_number;
            }

            public void setMall_number(String mall_number) {
                this.mall_number = mall_number;
            }

            public String get$$hashKey() {
                return $$hashKey;
            }

            public void set$$hashKey(String $$hashKey) {
                this.$$hashKey = $$hashKey;
            }
        }
    }
}
