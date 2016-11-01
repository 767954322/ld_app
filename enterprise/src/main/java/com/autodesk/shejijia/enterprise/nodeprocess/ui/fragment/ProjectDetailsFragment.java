package com.autodesk.shejijia.enterprise.nodeprocess.ui.fragment;

import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.enterprise.base.fragments.BaseEnterpriseFragment;
import com.autodesk.shejijia.enterprise.nodeprocess.contract.ProjectDetailsContract;
import com.autodesk.shejijia.enterprise.nodeprocess.presenter.ProjectDetailsPresenter;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;

/**
 * Created by t_xuz on 10/20/16.
 * 项目详情
 */
public class ProjectDetailsFragment extends BaseEnterpriseFragment implements ProjectDetailsContract.View {

    private ProjectDetailsContract.Presenter mProjectDetailsPresenter;

    public ProjectDetailsFragment() {
    }

    public static ProjectDetailsFragment newInstance() {
        return new ProjectDetailsFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_project_details_view;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mProjectDetailsPresenter = new ProjectDetailsPresenter(mContext, this);
        if (getArguments().getLong("projectId") != 0) {
            mProjectDetailsPresenter.initRequestParams(getArguments().getLong("projectId"), true);
            mProjectDetailsPresenter.getProjectDetails();
        } else {
            LogUtils.e("GetProjectDetails", "you should input right projectId");
        }
    }
}
