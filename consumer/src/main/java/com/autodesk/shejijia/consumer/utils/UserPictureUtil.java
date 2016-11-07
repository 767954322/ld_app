package com.autodesk.shejijia.consumer.utils;

import android.app.Activity;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.ConsumerEssentialInfoEntity;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.DesignerInfoDetails;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;

import org.json.JSONObject;

/**
 */
public class UserPictureUtil {

    //设置头像
    public static void setConsumerOrDesignerPicture(Activity context,ImageView iv) {

        MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (mMemberEntity != null &&
                Constant.UerInfoKey.CONSUMER_TYPE.equals(mMemberEntity.getMember_type())) {
            getConsumerInfoData(mMemberEntity.getAcs_member_id(),context,iv);

        }else if (mMemberEntity != null && Constant.UerInfoKey.DESIGNER_TYPE.equals(mMemberEntity.getMember_type())) {
            getDesignerInfoData(mMemberEntity.getAcs_member_id(), mMemberEntity.getHs_uid(),context,iv);

        }else {
            iv.setImageResource(R.drawable.icon_default_avator);
        }
    }

    /**
     * 获取个人基本信息
     *
     * @param member_id
     * @brief For details on consumers .
     */
    private static void getConsumerInfoData(String member_id, final Activity context, final ImageView iv) {

        MPServerHttpManager.getInstance().getConsumerInfoData(member_id, new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonString = GsonUtil.jsonToString(jsonObject);
                ConsumerEssentialInfoEntity mConsumerEssentialInfoEntity = GsonUtil.jsonToBean(jsonString, ConsumerEssentialInfoEntity.class);
                if (null != mConsumerEssentialInfoEntity) {
                    ImageUtils.displayAvatarImage(mConsumerEssentialInfoEntity.getAvatar(), iv);
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (context != null) {
                    ApiStatusUtil.getInstance().apiStatuError(volleyError,context);
                }
            }
        });
    }


    /**
     * 设计师个人信息
     *
     * @param designer_id
     * @param hs_uid
     */
    private static void getDesignerInfoData(String designer_id, String hs_uid, final Activity context, final ImageView iv) {
        MPServerHttpManager.getInstance().getDesignerInfoData(designer_id, hs_uid, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonString = GsonUtil.jsonToString(jsonObject);
                DesignerInfoDetails designerInfoDetails = GsonUtil.jsonToBean(jsonString, DesignerInfoDetails.class);
                ImageUtils.displayAvatarImage(designerInfoDetails.getAvatar(), iv);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ApiStatusUtil.getInstance().apiStatuError(volleyError,context);
            }
        });
    }
}
