package com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
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
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.ProjectDetailsContract;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.activity.CreateOrEditPlanActivity;
import com.autodesk.shejijia.shared.components.nodeprocess.presenter.ProjectDetailsPresenter;
import com.autodesk.shejijia.shared.framework.fragment.BaseConstructionFragment;

/**
 * Created by t_xuz on 10/20/16.
 * 项目详情
 */
public class ProjectDetailsFragment extends BaseConstructionFragment implements ProjectDetailsContract.View {

    private RecyclerView mTaskListView;
    private Button mCreatePlanBtn;
    private TextView mWorkStateView;
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
        mTaskListView = (RecyclerView) rootView.findViewById(R.id.rcy_task_list);
        mCreatePlanBtn = (Button) rootView.findViewById(R.id.button_create_plan);
        mWorkStateView = (TextView) rootView.findViewById(R.id.img_work_state);

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
    protected void initListener() {
        super.initListener();
        mCreatePlanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateOrEditPlanActivity.class);
                intent.putExtra("pid", String.valueOf(getArguments().getLong("projectId")));
                startActivity(intent);
            }
        });
    }

    @Override
    public void updateProjectDetailsView(String memberType, ProjectInfo projectInfo) {
        LogUtils.e("memberType+projectStatus" + "--" + memberType + "---" + projectInfo.getPlan().getStatus());
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
                    mCreatePlanBtn.setVisibility(View.GONE);
                    mTaskListView.setVisibility(View.GONE);
                }
                break;
            case "COMPLETION":
                mTaskListView.setVisibility(View.VISIBLE);
                mWorkStateView.setVisibility(View.GONE);
                mCreatePlanBtn.setVisibility(View.GONE);
                // TODO: 11/9/16 显示项目的任务列表
                break;
            default:
                break;
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
