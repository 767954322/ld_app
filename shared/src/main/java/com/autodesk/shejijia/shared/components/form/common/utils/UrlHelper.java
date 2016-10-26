package com.autodesk.shejijia.shared.components.form.common.utils;


/**
 * Created by t_panya on 16/10/24.
 */

public class UrlHelper {
    public static String bindFormUrl(String fid,boolean isWhole){
        StringBuilder sb = new StringBuilder(Constants.BASE_URL);
        sb.append("/forms")
                .append("/")
                .append("id")
                .append("\\?")
                .append("ids=")
                .append(fid);
        if(isWhole) {
            sb.append("&meta_data=true");
        }else {
            sb.append("&meta_data=false");
        }
        return sb.toString();
    }

    public static String transArray2String(String[] fids){
        StringBuilder sbFormat = new StringBuilder();
        for(int i = 0 ; i < fids.length ; i++){
            if(i == fids.length -1){
                sbFormat.append(fids[i]);
            }else {
                sbFormat.append(fids[i] + ",");
            }
        }
        return sbFormat.toString();
    }

    public static String bindFormGetUrl(String[] fids){
        StringBuilder sb = new StringBuilder(Constants.BASE_URL);
        sb.append("/forms")
                .append("/")
                .append("id")
                .append("\\?")
                .append("ids=")
                .append(transArray2String(fids))
                .append("&meta_data=false");
        return sb.toString();
    }

    public static String bindFormPutUrl(){
        return Constants.BASE_URL + "/forms";
    }
}
