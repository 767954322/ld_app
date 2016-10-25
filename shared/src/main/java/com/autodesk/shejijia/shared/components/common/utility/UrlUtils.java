package com.autodesk.shejijia.shared.components.common.utility;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by t_xuz on 10/13/16.
 * 获取网络请求接口的 url
 */
public final class UrlUtils {

    private UrlUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static String buildUrl(String baseUrl, Bundle requestParams) {

        if (requestParams == null) {
            return baseUrl;
        }

        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        for (String key : requestParams.keySet()) {
            urlBuilder.append(key + "=" + requestParams.get(key));
            urlBuilder.append("&");
        }

        if ('&' == urlBuilder.charAt(urlBuilder.length() - 1)) {
            urlBuilder.deleteCharAt(urlBuilder.length() - 1);
        }
        return urlBuilder.toString();
    }

}
