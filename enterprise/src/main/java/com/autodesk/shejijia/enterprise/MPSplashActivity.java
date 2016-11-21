package com.autodesk.shejijia.enterprise;

import android.text.TextUtils;

import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.tools.login.RegisterOrLoginActivity;
import com.autodesk.shejijia.shared.components.common.utility.UserInfoUtils;
import com.autodesk.shejijia.shared.components.form.ui.activity.ScanQrCodeActivity;
import com.autodesk.shejijia.shared.framework.activity.SplashActivity;


public class MPSplashActivity extends SplashActivity {
    @Override
    protected Class getNextActivityToLaunch() {

        String memberType = UserInfoUtils.getMemberType(this);
        if(TextUtils.isEmpty(memberType)) {
            return RegisterOrLoginActivity.class;
        } else {
           switch(memberType) {
               case ConstructionConstants.MemberType.INSPECTOR:
                   return ScanQrCodeActivity.class;
               default:
                   return EnterpriseHomeActivity.class;
           }
        }

    }
}
