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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.uielements.SwipeRefreshLayout;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.ScreenUtil;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.ProjectDetailsContract;
import com.autodesk.shejijia.shared.components.nodeprocess.presenter.ProjectDetailsPresenter;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.activity.CreateOrEditPlanActivity;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.adapter.ProjectDetailsPagerAdapter;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.progressbar.ProgressbarIndicator;
import com.autodesk.shejijia.shared.framework.fragment.BaseConstructionFragment;

import java.util.List;

/**
 * Created by t_xuz on 10/20/16.
 * 项目详情
 */


public class ProjectDetailsFragment extends BaseConstructionFragment implements ProjectDetailsContract.View, View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private final static int REQUEST_CODE_EDIT_PLAN = 0x0099;
    private final static int REQUEST_CODE_PROJECT_MESSAGE_CENTER = 0x0097;
    //    private String threadId;
    private LinearLayout mProjectRootView;
    private RelativeLayout mContentTipView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ViewPager mContentViewPager;
    private ProgressbarIndicator mProgressBarIndicator;
    private Button mCreatePlanBtn;
    private TextView mWorkStateView;
    private TextView mEditPlanBtn;
    private TextView mtvMenuBadge;
    private boolean mIsUnread = false;
    private ProjectDetailsContract.Presenter mProjectDetailsPresenter;
    private ProjectDetailsPagerAdapter mFragmentPagerAdapter;
    private boolean mIsRefresh;

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
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        mContentTipView = (RelativeLayout) rootView.findViewById(R.id.rl_content_tip);
        mContentViewPager = (ViewPager) rootView.findViewById(R.id.vp_task_list);
        mProgressBarIndicator = (ProgressbarIndicator) rootView.findViewById(R.id.progressBar_indicator);
        mCreatePlanBtn = (Button) rootView.findViewById(R.id.btn_create_plan);
        mWorkStateView = (TextView) rootView.findViewById(R.id.img_work_state);
        mEditPlanBtn = (TextView) rootView.findViewById(R.id.tv_edit_plan);
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat
                .getColor(mContext, R.color.colorPrimary));
        setHasOptionsMenu(true);
    }

    @Override
    protected void initData() {
        mIsRefresh = false;
        mProjectDetailsPresenter = new ProjectDetailsPresenter(getActivity(), this);
        long projectId = getArguments().getLong(ConstructionConstants.BUNDLE_KEY_PROJECT_ID);
        if (projectId != 0) {
            LogUtils.e("projectDetails_projectId ", projectId + "");
            mProjectRootView.setVisibility(View.GONE);
            mProjectDetailsPresenter.getUnreadMsgCount(projectId + "", TAG);
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
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        mIsRefresh = true;
        long projectId = getArguments().getLong(ConstructionConstants.BUNDLE_KEY_PROJECT_ID);
        if (projectId != 0) {
            LogUtils.e("projectDetails_projectId ", projectId + "");
            mProjectDetailsPresenter.initRequestParams(projectId, true);
            mProjectDetailsPresenter.initRefreshState(true);
            mProjectDetailsPresenter.getProjectDetails();
        } else {
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_create_plan || v.getId() == R.id.tv_edit_plan) {
            Intent intent = new Intent(getActivity(), CreateOrEditPlanActivity.class);
            intent.putExtra(ConstructionConstants.BUNDLE_KEY_PROJECT_ID, String.valueOf(getArguments().getLong(ConstructionConstants.BUNDLE_KEY_PROJECT_ID)));
            intent.putExtra(ConstructionConstants.BUNDLE_KEY_PLAN_OPERATION, v.getId() == R.id.btn_create_plan ? "start" : "edit");
            startActivityForResult(intent, REQUEST_CODE_EDIT_PLAN);
        }
    }

    @Override
    public void updateProjectDetailsView(String memberType, String avatarUrl, List<List<Task>> taskLists, int currentMilestonePosition, boolean isKaiGongResolved) {

        mProjectRootView.setVisibility(View.VISIBLE);
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }

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

            if (mIsRefresh) {
                mFragmentPagerAdapter.updateFragment(taskLists);
            } else {
                mFragmentPagerAdapter = new ProjectDetailsPagerAdapter(getFragmentManager(), avatarUrl, taskLists);
                mContentViewPager.setAdapter(mFragmentPagerAdapter);
            }

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
    public void updateUnreadMsgCountView(int count) {
        if (count != 0) {
            mIsUnread = true;
            mtvMenuBadge.setVisibility(View.VISIBLE);
        }
        mtvMenuBadge.setText(count + "");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.project_details_menu, menu);
        FrameLayout frameLayout = (FrameLayout) menu.findItem(R.id.project_toolbar_message).getActionView();
        mtvMenuBadge = (TextView) frameLayout.findViewById(R.id.menu_badge);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mtvMenuBadge.setVisibility(View.GONE);
                mProjectDetailsPresenter.navigateToMessageCenter(ProjectDetailsFragment.this, mIsUnread, REQUEST_CODE_PROJECT_MESSAGE_CENTER);
                mIsUnread = false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.project_toolbar_info) {
            mProjectDetailsPresenter.getProjectInformation();
            return true;
        } else if (itemId == R.id.project_toolbar_message) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_EDIT_PLAN:
                if (resultCode == Activity.RESULT_OK) {
                    mIsRefresh = true;
                    mProjectDetailsPresenter.initRefreshState(false);
                    mProjectDetailsPresenter.getProjectDetails();
                } else {
                    if (mCreatePlanBtn.getVisibility() == View.VISIBLE
                            && data != null && data.getBooleanExtra(ConstructionConstants.BUNDLE_KEY_IS_PLAN_EDITING, false)) {
                        mCreatePlanBtn.setText(R.string.continue_edit_plan);
                    }
                }
                break;
            case REQUEST_CODE_PROJECT_MESSAGE_CENTER:
                if (resultCode == Activity.RESULT_OK) {
                    mIsUnread = false;
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

}
