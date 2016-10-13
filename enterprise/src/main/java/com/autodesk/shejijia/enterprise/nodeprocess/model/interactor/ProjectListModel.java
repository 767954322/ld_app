package com.autodesk.shejijia.enterprise.nodeprocess.model.interactor;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.enterprise.base.network.EnterpriseServerHttpManager;
import com.autodesk.shejijia.enterprise.common.Interface.BaseLoadedListener;
import com.autodesk.shejijia.enterprise.common.utils.Constants;
import com.autodesk.shejijia.enterprise.common.utils.UrlHelper;
import com.autodesk.shejijia.enterprise.nodeprocess.contract.ProjectListContract;
import com.autodesk.shejijia.enterprise.nodeprocess.model.entity.TaskListBean;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;

import org.json.JSONObject;

/**
 * Created by t_xuz on 10/11/16.
 * 获取网络请求的结果,model层
 */
public class ProjectListModel implements ProjectListContract.Model {

    private BaseLoadedListener<TaskListBean> mLoadedListener;

    public ProjectListModel(BaseLoadedListener<TaskListBean> loadedListener) {
        this.mLoadedListener = loadedListener;
    }


    @Override
    public void getProjectListData(String requestUrl, final String eventTag, String requestTag, String token) {

        EnterpriseServerHttpManager.getInstance().getUserProjectLists(requestUrl, token, requestTag, new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtils.d(jsonObject + "");
                String result = jsonObject.toString();
                TaskListBean taskListBean = GsonUtil.jsonToBean(result, TaskListBean.class);
                mLoadedListener.onSuccess(eventTag, taskListBean);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mLoadedListener.onError(volleyError.getMessage());
            }
        });
    }
}
