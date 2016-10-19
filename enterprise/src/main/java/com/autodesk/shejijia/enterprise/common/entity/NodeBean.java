package com.autodesk.shejijia.enterprise.common.entity;

import com.autodesk.shejijia.enterprise.common.entity.microbean.Comment;
import com.autodesk.shejijia.enterprise.common.entity.microbean.SubTask;
import com.autodesk.shejijia.enterprise.common.entity.microbean.TaskForm;
import com.autodesk.shejijia.enterprise.common.entity.microbean.Time;

import java.io.Serializable;
import java.util.List;

/**
 * Created by t_xuz on 8/23/16.
 * task 每个节点的详情 (未调试)
 */
public class NodeBean {

    private String docType;
    private String taskId;
    private String taskTemplateId;
    private String projectId;
    private String planId;
    private String status;
    private String name;
    private String description;
    private String category;
    private String assignee;
    private Time planningTime;
    private Time effectTime;
    private Time reserveTime;
    private List<Comment> comments;
    private List<TaskForm> taskFroms;//待确定字段
    private List<SubTask> subtasks;
    private boolean milestone;

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

    public String getTaskTemplateId() {
        return taskTemplateId;
    }

    public void setTaskTemplateId(String taskTemplateId) {
        this.taskTemplateId = taskTemplateId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<TaskForm> getTaskFroms() {
        return taskFroms;
    }

    public void setTaskFroms(List<TaskForm> taskFroms) {
        this.taskFroms = taskFroms;
    }

    public List<SubTask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<SubTask> subtasks) {
        this.subtasks = subtasks;
    }

    public boolean isMilestone() {
        return milestone;
    }

    public void setMilestone(boolean milestone) {
        this.milestone = milestone;
    }
}
