package com.autodesk.shejijia.shared.components.form.entity.microBean;

import java.io.Serializable;

/**
 * Created by t_panya on 16/10/24.
 */

public class FeedBack implements Serializable {
    private String type;
    private String value;
    private String id;
    private Integer checkResult;
    private Integer actionResult;
    private String commentValue;
    private String imageValue;
    private String audioValue;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
    }

    public Integer getActionResult() {
        return actionResult;
    }

    public void setActionResult(Integer actionResult) {
        this.actionResult = actionResult;
    }

    public String getCommentValue() {
        return commentValue;
    }

    public void setCommentValue(String commentValue) {
        this.commentValue = commentValue;
    }

    public String getImageValue() {
        return imageValue;
    }

    public void setImageValue(String imageValue) {
        this.imageValue = imageValue;
    }

    public String getAudioValue() {
        return audioValue;
    }

    public void setAudioValue(String audioValue) {
        this.audioValue = audioValue;
    }

}
