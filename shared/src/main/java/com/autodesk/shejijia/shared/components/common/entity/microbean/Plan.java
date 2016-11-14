package com.autodesk.shejijia.shared.components.common.entity.microbean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by t_xuz on 10/19/16.
 * update 10/24
 * Project接口中，tasks字段对应的是任务id集合，List<String>类型
 */
public class Plan implements Serializable{

    private String status;
    private MileStone milestone;
    private String start;
    private String completion;
    private List<String> tasks;
    @SerializedName("plan_template_id")
    private String planTemplateId;
    @SerializedName("plan_id")
    private int planId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MileStone getMilestone() {
        return milestone;
    }

    public void setMilestone(MileStone milestone) {
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

    public List<String> getTasks() {
        return tasks;
    }

    public void setTasks(List<String> tasks) {
        this.tasks = tasks;
    }

    public String getPlanTemplateId() {
        return planTemplateId;
    }

    public void setPlanTemplateId(String planTemplateId) {
        this.planTemplateId = planTemplateId;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }
}
