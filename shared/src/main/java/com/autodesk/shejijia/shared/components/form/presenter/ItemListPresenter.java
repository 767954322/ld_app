package com.autodesk.shejijia.shared.components.form.presenter;

import com.autodesk.shejijia.shared.components.form.common.entity.OptionCell;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHInspectionForm;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.CheckItem;
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
                OptionCell optionCell = new OptionCell();
                optionCell.setTitle(checkItem.getTitle());

                optionCell.setStandard(checkItem.getStandard());
                optionCell.setActionType(checkItem.getActionType());

                HashMap<String, String[]> typeDict = new HashMap<>();
                String itemTypeDict = checkItem.getCheckType();
//                LogUtils.d("asdf",typeDict.toString() + "类别: "+ itemTypeDict);
                if(map.containsKey(itemTypeDict)) {
                    Object o = map.get(itemCellList);
                    if( o instanceof String[]) {
                        String[] s = (String[]) o;
                        typeDict.put(itemTypeDict, s);
                    } else if(o instanceof List[]){
                        List<String> list = (List<String>) o;
                        Object[] objects = list.toArray();
                        try{
                            String[] strings = (String[]) objects;
                        } catch (Exception e) {
                            typeDict.put(itemTypeDict,new String[]{"右边1","中间1","左边1"});
                        }
                    } else {
                        typeDict.put(itemTypeDict,new String[]{"右边","中间","左边"});
                    }
                    optionCell.setTypeDict(typeDict);
                }
                itemCellList.add(optionCell);
            }
        }

        return itemCellList;
    }
}
