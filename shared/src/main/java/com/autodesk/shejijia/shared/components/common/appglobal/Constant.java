package com.autodesk.shejijia.shared.components.common.appglobal;

import com.autodesk.shejijia.shared.components.common.utility.CommonUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-6-6 .
 * @file Constant.java .
 * @brief 常量列表 .
 */
public class Constant {
    /**
     * 版本前缀:
     * 第一位: W 代表workflow全流程, 有W全流程和B北舒两种
     * 第二位: 代表环境, A代表alpha环境, Q代表QA环境, D代表开发环境, P代表生产环境, U代表UAT环境
     * <p/>
     * 版本号: 0010
     * 第一位: 预留版号.
     * 第二位: 大功能新增.
     * 第三位: 对客户过版的编号.
     * 第四位: 对QA测试的跳版编号.
     */
    private static final String WB = "W";
    // private static final String WB = "B";
    private static final String VERSION_IMIE = "0.0.0.10";
    public static final String VERSION_NUMBER = "Version " + WB + ApiManager.getVersionNumber(ApiManager.RUNNING_DEVELOPMENT) +
            CommonUtils.getAppVersionName(AdskApplication.getInstance());

    /**
     * 登录用户数据用到的key
     */
    public static class UerInfoKey {
        /// 存储到SharedPreference中的key .
        public static final String USER_INFO = "UserInfo";
        /// 用户类型:设计师 .
        public static final String DESIGNER_TYPE = "designer";
        /// 用户类型:消费者 .
        public static final String CONSUMER_TYPE = "member";

    }

    /**
     * Flow pass parameter .
     */
    public static class ProjectMaterialKey {
        public static final String IM_TO_FLOW_DESIGNER_ID = "imToFlowDesigner";
        public static final String IM_TO_FLOW_NEEDS_ID = "imToFlowNeeds";
        public static final String IM_TO_FLOW_NODE_ID = "imToFlowNode";
    }

    /**
     * Jump from state .
     */
    public static class WorkFlowStateKey {
        public static final int STEP_DECORATION = 0;
        public static final int STEP_IM = 1;
        public static final int STEP_FLOW = 2;
        public static final int STEP_MATERIAL = 3;
        public static final String JUMP_FROM_STATE = "jump_from_state";
    }

    /**
     * 二维码扫描的key
     */
    public static class QrResultKey {
        public static final String SCANNER_RESULT = "result";
    }

    /**
     * 数字对应的常量
     */
    public static class NumKey {
        public static final String _ONE = "-1";
        public static final String ZERO = "0";
        public static final String ONE = "1";
        public static final String TWO = "2";
        public static final String THREE = "3";
        public static final String FOUR = "4";
        public static final String FIVE = "5";
        public static final String SIX = "6";
        public static final String ZERO_ONE = "01";
        public static final String ZERO_TWO = "02";
        public static final String ZERO_THREE = "03";
    }

    /**
     * json文件对应的名字的key
     */
    public static class JsonLocationKey {
        public static final String SPACE_JSON = "space.json";
        public static final String LIVING_ROOM_JSON = "living_room.json";
        public static final String ROOM_JSON = "room.json";
        public static final String TOILET_JSON = "toilet.json";
        public static final String STYLE_JSON = "style.json";
        public static final String AREA_JSON = "area.json";
    }

    /**
     * 全流程分发到每个状态的key
     */
    public static class BundleKey {
        public static final String BUNDLE_ASSET_NEED_ID = "ASSET_NEEDS_ID";
        public static final String BUNDLE_SUB_NODE_ID = "WORK_FLOW_SUB_NODE_ID";
        public static final String BUNDLE_ACTION_NODE_ID = "WORK_ACTION_NODE_ID";
        public static final String BUNDLE_DESIGNER_ID = "DESIGNER_ID";
//        public static final String IM_TO_FLOW_DESIGNER_ID = "imToFlowDesigner";
//        public static final String IM_TO_FLOW_NEEDS_ID = "imToFlowNeeds";
//        public static final String IM_TO_FLOW_NODE_ID = "imToFlowNode";
    }

    /**
     * 消费者个人中心fragment
     */
    public static class ConsumerPersonCenterFragmentKey {
        public static final String NICK_NAME = "NICK_NAME";
        public static final String CONSUMER_PERSON = "CONSUMER_PERSON";

    }

    /**
     * 消费者量房页面Key
     */
    public static class ConsumerMeasureFormKey {
        public static final String DESIGNER_ID = "DESIGNER_ID";
        public static final String HS_UID = "HS_UID";
        public static final String MEASURE = "MEASURE";
    }

    /**
     * 消费者普通订单fragment
     */
    public static class ConsumerDecorationFragment {

        public static final String designer_id = "designer_id";
        public static final String hs_uid = "hs_uid";
        public static final String NEED_ID = "NEED_ID";
    }

    /**
     * 查看设计师详情页面
     */
    public static class SeekDesignerDetailKey {
        public static String MEASURE_FREE = "MEASURE_FREE";
        public static String DESIGNER_ID = "DESIGNER_ID";
        public static String NEEDS_ID = "NEEDS_ID";
        public static String SEEK_TYPE = "TYPE";
        public static String SEEK_DESIGNER_DETAIL = "SEEK_DESIGNER_DETAIL";
        public static String HS_UID = "HS_UID";
        public static String FLOW_STATE = "FLOW_STATE";
    }

    /**
     * 案例详情页面
     */
    public static class CaseLibraryDetail {
        public static final String CASE_URL = "url";
        public static final String CASE_DETAIL_BEAN = "CASE_DETAIL_BEAN";
        public static final String JPG = "HD.jpg";
        public static final String CASE_ID = "case_id";
    }

    /**
     * 筛选
     */
    public static class CaseLibrarySearch {
        public static final String SEARCH_TYPE = "TYPE";
        public static final String AREA_INDEX = "areaIdex";
        public static final String HOUSING_INDEX = "housingIdex";
        public static final String STYLE_INDEX = "styleIdex";
        public static final String CONTENT_BEAN = "contentBean";

    }

    /**
     * 个人中心会员信息
     */
    public static class PersonCenterKey {
        public static final String NICK_NAME = "nick_name";
        public static final String GENDER = "gender";
        public static final String HOME_PHONE = "home_phone";
        public static final String BIRTHDAY = "birthday";
        public static final String ZIP_CODE = "zip_code";
        public static final String PROVINCE_NAME = "province_name";
        public static final String PROVINCE = "province";
        public static final String CITY_NAME = "city_name";
        public static final String CITY = "city";
        public static final String DISTRICT_NAME = "district_name";
        public static final String DISTRICT = "district";
    }

    /**
     * 个人中心标记标签
     */
    public static class PersonCenterTagKey {
        public static final String ESSENTIAL_INFO_TAG = "ESSENTIAL_INFO_TAG";
        public static final String DESIGNER_CONTENT = "DESIGNER_CONTENT";
        public static final String CONSUMER_CONTENT = "CONSUMER_CONTENT";
        public static final String MEASURE_CONTENT = "MEASURE_CONTENT";
        public static final String DESIGNER_INFO = "DesignerInfo";
        public static final String CONSUMER_INFO = "ConsumerInfo";
        public static final String MEASURE_HOUSE = "MeasureHouse";
    }

    /**
     * 设计师个人中心
     */
    public static class DesignerCenterBundleKey {
        public static final String DESIGNER_ID = "DESIGNER_ID";
        public static final String HS_UID = "HS_UID";
        public static final String MEMBER = "member_id";
        public static final String MEMBER_INFO = "MEMBER_INFO";
        public static final String HOUSE_COST = "HOUSE_COST";
        public static final String DESIGNER_INFO_DETAILS = "designerInfoDetails:";
        public static final String AUDIT_STATUS = "AUDIT_STATUS";
    }

    /**
     * 设计师个人中心页面BeiShuMealActivity
     */
    public static class DesignerBeiShuMeal {
        public static final String SKIP_DESIGNER_PERSONAL_CENTER = "DesignerPersonalCenterFragment";
    }

    /**
     * 设计师个人中心MyPropertyActivity
     */
    public static class DesignerMyPropertyKey {

        public static final String MY_PROPERTY_BEAN = "MY_PROPERTY_BEAN";
    }

    /**
     * 设计师WithdrawActivity
     */
    public static class DesignerWithDraw {

        public static final String AMOUNT = "AMOUNT";
        public static final String IS_SUCCESS = "IS_SUCCESS";
    }


    /**
     * 设计师基础信息
     */
    public static class DesignerBasicInfoKey {
        public static final String DESIGN_PRICE_MIN = "design_price_min";
        public static final String DESIGN_PRICE_MAX = "design_price_max";
        public static final String MEASUREMENT_PRICE = "measurement_price";
        public static final String STYLE_LONG_NAMES = "style_long_names";
        public static final String INTRODUCTION = "introduction";
        public static final String EXPERIENCE = "experience";
        public static final String PERSONAL_HONOUR = "personal_honour";
        public static final String DIY_COUNT = "diy_count";
        public static final String CASE_COUNT = "case_count";
        public static final String THEME_PIC = "theme_pic";
    }

    /**
     * 设计合同
     */
    public static class EstablishContractKey {
        public static final String NAME = "name";
        public static final String MOBILE = "mobile";
        public static final String ZIP = "zip";
        public static final String EMAIL = "email";
        public static final String ADDR = "addr";
        public static final String ADDRDE = "addrDe";
        public static final String DESIGN_SKETCH = "design_sketch";
        public static final String RENDER_MAP = "render_map";
        public static final String DESIGN_SKETCH_PLUS = "design_sketch_plus";
        public static final String CONTRACT_NO = "contract_no";
        public static final String CONTRACT_CHARGE = "contract_charge";
        public static final String CONTRACT_FIRST_CHARGE = "contract_first_charge";
        public static final String DESIGNER_ID = "designer_id";
        public static final String CONTRACT_TYPE = "contract_type";
        public static final String CONTRACT_TEMPLATE_URL = "contract_template_url";
        public static final String CONTRACT_DATA = "contract_data";
    }

    /**
     * 上传设计交付物
     */
    public static class DeliveryBundleKey {

        public static final String DELIVERY_ENTITY = "DELIVERY_ENTITY";
        public static final String LEVEL = "LEVEL";
        public static final String DELIVERY_STATE = "DELIVERY_STATE";
        public static final String LEVEL_BUNDLE = "LEVEL_BUNDLE";

        public static final String THREE_PLAN_ALL = "THREE_PLAN_ALL";/// 发送到第二页面的所有3D方案 .
        public static final String THREE_PLAN = "THREE_PLAN";/// 选中某一个3D方案，要传递给第二页面.
    }

    public static class DeliveryShowBundleKey {
        public static final String _BUNDLE_INTENT = "SHOW_BUNDLE_INTENT";
        public static final String _IMAGE_BEAN = "SHOW_IMAGE_BEAN";
        public static final String _LEVEL_TAG = "SHOW_LEVEL_0";
        public static final String _JAVA_BEAN = "DESIGN_OR_MEASURE_DELIVERY";

        public static final String DESIGN_DELIVERY_LEVEL_ZERO = "DESIGN_DELIVERY";
        public static final String DESIGN_DELIVERY_OTHERS = "MEASURE_DELIVERY";
    }

    /**
     * 交付物选择
     */
    public static class DeliveryResponseBundleKey {

        public static final String DESIGN_FILE_ID = "DESIGN_FILE_ID";
        public static final String FILE_LINK = "FILE_LINK";
        public static final String DESIGN_ASSET_ID = "DESIGN_ASSET_ID";
        public static final String RESPONSE_BUNDLE = "RESPONSE_BUNDLE";
    }

    /**
     * 交付物类型
     */
    public static class DeliveryTypeBundleKey {

        public static final String TYPE_MEASURE_DELIVERY = "0";  /// 量房交付 .
        public static final String TYPE_DESIGN_DELIVERY = "1";  /// 设计交付 .

        /**
         * 0 :  渲染图（render）
         * 1 :  3D方案（3D）  
         * 2 :  BOM
         * 3 :  CAD    (2D平面图)  
         * 4 :  360全景图
         * 5 :  装修案例图
         * 6 :  截图  （SNAPSHOT）
         * 7 :  其它  （Others）
         * 8 ：3D平面图URL
         * 9 ：2D平面图URL
         * 10：3D户型图URL
         * <p/>
         * 我的3d方案	10
         * 渲染图设计	0、4
         * 设计图纸	3
         * 材料清单	2
         * <p/>
         */
        public static final String USAGE_TYPE_THREE_PLAN_DELIVERY = "10";  /// 我的3d设计方案 .
        public static final String USAGE_TYPE_RENDERING_DESIGN_DELIVERY_0 = "0";  ///渲染图设计1 .
        public static final String USAGE_TYPE_READERING_DESIGN_DELIVERY_4 = "4";  ///渲染图设计2 .
        public static final String USAGE_TYPE_DESIGN_BLUEPRINT_DELIVERY = "3";  ///设计图纸 .
        public static final String USAGE_TYPE_MATERIAL_BILL_DELIVERY = "2";  ///材料清单 .
    }

    /**
     * 全流程状态机
     */
    public static class WkFlowStatePath {
        public static final String WF_NODE_ID_NAME_CONSUMER = "MPCurrentNodeConsumer.json";                                  /// wk_template_id对应节点 .
        public static final String WF_NODE_ID_NAME_DESIGNER = "MPCurrentNodeDesigner.json";

        public static final String WF_BID_SUB_NODE_ID_NAME_CONSUMER = "MPBidSubNodeConsumer.json";                  /// 应标消费者 .
        public static final String WF_BID_SUB_NODE_ID_NAME_DESIGNER = "MPBidSubNodeDesigner.json";                       /// 应标设计师 .
        public static final String WF_CHOOSE_SUB_NODE_ID_NAME_CONSUMER = "MPChooseSubNodeConsumer.json";  /// 量房消费者 .
        public static final String WF_CHOOSE_SUB_NODE_ID_NAME_DESIGNER = "MPChooseSubNodeDesigner.json";       /// 量房设计师 .
    }

    /**
     * 数据库转码
     */
    public static class DbTag {
        public static final String PROVINCE = "province";
        public static final String CITY = "city";
        public static final String DISTRICT = "district";
        public static final String CODE_NAME = "region_name";

        public static final String PROVINCE_TABLE_NAME = "t_address_province";
        public static final String CITY_TABLE_NAME = "t_address_city";
        public static final String DISTRICT_TABLE_NAME = "t_address_town";
    }

    /**
     * 性别
     */
    public static class Gender {
        public static final String SECRECY = "保密";
        public static final String GIRL = "女";
        public static final String BOY = "男";
    }

    public static final String LOGOUT = "LOGOUT";

    /**
     * 网络常量设置
     */
    public static class NetBundleKey {
        //        public static final String X_TOKEN = "X-Token";
        public static final String X_TOKEN_PREFIX = "Basic ";
        public static final String X_TOKEN = "Authorization";
        public static final String X_XTOKEN = "X-Token";
        public static final String CONTENT_TYPE = "Content-Type";
        public static final String MEMBER_TYPE = "Member-Type";
        public static final String ACS_TOKEN = "ACS-Token";
        public static final String HS_UID = "hs_uid";

        public static final String APPLICATON_JSON = "application/json";
        public static final String UTF_8 = "UTF-8";
        public static final String ISO_8859_1 = "ISO-8859-1";
        public static final String MIME_TYPE_TEXT_HTML = "text/html";
    }

    /**
     * 我的家装订单
     */
    public static class DecorationBundleKey {
        public static final String DECORATION_NEEDS_KEY = "NeedsListEntity";
        public static final String DECORATION_BEISHU_NEEDS_KEY = "BeishuNeedsListEntity";
    }

    /**
     * 需求详情
     */
    public static class DemandDetailBundleKey {
        public static final String DEMAND_NEEDS_ID = "needs_id";
        public static final String DEMAND_TYPE = "TYPE";
        public static final String DEMAND_BID_STATUS = "BID_STATUS";

        public static final String TYPE_DESIGNERORDER_ACTIVITY = "DesignerOrderActivity";
        public static final String TYPE_BEING_FRAGMENT = "Be_being_Fragment";
        public static final String TYPE_CUSTOMBID_FRAGMENT = "Custom_Bid_Fragment";
    }

    /**
     * 文件类型
     */
    public static class DocumentTypeKey {

        public static final String TYPE_DOCX = "docx";
        public static final String TYPE_DOC = "doc";
        public static final String TYPE_XLSX = "xlsx";
        public static final String TYPE_XLS = "xls";
        public static final String TYPE_PDF = "pdf";
        public static final String TYPE_PNG = "png";
        public static final String TYPE_JPG = "jpg";
    }

    /**
     * 设计师FlowEstablishContraActivity
     */
    public static class DesignerFlowEstablishContract {

        public static final String DESIGNSKETCH = "designSketch";
        public static final String EFFECTIVEPICTURE = "effectivePicture";
        public static final String ADDCURRENCY = "addCurrency";
        public static final String DESIGNPAY = "designPay";
        public static final String DESIGNFIRSTFEE = "designFirstFee";
        public static final String DESIGNBALANCEFEE = "designBalanceFee";
    }

    private Constant() {
    }
}
