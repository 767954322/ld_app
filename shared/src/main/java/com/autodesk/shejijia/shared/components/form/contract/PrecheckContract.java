package com.autodesk.shejijia.shared.components.form.contract;

import android.widget.TextView;

import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.form.common.entity.ContainedForm;
import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

/**
 * Created by t_aij on 16/10/28.
 */

public interface PrecheckContract {
    interface View extends BaseView{

        void setToolbarTitle(String title);

        void addNecessaryView(TextView view);  //添条件
        void addAdditionalLayout(android.view.View view);   //添加辅助条件

        //显示按钮,分为两个合格 ,不合格。
        void showQualifiedBtn();
        void showUnqualifiedBtn();

        //合格的进入
        void enterQualified(Task task);
        //不合格的进入
        void enterUnqualified(ContainedForm form);


    }

    interface Presenter extends BasePresenter{

        //根据项目详情传递过来的数据,选择打开哪些表格,显示对应表格中的内容,这个就是分类显示什么养的界面
        void showForm(Task task);

        //点击合格或者不合格按钮,显示下面的按钮
        //验收或者不验收按钮,进入到写一个页面时,可以有显示加载的东西
        void showOkBtn();
        void showNoBtn();

        void clickOptionBtn();  //选择对应的btn

    }


}
