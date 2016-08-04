package com.autodesk.shejijia.consumer.home.decorationdesigners.entity;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-8-2 .
 * @file RealNameBean.java .
 * @brief 实名认证的实体类 .
 */
public class RealNameBean {
    private String certificate_no;
    private String real_name;
    private String mobile_number;

    private Object birthday;
    private Object auditor;
    private Object certificate_type;
    private Object photo_front_end;
    private Object photo_back_end;
    private Object photo_in_hand;
    private Object audit_status;
    private Object audit_date;
    private Object auditor_opinion;


    public Object getBirthday() {
        return birthday;
    }

    public void setBirthday(Object birthday) {
        this.birthday = birthday;
    }

    public Object getAuditor() {
        return auditor;
    }

    public void setAuditor(Object auditor) {
        this.auditor = auditor;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public Object getCertificate_type() {
        return certificate_type;
    }

    public void setCertificate_type(Object certificate_type) {
        this.certificate_type = certificate_type;
    }

    public String getCertificate_no() {
        return certificate_no;
    }

    public void setCertificate_no(String certificate_no) {
        this.certificate_no = certificate_no;
    }

    public Object getPhoto_front_end() {
        return photo_front_end;
    }

    public void setPhoto_front_end(Object photo_front_end) {
        this.photo_front_end = photo_front_end;
    }

    public Object getPhoto_back_end() {
        return photo_back_end;
    }

    public void setPhoto_back_end(Object photo_back_end) {
        this.photo_back_end = photo_back_end;
    }

    public Object getPhoto_in_hand() {
        return photo_in_hand;
    }

    public void setPhoto_in_hand(Object photo_in_hand) {
        this.photo_in_hand = photo_in_hand;
    }

    public Object getAudit_status() {
        return audit_status;
    }

    public void setAudit_status(Object audit_status) {
        this.audit_status = audit_status;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public Object getAudit_date() {
        return audit_date;
    }

    public void setAudit_date(Object audit_date) {
        this.audit_date = audit_date;
    }

    public Object getAuditor_opinion() {
        return auditor_opinion;
    }

    public void setAuditor_opinion(Object auditor_opinion) {
        this.auditor_opinion = auditor_opinion;
    }
}
