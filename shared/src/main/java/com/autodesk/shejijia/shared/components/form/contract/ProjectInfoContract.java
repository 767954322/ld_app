package com.autodesk.shejijia.shared.components.form.contract;


import com.autodesk.shejijia.shared.components.common.entity.Project;
import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

/**
 * Created by t_aij on 16/10/21.
 */

public interface ProjectInfoContract {

    interface View extends BaseView {
        void setNavigation();   //设置bar的显示

        void setUsername(String username);
        void setTelephone(String telephone);
        void setAddress(String address);
        void setCommunite(String communite);

        void selectConfirm();   //确定进入表格,应该是选择那个表格,所以需要传进去一个activity,而且对应的表格传进去的数据不一样
        //1,没有表格;2,复检;3,预检;4,检查完之后的查看
        void selectCancel();    //取消进入表格


    }

    interface Presenter extends BasePresenter {

        void setNavigation();   //设置bar的显示

        void setCustomer(Project projectBean); //设置客户信息

        void confirm(Project project);   //确定进入

        void cancel();    //取消退出
    }

}
