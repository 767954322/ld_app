package com.autodesk.shejijia.shared.components.im.datamodel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MPChatThumbnails
{

    public ArrayList<MPChatThumbnail> thumbnails;


    public static MPChatThumbnails fromJSONString(String jsonString)
    {
        MPChatThumbnails cons = null;
        try
        {
            JSONArray jArray = new JSONArray(jsonString);
            cons = MPChatThumbnails.fromJSONArray(jArray);
        }
        catch(JSONException e)
        {
            cons = null;
        }

        return cons;
    }


    public static MPChatThumbnails fromJSONArray(JSONArray jArray)
    {
        if (jArray == null)
            return  null;

        MPChatThumbnails thumbs = new MPChatThumbnails();

        thumbs.thumbnails = new ArrayList<MPChatThumbnail>();
        try
        {
            for (int i = 0; i < jArray.length(); ++i)
                thumbs.thumbnails.add(MPChatThumbnail.fromJSONObject(jArray.optJSONObject(i)));
        }
        catch(Exception e)
        {
            thumbs = null;
        }

        return thumbs;
    }

}
