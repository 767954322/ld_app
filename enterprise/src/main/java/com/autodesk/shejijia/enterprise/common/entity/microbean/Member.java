package com.autodesk.shejijia.enterprise.common.entity.microbean;

import java.io.Serializable;

/**
 * Created by t_xuz on 10/19/16.
 * update 10/19
 */
public class Member implements Serializable{

    private String role;
    private String uid;
    private Profile profile;
    private int acs_member_id;
    private String thread_id;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public int getAcs_member_id() {
        return acs_member_id;
    }

    public void setAcs_member_id(int acs_member_id) {
        this.acs_member_id = acs_member_id;
    }

    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }
}
