package com.autodesk.shejijia.shared.components.issue.ui.fragment;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.uielements.swiperecyclerview.RefreshLoadMoreListener;
import com.autodesk.shejijia.shared.components.common.uielements.swiperecyclerview.SwipeRecyclerView;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.issue.contract.IssueListContract;
import com.autodesk.shejijia.shared.components.issue.presenter.IssueListPresent;
import com.autodesk.shejijia.shared.components.issue.ui.activity.AddIssueListActivity;
import com.autodesk.shejijia.shared.components.issue.ui.adapter.IssueListAdapter;
import com.autodesk.shejijia.shared.framework.fragment.BaseConstructionFragment;

/**
 * Created by t_xuz on 8/25/16.
 */
public class IssueListFragment extends BaseConstructionFragment implements IssueListContract.View, IssueListAdapter.IssueListItemListener, RefreshLoadMoreListener, View.OnClickListener {

    private IssueListAdapter mIssueListAdapter;
    private IssueListPresent mIssueListPresent;
    private SwipeRecyclerView mIssueListView;
    private RelativeLayout mAddIssueTracking;
    private String[] mIssueListData;
    private String issue_tracking_type;
    private TextView mEmptyView;

    public static IssueListFragment newInstance() {
        return new IssueListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_issue_list_view;
    }

    @Override
    protected void initView() {

        mIssueListView = (SwipeRecyclerView) rootView.findViewById(R.id.rcy_issue_tracking_list);
        mAddIssueTracking = (RelativeLayout) rootView.findViewById(R.id.rl_add_issuetracking);
        mEmptyView = (TextView) rootView.findViewById(R.id.tv_empty_message);

    }

    @Override
    protected void initData() {
        /**
         * public static final String ENTERPRISE_ALL_TAG = "enterprise_all_tag";施工总问题
         * public static final String ENTERPRISE_ONE_TAG = "enterprise_one_tag";施工单个项目的问题
         * public static final String CONSEMER_TAG = "consumer_tag";消费者的问题
         */
        issue_tracking_type = activity.getIntent().getStringExtra(ConstructionConstants.IssueTracking.BUNDLE_KEY_ISSUE_LIST_TYPE);
        //初始化IssueListPresent
        mIssueListPresent = new IssueListPresent(getActivity(), mContext.getSupportFragmentManager(), this);
        mIssueListPresent.setIssueListStyle(issue_tracking_type);
        //获取列表问题数量（目前是三种情况）
        mIssueListPresent.getIssueNumber();

    }

    @Override
    protected void initListener() {
        super.initListener();

        mIssueListView.setRefreshLoadMoreListener(this);
        //让其自动刷新一下，会回调onRefresh()方法一次
        mIssueListView.setRefreshing(true);
        mAddIssueTracking.setOnClickListener(this);

    }

    //初始化recyclerView
    private void initList() {
        //添加问题模块BUTON，默认隐藏，只有单项目可以添加
        if (!TextUtils.isEmpty(issue_tracking_type) && ConstructionConstants.IssueTracking.ISSUE_LIST_TYPE_SINGLE_PROJECT.equals(issue_tracking_type)) {
            mAddIssueTracking.setVisibility(View.VISIBLE);
        } else {
            mAddIssueTracking.setVisibility(View.GONE);
        }
        mIssueListView.getRecyclerView().setLayoutManager(new LinearLayoutManager(mContext));
        mIssueListView.getRecyclerView().setHasFixedSize(true);
        mIssueListView.getRecyclerView().setItemAnimator(new DefaultItemAnimator());
        mIssueListView.getSwipeRefreshLayout()
                .setColorSchemeColors(ContextCompat.getColor(mContext, R.color.colorPrimary));
        //init recyclerView adapter
        mIssueListAdapter = new IssueListAdapter(mIssueListData, getActivity(), R.layout.listitem_issuetracking_list_view, this);
        mIssueListView.setAdapter(mIssueListAdapter);
        //set this fragment to hold optionsMenu
        setHasOptionsMenu(true);
    }

    //ReclerView 刷新监听
    @Override
    public void onRefresh() {

        mIssueListPresent.refreshIssueTracking();

    }

    //ReclerView 加载监听
    @Override
    public void onLoadMore() {

    }

    //刷新问题列表
    @Override
    public void onRefreshIssueTracking() {

        mIssueListView.complete();

    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.rl_add_issuetracking) {
            Intent addIssueTracking_Intent = new Intent(mContext, AddIssueListActivity.class);
            startActivity(addIssueTracking_Intent);
        } else {
        }
    }

    //获取列表数据样式
    @Override
    public void onIssueListStyle(String[] mIssueListData) {
        this.mIssueListData = mIssueListData;
    }

    //各个列表返回数量
    @Override
    public void onIssueListNum() {
        initList();
    }

    //问题追踪列表Item点击监听
    @Override
    public void onIssueListClick(int item_position) {

        ToastUtils.showLong(activity, "查看详情");

    }

}
