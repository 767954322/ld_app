package com.autodesk.shejijia.consumer.codecorationBase.packages.view;

import com.autodesk.shejijia.shared.BuildConfig;
import com.autodesk.shejijia.shared.components.common.appglobal.ApiManager;

/**
 * Created by allengu on 16-9-5.
 */
public class ImageUrlUtils {


    /**
     * 新版本接口
     */
    public static String getEvUrl() {
        String urlPath = ApiManager.getApiEvnVersionName();
        switch (urlPath) {
            case "A":
                return "http://static.gdfcx.net/leedian-static/alpha/images/app/ios/";
            case "U":
                return "http://static.gdfcx.net/leedian-static/uat/images/app/ios/";
            case "P":
                return "http://static.shejijia.com/juran-prod-static/images/app/ios/";
            default:
                return "http://static.shejijia.com/juran-prod-static/images/app/ios/";
        }
    }

    public static String[] getPackagesListImage() {

        String url_header = getEvUrl();
        String[] packagesList = {
                url_header + "list/juranzhuangshidongjiekuaican.jpg",
                url_header + "list/juranzhumeixingfujia.jpg",
                url_header + "list/juranzhuangshibeishutaocan.jpg",
                url_header + "list/juranzhumeiyishujia.jpg",
                url_header + "list/juranzhuangshinanyuntaocan.jpg",
                url_header + "list/juranzhumeipinzhijia.jpg",
                url_header + "list/juranzhuangshixijingtaocan.jpg",
                url_header + "list/juranzhumeizunxiangjia.jpg"
        };

        return packagesList;
    }

    public static String[] getPackagesDetailImage() {

        String url_header = getEvUrl();
        String[] packagesDetail = {
                url_header + "detail/dongjie@3x.jpg",
                url_header + "detail/xingfujia@3x.jpg",
                url_header + "detail/beishu@3x.jpg",
                url_header + "detail/yishujia@3x.jpg",
                url_header + "detail/nanyun@3x.jpg",
                url_header + "detail/pinzhijia@3x.jpg",
                url_header + "detail/xijing@3x.jpg",
                url_header + "detail/zunxiang@3x.jpg"
        };
        return packagesDetail;
    }

    public static String getPackagesListBanner() {
        String url_header = getEvUrl();

        return url_header + "list/banner2x.jpg";
    }

    public static String[] getPackagesListNames() {
        String[] packages_name = {"居然装饰东捷套餐", "居然装饰东捷套餐",
                "居然装饰东捷套餐", "居然住美幸福家", "居然装饰北舒套餐",
                "居然住美艺术家", "居然装饰南韵套餐", "居然住美品质家"};

        return packages_name;
    }

}
/**
 * 新版本接口
 */
//    public static String getEvUrl() {
//        String urlPath = (String) PropUtil.loadAssetsProperties(AdskApplication.getInstance(), ApiManagerV2.PROPERTY_PATH).get("versionName");
//        switch (urlPath) {
//            case "A":
//                return "http://static.gdfcx.net/leedian-static/alpha/images/app/ios/";
//            case "U":
//                return "http://static.gdfcx.net/leedian-static/uat/images/app/ios/";
//            case "P":
//                return "http://static.shejijia.com/juran-prod-static/images/app/ios/";
//            default:
//                return "http://static.shejijia.com/juran-prod-static/images/app/ios/";
//        }
//    }
//
//    public static String[] getPackagesListImage() {
//
//        String url_header = getEvUrl();
//        String[] packagesList = {
//                url_header + "list/juranzhuangshidongjiekuaican.jpg",
//                url_header + "list/juranzhumeixingfujia.jpg",
//                url_header + "list/juranzhuangshibeishutaocan.jpg",
//                url_header + "list/juranzhumeiyishujia.jpg",
//                url_header + "list/juranzhuangshinanyuntaocan.jpg",
//                url_header + "list/juranzhumeipinzhijia.jpg",
//                url_header + "list/juranzhuangshixijingtaocan.jpg",
//                url_header + "list/juranzhumeizunxiangjia.jpg"
//        };
//
//        return packagesList;
//    }
//
//    public static String[] getPackagesDetailImage() {
//
//        String url_header = getEvUrl();
//        String[] packagesDetail = {
//                url_header + "detail/dongjie.jpg",
//                url_header + "detail/xingfujia.jpg",
//                url_header + "detail/beishu.jpg",
//                url_header + "detail/yishujia.jpg",
//                url_header + "detail/nanyun.jpg",
//                url_header + "detail/pinzhijia.jpg",
//                url_header + "detail/xijing.jpg",
//                url_header + "detail/zunxiang.jpg"
//        };
//        return packagesDetail;
//    }
//
//    public static String getPackagesListBanner() {
//        String url_header = getEvUrl();
//
//        return url_header + "list/banner2x.jpg";
//    }
//
//    public static String[] getPackagesListNames() {
//        String[] packages_name = {"居然装饰东捷套餐", "居然装饰东捷套餐",
//                "居然装饰东捷套餐", "居然住美幸福家", "居然装饰北舒套餐",
//                "居然住美艺术家", "居然装饰南韵套餐", "居然住美品质家"};
//
//        return packages_name;
//    }

/**
 * 老版本接口
 */
//    public static String getEvUrl() {
//        String urlPath = (String) PropUtil.loadAssetsProperties(AdskApplication.getInstance(), ApiManagerV2.PROPERTY_PATH).get("versionName");
//        switch (urlPath) {
//            case "A":
//                return "http://static.gdfcx.net/leedian-static/alpha/images/app/ios/";
//            case "U":
//                return "http://static.gdfcx.net/leedian-static/uat/images/app/ios/";
//            case "P":
//                return "http://static.shejijia.com/juran-prod-static/images/app/ios/";
//            default:
//                return "http://static.shejijia.com/juran-prod-static/images/app/ios/";
//        }
//    }
//
//    public static String[] getPackagesListImage() {
//
//        String url_header = getEvUrl();
//        String[] packagesList = {
//                url_header + "list/juranzhuangshidongjiekuaican@3x.jpg",
//                url_header + "list/juranzhumeixingfujia@3x.jpg",
//                url_header + "list/juranzhuangshibeishutaocan@3x.jpg",
//                url_header + "list/juranzhumeiyishujia@3x.jpg",
//                url_header + "list/juranzhuangshinanyuntaocan@3x.jpg",
//                url_header + "list/juranzhumeipinzhijia@3x.jpg",
//                url_header + "list/juranzhuangshixijingtaocan@3x.jpg",
//                url_header + "list/juranzhumeizunxiangjia@3x.jpg"
//        };
//
//        return packagesList;
//    }
//
//    public static String[] getPackagesDetailImage() {
//
//        String url_header = getEvUrl();
//        String[] packagesDetail = {
//                url_header + "detail/dongjie@3x.jpg",
//                url_header + "detail/xingfujia@3x.jpg",
//                url_header + "detail/beishu@3x.jpg",
//                url_header + "detail/yishujia@3x.jpg",
//                url_header + "detail/nanyun@3x.jpg",
//                url_header + "detail/pinzhijia@3x.jpg",
//                url_header + "detail/xijing@3x.jpg",
//                url_header + "detail/zunxiang@3x.jpg"
//        };
//        return packagesDetail;
//    }
//
//    public static String getPackagesListBanner() {
//        String url_header = getEvUrl();
//
//        return url_header + "list/banner@2x.jpg";
//    }
//
//    public static String[] getPackagesListNames() {
//        String[] packages_name = {"居然装饰东捷套餐", "居然住美幸福家",
//                "居然装饰北舒套餐", "居然住美艺术家", "居然装饰南韵套餐",
//                "居然住美品质家", "居然装饰西境套餐", "居然住美尊享家"};
//
//        return packages_name;
//    }