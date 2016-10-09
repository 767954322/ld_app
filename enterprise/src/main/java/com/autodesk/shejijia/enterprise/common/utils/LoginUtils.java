package com.autodesk.shejijia.enterprise.common.utils;

import android.content.Context;
import android.content.Intent;

import com.autodesk.shejijia.enterprise.nodeprocess.projectlists.activitys.ProjectListsActivity;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.tools.login.RegisterOrLoginActivity;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.SharedPreferencesUtils;
import com.autodesk.shejijia.shared.components.im.constants.BroadCastInfo;

/**
 * Created by t_xuz on 8/15/16.
 * 登陆相关工具类
 */
public class LoginUtils {

    /*
    * 登陆
    * */
    public static void doLogin(Context mContext) {
        mContext.startActivity(new Intent(mContext, RegisterOrLoginActivity.class));
    }

    /*
    * 登出
    * */
    public static void doLogout(Context mContext) {
        Intent intent = new Intent();
        intent.setAction(BroadCastInfo.USER_DID_LOGOUT);
        mContext.sendBroadcast(intent);
    }

    /*
    * 登陆成功做一些处理
    * */
    public static void onLoginSuccess(MemberEntity entity,Context mContext) {
        /// 将获取到底数据设置为全局可以访问.
//        EnterpriseApplication.setMemberEntity(entity);

//        openChatConnection();

//        registerForPushNotification();

        // 保存获取到的数据 .
//        SPConfigUtils.put(mContext,Constants.USER_INFO,entity);
        SharedPreferencesUtils.saveObject(mContext,Constants.USER_INFO,entity);
        // 更新未读消息
//        Intent loginIntent = new Intent(BroadCastInfo.USER_DID_LOGIN);
//        sendBroadcast(loginIntent);

        LogUtils.e("login--entity",entity+"");
        // 跳转到项目列表页
        Intent intent = new Intent(mContext, ProjectListsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
