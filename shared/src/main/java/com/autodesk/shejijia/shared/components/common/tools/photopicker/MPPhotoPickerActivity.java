package com.autodesk.shejijia.shared.components.common.tools.photopicker;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.im.activity.ChatRoomActivity;
import com.autodesk.shejijia.shared.components.im.fragment.MPPhotoAlbumFragment;
import com.autodesk.shejijia.shared.components.im.fragment.MPPhotoPickerFragment;
import com.autodesk.shejijia.shared.components.im.manager.MPChatHttpManager;
import com.autodesk.shejijia.shared.components.common.utility.ImageProcessingUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPFileUtility;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/23 0023.
 */
public class MPPhotoPickerActivity extends NavigationBarActivity implements View.OnClickListener,
                                                                            MPPhotoPickerFragment.PhotoPickerFragmentListener,
                                                                            MPPhotoAlbumFragment.PhotoAlbumFragmentListener
{
    public static final String ASSET_ID = "assetid";
    public static final String MEMBER_ID = "memberid";
    public static final String X_TOKEN = "xtoken";

    @Override
    protected int getLayoutResId()
    {
        return R.layout.activity_photo_picker;
    }

    @Override
    protected void initView()
    {
        super.initView();
        mSourceButton = (LinearLayout) findViewById(R.id.photopicker_source_button);
        mBlurView = findViewById(R.id.photopicker_blur_view);
        mProgressBar = (ProgressBar) findViewById(R.id.photoactivity_progressbar);
        mFullBlurView = findViewById(R.id.photoactivity_fullblur_view);
        mAlbumFragmentContainer = (FrameLayout) findViewById(R.id.photopicker_album_fragment_container);
    }

    @Override
    protected void initData(Bundle savedInstanceState)
    {
        if (savedInstanceState != null)
            getInstanceStateFromBundle(savedInstanceState);
        else
        {
            Intent intent = getIntent();
            if (intent != null)
            {
                mAssetId = intent.getStringExtra(ASSET_ID);
                mMemberId = intent.getStringExtra(MEMBER_ID);
                mXToken = intent.getStringExtra(X_TOKEN);

                // Only for testing cloud albums, with li.yang
//                mAssetId = "1578095";
            }
        }

        mSelectedImages = new ArrayList<>();

        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.bringToFront();

        // Load the default album for the photopicker
        DefaultAlbumTask task = new DefaultAlbumTask(this);
        task.execute();
    }

    @Override
    protected void initListener()
    {
        super.initListener();
        mSourceButton.setOnClickListener(this);
        mFullBlurView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        int i = v.getId();
        if (i == R.id.photopicker_source_button)
        {
            if (mAlbumFragment != null)
                hideAlbumPickerFragment();
            else
                displayAlbumPickerFragment(mSelectedAlbum);

        }
        else if (i == R.id.photoactivity_fullblur_view)
        {// Do nothing

        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        putInstanceStateToBundle(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        setNextEnabled(false);
    }

    @Override
    public void onSelectionChanged(boolean selected, ArrayList<MPPickedPhotoModel> images)
    {
        setNextEnabled(selected);

        mSelectedImages = images;
    }

    @Override
    public void onAlbumSelectionChanged(Integer albumId, String albumName)
    {
        mSelectedAlbum = albumId;

        // Set the title; disable next; clear selections
        setTitleForNavbar(albumName);
        setNextEnabled(false);
        mSelectedImages.clear();

        hideAlbumPickerFragment();

        displayPickerFragment(mSelectedAlbum);
    }

    @Override
    public void onPlaceholderClicked()
    {
        if (mAlbumFragment != null)
            hideAlbumPickerFragment();
    }

    @Override
    public void onBackPressed()
    {
        if (mAlbumFragment != null)
        {
            hideAlbumPickerFragment();
            return;
        }

        super.onBackPressed();
    }

    @Override
    protected String getActivityTitle()
    {
        return getResources().getString(R.string.sd_imgs);
    }

    @Override
    protected String getRightNavButtonTitle()
    {
        return getResources().getString(R.string.determine);
    }

    @Override
    protected void rightNavButtonClicked(View view)
    {
        if (mSelectedImages != null && !mSelectedImages.isEmpty())
            fetchImageAndProceed();
    }

    private void getInstanceStateFromBundle(Bundle bundle)
    {
        mAssetId = bundle.getString(ASSET_ID);
        mMemberId = bundle.getString(MEMBER_ID);
        mXToken = bundle.getString(X_TOKEN);
    }

    private void putInstanceStateToBundle(Bundle bundle)
    {
        bundle.putString(ASSET_ID, mAssetId);
        bundle.putString(MEMBER_ID, mMemberId);
        bundle.putString(X_TOKEN, mXToken);
    }

    private void displayPickerFragment(Integer albumId)
    {
        MPPhotoPickerFragment pickerFragment = new MPPhotoPickerFragment();

        Bundle args = new Bundle();
        args.putInt(MPPhotoPickerFragment.ALBUM_ID, albumId);

        if (albumId.intValue() == MPPhotoAlbumUtility.kCloudAlbumId)
            args.putSerializable(MPPhotoPickerFragment.ALBUM_TYPE,
                    MPPhotoCollectionModel.AlbumType.CLOUD_ALBUM);
        else
            args.putSerializable(MPPhotoPickerFragment.ALBUM_TYPE,
                    MPPhotoCollectionModel.AlbumType.LOCAL_ALBUM);

        args.putString(MPPhotoAlbumFragment.ASSET_ID, mAssetId);
        args.putString(MPPhotoAlbumFragment.MEMBER_ID, mMemberId);
        args.putString(MPPhotoAlbumFragment.X_TOKEN, mXToken);

        pickerFragment.setArguments(args);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.photopicker_fragment_container, pickerFragment);
        transaction.commit();
    }

    private void displayAlbumPickerFragment(Integer albumId)
    {
        mBlurView.setVisibility(View.VISIBLE);
        mBlurView.bringToFront();
        mAlbumFragmentContainer.bringToFront();

        mAlbumFragment = new MPPhotoAlbumFragment();

        Bundle args = new Bundle();

        if (albumId != null)
            args.putInt(MPPhotoAlbumFragment.SELECTED_ALBUM, albumId);
        else
            args.putInt(MPPhotoAlbumFragment.SELECTED_ALBUM, 0);

        args.putString(MPPhotoAlbumFragment.ASSET_ID, mAssetId);
        args.putString(MPPhotoAlbumFragment.MEMBER_ID, mMemberId);
        args.putString(MPPhotoAlbumFragment.X_TOKEN, mXToken);

        mAlbumFragment.setArguments(args);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.photopicker_album_fragment_container, mAlbumFragment);
        transaction.commit();
    }

    private void hideAlbumPickerFragment()
    {
        mBlurView.setVisibility(View.INVISIBLE);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.remove(mAlbumFragment);
        transaction.commit();

        mAlbumFragment = null;
    }

    private void setNextEnabled(boolean enabled)
    {
        setNavButtonEnabled(ButtonType.RIGHT, enabled);

        int color;

        if (enabled)
            color = getResources().getColor(R.color.black);
        else
            color = getResources().getColor(R.color.gray);

        setTextColorForRightNavButton(color);
    }

    private void fetchImageAndProceed()
    {
        if (mSelectedImages == null || mSelectedImages.size() == 0)
            return;

        mFullBlurView.setVisibility(View.VISIBLE);
        mFullBlurView.bringToFront();
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.bringToFront();

        // Currently handling only the single selection case;
        // need to implement multiple image passing for the
        // multiple selection case
        if (mSelectedImages.get(0).photoSource == MPPickedPhotoModel.PhotoSource.CLOUD)
        {
            String fileName = MPFileUtility.getUniqueFileNameWithExtension("jpg");
            File imageFile = MPFileUtility.getFileFromName(this, fileName);
            String fileCloudUrl = mSelectedImages.get(0).fullImageUri;

            if (imageFile == null)
                return;

            final String fileLocalUrl = imageFile.getAbsolutePath();

            MPChatHttpManager.getInstance().downloadFileFromURL(fileCloudUrl,
                    fileLocalUrl, true, new MPChatHttpManager.ResponseHandler()
                    {
                        @Override
                        public void onSuccess(String response)
                        {
                            mProgressBar.setVisibility(View.INVISIBLE);
                            mFullBlurView.setVisibility(View.INVISIBLE);

                            Intent intent = new Intent();
                            intent.putExtra(ChatRoomActivity.CHOSEN_PHOTO, fileLocalUrl);
                            setResult(RESULT_OK, intent);
                            finish();
                        }

                        @Override
                        public void onFailure()
                        {
                            mProgressBar.setVisibility(View.INVISIBLE);
                            mFullBlurView.setVisibility(View.INVISIBLE);

                            Log.d("MPPhotoPickerActivity", "Download failed!");
                        }
                    });
        }
        else if (mSelectedImages.get(0).photoSource == MPPickedPhotoModel.PhotoSource.LOCAL)
        {
            String sourceFile = mSelectedImages.get(0).fullImageUri;

            String uniqueFileName = MPFileUtility.getUniqueFileNameWithExtension("jpg");
            File localFile = MPFileUtility.getFileFromName(this, uniqueFileName);

            if (localFile == null)
                return;

            String targetFile = localFile.getAbsolutePath();

            // Need to rotate clockwise, opposite of the photo orientation
            ImageProcessingUtil util = new ImageProcessingUtil();
            util.copyImageWithOrientation(sourceFile, targetFile,
                    mSelectedImages.get(0).orientation, new ImageProcessingUtil.ImageSaverHandler()
                    {
                        @Override
                        public void onSuccess(String outImagePath)
                        {
                            mProgressBar.setVisibility(View.INVISIBLE);
                            mFullBlurView.setVisibility(View.INVISIBLE);

                            if (outImagePath != null && !outImagePath.isEmpty())
                            {
                                Intent intent = new Intent();
                                intent.putExtra(ChatRoomActivity.CHOSEN_PHOTO, outImagePath);
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure()
                        {
                            mProgressBar.setVisibility(View.INVISIBLE);
                            mFullBlurView.setVisibility(View.INVISIBLE);

                            Log.d("MPPhotoPickerActivity", "Rotation failed!");
                        }
                    });
        }
    }

    private class DefaultAlbumTask extends AsyncTask<Void, Void, MPPhotoAlbumModel>
    {
        DefaultAlbumTask(Context context)
        {
            mContext = context;
        }

        @Override
        protected MPPhotoAlbumModel doInBackground(Void... voids)
        {
            if (mContext == null)
                return null;

            return MPPhotoAlbumUtility.getDefaultLocalAlbum(mContext);
        }

        @Override
        protected void onPostExecute(MPPhotoAlbumModel defaultAlbum)
        {
            mProgressBar.setVisibility(View.INVISIBLE);

            if (defaultAlbum != null)
            {
                mSelectedAlbum = defaultAlbum.albumId;
                setTitleForNavbar(defaultAlbum.albumName);
                displayPickerFragment(mSelectedAlbum);
            }
        }

        private Context mContext;
    }

    // Datamodel variables
    private String mAssetId;
    private String mMemberId;
    private String mXToken;
    private Integer mSelectedAlbum;
    private ArrayList<MPPickedPhotoModel> mSelectedImages;

    // View related variables
    private LinearLayout mSourceButton;
    private View mBlurView;
    private ProgressBar mProgressBar;
    private View mFullBlurView;
    private FrameLayout mAlbumFragmentContainer;

    private MPPhotoAlbumFragment mAlbumFragment;
}
