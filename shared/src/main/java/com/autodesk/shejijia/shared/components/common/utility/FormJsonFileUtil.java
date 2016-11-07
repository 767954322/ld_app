package com.autodesk.shejijia.shared.components.common.utility;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Log;

import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;

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
 * Created by t_panya on 16/10/19.
 */

public class FormJsonFileUtil {
    private static final String FILE_SEPARATE = "/";

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
            if (null != context) {

                InputStream is = context.getAssets().open(fileName);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    public static JSONObject loadJSONDataFromAsset(Context context, String fileName) {
        JSONObject jsonObject = null;
        try {
            if (null != context) {

                InputStream is = context.getAssets().open(fileName);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                String json = new String(buffer, "UTF-8");
                jsonObject = new JSONObject(json);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return jsonObject;
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

            if (!TextUtils.isEmpty(jsonString)) {
                jsonObject = new JSONObject(jsonString);
            }

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

    public static void getExactlyJSONFile(Context context,String path){
        Map<String,String> fileMap = new HashMap<>();
        try {
            String[] fileList = context.getAssets().list(path);
            if(fileList.length > 0){        //目录
                for(String string:fileList){
                    path = path + FILE_SEPARATE + string;
                    Log.i("TAG", "getExactlyJSONFile: " + path);
                    getExactlyJSONFile(context,path);
                    path = path.substring(0,path.lastIndexOf(FILE_SEPARATE));
                }
            }else {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List jsonArray2ModelList(JSONArray jsonArray,Class clazz){
        if(jsonArray == null){
            return null;
        }
        List result = new ArrayList();
        for(int i=0; i<jsonArray.length(); i++){
            try{
                if(jsonArray.get(i) instanceof JSONObject){
                    result.add(json2Bean((jsonArray.get(i).toString()),clazz));
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return result;
    }


    public static <T> T json2Bean(String jsonString,Class<T> clazz){
        return GsonUtil.jsonToBean(jsonString,clazz);
    }

}
