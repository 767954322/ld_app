package com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment;


import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Like;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.uielements.swiperecyclerview.RefreshLoadMoreListener;
import com.autodesk.shejijia.shared.components.common.uielements.swiperecyclerview.SwipeRecyclerView;
import com.autodesk.shejijia.shared.components.common.utility.BackGroundUtils;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.ScreenUtil;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.message.activity.ProjectMessageCenterActivity;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.ProjectListContract;
import com.autodesk.shejijia.shared.components.nodeprocess.presenter.ProjectListPresenter;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.adapter.ProjectListAdapter;
import com.autodesk.shejijia.shared.framework.fragment.BaseConstructionFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by t_xuz on 8/25/16.
 * 首页-项目列表
 */
public class ProjectListFragment extends BaseConstructionFragment implements ProjectListContract.View,
        ProjectListAdapter.ProjectListItemListener, RefreshLoadMoreListener {

    private SwipeRecyclerView mProjectListView;
    private TextView mEmptyView;
    private ProjectListAdapter mProjectListAdapter;
    private ProjectListContract.Presenter mProjectListPresenter;
    private PopupWindow mScreenPopup;
    private boolean mIsFiltered;
    private MenuItem mMenuItem;

    public static ProjectListFragment newInstance() {
        return new ProjectListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_project_list_view;
    }

    @Override
    protected void initData() {
        mProjectListPresenter = new ProjectListPresenter(getActivity(), getChildFragmentManager(), this);
        //refresh ProjectLists
        String defaultSelectedDate = DateUtil.dateToIso8601(Calendar.getInstance().getTime());
        mProjectListPresenter.initFilterRequestParams(defaultSelectedDate, "false", ConstructionConstants.ProjectStatus.UNCOMPLET);
    }

    @Override
    protected void initView() {
        mProjectListView = (SwipeRecyclerView) rootView.findViewById(R.id.rcy_task_list);
        mEmptyView = (TextView) rootView.findViewById(R.id.tv_empty_message);
        //init recyclerView
        mProjectListView.getRecyclerView().setLayoutManager(new LinearLayoutManager(mContext));
        mProjectListView.getRecyclerView().setHasFixedSize(true);
        mProjectListView.getRecyclerView().setItemAnimator(new DefaultItemAnimator());
        mProjectListView.getSwipeRefreshLayout()
                .setColorSchemeColors(ContextCompat.getColor(mContext, R.color.colorPrimary));
        //init recyclerView adapter
        mProjectListAdapter = new ProjectListAdapter(new ArrayList<ProjectInfo>(0), R.layout.listitem_task_list_view, activity, this);
        mProjectListView.setAdapter(mProjectListAdapter);
        //set this fragment to hold optionsMenu
        setHasOptionsMenu(true);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mProjectListView.setRefreshLoadMoreListener(this);
        //让其自动刷新一下，会回调onRefresh()方法一次
        mProjectListView.setRefreshing(true);
    }

    /*
    * 筛选功能也会回调 onRefresh方法
    *
    * */
    @Override
    public void onRefresh() {
        mEmptyView.setVisibility(View.GONE);
        mProjectListView.setVisibility(View.VISIBLE);
        mProjectListView.onRefreshing();
        mProjectListPresenter.refreshProjectList();
    }

    @Override
    public void onLoadMore() {
        mEmptyView.setVisibility(View.GONE);
        mProjectListView.setVisibility(View.VISIBLE);
        mProjectListView.onLoadingMore();
        mProjectListPresenter.loadMoreProjectList();
    }

    /*
    * toolbar 日期变更点击回调方法（EnterpriseHomeActivity调用）
    * */
    public void refreshProjectListByDate(String date) {
        mProjectListPresenter.onFilterDateChange(date);
        mProjectListPresenter.refreshProjectList();
    }

    @Override
    public void refreshProjectListView(List<ProjectInfo> projectList) {
        if (projectList != null && projectList.size() > 0) {
            mEmptyView.setVisibility(View.GONE);
            mProjectListView.setVisibility(View.VISIBLE);
            mProjectListView.complete();
            mProjectListView.getRecyclerView().getLayoutManager().scrollToPosition(0);
            mProjectListAdapter.setProjectLists(projectList);
        } else {
            mEmptyView.setVisibility(View.VISIBLE);
            mProjectListView.setVisibility(View.GONE);
            mProjectListView.complete();
        }
    }

    @Override
    public void addMoreProjectListView(List<ProjectInfo> projectList) {
        if (projectList != null && projectList.size() > 0) {
            mProjectListView.complete();
            mProjectListAdapter.appendProjectLists(projectList);
        } else {
            mProjectListView.onNoMore(null);
        }
    }

    @Override
    public void refreshLikesButton(String filterLike, Like newLike, int likePosition) {
        mProjectListAdapter.updateProjectState(filterLike, newLike, likePosition);
    }

    @Override
    public void onProjectClick(List<ProjectInfo> projectList, int position) {
        mProjectListPresenter.navigateToProjectDetail(projectList, position);
    }

    @Override
    public void onTaskClick(ProjectInfo projectInfo, Task task) {
        mProjectListPresenter.navigateToTaskDetail(projectInfo, task);
    }

    @Override
    public void onStarLabelClick(List<ProjectInfo> projectList, boolean like, int position) {
        mProjectListPresenter.updateProjectLikesState(projectList, like, position);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_task_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        this.mMenuItem = item;
        int itemId = item.getItemId();
        if (itemId == R.id.home_toolbar_search) {
            ToastUtils.showShort(mContext, "search");
            // TODO: 10/25/16 跳转到搜索页面
            return true;
        } else if (itemId == R.id.home_toolbar_screen) {
            if (mIsFiltered) {
                mIsFiltered = false;
            } else {
                mIsFiltered = true;
            }
            initScreenPopupWin();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        if (mProjectListView.isRefreshing()) {
            mProjectListView.complete();
        }
    }

    private void initScreenPopupWin() {
        final View contentView = LayoutInflater.from(mContext).inflate(R.layout.popup_screen_dialog, null);
        final TextView mScreenAll = (TextView) contentView.findViewById(R.id.tv_screen_all);
        final TextView mScreenLike = (TextView) contentView.findViewById(R.id.tv_screen_like);
        //set popup state
        if (!TextUtils.isEmpty(mProjectListPresenter.getScreenPopupState())
                && mProjectListPresenter.getScreenPopupState().equals("true")) {
            mScreenAll.setTextColor(ContextCompat.getColor(mContext, R.color.font_gray));
            mScreenLike.setTextColor(ContextCompat.getColor(mContext, R.color.con_blue));
            mMenuItem.setIcon(ContextCompat.getDrawable(mContext, R.drawable.ic_menu_filtered));
        } else {
            mScreenAll.setTextColor(ContextCompat.getColor(mContext, R.color.con_blue));
            mScreenLike.setTextColor(ContextCompat.getColor(mContext, R.color.font_gray));
            mMenuItem.setIcon(ContextCompat.getDrawable(mContext, R.drawable.ic_menu_filter));
        }
        //listener
        mScreenAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScreenAll.setTextColor(ContextCompat.getColor(mContext, R.color.con_blue));
                mScreenLike.setTextColor(ContextCompat.getColor(mContext, R.color.font_gray));
                mProjectListPresenter.onFilterLikeChange("false");
                if (!mProjectListView.isRefreshing()) {
                    mProjectListView.setRefreshing(true);
                }
                if (!TextUtils.isEmpty(mProjectListPresenter.getScreenPopupState())
                        && mProjectListPresenter.getScreenPopupState().equals("true")) {
                    mMenuItem.setIcon(ContextCompat.getDrawable(mContext, R.drawable.ic_menu_filtered));
                } else {
                    mMenuItem.setIcon(ContextCompat.getDrawable(mContext, R.drawable.ic_menu_filter));
                }
                mScreenPopup.dismiss();
            }
        });
        mScreenLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScreenLike.setTextColor(ContextCompat.getColor(mContext, R.color.con_blue));
                mScreenAll.setTextColor(ContextCompat.getColor(mContext, R.color.font_gray));
                mProjectListPresenter.onFilterLikeChange("true");
                if (!mProjectListView.isRefreshing()) {
                    mProjectListView.setRefreshing(true);
                }
                if (!TextUtils.isEmpty(mProjectListPresenter.getScreenPopupState())
                        && mProjectListPresenter.getScreenPopupState().equals("true")) {
                    mMenuItem.setIcon(ContextCompat.getDrawable(mContext, R.drawable.ic_menu_filtered));
                } else {
                    mMenuItem.setIcon(ContextCompat.getDrawable(mContext, R.drawable.ic_menu_filter));
                }
                mScreenPopup.dismiss();
            }
        });
        contentView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mScreenPopup.dismiss();
                    return true;
                }
                return false;
            }
        });
        contentView.setFocusableInTouchMode(true);
        if (mScreenPopup == null) {
            mScreenPopup = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        mScreenPopup.setFocusable(true);
        mScreenPopup.setOutsideTouchable(true);
        mScreenPopup.setBackgroundDrawable(new ColorDrawable());
        mScreenPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                BackGroundUtils.dimWindowBackground(mContext, 0.7f, 1.0f);
            }
        });
        View view = (((Activity) mContext).findViewById(android.R.id.content)).getRootView();
        mScreenPopup.setAnimationStyle(R.style.pop_top_animation);
        BackGroundUtils.dimWindowBackground(mContext, 1.0f, 0.7f);
        mScreenPopup.showAtLocation(view, Gravity.TOP, 0, ScreenUtil.dip2px(80));

    }

}
