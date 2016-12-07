package com.autodesk.shejijia.shared.components.form.presenter;

import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Form;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.form.common.constant.SHFormConstant;
import com.autodesk.shejijia.shared.components.form.common.entity.ItemCell;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHInspectionForm;
import com.autodesk.shejijia.shared.components.form.contract.FormListContract;
import com.autodesk.shejijia.shared.components.form.data.FormRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_aij on 16/11/22.
 */

public class FormListPresenter implements FormListContract.Presenter {
    private FormListContract.View mView;
    private List<SHInspectionForm> mFormList = new ArrayList<>();

    public FormListPresenter(FormListContract.View view,Task task) {
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

    private void initFormList(Task task) {
        List<String> formIdList = new ArrayList<>();  //存储表单的id
        final List<ItemCell> itemCellList = new ArrayList<>();

        for (Form form : task.getForms()) {
            if (SHFormConstant.SHFormCategory.INSPECTION.equals(form.getCategory())) {   //查找预检的表单  的表单id
                formIdList.add(form.getFormId());
            }
        }

        FormRepository.getInstance().setFormList(null); //第一次显示数据,需要先置空,避免拿到内存中的其它的数据
        FormRepository.getInstance().getRemoteFormItemDetails(new ResponseCallback<List, ResponseError>() {  //根据表单id获取具体的表单数据
            @Override
            public void onSuccess(List data) {    //获取到四张表格的数据,包括文字内容
                mFormList.addAll(data);
                initItemCellList(itemCellList);
                mView.initItemCellList(itemCellList);
            }

            @Override
            public void onError(ResponseError error) {
                mView.showNetError(error);
            }
        }, formIdList);
    }

    private void initItemCellList(List<ItemCell> itemCellList) {
        if (mFormList != null && mFormList.size() != 0) {
            for (SHInspectionForm shInspectionForm : mFormList) {
                String title = shInspectionForm.getTitle();
                ItemCell itemCell = new ItemCell();
                itemCell.setTitle(title);
                itemCellList.add(itemCell);
            }
        }
    }
}
