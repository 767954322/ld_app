package com.autodesk.shejijia.enterprise.common.entity.microbean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by t_xuz on 10/19/16.
 * update 10/19
 */
public class Member implements Serializable{

    private String role;
    private String uid;
    private Profile profile;
    @SerializedName("acs_member_id")
    private int acsMemberId;
    @SerializedName("thread_id")
    private String threadId;

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

    public int getAcsMemberId() {
        return acsMemberId;
    }

    public void setAcsMemberId(int acsMemberId) {
        this.acsMemberId = acsMemberId;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }
}
