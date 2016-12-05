package com.autodesk.shejijia.shared.components.issue.data.source;

import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;

import java.util.List;

/**
 * Created by Administrator on 2016/12/5.
 */

public interface IssueDataSource {


    void getRemoteIssueNum(final ResponseCallback<String[], ResponseError> callBack);


    void putIssueTracking(String contentText, String audioPath, List<String> imgPath, final ResponseCallback<Boolean, ResponseError> callBack);


}
