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
    private LinearLayoutManager mLayoutManager;

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

        mPresenter = new ItemListPresenter(this);
        mOptionCellList.addAll(mPresenter.getOptionCells(mCategory, inspectionForm));
        initToolbar(mCategory);

        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new ItemListAdapter(mOptionCellList,this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initToolbar(mCategory);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLeftItemClick(View view, int position) {
//        LogUtils.d("asdf","左边 current position:" + (mIndex + position));
        mPresenter.setCheckIndex(mCheckItemList.get(mIndex + position),2);
//        refresh(position);
    }

    @Override
    public void onCenterItemClick(View view, int position) {
//        LogUtils.d("asdf","中间 current position:" + (mIndex + position));
        mPresenter.setCheckIndex(mCheckItemList.get(mIndex + position),1);
//        refresh(position);
    }

    @Override
    public void onRightItemClick(View view, int position) {
//        LogUtils.d("asdf","右边 current position:" + (mIndex + position));
        mPresenter.setCheckIndex(mCheckItemList.get(mIndex + position),0);
//        refresh(position);
    }

    private void initToolbar(String category) {
        ActionBar actionBar = mContext.getSupportActionBar();
        actionBar.setTitle(category);
    }

    private void refresh(int position) {
        int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
        int lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
        mAdapter.notifyItemRangeChanged(0,firstVisibleItemPosition-0);



//        if(position < firstVisibleItemPosition) {
//            if(firstVisibleItemPosition - position > 0) {
//                mAdapter.notifyItemRangeChanged(0,position-0);
//            }
//        } else if(position > lastVisibleItemPosition) {
//            if(position - lastVisibleItemPosition > 0) {
//                mAdapter.notifyItemRangeChanged();
//            }
//        }
    }

}
