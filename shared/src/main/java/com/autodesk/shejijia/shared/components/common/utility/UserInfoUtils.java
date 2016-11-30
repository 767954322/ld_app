package com.autodesk.shejijia.shared.components.common.utility;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;

/**
 * Created by t_xuz on 10/17/16.
 * 账号信息管理类
 */
public final class UserInfoUtils {

    private UserInfoUtils(){
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static String getToken(@NonNull Context context){
        MemberEntity entity = (MemberEntity) SharedPreferencesUtils.getObject(context, Constant.UerInfoKey.USER_INFO);
        if (entity != null && !TextUtils.isEmpty(entity.getHs_accesstoken())) {
            return entity.getHs_accesstoken();
        }
        return null;
    }

    public static String getNikeName(@NonNull Context context){
        MemberEntity entity = (MemberEntity) SharedPreferencesUtils.getObject(context, Constant.UerInfoKey.USER_INFO);
        if (entity != null && !TextUtils.isEmpty(entity.getNick_name())) {
            return entity.getNick_name();
        }
        return null;
    }

    public static String getMemberType(@NonNull Context context){
        MemberEntity entity = (MemberEntity) SharedPreferencesUtils.getObject(context, Constant.UerInfoKey.USER_INFO);
        if (entity != null && !TextUtils.isEmpty(entity.getMember_type())) {
            return entity.getMember_type();
        }
        return null;
    }

    public static String getMemberId(@NonNull Context context){
        MemberEntity entity = (MemberEntity) SharedPreferencesUtils.getObject(context, Constant.UerInfoKey.USER_INFO);
        if (entity != null && !TextUtils.isEmpty(entity.getMember_id())) {
            return entity.getMember_id();
        }
        return null;
    }
    public static String getAcsMemberId(@NonNull Context context){
        MemberEntity entity = (MemberEntity) SharedPreferencesUtils.getObject(context, Constant.UerInfoKey.USER_INFO);
        if (entity != null && !TextUtils.isEmpty(entity.getAcs_member_id())) {
            return entity.getAcs_member_id();
        }
        return null;
    }

    public static String getUid(@NonNull Context context){
        MemberEntity entity = (MemberEntity) SharedPreferencesUtils.getObject(context, Constant.UerInfoKey.USER_INFO);
        if (entity != null && !TextUtils.isEmpty(entity.getHs_uid())) {
            return entity.getHs_uid();
        }
        return null;
    }
}
