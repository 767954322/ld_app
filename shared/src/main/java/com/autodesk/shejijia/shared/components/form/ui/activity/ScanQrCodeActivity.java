package com.autodesk.shejijia.shared.components.form.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Member;
import com.autodesk.shejijia.shared.components.common.entity.microbean.PlanInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.tools.CaptureQrActivity;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.form.data.FormRepository;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_aij on 16/10/25.
 */

public class ScanQrCodeActivity extends CaptureQrActivity {

    @Override
    protected void initListener() {
        super.initListener();
        setNavigationBar();
    }

    @Override
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String projectId = result.getText();

        if (!TextUtils.isEmpty(projectId) && projectId.matches("[0-9]+")) {
            Bundle params = new Bundle();
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
                                Intent intent = new Intent(ScanQrCodeActivity.this, ProjectInfoActivity.class);
                                intent.putExtra("task", task);
                                intent.putExtra("building", data.getBuilding());
                                intent.putExtra("member", role);
                                startActivity(intent);

                                return;
                            }

                        }

                    }

                    Intent intent = new Intent(ScanQrCodeActivity.this, ScanQrDialogActivity.class);
                    intent.putExtra("error", UIUtils.getString(R.string.inspect_show_no_task_error));
                    startActivity(intent);

                }

                @Override
                public void onError(ResponseError error) {
                    Intent intent = new Intent(ScanQrCodeActivity.this, ScanQrDialogActivity.class);
                    intent.putExtra("error", error.getMessage());
                    startActivity(intent);
                }
            });

        } else {
            Intent intent = new Intent(this, ScanQrDialogActivity.class);
            intent.putExtra("error", UIUtils.getString(R.string.inspect_show_format_error));
            startActivity(intent);

        }


    }

    private void setNavigationBar() {
        ImageButton imageButton = (ImageButton) findViewById(com.autodesk.shejijia.shared.R.id.nav_left_imageButton);
        if (imageButton != null) {
            imageButton.setVisibility(View.GONE);
        }

        TextView rightText = (TextView) findViewById(R.id.nav_right_textView);
        if (rightText != null) {
            rightText.setVisibility(View.VISIBLE);
            rightText.setText(R.string.input_code);
            rightText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ScanQrCodeActivity.this, ProjectIdCodeActivity.class));
                }
            });
        }

    }

}
