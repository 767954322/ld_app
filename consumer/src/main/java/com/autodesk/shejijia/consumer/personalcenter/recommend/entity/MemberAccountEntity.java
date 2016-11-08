package com.autodesk.shejijia.consumer.personalcenter.recommend.entity;

import java.io.Serializable;

/**
 * Created by xueqiudong on 16-10-27.
 */

public class MemberAccountEntity implements Serializable {

    /**
     * uid : null
     * user_level : null
     * user_name : null
     * user_card_id : null
     * acs_member_id : null
     * check_flag : false
     * user_mobile : null
     * user_email : null
     * consumer_zid : null
     */

    private String uid;
    private String user_level;
    private String user_name;
    private String user_card_id;
    private String acs_member_id;
    private Integer check_flag;
    private String user_mobile;
    private String user_email;
    private String consumer_zid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUser_level() {
        return user_level;
    }

    public void setUser_level(String user_level) {
        this.user_level = user_level;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_card_id() {
        return user_card_id;
    }

    public void setUser_card_id(String user_card_id) {
        this.user_card_id = user_card_id;
    }

    public Integer getCheck_flag() {
        return check_flag;
    }

    public void setCheck_flag(Integer check_flag) {
        this.check_flag = check_flag;
    }
    public String getAcs_member_id() {
        return acs_member_id;
    }

    public void setAcs_member_id(String acs_member_id) {
        this.acs_member_id = acs_member_id;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getConsumer_zid() {
        return consumer_zid;
    }

    public void setConsumer_zid(String consumer_zid) {
        this.consumer_zid = consumer_zid;
    }
}
