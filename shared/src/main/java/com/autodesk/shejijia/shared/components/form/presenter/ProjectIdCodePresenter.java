package com.autodesk.shejijia.shared.components.form.presenter;

import android.os.Bundle;
import android.text.TextUtils;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Member;
import com.autodesk.shejijia.shared.components.common.entity.microbean.PlanInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.form.contract.ProjectIdCodeContract;
import com.autodesk.shejijia.shared.components.form.data.FormRepository;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by t_aij on 16/10/21.
 */

public class ProjectIdCodePresenter implements ProjectIdCodeContract.Presenter {
    private ProjectIdCodeContract.View mView;

    public ProjectIdCodePresenter(ProjectIdCodeContract.View view) {
        mView = view;
    }


    @Override
    public void enterProjectInfo() {
        String projectId = mView.getProjectId();
        if (TextUtils.isEmpty(projectId)) {
            mView.showError(UIUtils.getString(R.string.inspect_show_null_error));
            return;
        }

        final Bundle params = new Bundle();
        params.putLong("pid", Long.valueOf(projectId));
        params.putBoolean("task_data", true);

        FormRepository.getInstance().getProjectTaskData(params, "", new ResponseCallback<ProjectInfo, ResponseError>() {
            @Override
            public void onSuccess(ProjectInfo data) {
                PlanInfo planInfo = data.getPlan();
                List<Task> taskList = planInfo.getTasks();
                for (Task task : taskList) {
                    //根据项目的类型和状态,监理的进来:1,修改;2,查看
                    if (ConstructionConstants.TaskCategory.INSPECTOR_INSPECTION.equals(task.getCategory())) {
                        String status = task.getStatus();
                        Member role = null;
                        List<String> statusList = new ArrayList<>();
                        statusList.add(ConstructionConstants.TaskStatus.INPROGRESS);  //以下为修改项
                        statusList.add(ConstructionConstants.TaskStatus.DELAYED);
                        statusList.add(ConstructionConstants.TaskStatus.REINSPECTION_INPROGRESS);
                        statusList.add(ConstructionConstants.TaskStatus.REINSPECTION_DELAYED);
//                        statusList.add("REJECTED");       //以下为查看项
//                        statusList.add("QUALIFIED");
//                        statusList.add("REINSPECTION");
//                        statusList.add("RECTIFICATION");
//                        statusList.add("REINSPECTION_AND_RECTIFICATION");
                        if (statusList.contains(status)) {
                            for (Member member : data.getMembers()) {
                                if (ConstructionConstants.MemberType.MEMBER.equals(member.getRole())) {
                                    role = member;
                                    break;
                                }
                            }
                            mView.enterProjectInfo(task, data.getBuilding(), role);
                            return;
                        }

                    }

                }

                mView.showError(UIUtils.getString(R.string.inspect_show_no_task_error));

            }

            @Override
            public void onError(ResponseError error) {
                mView.showNetError(error);
            }
        });

    }

}
