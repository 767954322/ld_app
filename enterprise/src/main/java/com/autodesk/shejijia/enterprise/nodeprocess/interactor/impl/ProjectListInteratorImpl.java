package com.autodesk.shejijia.enterprise.nodeprocess.interactor.impl;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.enterprise.base.network.EnterpriseServerHttpManager;
import com.autodesk.shejijia.enterprise.common.Interface.BaseLoadedListener;
import com.autodesk.shejijia.enterprise.common.utils.Constants;
import com.autodesk.shejijia.enterprise.nodeprocess.entity.TaskListBean;
import com.autodesk.shejijia.enterprise.nodeprocess.interactor.ProjectListInteractor;
import com.autodesk.shejijia.shared.components.common.network.NetRequestManager;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;

import org.json.JSONObject;

/**
 * Created by t_xuz on 10/11/16.
 * 获取网络请求的结果,model层
 */
public class ProjectListInteratorImpl implements ProjectListInteractor{

    private BaseLoadedListener<TaskListBean> mLoadedListener;

    public ProjectListInteratorImpl(BaseLoadedListener<TaskListBean> loadedListener){
        this.mLoadedListener = loadedListener;
    }

    @Override
    public void getProjectListData(String findDate,final String eventTag,String requestTag, int pageSize, String token) {

        EnterpriseServerHttpManager.getInstance().getTaskLists(findDate, 0, Constants.PAGE_LIMIT, pageSize, token, new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtils.d(jsonObject+"");
                String result = jsonObject.toString();
                TaskListBean taskListBean = GsonUtil.jsonToBean(result, TaskListBean.class);
                mLoadedListener.onSuccess(eventTag,taskListBean);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mLoadedListener.onError(volleyError.getMessage());
            }
        });
    }
}
