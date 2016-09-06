package com.autodesk.shejijia.shared.components.common.appglobal;

import com.autodesk.shejijia.shared.components.common.utility.PropUtil;
import com.autodesk.shejijia.shared.framework.AdskApplication;

/**
 * @author he.liu
 * @version v1.0 .
 * @date 2016-6-6 .
 * @file ApiManager.java .
 * @brief 运行环境的管理切换类.
 * ApiManager升级，如需更改环境，请到配置环境切换 .
 */
public class ApiManagerV2 {

    public static final String PROPERTY_PATH = "apiurl.properties";
    private static final String API_DOMAIN = "apiDomain";
    private static final String LOGIN_URL = "loginPath";
    private static final String MEMBER_APP = "memberApp";
    private static final String DESIGN_APP = "designApp";
    private static final String SHARE_URL = "shareUrl";
    private static final String TRANSACTION_APP = "transactionApp";
    private static final String IS_PRODUCTION = "isProduction";
    private static final String VERSION_NAME = "versionName";

    /*环境的host地址*/
    public final static String APIDOMAIN_PATH = getUrlPath(API_DOMAIN);
    /*登录地址*/
    public final static String LOGIN_PATH = getUrlPath(LOGIN_URL);
    /*member-app*/
    public final static String MEMBER_PATH = getUrlPath(MEMBER_APP);
    /*design-app*/
    public final static String DESIGN_PATH = getUrlPath(DESIGN_APP);
    /*share拼接地址*/
    public final static String SHARE_PATH = getUrlPath(SHARE_URL);

    /*我的资产拼接地址*/
    public final static String TRANSACTION_PATH = getUrlPath(TRANSACTION_APP);
    /**
     * 判断是否是production环境，
     * 如果是返回1,就是production环境，
     * 如果是0,就是其它环境
     */
    public final static String PRODUCTION_DEVELOPMENT_TAG = getUrlPath(IS_PRODUCTION);
    public final static String VERSION_NAME_PREFIX = getUrlPath(VERSION_NAME);

    /**
     * 获取配置文件中对应的URL地址
     *
     * @param propKey 配置文件对应的key
     * @return value
     */
    private static String getUrlPath(String propKey) {
        String urlPath = (String) PropUtil.loadAssetsProperties(AdskApplication.getInstance(), PROPERTY_PATH).get(propKey);
        return urlPath;
    }
}
