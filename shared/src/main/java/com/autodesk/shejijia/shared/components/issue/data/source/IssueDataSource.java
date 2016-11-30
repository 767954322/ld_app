package com.autodesk.shejijia.shared.components.issue.data.source;

import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016/12/5.
 */

public interface IssueDataSource {


    void getRemoteIssueNum(final ResponseCallback<String[], ResponseError> callBack);


    void putIssueTracking(JSONObject jsonObject, final ResponseCallback<Boolean, ResponseError> callBack);


}
