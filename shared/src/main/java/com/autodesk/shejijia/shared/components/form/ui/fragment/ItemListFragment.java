package com.autodesk.shejijia.shared.components.form.ui.fragment;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.form.common.entity.OptionCell;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHInspectionForm;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.CheckItem;
import com.autodesk.shejijia.shared.components.form.contract.ItemListContract;
import com.autodesk.shejijia.shared.components.form.presenter.ItemListPresenter;
import com.autodesk.shejijia.shared.components.form.ui.adapter.ItemListAdapter;
import com.autodesk.shejijia.shared.framework.fragment.BaseConstructionFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_aij on 16/11/25.
 */

public class ItemListFragment extends BaseConstructionFragment implements ItemListContract.View, ItemListAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;

    private String mCategory;
    private ItemListPresenter mPresenter;
    private List<OptionCell> mOptionCellList = new ArrayList<>();
    private ItemListAdapter mAdapter;
    private int mIndex;
    private ArrayList<CheckItem> mCheckItemList;

    public static ItemListFragment newInstance(Bundle args) {
        ItemListFragment itemListFragment = new ItemListFragment();
        itemListFragment.setArguments(args);
        return itemListFragment;
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
        mIndex = getArguments().getInt("index");
        SHInspectionForm inspectionForm = (SHInspectionForm) getArguments().getSerializable("shInspectionForm");
        mCheckItemList = inspectionForm.getCheckItems();

        mPresenter = new ItemListPresenter(this,mCategory,inspectionForm);
        initToolbar(mCategory);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        if (mAdapter == null) {
            mAdapter = new ItemListAdapter(mOptionCellList,this);
        }
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            // TODO: 16/12/7 显示错误界面的fragment的时候需要调用
            initToolbar(mCategory);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLeftItemClick(View view, int position) {
        mPresenter.setCheckIndex(mCheckItemList.get(mIndex + position),2);
    }

    @Override
    public void onCenterItemClick(View view, int position) {
        mPresenter.setCheckIndex(mCheckItemList.get(mIndex + position),1);
    }

    @Override
    public void onRightItemClick(View view, int position) {
        mPresenter.setCheckIndex(mCheckItemList.get(mIndex + position),0);
    }

    @Override
    public void initOptionCellList(List<OptionCell> itemCellList) {
        mOptionCellList.clear();
        mOptionCellList.addAll(itemCellList);

        if (mAdapter == null) {
            mAdapter = new ItemListAdapter(mOptionCellList,this);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private void initToolbar(String category) {
        ActionBar actionBar = mContext.getSupportActionBar();
        actionBar.setTitle(category);
    }
}
