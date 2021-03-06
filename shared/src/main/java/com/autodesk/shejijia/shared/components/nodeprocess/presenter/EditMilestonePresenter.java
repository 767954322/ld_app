package com.autodesk.shejijia.shared.components.nodeprocess.presenter;

import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.entity.microbean.PlanInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.EditPlanContract;
import com.autodesk.shejijia.shared.components.nodeprocess.data.ProjectRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wenhulin on 11/15/16.
 */

public class EditMilestonePresenter implements EditPlanContract.MileStonePresenter {

    private EditPlanContract.MileStoneView mView;

    private String mProjectId;
    private PlanInfo mPlan;
    private Task mActiveTask;

    public EditMilestonePresenter(String projectId) {
        mProjectId = projectId;
    }

    @Override
    public void bindView(EditPlanContract.MileStoneView view) {
        mView = view;
    }

    @Override
    public void fetchPlan() {
        mView.showLoading();
        ProjectRepository.getInstance().getActivePlan(mProjectId, ConstructionConstants.REQUEST_TAG_FETCH_PLAN,
                new ResponseCallback<PlanInfo, ResponseError>() {
            @Override
            public void onSuccess(PlanInfo data) {
                mView.hideLoading();
                mPlan = data;
                mView.showTasks(getMileStoneNodes());
            }


            @Override
            public void onError(ResponseError error) {
                mView.hideLoading();
                mView.showNetError(error);
            }
        });
    }

    @Override
    public void updateTask(Date selectedDate) {
        if (selectedDate == null) {
            return;
        }

        Date startDate = selectedDate;
        Date endDate = DateUtil.getDateEndTime(startDate);

        Task newActiveTask = getMileStoneNode(startDate);
        if (newActiveTask == null) {
            if (mActiveTask != null) {
                // Update active task date
                Date oldDate = DateUtil.iso8601ToDate(mActiveTask.getPlanningTime().getStart());
                if (oldDate != null) {
                    String startDateString = DateUtil.dateToIso8601(startDate);
                    String endDateString = DateUtil.dateToIso8601(endDate);
                    updateTaskDate(mActiveTask, startDateString, endDateString);
                    if (DateUtil.compareDate(startDateString, mPlan.getStart()) < 0) {
                        mPlan.setStart(startDateString);
                    }
                    if (DateUtil.compareDate(endDateString, mPlan.getCompletion()) > 0) {
                        mPlan.setCompletion(endDateString);
                    }

                    mView.onTaskDateChange(mActiveTask, oldDate, startDate);
                    ProjectRepository.getInstance().setActivePlanEditing(true);
                }
            }
        } else {
            if (mActiveTask == null || !newActiveTask.getTaskId().equalsIgnoreCase(mActiveTask.getTaskId())) {
                // Update active task
                mActiveTask = newActiveTask;
            }
        }
        mView.showActiveTask(mPlan, mActiveTask);
    }

    private List<Task> getMileStoneNodes() {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : mPlan.getTasks()) {
            if (task.isMilestone()) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    private Task getMileStoneNode(Date date) {
        for (Task task : mPlan.getTasks()) {
            if (task.isMilestone()) {
                Date startDate = DateUtil.iso8601ToDate(task.getPlanningTime().getStart());
                if (DateUtil.isSameDay(startDate, date)) {
                    return task;
                }
            }
        }

        return null;
    }

    private void updateTaskDate(Task task, String startDate, String endDate) {
        task.getPlanningTime().setStart(startDate);
        task.getPlanningTime().setCompletion(endDate);
    }
}
