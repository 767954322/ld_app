package com.autodesk.shejijia.enterprise.nodeprocess.contract;


import com.autodesk.shejijia.enterprise.common.entity.Project;
import com.autodesk.shejijia.enterprise.common.entity.microbean.Task;
import com.autodesk.shejijia.enterprise.base.BasePresenter;
import com.autodesk.shejijia.enterprise.base.BaseView;

import java.util.List;

/**
 * Created by t_xuz on 10/13/16.
 * 施工主线mvp中的vp对应的接口
 */
public interface ProjectListContract {

    interface View extends BaseView {

        void refreshProjectListData(List<Project> projectList);

        void addMoreProjectListData(List<Project> projectList);

    }

    interface Presenter extends BasePresenter {
        /*
        * 加载所有任务列表
        * */
        void loadProjectListData(String requestUrl, String eventTag, String requestTag, boolean isSwipeRefresh);
        /*
       * 跳转项目详情页
       * */
        void navigateToProjectDetail(List<Project> projectList, int position);

        /*
        * 每个item中每个任务条目点击跳转到节点详情
        * */
        void navigateToTaskDetail(List<Task> taskLists, int position);
    }
}
