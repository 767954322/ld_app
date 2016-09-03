package com.autodesk.shejijia.shared.components.im.datamodel;

import org.json.JSONException;
import org.json.JSONObject;

public class MPChatEntityInfo
{
    public String entity_type;
    public String entity_id;
    public MPChatEntityData entity_data;
    public String date_submitted;

    public int x_coordinate;
    public int y_coordinate;
    public int z_coordinate;

    public static MPChatEntityInfo fromJSONString(String jsonString)
    {
        MPChatEntityInfo info = new MPChatEntityInfo();
        try
        {
            JSONObject jObject = new JSONObject(jsonString);
            info = MPChatEntityInfo.fromJSONObject(jObject);
        }
        catch(JSONException e)
        {
            info = null;
        }

        return info;
    }


    public static MPChatEntityInfo fromJSONObject(JSONObject jObject)
    {
        if (jObject == null)
            return  null;

        MPChatEntityInfo info = new MPChatEntityInfo();
        try
        {
            info.entity_type = jObject.optString("entity_type");
            info.entity_id = jObject.optString("entity_id");
            info.entity_data = MPChatEntityData.fromJSONObject(jObject.optJSONObject("entity_data"));
            info.date_submitted = jObject.optString("date_submitted");

            if(isFileEntity(info.entity_type))
            {
                info.x_coordinate = jObject.optInt("x_coordinate");
                info.y_coordinate = jObject.optInt("y_coordinate");
                info.z_coordinate = jObject.optInt("z_coordinate");
            }
        }
        catch(Exception e)
        {
            info = null;
        }

        return info;
    }

    private static boolean isFileEntity(String entity_type)
    {
        return entity_type.equalsIgnoreCase("FILE");
    }
}