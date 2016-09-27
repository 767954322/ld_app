package com.autodesk.shejijia.shared.framework.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.utility.CommonUtils;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.SharedPreferencesUtils;
import com.autodesk.shejijia.shared.components.im.constants.BroadCastInfo;
import com.autodesk.shejijia.shared.framework.AdskApplication;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by t_xuz on 9/27/16.
 * 登录登出广播接收器,用静态注册代替在application动态注册
 */
public class SignInNotificationReceiver extends BroadcastReceiver {//此广播会有延时，在进入界面后会有获取不到登陆人信息的的情况

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (action.equalsIgnoreCase(BroadCastInfo.USER_DID_LOGOUT)) {
            AdskApplication.getInstance().onLogout();
        } else if (action.equalsIgnoreCase(BroadCastInfo.LOGIN_ACTIVITY_FINISHED)) {
            String strToken = intent.getStringExtra(BroadCastInfo.LOGIN_TOKEN);

            MemberEntity entity = GsonUtil.jsonToBean(strToken, MemberEntity.class);

            String ZERO = "0";

            /// 为不符合规则的acs_member_id 补足位数 .
            String acs_member_id = entity.getAcs_member_id();
            if (acs_member_id.length() < 8) {
                acs_member_id += ZERO;
                entity.setAcs_member_id(acs_member_id);
            }
            LogUtils.i("APPLICATION", "memberEntity:" + entity);

            AdskApplication.getInstance().onLoginSuccess(entity);
        }
    }


}
