package com.autodesk.shejijia.enterprise.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.autodesk.shejijia.enterprise.EnterpriseHomeActivity;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.utility.SharedPreferencesUtils;
import com.autodesk.shejijia.shared.components.form.ui.activity.QRCodeActivity;

/**
 * Created by t_xuz on 8/15/16.
 * 登陆成功后的广播接受者
 */
public class LoginNotificationReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        MemberEntity entity = (MemberEntity) SharedPreferencesUtils.getObject(context, Constant.UerInfoKey.USER_INFO);

        if("inspector".equals(entity.getMember_type())) {
            // TODO: 16/10/31 监理进入的界面
            Intent inspectIntent = new Intent(context, QRCodeActivity.class);
            inspectIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(inspectIntent);

        } else /*if("clientmanager".equals(entity.getMember_type()))*/{
            // TODO: 16/10/31 其他人进入的界面
            Intent homeIntent = new Intent(context, EnterpriseHomeActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(homeIntent);

        }

    }
}
