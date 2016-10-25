package com.autodesk.shejijia.enterprise.common.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.autodesk.shejijia.shared.components.common.utility.Constants;

/**
 * Created by t_xuz on 10/13/16.
 * 获取网络请求接口的 url,单利类
 */
public class UrlHelper {

    //分页加载数据每页数据的个数
    public static final int PAGE_LIMIT = 30;

    private UrlHelper() {
    }

    private static class UrlHelperHolder {
        private static final UrlHelper INSTANCE = new UrlHelper();
    }

    public static UrlHelper getInstance() {
        return UrlHelperHolder.INSTANCE;
    }

    /*
    * 首页项目列表与我页全部项目列表对应的网络请求 url
    * 根据requestTag决定请求的url如何组合的
    * */
    public String getUserProjectListUrl(@NonNull String requestTag, @Nullable String findDate,
                                        @Nullable String status, boolean like, int offset) {
        String requestUrl = null;
        if (requestTag.equals(Constants.PROJECT_LIST_BY_DATE)) {
            requestUrl = Constants.BASE_URL + "/users/projects?"
                    + "findDate=" + findDate
                    + "&limit=" + PAGE_LIMIT
                    + "&offset=" + offset;
        } else if (requestTag.equals(Constants.PROJECT_LIST_BY_STATUS)) {
            requestUrl = Constants.BASE_URL + "/users/projects?"
                    + "status=" + status
                    + "&limit=" + PAGE_LIMIT
                    + "&offset=" + offset;
        } else if (requestTag.equals(Constants.PROJECT_LIST_BY_LIKE)) {
            requestUrl = Constants.BASE_URL + "/users/projects?"
                    + "findDate=" + findDate
                    + "&like=" + like
                    + "&limit=" + PAGE_LIMIT
                    + "&offset=" + offset;
        }
        return requestUrl;
    }

}
