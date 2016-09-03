package com.autodesk.shejijia.shared.components.im.datamodel;

import org.json.JSONException;
import org.json.JSONObject;

public class MPChatConversation
{
    public String thread_id;
    public int total_message_count;
    public int unread_message_count;
    public String date_submitted;
    public MPChatMessage latest_message;

    public int x_coordinate;
    public int y_coordinate;
    public int z_coordinate;


    public static MPChatConversation fromJSONString(String jsonString)
    {
        MPChatConversation con = new MPChatConversation();
        try
        {
            JSONObject jObject = new JSONObject(jsonString);
            con = MPChatConversation.fromJSONObject(jObject);
        }
        catch(JSONException e)
        {
            con = null;
        }

        return con;
    }


    public static MPChatConversation fromJSONObject(JSONObject jObject)
    {
        if (jObject == null)
            return  null;

        MPChatConversation con = new MPChatConversation();
        try
        {
            con.thread_id = jObject.optString("thread_id");
            con.total_message_count = jObject.optInt("total_message_count");
            con.unread_message_count = jObject.optInt("unread_message_count");
            con.date_submitted = jObject.optString("date_submitted");
            con.latest_message = MPChatMessage.fromJSONObject(jObject.optJSONObject("latest_message"));
            con.x_coordinate = jObject.optInt("x_coordinate");
            con.y_coordinate = jObject.optInt("y_coordinate");
            con.z_coordinate = jObject.optInt("z_coordinate");
        }
        catch(Exception e)
        {
            con = null;
        }

        return con;
    }
}