package com.autodesk.shejijia.shared.components.common.entity.microbean;

import java.io.Serializable;

/**
 * Created by t_xuz on 10/19/16.
 *  update 10/19
 */
public class Like implements Serializable{

    private String uid;
    private boolean like;

    public boolean getLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
