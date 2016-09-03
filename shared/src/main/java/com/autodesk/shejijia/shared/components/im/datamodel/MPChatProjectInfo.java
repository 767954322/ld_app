package com.autodesk.shejijia.shared.components.im.datamodel;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MPChatProjectInfo implements Parcelable
{
    public int asset_id;
    public String current_step;
    public String current_step_thread;
    public String current_subNode;
    public boolean is_beishu;
    public String workflow_id;

    public static MPChatProjectInfo fromJSONString(String jsonString)
    {
        MPChatProjectInfo entities = null;
        try
        {
            JSONObject jObj = new JSONObject(jsonString);
            entities = MPChatProjectInfo.fromJSONObject(jObj);
        }
        catch (JSONException e)
        {
            entities = null;
        }

        return entities;
    }


    public static MPChatProjectInfo fromJSONObject(JSONObject input)
    {
        if (input == null)
            return null;

        MPChatProjectInfo msgs = new MPChatProjectInfo();

        try
        {
            JSONObject jObject = input.getJSONObject("requirement");

            msgs.asset_id = jObject.optInt("needs_id");
            String isBeishu = jObject.getString("is_beishu");
            int isBeishuInt = Integer.parseInt(isBeishu);

            msgs.is_beishu = (isBeishuInt == 0); // important, sounds weired, yes - it is! Means opposite!

            /// if it is beishu, get the @"beishu_thread_id"  temporary
            if (msgs.is_beishu)
            {
                msgs.current_step_thread = jObject.optString("beishu_thread_id");
            }
            else
            {
                JSONArray bidders = jObject.optJSONArray("bidders");

                if (bidders != null)
                {
                    JSONObject bidder = bidders.optJSONObject(0);

                    if (bidder != null)
                    {
                        msgs.current_step = bidder.optString("wk_current_step_id");
                        msgs.workflow_id = bidder.optString("wk_id");
                        msgs.current_subNode = bidder.optString("wk_cur_sub_node_id");

                        JSONArray steps = bidder.optJSONArray("wk_steps");

                        if (steps != null)
                        {
                            for (int i = 0; i < steps.length(); ++i)
                            {
                                JSONObject step = steps.optJSONObject(i);

                                if (step != null)
                                {
                                    int stepId = step.optInt("wk_step_id");
                                    if (String.valueOf(stepId).equalsIgnoreCase((msgs.current_step)))
                                    {
                                        msgs.current_step_thread = step.optString("thread_id");
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (JSONException e)
        {
            msgs = null;
        }

        return msgs;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(asset_id);
        dest.writeString(current_step);
        dest.writeString(current_step_thread);
        dest.writeString(current_subNode);
        dest.writeByte((byte) (is_beishu ? 0x01 : 0x00));
        dest.writeString(workflow_id);
    }

    public MPChatProjectInfo()
    {
    }

    public MPChatProjectInfo(Parcel in)
    {
        asset_id = in.readInt();
        current_step = in.readString();
        current_step_thread = in.readString();
        current_subNode = in.readString();
        is_beishu = in.readByte() != 0x00;
        workflow_id = in.readString();
    }

    public static final Parcelable.Creator<MPChatProjectInfo> CREATOR = new Parcelable.Creator<MPChatProjectInfo>()
    {
        @Override
        public MPChatProjectInfo createFromParcel(Parcel in)
        {
            return new MPChatProjectInfo(in);
        }

        @Override
        public MPChatProjectInfo[] newArray(int size)
        {
            return new MPChatProjectInfo[size];
        }
    };
}