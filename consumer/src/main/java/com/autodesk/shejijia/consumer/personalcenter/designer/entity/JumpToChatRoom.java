package com.autodesk.shejijia.consumer.personalcenter.designer.entity;

import android.content.Context;
import android.content.Intent;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.components.common.appglobal.ApiManager;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.im.activity.ChatRoomActivity;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThread;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThreads;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatUtility;
import com.autodesk.shejijia.shared.components.im.manager.MPChatHttpManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by luchongbin on 16-9-9.
 */
public class JumpToChatRoom {

    public static void openChatRoom(final Context context, final JumpBean jumpBean) {

        String recipient_ids = jumpBean.getReciever_user_id() + "," + jumpBean.getAcs_member_id() + "," + ApiManager.getAdmin_User_Id();

        MPChatHttpManager.getInstance().retrieveMultipleMemberThreads(recipient_ids, 0, 10, new OkStringRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                MPNetworkUtils.logError(TAG, volleyError);
            }

            @Override
            public void onResponse(String s) {
                MPChatThreads mpChatThreads = MPChatThreads.fromJSONString(s);

                final Intent intent = new Intent(context, ChatRoomActivity.class);
                intent.putExtra(ChatRoomActivity.RECIEVER_USER_ID, jumpBean.getReciever_user_id());
                intent.putExtra(ChatRoomActivity.RECIEVER_USER_NAME, jumpBean.getReciever_user_name());
                intent.putExtra(ChatRoomActivity.MEMBER_TYPE, jumpBean.getMember_type());
                intent.putExtra(ChatRoomActivity.ACS_MEMBER_ID, jumpBean.getAcs_member_id());

                if (mpChatThreads != null && mpChatThreads.threads.size() > 0) {

                    MPChatThread mpChatThread = mpChatThreads.threads.get(0);
                    int assetId = MPChatUtility.getAssetIdFromThread(mpChatThread);
                    intent.putExtra(ChatRoomActivity.THREAD_ID, mpChatThread.thread_id);
                    intent.putExtra(ChatRoomActivity.ASSET_ID, assetId + "");
                    intent.putExtra(ChatRoomActivity.RECIEVER_HS_UID, jumpBean.getReciever_hs_uid());
                    context.startActivity(intent);

                } else {
                    MPChatHttpManager.getInstance().getThreadIdIfNotChatBefore(jumpBean.getAcs_member_id(), jumpBean.getReciever_user_id(), new OkStringRequest.OKResponseCallback() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
//                            MPNetworkUtils.logError(TAG, volleyError);
                        }

                        @Override
                        public void onResponse(String s) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                String thread_id = jsonObject.getString("thread_id");
                                intent.putExtra(ChatRoomActivity.ASSET_ID, "");
                                intent.putExtra(ChatRoomActivity.RECIEVER_HS_UID, jumpBean.getReciever_hs_uid());
                                intent.putExtra(ChatRoomActivity.THREAD_ID, thread_id);
                                context.startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }


}
