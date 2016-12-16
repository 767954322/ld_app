package com.autodesk.shejijia.shared.components.nodeprocess.contract;

import android.os.Bundle;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment.ProjectDetailsFragment;
import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

import java.util.List;

/**
 * Created by t_xuz on 11/1/16.
 * 施工主线mvp中的vp对应的接口--项目详情页面
 */

public interface ProjectDetailsContract {

    interface View extends BaseView {

        void updateProjectDetailsView(String memberType, String avatarUrl, List<List<Task>> taskLists, int currentMilestonePosition, boolean isKaiGongResolved);

        void showProjectInfoDialog(Bundle projectInfoBundle);

        void cancelProjectInfoDialog(); //没有获取项目详情的情况下，是无法显示项目消息的对话框的

        void updateUnreadMsgCountView(int count);
    }

    interface Presenter extends BasePresenter {

        void initRequestParams(long projectId, boolean isHasTaskData);  //初始化获取项目详情的请求参数

        void getProjectDetails(); //获取项目详情

        void getProjectInformation(); //获取项目信息

        String getThreadId();

        void initRefreshState(boolean isNeedRefresh);

        void navigateToMessageCenter(ProjectDetailsFragment projectDetailsFragment, boolean isUnread,int requestCode);//跳转消息中心

        void getUnreadMsgCount(String projectIds, String requestTag);

        void start();

        void stop();
    }
}
