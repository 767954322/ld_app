package com.autodesk.shejijia.shared.components.form.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.utility.DividerItemDecoration;
import com.autodesk.shejijia.shared.components.form.common.constant.SHFormConstant;
import com.autodesk.shejijia.shared.components.form.common.entity.ItemCell;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHInspectionForm;
import com.autodesk.shejijia.shared.components.form.contract.FormSubListContract;
import com.autodesk.shejijia.shared.components.form.presenter.FormSubListPresenter;
import com.autodesk.shejijia.shared.components.form.ui.activity.FormActivity;
import com.autodesk.shejijia.shared.components.form.ui.adapter.FormListAdapter;
import com.autodesk.shejijia.shared.framework.fragment.BaseConstructionFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_aij on 16/11/25.
 */

public class FormSubListFragment extends BaseConstructionFragment implements FormSubListContract.View {

    private RecyclerView mRecyclerView;
    private FormListAdapter mAdapter;
    private SHInspectionForm mShInspectionForm;
    private List<ItemCell> mItemCells = new ArrayList<>();   //adapter显示的类,需要大小
    private FormActivity mActivity;
    private FormSubListPresenter mPresenter;

    public static FormSubListFragment newInstance(Bundle args) {
        FormSubListFragment formSubListFragment = new FormSubListFragment();
        formSubListFragment.setArguments(args);

        return formSubListFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (FormActivity) context;
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
        mShInspectionForm = (SHInspectionForm) getArguments().getSerializable("shInspectionForm");
        mActivity.initToolbar(mShInspectionForm.getTitle());

        mPresenter = new FormSubListPresenter(this);
        mItemCells.addAll(mPresenter.getItemCells(mShInspectionForm.getCheckItems()));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new FormListAdapter(mContext, mItemCells);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void initListener() {

        mAdapter.setOnItemClickListener(new FormListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle args = new Bundle();
                args.putString("category", mItemCells.get(position).getTitle());  //标题
                args.putSerializable("shInspectionForm", mShInspectionForm);  //具体的条目
                ItemListFragment mItemListFragment = ItemListFragment.newInstance(args);    //传递具体的条目标题

                mContext.getSupportFragmentManager().beginTransaction()
                        .hide(mContext.getSupportFragmentManager().findFragmentByTag(SHFormConstant.FragmentTag.FORM_SUBLIST_FRAGMENT))
                        .add(R.id.fl_main_container, mItemListFragment, SHFormConstant.FragmentTag.ITEM_LIST_FRAGMENT)
                        .addToBackStack(null)
                        .commit();
            }

        });


    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
//            LogUtils.d("asdf","SubListFragment是" + hidden);
            mAdapter.notifyDataSetChanged();
            mActivity.initToolbar(mShInspectionForm.getTitle());
        }
    }

}
