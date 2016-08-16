package com.autodesk.shejijia.shared.components.im.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;
import com.autodesk.shejijia.shared.components.im.constants.HotSpotsInfo;
import com.autodesk.shejijia.shared.components.im.manager.ChatEventHandler;
import com.autodesk.shejijia.shared.components.im.manager.MPChatHttpManager;
import com.autodesk.shejijia.shared.components.common.utility.ImageProcessingUtil;
import com.autodesk.shejijia.shared.components.im.widget.MPHotSpotImageViewHelper;
import com.autodesk.shejijia.shared.components.common.uielements.RoundProgressBar;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPFileUtility;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class ImageChatRoomActivity extends BaseChatRoomActivity implements ChatEventHandler.ChatEventHandlerInterface
{
    final public static String FILEID = "fileid";
    final public static String SERVERFILEURL = "serverfileurl";
    final public static String LOCALIMAGEURL = "localimageurl";
    final public static String PARENTTHREADID = "parentthreadid";
    final public static String ABSOLUTEX = "absoluteX";
    final public static String ABSOLUTEY = "absoluteY";

    @Override
    protected int getLayoutResId()
    {
        return R.layout.activity_chat_room_image;
    }

    @Override
    protected void initView()
    {
        super.initView();

        mAudioProgressBar = (RoundProgressBar) findViewById(R.id.roundProgressBar);
        mAudioParentView = (RelativeLayout) findViewById(R.id.audio_recording_parent_view);

        mHotspotImageView = (ImageView) findViewById(R.id.hotspotImageView);
        mMainImageView = (ImageView) findViewById(R.id.mainImageView);

        setImageViewFrame();
    }

    @Override
    protected void initData(Bundle savedInstanceState)
    {
        super.initData(savedInstanceState);

        if (savedInstanceState != null)
            getInstanceStateFromBundle(savedInstanceState);
        else
        {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            getInstanceStateFromBundle(bundle);
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mShouldHideLoadindIndicator = false;
        super.showLoadingIndicator();
        loadSourceImage();
    }

    @Override
    public void onBackPressed()
    {
        setResultIntent();
        super.onBackPressed();
    }


    @Override
    protected void initListener()
    {
        super.initListener();

        LinearLayout layout = (LinearLayout) findViewById(R.id.chat_parent_layout);
        mHandler = new ChatEventHandler(this, layout, this);
    }

    @Override
    protected void getInstanceStateFromBundle(Bundle bundle)
    {
        super.getInstanceStateFromBundle(bundle);

        mSourceImagePath = bundle.getString(LOCALIMAGEURL);
        mImageAbsolutePt.x = bundle.getInt(ABSOLUTEX);
        mImageAbsolutePt.y = bundle.getInt(ABSOLUTEY);
        mServerFileURL = bundle.getString(SERVERFILEURL);
        mFileId = bundle.getString(FILEID);
        mParentThreadId = bundle.getString(PARENTTHREADID);
    }

    @Override
    protected void putInstanceStateToBundle(Bundle bundle)
    {
        super.putInstanceStateToBundle(bundle);

        bundle.putString(LOCALIMAGEURL, mSourceImagePath);
        bundle.putInt(ABSOLUTEX, mImageAbsolutePt.x);
        bundle.putInt(ABSOLUTEY, mImageAbsolutePt.y);
        bundle.putString(SERVERFILEURL, mServerFileURL);
        bundle.putString(FILEID, mFileId);
        bundle.putString(PARENTTHREADID, mParentThreadId);
    }

    @Override
    public void onSendTextClicked(String msg)
    {
        String filteredMessage = StringUtils.filterSpecialCharacters(msg);
        assert (filteredMessage != null);

        initiateTextMessageSendSequence(filteredMessage);
    }

    @Override
    public void customButtonDidClicked()
    {
        assert (false);
    }

    @Override
    public void audioRecordingStarted()
    {
        mAudioParentView.setVisibility(View.VISIBLE);
    }

    @Override
    public void audioRecordingEnded(final String filePath)
    {
        if (filePath != null)
            initiateAudioMessageSendSequence(filePath, new CallBack()
            {
                @Override
                public void call()
                {
                    showAudioMessageErrorDialog(filePath);
                }
            });

        mAudioParentView.setVisibility(View.GONE);
    }

    @Override
    public boolean shouldHideCustomButton()
    {
        return true;
    }

    @Override
    protected String getActivityTitle()
    {
        return getResources().getString(R.string.imagechatroom_title);
    }


    @Override
    protected void leftNavButtonClicked(View view)
    {
        setResultIntent();
        super.leftNavButtonClicked(view);
    }

    @Override
    protected void hideLoadingIndicator()
    {
        if (mProgressBar != null && mShouldHideLoadindIndicator)
            mProgressBar.setVisibility(View.GONE);
    }


    protected void onNewMessageReceived(Intent intent)
    {
        super.onNewMessageReceived(intent);

        //TODO: Animate imageviewup if keyboard is open
        updateMainImageViewVisibility();
    }

    @Override
    public void onGlobalLayout()
    {
        super.onGlobalLayout();

        if (mMessageList != null && !mMessageList.isEmpty())
            updateMainImageViewVisibility();
    }

    private void updateMainImageViewVisibility()
    {
        View view  = findViewById(R.id.mainImageViewframe);
        int visibility = View.VISIBLE;

        if(mIsKeyboardVisible)
            visibility = View.GONE;

        if (view != null)
            view.setVisibility(visibility);

    }

    private void initiateTextMessageSendSequence(final String body)
    {
        if (mThreadId != null) // if there is already a thread available
        {
            OkStringRequest.OKResponseCallback replyCallback = new OkStringRequest.OKResponseCallback()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    MPNetworkUtils.logError(TAG, error);
                }

                @Override
                public void onResponse(String response)
                {
                }
            };

            MPChatHttpManager.getInstance().replyToThread(mAcsMemberId, mThreadId, body, replyCallback);
        }
        else if (mFileId != null) //is file already uploaded
        {
            disableUserInteraction();
            showLoadingIndicator();

            initiateNewThreadSequenceWithTextMessage(body);
        }
        else //neither file is uploaded nor thread exist
        {
            disableUserInteraction();
            showLoadingIndicator();

            final String mediaType = "IMAGE"; //important, this should be only for image not audio
            MPChatHttpManager.getInstance().sendMediaMessage(mAcsMemberId, mRecieverUserId, "Subject",
                    mParentThreadId, new File(mSourceImagePath), mediaType, new MPChatHttpManager.ResponseHandler()
                    {
                        @Override
                        public void onSuccess(String response)
                        {
                            try
                            {
                                JSONObject jObj = new JSONObject(response);
                                String parentThreadId = jObj.optString("thread_id");
                                String fileId = jObj.optString("file_id");

                                if (mParentThreadId == null)
                                    mParentThreadId = parentThreadId;

                                setUploadedImageId(fileId);

                                attachedImageToAsset();
                                initiateNewThreadSequenceWithTextMessage(body);
                            }
                            catch (JSONException e)
                            {
                                mShouldHideLoadindIndicator = true;
                                hideLoadingIndicator();
                                enableUserInteraction();
                            }
                        }

                        @Override
                        public void onFailure()
                        {
                            mShouldHideLoadindIndicator = true;
                            hideLoadingIndicator();
                            MPNetworkUtils.logError(TAG, "initiateAudioMessageSendSequence failed");
                            enableUserInteraction();
                        }
                    });
        }

    }


    private void setResultIntent()
    {
        Intent intent = new Intent();
        intent.putExtra(PARENTTHREADID, mParentThreadId);
        intent.putExtra(FILEID, mFileId);
        setResult(RESULT_OK, intent);
    }


    private void setUploadedImageId(String imageFileId)
    {
        mFileId = imageFileId;
    }


    private void attachedImageToAsset()
    {
        if (mProjectInfo != null && mProjectInfo.asset_id > 0)
        {
            if (mProjectInfo.is_beishu)
            {
                MPChatHttpManager.getInstance().addFileToAsset(mFileId, String.valueOf(mProjectInfo.asset_id), new OkStringRequest.OKResponseCallback()
                {
                    @Override
                    public void onErrorResponse(VolleyError volleyError)
                    {
                        MPNetworkUtils.logError(TAG, volleyError);
                    }

                    @Override
                    public void onResponse(String s)
                    {
                    }
                });

            }
            else
            {
                MPChatHttpManager.getInstance().addFileToWorkflowStep(mFileId, String.valueOf(mProjectInfo.asset_id),
                        mProjectInfo.workflow_id, mProjectInfo.current_step, new OkStringRequest.OKResponseCallback()
                        {
                            @Override
                            public void onErrorResponse(VolleyError volleyError)
                            {
                                MPNetworkUtils.logError(TAG, volleyError);
                            }

                            @Override
                            public void onResponse(String s)
                            {
                            }
                        });
            }
        }
    }


    private void initiateNewThreadSequenceWithTextMessage(String body)
    {
        MPChatHttpManager.getInstance().sendNewThreadMessage(mAcsMemberId, mRecieverUserId, body,
                "Subject", new OkStringRequest.OKResponseCallback()
                {
                    @Override
                    public void onErrorResponse(VolleyError volleyError)
                    {
                        MPNetworkUtils.logError(TAG, volleyError);
                        mShouldHideLoadindIndicator = true;
                        hideLoadingIndicator();
                        enableUserInteraction();
                    }

                    @Override
                    public void onResponse(String s)
                    {
                        setNewThreadId(s);
                        assert (mThreadId != null);

                        refresh();
                        addConversationTofile();
                    }
                });

    }


    private void addConversationTofile()
    {
        MPChatHttpManager.getInstance().addConversationToFile(mFileId, mThreadId, mImageAbsolutePt.x,
                mImageAbsolutePt.y, new OkStringRequest.OKResponseCallback()
                {
                    @Override
                    public void onErrorResponse(VolleyError volleyError)
                    {
                        MPNetworkUtils.logError(TAG, "addConversationToFile Failure");
                        mShouldHideLoadindIndicator = true;
                        hideLoadingIndicator();
                        enableUserInteraction();
                    }

                    @Override
                    public void onResponse(String s)
                    {
                        MPNetworkUtils.logError(TAG, "addConversationToFile Success");
                        mShouldHideLoadindIndicator = true;
                        hideLoadingIndicator();
                        enableUserInteraction();
                    }
                });

    }


    private void initiateAudioMessageSendSequence(final String url, final CallBack errorCallback)
    {
        showLoadingIndicator();

        if (mThreadId != null)
        {
            String fileType = "AUDIO"; //This should be always audio here
            MPChatHttpManager.getInstance().sendMediaMessage(mAcsMemberId, mRecieverUserId, "Subject", mThreadId,
                    new File(url), fileType, new MPChatHttpManager.ResponseHandler()
                    {
                        @Override
                        public void onSuccess(String response)
                        {
                            MPFileUtility.removeFile(url);
                            mShouldHideLoadindIndicator = true;
                            hideLoadingIndicator();
                        }

                        @Override
                        public void onFailure()
                        {
                            MPNetworkUtils.logError(TAG, "initiateAudioMessageSendSequence failure");
                            errorCallback.call();

                            mShouldHideLoadindIndicator = true;
                            hideLoadingIndicator();
                        }
                    });
        }
        else if (mFileId != null)
        {
            disableUserInteraction();

            initiateNewThreadSequenceWithAudioMessage(url, errorCallback);
        }
        else
        {
            disableUserInteraction();

            String mediaType = "IMAGE"; //Important, we are sending image here, that too in parent thread
            MPChatHttpManager.getInstance().sendMediaMessage(mAcsMemberId, mRecieverUserId, "New", mParentThreadId,
                    new File(mSourceImagePath), mediaType, new MPChatHttpManager.ResponseHandler()
                    {
                        @Override
                        public void onSuccess(String response)
                        {
                            try
                            {
                                JSONObject jObj = new JSONObject(response);
                                String parentThreadId = jObj.optString("thread_id");
                                String fileId = jObj.optString("file_id");

                                if (mParentThreadId == null)
                                    mParentThreadId = parentThreadId;

                                setUploadedImageId(fileId);

                                attachedImageToAsset();
                                initiateNewThreadSequenceWithAudioMessage(url, errorCallback);
                            }
                            catch (JSONException e)
                            {
                                errorCallback.call();
                                mShouldHideLoadindIndicator = true;
                                hideLoadingIndicator();
                                enableUserInteraction();
                            }
                        }

                        @Override
                        public void onFailure()
                        {
                            MPNetworkUtils.logError(TAG, "initiateAudioMessageSendSequence failure");
                            errorCallback.call();
                            enableUserInteraction();
                        }
                    }
            );

        }

    }

    private void initiateNewThreadSequenceWithAudioMessage(final String url, final CallBack errorCallback)
    {
        assert (mThreadId == null);

        String mediaType = "AUDIO"; //this call below is not meant for image files
        MPChatHttpManager.getInstance().sendMediaMessage(mAcsMemberId, mRecieverUserId, "New", mThreadId, new File(url),
                mediaType, new MPChatHttpManager.ResponseHandler()
                {
                    @Override
                    public void onSuccess(String response)
                    {
                        setNewThreadId(response);
                        assert (mThreadId != null);

                        refresh();

                        addConversationTofile();

                        MPFileUtility.removeFile(url);

                        mShouldHideLoadindIndicator = true;
                        hideLoadingIndicator();
                    }

                    @Override
                    public void onFailure()
                    {
                        MPNetworkUtils.logError(TAG, "initiateNewThreadSequenceWithAudioMessage failure");
                        errorCallback.call();

                        mShouldHideLoadindIndicator = true;
                        hideLoadingIndicator();
                        enableUserInteraction();
                    }
                });
    }


    private void setImageViewFrame()
    {
        RelativeLayout p = (RelativeLayout) findViewById(R.id.mainImageViewframe);
        LinearLayout.LayoutParams rl = (LinearLayout.LayoutParams) p.getLayoutParams();
        rl.height = (int) (new ImageProcessingUtil().getCurrentImgWH()[2] * HotSpotsInfo.subImg_height_factor);
        p.setLayoutParams(rl);
    }


    private void loadSourceImage()
    {
        if (mSourceImagePath != null && !mSourceImagePath.isEmpty())
            loadLocalImage(mSourceImagePath);
        else
            downloadImageFromServer();
    }


    private void downloadImageFromServer()
    {
        if (mServerFileURL != null && !mServerFileURL.isEmpty())
            downloadImageFromServerWithFileURL();
        else if (mFileId != null)
            downloadImageFromServerWithFileId();
    }


    private void downloadImageFromServerWithFileURL()
    {
        String fileName = mFileId + ".jpg";
        final File file = MPFileUtility.getFileFromName(this, fileName);

        if (file.exists())
            loadLocalImage(file.getAbsolutePath());
        else
        {
            MPChatHttpManager.getInstance().downloadFileFromURL(mServerFileURL + "Original.jpg", file.getAbsolutePath(), true, new MPChatHttpManager.ResponseHandler()
            {
                @Override
                public void onSuccess(String response)
                {
                    loadLocalImage(file.getAbsolutePath());
                }

                @Override
                public void onFailure()
                {
                    mShouldHideLoadindIndicator = true;
                    hideLoadingIndicator();
                }
            });
        }
    }

    private void downloadImageFromServerWithFileId()
    {
        String fileName = mFileId + ".jpg";
        final File file = MPFileUtility.getFileFromName(this, fileName);

        if (file.exists())
            loadLocalImage(file.getAbsolutePath());
        else
        {
            MPChatHttpManager.getInstance().downloadFileId(mFileId, file.getAbsolutePath(), new MPChatHttpManager.ResponseHandler()
            {
                @Override
                public void onSuccess(String response)
                {
                    loadLocalImage(file.getAbsolutePath());
                }

                @Override
                public void onFailure()
                {
                    mShouldHideLoadindIndicator = true;
                    hideLoadingIndicator();
                }
            });
        }
    }


    private void loadLocalImage(final String localFileURL)
    {
        mSourceImagePath = localFileURL;
        final Context context = this;

        ImageUtils.loadFileImageListenr("file://" + mSourceImagePath, mMainImageView, new SimpleImageLoadingListener()
        {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
            {
                MPHotSpotImageViewHelper helper = new MPHotSpotImageViewHelper(context, mMainImageView, mHotspotImageView, mImageAbsolutePt);
                helper.reloadImageWithScale(loadedImage);
                mShouldHideLoadindIndicator = true;
                hideLoadingIndicator();
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason)
            {
                mShouldHideLoadindIndicator = true;
                hideLoadingIndicator();
            }


            @Override
            public void onLoadingCancelled(String imageUri, View view)
            {
                mShouldHideLoadindIndicator = true;
                hideLoadingIndicator();
            }

        }, null);
    }

    private void showAudioMessageErrorDialog(final String filePath)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.chatroom_audio_recording_erroralert_title))
                .setPositiveButton(getString(R.string.chatroom_audio_recording_erroralert_ok),
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                audioRecordingEnded(filePath);
                            }
                        })
                .setNegativeButton(getString(R.string.chatroom_audio_recording_erroralert_cancel),
                        null)
                .setMessage(getString(R.string.chatroom_audio_recording_erroralert_message))
                .create();

        alertDialog.show();
    }

    private void disableUserInteraction()
    {
        mHandler.disableUserInteraction();
    }

    private void enableUserInteraction()
    {
        mHandler.enableUserInteraction();
    }

    private RelativeLayout mAudioParentView;
    private RoundProgressBar mAudioProgressBar;
    private ImageView mMainImageView;
    private ImageView mHotspotImageView;

    private boolean mShouldHideLoadindIndicator = false;

    private String mFileId;
    private String mServerFileURL;
    private String mSourceImagePath;
    private String mParentThreadId = null;
    private Point mImageAbsolutePt = new Point();

    private ChatEventHandler mHandler;
}