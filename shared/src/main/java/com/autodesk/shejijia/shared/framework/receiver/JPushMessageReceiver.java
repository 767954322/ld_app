package com.autodesk.shejijia.shared.framework.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.components.common.network.PushNotificationHttpManager;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.im.manager.MPChatHttpManager;
import com.autodesk.shejijia.shared.components.common.utility.SharedPreferencesUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Random;

import cn.jpush.android.api.JPushInterface;

public class JPushMessageReceiver extends BroadcastReceiver {
    public JPushMessageReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[JPushMessageReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[JPushMessageReceiver] Registration Id : " + regId);
            registerJPushRegId(regId);
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[JPushMessageReceiver] custom message received: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[JPushMessageReceiver] Push down notice received");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[JPushMessageReceiver] notification ID: " + notifactionId);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[JPushMessageReceiver] Notification Opened event");
        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[JPushMessageReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[JPushMessageReceiver] Unhandled intent - " + intent.getAction());
        }
    }


    private String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {

        if (AdskApplication.getInstance().isChatRoomActivityInForeground()) {
            //Do we need to handle this??
            // TODO: add code incase you want to handle this condition
        } else {
            //Posting notification
            String title = context.getResources().getString(R.string.app_name);
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String contentText = message;

            try {
                JSONObject jsonObject = new JSONObject(message);

                if (jsonObject != null) {

                    JSONArray jArray = jsonObject.getJSONArray("args");
                    String param1 = null;
                    String param2 = null;

                    param1 = (jArray.length() > 0) ? jArray.getString(0) : null;
                    param2 = (jArray.length() > 1) ? jArray.getString(1) : null;
                    String userName = param1;

                    for (String token : param1.split("_")) {
                        userName = token;
                        break;
                    }

                    contentText = context.getResources().getString(R.string.push_notification_text_message, userName, param2);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //creating basic notification UI
            // You can create custom notification UI also if you want
            NotificationManager nm;
            NotificationCompat.Builder builder;
            int notificationId = (new Random()).nextInt(10000000);


            builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.icon_launcher_label)
                    .setContentTitle(title)
                    .setContentText(contentText);
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = builder.build();
            notification.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL | Notification.DEFAULT_SOUND;
            nm.notify(notificationId, notification);
        }
    }

    private void registerJPushRegId(String regId) {
        SharedPreferencesUtils.writeString(REGID, regId);

        //if log-in register this ID with marketplace
        // please note that this regid will be treated as device id in Marketplace API

        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();

        if (memberEntity != null) {
            PushNotificationHttpManager.registerDeviceWithMarketplace(regId, new OkStringRequest.OKResponseCallback() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }

                @Override
                public void onResponse(String s) {

                }
            });
        }
    }

    private static final String TAG = "JPush";
    public static final String MESSAGE_RECEIVED_ACTION = "com.autodesk.easyhome.marketplace.MESSAGE_RECEIVED_ACTION";
    public static final String REGID_RECEIVED_ACTION = "com.autodesk.easyhome.marketplace.REGID_RECEIVED_ACTION";
    public static final String PAYLOAD = "com.autodesk.easyhome.marketplace.PAYLOAD";
    public static final String REGID = "com.autodesk.easyhome.marketplace.REGID";
}
