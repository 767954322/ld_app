package com.autodesk.shejijia.shared;

/**
 * Created by wenhulin on 10/27/16.
 */

public class Config {
    public final static String API_VERSION_NAME = "A";
    public final static boolean IS_PRODUCTION = false;
    public final static String LOGIN_PATH = "http://alpha-www.gdfcx.net/sso/SSO_login.html?caller=shejijia&browser_type=android";
    public final static String MEMBER_PATH = "http://alpha-api.gdfcx.net/member-app/v1/api";
    public final static String DESIGN_PATH = "http://alpha-api.gdfcx.net/design-app/v1/api";
    public final static String TRANSACTION_PATH = "http://alpha-api.gdfcx.net/transaction-app/v1/api";
    public final static String SHARE_PATH = "http://alpha-api.gdfcx.net/share/2dcase.html?caseid=";
    public final static String SHARE3_PATH = null;
    public final static String API_DOMAIN = "http://alpha-api.gdfcx.net";
    // TODO  后期替换Alpha正式环境.
//    public final static String RECOMMEND_PATH = "http://192.168.71.86:8080/materials-recommend-app/v1/api";

    public final static String RECOMMEND_PATH = "http://alpha-api.gdfcx.net/materials-recommend-app/v1/api";


    //Construction urls
    public static final String CONSTRUCTION_MAIN_URL = "http://cp-alpha-plan.homestyler.com/api/v1";
    public static final String CONSTRUCTION_ISSUE_URL = "http://cp-alpha-issue.homestyler.com/v1";

}
