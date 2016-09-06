package com.autodesk.shejijia.consumer.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;

import com.autodesk.shejijia.shared.components.common.appglobal.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author xueqiudong .
 * @version v1.0 .
 * @date 2016-6-13 .
 * @file AppJsonFileReader.java .
 * @brief json文件对应集合的工具类 .
 */
public class AppJsonFileReader {
    private AppJsonFileReader() {
    }

    public static String loadJSONFromAsset(Activity activity, String fileName) {
        String json = null;
        try {
            InputStream is = activity.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    /**
     * one : 一室
     * two : 两室
     * three : 三室
     * four : 四室
     * five : 五室
     * loft : LOFT
     * multiple : 复式
     * villa : 别墅
     * other: "其他",
     *
     * @return
     */
    public static Map<String, String> getRoomHall(Activity activity) {
        String fileName = Constant.JsonLocationKey.LIVING_ROOM_JSON;
        JSONObject xmlJsonObject = getXmlJsonObject(activity, fileName);
        return jsonObj2Map(xmlJsonObject);
    }

    /**
     * one : 一厅
     * two : 两厅
     * three : 三厅
     * four : 四厅
     * five : 五厅
     */
    public static Map<String, String> getLivingRoom(Activity activity) {
        String fileName = Constant.JsonLocationKey.ROOM_JSON;
        JSONObject xmlJsonObject = getXmlJsonObject(activity, fileName);
        return jsonObj2Map(xmlJsonObject);
    }

    /**
     * one : 一卫
     * two : 两卫
     * three : 三卫
     * four : 四卫
     * five : 五卫
     */
    public static Map<String, String> getToilet(Activity activity) {
        String fileName = Constant.JsonLocationKey.TOILET_JSON;
        JSONObject xmlJsonObject = getXmlJsonObject(activity, fileName);
        return jsonObj2Map(xmlJsonObject);
    }

    /**
     * Japan : 日式
     * Korea : 韩式
     * Mashup : 混搭
     * european : 欧式
     * chinese : 中式
     * neoclassical : 新古典
     * ASAN : 东南亚
     * US : 美式
     * country : 田园
     * modern : 现代
     * "mediterranean": "地中海",
     * other : 其他
     */
    public static Map<String, String> getStyle(Activity activity) {
        String fileName = Constant.JsonLocationKey.STYLE_JSON;
        JSONObject xmlJsonObject = getXmlJsonObject(activity, fileName);
        return jsonObj2Map(xmlJsonObject);
    }

    /**
     * 从业年限
     *
     * @param activity
     * @return
     */
    public static Map<String, String> getWorkingTime(Activity activity) {
        String fileName = Constant.JsonLocationKey.SEARCH_WORKING_TIME;
        JSONObject xmlJsonObject = getXmlJsonObject(activity, fileName);
        return jsonObj2Map(xmlJsonObject);
    }

    /**
     * 价格
     *
     * @param activity
     * @return
     */
    public static Map<String, String> getPrice(Activity activity) {
        String fileName = Constant.JsonLocationKey.SEARCH_PRICE;
        JSONObject xmlJsonObject = getXmlJsonObject(activity, fileName);
        return jsonObj2Map(xmlJsonObject);
    }

    /**
     * one : 60㎡以下
     * two : 60-80㎡
     * three : 80-120㎡
     * five : 120㎡以上
     */
    public static Map<String, String> getArea(Activity activity) {
        String fileName = Constant.JsonLocationKey.AREA_JSON;
        JSONObject xmlJsonObject = getXmlJsonObject(activity, fileName);
        return jsonObj2Map(xmlJsonObject);
    }

    /**
     * house : 住宅空间
     * catering : 餐饮空间
     * office : 办公空间
     * hotel : 酒店空间
     * business : 商业展示
     * entertainment : 娱乐空间
     * leisure : 休闲场所
     * culture : 文化空间
     * healthcare :  医疗机构
     * sale :  售楼中心
     * finace : 金融场所
     * sport :  运动场所
     * education : 教育机构
     */
    public static Map<String, String> getSpace(Activity activity) {
        String fileName = Constant.JsonLocationKey.SPACE_JSON;
        JSONObject xmlJsonObject = getXmlJsonObject(activity, fileName);
        return jsonObj2Map(xmlJsonObject);
    }


    public static String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    /**
     * 将JsonString 转成JsonObject
     *
     * @param context  上下文对象
     * @param filename json文件asset名字
     * @return JSONObject
     */
    public static JSONObject getXmlJsonObject(Context context, String filename) {
        String jsonString = loadJSONFromAsset(context, filename);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * JsonObject转成集合Map
     *
     * @param jsonObject
     * @return
     */
    public static Map jsonObj2Map(JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }

        Map result = new HashMap();
        Object key, value = null;
        Iterator keyIterator = jsonObject.keys();
        while (keyIterator.hasNext()) {
            key = keyIterator.next();
            try {
                value = jsonObject.get(key.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (value instanceof JSONObject) {
                result.put(key, jsonObj2Map((JSONObject) value));
            } else if (value instanceof JSONArray) {
                result.put(key, jsonArray2List((JSONArray) value));
            } else {
                result.put(key, value);
            }
        }

        return result;
    }

    /**
     * JsonArray转成集合List
     *
     * @param jsonArray
     * @return
     */
    public static List jsonArray2List(JSONArray jsonArray) {
        if (jsonArray == null) {
            return null;
        }

        List result = new ArrayList();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                if (jsonArray.get(i) instanceof JSONObject) {
                    result.add(jsonObj2Map((JSONObject) jsonArray.get(i)));
                } else if (jsonArray.get(i) instanceof JSONArray) {
                    result.add(jsonArray2List((JSONArray) jsonArray.get(i)));
                } else {
                    result.add(jsonArray.get(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
