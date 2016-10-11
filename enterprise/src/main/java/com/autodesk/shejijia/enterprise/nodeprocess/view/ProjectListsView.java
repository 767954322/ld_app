package com.autodesk.shejijia.enterprise.nodeprocess.view;

import android.view.View;

import com.autodesk.shejijia.enterprise.nodeprocess.entity.TaskListBean;
import com.autodesk.shejijia.enterprise.nodeprocess.view.base.BaseView;

/**
 * Created by t_xuz on 10/11/16.
 */
public interface ProjectListsView extends BaseView{

    void refreshProjectListData(TaskListBean taskListBean);

    void addMoreProjectListData(TaskListBean taskListBean);

    void navigateProjectDetails(View view,int position,TaskListBean taskListBean);

    void navigateTaskDetails(View view,int position,TaskListBean taskListBean);
}
