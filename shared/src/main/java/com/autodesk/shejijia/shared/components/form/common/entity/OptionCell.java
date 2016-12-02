package com.autodesk.shejijia.shared.components.form.common.entity;

import java.util.HashMap;

/**
 * Created by t_aij on 16/12/2.
 */

public class OptionCell {
    private String title;
    private String standard;
    private String actionType;
    private HashMap<String,String[]> typeDict;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public HashMap<String, String[]> getTypeDict() {
        return typeDict;
    }

    public void setTypeDict(HashMap<String, String[]> typeDict) {
        this.typeDict = typeDict;
    }

}
