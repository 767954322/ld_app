package com.autodesk.shejijia.shared.components.nodeprocess.presenter;

import com.autodesk.shejijia.shared.components.nodeprocess.contract.TaskDetailsContract;
import com.autodesk.shejijia.shared.components.nodeprocess.data.ProjectRepository;

/**
 * Created by t_xuz on 11/11/16.
 */

public class TaskDetailsPresenter implements TaskDetailsContract.Presenter {

    private TaskDetailsContract.View mTaskDetailsView;
    private ProjectRepository mProjectRepository;

    public TaskDetailsPresenter(TaskDetailsContract.View taskDetailsView) {
        this.mTaskDetailsView = taskDetailsView;
        mProjectRepository = ProjectRepository.getInstance();
    }

    @Override
    public void fetchTaskDetails() {

    }
}
