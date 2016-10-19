package com.autodesk.shejijia.enterprise.common.entity.microbean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by t_xuz on 10/19/16.
 * version1.0 update 10/19
 * 用户简介
 */
public class Profile implements Serializable{

    private String name;
    private String mobile;
    private String email;
    private List<String> groupids;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getGroupids() {
        return groupids;
    }

    public void setGroupids(List<String> groupids) {
        this.groupids = groupids;
    }
}
