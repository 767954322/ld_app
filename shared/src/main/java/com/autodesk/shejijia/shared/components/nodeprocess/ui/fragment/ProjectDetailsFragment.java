package com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.ProjectDetailsContract;
import com.autodesk.shejijia.shared.components.nodeprocess.presenter.ProjectDetailsPresenter;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.framework.fragment.BaseConstructionFragment;

/**
 * Created by t_xuz on 10/20/16.
 * 项目详情
 */
public class ProjectDetailsFragment extends BaseConstructionFragment implements ProjectDetailsContract.View {

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

        int itemId = item.getItemId();
        if (itemId == R.id.project_toolbar_info) {
            ToastUtils.showShort(mContext, "projectInfo");
            // TODO: 11/1/16  跳转到项目信息页面

        } else if (itemId == R.id.project_toolbar_message) {
            ToastUtils.showShort(mContext, "projectMessage");
            // TODO: 11/1/16  跳转到消息中心页面

        }

        return super.onOptionsItemSelected(item);
    }
}
