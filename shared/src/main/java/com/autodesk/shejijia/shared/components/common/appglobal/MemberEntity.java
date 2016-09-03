package com.autodesk.shejijia.shared.components.common.appglobal;

import java.io.Serializable;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-6-7 .
 * @file MemberEntity.java .
 * @brief 登录后json数据转换的实体类 .
 */
public class MemberEntity implements Serializable {

    private String acs_token;
    private String member_type;
    private String member_id;
    private String token;
    private String guid;
    private String acs_member_id;
    private String acs_x_session;
    private String acs_x_secure_session;
    private String hs_uid;
    private String hs_accesstoken;
    private String mobile_number;
    private String nick_name;

    public void setAcs_token(String acs_token) {
        this.acs_token = acs_token;
    }

    public void setMember_type(String member_type) {
        this.member_type = member_type;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public void setAcs_member_id(String acs_member_id) {
        this.acs_member_id = acs_member_id;
    }

    public void setAcs_x_session(String acs_x_session) {
        this.acs_x_session = acs_x_session;
    }

    public void setAcs_x_secure_session(String acs_x_secure_session) {
        this.acs_x_secure_session = acs_x_secure_session;
    }

    public void setHs_uid(String hs_uid) {
        this.hs_uid = hs_uid;
    }

    public void setHs_accesstoken(String hs_accesstoken) {
        this.hs_accesstoken = hs_accesstoken;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getAcs_token() {
        return acs_token;
    }

    public String getMember_type() {
        return member_type;
    }

    public String getMember_id() {
        return member_id;
    }

    public String getToken() {
        return token;
    }

    public String getGuid() {
        return guid;
    }

    public String getAcs_member_id() {
        return acs_member_id;
    }

    public String getAcs_x_session() {
        return acs_x_session;
    }

    public String getAcs_x_secure_session() {
        return acs_x_secure_session;
    }

    public String getHs_uid() {
        return hs_uid;
    }

    public String getHs_accesstoken() {
        return hs_accesstoken;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public String getNick_name() {
        return nick_name;
    }

    @Override
    public String toString() {
        return "MemberEntity{" +
                "acs_token='" + acs_token + '\'' +
                ", member_type='" + member_type + '\'' +
                ", member_id='" + member_id + '\'' +
                ", token='" + token + '\'' +
                ", guid='" + guid + '\'' +
                ", acs_member_id='" + acs_member_id + '\'' +
                ", acs_x_session='" + acs_x_session + '\'' +
                ", acs_x_secure_session='" + acs_x_secure_session + '\'' +
                ", hs_uid='" + hs_uid + '\'' +
                ", hs_accesstoken='" + hs_accesstoken + '\'' +
                ", mobile_number='" + mobile_number + '\'' +
                ", nick_name='" + nick_name + '\'' +
                '}';
    }
}
