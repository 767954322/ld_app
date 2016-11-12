package com.autodesk.shejijia.shared.components.nodeprocess.presenter;

import android.os.Bundle;

import com.autodesk.shejijia.shared.components.common.entity.Project;
import com.autodesk.shejijia.shared.components.common.entity.microbean.PlanInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.EditPlanContract;
import com.autodesk.shejijia.shared.components.nodeprocess.data.ProjectRepository;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment.EditTaskNodeFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wenhulin on 11/3/16.
 */

public class EditPlanPresenter implements EditPlanContract.Presenter {
    private final static String LOG_TAG_EDIT_PLAN = "edit_plan";
    private final static String REQUEST_TAG_FETCH_PLAN = "fetch_plan";
    private final static String REQUEST_TAG_UPDATE_PLAN = "update_plan";

    private EditPlanContract.View mView;
    private EditState mEditState;
    private ProjectRepository mProjectRepository;

    private String mProjectId;
    private PlanInfo mPlan;
    private Task mActiveTask;


    public EditPlanPresenter(String projectId) {
        mProjectId = projectId;
        mProjectRepository = ProjectRepository.getInstance();
    }

    @Override
    public void bindView(EditPlanContract.View view) {
        mView = view;
        mView.bindPresenter(this);
        mActiveTask = null;
    }

    @Override
    public void fetchPlan() {
        if (mPlan != null) {
            mView.showTasks(filterTasks());
            return;
        }

        mProjectRepository.getPlanByProjectId(mProjectId, REQUEST_TAG_FETCH_PLAN, new ResponseCallback<PlanInfo>() {
            @Override
            public void onSuccess(PlanInfo data) {
                mPlan = data;
                mView.showTasks(filterTasks());
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }

    @Override
    public void updateTask(Date selectedDate) {
        switch (mEditState) {
            case EDIT_MILESTONE:
                Task newActiveTask = getMileStoneNode(selectedDate);
                if (newActiveTask == null) {
                    if (mActiveTask != null) {
                        // Update active task date
                        Date oldDate = DateUtil.iso8601ToDate(mActiveTask.getPlanningTime().getStart());
                        if (oldDate != null) {
                            String selectedDateString = DateUtil.dateToIso8601(selectedDate);
                            updateTaskDate(mActiveTask, selectedDateString);
                            if (DateUtil.compareDate(selectedDateString, mPlan.getStart()) < 0) {
                                mPlan.setStart(selectedDateString);
                            }
                            if (DateUtil.compareDate(selectedDateString, mPlan.getCompletion()) > 0) {
                                mPlan.setCompletion(selectedDateString);
                            }

                            mView.onTaskDateChange(mActiveTask, oldDate, selectedDate);
                        }
                    }
                } else {
                    if (mActiveTask ==null || newActiveTask.getTaskId() != mActiveTask.getTaskId()) {
                        // Update active task
                        mActiveTask = newActiveTask;
                        mView.showActiveTask(mActiveTask);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void commitPlan() {
        Bundle requestParams = new Bundle();
        requestParams.putString("operation", "edit"); // TODO Get operation
        requestParams.putSerializable("body", mPlan);
        mProjectRepository.updatePlan(mProjectId, requestParams, REQUEST_TAG_UPDATE_PLAN, new ResponseCallback<Project>() {
            @Override
            public void onSuccess(Project data) {
                LogUtils.d(LOG_TAG_EDIT_PLAN, "update plan success ");
                mView.onCommitSuccess();
            }

            @Override
            public void onError(String errorMsg) {
                LogUtils.e(LOG_TAG_EDIT_PLAN, "update plan fail " + errorMsg);
                mView.showError("Update plan fail!\n " + errorMsg); // TODO update string
            }
        });

    }

    public void editTaskNode(Task task) {
        ((EditTaskNodeFragment) mView).showBottomSheet(getMileStoneNodes(), task);
    }

    @Override
    public void updateEditState(EditState newState) {
        mEditState = newState;
    }

    @Override
    public EditState getEditState() {
        return mEditState;
    }

    /**
     * Created by wenhulin on 11/3/16.
     */
    public static enum EditState {
        EDIT_MILESTONE,
        EDIT_TASK_NODE,
    }

    private List<Task> filterTasks() {
        if (mEditState.equals(EditState.EDIT_MILESTONE)) {
            return getMileStoneNodes();
        } else {
            return mPlan.getTasks();
        }
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

    private Task getMileStoneNode(Date date) {
        for (Task task: mPlan.getTasks()) {
            if (task.isMilestone()) {
                Date startDate = DateUtil.iso8601ToDate(task.getPlanningTime().getStart());
                if(DateUtil.isSameDay(startDate, date)) {
                    return task;
                }
            }
        }

        return null;
    }

    private void updateTaskDate(Task task, String dateString) {
        task.getPlanningTime().setStart(dateString);
    }
}
