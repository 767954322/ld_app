package com.autodesk.shejijia.shared.components.common.appglobal;

/**
 * @author he.liu
 * @version v1.0 .
 * @date 2016-6-6 .
 * @file ApiManager.java .
 * @brief 运行环境的管理切换类.
 */
public class ApiManager {

    /**
     * 控制运行环境
     * 切换为：UrlConstants.*,*为以下可选值
     * RUNNING_DEVELOP,RUNNING_QA,RUNNING_UAT,RUNNING_ALPHA,RUNNING_PRODUCTION,RUNNING_DEV
     */
    public static String RUNNING_DEVELOPMENT = UrlConstants.RUNNING_ALPHA;

    /// 供给聊天使用的userId .
    public static int ADMIN_USER_ID = getAdmin_User_Id(RUNNING_DEVELOPMENT);

    /**
     * 登录
     *
     * @param runningDevelopment 运行环境
     * @return 登录的路径
     */
    public static String getLoginPath(String runningDevelopment) {
        String login_path = null;
        switch (runningDevelopment) {
            case UrlConstants.RUNNING_DEVELOP:
                login_path = UrlConstants.MP_MAIN_LOGIN_PATH;
                break;

            case UrlConstants.RUNNING_QA:
                login_path = UrlConstants.MP_MAIN_LOGIN_PATH;
                break;

            case UrlConstants.RUNNING_UAT:
                login_path = UrlConstants.MP_MAIN_LOGIN_PATH_UAT;
                break;

            case UrlConstants.RUNNING_ALPHA:
                login_path = UrlConstants.MP_MAIN_LOGIN_PATH_ALPHA;
                break;

            case UrlConstants.RUNNING_PRODUCTION:
                login_path = UrlConstants.MP_MAIN_LOGIN_PATH_PRODUCTION;
                break;

            case UrlConstants.RUNNING_DEV:
                login_path = UrlConstants.MP_MAIN_LOGIN_PATH_DEV;
                break;
        }
        return login_path;
    }

    /**
     * 登出
     *
     * @param runningDevelopment 运行环境
     * @return 登出的路径
     */
    public static String getLogoutPath(String runningDevelopment) {
        String logout_path = null;
        switch (runningDevelopment) {
            case UrlConstants.RUNNING_DEVELOP:
                logout_path = UrlConstants.MP_MAIN_LOGOUT_PATH;
                break;

            case UrlConstants.RUNNING_QA:
                logout_path = UrlConstants.MP_MAIN_LOGOUT_PATH;
                break;

            case UrlConstants.RUNNING_UAT:
                logout_path = UrlConstants.MP_MAIN_LOGOUT_PATH_UAT;
                break;

            case UrlConstants.RUNNING_ALPHA:
                logout_path = UrlConstants.MP_MAIN_LOGOUT_PATH_ALPHA;
                break;

            case UrlConstants.RUNNING_PRODUCTION:
                logout_path = UrlConstants.MP_MAIN_LOGOUT_PATH_PRODUCTION;
                break;
            case UrlConstants.RUNNING_DEV:
                logout_path = UrlConstants.MP_MAIN_LOGOUT_PATH_DEV;
                break;
        }
        return logout_path;
    }

    /**
     * 设计师请求的url的公共部分
     *
     * @param runningDevelopment 运行环境
     * @return 设计师相关请求的url的公共部分
     */

    public static String getMPMain_Design(String runningDevelopment) {
        String main_design = null;
        switch (runningDevelopment) {
            case UrlConstants.RUNNING_DEVELOP:
                main_design = UrlConstants.DEVELOPMENT_MP_MAIN + UrlConstants.MP_MAIN_DESIGN;
                break;

            case UrlConstants.RUNNING_QA:
                main_design = UrlConstants.QA_MP_MAIN + UrlConstants.MP_MAIN_DESIGN;
                break;

            case UrlConstants.RUNNING_UAT:
                main_design = UrlConstants.UAT_MP_MAIN + UrlConstants.MP_MAIN_DESIGN;
                break;

            case UrlConstants.RUNNING_ALPHA:
                main_design = UrlConstants.ALPHA_MP_MAIN + UrlConstants.MP_MAIN_DESIGN;
                break;

            case UrlConstants.RUNNING_PRODUCTION:
                main_design = UrlConstants.PRODUCTION_MP_MAIN + UrlConstants.MP_MAIN_DESIGN;
                break;

            case UrlConstants.RUNNING_DEV:
                main_design = UrlConstants.DEV_MP_MAIN + UrlConstants.MP_MAIN_DESIGN;
                break;
        }
        return main_design;
    }

    /**
     * 消费者请求的url的公共部分
     *
     * @param runningDevelopment 运行环境
     * @return 消费者相关请求的url的公共部分
     */
    public static String getMPMain_Member(String runningDevelopment) {
        String main_member = null;
        switch (runningDevelopment) {
            case UrlConstants.RUNNING_DEVELOP:
                main_member = UrlConstants.DEVELOPMENT_MP_MAIN + UrlConstants.MP_MAIN_MEMBER;
                break;

            case UrlConstants.RUNNING_QA:
                main_member = UrlConstants.QA_MP_MAIN + UrlConstants.MP_MAIN_MEMBER;
                break;

            case UrlConstants.RUNNING_UAT:
                main_member = UrlConstants.UAT_MP_MAIN + UrlConstants.MP_MAIN_MEMBER;
                break;

            case UrlConstants.RUNNING_ALPHA:
                main_member = UrlConstants.ALPHA_MP_MAIN + UrlConstants.MP_MAIN_MEMBER;
                break;

            case UrlConstants.RUNNING_PRODUCTION:
                main_member = UrlConstants.PRODUCTION_MP_MAIN + UrlConstants.MP_MAIN_MEMBER;
                break;

            case UrlConstants.RUNNING_DEV:
                main_member = UrlConstants.DEV_MP_MAIN + UrlConstants.MP_MAIN_MEMBER;
                break;
        }
        return main_member;
    }

    /**
     * 我的资产请求url公共部分
     *
     * @param runningDevelopment 运行环境
     * @return 我的资产url公共部分
     */
    public static String getMPMain_Transaction(String runningDevelopment) {
        String main_transaction = null;
        switch (runningDevelopment) {
            case UrlConstants.RUNNING_DEVELOP:
                main_transaction = UrlConstants.DEVELOPMENT_MP_MAIN + UrlConstants.MP_MAIN_TRANSACTION;
                break;

            case UrlConstants.RUNNING_QA:
                main_transaction = UrlConstants.QA_MP_MAIN + UrlConstants.MP_MAIN_TRANSACTION;
                break;

            case UrlConstants.RUNNING_UAT:
                main_transaction = UrlConstants.UAT_MP_MAIN + UrlConstants.MP_MAIN_TRANSACTION;
                break;

            case UrlConstants.RUNNING_ALPHA:
                main_transaction = UrlConstants.ALPHA_MP_MAIN + UrlConstants.MP_MAIN_TRANSACTION;
                break;

            case UrlConstants.RUNNING_PRODUCTION:
                main_transaction = UrlConstants.PRODUCTION_MP_MAIN + UrlConstants.MP_MAIN_TRANSACTION;
                break;

            case UrlConstants.RUNNING_DEV:
                main_transaction = UrlConstants.DEV_MP_MAIN + UrlConstants.MP_MAIN_TRANSACTION;
                break;
        }
        return main_transaction;
    }


    /**
     * 判断现在运行的运行环境,供给IM使用
     *
     * @param runningDevelopment 运行环境
     * @return false 正式环境  ;  true 测试环境
     */
    public static boolean isRunningDevelopment(String runningDevelopment) {
        switch (runningDevelopment) {
            case UrlConstants.RUNNING_DEVELOP:
            case UrlConstants.RUNNING_QA:
            case UrlConstants.RUNNING_UAT:
            case UrlConstants.RUNNING_ALPHA:
            case UrlConstants.RUNNING_DEV:
                return true;
            case UrlConstants.RUNNING_PRODUCTION:
                return false;
            default:
                return false;
        }
    }

    /**
     * 获取不同开发环境的admin_user_id,供给IM使用
     *
     * @param runningDevelopment 运行环境
     * @return 不同环境环境的admin_user_id
     */
    public static int getAdmin_User_Id(String runningDevelopment) {
        int admin_user_id;
        switch (runningDevelopment) {
            case UrlConstants.RUNNING_PRODUCTION:
                /**
                 * 生产环境：20742718
                 * JuranAdmin@163.com
                 */
                admin_user_id = UrlConstants.PRODUCTION_ADMIN_USER_ID;
                break;
            default:
                /**
                 * UAT环境：20730165
                 *
                 * EZHome-Admin@autodesk.com
                 */
                admin_user_id = UrlConstants.UAT_ADMIN_USER_ID;
                break;
        }
        return admin_user_id;
    }

    /**
     * 获取不同开发环境的版本标识
     *
     * @param runningDevelopment 运行环境
     * @return 版本标识
     */
    public static String getVersionNumber(String runningDevelopment) {
        String versionPrefix = null;
        switch (runningDevelopment) {
            case UrlConstants.RUNNING_DEVELOP:
            case UrlConstants.RUNNING_DEV:
                versionPrefix = "D";
                break;
            case UrlConstants.RUNNING_QA:
                versionPrefix = "Q";
                break;
            case UrlConstants.RUNNING_UAT:
                versionPrefix = "U";
                break;
            case UrlConstants.RUNNING_ALPHA:
                versionPrefix = "A";
                break;
            case UrlConstants.RUNNING_PRODUCTION:
                versionPrefix = "P";
                break;
        }
        return versionPrefix;
    }

    private ApiManager() {
    }
}
