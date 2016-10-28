package com.autodesk.shejijia.shared.components.form.contract;

import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

/**
 * Created by t_aij on 16/10/28.
 */

public interface PrecheckContract {
    interface View extends BaseView{

        void setNavigationBar(); //设置navigationbar
        //显示必要条件
        //显示辅助条件
        //显示按钮


    }

    interface Presenter extends BasePresenter{

        //根据项目详情传递过来的数据,选择打开哪些表格,显示对应表格中的内容,这个就是分类显示什么养的界面

        //点击合格或者不合格按钮,显示下面的按钮
        //验收或者不验收按钮,进入到写一个页面时,可以有显示加载的东西

    }


}
