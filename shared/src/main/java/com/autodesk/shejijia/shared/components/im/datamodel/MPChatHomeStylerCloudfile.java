package com.autodesk.shejijia.shared.components.im.datamodel;

import org.json.JSONException;
import org.json.JSONObject;

public class MPChatHomeStylerCloudfile
{
    public int uid;
    public String name;
    public String url;
    public String thumbnail;

    public static MPChatHomeStylerCloudfile fromJSONString(String jsonString)
    {
        MPChatHomeStylerCloudfile file = new MPChatHomeStylerCloudfile();
        try
        {
            JSONObject jObject = new JSONObject(jsonString);
            file = MPChatHomeStylerCloudfile.fromJSONObject(jObject);
        }
        catch(JSONException e)
        {
            file = null;
        }

        return file;
    }


    public static MPChatHomeStylerCloudfile fromJSONObject(JSONObject jObject)
    {
        if (jObject == null)
            return  null;

        MPChatHomeStylerCloudfile info = new MPChatHomeStylerCloudfile();
        try
        {
            info.uid = jObject.optInt("id");
            info.name = jObject.optString("name");
            info.url = jObject.optString("url");
            info.thumbnail = jObject.optString("file_thumbnail");
        }
        catch(Exception e)
        {
            info = null;
        }

        return info;
    }
}