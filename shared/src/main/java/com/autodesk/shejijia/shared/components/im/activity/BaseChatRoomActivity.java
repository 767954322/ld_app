package com.autodesk.shejijia.shared.components.im.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.im.IWorkflowDelegate;
import com.autodesk.shejijia.shared.components.im.constants.BroadCastInfo;
import com.autodesk.shejijia.shared.components.im.constants.MPChatConstants;
import com.autodesk.shejijia.shared.components.im.adapter.ChatRoomAdapter;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatCommandInfo;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatMessage;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatMessages;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatProjectInfo;
import com.autodesk.shejijia.shared.components.im.manager.MPChatHttpManager;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPAudioManager;
import com.autodesk.shejijia.shared.components.common.utility.MPFileUtility;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.ScreenUtil;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BaseChatRoomActivity extends NavigationBarActivity implements ChatRoomAdapter.MessagesListInterface,
                                                                           View.OnClickListener,
                                                                           ViewTreeObserver.OnGlobalLayoutListener {
    public static final String THREAD_ID = "thread_id"; //pass if chat needs to happen on this thread
    public static final String ASSET_ID = "asset_id"; // pass if this chat is related to project
    public static final String MEMBER_TYPE = "member_type";
    public static final String RECIEVER_USER_ID = "reciever_user_id"; //if thread is not passed, new thread is created with this user
    public static final String RECIEVER_HS_UID = "reciever_hs_uid";
    public static final String RECIEVER_USER_NAME = "reciever_user_name";
    public static final String ACS_MEMBER_ID = "acs_member_id"; //logged in user
    public static final String PROJECT_INFO = "project_info";

    public interface CallBack {
        void call();
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_chat_room_base;
    }

    @Override
    protected void initView() {
        super.initView();

        mMessageListView = (ListView) findViewById(R.id.message_list_view);
        mViewHeader = LayoutInflater.from(this).inflate(R.layout.chat_room_loadmore_header, null);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mLoadmoreContent = mViewHeader.findViewById(R.id.loadmore_content);

        setHeaderViewVisibility(false);
        mMessageListView.addHeaderView(mViewHeader);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        if (savedInstanceState != null)
            getInstanceStateFromBundle(savedInstanceState);
        else {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            getInstanceStateFromBundle(bundle);
        }

        mMessageAdapter = new ChatRoomAdapter(this, this);
        mMessageListView.setAdapter(mMessageAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();

        mViewHeader.findViewById(R.id.chat_header).setOnClickListener(this);
        View rootView = findViewById(R.id.ll_parent_layout);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        registerBroadCast();

       mIWorkflowDelegate = AdskApplication.getInstance().getIMWorkflowDelegate();
    }


    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }


    @Override
    protected void onPause() {
        super.onPause();
        stopPlayingVoice();
    }

    @Override
    protected void onDestroy() {
        View rootView = findViewById(R.id.ll_parent_layout);
        rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

        unregisterReceiver(mNewMessageBroadCastReceiver);

        super.onDestroy();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        putInstanceStateToBundle(outState);
        super.onSaveInstanceState(outState);
    }


    protected void getInstanceStateFromBundle(Bundle bundle) {
        mThreadId = bundle.getString(THREAD_ID);
        mAssetId = bundle.getString(ASSET_ID);
        mMemberType = bundle.getString(MEMBER_TYPE);
        mRecieverUserId = bundle.getString(RECIEVER_USER_ID);
        mReceiverHsUid = bundle.getString(RECIEVER_HS_UID);
        mRecieverUserName = bundle.getString(RECIEVER_USER_NAME);
        mAcsMemberId = bundle.getString(ACS_MEMBER_ID);
        mProjectInfo = bundle.getParcelable(PROJECT_INFO);

    }

    protected void putInstanceStateToBundle(Bundle bundle) {
        bundle.putString(THREAD_ID, mThreadId);
        bundle.putString(ASSET_ID, mAssetId);
        bundle.putString(MEMBER_TYPE, mMemberType);
        bundle.putString(RECIEVER_USER_ID, mRecieverUserId);
        bundle.putString(RECIEVER_HS_UID, mReceiverHsUid);
        bundle.putString(RECIEVER_USER_NAME, mRecieverUserName);
        bundle.putString(ACS_MEMBER_ID, mAcsMemberId);
        bundle.putParcelable(PROJECT_INFO, mProjectInfo);
    }


    @Override
    public int getMessageCount() {
        return mMessageList.size();
    }

    @Override
    public MPChatMessage getMessageForIndex(int index) {
        if (index < mMessageList.size())
            return mMessageList.get(index);

        return null;
    }

    @Override
    public Boolean ifLoggedInUserIsConsumer() {
        return Constant.UerInfoKey.CONSUMER_TYPE.equals(mMemberType);
    }

    @Override
    public Boolean ifMessageSenderIsLoggedInUser(int index) {
        MPChatMessage msg = getMessageForIndex(index);
        if (msg != null)
            return mAcsMemberId.equalsIgnoreCase("" + msg.sender_member_id);

        return false;
    }

    @Override
    public void onMessageCellClicked(int index) {
        MPChatMessage msg = getMessageForIndex(index);

        if (msg != null) {
            switch (msg.message_media_type) {
                case eTEXT:
                    break;
                case eIMAGE:
                    Intent intent = new Intent(this, MPFileHotspotActivity.class);
                    intent.putExtra(MPFileHotspotActivity.RECEIVERID, mRecieverUserId);
                    intent.putExtra(MPFileHotspotActivity.LOGGEDINUSERID, mAcsMemberId);
                    intent.putExtra(MPFileHotspotActivity.PARENTTHREADID, mThreadId);
                    intent.putExtra(MPFileHotspotActivity.SERVERFILEURL, msg.body);
                    intent.putExtra(MPFileHotspotActivity.RECEIVERNAME, mRecieverUserName);
                    intent.putExtra(MPFileHotspotActivity.FILEID, String.valueOf(msg.media_file_id));
                    this.startActivity(intent);
                    break;
                case eAUDIO:
                    onAudioCellClicked(index);
                    break;
                case eCOMMAND:
                    if (mIWorkflowDelegate != null)
                    {
                        MPChatCommandInfo info = MPChatMessage.getCommandInfoFromMessage(msg);
                        mIWorkflowDelegate.onCommandCellClicked(this,info);
                    }
                    break;

                default:
                    break;
            }

        }
    }

    @Override
    public String getLoggedinMemberId() {
        return mAcsMemberId;
    }

    public int getAudioMessageDuration(int index) {
        MPChatMessage msg = getMessageForIndex(index);
        final File audioLocalFile = MPFileUtility.getFileFromName(this, String.valueOf(msg.media_file_id), MPChatConstants.AUDIO_FILE_EXT);
        MPAudioManager manager = MPAudioManager.getInstance();
        if (audioLocalFile.exists())
            return manager.getAudioDuration(audioLocalFile.getAbsolutePath());
        else
            return -1;
    }

    public boolean isAudioMessagePlayingForIndex(int index) {
        MPChatMessage msg = getMessageForIndex(index);
        final File audioLocalFile = MPFileUtility.getFileFromName(this, String.valueOf(msg.media_file_id), MPChatConstants.AUDIO_FILE_EXT);
        MPAudioManager manager = MPAudioManager.getInstance();
        if (audioLocalFile.exists())
            return manager.isCurrentlyPlayingFile(audioLocalFile.getAbsolutePath());
        else
            return false;
    }


    public ChatRoomAdapter.eDownloadState isAudioFileDownloading(int index) {
        MPChatMessage msg = getMessageForIndex(index);

        if (mAudioFileDownloadState.containsKey(msg.media_file_id))
            return mAudioFileDownloadState.get(msg.media_file_id);
        else
            return ChatRoomAdapter.eDownloadState.eNotStarted;
    }


    public boolean isDateChangeForIndex(int index) {
        if (index > 0) {
            MPChatMessage prevMsg = getMessageForIndex(index - 1);
            MPChatMessage curMsg = getMessageForIndex(index);

            Date prevDate = DateUtil.acsDateToDate(prevMsg.sent_time);
            Date curDate = DateUtil.acsDateToDate(curMsg.sent_time);

            if (prevDate != null && curDate != null)
                return (DateUtil.checkIfDateChangeIn(prevDate, curDate));
        }

        return true;
    }


    public View getMessageCellOfListView(int index) {
        int firstPosition = mMessageListView.getFirstVisiblePosition() - mMessageListView.getHeaderViewsCount();
        int resultIndex = index - firstPosition;
        if (resultIndex < 0 || resultIndex >= mMessageListView.getChildCount())
            return null;

        return mMessageListView.getChildAt(resultIndex);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.chat_header)
        {
            retrieveThreadMessagesWithOffset(mThreadId, mMessageList.size());

        }
    }


    @Override
    public void onGlobalLayout() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.chat_parent_layout);
        if (layout != null) {
            int[] location = new int[]{-1, -1};
            layout.getLocationOnScreen(location);
            int yPos = (int) ScreenUtil.convertPixelsToDp(this, location[1]);

            int measuredHeight = (int) ScreenUtil.convertPixelsToDp(this, layout.getMeasuredHeight());
            int[] screenDimension = ScreenUtil.getScreenDimensionsInDp(this);

            int screenHeight = screenDimension[1];
            if (screenHeight - yPos > measuredHeight + 100) // picking 100 just as a tolerance
            {
                if (mMessageList.size() > 0)
                    mMessageListView.smoothScrollToPosition(mMessageList.size() - 1 + mMessageListView.getHeaderViewsCount());

                mIsKeyboardVisible = true;
            } else
                mIsKeyboardVisible = false;
        }
    }

    protected void showLoadingIndicator() {
        if (mProgressBar != null)
            mProgressBar.setVisibility(View.VISIBLE);
    }

    protected void hideLoadingIndicator() {
        if (mProgressBar != null)
            mProgressBar.setVisibility(View.GONE);
    }

    private void stopPlayingVoice() {
        MPAudioManager manager = MPAudioManager.getInstance();
        manager.stopPlaying();
    }


    public void hideRightButton() {
        super.setVisibilityForNavButton(ButtonType.RIGHT, false);
    }


    protected void markThreadAsRead() {
        MPChatHttpManager.getInstance().markThreadAsRead(mAcsMemberId, mThreadId, new OkStringRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
            }

            @Override
            public void onResponse(String s) {
            }
        });
    }

    protected void retrieveThreadMessagesWithOffset(String threadId, final int offSet) {
        if (!mIsNextPageRequestRunning) {
            mIsNextPageRequestRunning = true;
            OkStringRequest.OKResponseCallback callback = new OkStringRequest.OKResponseCallback() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    MPNetworkUtils.logError(TAG, error);
                    mIsNextPageRequestRunning = false;
                    hideLoadingIndicator();
                }

                @Override
                public void onResponse(String response) {
                    mIsNextPageRequestRunning = false;
                    MPChatMessages msgs = MPChatMessages.fromJSONString(response);
                    onMessagesReceived(msgs);
                    hideLoadingIndicator();
                }
            };
            MPChatHttpManager.getInstance().retrieveThreadMessages(mAcsMemberId, threadId,
                    offSet, LIMIT, callback);
        } else
            hideLoadingIndicator();
    }


    protected void onMessagesReceived(MPChatMessages msgs) {
        ArrayList<MPChatMessage> messages = msgs.messages;

        if (msgs.offset == 0)
            reloadMessages(messages);
        else
            insertMessages(messages);

        if (messages.size() < LIMIT)
            setHeaderViewVisibility(false);
        else
            setHeaderViewVisibility(true);
    }

    protected void refresh() {
        if (mThreadId == null)
            return;

        showLoadingIndicator();
        markThreadAsRead();
        retrieveThreadMessagesWithOffset(mThreadId, 0);

        if (mAssetId != null)
            getProjectInfo();
    }


    protected void setNewThreadId(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            String thread_Id = obj.optString("thread_id");
            if (mThreadId == null)
                mThreadId = thread_Id;
        } catch (Exception e) {
        }
    }

    private void reloadMessages(ArrayList<MPChatMessage> messages) {
        mMessageList.clear();
        for (int i = messages.size() - 1; i >= 0; i--)
            mMessageList.add(messages.get(i));

        mMessageAdapter.notifyDataSetChanged();
        mMessageListView.setSelection(mMessageList.size() - 1 + mMessageListView.getHeaderViewsCount());
    }


    private void insertMessages(ArrayList<MPChatMessage> messages) {
        for (int i = 0; i < messages.size(); ++i)
            mMessageList.add(0, messages.get(i));

        mMessageAdapter.notifyDataSetChanged();
        mMessageListView.setSelection(messages.size() + mMessageListView.getHeaderViewsCount());
    }


    private void addMessage(MPChatMessage message) {

        if (checkIfMessageAlreadyExist(message))
            return;

        mMessageList.add(message);
        mMessageAdapter.notifyDataSetChanged();
        mMessageListView.smoothScrollToPosition(mMessageList.size() - 1 + mMessageListView.getHeaderViewsCount());
    }


    private boolean checkIfMessageAlreadyExist(MPChatMessage msg)
    {
        boolean exist = false;
        for (int i = 0; i < mMessageList.size(); ++i)
        {
            MPChatMessage currentMsg = mMessageList.get(i);
            if (currentMsg.message_id.equalsIgnoreCase(msg.message_id))
            {
                exist = true;
                break;
            }
        }

        return exist;
    }


    private void onAudioCellClicked(final int index) {
        final MPChatMessage msg = getMessageForIndex(index);
        String serverURL = msg.body + MPChatConstants.MP_AUDIO_FILE_NAME;
        final File audioLocalFile = MPFileUtility.getFileFromName(this, String.valueOf(msg.media_file_id), MPChatConstants.AUDIO_FILE_EXT);
        if (audioLocalFile.exists())
            updateAndPlayAudioMessage(index);
        else {
            mAudioFileDownloadState.put(msg.media_file_id, ChatRoomAdapter.eDownloadState.eInProgress);
            MPChatHttpManager.getInstance().downloadFileFromURL(serverURL, audioLocalFile.getAbsolutePath(), true, new MPChatHttpManager.ResponseHandler() {
                @Override
                public void onSuccess(String response) {
                    mAudioFileDownloadState.put(msg.media_file_id, ChatRoomAdapter.eDownloadState.eSuccess);
                    updateAndPlayAudioMessage(index);
                }

                @Override
                public void onFailure() {
                    mAudioFileDownloadState.put(msg.media_file_id, ChatRoomAdapter.eDownloadState.eFailure);
                    mMessageAdapter.updateMessageCell(index);
                }
            });

            mMessageAdapter.updateMessageCell(index);
        }
    }


    private void updateAndPlayAudioMessage(final int index) {
        try {
            MPAudioManager manager = MPAudioManager.getInstance();
            MPChatMessage msg = getMessageForIndex(index);
            final File audioLocalFile = MPFileUtility.getFileFromName(this, String.valueOf(msg.media_file_id), MPChatConstants.AUDIO_FILE_EXT);
            String localURL = audioLocalFile.getAbsolutePath();
            if (isAudioMessagePlayingForIndex(index)) {
                manager.stopPlaying();
                mMessageAdapter.updateMessageCell(index);
            } else {
                manager.startPlayingFile(localURL, new MPAudioManager.AudioPlayerListener() {
                    @Override
                    public void onAudioPlayingStart() {
                        mMessageAdapter.updateMessageCell(index);
                    }

                    @Override
                    public void onAudioPlayingEnd() {
                        mMessageAdapter.updateMessageCell(index);
                    }

                    @Override
                    public void onAudioPlayingError() {
                        mMessageAdapter.updateMessageCell(index);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            mMessageAdapter.updateMessageCell(index);
        }
    }

    protected void onNewMessageReceived(Intent intent) {
        try {
            String json_Msg = intent.getStringExtra("json");
            JSONArray arr = new JSONArray(json_Msg);
            MPChatMessage message = MPChatMessage.fromJSONObject(arr.optJSONObject(0));

            if (message.thread_id.equalsIgnoreCase(mThreadId)) {
                MPChatHttpManager.getInstance().markThreadAsRead(mAcsMemberId, mThreadId, new OkStringRequest.OKResponseCallback() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        MPNetworkUtils.logError(TAG, volleyError);
                    }

                    @Override
                    public void onResponse(String s) {
                    }
                });

                if (mMessageList.size() > 0)
                    addMessage(message);
                else
                    refresh();
            }
        } catch (JSONException e) {

        }
    }



    private void registerBroadCast() {
        mNewMessageBroadCastReceiver = new NewMessageBroadCastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadCastInfo.RECEVIER_RECEIVERMESSAGE);
        filter.addAction(BroadCastInfo.MPCHAT_CONNECT_NOTIFICATION);
        this.registerReceiver(mNewMessageBroadCastReceiver, filter);
    }

    private class NewMessageBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(BroadCastInfo.RECEVIER_RECEIVERMESSAGE))
                onNewMessageReceived(intent);
            else if (intent.getAction().equalsIgnoreCase(BroadCastInfo.MPCHAT_CONNECT_NOTIFICATION))
                refresh();
        }
    }


    protected void getProjectInfo() {
        assert (mAssetId != null);

        String designerId;

        if (mMemberType.equalsIgnoreCase(Constant.UerInfoKey.DESIGNER_TYPE))
            designerId = mAcsMemberId;
        else
            designerId = mRecieverUserId;

        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(final JSONObject jsonObject) {
                try {
                    String userInfo = new String(jsonObject.toString().getBytes("ISO-8859-1"),
                            "UTF-8");

                    mProjectInfo = MPChatProjectInfo.fromJSONString(userInfo);
                    //TODO: Update the UI based on the project info
                } catch (Exception e) {
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
            }
        };

        if (mIWorkflowDelegate != null)
            mIWorkflowDelegate.getProjectInfo(mAssetId, designerId,okResponseCallback);
    }

    private void setHeaderViewVisibility(boolean visible) {
        if (visible) {
            mViewHeader.setVisibility(View.VISIBLE);
            mLoadmoreContent.setVisibility(View.VISIBLE);
        } else {
            mViewHeader.setVisibility(View.GONE);
            mLoadmoreContent.setVisibility(View.GONE);
        }
    }


    protected boolean mIsNextPageRequestRunning;
    protected ListView mMessageListView;
    protected View mViewHeader;
    protected View mLoadmoreContent;
    protected ProgressBar mProgressBar;

    protected boolean mIsKeyboardVisible = false;

    protected String mThreadId;
    protected String mAssetId;
    protected String mMemberType;
    protected String mAcsMemberId;
    protected String mRecieverUserId;
    protected String mReceiverHsUid;
    protected String mRecieverUserName;
    protected MPChatProjectInfo mProjectInfo;


    IWorkflowDelegate mIWorkflowDelegate;


    //mMessageList stores the messages with oldest (at begining) to newest (at last)
    protected ArrayList<MPChatMessage> mMessageList = new ArrayList<MPChatMessage>();
    protected ChatRoomAdapter mMessageAdapter;

    private NewMessageBroadCastReceiver mNewMessageBroadCastReceiver;
    private Map<Integer, ChatRoomAdapter.eDownloadState> mAudioFileDownloadState = new HashMap<Integer, ChatRoomAdapter.eDownloadState>();

    protected static final int LIMIT = 20;

}
