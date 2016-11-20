package com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment;


import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.ProjectDetailsContract;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.activity.CreateOrEditPlanActivity;
import com.autodesk.shejijia.shared.components.nodeprocess.presenter.ProjectDetailsPresenter;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.adapter.PDFragmentPagerAdapter;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.progressbar.ProgressbarIndicator;
import com.autodesk.shejijia.shared.framework.fragment.BaseConstructionFragment;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import java.util.List;

/**
 * Created by t_xuz on 10/20/16.
 * 项目详情
 */

public class ProjectDetailsFragment extends BaseConstructionFragment implements ProjectDetailsContract.View {

    private static final int TASK_FRAGMENT_SIZE = 6;
    private ViewPager mContentViewPager;
    private ProgressbarIndicator progressBarIndicator;
    private Button mCreatePlanBtn;
    private TextView mWorkStateView;
    private ProjectDetailsContract.Presenter mProjectDetailsPresenter;
    private PDFragmentPagerAdapter mFragmentPagerAdapter;

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
        mContentViewPager = (ViewPager) rootView.findViewById(R.id.vp_task_list);
        progressBarIndicator = (ProgressbarIndicator)rootView.findViewById(R.id.progressBar_indicator);
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
                intent.putExtra(ConstructionConstants.BUNDLE_KEY_PROJECT_ID, String.valueOf(getArguments().getLong("projectId")));
                startActivityForResult(intent, REQUEST_CODE_EDIT_PLAN);
            }
        });
    }

    @Override
    public void updateProgressbarView() {

    }

    @Override
    public void updateViewpagerView(List<BaseFragment> fragmentList) {
        mFragmentPagerAdapter = new PDFragmentPagerAdapter(getFragmentManager(), fragmentList);
        mContentViewPager.setAdapter(mFragmentPagerAdapter);

        //progressbar indicator( must first have viewpager adapter)
        progressBarIndicator.setupWithViewPager(mContentViewPager);
        progressBarIndicator.setCurrentStatus(3);
        mContentViewPager.setCurrentItem(3);
    }

    @Override
    public void updateProjectDetailsView(String memberType, ProjectInfo projectInfo) {
        LogUtils.e("projectId+memberType+projectStatus", "--" + projectInfo.getProjectId() + "---" + memberType + "---" + projectInfo.getPlan().getStatus());
        // TODO: 11/16/16 两部分，一部分是progressbar里的数据处理及progressbar的显示 ，另一部分
        // TODO: 11/16/16 是viewpager里每个fragment里的数据处理及viewpager的显示

        mProjectDetailsPresenter.handleViewpagerData(projectInfo.getPlan(),TASK_FRAGMENT_SIZE);







        switch (projectInfo.getPlan().getStatus()) {
            case "OPEN":
            case "READY":
           /*     if (memberType.equals("clientmanager")) {
                    mCreatePlanBtn.setVisibility(View.VISIBLE);
                    mContentViewPager.setVisibility(View.GONE);
                    mWorkStateView.setVisibility(View.GONE);
                } else {
                    mWorkStateView.setVisibility(View.VISIBLE);
                    mWorkStateView.setText(getString(R.string.work_ready));
                    Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_work_open);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    mWorkStateView.setCompoundDrawables(null, drawable, null, null);
                    mWorkStateView.setCompoundDrawablePadding(ScreenUtil.dip2px(20));
                    mCreatePlanBtn.setVisibility(View.GONE);
                    mContentViewPager.setVisibility(View.GONE);
                }
                break;*/
            case "INPROGRESS":
             /*   if (memberType.equals("clientmanager")) {
                    mCreatePlanBtn.setVisibility(View.VISIBLE);
                    mContentViewPager.setVisibility(View.GONE);
                    mWorkStateView.setVisibility(View.GONE);
                } else {
                    mWorkStateView.setVisibility(View.VISIBLE);
                    mWorkStateView.setText(getString(R.string.work_inProgress));
                    Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_work_inprogress);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    mWorkStateView.setCompoundDrawables(null, drawable, null, null);
                    mWorkStateView.setCompoundDrawablePadding(ScreenUtil.dip2px(20));
                    mCreatePlanBtn.setVisibility(View.GONE);
                    mContentViewPager.setVisibility(View.GONE);
                }
                break;*/
            case "COMPLETION":
           /*     mContentViewPager.setVisibility(View.VISIBLE);
                mWorkStateView.setVisibility(View.GONE);
                mCreatePlanBtn.setVisibility(View.GONE);
                */
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
