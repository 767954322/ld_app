package com.autodesk.shejijia.enterprise.nodeprocess.projectlists.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.enterprise.base.fragments.BaseFragment;

/**
 * Created by t_xuz on 8/25/16.
 *
 */
public class IssueListFragment extends BaseFragment{

    private RecyclerView mIssueListView;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_issue_list_view;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mIssueListView = (RecyclerView)mContext.findViewById(R.id.rcy_issue_list);
    }

    @Override
    protected void initEvents() {

    }
}
