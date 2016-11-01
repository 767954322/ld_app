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

    //项目列表页同级fragment的tag
    public static final String TASK_LIST_FRAGMENT = "TaskListFragment";
    public static final String ISSUE_LIST_FRAGMENT = "IssueListFragment";
    public static final String GROUP_CHAT_FRAGMENT = "GroupChatFragment";

    //我页流程式fragment对应的tag
    public static final String PERSONAL_MORE_FRAGMENT = "MoreFragment";
    public static final String PERSONAL_POJECTLIST_FRAGMENT = "ProjectListFragment";

    //下拉刷新,上拉加载更多事件标记
    public static final String REFRESH_EVENT = "refresh";
    public static final String LOAD_MORE_EVENT = "loadMore";

    //项目列表请求tag
    public  static final String REQUEST_TAG_LOAD_PROJECTS = "project_list"; //项目列表请求接口的requestTag
    public static final String PROJECT_LIST_BY_DATE = "projectLists_by_date"; //根据日期查询列表的标记
    public static final String PROJECT_LIST_BY_STATUS = "projectLists_by_status"; //根据状态查询列表的标记
    public static final String PROJECT_LIST_BY_LIKE = "projectLists_by_like"; //根据是否星标查询列表的标记

    //项目列表查询的状态
    public static final String PROJECT_STATUS_COMPLETE = "COMPLETED";//已完成
    public static final String PROJECT_STATUS_UNCOMPLETE = "UNCOMPLETED";//未完成
    public static final String PROJECT_STATUS_INPROGRESS = "INPROGRESS";//进行中

    //项目详情请求tag
    public static final String REQUEST_TAG_GET_PROJECT_DETAILS = "project_details";
}
