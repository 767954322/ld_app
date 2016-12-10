package com.autodesk.shejijia.shared.components.nodeprocess.contract;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.PlanInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import java.util.List;

/**
 * Created by t_xuz on 11/1/16.
 * 施工主线mvp中的vp对应的接口--项目详情页面
 */

public interface ProjectDetailsContract {

    interface View extends BaseView {

        void updateProjectDetailsView(String memberType, List<List<Task>> taskLists, int currentMilestonePosition, boolean isKaiGongResolved);

        void showProjectInfoDialog(Bundle projectInfoBundle);

        void cancelProjectInfoDialog(); //没有获取项目详情的情况下，是无法显示项目消息的对话框的
    }

    interface Presenter extends BasePresenter {

        void initRequestParams(long projectId, boolean isHasTaskData);  //初始化获取项目详情的请求参数

        void getProjectDetails(); //获取项目详情

        void getProjectInformation(); //获取项目信息

        void navigateToMessageCenter();//跳转消息中心

        void initRefreshState(boolean isNeedRefresh);
    }
}
