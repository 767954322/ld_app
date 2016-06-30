package com.autodesk.shejijia.consumer.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;

import com.autodesk.shejijia.consumer.base.bean.AreaBean;
import com.autodesk.shejijia.consumer.base.bean.RoomBean;
import com.autodesk.shejijia.consumer.base.bean.LivingRoomBean;
import com.autodesk.shejijia.consumer.base.bean.SpaceBean;
import com.autodesk.shejijia.consumer.base.bean.StyleBean;
import com.autodesk.shejijia.consumer.base.bean.ToiletBean;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
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
        String livingRoomJSON = loadJSONFromAsset(activity, fileName);
        RoomBean livingRoom = new Gson().fromJson(livingRoomJSON, RoomBean.class);
        Map<String, String> livingRoomMap = new HashMap<>();
        livingRoomMap.put("one", livingRoom.getOne());
        livingRoomMap.put("two", livingRoom.getTwo());
        livingRoomMap.put("three", livingRoom.getThree());
        livingRoomMap.put("four", livingRoom.getFour());
        livingRoomMap.put("five", livingRoom.getFive());
        livingRoomMap.put("loft", livingRoom.getLoft());
        livingRoomMap.put("multiple", livingRoom.getMultiple());
        livingRoomMap.put("villa", livingRoom.getVilla());
        livingRoomMap.put("other", livingRoom.getOther());
        return livingRoomMap;
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
        String roomHallJSON = loadJSONFromAsset(activity, fileName);
        LivingRoomBean room = new Gson().fromJson(roomHallJSON, LivingRoomBean.class);
        Map<String, String> roomMap = new HashMap<>();
        roomMap.put("one", room.getOne());
        roomMap.put("two", room.getTwo());
        roomMap.put("three", room.getThree());
        roomMap.put("four", room.getFour());
        roomMap.put("five", room.getFive());
        return roomMap;

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
        String toiletJSON = loadJSONFromAsset(activity, fileName);
        ToiletBean toilet = new Gson().fromJson(toiletJSON, ToiletBean.class);
        Map<String, String> toiletMap = new HashMap<>();
        toiletMap.put("one", toilet.getOne());
        toiletMap.put("two", toilet.getTwo());
        toiletMap.put("three", toilet.getThree());
        toiletMap.put("four", toilet.getFour());
        toiletMap.put("five", toilet.getFive());
        return toiletMap;

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
        String styleJSON = loadJSONFromAsset(activity, fileName);
        StyleBean styleBean = new Gson().fromJson(styleJSON, StyleBean.class);
        Map<String, String> styleMap = new HashMap<>();
        styleMap.put("Japan", styleBean.getJapan());
        styleMap.put("korea", styleBean.getKorea());
        styleMap.put("Mashup", styleBean.getMashup());
        styleMap.put("european", styleBean.getEuropean());
        styleMap.put("chinese", styleBean.getChinese());
        styleMap.put("neoclassical", styleBean.getNeoclassical());
        styleMap.put("ASAN", styleBean.getASAN());
        styleMap.put("US", styleBean.getUS());
        styleMap.put("country", styleBean.getCountry());
        styleMap.put("modern", styleBean.getModern());
        styleMap.put("mediterranean", styleBean.getMediterranean());
        styleMap.put("other", styleBean.getOther());

        return styleMap;
    }

    /**
     * one : 60㎡以下
     * two : 60-80㎡
     * three : 80-120㎡
     * five : 120㎡以上
     */
    public static Map<String, String> getArea(Activity activity) {
        String fileName = Constant.JsonLocationKey.AREA_JSON;
        String areaJSON = loadJSONFromAsset(activity, fileName);
        AreaBean areaBean = new Gson().fromJson(areaJSON, AreaBean.class);
        Map<String, String> areaMap = new HashMap<>();
        areaMap.put("one", areaBean.getOne());
        areaMap.put("two", areaBean.getTwo());
        areaMap.put("three", areaBean.getThree());
        areaMap.put("five", areaBean.getFive());
        return areaMap;
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
        String spaceJSON = loadJSONFromAsset(activity, fileName);
        SpaceBean spaceBean = new Gson().fromJson(spaceJSON, SpaceBean.class);
        Map<String, String> spaceMap = new HashMap<>();
        spaceMap.put("house", spaceBean.getHouse());
        spaceMap.put("catering", spaceBean.getCatering());
        spaceMap.put("office", spaceBean.getOffice());
        spaceMap.put("hotel", spaceBean.getHotel());
        spaceMap.put("business", spaceBean.getBusiness());
        spaceMap.put("entertainment", spaceBean.getEntertainment());
        spaceMap.put("leisure", spaceBean.getLeisure());
        spaceMap.put("healthcare", spaceBean.getHealthcare());
        spaceMap.put("sale", spaceBean.getSale());
        spaceMap.put("finace", spaceBean.getFinace());
        spaceMap.put("sport", spaceBean.getSport());
        spaceMap.put("education", spaceBean.getEducation());
        spaceMap.put("other", spaceBean.getOther());
        return spaceMap;
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

    public static List<Map<String, String>> setData(String str) {
        try {
            List<Map<String, String>> data = new ArrayList<>();
            JSONArray array = new JSONArray(str);
            int len = array.length();
            Map<String, String> map;
            for (int i = 0; i < len; i++) {
                JSONObject object = array.getJSONObject(i);
                map = new HashMap<>();
                map.put("operator", object.getString("operator"));
                map.put("loginDate", object.getString("loginDate"));
                map.put("logoutDate", object.getString("logoutDate"));
                data.add(map);
            }
            return data;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static List<Map<String, String>> setListData(String str) {
        List<Map<String, String>> data = new ArrayList<>();
        try {

            JSONArray array = new JSONArray(str);
            int len = array.length();
            Map<String, String> map;
            for (int i = 0; i < len; i++) {
                JSONObject object = array.getJSONObject(i);
                map = new HashMap<>();
                map.put("imageId", object.getString("imageId"));
                map.put("title", object.getString("title"));
                map.put("subTitle", object.getString("subTitle"));
                map.put("type", object.getString("type"));
                data.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 获取应标类型订单消费者节点
     *
     * @param context
     * @return
     * @throws JSONException
     */
    public static JSONObject getCurrentSubNodeNameArrayConsumer(Context context) throws JSONException {
        String filename = Constant.WkFlowStatePath.WF_BID_SUB_NODE_ID_NAME_CONSUMER;
        return AppJsonFileReader.getXmlJsonObject(context, filename);
    }

    public static JSONObject getCurrentNodeArrayDesigner(Context context) throws JSONException {
        String filename = Constant.WkFlowStatePath.WF_NODE_ID_NAME_DESIGNER;
        return AppJsonFileReader.getXmlJsonObject(context, filename);
    }

    public static JSONObject getCurrentSubNodeArrayDesigner(Context context) throws JSONException {
        String filename = Constant.WkFlowStatePath.WF_BID_SUB_NODE_ID_NAME_DESIGNER;
        return AppJsonFileReader.getXmlJsonObject(context, filename);
    }

    public static JSONObject getCurrentNodeArrayConsumer(Context context) throws JSONException {
        String filename = Constant.WkFlowStatePath.WF_NODE_ID_NAME_CONSUMER;
        return AppJsonFileReader.getXmlJsonObject(context, filename);
    }


    public static JSONObject getXmlJsonObject(Context context, String filename) throws JSONException {
        String areaJSON = loadJSONFromAsset(context, filename);
        JSONObject jsonObject = new JSONObject(areaJSON);
        return jsonObject;
    }

}
