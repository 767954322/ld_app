package com.autodesk.shejijia.shared.components.im.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.components.common.tools.photopicker.MPPhotoPickerActivity;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatMessage;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThread;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatUtility;
import com.autodesk.shejijia.shared.components.im.manager.ChatEventHandler;
import com.autodesk.shejijia.shared.components.im.manager.MPChatHttpManager;
import com.autodesk.shejijia.shared.components.common.utility.ImageProcessingUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPCameraUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPFileUtility;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.google.gson.Gson;
import com.socks.library.KLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;


public class ChatRoomActivity extends BaseChatRoomActivity implements ChatEventHandler.ChatEventHandlerInterface {
    public static final String CHOSEN_PHOTO = "chosenphoto";


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_chat_room;
    }

    @Override
    protected void initView() {
        super.initView();

        mBottomCustomLayout = (RelativeLayout) findViewById(R.id.chat_tool_parentlayout);
        mSelectImageButton = (ImageView) findViewById(R.id.chat_selectphoto);
        mSelectTakeImageButton = (ImageView) findViewById(R.id.chat_takephoto);
        mWorkflowButton = (ImageView) findViewById(R.id.chat_custom_button);
        mAudioParentView = (RelativeLayout) findViewById(R.id.audio_recording_parent_view);
        mBottomCustomLayout.setVisibility(View.GONE);


        //top
        secondaryImageButton = (ImageButton) findViewById(R.id.nav_secondary_imageButton);
        rightImageButton = (ImageButton) findViewById(R.id.nav_right_imageButton);
        rightTextView = (TextView) findViewById(R.id.nav_right_textView);
        leftTextView = (TextView) findViewById(R.id.nav_left_textView);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mIsToolViewOpen = false;

        if (Constant.UerInfoKey.DESIGNER_TYPE.equals(mMemberType)) {
            designerId = mAcsMemberId;
        } else {
            designerId = mRecieverUserId;
        }

        isAssetId(mAssetId, designerId);

    }


    @Override
    protected void initListener() {
        super.initListener();

        mSelectImageButton.setOnClickListener(this);
        mSelectTakeImageButton.setOnClickListener(this);
        mWorkflowButton.setOnClickListener(this);
        secondaryImageButton.setOnClickListener(this);

        LinearLayout layout = (LinearLayout) findViewById(R.id.chat_parent_layout);
        mHandler = new ChatEventHandler(this, layout, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mThreadId != null && !mThreadId.isEmpty() && mAcsMemberId != null && !mAcsMemberId.isEmpty()) {
            getMediaMessageUnreadCount();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //TODO: Check is this is called before onResume
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_CODE_PHOTOPICKER:
                if (data != null) {
                    String path = data.getStringExtra(CHOSEN_PHOTO);
                    openHotspotActivity(path);
                }
                break;
            case RESULT_CODE_SNAPSHOT:
                if (resultCode == RESULT_OK) {
                    assert (new File(mSnapshotFile).exists());
                    processSnapshotAndProceed(mSnapshotFile);
                }
                break;
            case RESULT_CODE_HOTSPOT:
                if (resultCode == RESULT_OK && data != null)
                    mThreadId = data.getStringExtra(MPFileHotspotActivity.PARENTTHREADID);
                break;
        }
    }

    @Override
    protected void getInstanceStateFromBundle(Bundle bundle) {
        super.getInstanceStateFromBundle(bundle);
        mSnapshotFile = bundle.getString(SNAPSHOT_FILE);
    }

    @Override
    protected void putInstanceStateToBundle(Bundle bundle) {
        super.putInstanceStateToBundle(bundle);
        bundle.putString(SNAPSHOT_FILE, mSnapshotFile);
    }


    @Override
    public void onSendTextClicked(String msg) {
        sendTextMsg(msg);
    }


    @Override
    public void customButtonDidClicked() {
        if (mIsToolViewOpen) {
            mIsToolViewOpen = false;
            mBottomCustomLayout.setVisibility(View.GONE);
        } else {
            mIsToolViewOpen = true;
            mBottomCustomLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void audioRecordingStarted() {
        mAudioParentView.setVisibility(View.VISIBLE);
    }

    @Override
    public void audioRecordingEnded(final String filePath) {
        if (filePath != null)
            sendMediaMessage(filePath, "AUDIO", new CallBack() {
                @Override
                public void call() {
                    showAudioMessageErrorDialog(filePath);
                }
            });

        mAudioParentView.setVisibility(View.GONE);
    }

    @Override
    public boolean shouldHideCustomButton() {
        return false;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        if (i == R.id.chat_selectphoto)
        {
            onSelectPhotoClicked();

        }
        else if (i == R.id.chat_takephoto)
        {
            onTakeSnapshotClicked();

        }
        else if (i == R.id.chat_custom_button)
        {//TODO REFACTORING
//                onWorkFlow();

        }
        else if (i == R.id.nav_secondary_imageButton)
        {//TODO REFACTORING
            //onClickSecondary();

        }
    }


    @Override
    protected String getActivityTitle() {
        return getResources().getString(R.string.mychat);
    }

    @Override
    protected int getRightButtonImageResourceId() {
        //default set in XML
        return R.drawable.msg_file;
    }

    @Override
    protected void leftNavButtonClicked(View view) {
        super.leftNavButtonClicked(view);
    }

    @Override
    protected void rightNavButtonClicked(View view) {
        Intent intent = new Intent(this, MediaMessagesChatRoomActivity.class);
        intent.putExtra(MediaMessagesChatRoomActivity.THREAD_ID, mThreadId);
        intent.putExtra(MediaMessagesChatRoomActivity.RECIEVER_USER_NAME, mRecieverUserName);
        intent.putExtra(MediaMessagesChatRoomActivity.RECIEVER_USER_ID, mRecieverUserId);
        intent.putExtra(MediaMessagesChatRoomActivity.ACS_MEMBER_ID, mAcsMemberId);
        intent.putExtra(MediaMessagesChatRoomActivity.MEMBER_TYPE, mMemberType);
        startActivity(intent);
    }


    private void sendTextMsg(String body) {
        OkStringRequest.OKResponseCallback newThreadCallback = new OkStringRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MPNetworkUtils.logError(TAG, error);
            }

            @Override
            public void onResponse(String response) {
                setNewThreadId(response);
                refresh();
            }
        };

        OkStringRequest.OKResponseCallback replyCallback = new OkStringRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MPNetworkUtils.logError(TAG, error);
            }

            @Override
            public void onResponse(String response) {
            }
        };

        if (mThreadId == null)
            MPChatHttpManager.getInstance().sendNewThreadMessage(mAcsMemberId, mRecieverUserId, body, "Subject", newThreadCallback);
        else
            MPChatHttpManager.getInstance().replyToThread(mAcsMemberId, mThreadId, body, replyCallback);
    }


    private void sendMediaMessage(final String url, String fileType, final CallBack errorCallback) {
        showLoadingIndicator();

        MPChatHttpManager.getInstance().sendMediaMessage(mAcsMemberId, mRecieverUserId, "Subject", mThreadId, new File(url), fileType, new MPChatHttpManager.ResponseHandler() {
            @Override
            public void onSuccess(String response) {
                if (mThreadId == null) {
                    setNewThreadId(response);
                    refresh();
                }
                MPFileUtility.removeFile(url);

                hideLoadingIndicator();
            }

            @Override
            public void onFailure() {
                errorCallback.call();

                hideLoadingIndicator();
            }
        });
    }

    private void onTakeSnapshotClicked() {
        String imageFile = MPCameraUtils.getImageFileForGallery();

        if (imageFile != null) {
            File imageFileHandle = new File(imageFile);
            Uri fileUri = Uri.fromFile(imageFileHandle);

            Intent cameraIntent = MPCameraUtils.cookCameraLaunchIntent(this, fileUri);
            mSnapshotFile = imageFile;

            startActivityForResult(cameraIntent, RESULT_CODE_SNAPSHOT);
        }
    }

    private void processSnapshotAndProceed(final String sourceFilePath) {
        String uniqueFileName = MPFileUtility.getUniqueFileNameWithExtension("jpg");
        File localFile = MPFileUtility.getFileFromName(this, uniqueFileName);

        //TODO: Handle this, create an error dialog or something
        if (localFile == null)
            return;

        String targetFilePath = localFile.getAbsolutePath();

        ImageProcessingUtil util = new ImageProcessingUtil();
        util.fastRotateAndCopyImage(sourceFilePath, targetFilePath, this, new ImageProcessingUtil.ImageSaverHandler() {
            @Override
            public void onSuccess(String outImagePath) {
                AsyncTask task = new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... imagePaths) {
                        // Delete the non-rotated file
                        MPFileUtility.removeFile(sourceFilePath);

                        String galleryImagePath = MPCameraUtils.getImageFileForGallery();
                        try {
                            if (galleryImagePath != null) {
                                MPFileUtility.copyFile(new File(imagePaths[0]), new File(galleryImagePath));
                                MPCameraUtils.makeFileAvailableToGallery(galleryImagePath,
                                        ChatRoomActivity.this);
                            }
                        } catch (Exception e) {
                            MPNetworkUtils.logError(TAG, "Exception while copying processed image to " +
                                    "the user gallery");
                        }

                        return imagePaths[0];
                    }

                    @Override
                    protected void onPostExecute(String imagePath) {
                        if (imagePath != null)
                            openHotspotActivity(imagePath);
                    }
                }.execute(outImagePath);
            }

            @Override
            public void onFailure() {
                MPNetworkUtils.logError(TAG, "Could not rotate image successfully, sorry!");

                //TODO: Handle this, create an error dialog or something
            }
        });
    }

    /**
     * Access to SD card images, jump to display the page
     */
    private void onSelectPhotoClicked() {
        Intent intent = new Intent(this, MPPhotoPickerActivity.class);
        intent.putExtra(MPPhotoPickerActivity.ASSET_ID, mAssetId);
        intent.putExtra(MPPhotoPickerActivity.MEMBER_ID, mAcsMemberId);

        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        intent.putExtra(MPPhotoPickerActivity.X_TOKEN, memberEntity.getHs_accesstoken());

        startActivityForResult(intent, RESULT_CODE_PHOTOPICKER);
    }


    private void openHotspotActivity(String localPath) {
        Intent intent = new Intent(this, MPFileHotspotActivity.class);

        intent.putExtra(MPFileHotspotActivity.RECEIVERID, mRecieverUserId);
        intent.putExtra(MPFileHotspotActivity.RECEIVERNAME, mRecieverUserName);
        intent.putExtra(MPFileHotspotActivity.LOGGEDINUSERID, mAcsMemberId);
        intent.putExtra(MPFileHotspotActivity.LOCALIMAGEURL, localPath);
        intent.putExtra(MPFileHotspotActivity.PARENTTHREADID, mThreadId);

        if (mProjectInfo != null) {
            intent.putExtra(MPFileHotspotActivity.PROJECT_INFO, mProjectInfo);
        }

        startActivityForResult(intent, RESULT_CODE_HOTSPOT);
    }


    private void getMediaMessageUnreadCount() {
        MPChatHttpManager.getInstance().retrieveAllHotspotUnreadmessageCount(mAcsMemberId, mThreadId, new OkStringRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                setVisibilityForNavButton(ButtonType.BADGE, false);
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    int unread_message_count = obj.optInt("unread_message_count");

                    if (unread_message_count != 0)
                        showBadgeOnNavBar(Integer.toString(unread_message_count));
                    else
                        setVisibilityForNavButton(ButtonType.BADGE, false);
                } catch (JSONException e) {
                }
            }
        });
    }

    @Override
    protected void onNewMessageReceived(Intent intent) {
        try {
            super.onNewMessageReceived(intent);

            String json_Msg = intent.getStringExtra("json");
            JSONArray arr = new JSONArray(json_Msg);
            MPChatMessage message = MPChatMessage.fromJSONObject(arr.optJSONObject(0));

            if (!message.thread_id.equalsIgnoreCase(mThreadId) && mThreadId != null && !mThreadId.isEmpty() && mAcsMemberId != null && !mAcsMemberId.isEmpty()) {
                getMediaMessageUnreadCount();
                getThreadDetailForThreadId(message.thread_id);

            } else if (mAssetId != null) {
                getProjectInfo();
            }

        } catch (Exception e) {
        }
    }

    private void getThreadDetailForThreadId(String inThreadId) {
        MPChatHttpManager.getInstance().retrieveThreadDetails(mAcsMemberId, inThreadId, new OkStringRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
            }

            @Override
            public void onResponse(String s) {
                MPChatThread thread = MPChatThread.fromJSONString(s);
                updateUnreadMessageCountForThread(thread);

                String assetId = MPChatUtility.getAssetNameFromThread(thread);
                if (assetId != null && assetId.equalsIgnoreCase(mAssetId))
                    getProjectInfo();
            }
        });
    }


    private void updateUnreadMessageCountForThread(MPChatThread thread) {
        String fileId = MPChatUtility.getFileEntityIdForThread(thread);
        if (fileId != null) {
            for (int i = 0; i < mMessageList.size(); ++i) {
                MPChatMessage chatMsg = mMessageList.get(i);
                if (String.valueOf(chatMsg.media_file_id).equalsIgnoreCase(fileId)) {
                    mMessageAdapter.updateMessageCell(i);
                    break;
                }
            }
        }
    }


    private void showAudioMessageErrorDialog(final String filePath) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.chatroom_audio_recording_erroralert_title))
                .setPositiveButton(getString(R.string.chatroom_audio_recording_erroralert_ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                audioRecordingEnded(filePath);
                            }
                        })
                .setNegativeButton(getString(R.string.chatroom_audio_recording_erroralert_cancel),
                        null)
                .setMessage(getString(R.string.chatroom_audio_recording_erroralert_message))
                .create();

        alertDialog.show();
    }


    private void isAssetId(String mAssetId, String mAcsMemberId) {

        if (!mAssetId.equals("0")) {
            //TODO REFACTORING
//            getOrderDetailsInfo(mAssetId + "", mAcsMemberId);
        } else {
            if (Constant.UerInfoKey.CONSUMER_TYPE.equals(mMemberType)) { //消费者
                mWorkflowButton.setVisibility(View.VISIBLE);
                mWorkflowButton.setBackgroundResource(R.drawable.amount_room_ico);
            }
        }
    }

//TODO REFACTORING
//    private void getOrderDetailsInfo(String needs_id, String mAcsMemberId) {
//        final OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
//            @Override
//            public void onResponse(final JSONObject jsonObject) {
//                try {
//                    String OrderDetailsInfo = new String(jsonObject.toString().getBytes("ISO-8859-1"), "UTF-8");
//                    WkFlowDetailsBean wkFlowDetailsBean = new Gson().fromJson(OrderDetailsInfo, WkFlowDetailsBean.class);
//                    if (wkFlowDetailsBean.getRequirement().getIs_beishu().equals("0")) { // 北舒
//                        //什么都不显示
//                        secondaryImageButton.setVisibility(View.GONE);
//                        mWorkflowButton.setVisibility(View.GONE);
//                    } else {
//                        String Wk_cur_sub_node_id = wkFlowDetailsBean.getRequirement().getBidders().get(0).getWk_cur_sub_node_id();
//                        wk_cur_sub_node_idi = Integer.valueOf(Wk_cur_sub_node_id);
//                        Wk_cur_node_id = wkFlowDetailsBean.getRequirement().getBidders().get(0).getWk_cur_node_id();
//                        Wk_template_id = wkFlowDetailsBean.getRequirement().getWk_template_id();
//
//                        secondaryImageButton.setVisibility(View.VISIBLE);
//                        //设置图标
//                        initWorkflowButtonIco();
//                    }
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                KLog.json("getOrderDetailsInfo", error.toString());
//            }
//        };
//        MPServerHttpManager.getInstance().getOrderDetailsInfoData(needs_id, mAcsMemberId, okResponseCallback);
//    }

    private void initWorkflowButtonIco() {
        Log.d("test", "Wk_cur_node_id:" + Wk_cur_node_id);
        if (!Wk_cur_node_id.equals("-1")) {
            Log.d("test", "Wk_template_id:" + Wk_template_id);

            mWorkflowButton.setVisibility(View.VISIBLE);
            if (Wk_template_id.equals("1")) {
                Log.d("test", "wk_cur_sub_node_idi:" + wk_cur_sub_node_idi);
                switch (wk_cur_sub_node_idi) {
                    case -1:
                    case 11: // 量房
                    case 12: // 消费者拒绝设计师
                    case 14: // 设计师拒绝量房

                        mWorkflowButton.setBackgroundResource(R.drawable.amount_room_ico);
                        break;
                    case 13: // 支付
                        mWorkflowButton.setBackgroundResource(R.drawable.pay_ico);
                        break;
                    case 21: // 合同
                    case 22: // 打开3D工具
                        mWorkflowButton.setBackgroundResource(R.drawable.icon_design_contract);
                        break;
                    case 31: // 首款
                        mWorkflowButton.setBackgroundResource(R.drawable.pay_ico);
                        break;
                    case 33: // 量房交付物
                        mWorkflowButton.setBackgroundResource(R.drawable.icon_design_drawings);
                        break;
                    case 41: // 支付设计首款
                    case 42: // 打开3D工具
                        mWorkflowButton.setBackgroundResource(R.drawable.pay_ico);
                        break;
                    case 51: // 支付尾款
                    case 52: // 打开3D工具
                        mWorkflowButton.setBackgroundResource(R.drawable.icon_design_drawings);
                        break;
                    case 61: // 上传支付交付物
                    case 62: // 编辑交付物
                        mWorkflowButton.setBackgroundResource(R.drawable.icon_design_drawings);
                        break;
                    default:
                        mWorkflowButton.setVisibility(View.GONE);
                        break;
                }

            } else if (Wk_template_id.equals("2")) {
                switch (wk_cur_sub_node_idi) {
                    case -1:
                    case 11: // 量房
                        mWorkflowButton.setBackgroundResource(R.drawable.amount_room_ico);
                        break;
                    case 12: // 消费者同意设计师
                        mWorkflowButton.setBackgroundResource(R.drawable.pay_ico);
                        break;
                    case 13: // 消费者拒绝设计师
                        mWorkflowButton.setVisibility(View.GONE);
                        break;
                    case 21: // 合同
                    case 22: // 打开3D工具
                        mWorkflowButton.setBackgroundResource(R.drawable.icon_design_contract);
                        break;
                    case 31: // 首款
                        mWorkflowButton.setBackgroundResource(R.drawable.pay_ico);
                        break;
                    case 33: // 量房交付物
                        mWorkflowButton.setBackgroundResource(R.drawable.icon_design_drawings);
                        break;
                    case 41: // 支付设计首款
                    case 42: // 打开3D工具
                        mWorkflowButton.setBackgroundResource(R.drawable.pay_ico);
                        break;
                    case 51: // 支付尾款
                    case 52: // 打开3D工具
                        mWorkflowButton.setBackgroundResource(R.drawable.icon_design_drawings);
                        break;
                    case 61: // 上传支付交付物
                    case 62: // 编辑交付物
                        mWorkflowButton.setBackgroundResource(R.drawable.icon_design_drawings);
                        break;
                    default:
                        mWorkflowButton.setVisibility(View.GONE);
                        break;
                }

            } else if (Wk_template_id.equals("3")) {

            }
        }


    }

    //TODO REFACTORING

//    private void jumpToOtherProcessesThree(Class activity) {
//
//        Intent intent = new Intent(this, activity);
//        intent.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_NEEDS_ID, mAssetId + "");
//        intent.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_DESIGNER_ID, mRecieverUserId);
//        intent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_IM);
//        startActivity(intent);
//    }
//
//    private void jumpToOtherProcessesFour(Class activity) {
//
//        Intent intent = new Intent(this, activity);
//        intent.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_NEEDS_ID, mAssetId + "");
//        intent.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_DESIGNER_ID, designerId);
//        intent.putExtra(Constant.BundleKey.BUNDLE_ACTION_NODE_ID, MPStatusMachine.NODE__MEANSURE_PAY);
//        intent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_IM);
//        startActivity(intent);
//    }
//
//    private void onClickSecondary() {
//
//
//
//        Intent maIntent = new Intent(this, ProjectMaterialActivity.class);
//        maIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_IM);
//        maIntent.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_DESIGNER_ID, designerId);
//        maIntent.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_NEEDS_ID, mAssetId);
//        startActivity(maIntent);
//    }
//
//    private void onWorkFlow() {
//
//        if (wk_cur_sub_node_idi != 0) {
//            if (wk_cur_sub_node_idi == 11 || wk_cur_sub_node_idi == -1) {
//                jumpToOtherProcessesThree(FlowMeasureFormActivity.class);
//
//            } else if (wk_cur_sub_node_idi == 13) {
//                jumpToOtherProcessesFour(FlowMeasureCostActivity.class);
//
//            } else if (wk_cur_sub_node_idi == 12 || wk_cur_sub_node_idi == 14) {
//                jumpToOtherProcessesFour(FlowMeasureFormActivity.class);
//
//            } else if (wk_cur_sub_node_idi == 21) {
//                jumpToOtherProcessesThree(FlowEstablishContractActivity.class);
//
//            } else if (wk_cur_sub_node_idi == 31) {
//                jumpToOtherProcessesFour(FlowFirstDesignActivity.class);
//
//            } else if (wk_cur_sub_node_idi == 33 || wk_cur_sub_node_idi == 51) {
//                jumpToOtherProcessesThree(FlowUploadDeliveryActivity.class);
//
//            } else if (wk_cur_sub_node_idi == 41) {
//                jumpToOtherProcessesFour(FlowLastDesignActivity.class);
//
//            }
//        } else {
//            String spStr[] = new String[0];
//            if (mRecieverUserName != null && !mRecieverUserName.equals("")) {
//                spStr = mRecieverUserName.split("_");
//            }
//            Intent intent = new Intent(this, MeasureFormActivity.class);
//            intent.putExtra(Constant.SeekDesignerDetailKey.NEEDS_ID, mAssetId + "");
//            intent.putExtra(Constant.SeekDesignerDetailKey.DESIGNER_ID, designerId);
//            intent.putExtra(Constant.SeekDesignerDetailKey.FLOW_STATE, Constant.WorkFlowStateKey.STEP_IM);
//            intent.putExtra(Constant.SeekDesignerDetailKey.HS_UID, spStr[1]);
//            startActivity(intent);
//
//        }
//    }

    private String mSnapshotFile;
    private String designerId;
    private boolean mIsToolViewOpen;

    private RelativeLayout mAudioParentView;
    private RelativeLayout mBottomCustomLayout;
    private ImageView mSelectImageButton;
    private ImageView mSelectTakeImageButton;
    private ImageView mWorkflowButton;

    private int wk_cur_sub_node_idi; //全流程标识
    private String Wk_template_id;
    private String Wk_cur_node_id;
    private ImageButton secondaryImageButton;
    private ImageButton rightImageButton;
    private TextView rightTextView, leftTextView;

    private static final String SNAPSHOT_FILE = "Local_File";

    private ChatEventHandler mHandler;

    private static final int RESULT_CODE_SNAPSHOT = 1000;
    private static final int RESULT_CODE_PHOTOPICKER = 1001;
    private static final int RESULT_CODE_HOTSPOT = 1002;
}
