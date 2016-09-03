package com.autodesk.shejijia.shared.components.im.datamodel;

import org.json.JSONException;
import org.json.JSONObject;

public class MPProjectMaterial
{
    public Object parameters;
    public int type;

    public static MPProjectMaterial fromJSONString(String jsonString)
    {
        MPProjectMaterial material = new MPProjectMaterial();
        try
        {
            JSONObject jObject = new JSONObject(jsonString);
            material = MPProjectMaterial.fromJSONObject(jObject);
        }
        catch(JSONException e)
        {
            material = null;
        }

        return material;
    }


    public static MPProjectMaterial fromJSONObject(JSONObject jObject)
    {
        if (jObject == null)
            return  null;

        MPProjectMaterial material = new MPProjectMaterial();
        try
        {
            material.parameters = jObject.get("parameters");
            material.type = jObject.optInt("type");
        }
        catch(JSONException e)
        {
            material = null;
        }

        return material;
    }

}