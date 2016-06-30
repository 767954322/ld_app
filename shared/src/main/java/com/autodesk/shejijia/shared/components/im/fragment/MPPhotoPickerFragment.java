package com.autodesk.shejijia.shared.components.im.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.tools.photopicker.MPPhotoCollectionModel;
import com.autodesk.shejijia.shared.components.common.tools.photopicker.MPPhotoModel;
import com.autodesk.shejijia.shared.components.common.tools.photopicker.MPPickedPhotoModel;
import com.autodesk.shejijia.shared.components.common.tools.photopicker.MPPhotoPickerAdapter;
import com.autodesk.shejijia.shared.components.common.tools.photopicker.MPPhotoAlbumUtility;

import java.util.ArrayList;

public class MPPhotoPickerFragment extends Fragment implements
        MPPhotoPickerAdapter.PhotoPickerAdapterListener, AdapterView.OnItemClickListener,
        AbsListView.OnScrollListener
{
    public static final String ASSET_ID = "assetid";
    public static final String ALBUM_ID = "albumid";
    public static final String ALBUM_TYPE = "albumtype";
    public static final String MEMBER_ID = "memberid";
    public static final String X_TOKEN = "xtoken";


    public interface PhotoPickerFragmentListener
    {
        void onSelectionChanged(boolean selected, ArrayList<MPPickedPhotoModel> images);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null)
            getInstanceStateFromBundle(savedInstanceState);
        else if (getArguments() != null)
        {
            mAssetId = getArguments().getString(ASSET_ID);
            mAlbumId = getArguments().getInt(ALBUM_ID);
            mMemberId = getArguments().getString(MEMBER_ID);
            mXToken = getArguments().getString(X_TOKEN);
            mGivenCollectionType = (MPPhotoCollectionModel.AlbumType)getArguments().get(ALBUM_TYPE);

            assert (mAlbumId != null);

            mPendingTasks = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo_picker, container, false);

        mGridView = (GridView)view.findViewById(R.id.photopicker_grid);
        mBusyIndicator = (ProgressBar)view.findViewById(R.id.photopicker_progressbar);
        mCloudPlaceholder = (RelativeLayout)view.findViewById(R.id.photopicker_cloud_placeholder);

        return view;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        if (activity instanceof PhotoPickerFragmentListener)
            mListener = (PhotoPickerFragmentListener) activity;
        else
            throw new RuntimeException(activity.toString()
                    + " must implement PhotoPickerFragmentListener");
    }

    @Override
    public void onResume()
    {
        super.onResume();

        init();
    }

    @Override
    public void onPause()
    {
        super.onPause();

        // Cancel pending tasks before exiting
        cancelPendingTasks();

        mAdapter = null;
        mCollection = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        putInstanceStateToBundle(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    @Override
    public int getPhotoCount()
    {
        int photoCount = 0;

        if (mCollection != null && mCollection.photos != null)
            photoCount = mCollection.photos.size();

        return photoCount;
    }

    @Override
    public MPPhotoModel getPhotoModel(int position)
    {
        MPPhotoModel photoModel = null;

        if (mCollection != null && mCollection.photos != null)
            photoModel = mCollection.photos.get(position);

        return photoModel;
    }

    @Override
    public boolean getSelectedState(int position)
    {
        boolean isSelected = false;

        if (mCollection != null && mCollection.photos != null)
        {
            MPPhotoModel photo = mCollection.photos.get(position);
            if (photo != null)
                isSelected = photo.isSelected;
        }

        return isSelected;
    }

    @Override
    public boolean isCellVisible(int position)
    {
        boolean isVisible = false;

        if (mGridView == null)
            return false;

        int firstVisiblePosition = mGridView.getFirstVisiblePosition();
        int lastVisiblePosition = mGridView.getLastVisiblePosition();

        if (position >= firstVisiblePosition && position <= lastVisiblePosition)
            isVisible = true;

        return isVisible;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        if (mCollection != null && mCollection.photos != null)
        {
            ArrayList<MPPickedPhotoModel> selectedImages = new ArrayList<MPPickedPhotoModel>();
            for (MPPhotoModel ph : mCollection.photos)
            {
                // Deselect previous selections if multiple selections not allowed
                if (ph.isSelected && ph != mCollection.photos.get(position))
                {
                    if (!kMultipleSelectionAllowed)
                        ph.isSelected = false;
                    else
                    {
                        MPPickedPhotoModel pickedPhoto = new MPPickedPhotoModel();
                        pickedPhoto.fullImageUri = ph.fullImageUri;
                        pickedPhoto.orientation = ph.orientation;
                        selectedImages.add(pickedPhoto);
                    }
                }
            }

            MPPhotoModel photo = mCollection.photos.get(position);

            if (photo == null)
                return;

            photo.isSelected = !photo.isSelected;

            if (photo.isSelected)
            {
                MPPickedPhotoModel pickedPhoto = new MPPickedPhotoModel();
                pickedPhoto.fullImageUri = photo.fullImageUri;
                pickedPhoto.orientation = photo.orientation;

                if (mGivenCollectionType == MPPhotoCollectionModel.AlbumType.LOCAL_ALBUM)
                    pickedPhoto.photoSource = MPPickedPhotoModel.PhotoSource.LOCAL;
                else
                    pickedPhoto.photoSource = MPPickedPhotoModel.PhotoSource.CLOUD;

                selectedImages.add(pickedPhoto);
            }

            // Inform the activity
            if (!kMultipleSelectionAllowed)
                mListener.onSelectionChanged(photo.isSelected, selectedImages);
            else
            {
                if (selectedImages.size() > 0)
                    mListener.onSelectionChanged(true, selectedImages);
                else
                    mListener.onSelectionChanged(false, null);
            }

            // The selection state has changed, reload
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState)
    {
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount)
    {
        if (mGivenCollectionType != MPPhotoCollectionModel.AlbumType.LOCAL_ALBUM)
            return;

        int lastVisiblePosition = mGridView.getLastVisiblePosition();

        int difference = (totalItemCount - 1) - lastVisiblePosition;

        if ((difference > kPhotoFetchDelta || difference == 0) ||
                (!mShouldFetchPhotos || lastVisiblePosition < 0))
            return;

        fetchLocalPhotoChunk(totalItemCount);
    }


    private void init()
    {
        mBusyIndicator.setVisibility(View.VISIBLE);
        mPendingTasks = new ArrayList<>();

        switch (mGivenCollectionType)
        {
            case LOCAL_ALBUM:
                initLocalCollection();
                break;
            case CLOUD_ALBUM:
                initCloudCollection();
                break;
        }
    }

    private void getInstanceStateFromBundle(Bundle bundle)
    {
        mAssetId = bundle.getString(ASSET_ID);
        mAlbumId = bundle.getInt(ALBUM_ID);
        mMemberId = bundle.getString(MEMBER_ID);
        mXToken = bundle.getString(X_TOKEN);
        mGivenCollectionType = (MPPhotoCollectionModel.AlbumType)bundle.getSerializable(ALBUM_TYPE);
    }

    private void putInstanceStateToBundle(Bundle bundle)
    {
        bundle.putString(ASSET_ID, mAssetId);
        bundle.putInt(ALBUM_ID, mAlbumId);
        bundle.putString(MEMBER_ID, mMemberId);
        bundle.putString(X_TOKEN, mXToken);
        bundle.putSerializable(ALBUM_TYPE, mGivenCollectionType);
    }

    private void initLocalCollection()
    {
        if (mGivenCollectionType != MPPhotoCollectionModel.AlbumType.LOCAL_ALBUM)
            return;

        if (mAlbumId != null)
            fetchLocalPhotoChunk(0);
    }

    private void initCloudCollection()
    {
        if (mGivenCollectionType != MPPhotoCollectionModel.AlbumType.CLOUD_ALBUM)
            return;

        if (mAssetId != null && mMemberId != null && mXToken != null)
        {
            MPPhotoAlbumUtility.getCloudCollection(getActivity(), mXToken, mAssetId,
                    mMemberId, new MPPhotoAlbumUtility.CollectionFetchListener()
                    {
                        @Override
                        public void onError()
                        {
                            Log.d("MPPhotoPickerFragment", "Error while fetching cloud data");
                            mBusyIndicator.setVisibility(View.INVISIBLE);

                            // TODO: Handle the condition when the network request fails
                        }

                        @Override
                        public void onSuccess(MPPhotoCollectionModel collection)
                        {
                            mCollection = collection;

                            mBusyIndicator.setVisibility(View.INVISIBLE);
                            initView();
                        }
                    });
        }
    }

    private void initView()
    {
        if (mCollection == null)
            return;

        // Show the placeholder when there are no images in the cloud album
        if (mCollection.albumType == MPPhotoCollectionModel.AlbumType.CLOUD_ALBUM &&
                mCollection.photos.size() == 0)
        {
            mGridView.setVisibility(View.INVISIBLE);
            mCloudPlaceholder.setVisibility(View.VISIBLE);

            return;
        }
        else
        {
            mGridView.setVisibility(View.VISIBLE);
            mCloudPlaceholder.setVisibility(View.INVISIBLE);
        }

        mAdapter = new MPPhotoPickerAdapter(getActivity(), this);
        mGridView.setAdapter(mAdapter);

        mGridView.setOnItemClickListener(this);
        mGridView.setOnScrollListener(this);
    }

    private void cancelPendingTasks()
    {
        if (mPendingTasks == null || mPendingTasks.isEmpty())
            return;

        Log.d("MPPhotoPickerFragment", "Cancelling pending tasks");
        for(AsyncTask task : mPendingTasks)
        {
            try
            {
                task.cancel(true);
            }
            catch (Exception e)
            {
                Log.d("MPPhotoPickerFragment", "Exception while cancelling pending tasks, but we" +
                        "should be fine: " + e.getMessage());
            }
        }

        mPendingTasks = null;
    }

    private void reloadView()
    {
        if (getActivity() == null)
            return;

        getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                else
                    initView();
            }
        });
    }

    private void fetchLocalPhotoChunk(final int offset)
    {
        AsyncTask fetchTask = new AsyncTask<Object, Void, MPPhotoCollectionModel>()
        {
            @Override
            protected MPPhotoCollectionModel doInBackground(Object... nothing)
            {
                return MPPhotoAlbumUtility.getPartialLocalPhotoCollection(getActivity(),
                        mAlbumId, offset, kPhotosLimit);
            }

            @Override
            protected void onPostExecute(MPPhotoCollectionModel photoCollectionModel)
            {
                if (photoCollectionModel == null)
                {
                    // All photos fetched, job done
                    mShouldFetchPhotos = false;
                    return;
                }

                // Allow firing requests again
                mShouldFetchPhotos = true;

                if (mBusyIndicator.getVisibility() == View.VISIBLE)
                    mBusyIndicator.setVisibility(View.INVISIBLE);

                if (offset == 0)
                    mCollection = photoCollectionModel;
                else
                    mCollection.photos.addAll(photoCollectionModel.photos);

                reloadView();

                // Task finished, remove from list of pending tasks
                mPendingTasks.remove(this);
            }
        };

        // Block the firing of further requests until this one returns
        mShouldFetchPhotos = false;

        fetchTask.execute();
        mPendingTasks.add(fetchTask);
    }

    private static final boolean kMultipleSelectionAllowed = false;
    private static final int kPhotosLimit = 30;
    private static final int kPhotoFetchDelta = 10;

    private PhotoPickerFragmentListener mListener;
    private MPPhotoPickerAdapter mAdapter;

    // Datamodel variables
    private Integer mAlbumId;
    private MPPhotoCollectionModel.AlbumType mGivenCollectionType;
    private MPPhotoCollectionModel mCollection;
    private ArrayList<AsyncTask> mPendingTasks;
    private boolean mShouldFetchPhotos = true;

    private String mAssetId;
    private String mMemberId;
    private String mXToken;

    // View related variables
    private GridView mGridView;
    private ProgressBar mBusyIndicator;
    private RelativeLayout mCloudPlaceholder;
}
