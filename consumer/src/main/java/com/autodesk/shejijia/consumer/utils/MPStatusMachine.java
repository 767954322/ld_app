package com.autodesk.shejijia.consumer.utils;


import android.content.Context;

import com.autodesk.shejijia.shared.framework.AdskApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author   Malidong .
 * @version  v1.0 .
 * @date       2016-6-13 .
 * @file          MPStatusMachine.java .
 * @brief        .
 */
public class MPStatusMachine {


    static public final int NODE_CONFIRM_MEANSURE = 1;
    static public final int NODE__MEANSURE_PAY = 2;
    static public final int NODE__DESIGN_CONTRACT = 3;
    static public final int NODE__DESIGN_FIRST_PAY = 4;
    static public final int NODE__DESIGN_BALANCE_PAY = 5;
    static public final int NODE__DESIGN_DESIGN_DELIVERY = 6;

    public static String GetCurrentNodeName() {
        Context context = AdskApplication.getInstance().getApplicationContext();

        try {
            //
            JSONObject statusNameDefine = AppJsonFileReader.getCurrentNodeArrayDesigner(context);
            // JSONObject statusNameDefine=AppJsonFileReader.getCurrentNodeArrayConsumer(context);

            JSONArray jsonArray = statusNameDefine.getJSONArray("Root");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jWorkFlow = jsonArray.getJSONObject(i);
                String id = jWorkFlow.getString("wk_template_id");
                if (id.equals("1")) {
                    JSONArray jsonSteps = jWorkFlow.getJSONArray("wk_cur_node");
                    for (int z = 0; z < jsonSteps.length(); z++) {
                        JSONObject jStep = jsonSteps.getJSONObject(z);
                        if (true)
                            return jStep.getString("nName");
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String GetCurrntSubNodeName() {

        try {
            JSONObject statusNameDefine = AppJsonFileReader.getCurrentSubNodeNameArrayConsumer(AdskApplication.getInstance().getApplicationContext());
            //JSONObject statusNameDefine=AppJsonFileReader.getCurrentSubNodeNameArrayDesigner(AdskApplication.getInstance().getApplicationContext());
            JSONArray jsonArray = statusNameDefine.getJSONArray("Root");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jSubNode = jsonArray.getJSONObject(i);
                String sub_id = jSubNode.getString("wk_cur_sub_node_id");
                if (sub_id.equals("11")) {
                    return jSubNode.getString("wk_cur_sub_name");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static ArrayList<JSONObject> GetCurrntSubNodeMessage() {
        ArrayList<JSONObject> list = new ArrayList<>();
        try {
            JSONObject statusNameDefine = AppJsonFileReader.getCurrentSubNodeNameArrayConsumer(AdskApplication.getInstance().getApplicationContext());
            //JSONObject statusNameDefine=AppJsonFileReader.getCurrentSubNodeNameArrayDesigner(AdskApplication.getInstance().getApplicationContext());
            JSONArray jsonArray = statusNameDefine.getJSONArray("Root");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jSubNode = jsonArray.getJSONObject(i);
                String sub_id = jSubNode.getString("wk_cur_sub_node_id");
                if (sub_id.equals("11")) {
                    JSONArray jsonMsgs = jSubNode.getJSONArray("detail");
                    for (int z = 0; z < jsonMsgs.length(); z++) {
                        JSONObject jMsg = jsonMsgs.getJSONObject(z);
                        list.add(jMsg);
                    }
                    return list;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
