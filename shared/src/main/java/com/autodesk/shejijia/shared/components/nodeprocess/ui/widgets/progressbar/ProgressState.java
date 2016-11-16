package com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.progressbar;

import java.io.Serializable;

/**
 * Created by t_xuz on 11/15/16.
 */

public class ProgressState implements Serializable{

    private String status;
    private String name;

    public ProgressState(String status, String name) {
        this.status = status;
        this.name = name;
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
}
