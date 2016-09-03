package com.autodesk.shejijia.shared.components.im.datamodel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MPChatRecipients
{
    public ArrayList<MPChatUser> users;

    public static MPChatRecipients fromJSONString(String jsonString)
    {
        MPChatRecipients cons = null;
        try
        {
            JSONArray jArray = new JSONArray(jsonString);
            cons = MPChatRecipients.fromJSONArray(jArray);
        }
        catch(JSONException e)
        {
            cons = null;
        }

        return cons;
    }


    public static MPChatRecipients fromJSONArray(JSONArray jArray)
    {
        if (jArray == null)
            return  null;

        MPChatRecipients recipients = new MPChatRecipients();

        recipients.users = new ArrayList<MPChatUser>();
        try
        {
            for (int i = 0; i < jArray.length(); ++i)
                recipients.users.add(MPChatUser.fromJSONObject(jArray.optJSONObject(i)));
        }
        catch(Exception e)
        {
            recipients = null;
        }

        return recipients;
    }
}