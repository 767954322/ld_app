package com.autodesk.shejijia.shared.components.nodeprocess.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.entity.microbean.PlanInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.utility.LoginUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.common.utility.UserInfoUtils;
import com.autodesk.shejijia.shared.components.message.ProjectMessageCenterActivity;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.ProjectDetailsContract;
import com.autodesk.shejijia.shared.components.nodeprocess.data.ProjectRepository;
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
    private boolean mIsHasTaskData;
    private boolean mIsNeedRefresh; //标记已经在项目详情页面下拉刷新时候的状态

    public ProjectDetailsPresenter(Context context, ProjectDetailsContract.View projectDetailsView) {
        this.mContext = context;
        this.mProjectDetailsView = projectDetailsView;
        mProjectRepository = ProjectRepository.getInstance();
    }

    @Override
    public void initRequestParams(long projectId, boolean taskData) {
        this.mProjectId = projectId;
        this.mIsHasTaskData = taskData;
        this.mIsNeedRefresh = false;
    }

    @Override
    public void initRefreshState(boolean isNeedRefresh) {
        this.mIsNeedRefresh = isNeedRefresh;
    }

    @Override
    public void getProjectDetails() {
        if (!mIsNeedRefresh) {
            mProjectDetailsView.showLoading();
        }
        Bundle requestParamsBundle = new Bundle();
        requestParamsBundle.putLong("pid", mProjectId);
        requestParamsBundle.putBoolean("task_data", mIsHasTaskData);

        getProjectDetailsData(requestParamsBundle);
    }

    private void getProjectDetailsData(Bundle requestParams) {
        mProjectRepository.getProjectInfo(requestParams, ConstructionConstants.REQUEST_TAG_GET_PROJECT_DETAILS, new ResponseCallback<ProjectInfo, ResponseError>() {
            @Override
            public void onSuccess(ProjectInfo data) {
                handleProjectInfo(data);
            }

            @Override
            public void onError(ResponseError error) {
                mProjectDetailsView.hideLoading();
                mProjectDetailsView.showNetError(error);
            }
        });
    }

    /*
    * 判断当前角色的开工交底是否已经完成
    * */
    private boolean isKaiGongResolved(ProjectInfo projectInfo) {
        if (projectInfo.getPlan() != null) {
            List<Task> taskList = projectInfo.getPlan().getTasks();
            List<Task> milestoneList = new ArrayList<>();
            if (taskList != null && taskList.size() > 0) {
                //get milestone list
                for (int i = 0; i < taskList.size(); i++) {
                    if (taskList.get(i).isMilestone()) {
                        milestoneList.add(taskList.get(i));
                    }
                }
                return milestoneList.get(0).getStatus()
                        .equalsIgnoreCase(ConstructionConstants.TaskStatus.RESOLVED);
            }
        }
        return false;
    }


    private void handleProjectInfo(ProjectInfo projectInfo) {
        /*handle planInfo data*/
        if (projectInfo.getPlan() != null) {
            int currentMilestonePosition = getCurrentMilestonePosition(projectInfo.getPlan());
            List<List<Task>> taskLists = handleTaskListData(projectInfo.getPlan());
            boolean isKaiGongResolved = isKaiGongResolved(projectInfo);
            if (taskLists != null) {
                mProjectDetailsView.hideLoading();
                mProjectDetailsView.updateProjectDetailsView(UserInfoUtils.getMemberType(mContext), taskLists, currentMilestonePosition, isKaiGongResolved);
            } else {
                mProjectDetailsView.showError("handle data error");
            }
        }
    }

    private int getCurrentMilestonePosition(PlanInfo planInfo) {
        int position = 0;
        if (planInfo.getMilestone() != null) {
            String milestoneId = planInfo.getMilestone().getMilestoneId();
            List<Task> taskList = planInfo.getTasks();
            if (taskList != null) {
                /*get current milestone index in task list*/
                int nowIndex = 0;
                for (int index = 0; index < taskList.size(); index++) {
                    if (taskList.get(index).getTaskId().equals(milestoneId)) {
                        nowIndex = index;
                        break;
                    }
                }
                /* get milestone's index list*/
                List<Integer> taskIndexList = new ArrayList<>();
                for (int index = 0; index < taskList.size(); index++) {
                    if (taskList.get(index).isMilestone()) {
                        taskIndexList.add(index);
                    }
                }
                /* get current milestone position in milestone index list*/
                for (int i = 0; i < taskIndexList.size(); i++) {
                    if (nowIndex == taskIndexList.get(i)) {
                        position = i;
                        if (position == 0) { //如果是开工交底已完成，那么就跳到隐秘工程阶段
                            position += 1;
                        }
                        break;
                    }
                }
            }
        }
        return position;
    }

    private List<List<Task>> handleTaskListData(PlanInfo planInfo) {
        List<Task> taskList = planInfo.getTasks();
        if (taskList != null) {
            /* get milestone's index list*/
            List<Integer> taskIndexList = new ArrayList<>();
            for (int index = 0; index < taskList.size(); index++) {
                if (taskList.get(index).isMilestone()) {
                    taskIndexList.add(index);
                }
            }

            /*get child task list*/
            List<List<Task>> taskLists = new ArrayList<>();
            int index = 0;
            for (int i = 0; i < taskIndexList.size(); i++) {
                int value = taskIndexList.get(i);
                List<Task> childTaskList = taskList.subList(index, value + 1);
                index = value + 1;
                taskLists.add(childTaskList);
            }
            return taskLists;
        }
        return null;
    }


    @Override
    public void getProjectInformation() {
        ProjectInfo projectInfo = mProjectRepository.getActiveProject();
        if (projectInfo != null) {
            Bundle projectInfoBundle = new Bundle();
            projectInfoBundle.putLong("projectId", projectInfo.getProjectId());
            if (!TextUtils.isEmpty(projectInfo.getName()) && projectInfo.getName().contains("/")) {
                projectInfoBundle.putString("userName", projectInfo.getName().split("/")[0]);
                projectInfoBundle.putString("userAddress", projectInfo.getName().split("/")[1]);
            }
            projectInfoBundle.putString("roomArea", projectInfo.getBuilding().getArea() + " m\u00B2");
            projectInfoBundle.putString("roomType", projectInfo.getBuilding().getBathrooms()
                    + UIUtils.getString(R.string.room) + projectInfo.getBuilding().getHalls() + UIUtils.getString(R.string.hall));
            mProjectDetailsView.showProjectInfoDialog(projectInfoBundle);
        } else {
            // 如果没有拿到项目详情，就无法弹出项目消息对话框
            mProjectDetailsView.cancelProjectInfoDialog();
        }
    }

    @Override
    public void navigateToMessageCenter() {

        ProjectInfo projectInfo = mProjectRepository.getActiveProject();
        Intent intent = new Intent(mContext,ProjectMessageCenterActivity.class);
        intent.putExtra(ConstructionConstants.BUNDLE_KEY_PROJECT_ID,projectInfo.getProjectId());
        intent.putExtra(ConstructionConstants.UNREAD,false);
        Log.d("test","test:"+projectInfo.getProjectId());
        mContext.startActivity(intent);

    }

}
