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
    private List<OptionCell> mItemCellList = new ArrayList<>();

    public ItemListPresenter(ItemListContract.View view,String category,SHInspectionForm inspectionForm) {
        mView = view;
        initOptionCellList(category,inspectionForm);
    }

    @Override
    public void setCheckIndex(CheckItem checkItem, int type) {
        FormFeedBack formFeedBack = checkItem.getFormFeedBack();
        formFeedBack.setCurrentCheckIndex(type);

        refreshItemData(checkItem,type);
    }

    private void initOptionCellList(String category, SHInspectionForm shInspectionForm) {
        ArrayList<CheckItem> checkItems = shInspectionForm.getCheckItems();
        HashMap map = shInspectionForm.getTypeDict();

        for (CheckItem checkItem : checkItems) {

            if (category.equals(checkItem.getCategory())) {
                mItemCellList.add(initOptionCell(checkItem, map));
            }

        }
        mView.initOptionCellList(mItemCellList);
    }

    private OptionCell initOptionCell(CheckItem checkItem, HashMap map) {
        OptionCell optionCell = new OptionCell();
        FormFeedBack formFeedBack = checkItem.getFormFeedBack();

        optionCell.setTitle(checkItem.getTitle());
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

    private void refreshItemData(CheckItem checkItem, int type) {
        for (OptionCell optionCell : mItemCellList) {
            if(optionCell.getTitle().equals(checkItem.getTitle())) {
                optionCell.setCheckResult(type);
                break;
            }
        }

    }
}
