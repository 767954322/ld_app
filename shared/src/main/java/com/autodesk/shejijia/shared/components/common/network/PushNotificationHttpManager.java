package com.autodesk.shejijia.shared.components.common.network;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.autodesk.shejijia.shared.components.common.appglobal.UrlMessagesContants;
import com.autodesk.shejijia.shared.framework.AdskApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by agrawam on 28/06/16.
 */
public class PushNotificationHttpManager
{
    public static void registerDeviceWithMarketplace(final String deviceId, OkStringRequest.OKResponseCallback callback) {
//        Assert.assertTrue(deviceId != null && !deviceId.isEmpty());

        String url = UrlMessagesContants.StrHttpServicerootCn;
        url += "/devices?";
        url += "app_id=" + UrlMessagesContants.appID;
        url += "&";
        url += "device_id" + "=" + deviceId;
        url += "&";
        url += "device_type=3";
        url += "&";
        url += "message_version=v1";

        AdskApplication.getInstance().queue.add(new OkStringRequest(Request.Method.PUT, url, callback) {
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                return getDefaultHeaders();
            }
        });
    }


    public static void unRegisterDeviceWithMarketplace(final String deviceId, OkStringRequest.OKResponseCallback callback) {
//        Assert.assertTrue(deviceId != null && !deviceId.isEmpty());

        String url = UrlMessagesContants.StrHttpServicerootCn;
        url += "/devices?";
        url += "app_id=" + UrlMessagesContants.appID;
        url += "&";
        url += "device_id" + "=" + deviceId;

        AdskApplication.getInstance().queue.add(new OkStringRequest(Request.Method.DELETE, url, callback) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getDefaultHeaders();
            }
        });
    }

    private static Map<String, String> getDefaultHeaders() {
        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/x-www-form-urlencoded");
        map.put("X-AFC", UrlMessagesContants.initializeMarketplaceWithAFC);
        if (AdskApplication.getInstance().getMemberEntity() != null)
          map.put("X-Session", AdskApplication.getInstance().getMemberEntity().getAcs_x_session());
        return map;
    }
}
