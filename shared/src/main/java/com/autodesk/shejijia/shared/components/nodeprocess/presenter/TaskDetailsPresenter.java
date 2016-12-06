package com.autodesk.shejijia.shared.components.nodeprocess.presenter;

import android.support.annotation.Nullable;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Member;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Time;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.TaskDetailsContract;
import com.autodesk.shejijia.shared.components.nodeprocess.data.ProjectRepository;
import com.autodesk.shejijia.shared.components.nodeprocess.utility.TaskUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by t_xuz on 11/11/16.
 */

public class TaskDetailsPresenter implements TaskDetailsContract.Presenter {

    private TaskDetailsContract.View mTaskDetailsView;
    private ProjectRepository mProjectRepository;

    private ProjectInfo mProjectInfo;
    private Task mTask;

    public TaskDetailsPresenter(TaskDetailsContract.View taskDetailsView, ProjectInfo projectInfo, Task task) {
        this.mTaskDetailsView = taskDetailsView;
        this.mTask = task;
        this.mProjectInfo = projectInfo;
        mProjectRepository = ProjectRepository.getInstance();
    }

    @Override
    public void startPresent() {
        mTaskDetailsView.showTaskName(mTask.getName());
        mTaskDetailsView.showTaskStatus(mTask.getStatus());
        mTaskDetailsView.showTaskAddress(mProjectInfo.getBuilding().getAddress());
        mTaskDetailsView.showTaskTime(getTaskTime());
        mTaskDetailsView.showInspectCompanyInfo("监理公司", "111111111");//TODO get inspect company info

        mTaskDetailsView.editComment("Comments"); // TODO show or edit

        mTaskDetailsView.showTaskMembers(new ArrayList<Member>()); // TODO get members

        mTaskDetailsView.showActions(mTask, mProjectInfo);
    }

    @Override
    public void addComment(@Nullable String comment) {

    }

    @Override
    public void changeReserveTime(@Nullable Date date) {

    }

    private String getTaskTime() {
        Time time = TaskUtils.getDisplayTime(mTask);
        return DateUtil.getStringDateByFormat(DateUtil.iso8601ToDate(time.getStart()),
                UIUtils.getString(R.string.date_format_task_details));
    }
}
