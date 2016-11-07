package com.autodesk.shejijia.consumer.personalcenter.consumer.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by allengu on 16-11-7.
 */
public class MessageCenterBodyNew implements Serializable {

    private DisplayMessage display_message;
//    private CustomData custom_data;

//    public CustomData getCustom_data() {
//        return custom_data;
//    }
//
//    public void setCustom_data(CustomData custom_data) {
//        this.custom_data = custom_data;
//    }

    public DisplayMessage getDisplay_message() {
        return display_message;
    }

    public void setDisplay_message(DisplayMessage display_message) {
        this.display_message = display_message;
    }

    @Override
    public String toString() {
        return "MessageCenterBodyNew{" +
                "display_message=" + display_message +
                ", custom_data="  +
                '}';
    }

    public static class DisplayMessage {

        private String summary;
        private List<String> detail_items;

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public List<String> getDetail_items() {
            return detail_items;
        }

        public void setDetail_items(List<String> detail_items) {
            this.detail_items = detail_items;
        }

    }

    public static class CustomData {

        private String project_id;
        private String task_id;
        private String event_category;
        private String event;
        private boolean in_consumer_feeds;
        private String entity_id;
        private String extend_data;

        public CustomData(String project_id, String extend_data, String entity_id, boolean in_consumer_feeds, String event, String event_category, String task_id) {
            this.project_id = project_id;
            this.extend_data = extend_data;
            this.entity_id = entity_id;
            this.in_consumer_feeds = in_consumer_feeds;
            this.event = event;
            this.event_category = event_category;
            this.task_id = task_id;
        }

        public String getProject_id() {
            return project_id;
        }

        public void setProject_id(String project_id) {
            this.project_id = project_id;
        }

        public String getTask_id() {
            return task_id;
        }

        public void setTask_id(String task_id) {
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

        public String getExtend_data() {
            return extend_data;
        }

        public void setExtend_data(String extend_data) {
            this.extend_data = extend_data;
        }

        @Override
        public String toString() {
            return "CustomData{" +
                    "project_id='" + project_id + '\'' +
                    ", task_id='" + task_id + '\'' +
                    ", event_category='" + event_category + '\'' +
                    ", event='" + event + '\'' +
                    ", in_consumer_feeds=" + in_consumer_feeds +
                    ", entity_id='" + entity_id + '\'' +
                    ", extend_data='" + extend_data + '\'' +
                    '}';
        }
    }

}
