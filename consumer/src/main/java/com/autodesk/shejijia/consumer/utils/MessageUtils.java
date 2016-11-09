package com.autodesk.shejijia.consumer.utils;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.ConsumerEssentialInfoEntity;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThread;
import com.autodesk.shejijia.shared.components.im.manager.MPChatHttpManager;
import com.autodesk.shejijia.shared.framework.AdskApplication;

import org.json.JSONObject;

/**
 * Created by allengu on 16-11-9.
 */
public class MessageUtils {

    /**
     * 获取个人基本信息
     *
     * @brief For details on consumers .
     */
    public static void getMsgConsumerInfoData(UnMessageCallBack unMsgCallBack) {
        unMessageCallBack = unMsgCallBack;
        MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (mMemberEntity != null) {
            final String acs_member_id = mMemberEntity.getAcs_member_id();
            MPServerHttpManager.getInstance().getConsumerInfoData(acs_member_id, new OkJsonRequest.OKResponseCallback() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    String jsonString = GsonUtil.jsonToString(jsonObject);
                    ConsumerEssentialInfoEntity mConsumerEssentialInfoEntity = GsonUtil.jsonToBean(jsonString, ConsumerEssentialInfoEntity.class);

                    if (null != mConsumerEssentialInfoEntity) {
                        String thread_id = mConsumerEssentialInfoEntity.getThread_id();
                        getThreadDetailForThreadId(acs_member_id, thread_id);
                    }

                }

                @Override
                public void onErrorResponse(VolleyError volleyError) {
                }
            });
        }

    }

    private static void getThreadDetailForThreadId(String member_id, String inThreadId) {
        MPChatHttpManager.getInstance().retrieveThreadDetails(member_id, inThreadId, new OkStringRequest.OKResponseCallback() {
            @Override

            public void onErrorResponse(VolleyError volleyError) {
//                MPNetworkUtils.logError(TAG, volleyError);
            }

            @Override

            public void onResponse(String s) {
                MPChatThread thread = MPChatThread.fromJSONString(s);
                int unread_message_count = thread.unread_message_count;

                unMessageCallBack.unCOuntMsgCallBack(unread_message_count);

            }

        });
    }
//    public static void setCallBack(UnMessageCallBack unMsgCallBack) {
//
//        unMessageCallBack = unMsgCallBack;
//    }

    public interface UnMessageCallBack {

        void unCOuntMsgCallBack(int msgUnCount);

    }


    private static UnMessageCallBack unMessageCallBack;
}