package com.autodesk.shejijia.shared.components.issue.presenter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

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
        if (TextUtils.isEmpty(tag) || ConstructionConstants.IssueTracking.ENTERPRISE_ALL_TAG.equals(tag)) {//总项目
            mIssueListData = new String[]{"已过期", "今天到期", "我要跟进的问题", "客户反馈", "所有项目问题", "已跟进"};
        } else if (ConstructionConstants.IssueTracking.CONSEMER_TAG.equals(tag)) {//消费者
            mIssueListData = new String[]{"消费者1", "消费者2", "消费者3"};
        } else if (ConstructionConstants.IssueTracking.ENTERPRISE_ONE_TAG.equals(tag)) {//单项目
            mIssueListData = new String[]{"已过期", "今天到期", "我要跟进的问题", "客户反馈", "其他待处理问题", "已跟进"};
        }
        mView.getListData(mIssueListData);

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

        mView.getIssueNum();

    }

    public String[] getmIssueListData() {
        return mIssueListData;
    }

}
