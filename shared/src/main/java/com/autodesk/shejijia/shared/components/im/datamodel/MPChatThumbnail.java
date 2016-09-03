package com.autodesk.shejijia.shared.components.im.datamodel;

import org.json.JSONException;
import org.json.JSONObject;

public class MPChatThumbnail
{
    public String caption;
    public String desc;
    public int file_id;
    public String thumb_path_prefix;
    public boolean is_primary;

    public static MPChatThumbnail fromJSONString(String jsonString)
    {
        MPChatThumbnail thumb = null;
        try
        {
            JSONObject jObject = new JSONObject(jsonString);
            thumb = MPChatThumbnail.fromJSONObject(jObject);
        }
        catch(JSONException e)
        {
            thumb = null;
        }

        return thumb;
    }


    public static MPChatThumbnail fromJSONObject(JSONObject jObject)
    {
        if (jObject == null)
            return  null;

        MPChatThumbnail info = new MPChatThumbnail();
        try
        {
            info.caption = jObject.optString("caption");
            info.desc = jObject.optString("desc");
            info.file_id = jObject.optInt("file_id");
            info.thumb_path_prefix = jObject.optString("thumb_path_prefix");
            info.is_primary = jObject.getBoolean("is_primary");
        }
        catch(JSONException e)
        {
            info = null;
        }

        return info;
    }
}