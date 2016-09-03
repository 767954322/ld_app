package com.autodesk.shejijia.shared.components.im.datamodel;

import org.json.JSONException;
import org.json.JSONObject;

public class MPChatCommandInfo {
    public String consumer_id;
    public String designer_id;
    public String for_consumer;
    public String for_designer;
    public String need_id;
    public String sender;
    public String sub_node_id;

    public static MPChatCommandInfo fromJSONString(String jsonString) {
        MPChatCommandInfo info = null;
        try {
            JSONObject jObject = new JSONObject(jsonString);
            info = MPChatCommandInfo.fromJSONObject(jObject);
        } catch (JSONException e) {
            info = null;
        }

        return info;
    }


    public static MPChatCommandInfo fromJSONObject(JSONObject jObject) {
        if (jObject == null)
            return null;

        MPChatCommandInfo info = new MPChatCommandInfo();
        try {
            info.consumer_id = jObject.optString("consumer_id");
            info.designer_id = jObject.optString("designer_id");
            info.for_consumer = jObject.optString("for_consumer");
            info.for_designer = jObject.optString("for_designer");
            info.need_id = jObject.optString("need_id");
            info.sender = jObject.optString("sender");
            info.sub_node_id = jObject.optString("sub_node_id");
        } catch (Exception e) {
            info = null;
        }

        return info;
    }
}