package com.autodesk.shejijia.shared.components.issue.data.source;

import android.support.annotation.NonNull;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.ResponseErrorUtil;
import com.autodesk.shejijia.shared.components.form.data.source.FormRemoteDataSource;
import com.autodesk.shejijia.shared.components.issue.common.network.IssueServerHttpManager;

import org.json.JSONObject;

import java.util.List;

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

                //TODO  获取数据后失败
                callBack.onError(ResponseErrorUtil.checkVolleyError(volleyError));

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                //TODO  获取数据后，改为真是数据
                String[] data = {};
                callBack.onSuccess(data);
            }
        });


    }

    @Override
    public void putIssueTracking(JSONObject jsonObject, final ResponseCallback<Boolean, ResponseError> callBack) {
        IssueServerHttpManager.getInstance().putIssueTracking(jsonObject, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callBack.onError(ResponseErrorUtil.checkVolleyError(volleyError));
            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                callBack.onSuccess(true);
            }
        });
    }
}
