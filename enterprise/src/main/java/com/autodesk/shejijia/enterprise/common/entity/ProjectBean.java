package com.autodesk.shejijia.enterprise.common.entity;

import com.autodesk.shejijia.enterprise.common.entity.microbean.Building;
import com.autodesk.shejijia.enterprise.common.entity.microbean.Like;
import com.autodesk.shejijia.enterprise.common.entity.microbean.Member;
import com.autodesk.shejijia.enterprise.common.entity.microbean.Plan;
import com.autodesk.shejijia.enterprise.common.entity.microbean.PlanTask;

import java.io.Serializable;
import java.util.List;

/**
 * Created by t_xuz on 8/25/16.
 * 每个项目详情的bean,含task数据
 */
public class ProjectBean implements Serializable{

    private List<Like> likes;
    private List<Member> members;
    private Building building;
    private long owner;
    private PlanTask plan;
    private String name;
    private long project_id;
    private long main_project_id;
    private int coupon_type;
    private String create_time;
    private String group_chat_thread_id;


    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public PlanTask getPlan() {
        return plan;
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

    public void setPlan(PlanTask plan) {
        this.plan = plan;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getProject_id() {
        return project_id;
    }

    public void setProject_id(long project_id) {
        this.project_id = project_id;
    }

    public long getMain_project_id() {
        return main_project_id;
    }

    public void setMain_project_id(long main_project_id) {
        this.main_project_id = main_project_id;
    }

    public int getCoupon_type() {
        return coupon_type;
    }

    public void setCoupon_type(int coupon_type) {
        this.coupon_type = coupon_type;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getGroup_chat_thread_id() {
        return group_chat_thread_id;
    }

    public void setGroup_chat_thread_id(String group_chat_thread_id) {
        this.group_chat_thread_id = group_chat_thread_id;
    }
}
