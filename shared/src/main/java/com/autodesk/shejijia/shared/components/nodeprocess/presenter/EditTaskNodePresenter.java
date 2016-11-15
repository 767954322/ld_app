package com.autodesk.shejijia.shared.components.nodeprocess.presenter;

import android.os.Bundle;

import com.autodesk.shejijia.shared.components.common.entity.Project;
import com.autodesk.shejijia.shared.components.common.entity.microbean.PlanInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.EditPlanContract;
import com.autodesk.shejijia.shared.components.nodeprocess.data.ProjectRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by wenhulin on 11/3/16.
 */

public class EditTaskNodePresenter implements EditPlanContract.TaskNodePresenter {
    private final static String LOG_TAG_EDIT_PLAN = "edit_plan";
    private final static String REQUEST_TAG_UPDATE_PLAN = "update_plan";

    private EditPlanContract.TaskNodeView mView;

    private String mProjectId;
    private PlanInfo mPlan;
    private Task mActiveTask;

    public EditTaskNodePresenter(String projectId) {
        mProjectId = projectId;
    }

    @Override
    public void fetchPlan() {
        PlanInfo editingPlan = ProjectRepository.getInstance().getEditingPlan();
        if (editingPlan == null) {
            mView.showError("No active project"); // TODO update string
            return;
        }

        mPlan = editingPlan;
        sortTasks();
        mView.showTasks(mPlan.getTasks());
    }

    @Override
    public void bindView(EditPlanContract.TaskNodeView view) {
        mView = view;
        mActiveTask = null;
    }

    @Override
    public void editTaskNode(Task task) {
        mActiveTask = task;
        mView.showBottomSheet(getMileStoneNodes(), task);
    }

    @Override
    public void updateTask(List<Date> selectedDates) {
        if (selectedDates.size() < 1) {
            return;
        }

        Collections.sort(selectedDates, new Comparator<Date>() {
            @Override
            public int compare(Date lhs, Date rhs) {
                return DateUtil.compareDate(lhs, rhs);
            }
        });

        Date startDate = selectedDates.get(0);
        Date endDate;
        if (selectedDates.size() == 1) {
            endDate = DateUtil.getDateEndTime(startDate);
        } else {
            endDate = selectedDates.get(selectedDates.size() - 1);
        }

        String startDateString = DateUtil.dateToIso8601(startDate);
        String endDateString = DateUtil.dateToIso8601(endDate);
        updateTaskDate(mActiveTask, startDateString, endDateString);
        sortTasks();
        mView.showTasks(mPlan.getTasks());
    }

    @Override
    public void commitPlan() {
        Bundle requestParams = new Bundle();
        requestParams.putString("operation", "edit"); // TODO Get operation
        requestParams.putSerializable("body", mPlan);
        mView.showUpLoading();
        ProjectRepository.getInstance().updatePlan(mProjectId, requestParams, REQUEST_TAG_UPDATE_PLAN, new ResponseCallback<Project>() {
            @Override
            public void onSuccess(Project data) {
                LogUtils.d(LOG_TAG_EDIT_PLAN, "update plan success ");
                mView.hideUpLoading();
                mView.onCommitSuccess();
            }

            @Override
            public void onError(String errorMsg) {
                LogUtils.e(LOG_TAG_EDIT_PLAN, "update plan fail " + errorMsg);
                mView.hideUpLoading();
                mView.onCommitError("Update plan fail!\n " + errorMsg); // TODO update string
            }
        });

    }

    private PlanInfo copyPlan(PlanInfo planInfo) {
        String jsonString = GsonUtil.beanToString(planInfo);
        return GsonUtil.jsonToBean(jsonString, PlanInfo.class);
    }

    private List<Task> getMileStoneNodes() {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task: mPlan.getTasks()) {
            if (task.isMilestone()) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    private void updateTaskDate(Task task, String startDate, String endDate) {
        task.getPlanningTime().setStart(startDate);
        task.getPlanningTime().setCompletion(endDate);
    }

    private void sortTasks() {
        List<Task> tasks = mPlan.getTasks();
        Collections.sort(tasks, new Comparator<Task>() {
            @Override
            public int compare(Task lhs, Task rhs) {
                return DateUtil.compareDate(lhs.getPlanningTime().getStart(), rhs.getPlanningTime().getStart());
            }
        });
    }
}
