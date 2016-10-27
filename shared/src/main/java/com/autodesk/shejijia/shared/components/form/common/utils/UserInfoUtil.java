package com.autodesk.shejijia.shared.components.form.common.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.utility.SharedPreferencesUtils;

/**
 * Created by t_panya on 16/10/25.
 */

public final class UserInfoUtil {
    private UserInfoUtil(){
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static String getToken(@NonNull Context context){
        MemberEntity entity = (MemberEntity) SharedPreferencesUtils.getObject(context, Constants.USER_INFO);
        if (entity != null && !TextUtils.isEmpty(entity.getHs_accesstoken())) {
            return entity.getHs_accesstoken();
        }
        return null;
    }
}
