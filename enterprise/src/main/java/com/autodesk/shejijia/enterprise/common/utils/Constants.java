package com.autodesk.shejijia.enterprise.common.utils;

/**
 * Created by t_xuz on 8/15/16.
 * 一些公用的常量字段
 */
public class Constants {

    //登陆登出广播接收器的action
    public static final String LOGIN_IN_ACTION = "com.easyhome.enterprise.login";
    public static final String LOGIN_OUT_ACTION = "com.easyhome.enterprise.logout";
    public static final String LOGIN_IN_ACTIVITY_FINISHED = "com.easyhome.login.activity.finished";

    //用户登陆成功后存在sp中的key
    public static final String USER_INFO = "user_info";

    //服务器端url地址:
    public static final String BASE_URL = "http://cp-alpha-plan.homestyler.com/api/v1";
//    public static final String BASE_URL = "http://ec2-54-223-58-121.cn-north-1.compute.amazonaws.com.cn:8080/api/v1";

    //项目列表页同级fragment的tag
    public static final String TASK_LIST_FRAGMENT = "TaskListFragment";
    public static final String ISSUE_LIST_FRAGMENT = "IssueListFragment";
    public static final String GROUP_CHAT_FRAGMENT = "GroupChatFragment";

    //我页流程式fragment对应的tag
    public static final String PERSONAL_MORE_FRAGMENT = "MoreFragment";
    public static final String PERSONAL_POJECTLIST_FRAGMENT = "MyProjectListFragment";

    //下拉刷新,上拉加载更多事件标记
    public static final String REFRESH_EVENT = "refresh";
    public static final String LOAD_MORE_EVENT = "loadMore";


}
