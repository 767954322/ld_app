package com.autodesk.shejijia.consumer.personalcenter.recommend.entity;

import java.util.List;

/**
 * Created by yaoxuehua on 16-10-27.
 */

public class ShowBrandsBean {


    /**
     * count : null
     * category_3d_id :
     * category_3d_name :
     * sub_category_3d_id :
     * sub_category_3d_name :
     * brands : [{"malls":[{"mall_name":"金源店","mall_number":"DS4","booth_number":null,"booth_name":null},{"mall_name":"大兴店","mall_number":"DS74","booth_number":null,"booth_name":null}],"brand_name":"酷雅可爱多","code":"00451901","logo_url":"http://image1.juran.cn/img/543dc6ea498e65e70918cf7e.img"},{"malls":[{"mall_name":"丽泽店","mall_number":"DS25","booth_number":null,"booth_name":null},{"mall_name":"大兴店","mall_number":"DS74","booth_number":null,"booth_name":null}],"brand_name":"马可波罗","code":"00001003","logo_url":"http://image1.juran.cn/img/5257aefe498e46b0f16dd3d7.img"},{"malls":[{"mall_name":"十里河店","mall_number":"DS2","booth_number":null,"booth_name":null},{"mall_name":"丽泽店","mall_number":"DS25","booth_number":null,"booth_name":null}],"brand_name":"哈利魔画","code":"00001004","logo_url":"http://image1.juran.cn/img/543ba8ad498e905d7cfd2516.img"},{"malls":[{"mall_name":null,"mall_number":null,"booth_number":null,"booth_name":null},{"mall_name":"大兴店","mall_number":"DS74","booth_number":null,"booth_name":null}],"brand_name":"拉菲德堡","code":"00001005","logo_url":"http://image1.juran.cn/img/53929cd2498e65e70909b490.img"},{"malls":[{"mall_name":"丽泽店","mall_number":"DS25","booth_number":null,"booth_name":null},{"mall_name":"北四环店","mall_number":"DS1","booth_number":null,"booth_name":null}],"brand_name":"东方印象","code":"00001006","logo_url":"http://image1.juran.cn/img/5268a3cf498e46b0f16f33ae.img"},{"malls":[{"mall_name":"大兴店","mall_number":"DS74","booth_number":null,"booth_name":null}],"brand_name":"蒙娜丽莎","code":"00001007","logo_url":"http://image1.juran.cn/img/525e5cca498e46b0f16e5b2b.img"},{"malls":[{"mall_name":"十里河店","mall_number":"DS2","booth_number":null,"booth_name":null},{"mall_name":"玉泉营店","mall_number":"DS3","booth_number":null,"booth_name":null},{"mall_name":"金源店","mall_number":"DS4","booth_number":null,"booth_name":null}],"brand_name":"圣象","code":"00001008","logo_url":"http://image1.juran.cn/img/5247ec30498e483d59518f1e.img"},{"malls":[{"mall_name":"顺义店","mall_number":"DS57","booth_number":null,"booth_name":null}],"brand_name":"皇家丽景","code":"00001009","logo_url":"http://image1.juran.cn/img/54896767498e74f7718c0254.img"},{"malls":[{"mall_name":"丽泽店","mall_number":"DS25","booth_number":null,"booth_name":null},{"mall_name":"大兴店","mall_number":"DS74","booth_number":null,"booth_name":null},{"mall_name":"八角店","mall_number":"DS76","booth_number":null,"booth_name":null},{"mall_name":"北四环店","mall_number":"DS1","booth_number":null,"booth_name":null}],"brand_name":"英伦华庄","code":"00001010","logo_url":"http://image1.juran.cn/img/52552f6b498e46b0f16db74e.img"}]
     */

    private Object count;
    private String category_3d_id;
    private String category_3d_name;
    private String sub_category_3d_id;
    private String sub_category_3d_name;
    /**
     * malls : [{"mall_name":"金源店","mall_number":"DS4","booth_number":null,"booth_name":null},{"mall_name":"大兴店","mall_number":"DS74","booth_number":null,"booth_name":null}]
     * brand_name : 酷雅可爱多
     * code : 00451901
     * logo_url : http://image1.juran.cn/img/543dc6ea498e65e70918cf7e.img
     */

    private List<BrandsBean> brands;

    public Object getCount() {
        return count;
    }

    public void setCount(Object count) {
        this.count = count;
    }

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
        /**
         * mall_name : 金源店
         * mall_number : DS4
         * booth_number : null
         * booth_name : null
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

        public List<MallsBean> getMalls() {
            return malls;
        }

        public void setMalls(List<MallsBean> malls) {
            this.malls = malls;
        }

        public static class MallsBean {
            private String mall_name;
            private String mall_number;
            private Object booth_number;
            private Object booth_name;

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

            public Object getBooth_number() {
                return booth_number;
            }

            public void setBooth_number(Object booth_number) {
                this.booth_number = booth_number;
            }

            public Object getBooth_name() {
                return booth_name;
            }

            public void setBooth_name(Object booth_name) {
                this.booth_name = booth_name;
            }
        }
    }
}
