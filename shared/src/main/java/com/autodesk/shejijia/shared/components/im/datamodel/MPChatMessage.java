package com.autodesk.shejijia.shared.components.im.datamodel;

import org.json.JSONException;
import org.json.JSONObject;



public class MPChatMessage
{
    public String subject;
    public String body;
    public String thread_id;
    public String sent_time;
    public int sender_member_id;
    public String sender_screen_name;
    public int recipient_member_id;
    public String recipient_screen_name;
    public String message_id;
    public String read_time;
    public String recipient_box;
    public String sender_box;
    public eMPChatMessageType message_media_type;
    public String sender_profile_image;
    public String recipient_profile_image;
    public String command;
    public String in_reply_to;
    public int media_file_id;

    public enum eMPChatMessageType
    {
        eTEXT,
        eIMAGE,
        eAUDIO,
        eCOMMAND,
        eNONE
    }

    public static eMPChatMessageType toMPChatMessageType(String mediaType)
    {
        if (mediaType.equalsIgnoreCase("TEXT"))
            return eMPChatMessageType.eTEXT;
        else if (mediaType.equalsIgnoreCase("IMAGE"))
            return eMPChatMessageType.eIMAGE;
        else if (mediaType.equalsIgnoreCase("AUDIO"))
            return eMPChatMessageType.eAUDIO;
        else if (mediaType.equalsIgnoreCase("COMMAND"))
            return eMPChatMessageType.eCOMMAND;
        else
            return eMPChatMessageType.eNONE;
    }


    public static MPChatMessage fromJSONString(String jsonString)
    {
        MPChatMessage msg = new MPChatMessage();
        try
        {
            JSONObject jObject = new JSONObject(jsonString);
            msg = MPChatMessage.fromJSONObject(jObject);
        }
        catch(JSONException e)
        {
            msg = null;
        }

        return msg;
    }

    public static MPChatMessage fromJSONObject(JSONObject jObject)
    {
        if (jObject == null)
            return  null;

        MPChatMessage msg = new MPChatMessage();
        try
        {
            msg.subject = jObject.optString("subject");

            msg.message_media_type = MPChatMessage.toMPChatMessageType(jObject.optString("message_media_type"));

            if (msg.message_media_type == eMPChatMessageType.eIMAGE ||
                    msg.message_media_type == eMPChatMessageType.eAUDIO)
            {
                String body = jObject.optString("body");
                JSONObject jObject2 = new JSONObject(body);
                msg.body = jObject2.optString("file_url");
                msg.media_file_id = jObject2.optInt("media_file_id");
            }
            else
            {
                msg.body = jObject.optString("body");
            }


            msg.thread_id = jObject.optString("thread_id");
            msg.sent_time = jObject.optString("sent_time");
            msg.sender_member_id = jObject.optInt("sender_member_id");
            msg.sender_screen_name = jObject.optString("sender_screen_name");
            msg.recipient_member_id = jObject.optInt("recipient_member_id");
            msg.recipient_screen_name = jObject.optString("recipient_screen_name");
            msg.message_id = jObject.optString("message_id");
            msg.read_time = jObject.optString("read_time");
            msg.recipient_box = jObject.optString("recipient_box");
            msg.sender_box = jObject.optString("sender_box");
            msg.message_media_type = MPChatMessage.toMPChatMessageType(jObject.optString("message_media_type"));
            msg.sender_profile_image = jObject.optString("sender_profile_image");
            msg.recipient_profile_image = jObject.optString("recipient_profile_image");
            msg.command = jObject.optString("command");
            msg.in_reply_to = jObject.optString("in_reply_to");
        }
        catch(JSONException e)
        {
            msg = null;
        }

        return msg;
    }

    public static MPChatCommandInfo getCommandInfoFromMessage(MPChatMessage msg)
    {
        MPChatCommandInfo commandMessage = null;
        String body = msg.body;
        try
        {
            JSONObject jObject2 = new JSONObject(body);
            commandMessage = MPChatCommandInfo.fromJSONObject(jObject2);
        }
        catch(JSONException e)
        {
        }

        return commandMessage;
    }

}
