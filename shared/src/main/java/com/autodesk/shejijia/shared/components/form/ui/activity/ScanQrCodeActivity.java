package com.autodesk.shejijia.shared.components.form.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
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
                    finish();
                }
            });
        }

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
            FormRepository.getInstance().getProjectTaskData(params, "", new ResponseCallback<ProjectInfo>() {
                @Override
                public void onSuccess(ProjectInfo data) {
                    PlanInfo planInfo = data.getPlan();
                    List<Task> taskList = planInfo.getTasks();
                    for (Task task : taskList) {
                        //根据监理进来:1,修改;2,查看
                        if ("inspectorInspection".equals(task.getCategory())) {   //按照任务状态来分类,现在是监理验收
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
                            if (statusList.contains(status)) {
                                for (Member member : data.getMembers()) {
                                    if ("member".equals(member.getRole())) {
                                        role = member;
                                        break;
                                    }
                                }
                                Intent intent = new Intent(ScanQrCodeActivity.this, ProjectInfoActivity.class);
                                intent.putExtra("task", task);
                                intent.putExtra("building", data.getBuilding());
                                intent.putExtra("member", role);
                                startActivity(intent);

                                finish();
                                return;
                            }

                        }

                    }

                    Intent intent = new Intent(ScanQrCodeActivity.this, ScanQrDialogActivity.class);
                    intent.putExtra("error", UIUtils.getString(R.string.inspect_show_no_task_error));
                    startActivity(intent);

                }

                @Override
                public void onError(String errorMsg) {
                    Intent intent = new Intent(ScanQrCodeActivity.this, ScanQrDialogActivity.class);
                    intent.putExtra("error", errorMsg);
                    startActivity(intent);
                }
            });

        } else {
            Intent intent = new Intent(this, ScanQrDialogActivity.class);
            intent.putExtra("error", UIUtils.getString(R.string.inspect_show_format_error));
            startActivity(intent);

        }


    }

}
