package com.autodesk.shejijia.consumer.codecorationBase.studio.fragment.entity;

import java.io.Serializable;


public class RealName implements Serializable {


    /**
     * 设计师真实姓名
     */
    private String real_name;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 证件类型
     */
    private String certificate_type;

    /**
     * 证件号
     */
    private String certificate_no;

    /**
     * 证件正面照
     */
    private ImageFileRsp photo_front_end;

    /**
     * 证件背面照
     */
    private ImageFileRsp photo_back_end;

    /**
     * 手持证件照
     */
    private ImageFileRsp photo_in_hand;

    /**
     * 实名认证状态
     */
    private String audit_status;

    /**
     * 手机号
     */
    private String mobile_number;

    /**
     * 认证日期
     */
    private String audit_date;

    /**
     * 认证（审核）人
     */
    private String auditor;

    /**
     * 认证（审核）意见
     */
    private String auditor_opinion;


    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }



    public String getCertificate_type() {
        return certificate_type;
    }

    public void setCertificate_type(String certificate_type) {
        this.certificate_type = certificate_type;
    }

    public String getCertificate_no() {
        return certificate_no;
    }

    public void setCertificate_no(String certificate_no) {
        this.certificate_no = certificate_no;
    }

    public ImageFileRsp getPhoto_front_end() {
        return photo_front_end;
    }

    public void setPhoto_front_end(ImageFileRsp photo_front_end) {
        this.photo_front_end = photo_front_end;
    }

    public ImageFileRsp getPhoto_back_end() {
        return photo_back_end;
    }

    public void setPhoto_back_end(ImageFileRsp photo_back_end) {
        this.photo_back_end = photo_back_end;
    }

    public ImageFileRsp getPhoto_in_hand() {
        return photo_in_hand;
    }

    public void setPhoto_in_hand(ImageFileRsp photo_in_hand) {
        this.photo_in_hand = photo_in_hand;
    }


    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getAuditor_opinion() {
        return auditor_opinion;
    }

    public void setAuditor_opinion(String auditor_opinion) {
        this.auditor_opinion = auditor_opinion;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAudit_status() {
        return audit_status;
    }

    public void setAudit_status(String audit_status) {
        this.audit_status = audit_status;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getAudit_date() {
        return audit_date;
    }

    public void setAudit_date(String audit_date) {
        this.audit_date = audit_date;
    }
/**
     * @Fields 高阶认证
     */


}