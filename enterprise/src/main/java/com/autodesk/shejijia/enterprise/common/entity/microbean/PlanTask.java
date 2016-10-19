package com.autodesk.shejijia.enterprise.common.entity.microbean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by t_xuz on 10/19/16.
 * update 10/19
 * Project接口中，tasks字段对应的是任务详情集合，List<Task>类型
 */
public class PlanTask implements Serializable{

    private String status;
    private String milestone;
    private String start;
    private String completion;
    private List<Task> tasks;
    private String plan_template_id;
    private String modified_time;
    private int plan_id;

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

    public String getPlan_template_id() {
        return plan_template_id;
    }

    public void setPlan_template_id(String plan_template_id) {
        this.plan_template_id = plan_template_id;
    }

    public String getModified_time() {
        return modified_time;
    }

    public void setModified_time(String modified_time) {
        this.modified_time = modified_time;
    }

    public int getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(int plan_id) {
        this.plan_id = plan_id;
    }
}
