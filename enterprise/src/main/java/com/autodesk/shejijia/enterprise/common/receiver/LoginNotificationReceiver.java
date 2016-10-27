package com.autodesk.shejijia.enterprise.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.autodesk.shejijia.shared.components.common.utility.Constants;
import com.autodesk.shejijia.enterprise.common.utils.LoginUtils;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.im.constants.BroadCastInfo;

/**
 * Created by t_xuz on 8/15/16.
 * 登陆及登出广播接收器
 */
public class LoginNotificationReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (!TextUtils.isEmpty(action) && action.equalsIgnoreCase(Constants.LOGIN_OUT_ACTION)){
            LoginUtils.doLogout(context);
        }else if (!TextUtils.isEmpty(action) && action.equalsIgnoreCase(BroadCastInfo.LOGIN_ACTIVITY_FINISHED)){
            String strToken = intent.getStringExtra(BroadCastInfo.LOGIN_TOKEN);
            LogUtils.e("login-result",strToken);
            MemberEntity entity = GsonUtil.jsonToBean(strToken, MemberEntity.class);

            String ZERO = "0";

            /// 为不符合规则的acs_member_id 补足位数 .
            String acs_member_id = entity.getAcs_member_id();
            if (acs_member_id.length() < 8) {
                acs_member_id += ZERO;
                entity.setAcs_member_id(acs_member_id);
            }
            LogUtils.d("APPLICATION", "memberEntity:" + entity);
            LogUtils.e("login-success",entity.toString());

            LoginUtils.onLoginSuccess(entity,context);
        }
    }
}
