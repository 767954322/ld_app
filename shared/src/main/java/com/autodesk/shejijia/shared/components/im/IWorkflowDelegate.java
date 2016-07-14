package com.autodesk.shejijia.shared.components.im;

import android.content.Context;

import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatCommandInfo;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatProjectInfo;

public interface IWorkflowDelegate
{
    void getProjectInfo(String assetId, String memberId, OkJsonRequest.OKResponseCallback callback);
    void onCommandCellClicked(Context context, MPChatCommandInfo mpChatCommandInfo);
    int getImageForProjectInfo(String OrderDetailsInfo,boolean ifIsDesiner);
    void onChatRoomSupplementryButtonClicked(Context context,String assetId, String recieverId);
    void onChatRoomWorkflowButtonClicked(Context context,int wk_cur_sub_node_idi,String assetId, String recieverId,String receiverUserName,String designerId);
}
