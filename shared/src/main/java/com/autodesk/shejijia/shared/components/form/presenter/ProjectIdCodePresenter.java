package com.autodesk.shejijia.shared.components.form.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.autodesk.shejijia.shared.components.common.entity.Project;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.form.contract.ProjectIdCodeContract;
import com.autodesk.shejijia.shared.components.form.data.FormRepository;
import com.autodesk.shejijia.shared.components.form.ui.activity.ProjectInfoActivity;
import com.autodesk.shejijia.shared.components.form.ui.activity.ScanQrCodeActivity;


/**
 * Created by t_aij on 16/10/21.
 */

public class ProjectIdCodePresenter implements ProjectIdCodeContract.Presenter{
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

        Bundle params = new Bundle();
        params.putLong("pid",pid);

        FormRepository.getInstance().getProjectTaskId(params, "", new ResponseCallback<Project>() {
            @Override
            public void onSuccess(Project data) {
                Intent intent = new Intent(mContext,ProjectInfoActivity.class);
                intent.putExtra("projectBean",data);
                mContext.startActivity(intent);
                mView.dismiss();
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
