package com.autodesk.shejijia.shared.components.im.activity;

import android.content.Intent;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatMessage;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatMessages;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThread;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatUtility;
import com.autodesk.shejijia.shared.components.im.manager.MPChatHttpManager;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;

import org.json.JSONArray;

public class MediaMessagesChatRoomActivity extends BaseChatRoomActivity
{
    @Override
    protected int getLayoutResId()
    {
        return R.layout.activity_chat_media_message;
    }

    protected void retrieveThreadMessagesWithOffset(String threadId, final int offSet)
    {
        if (!mIsNextPageRequestRunning)
        {
            mIsNextPageRequestRunning = true;
            OkStringRequest.OKResponseCallback callback = new OkStringRequest.OKResponseCallback()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    MPNetworkUtils.logError(TAG, error);
                    mIsNextPageRequestRunning = false;
                    hideLoadingIndicator();
                }

                @Override
                public void onResponse(String response)
                {
                    mIsNextPageRequestRunning = false;
                    MPChatMessages msgs = MPChatMessages.fromJSONString(response);
                    onMessagesReceived(msgs);
                    hideLoadingIndicator();
                }
            };
            MPChatHttpManager.getInstance().retrieveMediaMessages(mAcsMemberId, threadId,
                    offSet, LIMIT, callback);
        }

    }

    @Override
    protected String getActivityTitle() {
        return getResources().getString(R.string.mychat);
    }

    @Override
    protected void onNewMessageReceived(Intent intent)
    {
        try
        {
            String json_Msg = intent.getStringExtra("json");
            JSONArray arr = new JSONArray(json_Msg);
            MPChatMessage message = MPChatMessage.fromJSONObject(arr.optJSONObject(0));

            if (!message.thread_id.equalsIgnoreCase(mThreadId))
                getThreadDetailForThreadId(message.thread_id);

            if (message.message_media_type == MPChatMessage.eMPChatMessageType.eIMAGE)
                super.onNewMessageReceived(intent);
        }
        catch (Exception e)
        {
        }
    }

    private void getThreadDetailForThreadId(String inThreadId)
    {
        MPChatHttpManager.getInstance().retrieveThreadDetails(mAcsMemberId, inThreadId, new OkStringRequest.OKResponseCallback()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
                MPNetworkUtils.logError(TAG, volleyError);
            }

            @Override
            public void onResponse(String s)
            {
                MPChatThread thread = MPChatThread.fromJSONString(s);
                updateUnreadMessageCountForThread(thread);
            }
        });
    }


    private void updateUnreadMessageCountForThread(MPChatThread thread)
    {
        String fileId = MPChatUtility.getFileEntityIdForThread(thread);
        if (fileId != null)
        {
            for (int i = 0; i < mMessageList.size(); ++i)
            {
                MPChatMessage chatMsg = mMessageList.get(i);
                if (String.valueOf(chatMsg.media_file_id).equalsIgnoreCase(fileId))
                {
                    mMessageAdapter.updateMessageCell(i);
                    break;
                }
            }
        }
    }

}
