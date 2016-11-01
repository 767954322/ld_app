package com.autodesk.shejijia.enterprise.nodeprocess.ui.fragment;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.enterprise.base.fragments.BaseEnterpriseFragment;
import com.autodesk.shejijia.enterprise.common.utils.ToastUtils;
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

        setHasOptionsMenu(true);
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


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.project_details_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.project_toolbar_info:
                ToastUtils.showShort(mContext, "projectInfo");
                // TODO: 11/1/16  跳转到项目信息页面
                break;
            case R.id.project_toolbar_message:
                ToastUtils.showShort(mContext, "projectMessage");
                // TODO: 11/1/16  跳转到消息中心页面 
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
