package com.autodesk.shejijia.consumer.home.decorationdesigners.entity;

import java.io.Serializable;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-8-2 .
 * @file RealNameBean.java .
 * @brief 实名认证的实体类 .
 */
public class RealNameBean implements Serializable {
    private String certificate_no;
    private String real_name;
    private String mobile_number;

    private String birthday;
    private String auditor;
    private String certificate_type;
    private String photo_front_end;
    private String photo_back_end;
    private String photo_in_hand;
    private String audit_status;
    private String audit_date;
    private String auditor_opinion;

    public String getCertificate_no() {
        return certificate_no;
    }

    public void setCertificate_no(String certificate_no) {
        this.certificate_no = certificate_no;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getCertificate_type() {
        return certificate_type;
    }

    public void setCertificate_type(String certificate_type) {
        this.certificate_type = certificate_type;
    }

    public String getPhoto_front_end() {
        return photo_front_end;
    }

    public void setPhoto_front_end(String photo_front_end) {
        this.photo_front_end = photo_front_end;
    }

    public String getPhoto_back_end() {
        return photo_back_end;
    }

    public void setPhoto_back_end(String photo_back_end) {
        this.photo_back_end = photo_back_end;
    }

    public String getPhoto_in_hand() {
        return photo_in_hand;
    }

    public void setPhoto_in_hand(String photo_in_hand) {
        this.photo_in_hand = photo_in_hand;
    }

    public String getAudit_status() {
        return audit_status;
    }

    public void setAudit_status(String audit_status) {
        this.audit_status = audit_status;
    }

    public String getAudit_date() {
        return audit_date;
    }

    public void setAudit_date(String audit_date) {
        this.audit_date = audit_date;
    }

    public String getAuditor_opinion() {
        return auditor_opinion;
    }

    public void setAuditor_opinion(String auditor_opinion) {
        this.auditor_opinion = auditor_opinion;
    }
}
