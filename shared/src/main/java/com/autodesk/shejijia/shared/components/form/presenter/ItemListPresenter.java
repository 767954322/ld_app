package com.autodesk.shejijia.shared.components.form.presenter;

import com.autodesk.shejijia.shared.components.form.common.entity.OptionCell;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHInspectionForm;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.CheckItem;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.FormFeedBack;
import com.autodesk.shejijia.shared.components.form.contract.ItemListContract;

import java.util.ArrayList;
import java.util.HashMap;
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
    public List<OptionCell> getOptionCells(String category, SHInspectionForm shInspectionForm) {
        ArrayList<CheckItem> checkItems = shInspectionForm.getCheckItems();
        HashMap map = shInspectionForm.getTypeDict();
        List<OptionCell> itemCellList = new ArrayList<>();

        for (CheckItem checkItem : checkItems) {

            if (category.equals(checkItem.getCategory())) {
                itemCellList.add(initOptionCell(checkItem, map));
            }

        }

        return itemCellList;
    }

    @Override
    public void setCheckIndex(CheckItem checkItem, int type) {
        FormFeedBack formFeedBack = checkItem.getFormFeedBack();
        formFeedBack.setCurrentCheckIndex(type);
    }

    private OptionCell initOptionCell(CheckItem checkItem, HashMap map) {
        OptionCell optionCell = new OptionCell();
        optionCell.setTitle(checkItem.getTitle());
        FormFeedBack formFeedBack = checkItem.getFormFeedBack();
        //根据map的索引获取其中的文字
        optionCell.setCheckResult(formFeedBack.getCurrentCheckIndex());

        if (map.containsKey(checkItem.getActionType())) {
            Object o = map.get(checkItem.getActionType());
            if (o instanceof List) {
                optionCell.setActionResult(((List<String>) o).get(formFeedBack.getCurrentActionIndex()));
            }
        }

        optionCell.setShowStandard(true);
        optionCell.setStandard(checkItem.getStandard());

        HashMap<String, List<String>> typeDict = new HashMap<>();
        String itemTypeDict = checkItem.getCheckType();

        if (map.containsKey(itemTypeDict)) {
            Object o = map.get(itemTypeDict);
            if (o instanceof List) {
                typeDict.put(itemTypeDict, (List<String>) o);
            }
            optionCell.setTypeDict(typeDict);
        }
        return optionCell;
    }
}
