package com.autodesk.shejijia.shared.components.common.entity.microbean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_xuz on 10/19/16.
 * update 12/02
 */
public class Member implements Serializable{

    private String role;
    private String uid;
    private boolean group;
    private Profile profile;
    @SerializedName("acs_member_id")
    private long acsMemberId;
    @SerializedName("thread_id")
    private String threadId;
    @SerializedName("progress_thread_id")
    private String progressThreadId;
    @SerializedName("is_group")
    private boolean isGroup;
    @SerializedName("group_members")
    private ArrayList<Member> groupMembers;
    private ArrayList<String> allowed;


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

    public ArrayList<Member> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(ArrayList<Member> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public ArrayList<String> getAllowed() {
        return allowed;
    }

    public void setAllowed(ArrayList<String> allowed) {
        this.allowed = allowed;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public long getAcsMemberId() {
        return acsMemberId;
    }

    public void setAcsMemberId(long acsMemberId) {
        this.acsMemberId = acsMemberId;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getProgressThreadId() {
        return progressThreadId;
    }

    public void setProgressThreadId(String progressThreadId) {
        this.progressThreadId = progressThreadId;
    }
}
