package com.autodesk.shejijia.shared.components.common.appglobal;

import com.socks.library.KLog;

/**
 * @author yangxuewu .
 * @version v1.0 .
 * @date 2016-6-7 .
 * @file UrlMessagesContants.java .
 * @brief 消息常量管理 .
 */
public class UrlMessagesContants {

    public static void init(boolean Staging) {
        isStaging = Staging;
        initImParameter();
        initIMServerUrl();
    }

    /**
     * 初始化参数
     */
    public static void initImParameter() {
        if (isStaging) {//  测试环境
            initializeMarketplaceWithAFC = "HW1ONB";
            appID = "96";
            mediaIdProject = "53";
            mediaIdCase = "58";
            mediaIdConstruction = "62";
        } else {       //    product  环境
            initializeMarketplaceWithAFC = "HW1ON1";
            appID = "96";
            mediaIdProject = "53";
            mediaIdCase = "58";
            mediaIdConstruction = "62";
        }
        KLog.d("yxw", initializeMarketplaceWithAFC + "   appID  ");
    }

    /**
     * 初始化地址
     */
    public static void initIMServerUrl() {
        if (isStaging) {
            //   中国 测试
            ConnectWebSocketUrl = "ws://beta-chat.acgcn.autodesk.com:80/api/v2/connect?";
            StrHttpServicerootCn = "http://beta-api.acgcn.autodesk.com/API/v2";
        } else {
            //正式地址
            ConnectWebSocketUrl = "ws://chat.acgcn.autodesk.com:80/api/v2/connect?";
            StrHttpServicerootCn = "http://api.acgcn.autodesk.com/API/v2";
//            //   美国 测试
//            ConnectWebSocketUrl = "ws://api.beta.squidplatform.com:80/api/v2/connect";
//            StrHttpServicerootCn= "https://beta-api.acg.autodesk.com/api/v2/";
        }
    }

    /**
     * connect  websocket
     */
    public static String ConnectWebSocketUrl = null;
    //    public static String ConnectWebSocketUrl = "ws://beta-chat.acgcn.autodesk.com:80/api/v2/connect?";
//    public static String StrHttpServicerootCn = "http://beta-api.acgcn.autodesk.com/API/v2";
    public static String StrHttpServicerootCn = null;
    public static String initializeMarketplaceWithAFC = null;
    public static String appID = null;
    public static String mediaIdProject = null;
    public static String mediaIdConstruction = null;
    public static String mediaIdCase = null;
    public static boolean isStaging;
}
