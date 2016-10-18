package com.autodesk.shejijia.enterprise.nodeprocess.contract;


import com.autodesk.shejijia.enterprise.nodeprocess.BasePresenter;
import com.autodesk.shejijia.enterprise.nodeprocess.BaseView;
import com.autodesk.shejijia.enterprise.nodeprocess.data.entity.TaskListBean;

import java.util.List;

/**
 * Created by t_xuz on 10/13/16.
 * 施工主线mvp中的vp对应的接口
 */
public interface ProjectListContract {

    interface View extends BaseView {

        void refreshProjectListData(TaskListBean taskListBean);

        void addMoreProjectListData(TaskListBean taskListBean);

    }

    interface Presenter extends BasePresenter {
        /*
        * 加载所有任务列表
        * */
        void loadProjectListData(String requestUrl, String eventTag, String requestTag, boolean isSwipeRefresh);
        /*
       * 跳转项目详情页的监听器
       * */
        void onProjectClickListener(List<TaskListBean.TaskList> projectList, int position);

        /*
        * 每个item中每个任务条目点击跳转的监听器
        * */
        void onTaskClickListener(List<TaskListBean.TaskList.Plan.Task> taskIdLists, int position);
    }
}
