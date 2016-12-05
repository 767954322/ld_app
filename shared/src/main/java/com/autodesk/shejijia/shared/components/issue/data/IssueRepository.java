package com.autodesk.shejijia.shared.components.issue.data;

import android.support.annotation.NonNull;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.issue.common.network.IssueServerHttpManager;
import com.autodesk.shejijia.shared.components.issue.data.source.IssueDataSource;
import com.autodesk.shejijia.shared.components.issue.data.source.IssueRemoteDataSource;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/12/5.
 */

public class IssueRepository implements IssueDataSource {

    private IssueRepository() {
    }

    private static class IssueRepositoryHolder {
        private static final IssueRepository INSTANCE = new IssueRepository();
    }

    public static IssueRepository getInstance() {
        return IssueRepository.IssueRepositoryHolder.INSTANCE;
    }

    @Override
    public void getRemoteIssueNum(@NonNull final ResponseCallback<String[], ResponseError> callBack) {

        IssueRemoteDataSource.getInstance().getRemoteIssueNum(new ResponseCallback<String[], ResponseError>() {
            @Override
            public void onSuccess(String[] data) {
                //TODO  获取数据后，改为真是数据
                callBack.onSuccess(data);
            }

            @Override
            public void onError(ResponseError errorMsg) {
                callBack.onError(errorMsg);
            }
        });

    }

}
