package com.autodesk.shejijia.shared.components.issue.presenter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.uielements.swiperecyclerview.SwipeRecyclerView;
import com.autodesk.shejijia.shared.components.issue.contract.IssueListContract;

/**
 * Created by Menghao.Gu on 2016/12/1.
 */

public class IssueListPresent implements IssueListContract.Presenter {

    private Context mContext;
    private FragmentManager fragmentManager;
    private SwipeRecyclerView mIssueListView;
    private IssueListContract.View mView;
    private String[] mIssueListData;
    private String mIssueTruckingType;

    public IssueListPresent(Context mContext, FragmentManager fragmentManager, IssueListContract.View mView) {
        this.mContext = mContext;
        this.fragmentManager = fragmentManager;
        this.mView = mView;
    }

    @Override
    public void setIssueListStyle(String tag) {

        mIssueTruckingType = tag;
        if (TextUtils.isEmpty(tag) || ConstructionConstants.IssueTracking.ISSUE_LIST_TYPE_ALL_PROJECTS.equals(tag)) {//总项目
            mIssueListData = mContext.getResources().getStringArray(R.array.issue_list_type_all_project);
        } else if (ConstructionConstants.IssueTracking.ISSUE_LIST_TYPE_CONSUMER.equals(tag)) {//消费者
            mIssueListData = mContext.getResources().getStringArray(R.array.issue_list_type_consumer);
        } else if (ConstructionConstants.IssueTracking.ISSUE_LIST_TYPE_SINGLE_PROJECT.equals(tag)) {//单项目
            mIssueListData = mContext.getResources().getStringArray(R.array.issue_list_type_single_project);
        }
        mView.onIssueListStyle(mIssueListData);

    }

    @Override
    public void initFilterRequestParams() {

    }

    @Override
    public void refreshIssueTracking() {

        mView.onRefreshIssueTracking();

    }

    @Override
    public void getIssueNumber() {

        mView.onIssueListNum();

    }

    public String[] getmIssueListData() {
        return mIssueListData;
    }

}
