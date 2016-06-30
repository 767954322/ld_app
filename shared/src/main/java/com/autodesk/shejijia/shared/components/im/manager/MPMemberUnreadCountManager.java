package com.autodesk.shejijia.shared.components.im.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.im.constants.BroadCastInfo;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatUtility;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class MPMemberUnreadCountManager
{
    public interface MPMemberUnreadCountInterface
    {
        TextView getUnreadBadgeLabel();
    }


    public static MPMemberUnreadCountManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (MPMemberUnreadCountManager.class)
            {
                mInstance = new MPMemberUnreadCountManager();
            }
        }

        return mInstance;
    }


    public void registerForMessageUpdates(Context context, MPMemberUnreadCountInterface listener)
    {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BroadCastInfo.USER_DID_LOGOUT);
        intentFilter.addAction(BroadCastInfo.USER_DID_LOGIN);
        intentFilter.addAction(BroadCastInfo.RECEVIER_RECEIVERMESSAGE);

        mMessageBroadcastReceiver = new MessageBroadcastReceiver();

        context.registerReceiver(mMessageBroadcastReceiver, intentFilter);

        if (listener != null)
            mListener = listener;
    }


    public void unregisterForMessageUpdates(Context context)
    {
        context.unregisterReceiver(mMessageBroadcastReceiver);

        mListener = null;
    }


    public void refreshCount()
    {
        getMemberThreadUnreadCount();
    }


    private MPMemberUnreadCountManager()
    {

    }


    private void getMemberThreadUnreadCount()
    {
        if (mMemberEntity == null)
        {
            // TODO: Correct this if we figure out a better way to inform
            // clients of login available on 2nd launch
            MemberEntity entity = AdskApplication.getInstance().getMemberEntity();

            if (entity == null)
                return;
            else
                mMemberEntity = entity;
        }


        MPChatHttpManager.getInstance().retrieveMemberUnreadMessageCount(mMemberEntity.getAcs_member_id(),
                true, new OkStringRequest.OKResponseCallback()
                {
                    @Override
                    public void onErrorResponse(VolleyError volleyError)
                    {
                        MPNetworkUtils.logError(TAG, volleyError);
                    }

                    @Override
                    public void onResponse(String s)
                    {
                        JSONObject jObj = null;
                        int unread_message_count = 0;

                        try
                        {
                            jObj = new JSONObject(s);
                            unread_message_count = jObj.optInt("unread_message_count");
                        }
                        catch (JSONException e)
                        {
                            Log.e(TAG, "Exception while parsing unread count from JSON");
                        }

                        if (unread_message_count <= 0)
                            hideUnreadBadge();
                        else
                            updateUnreadBadge(unread_message_count);
                    }
                });
    }


    private void updateUnreadBadge(int unreadCount)
    {
        if (mListener == null)
            return;

        TextView unreadLabel = mListener.getUnreadBadgeLabel();

        if (unreadLabel != null)
        {
            unreadLabel.setVisibility(View.VISIBLE);
            unreadLabel.setText(MPChatUtility.getFormattedBadgeString(unreadCount));
        }
    }


    private void hideUnreadBadge()
    {
        if (mListener == null)
            return;

        TextView unreadLabel = mListener.getUnreadBadgeLabel();

        if (unreadLabel != null)
            unreadLabel.setVisibility(View.INVISIBLE);
    }


    private class MessageBroadcastReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();

            if (action.equalsIgnoreCase(BroadCastInfo.RECEVIER_RECEIVERMESSAGE))
                getMemberThreadUnreadCount();
            else if (action.equalsIgnoreCase(BroadCastInfo.USER_DID_LOGIN))
            {
                mMemberEntity = AdskApplication.getInstance().getMemberEntity();
                getMemberThreadUnreadCount();
            }
            else if (action.equalsIgnoreCase(BroadCastInfo.USER_DID_LOGOUT))
            {
                hideUnreadBadge();
                mMemberEntity = null;
            }
        }
    }

    private static final String TAG = "MemberUnreadCountMgr";

    private static MPMemberUnreadCountManager mInstance;

    private MessageBroadcastReceiver mMessageBroadcastReceiver;
    private MPMemberUnreadCountInterface mListener;
    private MemberEntity mMemberEntity;
}
