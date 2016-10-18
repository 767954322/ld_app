package com.autodesk.shejijia.enterprise.nodeprocess.data.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by t_xuz on 8/22/16.
 * 项目列表的bean
 */
public class ProjectListBean implements Serializable{

    private int total;
    private int limit;
    private int offset;
    private List<ProjectList> data;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
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

    public List<ProjectList> getData() {
        return data;
    }

    public void setData(List<ProjectList> data) {
        this.data = data;
    }

    public static class ProjectList implements Serializable{

        private Building building;
        private int owner;
        private List<Member> members;
        private Plan plan;
        private List<Link> likes;
        //服务端是int,好长
        private String version;
        private String name;
        private String description;
        private String doc_type;
        private long project_id;
        private long main_project_id;
        private int coupon_type;
        private String create_time;
        private String group_chat_thread_id;

        public static class Building implements Serializable {
            //12 个字段
            private int halls;
            private int bathrooms;
            private int area;
            private String province;
            private String city;
            private String district;
            private Boolean is_new;
            private int room_type;
            private String province_name;
            private String city_name;
            private String district_name;
            private String community_name;


            public int getHalls() {
                return halls;
            }

            public void setHalls(int halls) {
                this.halls = halls;
            }

            public int getBathrooms() {
                return bathrooms;
            }

            public void setBathrooms(int bathrooms) {
                this.bathrooms = bathrooms;
            }

            public int getArea() {
                return area;
            }

            public void setArea(int area) {
                this.area = area;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public Boolean getIs_new() {
                return is_new;
            }

            public void setIs_new(Boolean is_new) {
                this.is_new = is_new;
            }

            public int getRoom_type() {
                return room_type;
            }

            public void setRoom_type(int room_type) {
                this.room_type = room_type;
            }

            public String getProvince_name() {
                return province_name;
            }

            public void setProvince_name(String province_name) {
                this.province_name = province_name;
            }

            public String getCity_name() {
                return city_name;
            }

            public void setCity_name(String city_name) {
                this.city_name = city_name;
            }

            public String getDistrict_name() {
                return district_name;
            }

            public void setDistrict_name(String district_name) {
                this.district_name = district_name;
            }

            public String getCommunity_name() {
                return community_name;
            }

            public void setCommunity_name(String community_name) {
                this.community_name = community_name;
            }
        }

        public static class Member implements Serializable{
            private String role;
            private String uid;
            private int acs_member_id;
            private String thread_id;

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role;
            }

            public int getAcs_member_id() {
                return acs_member_id;
            }

            public void setAcs_member_id(int acs_member_id) {
                this.acs_member_id = acs_member_id;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getThread_id() {
                return thread_id;
            }

            public void setThread_id(String thread_id) {
                this.thread_id = thread_id;
            }
        }

        public static class Plan implements Serializable{
            //7个字段
            private String status;
            private String milestone;
            private String start;
            private String completion;
            private List<String> tasks;
            private String plan_template_id;
            private int plan_id;

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

            public List<String> getTasks() {
                return tasks;
            }

            public void setTasks(List<String> tasks) {
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

        public static class Link implements Serializable{
            private String uid;
            private Boolean like;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public Boolean getLike() {
                return like;
            }

            public void setLike(Boolean like) {
                this.like = like;
            }
        }

        public Building getBuilding() {
            return building;
        }

        public void setBuilding(Building building) {
            this.building = building;
        }

        public int getOwner() {
            return owner;
        }

        public void setOwner(int owner) {
            this.owner = owner;
        }

        public List<Member> getMembers() {
            return members;
        }

        public void setMembers(List<Member> members) {
            this.members = members;
        }

        public Plan getPlan() {
            return plan;
        }

        public void setPlan(Plan plan) {
            this.plan = plan;
        }

        public List<Link> getLikes() {
            return likes;
        }

        public void setLikes(List<Link> likes) {
            this.likes = likes;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
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

        public String getDoc_type() {
            return doc_type;
        }

        public void setDoc_type(String doc_type) {
            this.doc_type = doc_type;
        }

        public long getProject_id() {
            return project_id;
        }

        public void setProject_id(int project_id) {
            this.project_id = project_id;
        }

        public long getMain_project_id() {
            return main_project_id;
        }

        public void setMain_project_id(int main_project_id) {
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

        @Override
        public String toString() {
            return "Project{" +
                    "building=" + building +
                    ", owner=" + owner +
                    ", members=" + members +
                    ", plan=" + plan +
                    ", likes=" + likes +
                    ", version='" + version + '\'' +
                    ", name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    ", doc_type='" + doc_type + '\'' +
                    ", project_id=" + project_id +
                    ", main_project_id=" + main_project_id +
                    ", coupon_type=" + coupon_type +
                    ", create_time='" + create_time + '\'' +
                    ", group_chat_thread_id='" + group_chat_thread_id + '\'' +
                    '}';
        }
    }

}
