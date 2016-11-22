package com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment;


import android.app.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.ScreenUtil;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.ProjectDetailsContract;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.activity.CreateOrEditPlanActivity;
import com.autodesk.shejijia.shared.components.nodeprocess.presenter.ProjectDetailsPresenter;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.adapter.ProjectDetailsPagerAdapter;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.progressbar.ProgressbarIndicator;
import com.autodesk.shejijia.shared.framework.fragment.BaseConstructionFragment;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import java.util.List;

/**
 * Created by t_xuz on 10/20/16.
 * 项目详情
 */
public class ProjectDetailsFragment extends BaseConstructionFragment implements ProjectDetailsContract.View, View.OnClickListener {
    private final static int REQUEST_CODE_EDIT_PLAN = 0;

    private LinearLayout mProjectRootView;
    private RelativeLayout mContentTipView;
    private ViewPager mContentViewPager;
    private ProgressbarIndicator mProgressBarIndicator;
    private Button mCreatePlanBtn;
    private TextView mWorkStateView;
    private TextView mEditPlanBtn;
    private ProjectDetailsContract.Presenter mProjectDetailsPresenter;
    private ProjectDetailsPagerAdapter mFragmentPagerAdapter;

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
        mProjectRootView = (LinearLayout) rootView.findViewById(R.id.ll_project_details);
        mContentTipView = (RelativeLayout) rootView.findViewById(R.id.rl_content_tip);
        mContentViewPager = (ViewPager) rootView.findViewById(R.id.vp_task_list);
        mProgressBarIndicator = (ProgressbarIndicator) rootView.findViewById(R.id.progressBar_indicator);
        mCreatePlanBtn = (Button) rootView.findViewById(R.id.btn_create_plan);
        mWorkStateView = (TextView) rootView.findViewById(R.id.img_work_state);
        mEditPlanBtn = (TextView) rootView.findViewById(R.id.tv_edit_plan);

        setHasOptionsMenu(true);
    }

    @Override
    protected void initData() {
        mProjectDetailsPresenter = new ProjectDetailsPresenter(mContext, this);
        long projectId = getArguments().getLong(ConstructionConstants.BUNDLE_KEY_PROJECT_ID);
        if (projectId != 0) {
            LogUtils.e("projectDetails_projectId ", projectId + "");
            mProjectRootView.setVisibility(View.GONE);
            mProjectDetailsPresenter.initRequestParams(projectId, true);
            mProjectDetailsPresenter.getProjectDetails();
        } else {
            LogUtils.e("GetProjectDetails", "you should input right projectId");
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mCreatePlanBtn.setOnClickListener(this);
        mEditPlanBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_create_plan) {
            Intent intent = new Intent(getActivity(), CreateOrEditPlanActivity.class);
            intent.putExtra(ConstructionConstants.BUNDLE_KEY_PROJECT_ID, String.valueOf(getArguments().getLong(ConstructionConstants.BUNDLE_KEY_PROJECT_ID)));
            startActivity(intent);
        } else if (v.getId() == R.id.tv_edit_plan) {

        }
    }

    @Override
    public void updateProjectDetailsView(String memberType, List<BaseFragment> fragmentList, int currentMilestonePosition, boolean isKaiGongResolved) {

        mProjectRootView.setVisibility(View.VISIBLE);

        if (isKaiGongResolved) {
            if (memberType.equals(ConstructionConstants.MemberType.CLIENT_MANAGER)) {
                mEditPlanBtn.setVisibility(View.VISIBLE);
            } else {
                mEditPlanBtn.setVisibility(View.GONE);
            }
            mContentViewPager.setVisibility(View.VISIBLE);
            mContentTipView.setVisibility(View.GONE);
            mCreatePlanBtn.setVisibility(View.GONE);
            mWorkStateView.setVisibility(View.GONE);

            mFragmentPagerAdapter = new ProjectDetailsPagerAdapter(getFragmentManager(), fragmentList);
            mContentViewPager.setAdapter(mFragmentPagerAdapter);

            //progressbar indicator( must first have viewpager adapter)
            mProgressBarIndicator.setupWithViewPager(mContentViewPager);
            mProgressBarIndicator.setCurrentStatus(currentMilestonePosition);
            mContentViewPager.setCurrentItem(currentMilestonePosition);
        } else {
            mEditPlanBtn.setVisibility(View.GONE);
            mContentTipView.setVisibility(View.VISIBLE);
            if (memberType.equals(ConstructionConstants.MemberType.CLIENT_MANAGER)) {
                mCreatePlanBtn.setVisibility(View.VISIBLE);
                mWorkStateView.setVisibility(View.GONE);
                mContentViewPager.setVisibility(View.GONE);
            } else {
                mWorkStateView.setVisibility(View.VISIBLE);
                mCreatePlanBtn.setVisibility(View.GONE);
                mContentViewPager.setVisibility(View.GONE);
                mWorkStateView.setText(getString(R.string.work_ready));
                Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_work_open);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                mWorkStateView.setCompoundDrawables(null, drawable, null, null);
                mWorkStateView.setCompoundDrawablePadding(ScreenUtil.dip2px(20));
            }
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
