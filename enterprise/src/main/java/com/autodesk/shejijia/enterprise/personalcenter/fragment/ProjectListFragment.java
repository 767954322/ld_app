package com.autodesk.shejijia.enterprise.personalcenter.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.enterprise.base.fragments.BaseEnterpriseFragment;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.utility.Constants;
import com.autodesk.shejijia.enterprise.common.utils.UrlHelper;
import com.autodesk.shejijia.enterprise.nodeprocess.contract.ProjectListContract;
import com.autodesk.shejijia.enterprise.nodeprocess.presenter.ProjectListPresenter;
import com.autodesk.shejijia.enterprise.personalcenter.adapter.ProjectListAdapter;

import java.util.List;

/**
 * Created by t_xuz on 8/30/16.
 * 我页--全部项目列表页面
 */
public class ProjectListFragment extends BaseEnterpriseFragment implements View.OnClickListener, ProjectListContract.View {
    private TextView mTopBarTitle;
    private ImageButton mScreenBtn;
    private ImageButton mBackBtn;
    private RecyclerView mProjectListView;
    private ProjectListAdapter mProjectListAdapter;
    private ProjectListContract.Presenter mProjectListPresenter;

    public static ProjectListFragment newInstance() {
        return new ProjectListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_personal_center_project;
    }

    @Override
    protected void initView() {
        mTopBarTitle = (TextView) rootView.findViewById(R.id.tv_personal_title);
        mScreenBtn = (ImageButton) rootView.findViewById(R.id.imgBtn_screen);
        mBackBtn = (ImageButton) rootView.findViewById(R.id.imgBtn_back);
        mProjectListView = (RecyclerView) rootView.findViewById(R.id.rcy_project_list);
        mTopBarTitle.setText(getString(R.string.personal_center_completed_project));
        //init recyclerView
        mProjectListView.setLayoutManager(new LinearLayoutManager(mContext));
        mProjectListView.setHasFixedSize(true);
        mProjectListView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initData() {
        mProjectListPresenter = new ProjectListPresenter(mContext, this);
        String requestUrl = UrlHelper.getInstance().getUserProjectListUrl(Constants.PROJECT_LIST_BY_STATUS, null, Constants.PROJECT_STATUS_COMPLETE, false, 0);
        mProjectListPresenter.loadProjectListData(requestUrl, Constants.REFRESH_EVENT, "project_list", false);
    }

    @Override
    protected void initListener() {
        mScreenBtn.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBtn_back:
                mContext.getSupportFragmentManager().popBackStack();
                break;
            case R.id.imgBtn_screen:

                break;
        }
    }

    @Override
    public void refreshProjectListData(List<ProjectInfo> projectList) {
        //获取当前日期(默认就是当前日期)的任务列表
        //显示任务列表到页面上
        mProjectListAdapter = new ProjectListAdapter(projectList, R.layout.listitem_project_list_view, mContext);
        mProjectListView.setAdapter(mProjectListAdapter);

    }

    @Override
    public void addMoreProjectListData(List<ProjectInfo> projectList) {

    }

}
