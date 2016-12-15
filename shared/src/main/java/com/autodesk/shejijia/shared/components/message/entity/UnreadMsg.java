package com.autodesk.shejijia.shared.components.message.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by luchongbin on 2016/12/14.
 */

public class UnreadMsg implements Serializable {
    @SerializedName("project_id")
    private long projectId;
    @SerializedName("thread_id")
    private String threadId;
    private int count;

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }
}