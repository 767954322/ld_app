package com.autodesk.shejijia.enterprise.base.common.utils;

/**
 * Created by t_xuz on 8/15/16.
 * 一些公用的常量字段
 */
public class Constants {

    //登陆登出广播接收器的action
    public static final String LOGIN_IN_ACTION = "com.easyhome.enterprise.login";
    public static final String LOGIN_OUT_ACTION = "com.easyhome.enterprise.logout";
    public static final String LOGIN_IN_ACTIVITY_FINISHED = "com.easyhome.login.activity.finished";

    //用户登陆成功后存在sp中的key
    public static final String USER_INFO = "user_info";

    //服务器端url地址:
    public static final String BASE_URL = "http://cp-alpha-plan.homestyler.com/api/v1";
//    public static final String BASE_URL = "http://ec2-54-223-58-121.cn-north-1.compute.amazonaws.com.cn:8080/api/v1";

}
