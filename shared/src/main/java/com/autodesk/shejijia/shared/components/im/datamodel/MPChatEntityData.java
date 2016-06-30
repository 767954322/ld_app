package com.autodesk.shejijia.shared.components.im.datamodel;

import org.json.JSONException;
import org.json.JSONObject;

public class MPChatEntityData
{
    public String asset_name;
    public int asset_id;
    public MPChatThumbnails thumbnails;
    public String public_file_url;
    public String workflow_step_name;

    public static MPChatEntityData fromJSONString(String jsonString)
    {
        MPChatEntityData info = null;
        try
        {
            JSONObject jObject = new JSONObject(jsonString);
            info = MPChatEntityData.fromJSONObject(jObject);
        }
        catch(JSONException e)
        {
            info = null;
        }

        return info;
    }


    public static MPChatEntityData fromJSONObject(JSONObject jObject)
    {
        if (jObject == null)
            return  null;

        MPChatEntityData data = new MPChatEntityData();
        try
        {
            data.asset_name = jObject.optString("asset_name");
            data.asset_id = jObject.optInt("asset_id");
            data.thumbnails = MPChatThumbnails.fromJSONArray(jObject.optJSONArray("thumbnails"));
            data.public_file_url = jObject.optString("public_file_url");
            data.workflow_step_name = jObject.optString("workflow_step_name");
        }
        catch(Exception e)
        {
            data = null;
        }

        return data;
    }
}