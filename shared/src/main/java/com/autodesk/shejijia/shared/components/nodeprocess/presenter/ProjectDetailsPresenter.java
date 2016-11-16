package com.autodesk.shejijia.shared.components.nodeprocess.presenter;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;

import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.PlanInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.UserInfoUtils;
import com.autodesk.shejijia.shared.components.nodeprocess.bean.TaskListBean;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.ProjectDetailsContract;
import com.autodesk.shejijia.shared.components.nodeprocess.data.ProjectRepository;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment.PDTaskListFragment;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_xuz on 10/31/16.
 * 项目详情presenter-->ProjectDetailsFragment
 */

public class ProjectDetailsPresenter implements ProjectDetailsContract.Presenter {

    private Context mContext;
    private ProjectDetailsContract.View mProjectDetailsView;
    private ProjectRepository mProjectRepository;
    private long mProjectId;
    private boolean isHasTaskData;

    public ProjectDetailsPresenter(Context context, ProjectDetailsContract.View projectDetailsView) {
        this.mContext = context;
        this.mProjectDetailsView = projectDetailsView;
        mProjectRepository = ProjectRepository.getInstance();
    }

    @Override
    public void initRequestParams(long projectId, boolean taskData) {
        this.mProjectId = projectId;
        this.isHasTaskData = taskData;
    }

    @Override
    public void getProjectDetails() {
        Bundle requestParamsBundle = new Bundle();
        requestParamsBundle.putLong("pid", mProjectId);
        requestParamsBundle.putBoolean("task_data", isHasTaskData);

        getProjectDetailsData(requestParamsBundle);
    }

    private void getProjectDetailsData(Bundle requestParams) {
        mProjectRepository.getProjectInfo(requestParams, ConstructionConstants.REQUEST_TAG_GET_PROJECT_DETAILS, new ResponseCallback<ProjectInfo>() {
            @Override
            public void onSuccess(ProjectInfo data) {
                mProjectDetailsView.hideLoading();
                mProjectDetailsView.updateProjectDetailsView(UserInfoUtils.getMemberType(mContext), data);
            }

            @Override
            public void onError(String errorMsg) {
                mProjectDetailsView.hideLoading();
                mProjectDetailsView.showNetError(errorMsg);
            }
        });
    }

    @Override
    public void handleProgressbarData() {

    }

    @Override
    public void handleViewpagerData(PlanInfo planInfo, int pageCount) {
        List<Task> taskList = planInfo.getTasks();
        if (taskList != null) {
            List<Integer> taskIndexList = new ArrayList<>();
            for (int index = 0; index < taskList.size(); index++) {
                if (taskList.get(index).isMilestone()) {
                    taskIndexList.add(index);
                }
            }
            handleTaskIndex(taskList, taskIndexList);
        } else {
            mProjectDetailsView.showNetError("get project data error");
        }
    }

    private void handleTaskIndex(List<Task> taskList, List<Integer> taskIndexList) {
        List<BaseFragment> fragmentList = new ArrayList<>();
        int index = 0;
        for (int i = 0; i < taskIndexList.size(); i++) {
            int value = taskIndexList.get(i);
            List<Task> childTaskList = taskList.subList(index, value + 1);
            index = value + 1;
            Bundle taskListBundle = new Bundle();
            taskListBundle.putSerializable("task_list", new TaskListBean(childTaskList));
            fragmentList.add(PDTaskListFragment.newInstance(taskListBundle));
        }
        mProjectDetailsView.updateViewpagerView(fragmentList);
    }

    @Override
    public void getProjectInformation() {
        ProjectInfo projectInfo = mProjectRepository.getActiveProject();
        if (projectInfo != null) {
            Bundle projectInfoBundle = new Bundle();
            projectInfoBundle.putLong("projectId", projectInfo.getProjectId());
            projectInfoBundle.putString("userName", projectInfo.getName().split("/")[0]);
            projectInfoBundle.putString("userAddress", projectInfo.getName().split("/")[1]);
            projectInfoBundle.putString("roomArea", projectInfo.getBuilding().getArea() + "m2");
            projectInfoBundle.putString("roomType", projectInfo.getBuilding().getBathrooms()
                    + "室" + projectInfo.getBuilding().getHalls() + "厅");
            mProjectDetailsView.showProjectInfoDialog(projectInfoBundle);
        } else {
            // 如果没有拿到项目详情，就无法弹出项目消息对话框
            mProjectDetailsView.cancelProjectInfoDialog();
        }
    }

    @Override
    public void navigateToMessageCenter() {
        //// TODO: 11/11/16 跳转消息中心逻辑
    }

    private void handleTaskListData() {

    }
}
