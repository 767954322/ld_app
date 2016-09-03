package com.autodesk.shejijia.shared.components.im.datamodel;

import org.json.JSONException;
import org.json.JSONObject;

public class MPChatEntity
{
    public String entity_type;
    public String entity_id;
    public MPChatConversations conversations;
    public MPChatEntityData entity_data;

    public static MPChatEntity fromJSONString(String jsonString)
    {
        MPChatEntity info = null;
        try
        {
            JSONObject jObject = new JSONObject(jsonString);
            info = MPChatEntity.fromJSONObject(jObject);
        }
        catch(JSONException e)
        {
            info = null;
        }

        return info;
    }


    public static MPChatEntity fromJSONObject(JSONObject jObject)
    {
        if (jObject == null)
            return  null;

        MPChatEntity entity = new MPChatEntity();
        try
        {
            entity.entity_type = jObject.optString("entity_type");
            entity.entity_id = jObject.optString("entity_id");
            entity.conversations = MPChatConversations.fromJSONArray(jObject.optJSONArray("conversations"));
            entity.entity_data = MPChatEntityData.fromJSONObject(jObject.optJSONObject("entity_data"));
        }
        catch(Exception e)
        {
            entity = null;
        }

        return entity;
    }
}
