package com.autodesk.shejijia.enterprise.nodeprocess.interactor;

/**
 * Created by t_xuz on 10/10/16.
 */
public interface ProjectListInteractor {

    void getProjectListData(String findDate,String eventTag,String requestTag,int pageSize,String token);
}
