package com.autodesk.shejijia.shared.components.form.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.utility.DividerItemDecoration;
import com.autodesk.shejijia.shared.components.form.common.entity.ItemCell;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHInspectionForm;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.CheckItem;
import com.autodesk.shejijia.shared.components.form.ui.adapter.FormListAdapter;
import com.autodesk.shejijia.shared.framework.fragment.BaseConstructionFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by t_aij on 16/11/25.
 */

public class FormSubListFragment extends BaseConstructionFragment {

    private RecyclerView mRecyclerView;
    private FormListAdapter mAdapter;
    private List<CheckItem> mCheckItems = new ArrayList<>();
    private ItemListFragment mItemListFragment;
    private SHInspectionForm mShInspectionForm;
    private List<String> mCategoryList;
    private List<ItemCell> mItemCells = new ArrayList<>();

    public static FormSubListFragment newInstance(Bundle args) {
        FormSubListFragment formSubListFragment = new FormSubListFragment();
        formSubListFragment.setArguments(args);

        return formSubListFragment;
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
        mShInspectionForm = (SHInspectionForm) getArguments().getSerializable("shInspectionForm");
        mCheckItems = mShInspectionForm.getCheckItems();   //根据具体的表格,获取到每一个条目

        mCategoryList = new ArrayList<>();
        HashMap<String,Integer> hashMap = new HashMap<>();

        for (CheckItem checkItem : mCheckItems) {
            String category = checkItem.getCategory();

            if(hashMap.containsKey(category)) {
                hashMap.put(category, hashMap.get(category)+1); //统计类别和个数
//                LogUtils.d("asdf",hashMap.toString());
            } else {
                mCategoryList.add(category);  //将表格中的种类名称提取出来
                hashMap.put(category,1);
            }
        }

        for (int i = 0; i < mCategoryList.size(); i++) {
            ItemCell itemCell = new ItemCell();
            itemCell.setTitle(mCategoryList.get(i));
            itemCell.setResult("/" + hashMap.get(mCategoryList.get(i)));
            mItemCells.add(itemCell);
        }

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
                args.putString("category",mCategoryList.get(position));                //标题
                args.putSerializable("shInspectionForm",mShInspectionForm);  //具体的条目
                mItemListFragment = ItemListFragment.newInstance(args);    //传递具体的条目标题

                mContext.getSupportFragmentManager().beginTransaction()
                        .hide(mContext.getSupportFragmentManager().findFragmentByTag("formSubListFragment"))
                        .add(R.id.fl_main_container, mItemListFragment, "ItemListFragment")
                        .addToBackStack(null)
                        .commit();
            }

        });


    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden) {
//            LogUtils.d("asdf","SubListFragment是" + hidden);
            mAdapter.notifyDataSetChanged();
        }
    }
}
