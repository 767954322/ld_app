package com.autodesk.shejijia.enterprise.common.entity.microbean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by t_xuz on 10/19/16.
 * update 10/19
 * 任务详情
 */
public class Task implements Serializable{

    private String status;
    private String name;
    private String description;
    private String category;
    private String assignee;
    private List<Comment> comments;
    private List<Form> forms;
    private List<File> files;
    private boolean milestone;
    @SerializedName("doc_type")
    private String docType;
    @SerializedName("task_id")
    private String taskId;
    @SerializedName("task_index")
    private String taskIndex;
    @SerializedName("task_template_id")
    private String taskTemplateId;
    @SerializedName("project_id")
    private long projectId;
    @SerializedName("plan_id")
    private String planId;
    @SerializedName("is_milestone")
    private boolean isMilestone;
    @SerializedName("planning_time")
    private Time planningTime;
    @SerializedName("effect_time")
    private Time effectTime;
    @SerializedName("reserve_time")
    private Time reserveTime;
    @SerializedName("unfinished_subtask_count")
    private int unfinishedSubTaskCount;
    @SerializedName("subtasks")
    private List<SubTask> subTasks;
    @SerializedName("current_subtask_id")
    private String currentSubTaskId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Form> getForms() {
        return forms;
    }

    public void setForms(List<Form> forms) {
        this.forms = forms;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public boolean isMilestone() {
        return milestone;
    }

    public void setMilestone(boolean milestone) {
        this.milestone = milestone;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskIndex() {
        return taskIndex;
    }

    public void setTaskIndex(String taskIndex) {
        this.taskIndex = taskIndex;
    }

    public String getTaskTemplateId() {
        return taskTemplateId;
    }

    public void setTaskTemplateId(String taskTemplateId) {
        this.taskTemplateId = taskTemplateId;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public Time getPlanningTime() {
        return planningTime;
    }

    public void setPlanningTime(Time planningTime) {
        this.planningTime = planningTime;
    }

    public Time getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(Time effectTime) {
        this.effectTime = effectTime;
    }

    public Time getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(Time reserveTime) {
        this.reserveTime = reserveTime;
    }

    public int getUnfinishedSubTaskCount() {
        return unfinishedSubTaskCount;
    }

    public void setUnfinishedSubTaskCount(int unfinishedSubTaskCount) {
        this.unfinishedSubTaskCount = unfinishedSubTaskCount;
    }

    public List<SubTask> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(List<SubTask> subTasks) {
        this.subTasks = subTasks;
    }

    public String getCurrentSubTaskId() {
        return currentSubTaskId;
    }

    public void setCurrentSubTaskId(String currentSubTaskId) {
        this.currentSubTaskId = currentSubTaskId;
    }
}
