package com.autodesk.shejijia.shared.components.im.datamodel;

import org.json.JSONException;
import org.json.JSONObject;

public class MPChatThread
{
    public String subject;
    public String thread_id;
    public MPChatRecipients recipients;
    public int total_message_count;
    public int unread_message_count;
    public MPChatUser sender;
    public String created_date;
    public MPChatEntityInfos entity;
    public MPChatMessage latest_message;

    public static MPChatThread fromJSONString(String jsonString)
    {
        MPChatThread thread = new MPChatThread();
        try
        {
            JSONObject jObject = new JSONObject(jsonString);
            thread = MPChatThread.fromJSONObject(jObject);
        }
        catch (JSONException e)
        {
            thread = null;
        }

        return thread;
    }


    public static MPChatThread fromJSONObject(JSONObject jObject)
    {
        if (jObject == null)
            return null;

        MPChatThread thread = new MPChatThread();
        try
        {
            thread.subject = jObject.optString("subject");
            thread.thread_id = jObject.optString("thread_id");
            thread.recipients = MPChatRecipients.fromJSONArray(jObject.optJSONArray("recipients"));
            thread.total_message_count = jObject.optInt("total_message_count");
            thread.unread_message_count = jObject.optInt("unread_message_count");
            thread.sender = MPChatUser.fromJSONObject(jObject.optJSONObject("sender"));
            thread.created_date = jObject.optString("created_date");
            thread.entity = MPChatEntityInfos.fromJSONArray(jObject.optJSONArray("entity"));
            thread.latest_message = MPChatMessage.fromJSONObject(jObject.optJSONObject("latest_message"));
        }
        catch (Exception e)
        {
            thread = null;
        }

        return thread;
    }
}