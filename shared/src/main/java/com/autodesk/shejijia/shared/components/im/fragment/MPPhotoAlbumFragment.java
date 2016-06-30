package com.autodesk.shejijia.shared.components.im.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.tools.photopicker.MPPhotoAlbumModel;
import com.autodesk.shejijia.shared.components.common.tools.photopicker.MPPhotoAlbumAdapter;
import com.autodesk.shejijia.shared.components.common.tools.photopicker.MPPhotoAlbumUtility;
import com.autodesk.shejijia.shared.components.common.utility.ScreenUtil;

import java.util.ArrayList;


public class MPPhotoAlbumFragment extends Fragment
        implements MPPhotoAlbumAdapter.PhotoAlbumAdapterListener
{
    public static final String ASSET_ID = "assetid";
    public static final String SELECTED_ALBUM = "selectedalbum";
    public static final String MEMBER_ID = "memberid";
    public static final String X_TOKEN = "xtoken";

    public interface PhotoAlbumFragmentListener
    {
        void onAlbumSelectionChanged(Integer albumId, String albumName);

        void onPlaceholderClicked();
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
            mSelectedAlbum = getArguments().getInt(SELECTED_ALBUM);
            mMemberId = getArguments().getString(MEMBER_ID);
            mXToken = getArguments().getString(X_TOKEN);

            assert (mSelectedAlbum != null);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo_album, container, false);

        mListView = (ListView) view.findViewById(R.id.photoalbum_list);
        mBusyIndicator = (ProgressBar) view.findViewById(R.id.photoalbum_progressbar);
        mPlaceholder = view.findViewById(R.id.photoalbum_spacer);

        mPlaceholder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (mListener != null)
                    mListener.onPlaceholderClicked();
            }
        });

        return view;
    }

    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim)
    {
        if (enter)
        {
            float lowerLimit = getWindowHeight() - ScreenUtil.convertDpToPixel(getActivity(),
                    kTranslateLowerLimit);

            // Android fills in the target for us, no worries
            ObjectAnimator animator = ObjectAnimator.ofFloat(null, View.Y,
                    lowerLimit, 0);
            animator.setDuration(500);

            return animator;
        }
        else
        {
            float lowerLimitExit = getWindowHeight() - ScreenUtil.convertDpToPixel(getActivity(),
                    kTranslateLowerLimit);

            // Android fills in the target for us, no worries
            ObjectAnimator animatorExit = ObjectAnimator.ofFloat(null, View.Y,
                    0, lowerLimitExit);
            animatorExit.setDuration(500);

            return animatorExit;
        }
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        if (activity instanceof PhotoAlbumFragmentListener)
            mListener = (PhotoAlbumFragmentListener) activity;
        else
        {
            throw new RuntimeException(activity.toString()
                    + " must implement PhotoAlbumFragmentListener");
        }
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
    public int getAlbumCount()
    {
        int count = 0;

        if (mAlbums != null)
            count = mAlbums.size();

        return count;
    }

    @Override
    public MPPhotoAlbumModel getAlbum(int position)
    {
        MPPhotoAlbumModel albumModel = null;

        if (mAlbums != null && mAlbums.size() != 0)
            albumModel = mAlbums.get(position);

        return albumModel;
    }

    @Override
    public boolean getAlbumSelectedState(int position)
    {
        boolean isAlbumSelected = false;

        if (mAlbums != null && mAlbums.size() != 0)
        {
            MPPhotoAlbumModel album = mAlbums.get(position);
            if (album != null)
                isAlbumSelected = album.isSelected;
        }

        return isAlbumSelected;
    }

    @Override
    public boolean isRowVisible(int position)
    {
        if (mListView == null)
            return false;

        int firstVisiblePosition = mListView.getFirstVisiblePosition();
        int lastVisiblePosition = mListView.getLastVisiblePosition();

        return position >= firstVisiblePosition && position <= lastVisiblePosition;
    }

    private void init()
    {
        mAlbums = new ArrayList<>();

        LocalDataModelTask localTask = new LocalDataModelTask();

        mBusyIndicator.setVisibility(View.VISIBLE);
        localTask.execute();

        initCloudAlbum();

        mPendingTasks = new ArrayList<>();
        mPendingTasks.add(localTask);
    }

    private void getInstanceStateFromBundle(Bundle bundle)
    {
        mAssetId = bundle.getString(ASSET_ID);
        mMemberId = bundle.getString(MEMBER_ID);
        mXToken = bundle.getString(X_TOKEN);
        mSelectedAlbum = bundle.getInt(SELECTED_ALBUM);
    }

    private void putInstanceStateToBundle(Bundle bundle)
    {
        bundle.putString(ASSET_ID, mAssetId);
        bundle.putString(MEMBER_ID, mMemberId);
        bundle.putString(X_TOKEN, mXToken);
        bundle.putInt(SELECTED_ALBUM, mSelectedAlbum);
    }

    private void initLocalAlbums()
    {
        final ArrayList<MPPhotoAlbumModel> albums = MPPhotoAlbumUtility.getAllLocalAlbums(getActivity());

        if (albums == null)
            return;

        for (MPPhotoAlbumModel album : albums)
        {
            album.isSelected = (album.albumId.intValue() == mSelectedAlbum.intValue());
        }

        getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                mAlbums.addAll(albums);

                reloadView();
            }
        });
    }

    private void initCloudAlbum()
    {
        MPPhotoAlbumModel cloudAlbum = new MPPhotoAlbumModel();
        cloudAlbum.albumType = MPPhotoAlbumModel.eAlbumType.CLOUD_ALBUM;
        cloudAlbum.albumName = getActivity().getString(R.string.photopicker_cloud_album);
        cloudAlbum.albumId = MPPhotoAlbumUtility.kCloudAlbumId;
        cloudAlbum.isSelected = false;
        cloudAlbum.thumbnailOrientation = 0;
        cloudAlbum.albumSize = -1; // Set a sentinel value

        if (mMemberId != null && mXToken != null && mAssetId != null)
        {
            if (mAlbums != null)
            {
                mAlbums.add(kCloudAlbumRowPosition, cloudAlbum);
                reloadView();
            }

            MPPhotoAlbumUtility.getCloudAlbumAsync(getActivity(), mXToken, mAssetId, mMemberId,
                    new MPPhotoAlbumUtility.AlbumFetchListener()
                    {
                        @Override
                        public void onError()
                        {
                            Log.d(TAG, "Error while fetching cloud data");
                            if (mAlbums != null && mAlbums.size() != 0)
                            {
                                MPPhotoAlbumModel cloudAlbum = mAlbums.get(kCloudAlbumRowPosition);

                                // Confirm that we are picking the right row to delete
                                if (cloudAlbum.albumType == MPPhotoAlbumModel.eAlbumType.CLOUD_ALBUM)
                                {
                                    mAlbums.remove(kCloudAlbumRowPosition);
                                    reloadView();
                                }
                            }
                        }

                        @Override
                        public void onSuccess(MPPhotoAlbumModel album)
                        {
                            if (mAlbums == null)
                                return;

                            MPPhotoAlbumModel albumToReplace = mAlbums.get(kCloudAlbumRowPosition);

                            if (albumToReplace.albumType == MPPhotoAlbumModel.eAlbumType.CLOUD_ALBUM)
                            {
                                if (album.albumId == mSelectedAlbum.intValue())
                                    album.isSelected = true;

                                mAlbums.set(kCloudAlbumRowPosition, album);

                                mBusyIndicator.setVisibility(View.INVISIBLE);
                                reloadView();
                            }
                        }
                    });
        }
    }

    private void initView()
    {
        if (mListView == null)
            return;

        mAdapter = new MPPhotoAlbumAdapter(getActivity(), this);
        final MPPhotoAlbumAdapter adapter = mAdapter;
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (mAlbums != null && mAlbums.size() > 0)
                {
                    Integer selectedAlbum = 0;

                    MPPhotoAlbumModel album = mAlbums.get(position);

                    // The album is already selected; do nothing
                    if (album.isSelected)
                        return;

                    // Do not allow selection of invalid/improperly initialized albums
                    if (album.albumSize < 0)
                        return;

                    for (MPPhotoAlbumModel al : mAlbums)
                    {
                        if (al.isSelected && al != album)
                            al.isSelected = false;
                    }

                    album.isSelected = true;

                    selectedAlbum = album.albumId;

                    // Inform the activity
                    mListener.onAlbumSelectionChanged(selectedAlbum, album.albumName);

                    getActivity().runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            // The selection state has changed, reload
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
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

    private void cancelPendingTasks()
    {
        if (mPendingTasks == null || mPendingTasks.isEmpty())
            return;

        for (AsyncTask task : mPendingTasks)
        {
            try
            {
                task.cancel(true);
            }
            catch (Exception e)
            {
                Log.d(TAG, "Exception while cancelling pending tasks, but we" +
                        "should be fine: " + e.getMessage());
            }
        }

        mPendingTasks = null;
    }

    private float getWindowHeight()
    {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);

        return metrics.heightPixels;
    }

    private class LocalDataModelTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... nothing)
        {
            initLocalAlbums();
            return null;
        }

        @Override
        protected void onPostExecute(Void nothing)
        {
            mBusyIndicator.setVisibility(View.INVISIBLE);
            reloadView();
        }
    }

    // Height of the bottom bar in dp (bottom bar in activity layout)
    private static final int kTranslateLowerLimit = 50;
    private static final int kCloudAlbumRowPosition = 0;

    private static final String TAG = "MPPhotoAlbumFragment";

    private String mAssetId;
    private String mMemberId;
    private String mXToken;
    private ArrayList<MPPhotoAlbumModel> mAlbums;
    private Integer mSelectedAlbum;
    private ArrayList<AsyncTask> mPendingTasks;

    private PhotoAlbumFragmentListener mListener;
    private MPPhotoAlbumAdapter mAdapter;
    private ListView mListView;
    private ProgressBar mBusyIndicator;
    private View mPlaceholder;
}
