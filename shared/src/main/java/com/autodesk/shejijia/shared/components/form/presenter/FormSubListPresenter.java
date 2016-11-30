package com.autodesk.shejijia.shared.components.form.presenter;

import com.autodesk.shejijia.shared.components.form.common.entity.ItemCell;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.CheckItem;
import com.autodesk.shejijia.shared.components.form.contract.FormSubListContract;
import com.autodesk.shejijia.shared.components.form.contract.FormSubListContract.Presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by t_aij on 16/11/29.
 */

public class FormSubListPresenter implements Presenter {
    private FormSubListContract.View mView;

    public FormSubListPresenter(FormSubListContract.View view) {
        mView = view;
    }


    @Override
    public List<ItemCell> getItemCells(List<CheckItem> checkItemList) {
        List<ItemCell> itemCellList = new ArrayList<>();
        List<String> categoryList = new ArrayList<>();
        HashMap<String, Integer> hashMap = new HashMap<>();

        for (int i = 0; i < checkItemList.size(); i++) {  //计算出条目的种类以及个数
            CheckItem checkItem = checkItemList.get(i);
            String category = checkItem.getCategory();

            if (hashMap.containsKey(category)) {
                hashMap.put(category, hashMap.get(category) + 1); //统计类别和个数
            } else {
                hashMap.put(category, 1);
                categoryList.add(category);  //将表格中的种类名称提取出来
            }

        }

        for (int i = 0; i < categoryList.size(); i++) {
            ItemCell itemCell = new ItemCell();
            itemCell.setTitle(categoryList.get(i));
            itemCell.setResult("/" + hashMap.get(categoryList.get(i)));
            itemCellList.add(itemCell);
        }

        return itemCellList;
    }
}
