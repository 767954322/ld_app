package com.autodesk.shejijia.shared.components.message.entity;

import java.io.Serializable;

/**
 * Created by luchongbin on 2016/12/9.
 */

public  class CustomDataBean implements Serializable {
    private String project_id;
    private Object task_id;
    private String event_category;
    private String event;
    private boolean in_consumer_feeds;
    private String entity_id;
    private ExtendDataBean extend_data;

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public Object getTask_id() {
        return task_id;
    }

    public void setTask_id(Object task_id) {
        this.task_id = task_id;
    }

    public String getEvent_category() {
        return event_category;
    }

    public void setEvent_category(String event_category) {
        this.event_category = event_category;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public boolean isIn_consumer_feeds() {
        return in_consumer_feeds;
    }

    public void setIn_consumer_feeds(boolean in_consumer_feeds) {
        this.in_consumer_feeds = in_consumer_feeds;
    }

    public String getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(String entity_id) {
        this.entity_id = entity_id;
    }

    public ExtendDataBean getExtend_data() {
        return extend_data;
    }

    public void setExtend_data(ExtendDataBean extend_data) {
        this.extend_data = extend_data;
    }

}