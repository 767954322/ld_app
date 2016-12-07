package com.autodesk.shejijia.shared.components.message.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by luchongbin on 2016/12/6.
 */
public class MessageInfo implements Serializable {
    private int total;
    private int limit;
    private int offset;
    private List<DataBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        private DisplayMessageBean display_message;
        private CustomDataBean custom_data;
        private String sender_role;
        private String sender_avatar;
        private String sent_time;
        public DisplayMessageBean getDisplay_message() {
            return display_message;
        }

        public void setDisplay_message(DisplayMessageBean display_message) {
            this.display_message = display_message;
        }

        public CustomDataBean getCustom_data() {
            return custom_data;
        }

        public void setCustom_data(CustomDataBean custom_data) {
            this.custom_data = custom_data;
        }

        public String getSender_role() {
            return sender_role;
        }

        public void setSender_role(String sender_role) {
            this.sender_role = sender_role;
        }

        public String getSender_avatar() {
            return sender_avatar;
        }

        public void setSender_avatar(String sender_avatar) {
            this.sender_avatar = sender_avatar;
        }

        public String getSent_time() {
            return sent_time;
        }

        public void setSent_time(String sent_time) {
            this.sent_time = sent_time;
        }

        public static class DisplayMessageBean implements Serializable{
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

        public static class CustomDataBean implements Serializable{
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

            public static class ExtendDataBean implements Serializable{
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
        }
    }
}
