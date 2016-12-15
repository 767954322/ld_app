package com.autodesk.shejijia.shared.components.form.presenter;

import android.os.Bundle;

import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Form;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.utility.UserInfoUtils;
import com.autodesk.shejijia.shared.components.form.common.constant.SHFormConstant;
import com.autodesk.shejijia.shared.components.form.common.entity.ItemCell;
import com.autodesk.shejijia.shared.components.form.common.entity.SHForm;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHPrecheckForm;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.CheckItem;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.FormFeedBack;
import com.autodesk.shejijia.shared.components.form.contract.FormListContract;
import com.autodesk.shejijia.shared.components.form.data.FormRepository;
import com.autodesk.shejijia.shared.framework.AdskApplication;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by t_aij on 16/11/22.
 */

public class FormListPresenter implements FormListContract.Presenter {
    private FormListContract.View mView;
    private ArrayList<SHForm> mFormList = new ArrayList<>();
    private LinkedHashMap<String, List<String>> mReinspectionMap = new LinkedHashMap<>();   //强制复验项,string是表单Id,List<String>是强制复验项的item_id的集合
    private LinkedHashMap<String, List<String>> mRectificationMap = new LinkedHashMap<>();  //监督整改项,同上
    private int mCheckSum = 0;
    private final int REINSPECTION = 1;   //身份标识,下同
    private final int RECTIFICATION = 2;

    public FormListPresenter(FormListContract.View view, Task task) {
        mView = view;
        initFormList(task);
    }

    @Override
    public void showFormItemList(int position) {
        if (mFormList != null && mFormList.size() != 0) {
            SHForm shInspectionForm = mFormList.get(position);
            mView.enterFormItem(shInspectionForm);
        }
    }

    @Override
    public void refreshData(List<ItemCell> itemCellList) {
        if (mFormList != null && mFormList.size() != 0) {
            int sum = 0;
            mCheckSum = 0;

            for (int i = 0; i < mFormList.size(); i++) {
                ItemCell itemCell = itemCellList.get(i);
                SHForm inspectionForm = mFormList.get(i);
                sum += inspectionForm.getCheckItems().size();

                refreshItemCell(itemCell, inspectionForm);

            }

            if (sum == mCheckSum) {
                mView.showSubmitBtn();
            }

        }
    }

    @Override
    public void submitData(final SHPrecheckForm precheckForm) {
        mFormList.add(precheckForm);
        Bundle bundle = new Bundle();
        bundle.putString("user_id", UserInfoUtils.getMemberId(AdskApplication.getInstance().getApplicationContext()));

        FormRepository.getInstance().updateRemoteForms(mFormList, bundle, new ResponseCallback<Object, ResponseError>() {
            @Override
            public void onSuccess(Object data) {
                modifyTaskStatus(precheckForm);
            }

            @Override
            public void onError(ResponseError error) {
// TODO: 16/12/13 提交表单失败的业务逻辑
                mView.showError(error.getMessage());
            }

        });
    }

    @Override
    public void enterReinspection() {
        //可变的界面和不可变的界面
        mView.enterMutableItems(mFormList,mReinspectionMap);
        //根据角色,进入不同的界面
    }

    @Override
    public void enterRectification() {
        //可变的界面和不可变的界面
        mView.enterImmutableItems(mFormList,mRectificationMap);
    }

    private void modifyTaskStatus(SHPrecheckForm precheckForm) {
        Bundle bundle = new Bundle();
        bundle.putLong("pid", Long.valueOf(precheckForm.getProjectId()));
        bundle.putString("tid", precheckForm.getTaskId());

        boolean isReinspection = getByMap(mReinspectionMap);

        boolean isRectification = getByMap(mRectificationMap);

        Map<String, String> map = new HashMap<>();
        if (isRectification && isReinspection) {   //监督整改,强制复验
            map.put("result", ConstructionConstants.TaskStatus.REINSPECTION_AND_RECTIFICATION.toUpperCase());
        } else {
            if (isRectification) {
                map.put("result", ConstructionConstants.TaskStatus.RECTIFICATION.toUpperCase());
            } else if (isReinspection) {
                map.put("result", ConstructionConstants.TaskStatus.REINSPECTION.toUpperCase());
            } else {
                map.put("result", ConstructionConstants.TaskStatus.QUALIFIED.toUpperCase());
            }
        }

        FormRepository.getInstance().inspectTask(bundle, new JSONObject(map), new ResponseCallback<Map, ResponseError>() {
            @Override
            public void onSuccess(Map data) {
// TODO: 16/12/13 改变Task状态成功后的业务逻辑
                mView.SubmitSuccess();
            }

            @Override
            public void onError(ResponseError error) {
// TODO: 16/12/13 改变Task状态失败后的业务逻辑
                mView.hideLoading();
                mView.showError(error.getMessage());
            }
        });


    }

    //获取数据,初始化各项表单
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
            for (SHForm shInspectionForm : mFormList) {
                itemCellList.add(initItemCell(shInspectionForm));
            }
        }

        return itemCellList;
    }

    private ItemCell initItemCell(SHForm shInspectionForm) {
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

    //跟新每个条目
    private void refreshItemCell(ItemCell itemCell, SHForm inspectionForm) {
        ArrayList<CheckItem> checkItems = inspectionForm.getCheckItems();
        HashMap typeDict = inspectionForm.getTypeDict();
        String formTemplateId = inspectionForm.getFormTemplateId();
        int num = 0;

        for (int i = 0; i < checkItems.size(); i++) {
            CheckItem checkItem = checkItems.get(i);
            FormFeedBack formFeedBack = checkItem.getFormFeedBack();

            if (formFeedBack.getCurrentCheckIndex() == 1) {
                Object check = typeDict.get(checkItem.getCheckType());
                Object action = typeDict.get(checkItem.getActionType());
                if (check instanceof List && action instanceof List) {
                    List<String> checkList = (List<String>) check;
                    List<String> actionList = (List<String>) action;
                    if ("不合格".equals(checkList.get(1))) {
                        if ("强制复验".equals(actionList.get(Integer.valueOf(formFeedBack.getCurrentActionIndex())))) {
                            updateMap(mReinspectionMap, checkItem, formTemplateId, true);

                        } else if ("监督整改".equals(actionList.get(Integer.valueOf(formFeedBack.getCurrentActionIndex())))) {
                            updateMap(mRectificationMap, checkItem, formTemplateId, true);

                        }
                    }
                }
            } else {
                updateMap(mReinspectionMap, checkItem, formTemplateId, false);
                updateMap(mRectificationMap, checkItem, formTemplateId, false);
            }


            if (checkItem.isChecked()) {                    //判断验收来多少项
                num++;
                mCheckSum++;
            }

            refreshMapUi(mReinspectionMap, REINSPECTION);   //刷新强制复验

            refreshMapUi(mRectificationMap, RECTIFICATION); //刷新监督整改

            if (mReinspectionMap.containsKey(formTemplateId)) {   //根据强制复验项决定显示结果标签
                List<String> list = mReinspectionMap.get(formTemplateId);
                if (list == null || list.size() == 0) {
                    checkAllItem(itemCell, typeDict, inspectionForm, checkItems.size(), num);
                } else if (list != null && list.size() != 0 && num == checkItems.size()) {
                    itemCell.setResult("强制复验");
                    setFormStatus(typeDict, inspectionForm, "强制复验");
                }

            } else {
                checkAllItem(itemCell, typeDict, inspectionForm, checkItems.size(), num);
            }


        }


    }

    //设置表单状态
    private void setFormStatus(HashMap typeDict, SHForm inspectionForm, String type) {
        Object status = typeDict.get("status");   //设置整条表单验收状态,强制复验或者验收通过
        if (status instanceof List) {
            List<String> statusList = (List<String>) status;

            for (int j = 0; j < statusList.size(); j++) {
                if (type.equals(statusList.get(j))) {
                    inspectionForm.setStatus(j);
                    break;
                }
            }
        }
    }

    //跟新强制复验和监督整改的集合
    private void updateMap(Map<String, List<String>> map, CheckItem checkItem, String formTemplateId, boolean isAdd) {

        if (isAdd) {
            if (!map.containsKey(formTemplateId)) {
                map.put(formTemplateId, new ArrayList<String>());
            }
            ArrayList<String> list = (ArrayList<String>) map.get(formTemplateId);
            if (!list.contains(checkItem.getItemId())) {
                list.add(checkItem.getItemId());
            }
        } else {
            if (map.containsKey(formTemplateId)) {
                ArrayList<String> list = (ArrayList<String>) map.get(formTemplateId);
                if (list.contains(checkItem.getItemId())) {
                    list.remove(checkItem.getItemId());
                }
            }
        }


    }

    //跟新强制复验和监督整改的界面UI
    private void refreshMapUi(Map<String, List<String>> map, int identify) {
        boolean flag = getByMap(map);

        if (identify == REINSPECTION) {
            if (flag) {
                mView.refreshReinspection(map);
            } else {
                mView.refreshReinspection(null); //跟新强制复验的记录
            }
        } else if (identify == RECTIFICATION) {
            if (flag) {
                mView.refreshRectification(map);
            } else {
                mView.refreshRectification(null);
            }
        }

    }

    //一个条目是否全选的判断
    private void checkAllItem(ItemCell itemCell, HashMap typeDict, SHForm inspectionForm, int size, int num) {

        if (num == size) {    //通过的条件:1,全部选择完;2,没有强制复验项
            itemCell.setResult("验收通过");

            setFormStatus(typeDict, inspectionForm, "验收通过");

        } else {
            itemCell.setResult(null);
        }
    }

    //是否显示强制复验或者监督整改
    private boolean getByMap(Map<String, List<String>> map) {
        Iterator<List<String>> iterator = map.values().iterator();
        boolean flag = false;
        while (iterator.hasNext()) {
            List<String> list = iterator.next();
            if (list != null && list.size() != 0) {
                flag = true;
            }
        }
        return flag;
    }

    // TODO: 16/12/12 后期删除,只为测试使用
    public void selectorAll() {
        if (mFormList != null && mFormList.size() != 0) {

            for (int i = 0; i < mFormList.size(); i++) {
                SHForm inspectionForm = mFormList.get(i);
                for (CheckItem checkItem : inspectionForm.getCheckItems()) {
                    checkItem.getFormFeedBack().setCurrentCheckIndex(0);
                }
            }

            mView.showSubmitBtn();

        }

    }
}
