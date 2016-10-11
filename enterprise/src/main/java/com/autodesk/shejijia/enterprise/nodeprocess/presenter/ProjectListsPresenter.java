package com.autodesk.shejijia.enterprise.nodeprocess.presenter;

import android.view.View;

import com.autodesk.shejijia.enterprise.nodeprocess.entity.TaskListBean;

/**
 * Created by t_xuz on 10/11/16.
 * 主页 项目(任务)列表页对应的presenter-->对应 TaskListFragment
 */
public interface ProjectListsPresenter {


    /*
    * 加载所有任务列表的方法
    * */
    void loadTaskListData(String findDate,String eventTag,String requestTag,int pageSize,boolean isSwipeRefresh);

    /*
    * 跳转项目详情页的监听器
    * */
    void onItemTopClickListener(View view, int position, TaskListBean entity);

    /*
    * 每个item中每个任务条目点击跳转的监听器
    * */
    void onItemChildItemClickListener(View view,int position,TaskListBean entity);

}
