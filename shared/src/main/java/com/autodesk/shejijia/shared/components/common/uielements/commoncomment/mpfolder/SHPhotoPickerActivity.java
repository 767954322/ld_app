package com.autodesk.shejijia.shared.components.common.uielements.commoncomment.mpfolder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.tools.photopicker.MPPhotoAlbumModel;
import com.autodesk.shejijia.shared.components.common.tools.photopicker.MPPhotoAlbumUtility;
import com.autodesk.shejijia.shared.components.common.tools.photopicker.MPPhotoCollectionModel;
import com.autodesk.shejijia.shared.components.common.tools.photopicker.MPPickedPhotoModel;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class SHPhotoPickerActivity extends NavigationBarActivity implements SHPhotoPickFragment.PickPhotoFragmentListener{
    public static final String BUNDLE_DATA = "bundle_data";
    public static final String ASSET_ID = "assetid";
    public static final String MEMBER_ID = "memberid";
    public static final String X_TOKEN = "xtoken";
    public static final String ALBUM_TYPE = "albumtype";
    public static final String RESULT = "result";
    public static final int RESULT_CODE = 0x00;

    // Datamodel variable
    private String mAssetId;
    private String mMemberId;
    private String mXToken;
    private MPPhotoCollectionModel.AlbumType mGivenCollectionType;
    private Integer mSelectedAlbum;
    private HashSet<MPPickedPhotoModel> mSelectedPhotoSet;
    private ArrayList<MPPickedPhotoModel> mPhotoList;

    // View related variables
    private ProgressBar mProgressBar;
    private Context mContext;
    private TextView mTextDone;

    private DispatchBackPress mDispatch;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_shphotopicker;
    }

    @Override
    protected void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mProgressBar = (ProgressBar) findViewById(R.id.pb_photo_picker_progress);
//        mTextDone = (TextView) findViewById(R.id.tv_photopicker_done);
//        mTextDone.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
//        if (savedInstanceState != null)
//            getInstanceStateFromBundle(savedInstanceState);
//        else {
//            Intent intent = getIntent();
//            if (intent != null) {
//                Bundle bundle = intent.getBundleExtra("BUNDLE_DATA");
//                mAssetId  = bundle.getString(ASSET_ID);
//                mMemberId = bundle.getString(MEMBER_ID);
//                mXToken = bundle.getString(X_TOKEN);
//                mGivenCollectionType = (MPPhotoCollectionModel.AlbumType) bundle.get(ALBUM_TYPE);
//                // Only for testing cloud albums, with li.yang
////                mAssetId = "1578095";
//
//                if (!StringUtils.isValidString(mAssetId))
//                    mAssetId = null;
//            }
//        }
        mContext = getBaseContext();
        mPhotoList = new ArrayList<>();
//        mSelectedImages = new HashMap<>();
//
//        mProgressBar.setVisibility(View.VISIBLE);
//        mProgressBar.bringToFront();
//
//        // Load the default album for the photopicker
//        if(mGivenCollectionType == MPPhotoCollectionModel.AlbumType.CLOUD_ALBUM){
//            displayPickerFragment(MPPhotoAlbumUtility.kCloudAlbumId);
//        } else {
            executeAlbumTask();
//        }
//        DefaultAlbumTask task = new DefaultAlbumTask();
//        task.execute();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        putInstanceStateToBundle(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
//        mSelectedImages.clear();
        super.onDestroy();
    }

    private void getInstanceStateFromBundle(Bundle bundle) {
        mAssetId = bundle.getString(ASSET_ID);
        mMemberId = bundle.getString(MEMBER_ID);
        mXToken = bundle.getString(X_TOKEN);
    }

    private void putInstanceStateToBundle(Bundle bundle) {
        bundle.putString(ASSET_ID, mAssetId);
        bundle.putString(MEMBER_ID, mMemberId);
        bundle.putString(X_TOKEN, mXToken);
    }

    private void displayPickerFragment(Integer albumId) {
        SHPhotoPickFragment pickerFragment = new SHPhotoPickFragment();
        mDispatch = pickerFragment;

        Bundle args = new Bundle();
        Log.d(TAG, "displayPickerFragment: albumId == " + albumId);
        args.putInt(SHPhotoPickFragment.ALBUM_ID, albumId);
//
//        if (albumId.intValue() == MPPhotoAlbumUtility.kCloudAlbumId)
//            args.putSerializable(MPPhotoPickerFragment.ALBUM_TYPE,
//                    MPPhotoCollectionModel.AlbumType.CLOUD_ALBUM);
//        else
//            args.putSerializable(MPPhotoPickerFragment.ALBUM_TYPE,
//                    MPPhotoCollectionModel.AlbumType.LOCAL_ALBUM);

        args.putSerializable(SHPhotoPickFragment.ALBUM_TYPE,mGivenCollectionType);
        args.putString(SHPhotoPickFragment.ASSET_ID, mAssetId);
        args.putString(SHPhotoPickFragment.MEMBER_ID, mMemberId);
        args.putString(SHPhotoPickFragment.X_TOKEN, mXToken);

        pickerFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_photopicker_container, pickerFragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if(mDispatch.onActivityBackPress()){
            // do nothing right now
        } else {
            super.onBackPressed();
        }
    }

//    private void fetchImageAndProceed() {
//        if (mSelectedPhotoSet == null || mSelectedPhotoSet.size() == 0)
//            return;
//        mProgressBar.setVisibility(View.VISIBLE);
//        mProgressBar.bringToFront();
//
//        // Currently handling only the single selection case;
//        // need to implement multiple image passing for the
//        // multiple selection case
//        if (mSelectedImages.get(0).photoSource == MPPickedPhotoModel.PhotoSource.CLOUD) {
//            String fileName = MPFileUtility.getUniqueFileNameWithExtension("jpg");
//            File imageFile = MPFileUtility.getFileFromName(this, fileName);
//            String fileCloudUrl = mSelectedImages.get(0).fullImageUri;
//
//            if (imageFile == null)
//                return;
//
//            final String fileLocalUrl = imageFile.getAbsolutePath();
//
//            MPChatHttpManager.getInstance().downloadFileFromURL(fileCloudUrl,
//                    fileLocalUrl, true, new MPChatHttpManager.ResponseHandler() {
//                        @Override
//                        public void onSuccess(String response) {
//                            mProgressBar.setVisibility(View.INVISIBLE);
//
//                            Intent intent = new Intent();
//                            intent.putExtra(ChatRoomActivity.CHOSEN_PHOTO, fileLocalUrl);
//                            setResult(RESULT_OK, intent);
//                            finish();
//                        }
//
//                        @Override
//                        public void onFailure() {
//                            mProgressBar.setVisibility(View.INVISIBLE);
//
//                            Log.d("MPPhotoPickerActivity", "Download failed!");
//                        }
//                    });
//        } else if (mSelectedImages.get(0).photoSource == MPPickedPhotoModel.PhotoSource.LOCAL) {
//            String sourceFile = mSelectedImages.get(0).fullImageUri;
//
//            String uniqueFileName = MPFileUtility.getUniqueFileNameWithExtension("jpg");
//            File localFile = MPFileUtility.getFileFromName(this, uniqueFileName);
//
//            if (localFile == null)
//                return;
//
//            String targetFile = localFile.getAbsolutePath();
//
//            // Need to rotate clockwise, opposite of the photo orientation
//            ImageProcessingUtil util = new ImageProcessingUtil();
//            util.copyImageWithOrientation(sourceFile, targetFile,
//                    mSelectedImages.get(0).orientation, new ImageProcessingUtil.ImageSaverHandler() {
//                        @Override
//                        public void onSuccess(String outImagePath) {
//                            mProgressBar.setVisibility(View.INVISIBLE);
//
//                            if (outImagePath != null && !outImagePath.isEmpty()) {
//                                Intent intent = new Intent();
//                                intent.putExtra(ChatRoomActivity.CHOSEN_PHOTO, outImagePath);
//                                setResult(RESULT_OK, intent);
//                                finish();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure() {
//                            mProgressBar.setVisibility(View.INVISIBLE);
//
//                            Log.d("MPPhotoPickerActivity", "Rotation failed!");
//                        }
//                    });
//        }
//    }

    private void executeAlbumTask(){
        new MultiTask<Void, Void, MPPhotoAlbumModel>(){

            @Override
            protected MPPhotoAlbumModel doInBackground(Void... params) {
                if (mContext == null){
                    return null;
                }
                return MPPhotoAlbumUtility.getDefaultLocalAlbum(mContext);
            }

            @Override
            protected void onPostExecute(MPPhotoAlbumModel defaultAlbum) {
                mProgressBar.setVisibility(View.INVISIBLE);

                if (defaultAlbum != null) {
                    mSelectedAlbum = defaultAlbum.albumId;
                    Log.d(TAG, "onPostExecute: mSelectedAlbum == " + mSelectedAlbum);
                    displayPickerFragment(mSelectedAlbum);
                }
            }

        }.executeMultiTask();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSelectedChange(boolean select, HashSet<MPPickedPhotoModel> selectedPhotoSet) {
        if(selectedPhotoSet == null || selectedPhotoSet.size() == 0){
            // TODO: 16/11/26 set button enable 
            return;
        } else {
            mSelectedPhotoSet = selectedPhotoSet;
        }
    }

    @Override
    public void onSelectedDoneButton() {
        if(mSelectedPhotoSet != null && mSelectedPhotoSet.size() != 0){
            Iterator<MPPickedPhotoModel> iterator = mSelectedPhotoSet.iterator();
            while (iterator.hasNext()){
                MPPickedPhotoModel photo = iterator.next();
                mPhotoList.add(photo);
            }
        }
        Intent intent = new Intent();
        intent.putExtra(RESULT,mPhotoList);
        setResult(RESULT_CODE,intent);
        finish();
    }
}
