package com.autodesk.shejijia.shared.components.common.entity;

/**
 * Created by t_aij on 16/11/1.
 */

public class ResponseError {

    /**
     * timestamp : 1477965760620
     * status : 500
     * error : Internal Server Error
     * exception : java.util.MissingResourceException
     * message : Can't find resource for bundle java.util.PropertyResourceBundle, key HTTP 400 Bad Request
     * path : /api/v1/projects/12341234
     */

    private long timestamp;
    private int status;
    private String error;
    private String exception;
    private String message;
    private String path;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
