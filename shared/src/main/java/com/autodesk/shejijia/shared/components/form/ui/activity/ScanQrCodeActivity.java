package com.autodesk.shejijia.shared.components.form.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.Project;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.tools.CaptureQrActivity;
import com.autodesk.shejijia.shared.components.form.data.FormRepository;
import com.google.zxing.Result;

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
        imageButton.setVisibility(View.GONE);

        TextView rightText = (TextView) findViewById(R.id.nav_right_textView);
        rightText.setVisibility(View.VISIBLE);
        rightText.setText("输入编码");
        rightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScanQrCodeActivity.this, ProjectIdCodeActivity.class));
                finish();
            }
        });

    }

    @Override
    public void handleDecode(Result result, Bitmap barcode) {
        // TODO: 16/11/1 处理返回的数据 以及对应的弹框显示
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String projectId = result.getText();
        if (!TextUtils.isEmpty(projectId) && projectId.matches("[0-9]+")) {
            // TODO: 16/11/3 扫码得到projectId ,判断是否得到项目详情,如果得到然后再将项目详情传递过去,没有的话显示网络错误,错误信息
            Bundle params = new Bundle();
            params.putLong("pid", Long.valueOf(projectId));

            FormRepository.getInstance().getProjectTaskId(params, null, new ResponseCallback<Project>() {
                @Override
                public void onSuccess(Project data) {
                    Intent intent = new Intent(ScanQrCodeActivity.this, ProjectInfoActivity.class);
                    intent.putExtra("projectBean", data);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onError(String errorMsg) {
                    Log.d("asdf", "错误信息了");
                    Intent intent = new Intent(ScanQrCodeActivity.this,ScanQrDialogActivity.class);
                    intent.putExtra("error",errorMsg);
                    startActivity(intent);
                }
            });


        } else {
            Intent intent = new Intent(this,ScanQrDialogActivity.class);
            intent.putExtra("format","二维码格式不正确,是否跳到输入编码?");
            startActivity(intent);
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
