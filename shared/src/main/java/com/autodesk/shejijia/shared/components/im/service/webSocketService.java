package com.autodesk.shejijia.shared.components.im.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.im.manager.MPChatHttpManager;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.im.constants.BroadCastInfo;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author: yangxuewu .
 * @version: 1.0 .
 * created at: 2016/1/13 0013 13:29 .
 * Describe : webSocketService  .
 */
public class webSocketService extends Service {

    public WebSocketClient mWebSocketClient;
    private String connectWebSocketUrl;
    private String acs_member_id;
    private String acs_x_session;
    private android.os.Handler handler = new android.os.Handler();
    private boolean isTryingToReconnect = false;
    private boolean isStopService = false;

    private long mDelayInSeconds = 1;

    private String TAG = "*******WebSocket Message";

    public webSocketService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.i("webSocket", "Service onBind--->");
        return null;
    }

    public void onStart(Intent intent, int startId) {
        if (intent != null) {
            acs_member_id = intent.getStringExtra("acs_member_id");
            acs_x_session = intent.getStringExtra("acs_x_session");
            LogUtils.i("webSocket", "Service onStart--->" + acs_member_id + "   " + acs_x_session);
            connectWebSocketUrl = MPChatHttpManager.getInstance().getConnectWebSocketUrl(AdskApplication.getInstance());
            URI uri = null;
            isStopService = false;
            try {
                uri = new URI(connectWebSocketUrl);
                connectWebSocket(uri);
                LogUtils.i("webSocket", "connectWebSocketUrl  " + connectWebSocketUrl);
            } catch (URISyntaxException e) {
                LogUtils.i("webSocket", "URISyntaxException  " + e.toString());
                e.printStackTrace();
            }
        }
    }

    public void onDestroy() {
        isStopService = true;
        if (mWebSocketClient != null) {
            mWebSocketClient.close();
        }
        LogUtils.i("webSocket", "Service onDestroy--->");
    }

    public boolean onUnbind(Intent intent) {
        LogUtils.i("webSocket", "Service onUnbind--->");
        return super.onUnbind(intent);
    }

    /**
     * create connect  websocket
     */
    public void connectWebSocket(URI uri) {
        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                LogUtils.i("Websocket Opened");
                LogUtils.i(TAG, "Posting socket notification" + BroadCastInfo.MPCHAT_CONNECT_NOTIFICATION);
                Intent intent = new Intent();
                intent.setAction(BroadCastInfo.MPCHAT_CONNECT_NOTIFICATION);
                sendBroadcast(intent);
                mDelayInSeconds = 1;
            }

            @Override
            public void onMessage(String msg) {
                if (msg.equals("SUCCESS")) {
                    AdskApplication.getInstance().setWebSocketStatus(true);
                    setIsTryingToReconnectValue(false);
                    handler.removeCallbacks(reconnectWebSocket);
                    LogUtils.i("webSocket", "Connect SUCCESS!!");
                } else if (msg.equals("SESSION_INVALID")) {
                    LogUtils.i("SESSION_INVALID");
                } else {
                    LogUtils.i(TAG, msg);
                    LogUtils.i(TAG, "Posting socket notification" + BroadCastInfo.RECEVIER_RECEIVERMESSAGE);

                    Intent intent = new Intent();
                    intent.setAction(BroadCastInfo.RECEVIER_RECEIVERMESSAGE);
                    intent.putExtra("json", msg);
                    sendBroadcast(intent);
                }
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                mDelayInSeconds = 1;
                Toast.makeText(webSocketService.this, R.string.connection_close, Toast.LENGTH_SHORT).show();
                //mWebSocketClient.connect();
                LogUtils.i(TAG, "Websocket  Closed " + s);
                LogUtils.i(TAG, "Posting socket notification" + BroadCastInfo.MPCHAT_CLOSE_CONNECTION_NOTIFICATION);
                Intent intent = new Intent();
                intent.setAction(BroadCastInfo.MPCHAT_CLOSE_CONNECTION_NOTIFICATION);
                sendBroadcast(intent);
            }

            @Override
            public void onError(Exception e) {
                LogUtils.i(TAG, "Failed with error" + e.getMessage());
                LogUtils.i(TAG, "Posting socket notification" + BroadCastInfo.MPCHAT_DISCONNECT_NOTIFICATION);
                Intent intent = new Intent();
                intent.setAction(BroadCastInfo.MPCHAT_DISCONNECT_NOTIFICATION);
                intent.putExtra(BroadCastInfo.MPCHAT_ERROR, e.getLocalizedMessage());
                sendBroadcast(intent);

                if (mWebSocketClient != null) {
                    mWebSocketClient.close();
                }

                if (!getIsTryingToReconnectValue() && !isStopService) {
                    setIsTryingToReconnectValue(true);

                    AdskApplication.getInstance().setWebSocketStatus(false);

                    if (mDelayInSeconds > 40)
                        mDelayInSeconds = 1;

                    LogUtils.i(TAG, "socket reconnection will be attempted after " + mDelayInSeconds + " seconds");
                    handler.postDelayed(reconnectWebSocket, mDelayInSeconds * 1000);

                }
//                mWebSocketClient.connect();
            }

        };

        mWebSocketClient.connect();

    }

    public Runnable reconnectWebSocket = new Runnable() {
        @Override
        public void run() {
            try {
                mDelayInSeconds *= 2;
                connectWebSocket(new URI(connectWebSocketUrl));

                setIsTryingToReconnectValue(false);
//                mWebSocketClient.close();

            } catch (URISyntaxException e) {
                LogUtils.i("webSocket", "URISyntaxException  " + e.toString());
                e.printStackTrace();
            }
        }
    };


    private synchronized void setIsTryingToReconnectValue(boolean value) {
        isTryingToReconnect = value;
    }

    private synchronized boolean getIsTryingToReconnectValue() {
        return isTryingToReconnect;
    }

}
