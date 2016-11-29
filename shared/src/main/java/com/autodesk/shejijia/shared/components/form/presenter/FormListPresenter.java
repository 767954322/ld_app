package com.autodesk.shejijia.shared.components.form.presenter;

import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHInspectionForm;
import com.autodesk.shejijia.shared.components.form.contract.FormListContract;

import java.util.List;

/**
 * Created by t_aij on 16/11/22.
 */

public class FormListPresenter implements FormListContract.Presenter {
    private Task mTask;
    private FormListContract.View mView;
//    private FormPresenter mPresenter;
    private List<SHInspectionForm> mSHFormList;

    public FormListPresenter(FormListContract.View view, FormPresenter presenter) {
        mView = view;
//        mPresenter = presenter;
        mSHFormList = presenter.getShInspectionFormList();
    }

    @Override
    public void showFormItemList(int position) {
        if (mSHFormList != null && mSHFormList.size() != 0) {
            SHInspectionForm shInspectionForm = mSHFormList.get(position);
            mView.enterFormItem(shInspectionForm);
        }
    }
}
