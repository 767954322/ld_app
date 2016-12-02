package com.autodesk.shejijia.shared.components.form.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.form.common.entity.OptionCell;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHInspectionForm;
import com.autodesk.shejijia.shared.components.form.contract.ItemListContract;
import com.autodesk.shejijia.shared.components.form.presenter.ItemListPresenter;
import com.autodesk.shejijia.shared.components.form.ui.activity.FormActivity;
import com.autodesk.shejijia.shared.components.form.ui.adapter.ItemListAdapter;
import com.autodesk.shejijia.shared.framework.fragment.BaseConstructionFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_aij on 16/11/25.
 */

public class ItemListFragment extends BaseConstructionFragment implements ItemListContract.View {

    private RecyclerView mRecyclerView;
    private FormActivity mActivity;
    private String mCategory;
    private ItemListPresenter mPresenter;
    private List<OptionCell> mOptionCellList = new ArrayList<>();
    private ItemListAdapter mAdapter;

    public static ItemListFragment newInstance(Bundle args) {
        ItemListFragment itemListFragment = new ItemListFragment();
        itemListFragment.setArguments(args);
        return itemListFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (FormActivity) context;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_form_item_list;
    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
    }

    @Override
    protected void initData() {
        mCategory = getArguments().getString("category");
        SHInspectionForm shInspectionForm = (SHInspectionForm) getArguments().getSerializable("shInspectionForm"); //具体的表格

        mPresenter = new ItemListPresenter(this);
        mOptionCellList.addAll(mPresenter.getOptionCells(mCategory,shInspectionForm));
        mActivity.initToolbar(mCategory);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new ItemListAdapter(mContext, mOptionCellList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mActivity.initToolbar(mCategory);
            mAdapter.notifyDataSetChanged();
        }
    }
}
