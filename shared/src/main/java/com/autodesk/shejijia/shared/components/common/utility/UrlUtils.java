package com.autodesk.shejijia.shared.components.common.utility;

import android.os.Bundle;

import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;

import java.util.List;

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

    public static String transArray2String(List<String> formIds){
        StringBuilder sbFormat = new StringBuilder();
        if(formIds == null || formIds.size() == 0){
            return null;
        }
        for(int i = 0 ; i < formIds.size() ; i++){
            sbFormat.append(formIds.get(i) + ",");
        }
        if(',' == sbFormat.charAt(sbFormat.length() - 1)){
            sbFormat.deleteCharAt(sbFormat.length() - 1);
        }
        return sbFormat.toString();
    }

    public static String bindFormGetUrl(List<String> formIds){
        if(formIds == null || formIds.size() == 0){
            return null;
        }
        StringBuilder sbFormUrl = new StringBuilder(ConstructionConstants.BASE_URL);
        sbFormUrl.append("/forms/")
                .append("ids?ids=")
                .append(transArray2String(formIds))
                .append("&meta_data=false");
        return sbFormUrl.toString();
    }
}
