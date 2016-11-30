package com.autodesk.shejijia.shared.components.message.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by luchongbin on 2016/12/9.
 */


public  class ExtendData implements Serializable {
    private String title;
    private String description;
    @SerializedName("task_name")
    private String taskName;
    private Object files;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Object getFiles() {
        return files;
    }

    public void setFiles(Object files) {
        this.files = files;
    }
}
