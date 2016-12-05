package com.autodesk.shejijia.shared.components.issue.data.source;

import android.support.annotation.NonNull;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.form.data.source.FormRemoteDataSource;
import com.autodesk.shejijia.shared.components.issue.common.network.IssueServerHttpManager;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/12/5.
 */

public class IssueRemoteDataSource implements IssueDataSource {


    private IssueRemoteDataSource() {
    }

    private static class IssueRemoteDataSourceHolder {
        private static final IssueRemoteDataSource INSTANCE = new IssueRemoteDataSource();
    }

    public static IssueRemoteDataSource getInstance() {
        return IssueRemoteDataSource.IssueRemoteDataSourceHolder.INSTANCE;
    }

    @Override
    public void getRemoteIssueNum(@NonNull final ResponseCallback<String[], ResponseError> callBack) {

        IssueServerHttpManager.getInstance().getIssueNum(new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                //TODO  获取数据后，改为真是数据
                String[] data = {};
                callBack.onSuccess(data);
            }
        });


    }
}
