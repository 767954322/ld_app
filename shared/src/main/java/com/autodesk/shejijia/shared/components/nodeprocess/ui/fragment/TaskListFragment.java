package com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment;


import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.utility.BackGroundUtils;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.ScreenUtil;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
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
public class TaskListFragment extends BaseConstructionFragment implements ProjectListContract.View, ProjectListAdapter.ProjectListItemListener {

    private RecyclerView mProjectListView;
    private ProjectListAdapter mProjectListAdapter;
    private ProjectListContract.Presenter mProjectListPresenter;
    private PopupWindow mScreenPopup;

    public static TaskListFragment newInstance() {
        return new TaskListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_task_list_view;
    }


    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void initData() {
        mProjectListPresenter = new ProjectListPresenter(getActivity(), this);
        //refresh ProjectLists
        String defaultSelectedDate = DateUtil.getStringDateByFormat(Calendar.getInstance().getTime(), "yyyy-MM-dd");
        mProjectListPresenter.initFilterRequestParams(defaultSelectedDate, null, null);
        mProjectListPresenter.refreshProjectList();
    }

    @Override
    protected void initView() {
        mProjectListView = (RecyclerView) rootView.findViewById(R.id.rcy_task_list);
        //init recyclerView
        mProjectListView.setLayoutManager(new LinearLayoutManager(mContext));
        mProjectListView.setHasFixedSize(true);
        mProjectListView.setItemAnimator(new DefaultItemAnimator());
        //init recyclerView adapter
        mProjectListAdapter = new ProjectListAdapter(new ArrayList<ProjectInfo>(0), R.layout.listitem_task_list_view, activity, this);
        mProjectListView.setAdapter(mProjectListAdapter);
        //set this fragment to hold optionsMenu
        setHasOptionsMenu(true);
    }

    // TODO: 10/25/16 模拟上拉刷新监听
    private void onRefresh() {
        mProjectListPresenter.refreshProjectList();
    }

    // TODO: 10/25/16 模拟下拉加载更多监听
    private void onLoadMore() {
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
            mProjectListAdapter.setProjectLists(projectList);
        }
    }

    @Override
    public void addMoreProjectListView(List<ProjectInfo> projectList) {
        if (projectList != null && projectList.size() > 0) {
            mProjectListAdapter.appendProjectLists(projectList);
        }
    }

    @Override
    public void onProjectClick(List<ProjectInfo> projectList, int position) {
        mProjectListPresenter.navigateToProjectDetail(projectList, position);
    }

    @Override
    public void onTaskClick(List<Task> taskLists, int position) {
        mProjectListPresenter.navigateToTaskDetail(taskLists, position);
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

        int itemId = item.getItemId();
        if (itemId == R.id.home_toolbar_search) {
            ToastUtils.showShort(mContext, "search");
            // TODO: 10/25/16 跳转到搜索页面

        } else if (itemId == R.id.home_toolbar_screen) {
            initScreenPopupWin();
        }

        return super.onOptionsItemSelected(item);
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
        } else {
            mScreenAll.setTextColor(ContextCompat.getColor(mContext, R.color.con_blue));
            mScreenLike.setTextColor(ContextCompat.getColor(mContext, R.color.font_gray));
        }
        //listener
        mScreenAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScreenAll.setTextColor(ContextCompat.getColor(mContext, R.color.con_blue));
                mScreenLike.setTextColor(ContextCompat.getColor(mContext, R.color.font_gray));
                mProjectListPresenter.initFilterRequestParams(null, null, null);
                mProjectListPresenter.onFilterLikeChange(null);
                mScreenPopup.dismiss();
            }
        });
        mScreenLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScreenLike.setTextColor(ContextCompat.getColor(mContext, R.color.con_blue));
                mScreenAll.setTextColor(ContextCompat.getColor(mContext, R.color.font_gray));
                mProjectListPresenter.initFilterRequestParams(null, null, null);
                mProjectListPresenter.onFilterLikeChange("true");
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
