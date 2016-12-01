package com.autodesk.shejijia.shared.components.common.uielements.commoncomment.mpfolder;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.tools.photopicker.MPPhotoAlbumModel;
import com.autodesk.shejijia.shared.components.common.tools.photopicker.MPPhotoAlbumUtility;
import com.autodesk.shejijia.shared.components.common.tools.photopicker.MPPhotoCollectionModel;
import com.autodesk.shejijia.shared.components.common.tools.photopicker.MPPhotoModel;
import com.autodesk.shejijia.shared.components.common.tools.photopicker.MPPickedPhotoModel;
import com.autodesk.shejijia.shared.components.common.utility.SharedPreferencesUtils;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by t_panya on 16/11/23.
 */
@SuppressWarnings("unused")
public class SHPhotoPickFragment extends Fragment implements AbsListView.OnScrollListener,
                                                            AdapterView.OnItemClickListener,
                                                            View.OnClickListener,
                                                            AlbumListAdapter.AlbumListAdapterListener,
                                                            PhotoGridAdapter.PhotoGridAdapterListener,
                                                            DispatchBackPress{

    public interface PickPhotoFragmentListener{
        void onSelectedChange(boolean select, HashSet<MPPickedPhotoModel> selectedPhotoSet);
        void onSelectedDoneButton();
    }

    private static final String TAG = "SHPhotoPickFragment";
    public static final String ASSET_ID = "assetid";
    public static final String ALBUM_ID = "albumid";
    public static final String ALBUM_TYPE = "albumtype";
    public static final String MEMBER_ID = "memberid";
    public static final String X_TOKEN = "xtoken";

    public static final String RESULT = "result";
    public static final int RESULT_CODE = 0x00;
    public static final int REQ_CAMERA = 1000;

    public static final String CAMERA_PHOTO_PATH = "/enterprise/camera";//这是相机拍照完毕之后存储相片的路径

    private String mAssetId;
    private String mMemberId;
    private String mXToken;

    private static final int kPhotosLimit = 30;
    private static final int kPhotoFetchDelta = 10;
    private static final int kCloudAlbumRowPosition = 0;

    private Integer mAlbumId;
    private MPPhotoCollectionModel.AlbumType mGivenCollectionType;
    private MPPhotoCollectionModel mCollection;
    private boolean mShouldFetchPhotos = true;

    private GridView mGridView;
    private RelativeLayout mCloudPlaceholder;
    private RelativeLayout mAlbumShaderContainer;
    private LinearLayout mAlbumChangeButton;
    private ListView mAlbumListView;
    private TextView mAlbumNameText;
    private View mSpacerView;
    private TextView mButtonDone;
    private View mFullBlurView;
    private ProgressBar mProgressBar;
    private Integer mSelectedAlbum;
    private boolean isShowAlbum = false;

    private AlbumListAdapter mAlbumListAdapter;
    private PhotoGridAdapter mPhotoGridAdapter;

    private PickPhotoFragmentListener mListener;
    private String cameraPhotoUrl;

    private HashSet<MPPickedPhotoModel> mSelectedPhotoSet;
    private ArrayList<MPPickedPhotoModel> mPhotoList = new ArrayList<>();
    private ArrayList<MPPhotoAlbumModel> mAlbums;
    private File mTmpFile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            getInstanceStateFromBundle(savedInstanceState);
        } else if (getArguments() != null) {
            mAssetId = getArguments().getString(ASSET_ID);
            mAlbumId = getArguments().getInt(ALBUM_ID);
            mMemberId = getArguments().getString(MEMBER_ID);
            mXToken = getArguments().getString(X_TOKEN);
            //初次进来的album type
//            mGivenCollectionType = (MPPhotoCollectionModel.AlbumType) getArguments().get(ALBUM_TYPE);
            mGivenCollectionType = MPPhotoCollectionModel.AlbumType.LOCAL_ALBUM;
            assert (mAlbumId != null);
        }
        mAlbums = new ArrayList<>();
        mSelectedPhotoSet = new HashSet<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shphotopicker, container, false);

        mGridView = (GridView) view.findViewById(R.id.gv_photo_display);
        mCloudPlaceholder = (RelativeLayout) view.findViewById(R.id.rl_photopicker_cloud_placeholder);
        mAlbumChangeButton = (LinearLayout) view.findViewById(R.id.ll_photopicker_button);
        mAlbumChangeButton.setOnClickListener(this);
        mAlbumListView = (ListView) view.findViewById(R.id.lv_photo_album);
        mAlbumNameText = (TextView) view.findViewById(R.id.tv_photopicker_album);
        mAlbumShaderContainer = (RelativeLayout) view.findViewById(R.id.rl_album_shader_container);
        mAlbumShaderContainer.setOnClickListener(this);
        mSpacerView = view.findViewById(R.id.view_photoalbum_sapcer);
        mButtonDone = (TextView) view.findViewById(R.id.tv_photopicker_done);
        mButtonDone.setOnClickListener(this);
        mFullBlurView = view.findViewById(R.id.view_photoactivity_fullblur);
        mProgressBar = (ProgressBar) view.findViewById(R.id.pb_progress_show);
//        mSpacerView.setOnClickListener(this);
        mSelectedAlbum = mAlbumId;
        Log.d(TAG, "onCreateView: mSelectedAlbum == " + mSelectedAlbum);
        init();
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof PickPhotoFragmentListener){
            mListener = (PickPhotoFragmentListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement PhotoPickerFragmentListener");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        putInstanceStateToBundle(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCollection = null;
        mSelectedPhotoSet = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (mGivenCollectionType != MPPhotoCollectionModel.AlbumType.LOCAL_ALBUM){
            return;
        }

        int lastVisiblePosition = mGridView.getLastVisiblePosition();

        int difference = (totalItemCount - 1) - lastVisiblePosition;

        if ((difference > kPhotoFetchDelta || difference == 0) ||
                (!mShouldFetchPhotos || lastVisiblePosition < 0)){
            return;
        }

        getLocalPhotoChunk(totalItemCount);
    }

    private void init() {
        switch (mGivenCollectionType) {
            case LOCAL_ALBUM:
                initLocalCollection();
                break;
            case CLOUD_ALBUM:
                initCloudCollection();
                break;
        }
        initAlbumLocalList();
        initCloudAlbum();
    }

    private void initLocalCollection() {
        Log.d(TAG, "initLocalCollection: begin");
        if (mGivenCollectionType != MPPhotoCollectionModel.AlbumType.LOCAL_ALBUM){
            return;
        }

        if (mSelectedAlbum != null){
            getLocalPhotoChunk(0);
        }
    }

    private void initCloudCollection() {
        if (mGivenCollectionType != MPPhotoCollectionModel.AlbumType.CLOUD_ALBUM){
            return;
        }

        if (mAssetId != null && mMemberId != null && mXToken != null) {
            MPPhotoAlbumUtility.getCloudCollectionAsync(getActivity(), mXToken, mAssetId,
                    mMemberId, new MPPhotoAlbumUtility.CollectionFetchListener() {
                        @Override
                        public void onError() {
                            Log.d(TAG, "Error while fetching cloud data");

                            // TODO: Handle the condition when the network request fails
                        }

                        @Override
                        public void onSuccess(MPPhotoCollectionModel collection) {
                            mCollection = collection;
                            initCollectionView();
                        }
                    });
        }
    }

    private void initAlbumLocalList(){
        new MultiTask<Void, Void, ArrayList<MPPhotoAlbumModel>>() {

            @Override
            protected ArrayList<MPPhotoAlbumModel> doInBackground(Void... params) {
//                initLocalAlbums();
                return MPPhotoAlbumUtility.getAllLocalAlbums(getActivity());
            }

            @Override
            protected void onPostExecute(ArrayList<MPPhotoAlbumModel> albums) {
                if(albums == null){
                    return;
                }
                for (MPPhotoAlbumModel album : albums) {
                    album.isSelected = (album.albumId.intValue() == mSelectedAlbum.intValue());
                }
                mAlbums.addAll(albums);
                reloadAlbumView();
            }
        }.executeMultiTask();

    }

    private void initCloudAlbum() {
        MPPhotoAlbumModel cloudAlbum = new MPPhotoAlbumModel();
        cloudAlbum.albumType = MPPhotoAlbumModel.eAlbumType.CLOUD_ALBUM;
        cloudAlbum.albumName = getActivity().getString(R.string.photopicker_cloud_album);
        cloudAlbum.albumId = MPPhotoAlbumUtility.kCloudAlbumId;
        cloudAlbum.isSelected = false;
        cloudAlbum.thumbnailOrientation = 0;
        cloudAlbum.albumSize = -1; // Set a sentinel value

        if (mMemberId != null && mXToken != null && mAssetId != null) {
            if (mAlbums != null) {
                mAlbums.add(kCloudAlbumRowPosition, cloudAlbum);
                reloadAlbumView();
            }

            MPPhotoAlbumUtility.getCloudAlbumAsync(getActivity(), mXToken, mAssetId, mMemberId,
                    new MPPhotoAlbumUtility.AlbumFetchListener() {
                        @Override
                        public void onError() {
                            Log.d(TAG, "Error while fetching cloud data");
                            if (mAlbums != null && mAlbums.size() != 0) {
                                MPPhotoAlbumModel cloudAlbum = mAlbums.get(kCloudAlbumRowPosition);

                                // Confirm that we are picking the right row to delete
                                if (cloudAlbum.albumType == MPPhotoAlbumModel.eAlbumType.CLOUD_ALBUM) {
                                    mAlbums.remove(kCloudAlbumRowPosition);
                                    reloadAlbumView();
                                }
                            }
                        }

                        @Override
                        public void onSuccess(MPPhotoAlbumModel album) {
                            if (mAlbums == null){
                                return;
                            }

                            MPPhotoAlbumModel albumToReplace = mAlbums.get(kCloudAlbumRowPosition);

                            if (albumToReplace.albumType == MPPhotoAlbumModel.eAlbumType.CLOUD_ALBUM) {
                                if (album.albumId == mSelectedAlbum.intValue())
                                    album.isSelected = true;

                                mAlbums.set(kCloudAlbumRowPosition, album);

                                reloadAlbumView();
                            }
                        }
                    });
        }
    }

    private void getLocalPhotoChunk(final int offset) {
        mShouldFetchPhotos = false;
        new MultiTask<Void, Void, MPPhotoCollectionModel>() {
            @Override
            protected MPPhotoCollectionModel doInBackground(Void... params) {
                return MPPhotoAlbumUtility.getPartialLocalPhotoCollection(getActivity(),
                        mSelectedAlbum, offset, kPhotosLimit);
            }

            @Override
            protected void onPostExecute(MPPhotoCollectionModel photoCollectionModel) {
                if (photoCollectionModel == null) {
                    // All photos fetched, job done
                    mShouldFetchPhotos = false;
                    return;
                }

                // Allow firing requests again
                mShouldFetchPhotos = true;

                if (offset == 0){
                    mCollection = photoCollectionModel;
                } else {
                    mCollection.photos.addAll(photoCollectionModel.photos);
                }

                reloadCollectionView();
            }
        }.executeMultiTask();
    }

    private void reloadCollectionView() {
        if (getActivity() == null){
            return;
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mPhotoGridAdapter != null){
                    mPhotoGridAdapter.notifyDataSetChanged();
                }
                else{
                    initCollectionView();
                }

            }
        });
    }

    private void initCollectionView() {
        if (mCollection == null){
            return;
        }

        // Show the placeholder when there are no images in the cloud album
        if (mCollection.albumType == MPPhotoCollectionModel.AlbumType.CLOUD_ALBUM &&
                mCollection.photos.size() == 0) {
            mGridView.setVisibility(View.INVISIBLE);
            mCloudPlaceholder.setVisibility(View.VISIBLE);
            return;
        } else {
            mGridView.setVisibility(View.VISIBLE);
            mCloudPlaceholder.setVisibility(View.INVISIBLE);
        }

        mPhotoGridAdapter = new PhotoGridAdapter(getActivity().getBaseContext(),this);
        mGridView.setAdapter(mPhotoGridAdapter);

        mGridView.setOnItemClickListener(this);
        mGridView.setOnScrollListener(this);
    }

    private void reloadAlbumView(){
        if (getActivity() == null){
            return;
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run()
            {
                if (mAlbumListAdapter != null) {
                    mAlbumListAdapter.notifyDataSetChanged();
                } else {
                    initAlbumView();
                }
            }
        });
    }

    private void initAlbumView(){
        if (mAlbumListView == null){
            return;
        }

        mAlbumListAdapter = new AlbumListAdapter(getActivity().getBaseContext(),this);
        final AlbumListAdapter adapter = mAlbumListAdapter;
        mAlbumListView.setAdapter(mAlbumListAdapter);

        mAlbumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mAlbums != null && mAlbums.size() > 0) {

                    MPPhotoAlbumModel album = mAlbums.get(position);

                    // Do not allow selection of invalid/improperly initialized albums
                    if (album.albumSize < 0){
                        return;
                    }

                    for (MPPhotoAlbumModel al : mAlbums) {
                        if (al.isSelected && al != album)
                            al.isSelected = false;
                    }

                    album.isSelected = true;

                    mSelectedAlbum = album.albumId;

                    //change album
                    onAlbumSelectionChanged(album.albumName);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // The selection state has changed, reload
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }

    private void getInstanceStateFromBundle(Bundle bundle) {
        mAssetId = bundle.getString(ASSET_ID);
        mAlbumId = bundle.getInt(ALBUM_ID);
        mMemberId = bundle.getString(MEMBER_ID);
        mXToken = bundle.getString(X_TOKEN);
        mGivenCollectionType = (MPPhotoCollectionModel.AlbumType) bundle.getSerializable(ALBUM_TYPE);
    }

    private void putInstanceStateToBundle(Bundle bundle) {
        bundle.putString(ASSET_ID, mAssetId);
        bundle.putInt(ALBUM_ID, mAlbumId);
        bundle.putString(MEMBER_ID, mMemberId);
        bundle.putString(X_TOKEN, mXToken);

        bundle.putSerializable(ALBUM_TYPE, mGivenCollectionType);
    }

    private void onAlbumSelectionChanged(String albumName){
        mAlbumNameText.setText(albumName);
        mAlbumShaderContainer.setVisibility(View.GONE);
        mSpacerView.setVisibility(View.GONE);
        isShowAlbum = !isShowAlbum;
        getLocalPhotoChunk(0);

    }

    private void fetchImageAndProceed(){
        if(mSelectedPhotoSet == null || mSelectedPhotoSet.size() == 0){
            return;
        }
        mFullBlurView.setVisibility(View.VISIBLE);
        mFullBlurView.bringToFront();
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.bringToFront();

        Iterator iterator = mSelectedPhotoSet.iterator();
        while (iterator.hasNext()){
            MPPickedPhotoModel photo = (MPPickedPhotoModel) iterator.next();
            if(photo.photoSource == MPPickedPhotoModel.PhotoSource.CLOUD){      //云端

            }else {

            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data == null) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
//            case SelectPhotoAdapter.REQ_CAMARA: {//1000如果是相机发来的
//                Log.i("Alex", "现在是相机拍照完毕");
//                if (Activity.RESULT_OK == resultCode) {//系统默认值
//                    String cameraPhotoUrl = "file.jpg";
//                    String cameraUrl = SharedPreferencesUtils.readString("Camera");
//
//                    File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + SelectPhotoAdapter.CAMERA_PHOTO_PATH);
//                    if (!dir.exists()) dir.mkdirs();
//                    File file = new File(dir, cameraPhotoUrl);//这个文件指针指向拍好的图片文件
//                    final String imageFilePath = file.getAbsolutePath();
//                    Log.i("Alex", "拍摄图片暂存到了" + imageFilePath + "  角度是" + AlxImageLoader.readPictureDegree(imageFilePath));
//                    //把图片压缩成指定宽高并且保存到本地
//                    file = new File(imageFilePath);
//                    String albumUrl = null;
//                    if (!file.exists()) break;
//                    Log.i("Alex", "准备存储到相册");
//                    try {
//                        ContentResolver cr = SelectPhotoActivity.this.getContentResolver();
//                        //在往相册存储的时候返回url是DCIM的url，不是原来的了，而且exif信息也全都没了
//                        // /storage/sdcard0/Alex/camera/1461321361499.jpg
//                        // /storage/sdcard0/DCIM/Camera/1461321370065.jpg
//                        //下面这句是把相机返回的文件拷贝到系统相册里面去,并且生产缩略图存在相册里，然后发送广播更新图片list
//                        albumUrl = AlxBitmapUtils.insertImage(this, cr, file, true);//返回值为要发送图片的url
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    // 提交数据，和选择图片用的同一个ArrayList
//                    SelectPhotoAdapter.SelectPhotoEntity photoEntity = new SelectPhotoAdapter.SelectPhotoEntity();
//                    //因为存储到相册之后exif全都没了，所以应该传源文件的路径
//                    photoEntity.url = albumUrl;
//                    if(selectedPhotoList == null)selectedPhotoList = new ArrayList<>(1);
//                    selectedPhotoList.add(photoEntity);
//                    Intent intent = new Intent();
//                    intent.putExtra("selectPhotos", selectedPhotoList);//把获取到图片交给别的Activity
//                    intent.putExtra("isFromCamera", true);
//                    setResult(SELECT_PHOTO_OK, intent);
//                    finish();
//                    break;
//                }
//            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0){
            cameraPhotoUrl = System.currentTimeMillis() + "enterprise.jpg";//相机拍完之后的命名
            File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + CAMERA_PHOTO_PATH);//设置相机拍摄完毕后放置的目录
            if (!dir.exists()) dir.mkdirs();
            try {
                SharedPreferencesUtils.writeString("Camera",cameraPhotoUrl);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            mTmpFile = new File(dir, cameraPhotoUrl);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));//在这里告诉相机你应该把拍好的照片放在什么位置
            getActivity().startActivityForResult(intent, REQ_CAMERA);
        } else {
            if(mCollection != null && mCollection.photos != null){
                MPPhotoModel photo = mCollection.photos.get(position - 1);
                if(photo == null){
                    return;
                }

                photo.isSelected = !photo.isSelected;
                MPPickedPhotoModel pickedPhoto = new MPPickedPhotoModel();
                pickedPhoto.fullImageUri = photo.fullImageUri;
                pickedPhoto.thumbnailUri = photo.thumbnailUri;
                pickedPhoto.orientation = photo.orientation;
                if(photo.isSelected){
                    if(mGivenCollectionType == MPPhotoCollectionModel.AlbumType.LOCAL_ALBUM){
                        pickedPhoto.photoSource = MPPickedPhotoModel.PhotoSource.LOCAL;
                    }else {
                        pickedPhoto.photoSource = MPPickedPhotoModel.PhotoSource.CLOUD;
                    }
                    mSelectedPhotoSet.add(pickedPhoto);
//                Log.d(TAG, "onItemClick: SIZE = " + mSelectedPhotoSet.size());
                    if(mSelectedPhotoSet.size() > 9){
                        mSelectedPhotoSet.remove(pickedPhoto);
                        photo.isSelected = !photo.isSelected;
//                    Log.d(TAG, "onItemClick: SIZE = " + mSelectedPhotoSet.size());
                        ToastUtils.showShort(getActivity(),"最多选择9张图片");
                        return;
                    }
                } else {
                    if(mSelectedPhotoSet.contains(pickedPhoto)){
                        mSelectedPhotoSet.remove(pickedPhoto);
//                    Log.d(TAG, "onItemClick: SIZE = " + mSelectedPhotoSet.size());
                    }
                }
                if(mSelectedPhotoSet == null || mSelectedPhotoSet.size() == 0){
                    mListener.onSelectedChange(false,null);
                } else {
                    mListener.onSelectedChange(true,mSelectedPhotoSet);
                }
                mPhotoGridAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.rl_album_shader_container){
            if(isShowAlbum){
                Log.d(TAG, "onClick: rl_album_shader_container");
                mAlbumShaderContainer.setVisibility(View.GONE);
                mSpacerView.setVisibility(View.GONE);
                isShowAlbum = !isShowAlbum;
            }
        } else if (v.getId() == R.id.ll_photopicker_button){
            if(isShowAlbum){
                mAlbumShaderContainer.setVisibility(View.GONE);
                mSpacerView.setVisibility(View.GONE);
                isShowAlbum = !isShowAlbum;
            } else {
                mAlbumShaderContainer.setVisibility(View.VISIBLE);
                mSpacerView.setVisibility(View.VISIBLE);
                isShowAlbum = !isShowAlbum;
            }
        } else if(v.getId() == R.id.tv_photopicker_done){
            if(isShowAlbum){
                Log.d(TAG, "onClick: rl_album_shader_container");
                mAlbumShaderContainer.setVisibility(View.GONE);
                mSpacerView.setVisibility(View.GONE);
                isShowAlbum = !isShowAlbum;
            }
//            mListener.onSelectedDoneButton();
            if(mSelectedPhotoSet != null && mSelectedPhotoSet.size() != 0){
                Iterator<MPPickedPhotoModel> iterator = mSelectedPhotoSet.iterator();
                while (iterator.hasNext()){
                    MPPickedPhotoModel photo = iterator.next();
                    mPhotoList.add(photo);
                }
            }
            Intent intent = new Intent();
            intent.putExtra(RESULT,mPhotoList);

            getActivity().setResult(RESULT_CODE,intent);
            getActivity().finish();
        }
    }


    ////// album list view interface//////////
    @Override
    public int getAlbumCount() {
        int count = 0;

        if (mAlbums != null){
            count = mAlbums.size();
        }

        return count;
    }

    @Override
    public MPPhotoAlbumModel getAlbum(int position) {
        if (mAlbums != null && mAlbums.size() != 0){
            return mAlbums.get(position);
        }
        return null;
    }

    @Override
    public boolean getAlbumSelectedState(int position) {
        return mAlbums.get(position).isSelected;
    }

    @Override
    public boolean isRowVisible(int position) {
        if (mAlbumListView == null){
            return false;
        }

        int firstVisiblePosition = mAlbumListView.getFirstVisiblePosition();
        int lastVisiblePosition = mAlbumListView.getLastVisiblePosition();

        return position >= firstVisiblePosition && position <= lastVisiblePosition;
    }

    /////photo grid view interface /////////
    @Override
    public int getPhotoCount() {
        return mCollection.photos.size() + 1;
    }

    @Override
    public MPPhotoModel getPhotoModel(int position) {
        return mCollection.photos.get(position);
    }

    @Override
    public boolean getSelectedState(int position) {
//        Log.d(TAG, "getSelectedState: begin");
        if(mSelectedPhotoSet != null){
//            Log.d(TAG, "getSelectedState: begin !== null");
            Iterator iterator = mSelectedPhotoSet.iterator();
            while (iterator.hasNext()){
                MPPickedPhotoModel pickerPhoto = (MPPickedPhotoModel) iterator.next();
                if((mCollection.photos.get(position).fullImageUri.equals(pickerPhoto.fullImageUri))){
//                    Log.d(TAG, "getSelectedState: begin true");
                    mCollection.photos.get(position).isSelected = true;
                    return true;
                }
            }
        }

        return mCollection.photos.get(position).isSelected;
    }

    @Override
    public boolean isCellVisible(int position) {
        boolean isVisible = false;

        if (mGridView == null){
            return false;
        }

        int firstVisiblePosition = mGridView.getFirstVisiblePosition();
        int lastVisiblePosition = mGridView.getLastVisiblePosition();

        if (position >= firstVisiblePosition && position <= lastVisiblePosition){
            isVisible = true;
        }

        return isVisible;
    }

    @Override
    public boolean onActivityBackPress() {
        if(isShowAlbum){
            mAlbumShaderContainer.setVisibility(View.GONE);
            mSpacerView.setVisibility(View.GONE);
            isShowAlbum = !isShowAlbum;
            return true;
        }
        return false;
    }
}
