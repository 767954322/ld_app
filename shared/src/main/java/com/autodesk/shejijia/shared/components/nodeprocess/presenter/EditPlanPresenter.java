package com.autodesk.shejijia.shared.components.nodeprocess.presenter;

import android.os.Bundle;

import com.autodesk.shejijia.shared.components.common.entity.Project;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.PlanInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.EditPlanContract;
import com.autodesk.shejijia.shared.components.nodeprocess.data.ProjectRepository;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment.EditTaskNodeFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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

        ProjectInfo projectInfo = mProjectRepository.getProjectInfoByCache();
        if (projectInfo == null) {
            mView.showError("No active project"); // TODO update string
        } else {
            mPlan = projectInfo.getPlan();

            List<Task> tasks = filterTasks();
            mView.showTasks(tasks);
        }
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

        switch (mEditState) {
            case EDIT_MILESTONE:
                Task newActiveTask = getMileStoneNode(startDate);
                if (newActiveTask == null) {
                    if (mActiveTask != null) {
                        // Update active task date
                        Date oldDate = DateUtil.iso8601ToDate(mActiveTask.getPlanningTime().getStart());
                        if (oldDate != null) {
                            String startDateString = DateUtil.dateToIso8601(startDate);
                            String endDateString = DateUtil.dateToIso8601(endDate);
                            updateTaskDate(mActiveTask, startDateString, endDateString);
                            sortTasks();
                            if (DateUtil.compareDate(startDateString, mPlan.getStart()) < 0) {
                                mPlan.setStart(startDateString);
                            }
                            if (DateUtil.compareDate(endDateString, mPlan.getCompletion()) > 0) {
                                mPlan.setCompletion(endDateString);
                            }

                            mView.onTaskDateChange(mActiveTask, oldDate, startDate);
                        }
                    }
                } else {
                    if (mActiveTask == null || newActiveTask.getTaskId() != mActiveTask.getTaskId()) {
                        // Update active task
                        mActiveTask = newActiveTask;
                    }
                }
                mView.showActiveTask(mActiveTask);
                break;
            default:
                if (mActiveTask != null) {
                    String startDateString = DateUtil.dateToIso8601(startDate);
                    String endDateString = DateUtil.dateToIso8601(endDate);
                    updateTaskDate(mActiveTask, startDateString, endDateString);
                    sortTasks();
                    mView.showTasks(filterTasks());
                }
                break;
        }
    }

    @Override
    public void commitPlan() {
        Bundle requestParams = new Bundle();
        requestParams.putString("operation", "edit"); // TODO Get operation
        requestParams.putSerializable("body", mPlan);
        mView.showLoading();
        mProjectRepository.updatePlan(mProjectId, requestParams, REQUEST_TAG_UPDATE_PLAN, new ResponseCallback<Project>() {
            @Override
            public void onSuccess(Project data) {
                LogUtils.d(LOG_TAG_EDIT_PLAN, "update plan success ");
                mView.hideLoading();
                mView.onCommitSuccess();
            }

            @Override
            public void onError(String errorMsg) {
                LogUtils.e(LOG_TAG_EDIT_PLAN, "update plan fail " + errorMsg);
                mView.hideLoading();
                mView.showError("Update plan fail!\n " + errorMsg); // TODO update string
            }
        });

    }

    public void editTaskNode(Task task) {
        mActiveTask = task;
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
