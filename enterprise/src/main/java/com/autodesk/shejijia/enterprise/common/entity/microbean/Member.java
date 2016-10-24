package com.autodesk.shejijia.enterprise.common.entity.microbean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by t_xuz on 10/19/16.
 * update 10/24
 */
public class Member implements Serializable{

    private String role;
    private String uid;
    private boolean group;
    private Profile profile;
    @SerializedName("acs_member_id")
    private int acsMemberId;
    @SerializedName("thread_id")
    private String threadId;
    @SerializedName("is_group")
    private boolean isGroup;
    @SerializedName("group_members")
    private List<Member> groupMembers;
    private List<String> allowed;


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

    public boolean isGroup() {
        return group;
    }

    public void setGroup(boolean group) {
        this.group = group;
    }

    public List<Member> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<Member> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public List<String> getAllowed() {
        return allowed;
    }

    public void setAllowed(List<String> allowed) {
        this.allowed = allowed;
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
