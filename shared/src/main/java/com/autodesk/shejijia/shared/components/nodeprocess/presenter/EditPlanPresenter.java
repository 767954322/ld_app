package com.autodesk.shejijia.shared.components.nodeprocess.presenter;

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
 * Created by wenhulin on 11/3/16.
 */

public class EditPlanPresenter implements EditPlanContract.Presenter {
    private final static String REQUEST_TAG_FETCH_PLAN = "fetch_plan";
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

    public void updateTask(Date selectedDate) {
        switch (mEditState) {
            case EDIT_MILESTONE:
                Task newActiveTask = getMileStoneNode(selectedDate);
                if (newActiveTask == null) {
                    if (mActiveTask != null) {
                        // Update active task date
                        Date oldDate = DateUtil.isoStringToDate(mActiveTask.getPlanningTime().getStart());
                        updateTaskDate(mActiveTask, selectedDate);
                        mView.onTaskDateChange(mActiveTask, oldDate, selectedDate);
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

    private void updateTask(Task task, Date newDate) {
        String dateString = DateUtil.getStringDateByFormat(newDate, "yyyy-MM-dd'T'HH:mm:ss'Z'");
        task.getPlanningTime().setStart(dateString);
    }

    @Override
    public void commitPlan() {
        //TODO
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
                Date startDate = DateUtil.isoStringToDate(task.getPlanningTime().getStart());
                if(DateUtil.isSameDay(startDate, date)) {
                    return task;
                }
            }
        }

        return null;
    }

    private void updateTaskDate(Task task, Date newDate) {
        String dateString = DateUtil.getStringDateByFormat(newDate, "yyyy-MM-dd'T'HH:mm:ss'Z'");
        task.getPlanningTime().setStart(dateString);
    }
}
