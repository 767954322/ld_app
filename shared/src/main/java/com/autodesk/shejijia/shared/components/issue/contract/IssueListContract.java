package com.autodesk.shejijia.shared.components.issue.contract;

import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;
/**
 * Created by Menghao.Gu on 2016/12/1.
 */

public interface IssueListContract {

    interface View extends BaseView {

        void onRefreshIssueTracking();

        void getListData(String[] mIssueListData);

        void getIssueNum();

    }

    interface Presenter extends BasePresenter {


        /*
        *设置列表展示样式
        * */
        void setIssueListStyle(String tag);

        /*
        * 初始化请求参数
         * */
        void initFilterRequestParams();

        /*
        * 刷新问题追踪列表
        * */
        void refreshIssueTracking();

        /*
        * 获取施工已过期，今天到期等 总问题数（一个接口）
        * 获取某个项目的已过期，今天到期等问题数（需要六个接口）
        * */
        void getIssueNumber();

    }

}
