package com.autodesk.shejijia.shared.components.form.presenter;

import com.autodesk.shejijia.shared.components.form.contract.ProjectInfoContract;

/**
 * Created by t_aij on 16/10/21.
 */

public class ProjectInfoPresenter implements ProjectInfoContract.Presenter {
    private ProjectInfoContract.View mView;

    public ProjectInfoPresenter(ProjectInfoContract.View view) {
        mView = view;
        mView.setToolbar();
    }

    @Override
    public void confirm() {
        // TODO: 16/10/28 根据项目信息的状态,选择进入不同的表格
        String status = mView.getStatus();
        switch (status) {
            case "INPROGRESS":  //进行中,修改
            case "DELAYED":     //已延期,修改
                mView.enterPrecheck(mView.getTask());
//                mView.dismiss();
                break;
            case "REINSPECTION_INPROGRESS":  //复验进行中,修改
            case "REINSPECTION_DELAYED":

                break;
            case "REJECTED":   //验收拒绝,查看
                break;
            case "QUALIFIED":   //合格,查看
                break;
            case "REINSPECTION":   //复验项,查看
                break;
            case "RECTIFICATION":   //整改项,查看
                break;
            case "REINSPECTION_AND_RECTIFICATION":   //包含复验项和整改项,查看
                break;
            default:
                break;

        }

    }

    @Override
    public void cancel() {
        mView.selectCancel();
        mView.dismiss();
    }

}
