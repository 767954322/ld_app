package com.autodesk.shejijia.shared.components.common.appglobal;

/**
 * @author he.liu
 * @version v1.0 .
 * @date 2016-6-6 .
 * @file ApiManager.java .
 * @brief 运行环境的管理切换类, 使用ApiManagerV2类中配置文件配置.
 */
public class ApiManager {

    /**
     * TODO DELETE
     */
    public static String RUNNING_DEVELOPMENT = "";

    /// 供给聊天使用的userId .
    public static int ADMIN_USER_ID = getAdmin_User_Id();

    /**
     * 运行在正式环境的标识
     */
    private static final String IS_PRODUCTION = "1";

    private ApiManager() {

    }

    /**
     * 判断现在运行的运行环境,供给IM使用
     *
     * @param runningDevelopment 运行环境
     * @return false 正式环境  ;  true 测试环境
     */
    public static boolean isRunningDevelopment(String runningDevelopment) {
        String productionDevelopmentTag = ApiManagerV2.PRODUCTION_DEVELOPMENT_TAG;

        if (IS_PRODUCTION.equals(productionDevelopmentTag)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取不同开发环境的admin_user_id,供给IM使用
     *
     * @return 不同环境环境的admin_user_id
     */
    /// 生产环境user_id .
    private static final int PRODUCTION_ADMIN_USER_ID = 20742718;

    /// 开发环境user_id .
    private static final int UAT_ADMIN_USER_ID = 20730165;

    public static int getAdmin_User_Id() {
        String productionDevelopmentTag = ApiManagerV2.PRODUCTION_DEVELOPMENT_TAG;
        int admin_user_id;

        if (IS_PRODUCTION.equals(productionDevelopmentTag)) {
            /**
             * 生产环境：20742718
             * JuranAdmin@163.com
             */
            admin_user_id = PRODUCTION_ADMIN_USER_ID;

        } else {
            /**
             * UAT环境：20730165
             *
             * EZHome-Admin@autodesk.com
             */
            admin_user_id = UAT_ADMIN_USER_ID;
        }
        return admin_user_id;
    }

    /**
     * 获取不同开发环境的版本标识
     *
     * @return 版本标识
     */
    public static String getVersionNumber() {
        return ApiManagerV2.VERSION_NAME_PREFIX;
    }

    /**
     * 获得html5分享界面的url
     *
     * @return 我的资产url公共部分
     */
    public static String getHtml5Url(String caseId) {
        return UrlConstants.MP_MAIN_SHARE + caseId;
    }
}
