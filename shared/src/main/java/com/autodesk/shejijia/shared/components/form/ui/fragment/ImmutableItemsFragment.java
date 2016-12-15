package com.autodesk.shejijia.shared.components.form.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.utility.DividerItemDecoration;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.form.common.entity.ItemCell;
import com.autodesk.shejijia.shared.components.form.common.entity.SHForm;
import com.autodesk.shejijia.shared.components.form.contract.ImmutableItemsContract;
import com.autodesk.shejijia.shared.components.form.presenter.ImmutableItemsPresenter;
import com.autodesk.shejijia.shared.components.form.ui.adapter.FormListAdapter;
import com.autodesk.shejijia.shared.framework.fragment.BaseConstructionFragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by t_aij on 16/12/15.
 */

public class ImmutableItemsFragment extends BaseConstructionFragment implements FormListAdapter.OnItemClickListener, ImmutableItemsContract.View {
    private RecyclerView mRecyclerView;
    private FormListAdapter mAdapter;
    private List<ItemCell> mItemCellList = new ArrayList<>();
    private ArrayList<SHForm> mFormList;
    private LinkedHashMap<String, List<String>> mLinkedHashMap;
    private ImmutableItemsPresenter mPresenter;

    public static ImmutableItemsFragment newInstance(Bundle bundle) {
        ImmutableItemsFragment fragment = new ImmutableItemsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_form_sub_list;
    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if(bundle != null) {
            mFormList = (ArrayList<SHForm>) bundle.getSerializable("formList");
            mLinkedHashMap = (LinkedHashMap<String, List<String>>) bundle.getSerializable("linkedHashMap");
        }
        initToolbar();
        mPresenter = new ImmutableItemsPresenter(this,mFormList,mLinkedHashMap);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
        if(mAdapter == null) {
            mAdapter = new FormListAdapter(mItemCellList,this);
        }
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initToolbar() {
        mContext.getSupportActionBar().setTitle(UIUtils.getString(R.string.form_rectification_log));
    }

    @Override
    public void onItemClick(View view, int position) {
        ToastUtils.showShort(mContext,"当前位置:" + position);
    }

    @Override
    public void initItemCellList(List<ItemCell> itemCellList) {
        mItemCellList.clear();
        mItemCellList.addAll(itemCellList);
        if (mAdapter == null) {
            mAdapter = new FormListAdapter(mItemCellList, this);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }
}
