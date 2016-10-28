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

        void selectConfirm();   //确定进入表格
        void selectCancel();    //取消进入表格


    }

    interface Presenter extends BasePresenter {

        void setNavigation();   //设置bar的显示

        void setCustomer(Project projectBean); //设置客户信息

        void confirm();   //确定进入
        void cancel();    //取消退出
    }

}
