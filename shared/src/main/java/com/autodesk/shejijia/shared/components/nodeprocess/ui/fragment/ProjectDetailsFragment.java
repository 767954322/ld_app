package com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.ScreenUtil;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.ProjectDetailsContract;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.activity.CreateOrEditPlanActivity;
import com.autodesk.shejijia.shared.components.nodeprocess.presenter.ProjectDetailsPresenter;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.adapter.PDTaskListAdapter;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.adapter.TaskListAdapter;
import com.autodesk.shejijia.shared.framework.fragment.BaseConstructionFragment;

import java.util.List;

/**
 * Created by t_xuz on 10/20/16.
 * 项目详情
 */
public class ProjectDetailsFragment extends BaseConstructionFragment implements ProjectDetailsContract.View, PDTaskListAdapter.TaskListItemClickListener {
    private final static int REQUEST_CODE_EDIT_PLAN = 0;

    private RecyclerView mTaskListView;
    private Button mCreatePlanBtn;
    private TextView mWorkStateView;
    private ProjectDetailsContract.Presenter mProjectDetailsPresenter;
    private PDTaskListAdapter mTaskListAdapter;

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
        mTaskListView = (RecyclerView) rootView.findViewById(R.id.rcy_task_list);
        mCreatePlanBtn = (Button) rootView.findViewById(R.id.button_create_plan);
        mWorkStateView = (TextView) rootView.findViewById(R.id.img_work_state);

        setHasOptionsMenu(true);
    }

    @Override
    protected void initData() {
        mProjectDetailsPresenter = new ProjectDetailsPresenter(mContext, this);
        if (getArguments().getLong("projectId") != 0) {
            LogUtils.e("projectDetails_projectId ", getArguments().getLong("projectId") + "");
            mProjectDetailsPresenter.initRequestParams(getArguments().getLong("projectId"), true);
            mProjectDetailsPresenter.getProjectDetails();
        } else {
            LogUtils.e("GetProjectDetails", "you should input right projectId");
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mCreatePlanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateOrEditPlanActivity.class);
                intent.putExtra("pid", String.valueOf(getArguments().getLong("projectId")));
                startActivityForResult(intent, REQUEST_CODE_EDIT_PLAN);
            }
        });
    }

    @Override
    public void updateProjectDetailsView(String memberType, ProjectInfo projectInfo) {
        LogUtils.e("projectId+memberType+projectStatus", "--" + projectInfo.getProjectId() + "---" + memberType + "---" + projectInfo.getPlan().getStatus());
        switch (projectInfo.getPlan().getStatus()) {
            case "OPEN":
            case "READY":
                if (memberType.equals("clientmanager")) {
                    mCreatePlanBtn.setVisibility(View.VISIBLE);
                    mTaskListView.setVisibility(View.GONE);
                    mWorkStateView.setVisibility(View.GONE);
                } else {
                    mWorkStateView.setVisibility(View.VISIBLE);
                    mWorkStateView.setText(getString(R.string.work_ready));
                    Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_work_open);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    mWorkStateView.setCompoundDrawables(null, drawable, null, null);
                    mWorkStateView.setCompoundDrawablePadding(ScreenUtil.dip2px(20));
                    mCreatePlanBtn.setVisibility(View.GONE);
                    mTaskListView.setVisibility(View.GONE);
                }
                break;
            case "INPROGRESS":
                if (memberType.equals("clientmanager")) {
                    mCreatePlanBtn.setVisibility(View.VISIBLE);
                    mTaskListView.setVisibility(View.GONE);
                    mWorkStateView.setVisibility(View.GONE);
                } else {
                    mWorkStateView.setVisibility(View.VISIBLE);
                    mWorkStateView.setText(getString(R.string.work_inProgress));
                    Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_work_inprogress);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    mWorkStateView.setCompoundDrawables(null, drawable, null, null);
                    mWorkStateView.setCompoundDrawablePadding(ScreenUtil.dip2px(20));
                    mCreatePlanBtn.setVisibility(View.GONE);
                    mTaskListView.setVisibility(View.GONE);
                }
                break;
            case "COMPLETION":
               /* mTaskListView.setVisibility(View.VISIBLE);
                mWorkStateView.setVisibility(View.GONE);
                mCreatePlanBtn.setVisibility(View.GONE);
                //init recyclerView
                mTaskListView.setLayoutManager(new LinearLayoutManager(mContext));
                mTaskListView.setHasFixedSize(true);
                mTaskListView.setItemAnimator(new DefaultItemAnimator());
                //init adapter
                mTaskListAdapter = new PDTaskListAdapter(projectInfo.getPlan().getTasks(), R.layout.listitem_projectdetails_task_list_view, mContext, this);
                mTaskListView.setAdapter(mTaskListAdapter);*/
                break;
            default:
                break;
        }
    }

    @Override
    public void showProjectInfoDialog(Bundle projectInfoBundle) {
        ProjectInfoFragment projectInfoFragment = ProjectInfoFragment.newInstance(projectInfoBundle);
        projectInfoFragment.show(getFragmentManager(), "project_info");
    }

    @Override
    public void cancelProjectInfoDialog() {
        // TODO: 11/11/16 其他更加友好的提示方式 
        ToastUtils.showShort(mContext, "you couldn't get right project information");
    }

    @Override
    public void onTaskClick(List<Task> taskList, int position) {
        mProjectDetailsPresenter.navigateToTaskDetail(getFragmentManager(), taskList, position);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.project_details_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.project_toolbar_info) {
            mProjectDetailsPresenter.getProjectInformation();
            return true;
        } else if (itemId == R.id.project_toolbar_message) {
            mProjectDetailsPresenter.navigateToMessageCenter();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_EDIT_PLAN:
                if (requestCode == Activity.RESULT_OK) {
                    //TODO refresh project
                    mProjectDetailsPresenter.getProjectDetails();
                } else {
                    // TODO update button text
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
}
