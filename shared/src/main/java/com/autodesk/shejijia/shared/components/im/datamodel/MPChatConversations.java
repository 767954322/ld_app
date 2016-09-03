package com.autodesk.shejijia.shared.components.im.datamodel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MPChatConversations
{
    public ArrayList<MPChatConversation> conversations;


    public static MPChatConversations fromJSONString(String jsonString)
    {
        MPChatConversations cons = null;
        try
        {
            JSONArray jArray = new JSONArray(jsonString);
            cons = MPChatConversations.fromJSONArray(jArray);
        }
        catch(JSONException e)
        {
            cons = null;
        }

        return cons;
    }


    public static MPChatConversations fromJSONArray(JSONArray jArray)
    {
        if (jArray == null)
            return  null;

        MPChatConversations cons = new MPChatConversations();

        cons.conversations = new ArrayList<MPChatConversation>();
        try
        {
            for (int i = 0; i < jArray.length(); ++i)
                cons.conversations.add(MPChatConversation.fromJSONObject(jArray.optJSONObject(i)));
        }
        catch(Exception e)
        {
            cons = null;
        }

        return cons;
    }

}