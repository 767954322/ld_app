package com.autodesk.shejijia.shared.components.common.utility;


import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.entity.microbean.MileStone;
import com.autodesk.shejijia.shared.components.common.entity.microbean.PlanInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-6-12 .
 * @file GsonUtil.java .
 * @brief Json和Bean对象互转的工具类 .
 */
public class GsonUtil {
    private GsonUtil() {
    }

    private static String userInfo;

    public static <T> T jsonToBean(String json, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

    public static <T, V> T jsonToBean(@NonNull String json, @NonNull Class<T> clazz,
                                      @NonNull Class<V> multiTypeClazz, @NonNull TypeAdapter<V> typeAdapter) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(multiTypeClazz, typeAdapter)
                .create();

        return gson.fromJson(json, clazz);
    }

    public static <T> T jsonToBean(@NonNull String json, @NonNull Class<T> clazz,
                                      @NonNull List<Class<?>> multiTypeList, List<TypeAdapter<?>> typeAdapterList) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        for (int i=0; i<multiTypeList.size(); i++) {
            gsonBuilder.registerTypeAdapter(multiTypeList.get(i), typeAdapterList.get(i));
        }
        
        return gsonBuilder.create().fromJson(json, clazz);
    }


    public static String jsonToString(org.json.JSONObject jsonObject) {
        try {
            userInfo = new String(jsonObject.toString().getBytes(Constant.NetBundleKey.ISO_8859_1), Constant.NetBundleKey.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    public static <T> JSONObject beanToJSONObject(T bean) throws JSONException {
        Gson gson = new Gson();
        String jsonString = gson.toJson(bean);
        return new JSONObject(jsonString);
    }

    public static <T> String beanToString(T bean) {
        Gson gson = new Gson();
        return gson.toJson(bean);
    }
}
