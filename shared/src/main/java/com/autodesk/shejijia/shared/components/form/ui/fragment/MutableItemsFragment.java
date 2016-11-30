package com.autodesk.shejijia.shared.components.form.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.form.common.entity.OptionCell;
import com.autodesk.shejijia.shared.components.form.common.entity.SHForm;
import com.autodesk.shejijia.shared.components.form.contract.MutableItemContract;
import com.autodesk.shejijia.shared.components.form.presenter.MutableItemsPresenter;
import com.autodesk.shejijia.shared.components.form.ui.adapter.ItemListAdapter;
import com.autodesk.shejijia.shared.framework.fragment.BaseConstructionFragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by t_aij on 16/12/15.
 */

public class MutableItemsFragment extends BaseConstructionFragment implements MutableItemContract.View, ItemListAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private ArrayList<SHForm> mFormList;
    private LinkedHashMap<String, List<String>> mLinkedHashMap;
    private ItemListAdapter mAdapter;
    private List<OptionCell> mOptionCellList = new ArrayList<>();
    private MutableItemsPresenter mPresenter;

    public static MutableItemsFragment newInstance(Bundle bundle) {
        MutableItemsFragment fragment = new MutableItemsFragment();
        fragment.setArguments(bundle);
        return fragment;
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
        Bundle bundle = getArguments();
        if (bundle != null) {
            mFormList = (ArrayList<SHForm>) bundle.getSerializable("formList");
            mLinkedHashMap = (LinkedHashMap<String, List<String>>) bundle.getSerializable("linkedHashMap");
        }
        initToolbar();
        mPresenter = new MutableItemsPresenter(this,mFormList,mLinkedHashMap);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        if(mAdapter == null) {
            mAdapter = new ItemListAdapter(mOptionCellList, this);
        }
        mRecyclerView.setAdapter(mAdapter);

    }

    private void initToolbar() {
        mContext.getSupportActionBar().setTitle(UIUtils.getString(R.string.form_reinspection_log));
    }

    @Override
    public void initOptionCellList(List<OptionCell> optionCellList) {
        mOptionCellList.clear();
        mOptionCellList.addAll(optionCellList);
        if (mAdapter == null) {
            mAdapter = new ItemListAdapter(mOptionCellList, this);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLeftItemClick(int type, int position) {
        mPresenter.setCheckItemIndex(type,position);
    }

    @Override
    public void onCenterItemClick(int type, int position) {
        mPresenter.setCheckItemIndex(type,position);
    }

    @Override
    public void onRightItemClick(int type, int position) {
        mPresenter.setCheckItemIndex(type,position);
    }
}
