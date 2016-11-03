package com.autodesk.shejijia.shared.components.form.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.autodesk.shejijia.shared.components.common.entity.Project;
import com.autodesk.shejijia.shared.components.common.listener.LoadDataCallback;
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
        mView.setNavigationBar();
    }



    @Override
    public void ConfirmProject() {
        String projectId = mView.getProjectId();
        if (TextUtils.isEmpty(projectId)) {
            mView.showError("项目编码不能为空,请重新输入");
            return;
        }
        Long pid = Long.valueOf(projectId);

        Bundle params = new Bundle();
        params.putLong("pid",pid);

        FormRepository.getInstance().getProjectTaskId(params, "", new LoadDataCallback<Project>() {
            @Override
            public void onLoadSuccess(Project data) {
                Intent intent = new Intent(mContext,ProjectInfoActivity.class);
                intent.putExtra("projectBean",data);
                mContext.startActivity(intent);
                mView.dismiss();
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                mView.showNetError(errorMsg);
            }
        });

    }

    @Override
    public void enterCode() {
        // TODO: 16/10/25 进入扫码页面
        Intent intent = new Intent(mContext, ScanQrCodeActivity.class);
        //这个方式必须是activity才可以调用,因为这是有返回值的
        mContext.startActivity(intent);
        mView.dismiss();

    }

}
