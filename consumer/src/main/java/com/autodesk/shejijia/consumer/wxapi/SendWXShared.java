package com.autodesk.shejijia.consumer.wxapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.autodesk.shejijia.consumer.ConsumerApplication;
import com.autodesk.shejijia.consumer.R;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author allengu .
 * @version v1.0 .
 * @date 2016-7-19 .
 * @file AliPayService.java .
 * @brief 微信分享实现类 .
 */
public class SendWXShared implements ISendWXShared {

    @Override
    public void sendProjectToWX(Context context, String webUrl, String title, String description, String imageUrl, boolean ifSharedToFriends) throws IOException {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = webUrl;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;
        msg.thumbData = getBytes(new URL(imageUrl).openStream());

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = ifSharedToFriends ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        ConsumerApplication.api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream outstream = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int len = -1;

        while ((len = is.read(buffer)) != -1) {
            outstream.write(buffer, 0, len);
        }
        byte[] bytes = outstream.toByteArray();
        outstream.close();
        return bytes;
    }

}
