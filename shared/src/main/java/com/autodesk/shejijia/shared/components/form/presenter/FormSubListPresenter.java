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
    private ArrayList<CheckItem> mCheckItemList;
    private HashMap mTypeDict;
    private Map<String, Integer> mCategoryMap;

    public FormSubListPresenter(FormSubListContract.View view, SHInspectionForm inspectionForm) {
        mView = view;
        initFormData(inspectionForm);
    }

    private void initFormData(SHInspectionForm inspectionForm) {
        mCheckItemList = inspectionForm.getCheckItems();
        mTypeDict = inspectionForm.getTypeDict();

        List<ItemCell> itemCellList = new ArrayList<>();           //总的条目
        mCategoryMap = new LinkedHashMap<>();                      //条目的类型
        HashMap<String, Integer> checkMap = new HashMap<>();       //类型+选择数量
        HashMap<String, Integer> precheckMap = new HashMap<>();    //类型+强制复验项数量
        if(mCheckItemList.size() == 0) {
            // TODO: 16/12/13 对于基层检查的情况,需要后面在做处理 ,先设置一个完成的状态
            inspectionForm.setStatus(1);
            return;
        }

        for (int i = 0; i < mCheckItemList.size(); i++) {
            CheckItem checkItem = mCheckItemList.get(i);

            initAllMap(checkItem, mCategoryMap, precheckMap, checkMap, mTypeDict);
        }

        initItemCellList(itemCellList, mCategoryMap, checkMap, precheckMap);

        mView.initItemCellList(itemCellList);
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

    @Override
    public void refreshFormData(List<ItemCell> itemCells) {    //两个东西会变,一个是选择的数目,另一个是强制复验的数目
        HashMap<String, Integer> checkMap = new HashMap<>();
        HashMap<String, Integer> precheckMap = new HashMap<>();

        for (int i = 0; i < mCheckItemList.size(); i++) {
            CheckItem checkItem = mCheckItemList.get(i);
            refreshItemCellList(checkItem, checkMap, precheckMap);
        }

        for (int i = 0; i < itemCells.size(); i++) {
            ItemCell itemCell = itemCells.get(i);
            String category = itemCell.getTitle();
            itemCell.setResult(getBlueText(String.valueOf(checkMap.get(category))) + "/" + mCategoryMap.get(category));
            itemCell.setReinspectionNum(precheckMap.get(category));
        }
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

    private void refreshItemCellList(CheckItem checkItem, HashMap<String, Integer> checkMap, HashMap<String, Integer> precheckMap) {
        String category = checkItem.getCategory();

        if (!checkMap.containsKey(category)) {
            checkMap.put(category, 0);
        }

        if (!precheckMap.containsKey(category)) {
            precheckMap.put(category, 0);
        }

        if (checkItem.isChecked()) {
            checkMap.put(category, checkMap.get(category) + 1);
            List<String> actionTypeList = (List<String>) mTypeDict.get(checkItem.getActionType());
            List<String> checkTypeList = (List<String>) mTypeDict.get(checkItem.getCheckType());

            if ("强制复验".equals(actionTypeList.get(Integer.valueOf(checkItem.getFormFeedBack().getCurrentActionIndex())))
                    && "不合格".equals(checkTypeList.get(Integer.valueOf(checkItem.getFormFeedBack().getCurrentCheckIndex())))) {
                precheckMap.put(category, precheckMap.get(category) + 1);
            }

        }

    }
}
