package com.autodesk.shejijia.shared.components.common.tools.chatroom;

import android.content.Context;
import android.content.Intent;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.components.common.appglobal.ApiManager;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.im.activity.ChatRoomActivity;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThread;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThreads;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatUtility;
import com.autodesk.shejijia.shared.components.im.manager.MPChatHttpManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by luchongbin on 16-9-9.
 */
public class JumpToChatRoom {

    public static void getChatRoom(final Context context, final JumpBean jumpBean) {
        CustomProgress.show(context, "", false, null);
        String recipient_ids = jumpBean.getReciever_user_id() + "," + jumpBean.getAcs_member_id() + "," + ApiManager.getAdmin_User_Id();

        MPChatHttpManager.getInstance().retrieveMultipleMemberThreads(recipient_ids, 0, 10, new OkStringRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CustomProgress.cancelDialog();
//                MPNetworkUtils.logError(TAG, volleyError);
            }

            @Override
            public void onResponse(String json) {
                ss(json, context, jumpBean);
            }
        });
    }

    private static void ss(String json, final Context context, final JumpBean jumpBean) {
        Map<String, String> map = new HashMap<>();
        MPChatThreads mpChatThreads = MPChatThreads.fromJSONString(json);
        if (mpChatThreads != null && mpChatThreads.threads.size() > 0) {
            MPChatThread mpChatThread = mpChatThreads.threads.get(0);
            int assetId = MPChatUtility.getAssetIdFromThread(mpChatThread);
            map.put(ChatRoomActivity.THREAD_ID, mpChatThread.thread_id);
            map.put(ChatRoomActivity.ASSET_ID, assetId + "");
            openChatRoom(context, jumpBean, map);
            return;
        }
        getThreadIdIfNotChatBefore(context, jumpBean);
    }

    private static void getThreadIdIfNotChatBefore(final Context context, final JumpBean jumpBean) {
        String desiner_id = "";
        String cusomer_id = "";
        if (Constant.UerInfoKey.CONSUMER_TYPE.equals(jumpBean.getMember_type())) {

            desiner_id = jumpBean.getReciever_user_id();
            cusomer_id = jumpBean.getAcs_member_id();

        } else {

            desiner_id = jumpBean.getAcs_member_id();
            cusomer_id = jumpBean.getReciever_user_id();

        }

        MPChatHttpManager.getInstance().getThreadIdIfNotChatBefore(desiner_id, cusomer_id, new OkStringRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                            MPNetworkUtils.logError(TAG, volleyError);
                CustomProgress.cancelDialog();
            }

            @Override
            public void onResponse(String json) {
                try {
                    CustomProgress.cancelDialog();
                    JSONObject jsonObject = new JSONObject(json);
                    String thread_id = jsonObject.getString("thread_id");
                    Map<String, String> map = new HashMap<>();
                    map.put(ChatRoomActivity.THREAD_ID, thread_id);
                    map.put(ChatRoomActivity.ASSET_ID, "");
                    openChatRoom(context, jumpBean, map);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private static void openChatRoom(final Context context, final JumpBean jumpBean, Map<String, String> map) {
        CustomProgress.cancelDialog();
        final Intent intent = new Intent(context, ChatRoomActivity.class);
        intent.putExtra(ChatRoomActivity.RECIEVER_USER_ID, jumpBean.getReciever_user_id());
        intent.putExtra(ChatRoomActivity.RECIEVER_USER_NAME, jumpBean.getReciever_user_name());
        intent.putExtra(ChatRoomActivity.MEMBER_TYPE, jumpBean.getMember_type());
        intent.putExtra(ChatRoomActivity.ACS_MEMBER_ID, jumpBean.getAcs_member_id());

        intent.putExtra(ChatRoomActivity.THREAD_ID, map.get(ChatRoomActivity.THREAD_ID));
        intent.putExtra(ChatRoomActivity.ASSET_ID, map.get(ChatRoomActivity.ASSET_ID));
        intent.putExtra(ChatRoomActivity.RECIEVER_HS_UID, jumpBean.getReciever_hs_uid());
        context.startActivity(intent);

    }


}
