package com.autodesk.shejijia.shared.components.im.datamodel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MPChatMessages
{
    public int offset;
    public int numMessages;
    public ArrayList<MPChatMessage> messages;

    public static MPChatMessages fromJSONString(String jsonString)
    {
        MPChatMessages entities = null;
        try
        {
            JSONObject jObj = new JSONObject(jsonString);
            entities = MPChatMessages.fromJSONObject(jObj);
        }
        catch(JSONException e)
        {
            entities = null;
        }

        return entities;
    }


    public static MPChatMessages fromJSONObject(JSONObject jObject)
    {
        if (jObject == null)
            return  null;

        MPChatMessages msgs = new MPChatMessages();

        msgs.messages = new ArrayList<MPChatMessage>();
        try
        {
            msgs.offset = jObject.optInt("offset");
            msgs.numMessages = jObject.optInt("count");

            JSONArray jArray = jObject.optJSONArray("messages");
            if (jArray != null)
            {
                for (int i = 0; i < jArray.length(); ++i)
                    msgs.messages.add(MPChatMessage.fromJSONObject(jArray.optJSONObject(i)));
            }
        }
        catch(Exception e)
        {
            msgs = null;
        }

        return msgs;
    }
}