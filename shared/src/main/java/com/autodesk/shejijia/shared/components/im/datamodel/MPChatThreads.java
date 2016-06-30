package com.autodesk.shejijia.shared.components.im.datamodel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MPChatThreads
{
    public int count;
    public ArrayList<MPChatThread> threads;

    public static MPChatThreads fromJSONString(String jsonString)
    {
        MPChatThreads entities = null;
        try
        {
            JSONObject jObj = new JSONObject(jsonString);
            entities = MPChatThreads.fromJSONObject(jObj);
        }
        catch(JSONException e)
        {
            entities = null;
        }

        return entities;
    }


    public static MPChatThreads fromJSONObject(JSONObject jObject)
    {
        if (jObject == null)
            return  null;

        MPChatThreads threads = new MPChatThreads();

        threads.threads = new ArrayList<MPChatThread>();
        try
        {
            threads.count = jObject.optInt("count");

            JSONArray jArray = jObject.optJSONArray("threads");
            for (int i = 0; i < jArray.length(); ++i)
                threads.threads.add(MPChatThread.fromJSONObject(jArray.optJSONObject(i)));
        }
        catch(Exception e)
        {
            threads = null;
        }

        return threads;
    }
}