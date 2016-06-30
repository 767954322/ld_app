package com.autodesk.shejijia.consumer.personalcenter.consumer.entity;

import java.io.Serializable;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-6-8 .
 * @file ConsumerQrEntity.java .
 * @brief 二维码扫描的实体类.
 */
public class ConsumerQrEntity implements Serializable {

    public ConsumerQrEntity(String mobile_number, String member_type, String member_id, String hs_uid, String name, String avatar) {
        this.mobile_number = mobile_number;
        this.member_type = member_type;
        this.member_id = member_id;
        this.hs_uid = hs_uid;
        this.name = name;
        this.avatar = avatar;
    }

    /**
     * mobile_number :
     * member_type : member
     * member_id : 20743052
     * hs_uid : 1ea309b2-2617-41aa-9061-fb61db4861a4
     * name : 1992506
     * avatar :
     */
    private String mobile_number;
    private String member_type;
    private String member_id;
    private String hs_uid;
    private String name;
    private String avatar;

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getMember_type() {
        return member_type;
    }

    public void setMember_type(String member_type) {
        this.member_type = member_type;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getHs_uid() {
        return hs_uid;
    }

    public void setHs_uid(String hs_uid) {
        this.hs_uid = hs_uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
