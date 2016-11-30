package com.autodesk.shejijia.shared.components.form.presenter;

import com.autodesk.shejijia.shared.components.form.common.entity.ItemCell;
import com.autodesk.shejijia.shared.components.form.common.entity.SHForm;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.CheckItem;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.FormFeedBack;
import com.autodesk.shejijia.shared.components.form.contract.ImmutableItemsContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by t_aij on 16/12/15.
 */

public class ImmutableItemsPresenter implements ImmutableItemsContract.Presenter {
    private ImmutableItemsContract.View mView;
    private ArrayList<SHForm> mFormList;                         //引用,同一处
    private LinkedHashMap<String, List<String>> mLinkedHashMap;  //引用,同一处
    private List<ItemCell> mItemCellList = new ArrayList<>();     //展现的条目

    public ImmutableItemsPresenter(ImmutableItemsContract.View view, ArrayList<SHForm> formList, LinkedHashMap<String, List<String>> linkedHashMap) {
        mView = view;
        mFormList = formList;
        mLinkedHashMap = linkedHashMap;
        initItemList();
    }

    private void initItemList() {
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
                            mItemCellList.add(initItemCell(checkItem,map));
                        }
                    }
                }

            }

        }

        mView.initItemCellList(mItemCellList);
    }

    private ItemCell initItemCell(CheckItem checkItem, HashMap map) {
        ItemCell itemCell = new ItemCell();
        itemCell.setTitle(checkItem.getTitle());

        FormFeedBack formFeedBack = checkItem.getFormFeedBack();
        String checkType = checkItem.getCheckType();
        if(map.containsKey(checkType)) {
            Object o = map.get(checkType);
            if(o instanceof List) {
                ArrayList<String> list = (ArrayList<String>) o;
                itemCell.setResult(list.get(formFeedBack.getCurrentCheckIndex()));
            }
        }
        // TODO: 16/12/15 判断是否有错误的信息


        return itemCell;
    }

}
