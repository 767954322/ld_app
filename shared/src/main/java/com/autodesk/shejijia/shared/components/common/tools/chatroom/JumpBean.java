package com.autodesk.shejijia.shared.components.common.tools.chatroom;

import java.io.Serializable;

/**
 * Created by luchongbin on 16-9-9.
 */
public class JumpBean implements Serializable {

    private String reciever_user_id;     //接收者的id
    private String reciever_user_name;   //接收者的name
    private String reciever_hs_uid;      //接收者的hs_uid

    private String member_type;          //当前用户的type
    private String acs_member_id;        //当前用户的acs_member_id

    public JumpBean() {
    }

    public String getReciever_user_id() {
        return reciever_user_id;
    }

    public void setReciever_user_id(String reciever_user_id) {
        this.reciever_user_id = reciever_user_id;
    }

    public String getReciever_user_name() {
        return reciever_user_name;
    }

    public void setReciever_user_name(String reciever_user_name) {
        this.reciever_user_name = reciever_user_name;
    }

    public String getMember_type() {
        return member_type;
    }

    public void setMember_type(String member_type) {
        this.member_type = member_type;
    }

    public String getAcs_member_id() {
        return acs_member_id;
    }

    public void setAcs_member_id(String acs_member_id) {
        this.acs_member_id = acs_member_id;
    }

    public String getReciever_hs_uid() {
        return reciever_hs_uid;
    }

    public void setReciever_hs_uid(String reciever_hs_uid) {
        this.reciever_hs_uid = reciever_hs_uid;
    }

    @Override
    public String toString() {
        return "JumpBean{" +
                "reciever_user_id='" + reciever_user_id + '\'' +
                ", reciever_user_name='" + reciever_user_name + '\'' +
                ", member_type='" + member_type + '\'' +
                ", acs_member_id='" + acs_member_id + '\'' +
                ", reciever_hs_uid='" + reciever_hs_uid + '\'' +
                '}';
    }
}
