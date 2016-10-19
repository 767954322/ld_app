package com.autodesk.shejijia.enterprise.common.entity.microbean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by t_xuz on 10/19/16.
 */
public class Confirm implements Serializable{

    private String role;
    private String uid;
    @SerializedName("signoff")
    private boolean signOff;

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

    public boolean isSignOff() {
        return signOff;
    }

    public void setSignOff(boolean signOff) {
        this.signOff = signOff;
    }

}
