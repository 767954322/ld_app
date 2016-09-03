package com.autodesk.shejijia.consumer.personalcenter.workflow.entity;

import java.io.Serializable;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-07-17 .
 * @file MPWkStepBean.java .
 * @brief 交付物节点实体类 .
 */
public class MPWkStepBean implements Serializable {
    private Object files;
    private int status;
    private String thread_id;
    private String previous_lastest_msg_id;
    private String timestam_end;
    private String timestamp_start;
    private String wk_step_id;

    public Object getFiles() {
        return files;
    }

    public void setFiles(Object files) {
        this.files = files;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }

    public String getPrevious_lastest_msg_id() {
        return previous_lastest_msg_id;
    }

    public void setPrevious_lastest_msg_id(String previous_lastest_msg_id) {
        this.previous_lastest_msg_id = previous_lastest_msg_id;
    }

    public String getTimestam_end() {
        return timestam_end;
    }

    public void setTimestam_end(String timestam_end) {
        this.timestam_end = timestam_end;
    }

    public String getTimestamp_start() {
        return timestamp_start;
    }

    public void setTimestamp_start(String timestamp_start) {
        this.timestamp_start = timestamp_start;
    }

    public String getWk_step_id() {
        return wk_step_id;
    }

    public void setWk_step_id(String wk_step_id) {
        this.wk_step_id = wk_step_id;
    }
}
