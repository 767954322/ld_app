package com.autodesk.shejijia.enterprise.nodeprocess.data.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by t_xuz on 8/23/16.
 * task 每个节点的详情
 */
public class NodeBean {

    private String docType;
    private String taskId;
    private String taskTemplateId;
    private String projectId;
    private String planId;
    private String status;
    private String name;
    private String description;
    private String category;
    private String assignee;
    private Time planningTime;
    private Time effectTime;
    private Time reserveTime;
    private List<Comment> comments;
    private List<TaskForm> taskFroms;
    private List<SubTask> subtasks;
    private boolean milestone;

    /*
    * planningTime/effectTime/reserveTime
    * 三个字段共有的数据类型
    * */
    public static class Time implements Serializable{
        private String start;
        private String completion;

        public String getCompletion() {
            return completion;
        }

        public void setCompletion(String completion) {
            this.completion = completion;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }
    }

    public static class Comment implements Serializable {
        private String uid;
        private String content;
        private String submitted;
        private String modified;
        private String comment_id;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getSubmitted() {
            return submitted;
        }

        public void setSubmitted(String submitted) {
            this.submitted = submitted;
        }

        public String getModified() {
            return modified;
        }

        public void setModified(String modified) {
            this.modified = modified;
        }

        public String getComment_id() {
            return comment_id;
        }

        public void setComment_id(String comment_id) {
            this.comment_id = comment_id;
        }
    }

    public static class TaskForm implements Serializable {
        private String category;
        private String template_id;
        private String form_id;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getTemplate_id() {
            return template_id;
        }

        public void setTemplate_id(String template_id) {
            this.template_id = template_id;
        }

        public String getForm_id() {
            return form_id;
        }

        public void setForm_id(String form_id) {
            this.form_id = form_id;
        }
    }

    public static class SubTask implements Serializable {
        private String status;
        private String assignee;
        private String type;
        private String start;
        private String completion;
        private List<Confirm> confirms;
        private long subtask_id;

        public static class Confirm implements Serializable {
            private String role;
            private String uid;
            private boolean signoff;

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public boolean isSignoff() {
                return signoff;
            }

            public void setSignoff(boolean signoff) {
                this.signoff = signoff;
            }
        }

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

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskTemplateId() {
        return taskTemplateId;
    }

    public void setTaskTemplateId(String taskTemplateId) {
        this.taskTemplateId = taskTemplateId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public Time getPlanningTime() {
        return planningTime;
    }

    public void setPlanningTime(Time planningTime) {
        this.planningTime = planningTime;
    }

    public Time getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(Time effectTime) {
        this.effectTime = effectTime;
    }

    public Time getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(Time reserveTime) {
        this.reserveTime = reserveTime;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<TaskForm> getTaskFroms() {
        return taskFroms;
    }

    public void setTaskFroms(List<TaskForm> taskFroms) {
        this.taskFroms = taskFroms;
    }

    public List<SubTask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<SubTask> subtasks) {
        this.subtasks = subtasks;
    }

    public boolean isMilestone() {
        return milestone;
    }

    public void setMilestone(boolean milestone) {
        this.milestone = milestone;
    }

}
