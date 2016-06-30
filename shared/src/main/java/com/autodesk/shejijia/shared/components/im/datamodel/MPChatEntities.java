package com.autodesk.shejijia.shared.components.im.datamodel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MPChatEntities
{
    public ArrayList<MPChatEntity> entities;

    public static MPChatEntities fromJSONString(String jsonString)
    {
        MPChatEntities entities = null;
        try
        {
            JSONObject jObj = new JSONObject(jsonString);
            entities = MPChatEntities.fromJSONObject(jObj);
        }
        catch(JSONException e)
        {
            entities = null;
        }

        return entities;
    }


    public static MPChatEntities fromJSONObject(JSONObject jObj)
    {
        if (jObj == null)
            return  null;

        MPChatEntities entities = new MPChatEntities();

        JSONArray jArray = jObj.optJSONArray("entity");
        entities.entities = new ArrayList<MPChatEntity>();
        try
        {
            for (int i = 0; i < jArray.length(); ++i)
                entities.entities.add(MPChatEntity.fromJSONObject(jArray.optJSONObject(i)));
        }
        catch(Exception e)
        {
            entities = null;
        }

        return entities;
    }
}