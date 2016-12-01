package com.autodesk.shejijia.shared.components.common.uielements.commoncomment.mpfolder;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.tools.photopicker.MPPhotoAlbumModel;
import com.autodesk.shejijia.shared.components.common.utility.MPCameraUtils;
import com.autodesk.shejijia.shared.components.common.utility.ScreenUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

/**
 * Created by t_panya on 16/11/23.
 */

public class AlbumListAdapter extends BaseAdapter {
    private static final String TAG = "AlbumListAdapter";
    private static final int sThumbnailSizeInDp = 70;
    private Context mContext;
    private AlbumListAdapterListener mListener;
    private int mThumbSize;
    private LayoutInflater mInflater;

    public interface AlbumListAdapterListener {
        int getAlbumCount();

        MPPhotoAlbumModel getAlbum(int position);

        boolean getAlbumSelectedState(int position);

        boolean isRowVisible(int position);
    }

    public AlbumListAdapter(Context context,AlbumListAdapterListener listener){
        mContext = context;
        mListener = listener;
        mThumbSize = (int) ScreenUtil.convertDpToPixel(context,sThumbnailSizeInDp);
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mListener.getAlbumCount();
    }

    @Override
    public Object getItem(int position) {
        return mListener.getAlbum(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        PhotoAlbumRow row;
        if(convertView == null){
            view = mInflater.inflate(R.layout.view_photoalbum_cell,parent,false);
            row = new PhotoAlbumRow();
            row.thumbnail = (ImageView)view.findViewById(R.id.photoalbum_cell_thumbnail);
            row.cloudBadge = (ImageView)view.findViewById(R.id.photoalbum_cell_cloudindicator);
            row.titleLabel = (TextView)view.findViewById(R.id.photoalbum_cell_title);
            row.subtitleLabel = (TextView)view.findViewById(R.id.photoalbum_cell_subtitle);
            row.selectionIndicator = (ImageView)view.findViewById(R.id.photoalbum_cell_selectionindicator);

            view.setTag(row);
        } else {
            view = convertView;

            row = (PhotoAlbumRow)view.getTag();

            Integer prevPosition = row.position;
            if (!isRowVisible(prevPosition) && prevPosition != position)
            {
                row.thumbnail.setImageBitmap(null);
                row.thumbnail.setImageDrawable(mContext.getResources().getDrawable(R.drawable.photopicker_thumbnail_placeholder));

                ImageLoader.getInstance().cancelDisplayTask(row.thumbnail);
            }
        }

        try
        {
            MPPhotoAlbumModel album = (MPPhotoAlbumModel)getItem(position);

            if (album == null)
                return null;

            int imageSide = mThumbSize;

            row.titleLabel.setText(album.albumName);

            int numPhotos = album.albumSize;

            // Set visible by default; needed because we are recycling views
            row.subtitleLabel.setVisibility(View.VISIBLE);

            if (numPhotos > 1){
                row.subtitleLabel.setText(String.format("%d %s", numPhotos,
                        mContext.getString(R.string.photopicker_album_subtitle_multiple)));
            } else if (numPhotos == 1){
                row.subtitleLabel.setText(String.format("%d %s", numPhotos,
                        mContext.getString(R.string.photopicker_album_subtitle_single)));
            } else {
                row.subtitleLabel.setVisibility(View.GONE);
            }

            // Show the cloud badge icon only for cloud albums
            if (album.albumType == MPPhotoAlbumModel.eAlbumType.CLOUD_ALBUM){
                row.cloudBadge.setVisibility(View.VISIBLE);
            } else {
                row.cloudBadge.setVisibility(View.INVISIBLE);
            }

            BitmapRotate rotate = new BitmapRotate(album.thumbnailOrientation);
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .preProcessor(rotate)
                    .build();

            ImageSize imageSize = new ImageSize(imageSide, imageSide);

            if (album.thumbnailUri != null) {
                ImageLoader.getInstance().displayImage(album.thumbnailUri,
                        new ImageViewAware(row.thumbnail), options, imageSize, null, null);
            } else {
                row.thumbnail.setImageDrawable(mContext.getResources().getDrawable(
                        R.drawable.photopicker_placeholder));
            }
        } catch (Exception e) {
            Log.d(TAG, "Could not load image: " + e.getMessage());
        }

        if (getSelectedState(position)){
            row.selectionIndicator.setImageDrawable(mContext.getResources().
                    getDrawable(R.drawable.photopicker_checked));
        } else {
            row.selectionIndicator.setImageDrawable(mContext.getResources().
                    getDrawable(R.drawable.photopicker_unchecked));
        }
        return view;
    }

    private boolean isRowVisible(int position) {
        return mListener.isRowVisible(position);
    }

    private boolean getSelectedState(int position) {
        return mListener.getAlbumSelectedState(position);
    }

    private class BitmapRotate implements BitmapProcessor
    {
        private int mRotateBy;

        BitmapRotate(int rotateBy)
        {
            this.mRotateBy = rotateBy;
        }

        @Override
        public Bitmap process(Bitmap bitmap)
        {
            return MPCameraUtils.getRotatedImage(bitmap, mRotateBy);
        }
    }

    private class PhotoAlbumRow {
        ImageView thumbnail;
        ImageView cloudBadge;
        TextView titleLabel;
        TextView subtitleLabel;
        ImageView selectionIndicator;
        int position;
    }
}
