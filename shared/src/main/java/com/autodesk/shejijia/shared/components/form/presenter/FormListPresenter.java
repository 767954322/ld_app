package com.autodesk.shejijia.shared.components.form.presenter;

import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Form;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.form.common.constant.SHFormConstant;
import com.autodesk.shejijia.shared.components.form.common.entity.ItemCell;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHInspectionForm;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.CheckItem;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.FormFeedBack;
import com.autodesk.shejijia.shared.components.form.contract.FormListContract;
import com.autodesk.shejijia.shared.components.form.data.FormRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by t_aij on 16/11/22.
 */

public class FormListPresenter implements FormListContract.Presenter {
    private FormListContract.View mView;
    private List<SHInspectionForm> mFormList = new ArrayList<>();
    private Map<String,List<String>> mReinspectionMap = new LinkedHashMap<>();  //强制复验项,string是表单Id,List<String>是强制复验项的item_id的集合
    private Map<String,List<String>> mRectificationMap =new LinkedHashMap<>();  //监督整改项,同上

    public FormListPresenter(FormListContract.View view, Task task) {
        mView = view;
        initFormList(task);
    }

    @Override
    public void showFormItemList(int position) {
        if (mFormList != null && mFormList.size() != 0) {
            SHInspectionForm shInspectionForm = mFormList.get(position);
            mView.enterFormItem(shInspectionForm);
        }
    }

    @Override
    public void refreshData(List<ItemCell> itemCellList) {
        if (mFormList != null && mFormList.size() != 0) {
            for (int i = 0; i < mFormList.size(); i++) {
                ItemCell itemCell = itemCellList.get(i);
                SHInspectionForm inspectionForm = mFormList.get(i);

                refreshItemCell(itemCell, inspectionForm);
            }
        }
    }

    private void initFormList(Task task) {
        List<String> formIdList = new ArrayList<>();

        for (Form form : task.getForms()) {
            if (SHFormConstant.SHFormCategory.INSPECTION.equals(form.getCategory())) {
                formIdList.add(form.getFormId());
            }
        }

        FormRepository.getInstance().setFormList(null); //第一次显示数据,需要先置空,避免拿到内存中的其它的数据
        FormRepository.getInstance().getRemoteFormItemDetails(new ResponseCallback<List, ResponseError>() {
            @Override
            public void onSuccess(List data) {
                mFormList.clear();
                mFormList.addAll(data);
                mView.initItemCellList(initList());
            }

            @Override
            public void onError(ResponseError error) {
                mView.showNetError(error);
            }
        }, formIdList);
    }

    private List<ItemCell> initList() {
        List<ItemCell> itemCellList = new ArrayList<>();

        if (mFormList != null && mFormList.size() != 0) {
            for (SHInspectionForm shInspectionForm : mFormList) {
                itemCellList.add(initItemCell(shInspectionForm));
            }
        }

        return itemCellList;
    }

    private ItemCell initItemCell(SHInspectionForm shInspectionForm) {
        ItemCell itemCell = new ItemCell();
        HashMap typeDict = shInspectionForm.getTypeDict();
        itemCell.setTitle(shInspectionForm.getTitle());

        ArrayList<CheckItem> checkItems = shInspectionForm.getCheckItems();

        for (CheckItem checkItem : checkItems) {
            FormFeedBack formFeedBack = checkItem.getFormFeedBack();
            if (formFeedBack.getCurrentCheckIndex() == 1) {
                Object check = typeDict.get(checkItem.getCheckType());
                Object action = typeDict.get(checkItem.getActionType());
                if (check instanceof List && action instanceof List) {
                    List<String> checkList = (List<String>) check;
                    List<String> actionList = (List<String>) action;
                    if ("不合格".equals(checkList.get(1)) && "强制复验".equals(actionList.get(Integer.valueOf(formFeedBack.getCurrentActionIndex())))) {
                        itemCell.setResult("强制复验");
                    }
                }
            }

        }

        return itemCell;
    }

    private void refreshItemCell(ItemCell itemCell, SHInspectionForm inspectionForm) {
        ArrayList<CheckItem> checkItems = inspectionForm.getCheckItems();
        HashMap typeDict = inspectionForm.getTypeDict();
        int num = 0;
        for (int i = 0; i < checkItems.size(); i++) {
            CheckItem checkItem = checkItems.get(i);
            FormFeedBack formFeedBack = checkItem.getFormFeedBack();
// TODO: 16/12/8 需要根据temp_id 来确定一个item的输入值,强制复验和监督整改需要知道  表单ID和条目ID就可以
            if (formFeedBack.getCurrentCheckIndex() == 1) {
                Object check = typeDict.get(checkItem.getCheckType());
                Object action = typeDict.get(checkItem.getActionType());
                if (check instanceof List && action instanceof List) {
                    List<String> checkList = (List<String>) check;
                    List<String> actionList = (List<String>) action;
                    if ("不合格".equals(checkList.get(1)) && "强制复验".equals(actionList.get(Integer.valueOf(formFeedBack.getCurrentActionIndex())))) {
                        if("强制复验".equals(actionList.get(Integer.valueOf(formFeedBack.getCurrentActionIndex())))) {
                            itemCell.setResult("强制复验");
                            Object status = typeDict.get("status");
                            if (status instanceof List) {
                                List<String> statusList = (List<String>) status;
                                for (int j = 0; j < statusList.size(); j++) {
                                    if ("强制复验".equals(statusList.get(j))) {
                                        inspectionForm.setStatus(j);
                                    }
                                }
                            }

                        }



                        return;
                    }
                }
            }

            if (checkItem.isChecked()) {
                num++;
            }

            if (num == checkItems.size() - 1) {
                itemCell.setResult("验收通过");
                Object status = typeDict.get("status");
                if (status instanceof List) {
                    List<String> statusList = (List<String>) status;
                    for (int j = 0; j < statusList.size(); j++) {
                        if ("验收通过".equals(statusList.get(j))) {
                            inspectionForm.setStatus(j);
                        }
                    }
                }
                return;
            }
        }

        itemCell.setResult(null);
    }
}
