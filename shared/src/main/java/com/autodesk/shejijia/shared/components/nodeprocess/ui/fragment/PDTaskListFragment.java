package com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.framework.fragment.BaseConstructionFragment;

/**
 * Created by t_xuz on 11/14/16.
 * 项目详情页面下的task列表
 */

public class PDTaskListFragment extends BaseConstructionFragment {

    private RecyclerView mTaskListView;

    public static PDTaskListFragment newInstance(){
        return new PDTaskListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_task_list_view;
    }

    @Override
    protected void initView() {
        mTaskListView = (RecyclerView) rootView.findViewById(R.id.rcy_task_list);
    }

    @Override
    protected void initData() {

    }
}
