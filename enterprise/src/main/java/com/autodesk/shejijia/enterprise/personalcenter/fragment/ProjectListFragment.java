package com.autodesk.shejijia.enterprise.personalcenter.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.enterprise.base.fragments.BaseFragment;
import com.autodesk.shejijia.enterprise.common.utils.Constants;
import com.autodesk.shejijia.enterprise.common.utils.UrlHelper;
import com.autodesk.shejijia.enterprise.nodeprocess.contract.ProjectListContract;
import com.autodesk.shejijia.enterprise.nodeprocess.data.entity.TaskListBean;
import com.autodesk.shejijia.enterprise.nodeprocess.presenter.ProjectListPresenter;
import com.autodesk.shejijia.enterprise.personalcenter.adapter.ProjectListAdapter;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.utility.SharedPreferencesUtils;

import java.util.List;

/**
 * Created by t_xuz on 8/30/16.
 * 我页--全部项目列表页面
 */
public class ProjectListFragment extends BaseFragment implements View.OnClickListener, ProjectListContract.View {
    private TextView mTopBarTitle;
    private ImageButton mScreenBtn;
    private ImageButton mBackBtn;
    private RecyclerView mProjectListView;
    private ProjectListAdapter mProjectListAdapter;
    private MemberEntity entity;
    private List<TaskListBean.TaskList> taskLists;
    private ProjectListContract.Presenter mProjectListPresenter;

    public static ProjectListFragment newInstance() {
        return new ProjectListFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal_center_project;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mTopBarTitle = (TextView) view.findViewById(R.id.tv_personal_title);
        mScreenBtn = (ImageButton) view.findViewById(R.id.imgBtn_screen);
        mBackBtn = (ImageButton) view.findViewById(R.id.imgBtn_back);
        mProjectListView = (RecyclerView) view.findViewById(R.id.rcy_project_list);
        mTopBarTitle.setText(getString(R.string.personal_center_completed_project));
        //init recyclerView
        mProjectListView.setLayoutManager(new LinearLayoutManager(mContext));
        mProjectListView.setHasFixedSize(true);
        mProjectListView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initData() {
        entity = (MemberEntity) SharedPreferencesUtils.getObject(mContext, Constants.USER_INFO);
        mProjectListPresenter = new ProjectListPresenter(mContext, this);
        if (entity != null && !TextUtils.isEmpty(entity.getHs_accesstoken())) {
            String requestUrl = UrlHelper.getInstance().getUserProjectListUrl(Constants.PROJECT_LIST_BY_STATUS, null, Constants.PROJECT_STATUS_COMPLETE, false, 0);
            mProjectListPresenter.loadProjectListData(requestUrl, Constants.REFRESH_EVENT, "project_list", false);
        }
    }

    @Override
    protected void initEvents() {
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
    public void refreshProjectListData(TaskListBean taskListBean) {
        if (taskListBean != null) {
            //获取当前日期(默认就是当前日期)的任务列表
            taskLists = taskListBean.getData();
            if (taskLists != null && taskLists.size() > 0) {
                //显示任务列表到页面上
                mProjectListAdapter = new ProjectListAdapter(taskLists, R.layout.listitem_project_list_view, mContext);
                mProjectListView.setAdapter(mProjectListAdapter);
            }
        }
    }

    @Override
    public void addMoreProjectListData(TaskListBean taskListBean) {

    }

}
