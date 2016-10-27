package com.autodesk.shejijia.consumer.personalcenter.recommend.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yaoxuehua on 16-10-24.
 */

public class StoreInformationBean {


    /**
     * longitude : 116.44378
     * latitude : 39.996483
     * mall_number : DS1
     * mall_name : 北四环店
     * province_code : 110000
     * province_name : 北京
     * city_code : 110100
     * city_name : 北京市
     * district_code : 110105
     * district_name : 朝阳区
     * address : 北京市朝阳区北四环东路65号
     * sale_region_code : 000001
     * sale_region_name : 北京
     */

    private List<StoreListBean> store_list;

    public List<StoreListBean> getStore_list() {
        return store_list;
    }

    public void setStore_list(List<StoreListBean> store_list) {
        this.store_list = store_list;
    }

    public static class StoreListBean implements Parcelable {
        private String longitude;
        private String latitude;
        private String mall_number;
        private String mall_name;
        private String province_code;
        private String province_name;
        private String city_code;
        private String city_name;
        private String district_code;
        private String district_name;
        private String address;
        private String sale_region_code;
        private String sale_region_name;

        protected StoreListBean(Parcel in) {
            longitude = in.readString();
            latitude = in.readString();
            mall_number = in.readString();
            mall_name = in.readString();
            province_code = in.readString();
            province_name = in.readString();
            city_code = in.readString();
            city_name = in.readString();
            district_code = in.readString();
            district_name = in.readString();
            address = in.readString();
            sale_region_code = in.readString();
            sale_region_name = in.readString();
        }

        public static final Creator<StoreListBean> CREATOR = new Creator<StoreListBean>() {
            @Override
            public StoreListBean createFromParcel(Parcel in) {
                return new StoreListBean(in);
            }

            @Override
            public StoreListBean[] newArray(int size) {
                return new StoreListBean[size];
            }
        };

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getMall_number() {
            return mall_number;
        }

        public void setMall_number(String mall_number) {
            this.mall_number = mall_number;
        }

        public String getMall_name() {
            return mall_name;
        }

        public void setMall_name(String mall_name) {
            this.mall_name = mall_name;
        }

        public String getProvince_code() {
            return province_code;
        }

        public void setProvince_code(String province_code) {
            this.province_code = province_code;
        }

        public String getProvince_name() {
            return province_name;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }

        public String getCity_code() {
            return city_code;
        }

        public void setCity_code(String city_code) {
            this.city_code = city_code;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getDistrict_code() {
            return district_code;
        }

        public void setDistrict_code(String district_code) {
            this.district_code = district_code;
        }

        public String getDistrict_name() {
            return district_name;
        }

        public void setDistrict_name(String district_name) {
            this.district_name = district_name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getSale_region_code() {
            return sale_region_code;
        }

        public void setSale_region_code(String sale_region_code) {
            this.sale_region_code = sale_region_code;
        }

        public String getSale_region_name() {
            return sale_region_name;
        }

        public void setSale_region_name(String sale_region_name) {
            this.sale_region_name = sale_region_name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(longitude);
            dest.writeString(latitude);
            dest.writeString(mall_number);
            dest.writeString(mall_name);
            dest.writeString(province_code);
            dest.writeString(province_name);
            dest.writeString(city_code);
            dest.writeString(city_name);
            dest.writeString(district_code);
            dest.writeString(district_name);
            dest.writeString(address);
            dest.writeString(sale_region_code);
            dest.writeString(sale_region_name);
        }
    }
}
