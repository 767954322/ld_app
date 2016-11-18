package com.autodesk.shejijia.shared.components.form.common.constant;

/**
 * Created by t_panya on 16/11/8.
 */

public enum TaskStatusTypeEnum {
    TASK_STAUS_UNKOWN("unKnow"),    //未知
    TASK_STATUS_OPEN("open"),       //未开始
    TASK_STATUS_RESERVING("reserving"),     //待预约
    TASK_STATUS_RESERVED("reserved"),       //已预约
    TASK_STATUS_INPROGRESS("inProgress"),   //进行中
    TASK_STATUS_DELAY("delayed"),           //延期
    TASK_STATUS_QUALIFIED("qualified"),     //合格
    TASK_STATUS_UNQUALIFIED("unqualified"), //不合格
    TASK_STATUS_RESOLVED("resolved"),       //完成(或验收通过)
    TASK_STATUS_REJECTED("rejected"),       //验收拒绝(验收未通过)
    TASK_STATUS_REINSPECTION("reinspection"),   //强制复验
    TASK_STATUS_RECTIFICATION("rectification"), //监督整改
    TASK_STATUS_REINSPECTION_AND_RECTIFICATION("reinspectionAndRectification"), //复验并整改
    TASK_STATUS_REINSPECT_RESERVING("reinspectReserving"),      //复验待预约
    TASK_STATUS_REINSPECT_RESERVED("reinspectReserved"),        //复验已预约
    TASK_STATUS_REINSPECT_INPROGRESS("reinspectInProgress"),    //复验进行中
    TASK_STATUS_REINSPECT_DELAY("reinspectDelayed"),           //复验延期
    TASK_STATUS_DELETED("DELETED");            //已删除


    private String status;
    TaskStatusTypeEnum(String status) {
        this.status = status;
    }

    public String getTaskStatus(){
        return this.status;
    }

}
