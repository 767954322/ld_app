package com.autodesk.shejijia.enterprise.nodeprocess.contract;


import com.autodesk.shejijia.enterprise.nodeprocess.BaseModel;
import com.autodesk.shejijia.enterprise.nodeprocess.BasePresenter;
import com.autodesk.shejijia.enterprise.nodeprocess.BaseView;
import com.autodesk.shejijia.enterprise.nodeprocess.model.entity.TaskListBean;

/**
 * Created by t_xuz on 10/13/16.
 * 首页任务列表mvp每个角色对应的接口
 */
public interface ProjectListContract {

    interface Model extends BaseModel {

        void getProjectListData(String findDate, String eventTag, String requestTag, int pageSize, String token);

    }

    interface View extends BaseView {

        void refreshProjectListData(TaskListBean taskListBean);

        void addMoreProjectListData(TaskListBean taskListBean);

        void navigateProjectDetails(android.view.View view, int position, TaskListBean taskListBean);

        void navigateTaskDetails(android.view.View view, int position, TaskListBean taskListBean);
    }

    interface Presenter extends BasePresenter {
        /*
        * 加载所有任务列表的方法
        * */
        void loadTaskListData(String findDate, String eventTag, String requestTag, int pageSize, boolean isSwipeRefresh);

        /*
        * 跳转项目详情页的监听器
        * */
        void onItemTopClickListener(android.view.View view, int position, TaskListBean entity);

        /*
        * 每个item中每个任务条目点击跳转的监听器
        * */
        void onItemChildItemClickListener(android.view.View view, int position, TaskListBean entity);
    }
}
