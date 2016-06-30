package com.autodesk.shejijia.shared.components.im.datamodel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MPProjectMaterials {
    public ArrayList<MPProjectMaterial> materials;

    public static MPProjectMaterials fromJSONString(String jsonString) {
        MPProjectMaterials mats = null;
        try {
            JSONArray jArray = new JSONArray(jsonString);
            mats = MPProjectMaterials.fromJSONArray(jArray);
        } catch (JSONException e) {
            mats = null;
        }

        return mats;
    }


    public static MPProjectMaterials fromJSONArray(JSONArray jArray) {
        if (jArray == null)
            return null;

        MPProjectMaterials mats = new MPProjectMaterials();

        mats.materials = new ArrayList<MPProjectMaterial>();
        try {
            for (int i = 0; i < jArray.length(); ++i)
                mats.materials.add(MPProjectMaterial.fromJSONObject(jArray.optJSONObject(i)));
        } catch (Exception e) {
            mats = null;
        }

        return mats;
    }
}
