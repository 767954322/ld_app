package com.autodesk.shejijia.shared.components.nodeprocess.presenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.ProjectDetailsTasksContract;
import com.autodesk.shejijia.shared.components.nodeprocess.data.ProjectRepository;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment.TaskDetailsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_xuz on 11/14/16.
 */

public class ProjectDetailsTasksPresenter implements ProjectDetailsTasksContract.Presenter {

    private ProjectDetailsTasksContract.View mPDTaskListView;
    private ProjectRepository mProjectRepository;

    public ProjectDetailsTasksPresenter(ProjectDetailsTasksContract.View mPDTaskListView) {
        this.mPDTaskListView = mPDTaskListView;
        mProjectRepository = ProjectRepository.getInstance();
    }

    @Override
    public void handleTaskList(Bundle taskBundle) {
        if (taskBundle != null) {
            List<Task> taskList = (ArrayList<Task>) taskBundle.getSerializable(ConstructionConstants.BUNDLE_KEY_TASK_LIST);
            String avatarUrl = taskBundle.getString(ConstructionConstants.BUNDLE_KEY_USER_AVATAR);
            if (taskList != null) {
                mPDTaskListView.refreshTaskListView(taskList, avatarUrl);
            }
        }
    }

    @Override
    public void navigateToTaskDetail(FragmentManager fragmentManager, List<Task> taskList, int position) {
        TaskDetailsFragment taskDetailsFragment = TaskDetailsFragment.newInstance(mProjectRepository.getActiveProject(), taskList.get(position));
        taskDetailsFragment.setTargetFragment((Fragment) mPDTaskListView, ConstructionConstants.REQUEST_CODE_SHOW_TASK_DETAILS);
        taskDetailsFragment.show(fragmentManager, "task_details");
    }
}
