package com.autodesk.shejijia.shared.components.form.common.entity;

import java.util.HashMap;
import java.util.List;

/**
 * Created by t_aij on 16/12/2.
 */

public class OptionCell {
    private String title;                            //标题
    private String standard;                         //标准
    private HashMap<String, List<String>> typeDict;  //根据属性显示界面
    private boolean isShowStandard;                  //是否显示标准;
    private int checkResult;                         //选择结果
    private String actionResult;                     //属性结果

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

    public HashMap<String, List<String>> getTypeDict() {
        return typeDict;
    }

    public void setTypeDict(HashMap<String, List<String>> typeDict) {
        this.typeDict = typeDict;
    }

    public boolean isShowStandard() {
        return isShowStandard;
    }

    public void setShowStandard(boolean showStandard) {
        isShowStandard = showStandard;
    }

    public int getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(int checkResult) {
        this.checkResult = checkResult;
    }

    public String getActionResult() {
        return actionResult;
    }

    public void setActionResult(String actionResult) {
        this.actionResult = actionResult;
    }
}
