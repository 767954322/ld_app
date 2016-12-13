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
import java.util.Map;


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
        final String projectId = mView.getProjectId();
        if (TextUtils.isEmpty(projectId)) {
            mView.showError(UIUtils.getString(R.string.inspect_show_null_error));
            return;
        }

        FormRepository.getInstance().verifyInspector(Long.valueOf(projectId), new ResponseCallback<Map, ResponseError>() {
            @Override
            public void onSuccess(Map data) {
                boolean allow_inspect = (boolean) data.get("allow_inspect");
                if(!allow_inspect) {
                    mView.showError("该监理不能验收该项目");
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
                                statusList.add(ConstructionConstants.TaskStatus.INPROGRESS.toUpperCase());  //以下为修改项
                                statusList.add(ConstructionConstants.TaskStatus.DELAYED.toUpperCase());
                                statusList.add(ConstructionConstants.TaskStatus.REINSPECT_INPROGRESS.toUpperCase());
                                statusList.add(ConstructionConstants.TaskStatus.REINSPECT_DELAY.toUpperCase());
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

//                                    List<SubTask> subTaskList = task.getSubTasks();
//                                    for (int i = 0; i < subTaskList.size(); i++) {
//                                        String type = subTaskList.get(i).getType().toUpperCase();
//                                        if(statusList.contains(type)) {
//
//                                        }
//                                    }


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

            @Override
            public void onError(ResponseError error) {
                mView.showNetError(error);
            }
        });



    }

}
