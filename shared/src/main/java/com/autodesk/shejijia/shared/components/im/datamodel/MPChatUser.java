package com.autodesk.shejijia.shared.components.im.datamodel;

import org.json.JSONException;
import org.json.JSONObject;

public class MPChatUser
{
    public String name;
    public String user_id;
    public String profile_image;

    public static MPChatUser fromJSONString(String jsonString)
    {
        MPChatUser msg = new MPChatUser();
        try
        {
            JSONObject jObject = new JSONObject(jsonString);
            msg = MPChatUser.fromJSONObject(jObject);
        }
        catch(JSONException e)
        {
            msg = null;
        }

        return msg;
    }


    public static MPChatUser fromJSONObject(JSONObject jObject)
    {
        if (jObject == null)
            return  null;

        MPChatUser user = new MPChatUser();
        try
        {
            user.name = jObject.optString("name");
            user.user_id = jObject.optString("id");
            user.profile_image = jObject.optString("profile_image");
        }
        catch(Exception e)
        {
            user = null;
        }

        return user;
    }

}