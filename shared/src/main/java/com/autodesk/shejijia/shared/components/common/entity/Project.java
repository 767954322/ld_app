package com.autodesk.shejijia.shared.components.common.entity;

import com.autodesk.shejijia.shared.components.common.entity.microbean.Building;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Like;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Member;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Plan;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by t_xuz on 8/25/16.
 * 每个项目详情的bean,不含task详情数据
 */
public class Project implements Serializable{

    private String description;
    private List<Like> likes;
    private List<Member> members;
    private Building building;
    private long owner;
    private Plan plan;
    private String name;
    @SerializedName("project_id")
    private long projectId;
    @SerializedName("main_project_id")
    private long mainProjectId;
    @SerializedName("coupon_type")
    private int couponType;
    @SerializedName("create_time")
    private String createTime;
    @SerializedName("group_chat_thread_id")
    private String groupChatThreadId;
    @SerializedName("design_id")
    private String designId;
    @SerializedName("x_debug")
    private boolean xDebug;
    @SerializedName("xdebug_current_time")
    private String xDebugCurrentTime;

    public boolean isXDebug() {
        return xDebug;
    }

    public void setXDebug(boolean xDebug) {
        this.xDebug = xDebug;
    }

    public String getXDebugCurrentTime() {
        return xDebugCurrentTime;
    }

    public void setXDebugCurrentTime(String xDebugCurrentTime) {
        this.xDebugCurrentTime = xDebugCurrentTime;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public long getOwner() {
        return owner;
    }

    public void setOwner(long owner) {
        this.owner = owner;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public long getMainProjectId() {
        return mainProjectId;
    }

    public void setMainProjectId(long mainProjectId) {
        this.mainProjectId = mainProjectId;
    }

    public int getCouponType() {
        return couponType;
    }

    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getGroupChatThreadId() {
        return groupChatThreadId;
    }

    public void setGroupChatThreadId(String groupChatThreadId) {
        this.groupChatThreadId = groupChatThreadId;
    }

    public String getDesignId() {
        return designId;
    }

    public void setDesignId(String designId) {
        this.designId = designId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
