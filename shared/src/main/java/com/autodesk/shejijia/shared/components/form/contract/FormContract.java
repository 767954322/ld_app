package com.autodesk.shejijia.shared.components.form.contract;

import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

import java.util.ArrayList;

/**
 * Created by t_aij on 16/11/28.
 */

public interface FormContract {

    interface View extends BaseView{

        void initFormList(ArrayList<String> titleList);

    }

    interface Presenter extends BasePresenter {

        void show(Task task);   //通过具体的任务,展现后以后的数据
    }



}
