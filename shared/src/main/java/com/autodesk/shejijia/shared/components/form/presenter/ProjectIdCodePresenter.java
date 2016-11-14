package com.autodesk.shejijia.shared.components.form.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Member;
import com.autodesk.shejijia.shared.components.common.entity.microbean.MileStone;
import com.autodesk.shejijia.shared.components.common.entity.microbean.PlanInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.form.contract.ProjectIdCodeContract;
import com.autodesk.shejijia.shared.components.form.data.FormRepository;
import com.autodesk.shejijia.shared.components.form.ui.activity.ProjectInfoActivity;
import com.autodesk.shejijia.shared.components.form.ui.activity.ScanQrCodeActivity;

import java.util.ArrayList;
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
        params.putBoolean("task_data",true);

        FormRepository.getInstance().getProjectTaskData(params, "", new ResponseCallback<ProjectInfo>() {
            @Override
            public void onSuccess(ProjectInfo data) {
                PlanInfo planInfo = data.getPlan();
                List<Task> taskList = planInfo.getTasks();
                for (Task task : taskList) {
                    //根据监理进来:1,修改;
                    if("inspectorInspection".equals(task.getCategory())) {   //按照任务状态来分类,现在是监理验收
                        String status = task.getStatus();
                        Member role = null;
                        List<String> statusList = new ArrayList<>();
                        statusList.add("INPROGRESS");  //验收进行中
                        statusList.add("DELAYED");     //验收延期
                        statusList.add("REINSPECTION_INPROGRESS");   //复验进行中
                        statusList.add("REINSPECTION_DELAYED");    // 复验延期
//                        statusList.add("REJECTED");
//                        statusList.add("QUALIFIED");
//                        statusList.add("REINSPECTION");
//                        statusList.add("RECTIFICATION");
//                        statusList.add("REINSPECTION_AND_RECTIFICATION");  //check
                        if(statusList.contains(status)) {
                            for (Member member : data.getMembers()) {
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
