package com.autodesk.shejijia.shared.components.nodeprocess.presenter;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.PDTaskListContract;
import com.autodesk.shejijia.shared.components.nodeprocess.data.ProjectRepository;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment.TaskDetailsFragment;

import java.util.List;

/**
 * Created by t_xuz on 11/14/16.
 *
 */

public class PDTaskListPresenter implements PDTaskListContract.Presenter{

    private PDTaskListContract.View mPDTaskListView;
    private ProjectRepository mProjectRepository;

    public PDTaskListPresenter(PDTaskListContract.View mPDTaskListView){
        this.mPDTaskListView = mPDTaskListView;
        mProjectRepository = ProjectRepository.getInstance();
    }

    @Override
    public void navigateToTaskDetail(FragmentManager fragmentManager, List<Task> taskList, int position) {
        Bundle taskInfoBundle = new Bundle();
        taskInfoBundle.putSerializable("taskInfo", taskList.get(position));
        TaskDetailsFragment taskDetailsFragment = TaskDetailsFragment.newInstance(taskInfoBundle);
        taskDetailsFragment.setArguments(taskInfoBundle);
        taskDetailsFragment.show(fragmentManager, "task_details");
    }
}
