package com.autodesk.shejijia.shared.components.im.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.im.constants.BroadCastInfo;
import com.autodesk.shejijia.shared.components.im.constants.HotSpotsInfo;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatConversation;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatConversations;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatEntities;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatEntity;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatProjectInfo;
import com.autodesk.shejijia.shared.components.im.manager.MPChatHttpManager;
import com.autodesk.shejijia.shared.components.common.utility.ImageProcessingUtil;

import com.autodesk.shejijia.shared.components.im.widget.MPFileHotspotView;
import com.autodesk.shejijia.shared.components.common.utility.Device;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPFileUtility;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.SharedPreferencesUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class MPFileHotspotActivity extends NavigationBarActivity
{
    final public static String FILEID = "fileid";
    final public static String SERVERFILEURL = "serverfileurl";
    final public static String RECEIVERID = "receiverid";
    final public static String RECEIVERNAME = "receivername";
    final public static String LOGGEDINUSERID = "loggedinuserid";
    final public static String LOCALIMAGEURL = "localimageurl";
    final public static String PARENTTHREADID = "parentthreadid";
    final public static String PROJECT_INFO = "project_info";
    final public static String MEDIA_TYPE = "media_type";

    public class HotspotImageData
    {
        public int org_img_width;
        public int org_img_height;
        public float img_magnification;
        public RelativeLayout parentLayout;
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.activity_image_sign;
    }


    @Override
    protected void initView()
    {
        super.initView();

        mFileHotspotView = (MPFileHotspotView) findViewById(R.id.singHotSpotImageView);
        mAddHotSpotButton = (ImageButton) findViewById(R.id.add_hot_icon);
        mHotspotsContainer = (RelativeLayout) findViewById(R.id.hotSpots_frame);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mOnBoardingLayout = (ViewGroup) findViewById(R.id.onboarding_layout);

        mAddHotSpotButton.setEnabled(false);
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
    protected void initListener()
    {
        super.initListener();

        mAddHotSpotButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mAddHotspotEnabled = true;
            }
        });

        mFileHotspotView.setHotSpotImageViewListener(new MPFileHotspotView.HotSpotImageViewListender()
        {
            @Override
            public boolean shouldInsertNewLocation()
            {
                return (mAddHotspotEnabled && getCurrentHotspotsCount() < HotSpotsInfo.MaxHotSpotsNumber);
            }

            @Override
            public void didFinishwithAddingNewHotSpot(Point pt)
            {
                openImageChatRoom(null, pt);
                mAddHotspotEnabled = false;
            }

            @Override
            public void tapOnHotSpot(View view)
            {
                MPChatConversation conversation = (MPChatConversation) view.getTag();

                if (conversation != null)
                    openImageChatRoom(conversation.thread_id, new Point(conversation.x_coordinate, conversation.y_coordinate));
            }
        });

        if (mOnBoardingLayout != null)
        {
           mOnBoardingLayout.setOnClickListener(new View.OnClickListener()
           {
               @Override
               public void onClick(View v)
               {
                   ++mTapCount;

                   if (mTapCount == 1)
                       showOnboardingSecondPage();
                   else
                   {
                       saveOnboardingSettings();
                       finishOnBoarding();
                   }
               }
           });
        }

        registerBroadCast();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        reloadData();
    }

    @Override
    public void onBackPressed()
    {
        setResultIntent();
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        putInstanceStateToBundle(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == HotSpotsInfo.imgChatResultCode)
        {
            mParentThreadId = data.getStringExtra(ImageChatRoomActivity.PARENTTHREADID);
            mFileId = data.getStringExtra(ImageChatRoomActivity.FILEID);

            //TODO: check if this is called before onResume
        }
    }

    @Override
    protected String getActivityTitle()
    {
        return getResources().getString(R.string.tag_images);
    }


    @Override
    protected String getRightNavButtonTitle()
    {
        return getResources().getString(R.string.send);
    }


    @Override
    protected void rightNavButtonClicked(View view)
    {
        mSendButton = view;
        sendImage();
        mProgressBar.setVisibility(View.VISIBLE);
    }


    @Override
    protected void leftNavButtonClicked(View view)
    {
        setResultIntent();
        super.leftNavButtonClicked(view);
    }


    private void setResultIntent()
    {
        Intent intent = new Intent();
        intent.putExtra(PARENTTHREADID, mParentThreadId);
        setResult(RESULT_OK, intent);
    }

    private int getCurrentHotspotsCount()
    {
        int count = 0;
        if (mChatConversations != null && mChatConversations.conversations != null)
            count = mChatConversations.conversations.size();

        return count;
    }


    private void reloadData()
    {
        mChatConversations = null;
        mAddHotSpotButton.setEnabled(false);

        clearHotspots();

        mTapCount = 0;

        if (!shouldShowOnboarding())
            removeAllOnboardingViews();

        mProgressBar.setVisibility(View.VISIBLE);

        mFileHotspotView.setImageResource(0);
        mFileHotspotView.setImageDrawable(null);

        if (mFileId != null)
            downloadImageFromServer();
        else if (mLocalImageFileURL != null)
            loadLocalFile(mLocalImageFileURL);
    }


    private void downloadImageFromServer()
    {
        setVisibilityForNavButton(ButtonType.RIGHT, false);

        if (mServerFileURL != null && !mServerFileURL.isEmpty())
            downloadImageFromServerWithFileURL();
        else
            downloadImageFromServerWithFileId();
    }


    private void downloadImageFromServerWithFileURL()
    {
        String fileName = mFileId + ".jpg";
        final File file = MPFileUtility.getFileFromName(UIUtils.getContext(), fileName);

        if (file.exists())
            loadLocalFile(file.getAbsolutePath());
        else
        {
            MPChatHttpManager.getInstance().downloadFileFromURL(mServerFileURL + "Original.jpg", file.getAbsolutePath(), true, new MPChatHttpManager.ResponseHandler()
            {
                @Override
                public void onSuccess(String response)
                {

                    mProgressBar.setVisibility(View.GONE);
                    loadLocalFile(file.getAbsolutePath());
                }

                @Override
                public void onFailure()
                {
                    mProgressBar.setVisibility(View.GONE);
                }
            });
        }
    }


    private void downloadImageFromServerWithFileId()
    {
        String fileName = mFileId + ".jpg";
        final File file = MPFileUtility.getFileFromName(UIUtils.getContext(), fileName);

        if (file.exists())
            loadLocalFile(file.getAbsolutePath());
        else
        {

            MPChatHttpManager.getInstance().downloadFileId(mFileId, file.getAbsolutePath(), new MPChatHttpManager.ResponseHandler()
            {
                @Override
                public void onSuccess(String response)
                {
                    mProgressBar.setVisibility(View.GONE);
                    loadLocalFile(file.getAbsolutePath());
                }

                @Override
                public void onFailure()
                {
                    mProgressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    private void clearHotspots()
    {
        mHotspotsContainer.setVisibility(View.GONE);

        RelativeLayout parentLayout = (RelativeLayout) findViewById(R.id.singHotSpotImageView_frame);
        ArrayList<Integer> indices = new ArrayList<Integer>();

        for (int i = 0; i < parentLayout.getChildCount(); i++) {

            final View view = parentLayout.getChildAt(i);

            Object object = view.getTag(view.getId());

            if(object == null || !object.getClass().equals(Point.class))
                continue;

            indices.add(i);
        }

        if(!indices.isEmpty())
            parentLayout.removeViews(indices.get(0), indices.size());
    }


    private void updateImageView(Bitmap loadedImage)
    {
        int org_img_width, org_img_height;

        org_img_width = loadedImage.getWidth();
        org_img_height = loadedImage.getHeight();

        HotspotImageData imageData = new HotspotImageData();
        imageData.parentLayout = (RelativeLayout) findViewById(R.id.singHotSpotImageView_frame);
        imageData.org_img_width = org_img_width;
        imageData.org_img_height = org_img_height;

        ImageProcessingUtil imageProcessingUtil = new ImageProcessingUtil();

        float img_magnification = imageProcessingUtil.getImageMagnification(loadedImage, false);
        imageData.img_magnification = img_magnification;

        int width = (int) (org_img_width * img_magnification);
        int height = (int) (org_img_height * img_magnification);

        Bitmap resizedImage = imageProcessingUtil.getResizedBitmap(loadedImage, width, height);
        loadedImage.recycle();

        mFileHotspotView.setImageBitmap(resizedImage);

        mFileHotspotView.setDataFromImageSingActivity(MPFileHotspotActivity.this, imageData);
        mFileHotspotView.setEnableTouch(true);

        FadeInBitmapDisplayer.animate(mFileHotspotView, 500);

        if (shouldShowOnboarding())
            showOnboardingFirstPage();
    }


    private void loadLocalFile(final String localFileURL)
    {
        ImageView view = mFileHotspotView;
        mLocalImageFileURL = localFileURL;

        ImageUtils.loadHotspotImage("file://" + localFileURL, view, new SimpleImageLoadingListener()
        {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
            {
                updateImageView(loadedImage);

                if (mFileId != null && !mFileId.isEmpty())
                    retrieveThreadsAndUpdateView(mFileId);
                else
                {
                    mProgressBar.setVisibility(View.GONE);
                    mAddHotSpotButton.setEnabled(true);
                }
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason)
            {
                mProgressBar.setVisibility(View.GONE);

                File file = MPFileUtility.getFileFromPath(UIUtils.getContext(), localFileURL);

                if (file != null && file.exists())
                {
                    //delete this file and retry from server
                    MPFileUtility.removeFile(file);
                    reloadData();
                }

            }

        }, null);
    }


    private void retrieveThreadsAndUpdateView(String fileId)
    {
        MPChatHttpManager.getInstance().retrieveFileConversations(fileId, new OkStringRequest.OKResponseCallback()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
                MPNetworkUtils.logError(TAG, volleyError);
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(String s)
            {
                mProgressBar.setVisibility(View.GONE);

                MPChatEntities mpChatEntities = MPChatEntities.fromJSONString(s);
                if (mpChatEntities != null && mpChatEntities.entities != null && !mpChatEntities.entities.isEmpty())
                {
                    MPChatEntity mpChatEntity = mpChatEntities.entities.get(0);
                    if (mpChatEntity != null)
                    {
                        mChatConversations = mpChatEntity.conversations;

                        if (getCurrentHotspotsCount() < HotSpotsInfo.MaxHotSpotsNumber)
                        {
                            mAddHotSpotButton.setEnabled(true);
                        }
                        else
                            mAddHotSpotButton.setEnabled(false);

                        if (mChatConversations != null && mChatConversations.conversations != null)
                        {
                            for (MPChatConversation chatConversation : mChatConversations.conversations)
                            {
                                RelativeLayout view = (RelativeLayout) View.inflate(MPFileHotspotActivity.this, R.layout.view_hot_spot, null);
                                TextView tv = (TextView) view.findViewById(R.id.hotSpot_text);
                                tv.setText(String.valueOf(chatConversation.unread_message_count));

                                if (chatConversation.unread_message_count == 0)
                                    tv.setVisibility(View.GONE);

                                view.setTag(chatConversation);
                                mFileHotspotView.addHotSpots2Map(chatConversation.thread_id, view,
                                        new Point(chatConversation.x_coordinate, chatConversation.y_coordinate));
                            }
                        }
                    }
                }
                else
                    mAddHotSpotButton.setEnabled(true);
            }
        });
    }


    private void sendImage()
    {
        mSendButton.setEnabled(false);
        mAddHotSpotButton.setEnabled(false);
        MPChatHttpManager.getInstance().sendMediaMessage(mLoggedInUserId, mReceiverId, "New",
                mParentThreadId, new File(mLocalImageFileURL), "IMAGE", new MPChatHttpManager.ResponseHandler()
                {
                    @Override
                    public void onSuccess(String response)
                    {
                        try
                        {
                            JSONObject jObj = new JSONObject(response);
                            mFileId = jObj.optString("file_id");
                            if (mParentThreadId == null)
                                mParentThreadId = jObj.optString("thread_id");
                        } catch (Exception e)
                        {
                        }

                        attachedImageToAsset();
                        MPFileUtility.removeFile(mLocalImageFileURL);
                        mProgressBar.setVisibility(View.GONE);
                        MPFileHotspotActivity.this.finish();
                    }

                    @Override
                    public void onFailure()
                    {
                        //TODO error out for resending
                        mProgressBar.setVisibility(View.GONE);
                        MPFileHotspotActivity.this.finish();
                    }
                });
    }


    private void attachedImageToAsset()
    {
        if (mProjInfo != null && mProjInfo.asset_id > 0)
        {
            if (mProjInfo.is_beishu)
            {
                MPChatHttpManager.getInstance().addFileToAsset(mFileId, String.valueOf(mProjInfo.asset_id), new OkStringRequest.OKResponseCallback()
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
                MPChatHttpManager.getInstance().addFileToWorkflowStep(mFileId, String.valueOf(mProjInfo.asset_id),
                        mProjInfo.workflow_id, mProjInfo.current_step, new OkStringRequest.OKResponseCallback()
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

    private void registerBroadCast()
    {
        mBroadcastReceiver = new HotspotBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadCastInfo.RECEVIER_RECEIVERMESSAGE);
        registerReceiver(mBroadcastReceiver, filter);
    }


    class HotspotBroadcastReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (intent.getAction().equals(BroadCastInfo.RECEVIER_RECEIVERMESSAGE))
            {
                if (mFileId != null)
                {
                    clearHotspots();
                    retrieveThreadsAndUpdateView(mFileId);
                }
            }
        }
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        if (mBroadcastReceiver != null)
            unregisterReceiver(mBroadcastReceiver);
    }


    private void openImageChatRoom(String threadId, Point pt)
    {
        Intent intent = new Intent(this, ImageChatRoomActivity.class);
        intent.putExtra(ImageChatRoomActivity.THREAD_ID, threadId);
        intent.putExtra(ImageChatRoomActivity.ABSOLUTEX, pt.x);
        intent.putExtra(ImageChatRoomActivity.ABSOLUTEY, pt.y);

        intent.putExtra(ImageChatRoomActivity.FILEID, mFileId);
        intent.putExtra(ImageChatRoomActivity.SERVERFILEURL, mServerFileURL);
        intent.putExtra(ImageChatRoomActivity.RECIEVER_USER_ID, mReceiverId);
        intent.putExtra(ImageChatRoomActivity.RECIEVER_USER_NAME, mReceiverName);
        intent.putExtra(ImageChatRoomActivity.ACS_MEMBER_ID, mLoggedInUserId);
        intent.putExtra(ImageChatRoomActivity.LOCALIMAGEURL, mLocalImageFileURL);
        intent.putExtra(ImageChatRoomActivity.PARENTTHREADID, mParentThreadId);
        intent.putExtra(BaseChatRoomActivity.MEMBER_TYPE, AdskApplication.getInstance().getMemberEntity().getMember_type());

        if (mProjInfo != null) {
            intent.putExtra(ImageChatRoomActivity.PROJECT_INFO, mProjInfo);
        }

        startActivityForResult(intent, HotSpotsInfo.imgChatResultCode);
    }


    private void getInstanceStateFromBundle(Bundle bundle)
    {
        mFileId = bundle.getString(FILEID);
        mServerFileURL = bundle.getString(SERVERFILEURL);
        mReceiverId = bundle.getString(RECEIVERID);
        mReceiverName = bundle.getString(RECEIVERNAME);
        mLoggedInUserId = bundle.getString(LOGGEDINUSERID);
        mLocalImageFileURL = bundle.getString(LOCALIMAGEURL);
        mParentThreadId = bundle.getString(PARENTTHREADID);
        mProjInfo = bundle.getParcelable(PROJECT_INFO);
        mMediaType = bundle.getString(MEDIA_TYPE);
    }

    private void putInstanceStateToBundle(Bundle bundle)
    {
        bundle.putString(FILEID, mFileId);
        bundle.putString(SERVERFILEURL, mServerFileURL);
        bundle.putString(RECEIVERID, mReceiverId);
        bundle.putString(RECEIVERNAME, mReceiverName);
        bundle.putString(LOGGEDINUSERID, mLoggedInUserId);
        bundle.putString(LOCALIMAGEURL, mLocalImageFileURL);
        bundle.putString(PARENTTHREADID, mParentThreadId);
        bundle.putParcelable(PROJECT_INFO, mProjInfo);
        bundle.putString(MEDIA_TYPE,mMediaType);
    }


    private void saveOnboardingSettings()
    {
        SharedPreferencesUtils.delete(UIUtils.getContext(), ONBOARDING_SHOWN);
        SharedPreferencesUtils.delete(UIUtils.getContext(),APP_VERSION);

        int currentAppVersion = Device.getVersionCode();
        SharedPreferencesUtils.writeBoolean(ONBOARDING_SHOWN, true);
        SharedPreferencesUtils.writeInt(APP_VERSION,currentAppVersion);
    }

    private boolean shouldShowOnboarding()
    {
        boolean bShow = true;
        int savedAppVersion =  SharedPreferencesUtils.readInt(APP_VERSION);

        if (savedAppVersion > 0)
        {
            //get current bundle version
            int currentAppVersion = Device.getVersionCode();
            boolean bOnBoardingShown = SharedPreferencesUtils.readBoolean(ONBOARDING_SHOWN);

            if ((savedAppVersion == currentAppVersion) && bOnBoardingShown)
                bShow = false;
        }

        return bShow;
    }

    private void showOnboardingFirstPage()
    {
        if (mOnBoardingLayout != null)
        {
            mOnBoardingLayout.bringToFront();
            View view = findViewById(R.id.onboarding_slide1);

            if(view != null)
            {
                view.bringToFront();
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator.ofInt(mOnBoardingLayout, "visibility",View.GONE , View.VISIBLE))
                        .with(ObjectAnimator.ofInt(view, "visibility",View.GONE , View.VISIBLE));
                set.setDuration(500);
                set.setInterpolator(new DecelerateInterpolator());
                set.start();
            }
        }

    }

    private void showOnboardingSecondPage()
    {
        if (mOnBoardingLayout != null)
        {
            View view1 = findViewById(R.id.onboarding_slide1);
            View view2 = findViewById(R.id.onboarding_slide2);

            if(view1 != null && view2 != null)
            {
                view2.bringToFront();
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator.ofInt(view1, "visibility",View.VISIBLE , View.GONE))
                        .with(ObjectAnimator.ofInt(view2, "visibility",View.GONE , View.VISIBLE));
                set.setDuration(500);
                set.setInterpolator(new DecelerateInterpolator());
                set.start();
            }
        }

    }

    private void removeAllOnboardingViews()
    {
        if (mOnBoardingLayout != null)
            mOnBoardingLayout.removeAllViews();
    }

    private void finishOnBoarding()
    {
        if (mOnBoardingLayout != null)
        {
            mOnBoardingLayout.bringToFront();

            View view = findViewById(R.id.onboarding_slide2);

            if(view != null)
            {
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator.ofInt(mOnBoardingLayout, "visibility",View.VISIBLE , View.GONE)).
                        with(ObjectAnimator.ofInt(view, "visibility",View.VISIBLE , View.GONE));
                set.setDuration(500);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter()
                {
                    @Override
                    public void onAnimationCancel(Animator animation)
                    {
                        removeAllOnboardingViews();
                        super.onAnimationCancel(animation);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        removeAllOnboardingViews();
                        super.onAnimationEnd(animation);
                    }
                });

                set.start();
            }
        }

    }

    private MPFileHotspotView mFileHotspotView;
    private ImageButton mAddHotSpotButton;
    private View mSendButton;
    private RelativeLayout mHotspotsContainer;
    private ProgressBar mProgressBar;
    private ViewGroup mOnBoardingLayout;

    private String mFileId;
    private String mServerFileURL;
    private String mReceiverId;
    private String mReceiverName;
    private String mLoggedInUserId;
    private String mLocalImageFileURL;
    private String mParentThreadId;
    private MPChatProjectInfo mProjInfo;
    private String mMediaType;

    private MPChatConversations mChatConversations;
    private HotspotBroadcastReceiver mBroadcastReceiver;
    private boolean mAddHotspotEnabled = false;

    private int mTapCount = 0;
    private static final String ONBOARDING_SHOWN = "MP_HOTSPOT_ONBOARDING_SHOWN";
    private static final String APP_VERSION = "MP_APP_VERSION";
}