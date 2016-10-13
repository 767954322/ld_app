package com.autodesk.shejijia.shared.components.common.utility;

import android.content.Context;
import android.content.Intent;
import com.autodesk.shejijia.shared.components.common.tools.login.RegisterOrLoginActivity;
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

}
