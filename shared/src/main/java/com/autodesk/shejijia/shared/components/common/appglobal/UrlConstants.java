package com.autodesk.shejijia.shared.components.common.appglobal;

/**
 * @author yangxuewu .
 * @version v1.0 .
 * @date 2016-6-7 .
 * @file UrlConstants.java .
 * @brief url地址整合 .
 */
public final class UrlConstants {
    private UrlConstants() {
    }

    /// 开发环境 .
    public static final String RUNNING_DEVELOP = "RUNNING_DEVELOP";
    /// QA环境 .
    public static final String RUNNING_QA = "RUNNING_QA";
    /// UAT331环境 .
    public static final String RUNNING_UAT = "RUNNING_UAT";
    /// UAT415或者Alpha环境 .
    public static final String RUNNING_ALPHA = "RUNNING_ALPHA";
    /// 正式(PRODUCTION)环境  .
    public static final String RUNNING_PRODUCTION = "RUNNING_PRODUCTION";
    /// alpha dev境 .
    public static final String RUNNING_DEV = "RUNNING_DEV";
    /// DOCKER_DESIGN环境
    public static final String RUNNING_DOCKER_DESIGN = "RUNNING_DOCKER_DESIGN";
//    /// DOCKER_MEMBER
//    public static final String RUNNING_DOCKER_MENBER = "RUNNING_DOCKER_MENBER";
//    /// DOCKER_TRANSACTION
//    public static final String RUNNING_DOCKER_TRANSACTION = "RUNNING_DOCKER_TRANSACTION";

    /**
     * 如需更改环境，需到ApiManager中修改RUNNING_DEVLEOPMENT的值
     */
    public static String LOGIN_PATH = ApiManager.getLoginPath(ApiManager.RUNNING_DEVELOPMENT);
    public static String LOGOUT_PATH = ApiManager.getLogoutPath(ApiManager.RUNNING_DEVELOPMENT);

    /**
     * 拼接请求设计师相关数据的url的公共部分 .
     * 比如: http://124.207.32.252:6091/design-app/v1/api .
     */
    public static String MAIN_DESIGN = ApiManager.getMPMain_Design(ApiManager.RUNNING_DEVELOPMENT);

    /**
     * 拼接请求设计师相关数据的url的公共部分 .
     * 比如: http://124.207.32.252:6091/member-app/v1/api .
     */
    public static String MAIN_MEMBER = ApiManager.getMPMain_Member(ApiManager.RUNNING_DEVELOPMENT);

    /**
     * 我的资产相关数据的url的公共部分 .
     */
    private static String MAIN_TRANSACTION = ApiManager.getMPMain_Transaction(ApiManager.RUNNING_DEVELOPMENT);

    /// 登录 .
    public static final String MP_MAIN_LOGIN_PATH = "http://121.69.19.182:8100/SSO_login.html?caller=shejijia&browser_type=android";
    //    public static final String MP_MAIN_LOGIN_PATH_UAT = "http://uat331www.gdfcx.net/sso/SSO_login.html?caller=shejijia&browser_type=android";
    //    public static final String MP_MAIN_LOGIN_PATH_ALPHA = "http://uat415www.gdfcx.net/sso/SSO_login.html?caller=shejijia&browser_type=android";
    public static final String MP_MAIN_LOGIN_PATH_UAT = "http://uat-www.gdfcx.net/sso/SSO_login.html?caller=shejijia&browser_type=android";
    public static final String MP_MAIN_LOGIN_PATH_ALPHA = "http://alpha-www.gdfcx.net/sso/SSO_login.html?caller=shejijia&browser_type=android";
    public static final String MP_MAIN_LOGIN_PATH_PRODUCTION = "http://www.shejijia.com/sso/SSO_login.html?caller=shejijia&browser_type=android";
    public static final String MP_MAIN_LOGIN_PATH_DEV = "http://dev-www.gdfcx.net/sso/SSO_login.html?caller=shejijia&browser_type=android";

    /// 登出 .
    public static final String MP_MAIN_LOGOUT_PATH = "http://121.69.19.182:8100/SSO_logout.html";
    public static final String MP_MAIN_LOGOUT_PATH_UAT = "http://uat331www.gdfcx.net/sso/SSO_logout.html?caller=shejijia&browser_type=android";
    public static final String MP_MAIN_LOGOUT_PATH_ALPHA = "http://uat415www.gdfcx.net/sso/SSO_logout.html?caller=shejijia&browser_type=android";
    public static final String MP_MAIN_LOGOUT_PATH_PRODUCTION = "http://www.shejijia.com/sso/SSO_login.html?caller=shejijia&browser_type=android";
    public static final String MP_MAIN_LOGOUT_PATH_DEV = "http://dev-www.gdfcx.net/sso/SSO_login.html?caller=shejijia&browser_type=android";

    /// 安全中心 .
    public static final String DEVELOPMENT_SECURTY = "http://cas.juranzaixian.com.cn/myspace/member/account_security.htm";
    public static final String UAT_SECURTY = "http://cas.juranzx.com.cn/myspace/member/account_security.htm";

    /// 开发环境地址 .
    public static final String DEVELOPMENT_MP_MAIN = "http://124.207.32.252:6091";

    /// QA环境地址 .
    public static final String QA_MP_MAIN = "http://192.168.6.25:8080";

    //新环境
    public static final String DOCKER_HAWKEYE_DESIGN = "http://192.168.88.155:8080";

    //新环境
    public static final String DOCKER_HAWKEYE_MEMBER = "http://192.168.88.153:8080";

    //新环境
    public static final String DOCKER_HAWKEYE_TRANSCATION = "http://192.168.88.152:8080";


    /// UAT = UAT331环境地址 .
//    public static final String UAT_MP_MAIN = "http://uat331api.gdfcx.net:8080";
    public static final String UAT_MP_MAIN = "http://uat-api.gdfcx.net:8080";

    /// ALPHA = UAT415环境地址 .
//    public static final String ALPHA_MP_MAIN = "http://uat415api.gdfcx.net";
    public static final String ALPHA_MP_MAIN = "http://alpha-api.gdfcx.net";

    /// alpha-dev环境 .
    public static final String DEV_MP_MAIN = "http://dev-api.gdfcx.net";

    /// PRODUCTION .
    public static final String PRODUCTION_MP_MAIN = "http://api.shejijia.com";

    /// 生产环境user_id .
    public static final int PRODUCTION_ADMIN_USER_ID = 20742718;
    /// 开发环境user_id .
    public static final int UAT_ADMIN_USER_ID = 20730165;


    public static final String MP_MAIN_TRANSACTION = "/transaction-app/v1/api";
    public static final String MP_MAIN_DESIGN = "/design-app/v1/api";
    public static final String MP_MAIN_MEMBER = "/member-app/v1/api";

    /**
     * 搜索.
     */
    public static final String URL_GET_CASE_LIST_SEARCH = MAIN_DESIGN + "/cases/search?";

    /**
     * 发布装修需求
     */
    public static final String URL_SEND_DESIGN_REQUIREMENTS = MAIN_DESIGN + "/needs";

    /**
     * 应标大厅.
     */
    public static final String URL_GET_SHOULD_HALL_LIST = MAIN_DESIGN + "/search/needs";  //

    /**
     * 应标大厅详情页 .
     */
    public static final String URL_GET_BID_HALL_DETAIL = MAIN_DESIGN + "/needs/";
    /**
     * 我的家装订单.
     */
    public static final String URL_GET_MY_DECORATION_LIST = MAIN_DESIGN + "/member/";
    /**
     * 案例库详情.
     */
    public static final String URL_GET_CASE_DETAILS = MAIN_DESIGN + "/cases/";
    /**
     * 点赞接口
     * http://192.168.120.90:8080/design-app/v1/api/designers/d2/cases/like/{asset_id}    
     */
    public static final String URL_GET_CASE_DETAILS_LIKE = MAIN_DESIGN + "/cases/like/";

    /**
     * 实名认证.
     */
    public static final String URL_POST_REAL_NAME = MAIN_MEMBER + "/designers/";

    /**
     * 设计师应标.
     */
    public static final String URL_POST_I_WANT_SHOULD_BID = MAIN_DESIGN + "/needs/";

    /**
     * 找设计师.
     */
    public static final String URL_FIND_DESIGNER = MAIN_MEMBER + "/designers";

    /**
     * 获取设计师详情.
     */
    public static final String URL_GET_DESIGNER_INFO = MAIN_MEMBER + "/designers/";

    /**
     * 获取消费者详情.
     */
    public static final String URL_GET_CONSUMER_INFO = MAIN_MEMBER + "/members/";
    /**
     * 修改消费者个人信息.
     */
    public static final String URL_PUT_AMEND_CONSUMER_INFO = MAIN_MEMBER + "/members/";
    /**
     * 修改设计师信息.
     */
    public static final String URL_PUT_AMEND_DESIGNER_INFO = MAIN_MEMBER + "/members/"; //
    /**
     * 修改设计师两房费用.
     */
    public static final String URL_PUT_AMEND_DESIGNER_COST = MAIN_MEMBER + "/designers/";

    /**
     * 设计师详情.
     */
    public static final String URL_GET_SEEK_DESIGNER_DETAIL = MAIN_DESIGN + "/designers/";

    /**
     * 设计师详情-设计师信息.
     */
    public static final String URL_GET_SEEK_DESIGNER_DETAIL_HOME = MAIN_MEMBER + "/designers/";

    /**
     * 修改上传设计师头像.
     */
    public static final String URL_PUT_AMEND_DESIGNER_INFO_PHOTO = MAIN_MEMBER + "/members/avatars"; //

    /**
     * 是否是实名认证.
     */
    public static final String URL_GET_IS_REALY_NAME = MAIN_MEMBER + "/designers/";

    /**
     * 我的应标.
     */
    public static final String URL_GET_MY_BID = MAIN_DESIGN + "/designers/";

    /**
     * 设计师订单.
     */
    public static final String URL_GET_ORDER = MAIN_DESIGN + "/designers/";

    /**
     * 北舒设计师订单
     */
    public static final String URL_GET_BEI_SHU_ORDER = MAIN_DESIGN + "/beishu/";

    /**
     * 北舒订单表单.
     */
    public static final String URL_POST_BEI_SHU_MEAL = MAIN_DESIGN + "/beishu/needs/";

    /**
     * 修改需求,终止需求.
     */
    public static final String URL_POST_MODIFY_MEAL = MAIN_DESIGN + "/needs/";

    /**
     * 订单详情.
     */
    public static final String URL_GET_ORDER_DETAILS = MAIN_DESIGN + "/im/needs/"; //

    /**
     * 获取合同编号.
     */
    public static final String URL_GET_CONTRACT_NUM = MAIN_DESIGN + "/contracts/one";

    /**
     * 发送量房表单.
     */
    public static final String URL_POST_SEND_ESTABLISH_CONTRACT = MAIN_DESIGN + "/contracts?need_id=";

    /**
     * 设计师同意量房.
     */
    public static final String URL_PUT_AGREE_MEASURE_HOUSE = MAIN_DESIGN + "/orders/";

    /**
     * 设计师拒绝量房.
     */
    public static final String URL_PUT_REFUSED_MEASURE_HOUSE = MAIN_DESIGN + "/refused/";

    /**
     * 消费者同意应标
     */
    public static final String URL_AGREE_RESPONSE_BID = MAIN_DESIGN + "/orders?is_need=true";

    /**
     * 消费者自选设计师量房.
     */
    public static final String URL_ONESELF_AGREE_RESPONSE_BID = MAIN_DESIGN + "/orders?is_need=false";

    /**
     * 支付回调.
     */
    public static final String URL_PAY = MAIN_DESIGN + "/pay/alipay/app/";

    /**
     * 设计交付物.
     */
    public static final String URL_DELIVER = MAIN_DESIGN + "/hs/prints/";
    /**
     * 我的资产.
     */
    public static final String URL_MY_PROPERTY = MAIN_TRANSACTION + "/withdraw/";
    /**
     * 交易记录.
     */
    public static final String URL_TRANSACTION_RECORD = MAIN_TRANSACTION + "/finance/queryOrderList/";
    /**
     * 提现记录.
     */
    public static final String URL_WITHDRAW_RECORD = MAIN_TRANSACTION + "/finance/designerWithdrawalsTransLog/";
    /**
     * 提现确认并提交 .
     */
    public static final String URL_WITHDRAW_BALANCE = MAIN_TRANSACTION + "/withdraw/balance/";

    /**
     * 北舒套餐聊天入口
     */
    public static final String URL_BeiShuMeal_Chat = MAIN_DESIGN + "/beishu/needs/";

    /**
     * 消息中心接口
     */
    public static final String URL_MESSAGE_CENTER = MAIN_MEMBER + "/member/";

    /**
     * 交付物延期
     */
    public static final String URL_Delivery_Delay = MAIN_DESIGN + "/demands/";
    public static final String URL_Delivery_Delay_Data = MAIN_DESIGN + "/demands/";

    /**
     * 获取全流程节点信息网址
     * */

    public static final String URL_WkFlowState_pointe_Information = "http://192.168.88.155:8080/design-app/v1/api/fullflow/message";

    public static final String URL_DELIVERY_DELAY = MAIN_DESIGN + "/demands/";
    /**
     * 交付物延期时间
     */
    public static final String URL_DELIVERY_DELAY_DATA = MAIN_DESIGN + "/demands/";
    /**
     * 关注列表
     * "http://dev-www.gdfcx.net/member-app/v1/api"
     */
    public static final String URL_DELETE_ATTENTION = MAIN_MEMBER + "/members/";
}
