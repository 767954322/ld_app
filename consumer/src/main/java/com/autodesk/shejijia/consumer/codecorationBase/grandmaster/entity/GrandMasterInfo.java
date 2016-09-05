package com.autodesk.shejijia.consumer.codecorationBase.grandmaster.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by allengu on 16-8-26.
 */
public class GrandMasterInfo implements Serializable {


    private List<MasterInfo> designer_list;

    public GrandMasterInfo() {
    }

    public GrandMasterInfo(List<MasterInfo> designer_list) {
        this.designer_list = designer_list;
    }

    public List<MasterInfo> getDesigner_list() {
        return designer_list;
    }

    public void setDesigner_list(List<MasterInfo> designer_list) {
        this.designer_list = designer_list;
    }

    @Override
    public String toString() {
        return "GrandMasterInfo{" +
                "designer_list=" + designer_list +
                '}';
    }
}
