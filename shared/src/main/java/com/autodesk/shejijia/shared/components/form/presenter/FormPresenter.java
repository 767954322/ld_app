package com.autodesk.shejijia.shared.components.form.presenter;

import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Form;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.form.common.constant.SHFormConstant;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHInspectionForm;
import com.autodesk.shejijia.shared.components.form.contract.FormContract;
import com.autodesk.shejijia.shared.components.form.data.FormRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_aij on 16/11/28.
 */

public class FormPresenter implements FormContract.Presenter{
    private FormContract.View mView;
    private List<SHInspectionForm> shInspectionFormList = new ArrayList<>();

    public FormPresenter(FormContract.View view) {
        mView = view;
    }

    @Override
    public void show(Task task) {

        List<String> formIdList = new ArrayList<>();  //存储表单的id
        for (Form form : task.getForms()) {
            if (SHFormConstant.SHFormCategory.INSPECTION.equals(form.getCategory())) {   //查找预检的表单  的表单id
                formIdList.add(form.getFormId());
            }
        }

        FormRepository.getInstance().setFormList(null); //第一次显示数据,需要先置空,避免拿到内存中的其它的数据
        FormRepository.getInstance().getRemoteFormItemDetails(new ResponseCallback<List,ResponseError>() {  //根据表单id获取具体的表单数据
            @Override
            public void onSuccess(List data) {    //获取到四张表格的数据,包括文字内容
                shInspectionFormList.addAll(data);
                ArrayList<String> titleList = new ArrayList<>();
                for (SHInspectionForm shInspectionForm : shInspectionFormList) {
                    titleList.add(shInspectionForm.getTitle());
                }
// TODO: 16/11/28 以后肯定会有对身份,项目状态的判断,来显示项目的信息
                mView.initFormList(titleList);

            }

            @Override
            public void onError(ResponseError error) {

            }
        }, formIdList);

    }

    public List<SHInspectionForm> getShInspectionFormList() {
        return shInspectionFormList;
    }
}














