package com.autodesk.shejijia.shared.components.form.presenter;

import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.form.contract.ProjectInfoContract;

/**
 * Created by t_aij on 16/10/21.
 */

public class ProjectInfoPresenter implements ProjectInfoContract.Presenter {
    private ProjectInfoContract.View mView;

    public ProjectInfoPresenter(ProjectInfoContract.View view) {
        mView = view;
    }

    @Override
    public void submit(Task task) {
        String status = task.getStatus();
        switch (status) {
            case ConstructionConstants.TaskStatus.INPROGRESS:
            case ConstructionConstants.TaskStatus.DELAYED:
            case ConstructionConstants.TaskStatus.REINSPECTION_INPROGRESS:
            case ConstructionConstants.TaskStatus.REINSPECTION_DELAYED:
                mView.enterPrecheck(task);
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

}
