package com.autodesk.shejijia.shared.components.message.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by luchongbin on 2016/12/9.
 */

public  class CustomData implements Serializable {
    @SerializedName("project_id")
    private String projectId;
    @SerializedName("task_id")
    private String taskId;
    @SerializedName("event_category")
    private String eventCategory;
    private String event;
    @SerializedName("in_consumer_feeds")
    private boolean inConsumerFeeds;
    @SerializedName("entity_id")
    private String entityId;
    @SerializedName("extend_data")
    private ExtendData extendData;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public boolean isInConsumerFeeds() {
        return inConsumerFeeds;
    }

    public void setInConsumerFeeds(boolean inConsumerFeeds) {
        this.inConsumerFeeds = inConsumerFeeds;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public ExtendData getExtendData() {
        return extendData;
    }

    public void setExtendData(ExtendData extendData) {
        this.extendData = extendData;
    }
}