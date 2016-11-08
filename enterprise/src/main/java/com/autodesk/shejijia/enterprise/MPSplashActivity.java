package com.autodesk.shejijia.enterprise;

import com.autodesk.shejijia.shared.components.common.tools.login.RegisterOrLoginActivity;
import com.autodesk.shejijia.shared.components.common.utility.UserInfoUtils;
import com.autodesk.shejijia.shared.framework.activity.SplashActivity;


public class MPSplashActivity extends SplashActivity {
    @Override
    protected Class getNextActivityToLaunch() {

        if (UserInfoUtils.getToken(this) != null) {
            return EnterpriseHomeActivity.class;
        } else {
            return RegisterOrLoginActivity.class;
        }
    }
}
