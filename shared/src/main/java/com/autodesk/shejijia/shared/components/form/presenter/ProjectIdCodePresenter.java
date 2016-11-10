package com.autodesk.shejijia.shared.components.form.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Member;
import com.autodesk.shejijia.shared.components.common.entity.microbean.PlanInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.form.contract.ProjectIdCodeContract;
import com.autodesk.shejijia.shared.components.form.data.FormRepository;
import com.autodesk.shejijia.shared.components.form.ui.activity.ProjectInfoActivity;
import com.autodesk.shejijia.shared.components.form.ui.activity.ScanQrCodeActivity;

import java.util.List;


/**
 * Created by t_aij on 16/10/21.
 */

public class ProjectIdCodePresenter implements ProjectIdCodeContract.Presenter {
    private ProjectIdCodeContract.View mView;
    private Context mContext;

    public ProjectIdCodePresenter(Context context, ProjectIdCodeContract.View view) {
        mView = view;
        mContext = context;
        mView.setToolbar();
    }


    @Override
    public void confirmProject() {
        String projectId = mView.getProjectId();
        if (TextUtils.isEmpty(projectId)) {
            mView.showError("项目编码不能为空,请重新输入");
            return;
        }
        Long pid = Long.valueOf(projectId);

        final Bundle params = new Bundle();
        params.putLong("pid", pid);

//        FormRepository.getInstance().getProjectTaskId(params, "", new ResponseCallback<Project>() {
//            @Override
//            public void onSuccess(Project data) {
//                Plan plan = data.getPlan();
//                String milestone = plan.getMilestone();
//                params.putString("tid",milestone);
//
//
//                Intent intent = new Intent(mContext,ProjectInfoActivity.class);
//                intent.putExtra("projectBean",data);
//                mContext.startActivity(intent);
//                mView.dismiss();
//            }
//
//            @Override
//            public void onError(String errorMsg) {
//                mView.showNetError(errorMsg);
//            }
//        });
        params.putBoolean("task_data",true);
        FormRepository.getInstance().getProjectTaskData(params, "", new ResponseCallback<ProjectInfo>() {
            @Override
            public void onSuccess(ProjectInfo data) {
                PlanInfo planInfo = data.getPlan();
                List<Task> taskList = planInfo.getTasks();
                String milestone = planInfo.getMilestone();
                Member role = null;
                for (Task task : taskList) {
                    if (milestone.equals(task.getTaskId())) {
                        if ("inspectorInspection".equals(task.getCategory())) {
                            List<Member> members = data.getMembers();
                            for (Member member : members) {
                                if ("member".equals(member.getRole())) {
                                    role = member;
                                    break;
                                }
                            }

                            Intent intent = new Intent(mContext, ProjectInfoActivity.class);
                            intent.putExtra("task", task);
                            intent.putExtra("building", data.getBuilding());
                            intent.putExtra("member", role);
                            mContext.startActivity(intent);

                            mView.dismiss();
                            return;
                        }
                        break;
                    }
                }

                mView.showError("当前没有监理需要验收的项目");

            }

            @Override
            public void onError(String errorMsg) {
                mView.showNetError(errorMsg);
            }
        });

    }

    @Override
    public void enterCode() {
        // TODO: 16/10/25 进入扫码页面
        Intent intent = new Intent(mContext, ScanQrCodeActivity.class);
        mContext.startActivity(intent);
        mView.dismiss();

    }

}
