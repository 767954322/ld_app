package com.autodesk.shejijia.consumer.manager.constants;


/**
 * @author yangxuewu .
 * @version v1.0 .
 * @date 2016-6-7 .
 * @file JsonConstants.java .
 * @brief json数据需要用到一些常量 .
 */
public class JsonConstants {

    private JsonConstants() {
    }

    public static final String JSON_VS = "vs";
    public static final String JSON_VC = "vc";
    public static final String JSON_UA = "ua";
    public static final String JSON_OS = "os";
    public static final String JSON_SW = "sw";
    public static final String JSON_SH = "sh";
    public static final String JSON_CONTYPE = "contype";
    public static final String JSON_IMEI = "imei";
    public static final String JSON_MAC = "mac";
    public static final String JSON_CHANNEL = "channel";
    public static final String JSON_UDID = "udid";


    public static final String JSON_META = "meta";
    public static final String JSON_ECODE = "ecode";
    public static final String JSON_EMSG = "emsg";
    public static final String JSON_DATA = "data";

    public static final String RECOMMENDBRANDBEAN = "recommendBrandsBean";
    public static final String RECOMMENDBRANDSCFDBEAN = "mRecommendSCFDBean";

    /// M1.4  To test whether mobile phones has been registered
    public static final String JSON_MOBILENUMBER = "mobileNumber";
    public static final String JSON_MOBILEVAILDATA = "mobileVaildata";
    public static final String JSON_PASSWORD = "passWord";
    public static final String JSON_ROLE = "role";

    /// M1.5  Verify phone number + password and log in.
    public static final String JSON_LOGIN_MOBILENUMBER = "mobile_number";
    public static final String JSON_LOGIN_PASSWORD = "password";
    public static final String JSON_LOGIN_TYPE = "login_type";

    /// M1.3 Password to modify the  change password page .
    public static final String JSON_Verification_MOBILENUMBER = "mobileNumber";
    public static final String JSON_Verification_MOBILEVAILDATA = "mobileVaildata";

    /// D1.1 The new case .
    public static final String JSON_NEW_CASE = "case_id";

    /// D2.1 Design requirements .
    public static final String JSON_SEND_DESIGN_REQUIREMENTS_CONTACTS_NAME = "contacts_name";
    public static final String JSON_SEND_DESIGN_REQUIREMENTS_HOUSE_TYPE = "house_type";
    public static final String JSON_SEND_DESIGN_REQUIREMENTS_CONTACTS_MOBILE = "contacts_mobile";
    public static final String JSON_SEND_DESIGN_REQUIREMENTS_HOUSE_AREA = "house_area";
    public static final String JSON_SEND_DESIGN_REQUIREMENTS_DECORATION_BUDGET = "decoration_budget";
    public static final String JSON_SEND_DESIGN_REQUIREMENTS_DECORATION_STYLE = "decoration_style";
    public static final String JSON_SEND_DESIGN_REQUIREMENTS_DETAIL_DESC = "detail_desc";
    public static final String JSON_SEND_DESIGN_REQUIREMENTS_PROVINCE = "province";
    public static final String JSON_SEND_DESIGN_REQUIREMENTS_PROVINCE_NAME = "province_name";
    public static final String JSON_SEND_DESIGN_REQUIREMENTS_CITY = "city";
    public static final String JSON_SEND_DESIGN_REQUIREMENTS_CITY_NAME = "city_name";
    public static final String JSON_SEND_DESIGN_REQUIREMENTS_DISTRICT = "district";
    public static final String JSON_SEND_DESIGN_REQUIREMENTS_DISTRICT_NAME = "district_name";
    public static final String JSON_SEND_DESIGN_REQUIREMENTS_CLICK_NUMBER = "click_number";
    public static final String JSON_SEND_DESIGN_REQUIREMENTS_CONSUMER_MOBILE = "consumer_mobile";
    public static final String JSON_SEND_DESIGN_REQUIREMENTS_CONSUMER_NAME = "consumer_name";
    public static final String JSON_SEND_DESIGN_REQUIREMENTS_COMMUNITY_NAME = "community_name";
    public static final String JSON_SEND_DESIGN_REQUIREMENTS_TOILET = "toilet";
    public static final String JSON_SEND_DESIGN_REQUIREMENTS_LIVING_ROOM = "living_room";
    public static final String JSON_SEND_DESIGN_REQUIREMENTS_RENOVATION_STYLE = "renovation_style";
    public static final String JSON_SEND_DESIGN_REQUIREMENTS_ROOM = "room";

    /// D2.4 Get Customer ChatMessage List .
    public static final String JSON_DEMAND_LIST_OFFSET = "offset";
    public static final String JSON_DEMAND_LIST_LIMIT = "limit";
    public static final String JSON_DEMAND_LIST_SORT_BY = "sort_by";
    public static final String JSON_DEMAND_LIST_SORT_ORDER = "sort_order";
    public static final String BRAND_COUNT_LIMIT = "brand_count_limit";


    /// D2.5 Get My Fitment List .
    public static final String JSON_MY_FITMENT_OFFSET = "offset";
    public static final String JSON_MY_FITMENT_LIMIT = "limit";

    public static final String JSON_EXAMINE_RESPOND_OFFSET = "offset";
    public static final String JSON_EXAMINE_RESPOND_LIMIT = "limit";

    public static final String JSON_DELETE_EXAMINE_RESPOND_BIDDING_STATUS = "bidding_status";

    /// D2.2 On the need of consumer identity and identity to modify design requirements .
    public static final String JSON_MODIFY_DESIGNER_REQUIREMENT_NEED_ID = "need_id";
    public static final String JSON_MODIFY_DESIGNER_REQUIREMENT_CONTACTS_NAME = "contacts_name";
    public static final String JSON_MODIFY_DESIGNER_REQUIREMENT_HOUSE_TYPE = "house_type";
    public static final String JSON_MODIFY_DESIGNER_REQUIREMENT_CONTACTS_MOBILE = "contacts_mobile";
    public static final String JSON_MODIFY_DESIGNER_REQUIREMENT_HOUSE_AREA = "house_area";
    public static final String JSON_MODIFY_DESIGNER_REQUIREMENT_RENOVATION_BUDGET = "renovation_budget";
    public static final String JSON_MODIFY_DESIGNER_REQUIREMENT_RENOVATION_STYLE = "renovation_style";
    public static final String JSON_MODIFY_DESIGNER_REQUIREMENT_PROVINCE = "province";
    public static final String JSON_MODIFY_DESIGNER_REQUIREMENT_CITY = "city";
    public static final String JSON_MODIFY_DESIGNER_REQUIREMENT_DISTRICT = "district";
    public static final String JSON_MODIFY_DESIGNER_REQUIREMENT_NEIGHBOURHOODS = "neighbourhoods";
    public static final String JSON_MODIFY_DESIGNER_REQUIREMENT_ROOM = "room";
    public static final String JSON_MODIFY_DESIGNER_REQUIREMENT_LIVING_ROOM = "living_room";
    public static final String JSON_MODIFY_DESIGNER_REQUIREMENT_TOILET = "toilet";
    public static final String JSON_MODIFY_DESIGNER_REQUIREMENT_DETAIL_DESC = "detail_desc";

    /// M1.3 Real Name authentication interface .
    public static final String JSON_REAL_NAME_TRUE_NAME = "real_name";
    public static final String JSON_REAL_NAME_MOBILE_NUMBER = "mobile_number";
    public static final String JSON_REAL_NAME_BIRTHDAY = "certificate_no";
    public static final String JSON_REAL_NAME_POSITIVE_PHOTO = "photo_front_end";
    public static final String JSON_REAL_NAME_BACK_PHOTO = "photo_back_end";
    public static final String JSON_REAL_NAME_HEAD_PHOTO = "photo_in_hand";
    public static final String JSON_REAL_NAME_AUDIT_STATUS = "audit_status";
    public static final String JSON_REAL_NAME_FILE_ID = "file_id";
    public static final String JSON_REAL_NAME_FILE_URL = "file_url";
    public static final String JSON_REAL_NAME_FILE_NAME = "file_name";

    public static final String AMENDEMANDBEAN = "amendDemandBean";

    /// Q1.4 Measure House Form .


    ///I want bid.
    public static final String JSON_BID_DETAIL_DECLARATION = "declaration";
    public static final String JSON_BID_DETAIL_USER_NAME = "user_name";

    /// send measure form.
    public static final String JSON_MEASURE_FORM_AMOUNT = "amount";
    public static final String JSON_MEASURE_FORM_CHANNEL_TYPE = "channel_type";
    public static final String JSON_MEASURE_FORM_CITY = "city";
    public static final String JSON_MEASURE_FORM_CITY__NAME = "city_name";
    public static final String JSON_MEASURE_FORM_COMMUNITY_NAME = "community_name";
    public static final String JSON_MEASURE_FORM_CONTACTS_MOBILE = "contacts_mobile";
    public static final String JSON_MEASURE_FORM_CONTACTS_NAME = "contacts_name";
    public static final String JSON_MEASURE_FORM_DECORATION_BUDGET = "decoration_budget";
    public static final String JSON_MEASURE_FORM_DECORATION_STYLE = "decoration_style";
    public static final String JSON_MEASURE_FORM_DESIGN_BUDGET = "design_budget";
    public static final String JSON_MEASURE_FORM_DESIGNER_ID = "designer_id";
    public static final String JSON_MEASURE_FORM_DISTRICT = "district";
    public static final String JSON_MEASURE_FORM_DISTRICT_NAME = "district_name";
    public static final String JSON_MEASURE_FORM_HOUSE_AREA = "house_area";
    public static final String JSON_MEASURE_FORM_HOUSE_TYPE = "house_type";
    public static final String JSON_MEASURE_FORM_LIVING_ROOM = "living_room";
    public static final String JSON_MEASURE_FORM_HS_UID = "hs_uid";
    public static final String JSON_MEASURE_FORM_ORDER_TYPE = "order_type";
    public static final String JSON_MEASURE_FORM_PROVINCE = "province";
    public static final String JSON_MEASURE_FORM_PROVINCE_NAME = "province_name";
    public static final String JSON_MEASURE_FORM_ROOM = "room";
    public static final String JSON_MEASURE_FORM_SERVICE_DATE = "service_date";
    public static final String JSON_MEASURE_FORM_TOILET = "toilet";
    public static final String JSON_MEASURE_FORM_USER_ID = "user_id";
    public static final String JSON_MEASURE_FORM_THREAD_ID = "thread_id";
    public static final String ASSET_ID = "asset_id";
    public static final String MEASUREMENT="measurement_status";

    /// 我的资产.
    public static final String WITHDRAWARE_BALANCE_ACCOUNT_USER_NAME = "account_user_name";
    public static final String WITHDRAWARE_BALANCE_BANK_NAME = "bank_name";
    public static final String WITHDRAWARE_BALANCE_BRANCH_BANK_NAME = "branch_bank_name";
    public static final String WITHDRAWARE_BALANCE_DEPOSIT_CARD = "deposit_card";

    /// 全流程量房表单 .
    public static final String JSON_FLOW_MEASURE_FORM_SERVICE_DATE = "service_date";
    public static final String JSON_FLOW_MEASURE_FORM_USER_ID = "user_id";
    public static final String JSON_FLOW_MEASURE_FORM_DESIGNER_ID = "designer_id";
    public static final String JSON_FLOW_MEASURE_FORM_NEEDS_ID = "needs_id";
    public static final String JSON_FLOW_MEASURE_FORM_USER_NAME = "user_name";
    public static final String JSON_FLOW_MEASURE_FORM_MOBILE_NUMBER = "mobile_number";
    public static final String JSON_FLOW_MEASURE_FORM_ORDER_TYPE = "order_type";
    public static final String JSON_FLOW_MEASURE_FORM_AMOUNT = "amount";
    public static final String JSON_FLOW_MEASURE_FORM_ADJUSTMENT = "adjustment";
    public static final String JSON_FLOW_MEASURE_FORM_CHANNEL_TYPE = "channel_type";

    /// 北舒聊天室
    public static final String JSON_BEI_SHU_MEAL_CITY= "city";
    public static final String JSON_BEI_SHU_MEAL_CITY_NAME= "city_name";
    public static final String JSON_BEI_SHU_MEAL_COMMUNITY_NAME= "community_name";
    public static final String JSON_BEI_SHU_MEAL_CONSUMER_UID= "consumer_uid";
    public static final String JSON_BEI_SHU_MEAL_CONTACTS_MOBILE= "contacts_mobile";
    public static final String JSON_BEI_SHU_MEAL_CONTACTS_NAME= "contacts_name";
    public static final String JSON_BEI_SHU_MEAL_district= "district";
    public static final String JSON_BEI_SHU_MEAL_DISTRICT_NAME= "district_name";
    public static final String JSON_BEI_SHU_MEAL_PROVINCE= "province";
    public static final String JSON_BEI_SHU_MEAL_PROVINCE_NAME= "province_name";

    ///套餐发送预约表单
    public static final String JSON_PACKAGES_NAME = "customer_name";
    public static final String JSON_PACKAGES_PHONE_NUM = "mobile_phone";
    public static final String JSON_PACKAGES_PROVINCE = "province";
    public static final String JSON_PACKAGES_PROVINCE_NAME = "province_name";
    public static final String JSON_PACKAGES_CITY = "city";
    public static final String JSON_PACKAGES_CITY_NAME = "city_name";
    public static final String JSON_PACKAGES_DISTRICT = "district";
    public static final String JSON_PACKAGES_DISTRICT_NAME = "district_name";
    public static final String JSON_PACKAGES_ADDRESS = "address";
    public static final String JSON_PACKAGES_PROJECT_AREA = "project_area";
    public static final String JSON_PACKAGES_EXPENSE_BUDGET = "expense_budget";
    public static final String JSON_PACKAGES_PKG = "pkg";
    public static final String JSON_PACKAGES_PKG_NAME = "pkg_name";

    //大师预约表单
    public static final String JSON_MASTER_CONSUMER_NAME = "consumer_name";
    public static final String JSON_MASTER_CONSUMER_MOBILE = "consumer_mobile";
    public static final String JSON_MASTER_TYPE = "type";
    public static final String JSON_MASTER_CUSTOMER_ID = "customer_id";
    public static final String JSON_MASTER_CONSUMER_UID = "consumer_uid";
    public static final String JSON_MASTER_NAME = "name";
    public static final String JSON_MASTER_MEMBER_ID = "member_id";
    public static final String JSON_MASTER_HS_UID = "hs_uid";

    //新建清单

    public static final String JSON_NEW_INVENTORY_CITY = "city";
    public static final String JSON_NEW_INVENTORY_CITY_NAME = "city_name";
    public static final String JSON_NEW_INVENTORY_COMMUNITY_ADDRESS = "community_address";
    public static final String JSON_NEW_INVENTORY_COMMUNITY_NAME = "community_name";
    public static final String JSON_NEW_INVENTORY_CONSUMER_ID = "consumer_id";
    public static final String JSON_NEW_INVENTORY_CONSUMER_MOBILE = "consumer_mobile";
    public static final String JSON_NEW_INVENTORY_CUSTOMER_NAME = "consumer_name";
    public static final String JSON_NEW_INVENTORY_CONSUMER_UID = "consumer_uid";
    public static final String JSON_NEW_INVENTORY_DESIGNER_UID = "designer_uid";
    public static final String JSON_NEW_INVENTORY_DISTRICT = "district";
    public static final String JSON_NEW_INVENTORY_DISTRICT_NAME = "district_name";
    public static final String JSON_NEW_INVENTORY_PROVINCE = "province";
    public static final String JSON_NEW_INVENTORY_PROVINCE_NAME = "province_name";
    public static final String JSON_NEW_INVENTORY_DESIGN_PROJECT_ID = "design_project_id";
    public static final String JSON_NEW_INVENTORY_MAIN_PROJECT_ID  = "main_project_id";

    //清单列表
    public static final String JSON_PROJECT_NAME = "project_name";


    //添加主材
    public static final String JSON_STORE_NAME_INFOR_CHECK_LIST = "list";
    public static final String JSON_STORE_NAME_INFOR_LIST = "storeList";
    public static final String JSON_BACK_TOTAL_LIST = "totalList";
    //店面选择展示


}