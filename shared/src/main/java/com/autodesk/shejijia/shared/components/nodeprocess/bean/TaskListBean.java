package com.autodesk.shejijia.shared.components.nodeprocess.bean;

import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;

import java.io.Serializable;
import java.util.List;

/**
 * Created by t_xuz on 11/16/16.
 */

public class TaskListBean implements Serializable{

    private List<Task> taskList;

    public TaskListBean(List<Task> taskList) {
        this.taskList = taskList;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }
}
