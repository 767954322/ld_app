package com.autodesk.shejijia.shared.components.common.entity.microbean;

import java.io.Serializable;

/**
 * Created by t_xuz on 10/19/16.
 * version 1.0 update 10/19
 * Task 里 时间字段
 */
public class Time implements Serializable{

    private String start;
    private String completion;

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
}
