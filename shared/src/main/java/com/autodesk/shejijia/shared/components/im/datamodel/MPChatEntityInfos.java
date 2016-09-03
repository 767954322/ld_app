package com.autodesk.shejijia.shared.components.im.datamodel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MPChatEntityInfos
{
    public ArrayList<MPChatEntityInfo> entityInfos;

    public static MPChatEntityInfos fromJSONString(String jsonString)
    {
        MPChatEntityInfos infos = null;
        try
        {
            JSONArray jArray = new JSONArray(jsonString);
            infos = MPChatEntityInfos.fromJSONArray(jArray);
        }
        catch (JSONException e)
        {
            infos = null;
        }

        return infos;
    }


    public static MPChatEntityInfos fromJSONArray(JSONArray jArray)
    {
        if (jArray == null)
            return null;

        MPChatEntityInfos infos = new MPChatEntityInfos();

        infos.entityInfos = new ArrayList<MPChatEntityInfo>();
        try
        {
            for (int i = 0; i < jArray.length(); ++i)
                infos.entityInfos.add(MPChatEntityInfo.fromJSONObject(jArray.optJSONObject(i)));
        }
        catch (Exception e)
        {
            infos = null;
        }

        return infos;
    }
}