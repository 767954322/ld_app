package com.autodesk.shejijia.shared.components.nodeprocess.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Comment;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Member;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Time;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.ResponseErrorUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.common.utility.UserInfoUtils;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.TaskDetailsContract;
import com.autodesk.shejijia.shared.components.nodeprocess.data.ProjectRepository;
import com.autodesk.shejijia.shared.components.nodeprocess.utility.TaskUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;

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

    private String mEditingComment = null;

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

        String memType = UserInfoUtils.getMemberType(AdskApplication.getInstance());
        if (ConstructionConstants.MemberType.MATERIAL_STAFF.equalsIgnoreCase(memType)) {
            mTaskDetailsView.editComment(getDisplayComment(mTask));
        } else {
            mTaskDetailsView.showComment(getDisplayComment(mTask));
        }

        mTaskDetailsView.showTaskMembers(new ArrayList<Member>()); // TODO get members

        mTaskDetailsView.showActions(mTask, mProjectInfo);
    }

    @Override
    public void updateComment(@Nullable String comment) {
        mEditingComment = comment;
    }

    @Override
    public void submitComment() {
        if (mEditingComment == null) {
            mTaskDetailsView.close();
        } else {
            mTaskDetailsView.showLoading();
            Bundle params = new Bundle();
            params.putString(ConstructionConstants.BUNDLE_KEY_PROJECT_ID, String.valueOf(mProjectInfo.getProjectId()));
            params.putString(ConstructionConstants.BUNDLE_KEY_TASK_ID, String.valueOf(mTask.getTaskId()));
            params.putString(ConstructionConstants.BUNDLE_KEY_TASK_COMMENT_CONTENT, mEditingComment);

            Comment comment = getComment(mTask);
            if (comment != null) {
                params.putString(ConstructionConstants.BUNDLE_KEY_TASK_COMMENT_ID, String.valueOf(comment.getCommentId()));
            }

            mProjectRepository.submitTaskComment(params, "SUBMIT_COMMENT", new ResponseCallback<Void, ResponseError>() {
                @Override
                public void onSuccess(Void data) {
                    //TODO update dialog
                    mTaskDetailsView.hideLoading();
                    mTaskDetailsView.close();
                }

                @Override
                public void onError(ResponseError error) {
                    //TODO update dialog
                    mTaskDetailsView.hideLoading();
                    mTaskDetailsView.showError(error.getMessage());
                }
            });
        }
    }

    @Override
    public void changeReserveTime(@Nullable Date date) {
        mTaskDetailsView.showLoading();
        Bundle params = new Bundle();
        params.putString(ConstructionConstants.BUNDLE_KEY_PROJECT_ID, String.valueOf(mProjectInfo.getProjectId()));
        params.putString(ConstructionConstants.BUNDLE_KEY_TASK_ID, String.valueOf(mTask.getTaskId()));
        params.putString(ConstructionConstants.BUNDLE_KEY_TASK_START_DATE, DateUtil.dateToIso8601(date));
        mProjectRepository.reserveTask(params, "RESERVE_TASK", new ResponseCallback<Void, ResponseError>() {
            @Override
            public void onSuccess(Void data) {
                mTaskDetailsView.hideLoading();
                fetchTask();
            }

            @Override
            public void onError(ResponseError error) {
                mTaskDetailsView.hideLoading();
                mTaskDetailsView.showError(error.getMessage());
            }
        });
    }

    private void fetchTask() {
        mTaskDetailsView.showLoading();
        Bundle params = new Bundle();
        params.putString(ConstructionConstants.BUNDLE_KEY_PROJECT_ID, String.valueOf(mProjectInfo.getProjectId()));
        params.putString(ConstructionConstants.BUNDLE_KEY_TASK_ID, String.valueOf(mTask.getTaskId()));
        mProjectRepository.getTask(params, "GET_TASK", new ResponseCallback<Task, ResponseError>() {
            @Override
            public void onSuccess(Task data) {
                mTaskDetailsView.hideLoading();
                // TODO dirty pre page
                mTask = data;
                startPresent();
            }

            @Override
            public void onError(ResponseError error) {
                mTaskDetailsView.hideLoading();
                mTaskDetailsView.showError(error.getMessage());
            }
        });
    }

    private String getTaskTime() {
        Time time = TaskUtils.getDisplayTime(mTask);
        return DateUtil.getStringDateByFormat(DateUtil.iso8601ToDate(time.getStart()),
                UIUtils.getString(R.string.date_format_task_details));
    }

    private String getDisplayComment(Task task) {
        if (mEditingComment != null) {
            return mEditingComment;
        }

        Comment comment = getComment(task);
        return comment == null ? "" : comment.getContent();
    }

    private Comment getComment(Task task) {
        ArrayList<Comment> comments = task.getComments();
        if (comments != null && !comments.isEmpty()) {
            return comments.get(0);
        }

        return null;
    }
}
