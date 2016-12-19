package com.autodesk.shejijia.shared.components.nodeprocess.entity;

/**
 * Created by t_xuz on 12/16/16.
 */

public class MilestoneStatus {

    private int position;  //验收节点在progressBar 的位置
    private String name;    //验收节点在progressbar 显示的名称
    private String status;  //当前验收节点的状态

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
