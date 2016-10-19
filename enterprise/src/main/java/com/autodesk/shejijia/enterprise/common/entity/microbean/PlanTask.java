package com.autodesk.shejijia.enterprise.common.entity.microbean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by t_xuz on 10/19/16.
 * update 10/19
 * Project接口中，tasks字段对应的是任务详情集合，List<Task>类型
 */
public class PlanTask implements Serializable {

    private String status;
    private String milestone;
    private String start;
    private String completion;
    private List<Task> tasks;
    @SerializedName("plan_template_id")
    private String planTemplateId;
    @SerializedName("modified_time")
    private String modifiedTime;
    @SerializedName("plan_id")
    private int planId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMilestone() {
        return milestone;
    }

    public void setMilestone(String milestone) {
        this.milestone = milestone;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getCompletion() {
        return completion;
    }

    public void setCompletion(String completion) {
        this.completion = completion;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public String getPlanTemplateId() {
        return planTemplateId;
    }

    public void setPlanTemplateId(String planTemplateId) {
        this.planTemplateId = planTemplateId;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }
}
