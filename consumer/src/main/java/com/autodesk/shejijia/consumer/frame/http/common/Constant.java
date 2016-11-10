package com.autodesk.shejijia.consumer.frame.http.common;

/**
 * @Author: lizhipeng
 * @Data: 16/5/3 下午4:27
 * @Description:常量配置类
 */
public class Constant {
    public static  boolean isDebug = true;//是否打印日志，在application里面已经初始化了,无需手动更改
    public static final String  POSITION= "position";
    public interface LogConfig{//log工具类配置字段接口

        public static final boolean isWrite = false;//是否将日志写入文件
        public static final String TAG = "ezt_doctor";
    }
    public static final String BASE_PATH = "http://www.weather.com.cn/";//访问的地址


    public interface Alpha{
        public static String loginPath="http://alpha-www.gdfcx.net/sso/SSO_login.html?caller=shejijia&browser_type=android";
        public static String memberApp="http://alpha-api.gdfcx.net/member-app/v1/api";
        public static String designApp="http://alpha-api.gdfcx.net/design-app/v1/api";
        public static String recommendApp="http://192.168.71.86:8080/materials-recommend-app/v1/api/";
        public static String transactionApp="http://alpha-api.gdfcx.net/transaction-app/v1/api";
        public static String shareUrl="http://alpha-api.gdfcx.net/share/2dcase.html?caseid=";
        public static String apiDomain="http://alpha-api.gdfcx.net";
    }

}
