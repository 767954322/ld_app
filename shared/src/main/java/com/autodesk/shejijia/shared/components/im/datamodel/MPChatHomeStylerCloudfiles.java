package com.autodesk.shejijia.shared.components.im.datamodel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MPChatHomeStylerCloudfiles
{
    public ArrayList<MPChatHomeStylerCloudfile> files;

     public static MPChatHomeStylerCloudfiles fromJSONString(String jsonString)
        {
            MPChatHomeStylerCloudfiles files = null;
            try
            {
                JSONObject jObj = new JSONObject(jsonString);
                files = MPChatHomeStylerCloudfiles.fromJSONObject(jObj);
            }
            catch(JSONException e)
            {
                files = null;
            }

            return files;
        }


        public static MPChatHomeStylerCloudfiles fromJSONObject(JSONObject obj) {
            if (obj == null)
                return  null;

            JSONArray jArray = obj.optJSONArray("cloud_files");

            if (jArray == null)
                return null;

            MPChatHomeStylerCloudfiles files = new MPChatHomeStylerCloudfiles();

            files.files = new ArrayList<MPChatHomeStylerCloudfile>();
            try
            {
                for (int i = 0; i < jArray.length(); ++i)
                    files.files.add(MPChatHomeStylerCloudfile.fromJSONObject(jArray.optJSONObject(i)));
            }
            catch(Exception e)
            {
                files = null;
            }

            return files;
        }

}
