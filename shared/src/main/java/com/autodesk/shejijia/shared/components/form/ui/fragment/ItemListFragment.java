package com.autodesk.shejijia.shared.components.form.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.utility.DividerItemDecoration;
import com.autodesk.shejijia.shared.components.form.common.entity.ItemCell;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHInspectionForm;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.CheckItem;
import com.autodesk.shejijia.shared.components.form.ui.adapter.FormListAdapter;
import com.autodesk.shejijia.shared.framework.fragment.BaseConstructionFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_aij on 16/11/25.
 */

public class ItemListFragment extends BaseConstructionFragment {

    private RecyclerView mRecyclerView;

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
        List<ItemCell> list = new ArrayList<>();

        String title = getArguments().getString("category");
        SHInspectionForm shInspectionForm = (SHInspectionForm) getArguments().getSerializable("shInspectionForm");
        for (CheckItem checkItem : shInspectionForm.getCheckItems()) {
            if(title.equals(checkItem.getCategory())) {
//                list.add(checkItem.getTitle());
                ItemCell itemCell = new ItemCell();
                itemCell.setTitle(checkItem.getTitle());
                list.add(itemCell);
            }
        }


        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(new FormListAdapter(mContext, list));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden) {
            // TODO: 16/11/28 如何该界面显示
//            LogUtils.d("asdf","itemListFragment是" + hidden);
        }
    }
}
