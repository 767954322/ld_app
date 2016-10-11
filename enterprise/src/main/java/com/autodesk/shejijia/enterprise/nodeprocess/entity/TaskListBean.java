package com.autodesk.shejijia.enterprise.nodeprocess.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by t_xuz on 8/23/16.
 * 项目列表里的任务列表
 * 可以根据时间来查询
 */
public class TaskListBean implements Serializable{

    private int total;
    private int limit;
    private int offset;
    private List<TaskList> data;

    public class TaskList implements Serializable {

        private List<Like> likes;
        private List<ProjectListBean.ProjectList.Member> members;
        private ProjectListBean.ProjectList.Building building;
        private long owner;
        private Plan plan;
        private String name;
        private long project_id;
        private long main_project_id;
        private int coupon_type;
        private String create_time;
        private String group_chat_thread_id;


        public class Like implements Serializable {
            private String uid;
            private boolean like;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public boolean isLike() {
                return like;
            }

            public void setLike(boolean like) {
                this.like = like;
            }
        }

        public class Plan implements Serializable {
            //7个字段
            private String status;
            private String milestone;
            private String start;
            private String completion;
            private List<Task> tasks;
            private String plan_template_id;
            private int plan_id;

            public class Task implements Serializable {
                private String status;
                private String name;
                private String description;
                private String category;
                private String assignee;
                private List<NodeBean.Comment> comments;
                private List<Form> forms;
                private List<File> files;
                private boolean milestone;
                private String doc_type;
                private String task_id;
                private String task_index;
                private String task_template_id;
                private long project_id;
                private String plan_id;
                private boolean is_milestone;
                private NodeBean.Time planning_time;
                private NodeBean.Time effect_time;
                private NodeBean.Time reserve_time;
                private int unfinished_subtask_count;
                private List<NodeBean.SubTask> subtasks;
                private String current_subtask_id;


                public class Form implements Serializable {
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

                public class File implements Serializable {
                    private String name;
                    private String file_id;
                    private String file_type;
                    private String file_url;

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getFile_id() {
                        return file_id;
                    }

                    public void setFile_id(String file_id) {
                        this.file_id = file_id;
                    }

                    public String getFile_type() {
                        return file_type;
                    }

                    public void setFile_type(String file_type) {
                        this.file_type = file_type;
                    }

                    public String getFile_url() {
                        return file_url;
                    }

                    public void setFile_url(String file_url) {
                        this.file_url = file_url;
                    }
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

                public List<NodeBean.Comment> getComments() {
                    return comments;
                }

                public void setComments(List<NodeBean.Comment> comments) {
                    this.comments = comments;
                }

                public List<Form> getForms() {
                    return forms;
                }

                public void setForms(List<Form> forms) {
                    this.forms = forms;
                }

                public List<File> getFiles() {
                    return files;
                }

                public void setFiles(List<File> files) {
                    this.files = files;
                }

                public boolean isMilestone() {
                    return milestone;
                }

                public void setMilestone(boolean milestone) {
                    this.milestone = milestone;
                }

                public String getDoc_type() {
                    return doc_type;
                }

                public void setDoc_type(String doc_type) {
                    this.doc_type = doc_type;
                }

                public String getTask_id() {
                    return task_id;
                }

                public void setTask_id(String task_id) {
                    this.task_id = task_id;
                }

                public String getTask_index() {
                    return task_index;
                }

                public void setTask_index(String task_index) {
                    this.task_index = task_index;
                }

                public String getTask_template_id() {
                    return task_template_id;
                }

                public void setTask_template_id(String task_template_id) {
                    this.task_template_id = task_template_id;
                }

                public long getProject_id() {
                    return project_id;
                }

                public void setProject_id(long project_id) {
                    this.project_id = project_id;
                }

                public String getPlan_id() {
                    return plan_id;
                }

                public void setPlan_id(String plan_id) {
                    this.plan_id = plan_id;
                }

                public boolean is_milestone() {
                    return is_milestone;
                }

                public void setIs_milestone(boolean is_milestone) {
                    this.is_milestone = is_milestone;
                }

                public NodeBean.Time getPlanning_time() {
                    return planning_time;
                }

                public void setPlanning_time(NodeBean.Time planning_time) {
                    this.planning_time = planning_time;
                }

                public NodeBean.Time getEffect_time() {
                    return effect_time;
                }

                public void setEffect_time(NodeBean.Time effect_time) {
                    this.effect_time = effect_time;
                }

                public NodeBean.Time getReserve_time() {
                    return reserve_time;
                }

                public void setReserve_time(NodeBean.Time reserve_time) {
                    this.reserve_time = reserve_time;
                }

                public int getUnfinished_subtask_count() {
                    return unfinished_subtask_count;
                }

                public void setUnfinished_subtask_count(int unfinished_subtask_count) {
                    this.unfinished_subtask_count = unfinished_subtask_count;
                }

                public List<NodeBean.SubTask> getSubtasks() {
                    return subtasks;
                }

                public void setSubtasks(List<NodeBean.SubTask> subtasks) {
                    this.subtasks = subtasks;
                }

                public String getCurrent_subtask_id() {
                    return current_subtask_id;
                }

                public void setCurrent_subtask_id(String current_subtask_id) {
                    this.current_subtask_id = current_subtask_id;
                }

                @Override
                public String toString() {
                    return "Task{" +
                            "status='" + status + '\'' +
                            ", name='" + name + '\'' +
                            ", description='" + description + '\'' +
                            ", category='" + category + '\'' +
                            ", assignee='" + assignee + '\'' +
                            ", comments=" + comments +
                            ", forms=" + forms +
                            ", files=" + files +
                            ", milestone=" + milestone +
                            ", doc_type='" + doc_type + '\'' +
                            ", task_id='" + task_id + '\'' +
                            ", task_index='" + task_index + '\'' +
                            ", task_template_id='" + task_template_id + '\'' +
                            ", project_id=" + project_id +
                            ", plan_id='" + plan_id + '\'' +
                            ", is_milestone=" + is_milestone +
                            ", planning_time=" + planning_time +
                            ", effect_time=" + effect_time +
                            ", reserve_time=" + reserve_time +
                            ", unfinished_subtask_count=" + unfinished_subtask_count +
                            ", subtasks=" + subtasks +
                            ", current_subtask_id='" + current_subtask_id + '\'' +
                            '}';
                }

            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getMilestone() {
                return milestone;
            }

            public void setMilestone(String milestone) {
                this.milestone = milestone;
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

            public List<Task> getTasks() {
                return tasks;
            }

            public void setTasks(List<Task> tasks) {
                this.tasks = tasks;
            }

            public String getPlan_template_id() {
                return plan_template_id;
            }

            public void setPlan_template_id(String plan_template_id) {
                this.plan_template_id = plan_template_id;
            }

            public int getPlan_id() {
                return plan_id;
            }

            public void setPlan_id(int plan_id) {
                this.plan_id = plan_id;
            }
        }

        public List<Like> getLikes() {
            return likes;
        }

        public void setLikes(List<Like> likes) {
            this.likes = likes;
        }

        public List<ProjectListBean.ProjectList.Member> getMembers() {
            return members;
        }

        public void setMembers(List<ProjectListBean.ProjectList.Member> members) {
            this.members = members;
        }

        public ProjectListBean.ProjectList.Building getBuilding() {
            return building;
        }

        public void setBuilding(ProjectListBean.ProjectList.Building building) {
            this.building = building;
        }

        public long getOwner() {
            return owner;
        }

        public void setOwner(long owner) {
            this.owner = owner;
        }

        public Plan getPlan() {
            return plan;
        }

        public void setPlan(Plan plan) {
            this.plan = plan;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getProject_id() {
            return project_id;
        }

        public void setProject_id(long project_id) {
            this.project_id = project_id;
        }

        public long getMain_project_id() {
            return main_project_id;
        }

        public void setMain_project_id(long main_project_id) {
            this.main_project_id = main_project_id;
        }

        public int getCoupon_type() {
            return coupon_type;
        }

        public void setCoupon_type(int coupon_type) {
            this.coupon_type = coupon_type;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getGroup_chat_thread_id() {
            return group_chat_thread_id;
        }

        public void setGroup_chat_thread_id(String group_chat_thread_id) {
            this.group_chat_thread_id = group_chat_thread_id;
        }
    }

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

    public List<TaskList> getData() {
        return data;
    }

    public void setData(List<TaskList> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TaskListBean{" +
                "total=" + total +
                ", limit=" + limit +
                ", offset=" + offset +
                ", data=" + data +
                '}';
    }
}
