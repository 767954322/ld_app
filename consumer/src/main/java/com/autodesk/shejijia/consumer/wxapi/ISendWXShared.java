package com.autodesk.shejijia.consumer.wxapi;

import android.content.Context;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * @author allengu .
 * @version v1.0 .
 * @date 2016-7-19 .
 * @file AliPayService.java .
 * @brief 微信分享接口类 .
 */
public interface ISendWXShared {


    void sendProjectToWX(Context context, String webUrl, String title, String description, String imageUrl, boolean ifSharedToFriends) throws IOException;

}
