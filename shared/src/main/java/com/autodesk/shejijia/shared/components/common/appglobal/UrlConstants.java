package com.autodesk.shejijia.shared.components.common.appglobal;

import com.autodesk.shejijia.shared.Config;
import static com.autodesk.shejijia.shared.components.common.appglobal.ApiManagerV2.RECOMMEND_PATH;

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


    public static String LOGIN_PATH = Config.LOGIN_PATH;

    /**
     * design-app的url公共部分 .
     */
    public static String MAIN_DESIGN = Config.DESIGN_PATH;

    /**
     * recommend-app的url公共部分 .
     */
    public static String MAIN_RECOMMEND = RECOMMEND_PATH;

    /**
     * member-app 的url的公共部分 .
     */
    public static String MAIN_MEMBER = Config.MEMBER_PATH;

    /**
     * 我的资产相关数据的url的公共部分 .
     */
    private static String MAIN_TRANSACTION = Config.TRANSACTION_PATH;

    //分享界面的url
    public static final String MP_MAIN_SHARE = Config.SHARE_PATH;
    public static final String MP_MAIN3_SHARE = Config.SHARE3_PATH;


    /**
     * 我要装修中的几张图片.
     */
    public static final String URL_GET_RECOMMEND_LIST = MAIN_DESIGN + "/materials-recommend-app/v1/api/designers/";

    /**
     * 搜索.
     */
    public static final String URL_GET_CASE_LIST_SEARCH = MAIN_DESIGN + "/cases/search?";

    /**
     * 3D案例库
     */
    public static final String URL_GET_CASE_LIST_D3 = MAIN_DESIGN + "/d3/cases?";

    /**
     * 3D案例详情
     */
    public static final String URL_GET_CASE_LIST_D3_DETAIL = MAIN_DESIGN + "/d3/cases/";

    /**
     * 发布装修需求
     */
    public static final String URL_SEND_DESIGN_REQUIREMENTS = MAIN_DESIGN + "/needs";

    /**
     * 发布精选装修需求
     */
    public static final String URL_SEND_DESIGN_SELECTION_REQUIREMENTS = MAIN_DESIGN + "/selection/demands";


    /**
     * 应标大厅.
     */
    public static final String URL_GET_SHOULD_HALL_LIST = MAIN_DESIGN + "/search/needs";

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
     */
    public static final String URL_GET_CASE_DETAILS_LIKE = MAIN_DESIGN + "/designers/d2/cases/like/";

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
     * 终止合作.
     */

    public static final String URL_STOP_COLLABORATION = MAIN_DESIGN + "/selection/termination/demands";

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
    public static final String URL_PUT_AMEND_DESIGNER_INFO = MAIN_MEMBER + "/members/";
    /**
     * 修改设计师两房费用.
     */
    public static final String URL_PUT_AMEND_DESIGNER_COST = MAIN_MEMBER + "/designers/";

    /**
     * 我要装修中的几张图片.
     */
    public static final String URL_PUT_SELECTION_DESIGNER_PICTURES = MAIN_DESIGN + "/selection/pictures";

    /**
     * 设计师详情.
     */
    public static final String URL_GET_SEEK_DESIGNER_DETAIL = MAIN_DESIGN + "/designers/";

    /**
     * 节点优化.
     */
    public static final String URL_GET_NODE_CHANGE = MAIN_DESIGN;

    /**
     * 设计师个人中心3d详情
     */
    public static final String URL_GET_SEEK_DESIGNER_ANONYMITY = MAIN_DESIGN + "/hs/prints/anonymity/designers/";

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
     * 精选修改需求
     */
    public static final String URL_POST_ELITE_MODIFY_MEAL = MAIN_DESIGN + "/selection/demands/";

    /**
     * 订单详情.
     */
    public static final String URL_GET_ORDER_DETAILS = MAIN_DESIGN + "/im/needs/";

    /**
     * 获取合同编号.
     */
    public static final String URL_GET_CONTRACT_NUM = MAIN_DESIGN + "/contracts/one";

    /**
     * 发送量房表单.
     */
    public static final String URL_POST_SEND_ESTABLISH_CONTRACT = MAIN_DESIGN + "/contracts?need_id=";


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
     * 消息中心接口
     */
    public static final String URL_MESSAGE_CENTER = MAIN_MEMBER + "/member/";


    /**
     * 获取全流程节点信息(带精选)
     */
    public static final String URL_ALL_WKFLOWSTATE_POINTE_INFORMATION = MAIN_DESIGN + "/configration/workflow/get/all?templdate_ids=1,2,3,4,5,6";

    /**
     * 交付物延期时间
     */
    public static final String URL_DELIVERY_DELAY_DATA = MAIN_DESIGN + "/demands/";
    public static final String URL_DELIVERY_DELAY = MAIN_DESIGN + "/demands/";
    /**
     * 关注列表
     */
    public static final String URL_DELETE_ATTENTION = MAIN_MEMBER + "/members/";

    public static final String URL_WITHDRAW_MEMBERS = MAIN_TRANSACTION + "/members/";
    /**
     * 获得设计师的设计费用区间
     */
    public static final String URL_DESIGNER_DESIGN_COST_RANGE = MAIN_MEMBER + "/designers/costs";
    /**
     * 获得工作室列表
     */
    public static final String URL_WORK_ROOM_LIST = MAIN_MEMBER + "/designers/search/studio?limit=20&offset=0&design_type=工作室";

    /**
     * 发布套餐预定
     */
    public static final String SEND_PACKAGES_FORM = MAIN_DESIGN + "/appointMeal/";

    /**
     * 获取店面信息
     * */


}
