package com.autodesk.shejijia.shared.components.form.presenter;

import com.autodesk.shejijia.shared.components.form.common.entity.ItemCell;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.CheckItem;
import com.autodesk.shejijia.shared.components.form.contract.ItemListContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_aij on 16/11/30.
 */

public class ItemListPresenter implements ItemListContract.Presenter {
    private ItemListContract.View mView;

    public ItemListPresenter(ItemListContract.View view) {
        mView = view;
    }


    @Override
    public List<ItemCell> getItemCells(String title, ArrayList<CheckItem> checkItems) {
        List<ItemCell> itemCellList = new ArrayList<>();

        for (CheckItem checkItem : checkItems) {
            if (title.equals(checkItem.getCategory())) {
                ItemCell itemCell = new ItemCell();
                itemCell.setTitle(checkItem.getTitle());
                itemCellList.add(itemCell);
            }
        }

        return itemCellList;
    }
}
