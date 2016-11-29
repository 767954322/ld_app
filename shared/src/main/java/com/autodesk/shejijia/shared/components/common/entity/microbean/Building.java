package com.autodesk.shejijia.shared.components.common.entity.microbean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by t_xuz on 10/19/16.
 * update 10/19
 */
public class Building implements Serializable{

    private int halls;
    private int bathrooms;
    private int area;
    private String province;
    private String city;
    private String district;
    @SerializedName("is_new")
    private Boolean isNew;
    @SerializedName("room_type")
    private int roomType;
    @SerializedName("province_name")
    private String provinceName;
    @SerializedName("city_name")
    private String cityName;
    @SerializedName("district_name")
    private String districtName;
    @SerializedName("community_name")
    private String communityName;

    public String getAddress() {
        return cityName + district + communityName;
    }

    public int getHalls() {
        return halls;
    }

    public void setHalls(int halls) {
        this.halls = halls;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }

    public int getRoomType() {
        return roomType;
    }

    public void setRoomType(int roomType) {
        this.roomType = roomType;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }
}
