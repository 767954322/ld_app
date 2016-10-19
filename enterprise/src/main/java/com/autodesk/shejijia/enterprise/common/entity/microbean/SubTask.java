package com.autodesk.shejijia.enterprise.common.entity.microbean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by t_xuz on 10/19/16.
 */
public class SubTask implements Serializable{

    private String status;
    private String assignee;
    private String type;
    private String start;
    private String completion;
    private List<Confirm> confirms;
    private long subtask_id;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public List<Confirm> getConfirms() {
        return confirms;
    }

    public void setConfirms(List<Confirm> confirms) {
        this.confirms = confirms;
    }

    public long getSubtask_id() {
        return subtask_id;
    }

    public void setSubtask_id(long subtask_id) {
        this.subtask_id = subtask_id;
    }
}
