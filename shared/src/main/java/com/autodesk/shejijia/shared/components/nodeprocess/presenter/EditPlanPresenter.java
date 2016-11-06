package com.autodesk.shejijia.shared.components.nodeprocess.presenter;

import com.autodesk.shejijia.shared.components.common.entity.microbean.PlanInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.listener.LoadDataCallback;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.EditPlanContract;
import com.autodesk.shejijia.shared.components.nodeprocess.data.ProjectRepository;

import java.util.ArrayList;
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

    public EditPlanPresenter(String projectId) {
        mProjectId = projectId;
        mProjectRepository = ProjectRepository.getInstance();
    }

    @Override
    public void bindView(EditPlanContract.View view) {
        mView = view;
        mView.bindPresenter(this);
    }

    @Override
    public void fetchPlan() {
        mProjectRepository.getPlanByProjectId(mProjectId, REQUEST_TAG_FETCH_PLAN, new LoadDataCallback<PlanInfo>() {
            @Override
            public void onLoadSuccess(PlanInfo data) {
                mPlan = data;
                mView.showTasks(filterTasks());
            }

            @Override
            public void onLoadFailed(String errorMsg) {

            }
        });
    }

    private List<Task> filterTasks() {
        if (mEditState.equals(EditState.EDIT_MILESTONE)) {
            return getMileStoneNodes();
        } else {
            return getTaskNodes();
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

    private List<Task> getTaskNodes() {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task: mPlan.getTasks()) {
            if (!task.isMilestone()) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
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

    @Override
    public void updatePlan() {
        //TODO
    }

    /**
     * Created by wenhulin on 11/3/16.
     */
    public static enum EditState {
        EDIT_MILESTONE,
        EDIT_TASK_NODE,
    }
}
