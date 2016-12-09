package com.autodesk.shejijia.shared.components.message.entity;

import java.io.Serializable;

/**
 * Created by luchongbin on 2016/12/9.
 */


public  class ExtendDataBean implements Serializable {
    private String title;
    private String description;
    private String task_name;
    private Object files;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public Object getFiles() {
        return files;
    }

    public void setFiles(Object files) {
        this.files = files;
    }
}
