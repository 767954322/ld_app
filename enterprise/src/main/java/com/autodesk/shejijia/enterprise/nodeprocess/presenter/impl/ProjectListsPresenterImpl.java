package com.autodesk.shejijia.enterprise.nodeprocess.presenter.impl;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.autodesk.shejijia.enterprise.common.Interface.BaseLoadedListener;
import com.autodesk.shejijia.enterprise.common.utils.Constants;
import com.autodesk.shejijia.enterprise.nodeprocess.entity.TaskListBean;
import com.autodesk.shejijia.enterprise.nodeprocess.interactor.ProjectListInteractor;
import com.autodesk.shejijia.enterprise.nodeprocess.interactor.impl.ProjectListInteratorImpl;
import com.autodesk.shejijia.enterprise.nodeprocess.presenter.ProjectListsPresenter;
import com.autodesk.shejijia.enterprise.nodeprocess.view.ProjectListsView;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.SharedPreferencesUtils;

/**
 * Created by t_xuz on 10/11/16.
 * 主页 项目(任务)列表页对应的presenter的实现类-->对应 TaskListFragment
 */
public class ProjectListsPresenterImpl implements ProjectListsPresenter,BaseLoadedListener<TaskListBean>{

    private Context mContext;
    private ProjectListsView mProjectListsView;
    private ProjectListInteractor mProjectListInteractor;
    private String XToken;

    public ProjectListsPresenterImpl(Context context,ProjectListsView projectListsView){
        this.mContext = context;
        this.mProjectListsView = projectListsView;
        mProjectListInteractor = new ProjectListInteratorImpl(this);
        //获取token
        MemberEntity entity = (MemberEntity) SharedPreferencesUtils.getObject(mContext, Constants.USER_INFO);
        if (entity != null && !TextUtils.isEmpty(entity.getHs_accesstoken())) {
//            XToken = "587e1e6bd9c26875535868dec8e3045c";
            XToken = entity.getHs_accesstoken();
            LogUtils.e("XToken",XToken);
        }

    }

    /*
    * 从model层获取数据成功回调方法(如:网络请求返回的结果,数据库查询的结果)
    * */
    @Override
    public void onSuccess(String eventTag,TaskListBean data) {
        mProjectListsView.hideLoading();
        if (eventTag.equalsIgnoreCase(Constants.REFRESH_EVENT)){
            mProjectListsView.refreshProjectListData(data);
        }else if (eventTag.equalsIgnoreCase(Constants.LOAD_MORE_EVENT)){
            mProjectListsView.addMoreProjectListData(data);
        }
    }

    /*
    * 从 model层获取数据失败回调方法(如:网络返回错误,数据库查询错误)
    * */
    @Override
    public void onError(String msg) {
        mProjectListsView.hideLoading();
        mProjectListsView.showNetError(msg);
    }

    /*
    * presenter层提供给view层回调的方法,主要根据view层传入的参数来调用model层的方法
    * */
    @Override
    public void loadTaskListData(String findDate,String eventTag,String requestTag, int pageSize, boolean isSwipeRefresh) {
        mProjectListsView.hideLoading();
        mProjectListInteractor.getProjectListData(findDate,eventTag,requestTag,pageSize,XToken);
    }

    @Override
    public void onItemTopClickListener(View view, int position, TaskListBean entity) {
        mProjectListsView.navigateProjectDetails(view,position,entity);
    }

    @Override
    public void onItemChildItemClickListener(View view, int position, TaskListBean entity) {
        mProjectListsView.navigateTaskDetails(view,position,entity);
    }
}
