package com.autodesk.shejijia.enterprise.common.entity.microbean;

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
    private String doc_type;
    private String task_id;
    private String task_index;
    private String task_template_id;
    private long project_id;
    private String plan_id;
    private boolean is_milestone;
    private Time planning_time;
    private Time effect_time;
    private Time reserve_time;
    private int unfinished_subtask_count;
    private List<SubTask> subtasks;
    private String current_subtask_id;

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

    public String getDoc_type() {
        return doc_type;
    }

    public void setDoc_type(String doc_type) {
        this.doc_type = doc_type;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getTask_index() {
        return task_index;
    }

    public void setTask_index(String task_index) {
        this.task_index = task_index;
    }

    public String getTask_template_id() {
        return task_template_id;
    }

    public void setTask_template_id(String task_template_id) {
        this.task_template_id = task_template_id;
    }

    public long getProject_id() {
        return project_id;
    }

    public void setProject_id(long project_id) {
        this.project_id = project_id;
    }

    public String getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(String plan_id) {
        this.plan_id = plan_id;
    }

    public boolean is_milestone() {
        return is_milestone;
    }

    public void setIs_milestone(boolean is_milestone) {
        this.is_milestone = is_milestone;
    }

    public Time getPlanning_time() {
        return planning_time;
    }

    public void setPlanning_time(Time planning_time) {
        this.planning_time = planning_time;
    }

    public Time getEffect_time() {
        return effect_time;
    }

    public void setEffect_time(Time effect_time) {
        this.effect_time = effect_time;
    }

    public Time getReserve_time() {
        return reserve_time;
    }

    public void setReserve_time(Time reserve_time) {
        this.reserve_time = reserve_time;
    }

    public int getUnfinished_subtask_count() {
        return unfinished_subtask_count;
    }

    public void setUnfinished_subtask_count(int unfinished_subtask_count) {
        this.unfinished_subtask_count = unfinished_subtask_count;
    }

    public List<SubTask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<SubTask> subtasks) {
        this.subtasks = subtasks;
    }

    public String getCurrent_subtask_id() {
        return current_subtask_id;
    }

    public void setCurrent_subtask_id(String current_subtask_id) {
        this.current_subtask_id = current_subtask_id;
    }
}
