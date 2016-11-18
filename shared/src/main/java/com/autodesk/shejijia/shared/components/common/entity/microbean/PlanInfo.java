package com.autodesk.shejijia.shared.components.common.entity.microbean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by t_xuz on 10/19/16.
 * update 10/24
 * Project接口中，tasks字段对应的是任务详情集合，List<Task>类型
 */
public class PlanInfo implements Serializable {

    private String status;
    private MileStone milestone;
    private String start;
    private String completion;
    private List<Task> tasks;
    @SerializedName("plan_template_id")
    private String planTemplateId;
    @SerializedName("plan_id")
    private int planId;
    private String projectId;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

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

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }
}
