package com.autodesk.shejijia.shared.components.common.entity.microbean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by t_xuz on 11/30/16.
 * 未读消息与问题
 */

public class UnreadMessageIssue implements Serializable {

    @SerializedName("project_id")
    private long projectId;
    private Message message;
    private Issue issue;

    public static class Message implements Serializable {
        private int unread;

        public int getUnread() {
            return unread;
        }

        public void setUnread(int unread) {
            this.unread = unread;
        }
    }

    public static class Issue implements Serializable {
        private int unresolved;

        public int getUnresolved() {
            return unresolved;
        }

        public void setUnresolved(int unresolved) {
            this.unresolved = unresolved;
        }
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }
}
