package com.autodesk.shejijia.shared.components.nodeprocess.presenter;

import android.content.Context;
import android.os.Bundle;

import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.PlanInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.utility.UserInfoUtils;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.ProjectDetailsContract;
import com.autodesk.shejijia.shared.components.nodeprocess.data.ProjectRepository;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment.ProjectDetailTasksFragment;
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
        mProjectDetailsView.showLoading();
        Bundle requestParamsBundle = new Bundle();
        requestParamsBundle.putLong("pid", mProjectId);
        requestParamsBundle.putBoolean("task_data", isHasTaskData);

        getProjectDetailsData(requestParamsBundle);
    }

    private void getProjectDetailsData(Bundle requestParams) {
        mProjectRepository.getProjectInfo(requestParams, ConstructionConstants.REQUEST_TAG_GET_PROJECT_DETAILS, new ResponseCallback<ProjectInfo>() {
            @Override
            public void onSuccess(ProjectInfo data) {
                handleProjectInfo(data);
            }

            @Override
            public void onError(String errorMsg) {
                mProjectDetailsView.hideLoading();
                mProjectDetailsView.showNetError(errorMsg);
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
                        .equalsIgnoreCase(ConstructionConstants.TaskStatus.RESERVED);
            }
        }
        return false;
    }


    private void handleProjectInfo(ProjectInfo projectInfo) {
        /*handle planInfo data*/
        if (projectInfo.getPlan() != null) {
            int currentMilestonePosition = getCurrentMilestonePosition(projectInfo.getPlan());
            List<BaseFragment> fragmentList = handleTaskListData(projectInfo.getPlan());
            boolean isKaiGongResolved = isKaiGongResolved(projectInfo);
            if (fragmentList != null) {
                mProjectDetailsView.hideLoading();
                mProjectDetailsView.updateProjectDetailsView(UserInfoUtils.getMemberType(mContext), fragmentList, currentMilestonePosition, isKaiGongResolved);
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

    private List<BaseFragment> handleTaskListData(PlanInfo planInfo) {
        List<Task> taskList = planInfo.getTasks();
        if (taskList != null) {
            /* get milestone's index list*/
            List<Integer> taskIndexList = new ArrayList<>();
            for (int index = 0; index < taskList.size(); index++) {
                if (taskList.get(index).isMilestone()) {
                    taskIndexList.add(index);
                }
            }

            /* get fragment list*/
            List<BaseFragment> fragmentList = new ArrayList<>();
            int index = 0;
            for (int i = 0; i < taskIndexList.size(); i++) {
                int value = taskIndexList.get(i);
                List<Task> childTaskList = taskList.subList(index, value + 1);
                index = value + 1;
                Bundle taskListBundle = new Bundle();
                ArrayList<Task> childTaskArrayList = new ArrayList<>();
                childTaskArrayList.addAll(childTaskList);
                taskListBundle.putSerializable(ConstructionConstants.BUNDLE_KEY_TASK_LIST, childTaskArrayList);
                fragmentList.add(ProjectDetailTasksFragment.newInstance(taskListBundle));
            }
            return fragmentList;
        }
        return null;
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

}
