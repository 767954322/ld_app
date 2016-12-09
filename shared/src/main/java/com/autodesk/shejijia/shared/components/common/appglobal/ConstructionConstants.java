package com.autodesk.shejijia.shared.components.common.appglobal;

import com.autodesk.shejijia.shared.Config;

/**
 * Created by t_xuz on 8/15/16.
 * 一些公用的常量字段
 */
public class ConstructionConstants {

    //登陆登出广播接收器的action
    public static final String LOGIN_IN_ACTION = "com.easyhome.enterprise.login";
    public static final String LOGIN_OUT_ACTION = "com.easyhome.enterprise.logout";
    public static final String LOGIN_IN_ACTIVITY_FINISHED = "com.easyhome.login.activity.finished";

    //用户登陆成功后存在sp中的key
    public static final String USER_INFO = "user_info";

    //服务器端url地址(主线和电子表格):
    public static final String BASE_URL = Config.CONSTRUCTION_MAIN_URL;

    //服务器端url地址(问题列表):
    public static final String ISSUE_URL = Config.CONSTRUCTION_ISSUE_URL;

    //下拉刷新,上拉加载更多事件标记
    public static final String REFRESH_EVENT = "refresh";
    public static final String LOAD_MORE_EVENT = "loadMore";

    //项目列表请求tag
    public static final String REQUEST_TAG_LOAD_PROJECTS = "project_list"; //项目列表请求接口的requestTag
    public static final String REQUEST_TAG_STAR_PROJECTS = "star_project"; //星标项目 接口的requestTag
    public static final String PROJECT_LIST_BY_DATE = "projectLists_by_date"; //根据日期查询列表的标记
    public static final String PROJECT_LIST_BY_STATUS = "projectLists_by_status"; //根据状态查询列表的标记
    public static final String PROJECT_LIST_BY_LIKE = "projectLists_by_like"; //根据是否星标查询列表的标记
    public static final String REQUEST_TAG_FETCH_PLAN = "project_list";
    public static final String REQUEST_TAG_UPDATE_PLAN = "update_plan";

    //项目列表查询的状态
    public static final String PROJECT_STATUS_COMPLETE = "COMPLETED";//已完成
    public static final String PROJECT_STATUS_UNCOMPLETE = "UNCOMPLETED";//未完成
    public static final String PROJECT_STATUS_INPROGRESS = "INPROGRESS";//进行中

    //项目详情请求tag
    public static final String REQUEST_TAG_GET_PROJECT_DETAILS = "project_details";

    //Log tag
    public static final String LOG_TAG_REQUEST = "network_request";
    public static final String LOG_TAG_TASK = "TASK";

    // Request code
    public final static int REQUEST_CODE_PICK_DATE = 0x0110;
    public final static int REQUEST_CODE_PICK_DATE_RANGE = 0x0111;
    public final static int REQUEST_CODE_ADD_TASK = 0x0112;

    //Bundle key
    public static final String BUNDLE_KEY_PROJECT_ID = "project_id";
    public static final String BUNDLE_KEY_PROJECT_NAME = "project_name";
    public static final String BUNDLE_KEY_TASK_LIST = "task_list";
    public static final String BUNDLE_KEY_PLAN_OPERATION = "operation";
    public static final String BUNDLE_KEY_IS_PLAN_EDITING = "is_plan_editing";

    public static class TaskTemplateId {
        public static final String KAI_GONG_JIAO_DI = "kai_gong_jiao_di";//开工交底
        public static final String YINBIGONGCHENG_YANSHOU = "yinbigongcheng_yanshou";//隐蔽工程验收
        public static final String BI_SHUI_SHI_YAN = "bi_shui_shi_yan";//闭水实验
        public static final String ZHONGQI_YANSHOU = "zhongqi_yanshou";//中期验收
        public static final String JICHU_WANGONG_YANSHOU = "jichu_wangong_yanshou";//基础完工验收
        public static final String JUNGONG_YANSHOU = "jungong_yanshou";//竣工验收
    }

    public static class TaskCategory {
        public static final String TIME_LINE = "timeline";//开工交底
        public static final String INSPECTOR_INSPECTION = "inspectorInspection";//监理验收
        public static final String CLIENT_MANAGER_INSPECTION = "clientmanagerInspection";//客户经理验收
        public static final String CONSTRUCTION = "construction";//施工节点
        public static final String MATERIAL_MEASURING = "materialMeasuring";//主材测量
        public static final String MATERIAL_INSTALLATION = "materialInstallation";//主材安装
    }

    public static class MemberType {
        public static final String ADMIN = "admin";//系统管理员
        public static final String CLIENT_MANAGER = "clientmanager";//客户经理
        public static final String FORMAN = "foreman";//班长
        public static final String INSPECTOR = "inspector";//监理
        public static final String MATERIAL_STAFF = "materialstaff";//材料员
        public static final String DESIGNER = "designer";//设计师
        public static final String MEMBER = "member";//消费者
    }

    public static class TaskStatus {
        public static final String OPEN = "open";//未开始
        public static final String RESERVED = "reserved";//已预约
        public static final String RESERVING = "reserving";//待预约
        public static final String INPROGRESS = "inprogress";//进行中
        public static final String DELAYED = "delayed";//已延期
        public static final String QUALIFIED = "qualified";//合格
        public static final String UNQUALIFIED = "unqualified";//不合格
        public static final String RESOLVED = "resolved";//完成(或验收通过)
        public static final String REJECTED = "rejected";//验收拒绝
        public static final String REINSPECTION = "reinspection";//强制复验
        public static final String RECTIFICATION = "rectification";//监督整改
        public static final String REINSPECTING = "reinspecting";//复验中
        public static final String REINSPECTION_AND_RECTIFICATION = "reinspection_and_rectification";//复验并整改
        public static final String REINSPECT_RESERVING = "reinspection_reserving"; //复验待预约
        public static final String REINSPECT_RESERVED = "reinspection_reserved";//复验已预约
        public static final String REINSPECT_INPROGRESS = "reinspection_inprogress";//复验进行中
        public static final String REINSPECT_DELAY = "reinspection_delayed";//复验延期
        public static final String DELETED = "deleted";//已删除
    }

    public static class IssueTracking {
        public static final String BUNDLE_KEY_ISSUE_LIST_TYPE = "issue_tracking_type";//进入问题追踪的key
        public static final String ISSUE_LIST_TYPE_ALL_PROJECTS = "enterprise_all_tag";//角色查看服务端全部问题追踪
        public static final String ISSUE_LIST_TYPE_SINGLE_PROJECT = "enterprise_one_tag";//角色查看服务端单个问题追踪
        public static final String ISSUE_LIST_TYPE_CONSUMER = "consumer_tag";//消费端查看单个问题追踪
        public static final int ADD_ISSUE_DESCRIPTION_REQUEST_CODE = 1;//添加问题追踪描述返回请求码
        public static final String ADD_ISSUE_DESCRIPTION_RESULT_KEY = "key_issuedescription";//添加问题追踪描述返回KEY
    }

}