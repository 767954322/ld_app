package com.autodesk.shejijia.shared.components.form.presenter;

import com.autodesk.shejijia.shared.components.form.common.entity.OptionCell;
import com.autodesk.shejijia.shared.components.form.common.entity.SHForm;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.CheckItem;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.FormFeedBack;
import com.autodesk.shejijia.shared.components.form.contract.MutableItemContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by t_aij on 16/12/15.
 */

public class MutableItemsPresenter implements MutableItemContract.Presenter {
    private MutableItemContract.View mView;                         //引用
    private ArrayList<SHForm> mFormList;                         //引用,同一处
    private LinkedHashMap<String, List<String>> mLinkedHashMap;  //引用,同一处
    private List<OptionCell> mOptionCellList = new ArrayList<>();  //展现的条目
    private List<CheckItem> mCheckItemList = new ArrayList<>();  //所以条目的引用

    public MutableItemsPresenter(MutableItemContract.View view, ArrayList<SHForm> formList, LinkedHashMap<String, List<String>> linkedHashMap) {
        mView = view;
        mFormList = formList;
        mLinkedHashMap = linkedHashMap;
        initOptionList();
    }

    @Override
    public void setCheckItemIndex(int type, int position) {
        CheckItem checkItem = mCheckItemList.get(position);
        FormFeedBack formFeedBack = checkItem.getFormFeedBack();
        formFeedBack.setCurrentCheckIndex(type);

        refreshItemData(checkItem, type);
    }

    private void initOptionList() {
        for (int i = 0; i < mFormList.size(); i++) {
            SHForm form = mFormList.get(i);
            HashMap map = form.getTypeDict();
            if(mLinkedHashMap.containsKey(form.getFormTemplateId())) {
                ArrayList<String> list = (ArrayList<String>) mLinkedHashMap.get(form.getFormTemplateId());
                if(list != null && list.size() != 0) {
                    ArrayList<CheckItem> checkItems = form.getCheckItems();
                    for (int i1 = 0; i1 < checkItems.size(); i1++) {
                        CheckItem checkItem = checkItems.get(i1);

                        if(list.contains(checkItem.getItemId())) {
                            mOptionCellList.add(initItemCell(checkItem,map));
                            mCheckItemList.add(checkItem);
                        }
                    }
                }

            }

        }

        mView.initOptionCellList(mOptionCellList);

    }

    private OptionCell initItemCell(CheckItem checkItem,HashMap map) {
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
        for (OptionCell optionCell : mOptionCellList) {
            if (optionCell.getTitle().equals(checkItem.getTitle())) {
                optionCell.setCheckResult(type);
                break;
            }
        }

    }

}
