package com.autodesk.shejijia.shared.components.form.presenter;

import com.autodesk.shejijia.shared.components.form.common.entity.ItemCell;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHInspectionForm;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.CheckItem;
import com.autodesk.shejijia.shared.components.form.contract.FormSubListContract;
import com.autodesk.shejijia.shared.components.form.contract.FormSubListContract.Presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by t_aij on 16/11/29.
 */

public class FormSubListPresenter implements Presenter {
    private FormSubListContract.View mView;

    public FormSubListPresenter(FormSubListContract.View view) {
        mView = view;
    }


    @Override
    public List<ItemCell> getItemCells(SHInspectionForm inspectionForm) {
        ArrayList<CheckItem> checkItemList = inspectionForm.getCheckItems();
        HashMap typeDict = inspectionForm.getTypeDict();

        List<ItemCell> itemCellList = new ArrayList<>();           //总的条目
        Map<String, Integer> categoryMap = new LinkedHashMap<>();  //条目的类型
        HashMap<String, Integer> checkMap = new HashMap<>();       //类型+选择数量
        HashMap<String, Integer> precheckMap = new HashMap<>();    //类型+强制复验项数量

        for (int i = 0; i < checkItemList.size(); i++) {
            CheckItem checkItem = checkItemList.get(i);

            initAllMap(checkItem, categoryMap, precheckMap, checkMap, typeDict);
        }

        initItemCellList(itemCellList, categoryMap, checkMap, precheckMap);

        return itemCellList;
    }

    @Override
    public int getCategoryIndex(List<CheckItem> checkItemList, String category) {
        int index = 0;

        for (int i = 0; i < checkItemList.size(); i++) {
            if (category.equals(checkItemList.get(i).getCategory())) {
                index = i;
                break;
            }
        }

        return index;
    }

    private void initAllMap(CheckItem checkItem, Map<String, Integer> categoryMap,
                            HashMap<String, Integer> precheckMap, HashMap<String, Integer> checkMap, HashMap typeDict) {
        String category = checkItem.getCategory();

        if (categoryMap.containsKey(category)) {
            categoryMap.put(category, categoryMap.get(category) + 1); //统计类别和个数
        } else {
            categoryMap.put(category, 1);
        }

        if (!checkMap.containsKey(category)) {
            checkMap.put(category, 0);
        }

        if (!precheckMap.containsKey(category)) {
            precheckMap.put(category, 0);
        }

        if (checkItem.isChecked()) {
            checkMap.put(category, checkMap.get(category) + 1);
            List<String> actionTypeList = (List<String>) typeDict.get(checkItem.getActionType());
            List<String> checkTypeList = (List<String>) typeDict.get(checkItem.getCheckType());

            if ("强制复验".equals(actionTypeList.get(Integer.valueOf(checkItem.getFormFeedBack().getCurrentActionIndex())))
                    && "不合格".equals(checkTypeList.get(Integer.valueOf(checkItem.getFormFeedBack().getCurrentCheckIndex())))) {
                precheckMap.put(category, precheckMap.get(category) + 1);
            }

        }
    }

    private void initItemCellList(List<ItemCell> itemCellList, Map<String, Integer> categoryMap,
                                  HashMap<String, Integer> checkMap, HashMap<String, Integer> precheckMap) {
        Iterator<String> iterator = categoryMap.keySet().iterator();

        while (iterator.hasNext()) {
            String category = iterator.next();
            ItemCell itemCell = new ItemCell();
            itemCell.setTitle(category);

            itemCell.setResult(getBlueText(String.valueOf(checkMap.get(category))) + "/" + categoryMap.get(category));
            itemCell.setReinspectionNum(precheckMap.get(category));
            itemCellList.add(itemCell);

        }
    }

    private String getBlueText(String string) {
        return String.format("<font color=\"#2B77C1\">%s</font>", string);
    }
}
