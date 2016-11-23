package com.autodesk.shejijia.shared.components.nodeprocess.contract;


import android.support.annotation.Nullable;

import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Like;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

import java.util.List;

/**
 * Created by t_xuz on 10/13/16.
 * 施工主线mvp中的vp对应的接口--项目列表页面
 */
public interface ProjectListContract {

    interface View extends BaseView {

        void refreshProjectListView(List<ProjectInfo> projectList);

        void addMoreProjectListView(List<ProjectInfo> projectList);

        void refreshLikesButton(Like newLike, int likePosition);

    }

    interface Presenter extends BasePresenter {
        /*
        * 初始化请求参数
        * */
        void initFilterRequestParams(@Nullable String date, @Nullable String filterLike, @Nullable String filterStatus);

        /*
        * 日期变化，项目列表变更
        * */
        void onFilterDateChange(String newDate);

        /*
        * 项目状态变化，项目列表变更（我页－项目列表）
        * */
        void onFilterStatusChange(String newStatus);

        /*
        * 是否星标状态变更（全部项目 or 星标项目）
        * */
        void onFilterLikeChange(String newLike);

        /*
        *  根据筛选条件设置筛选popup状态
        * */
        String getScreenPopupState();

        /*
        * 上拉刷新项目列表
        * */
        void refreshProjectList();

        /*
        * 下拉加载更多项目列表
        * */
        void loadMoreProjectList();

        /*
       * 跳转项目详情页
       * */
        void navigateToProjectDetail(List<ProjectInfo> projectList, int position);

        /*
        * 每个item中每个任务条目点击跳转到节点详情
        * */
        void navigateToTaskDetail(List<Task> taskLists, int position);

        /*
        * 星标项目
        * */
        void updateProjectLikesState(List<ProjectInfo> projectList, boolean like, int position);
    }
}
