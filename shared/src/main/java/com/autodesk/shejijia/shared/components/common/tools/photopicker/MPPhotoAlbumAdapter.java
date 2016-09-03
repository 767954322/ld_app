package com.autodesk.shejijia.shared.components.common.tools.photopicker;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.utility.MPCameraUtils;
import com.autodesk.shejijia.shared.components.common.utility.ScreenUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

/**
 * Created by jainar on 02/05/16.
 */
public class MPPhotoAlbumAdapter extends BaseAdapter
{
    private static final int kThumbnailSizeInDp = 70;
    private PhotoAlbumAdapterListener mListener;
    private Context mContext;
    private int mThumbSize;

    public MPPhotoAlbumAdapter(Context context, PhotoAlbumAdapterListener listener)
    {
        mContext = context;

        assert  (listener != null);

        mListener = listener;

        mThumbSize = (int) ScreenUtil.convertDpToPixel(context,kThumbnailSizeInDp);
    }

    @Override
    public int getCount()
    {
        return mListener.getAlbumCount();
    }

    @Override
    public Object getItem(int position)
    {
        return mListener.getAlbum(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup)
    {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        View view;
        PhotoAlbumRow row;

        if (convertView == null)
        {
            view = inflater.inflate(R.layout.view_photoalbum_cell, null);

            row = new PhotoAlbumRow();
            row.thumbnail = (ImageView)view.findViewById(R.id.photoalbum_cell_thumbnail);
            row.cloudBadge = (ImageView)view.findViewById(R.id.photoalbum_cell_cloudindicator);
            row.titleLabel = (TextView)view.findViewById(R.id.photoalbum_cell_title);
            row.subtitleLabel = (TextView)view.findViewById(R.id.photoalbum_cell_subtitle);
            row.selectionIndicator = (ImageView)view.findViewById(R.id.photoalbum_cell_selectionindicator);

            view.setTag(row);
        }
        else
        {
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

        row.position = position;

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

            if (numPhotos > 1)
                row.subtitleLabel.setText(String.format("%d %s", numPhotos,
                        mContext.getString(R.string.photopicker_album_subtitle_multiple)));
            else if (numPhotos == 1)
                row.subtitleLabel.setText(String.format("%d %s", numPhotos,
                        mContext.getString(R.string.photopicker_album_subtitle_single)));
            else
                row.subtitleLabel.setVisibility(View.GONE);

            // Show the cloud badge icon only for cloud albums
            if (album.albumType == MPPhotoAlbumModel.eAlbumType.CLOUD_ALBUM)
                row.cloudBadge.setVisibility(View.VISIBLE);
            else
                row.cloudBadge.setVisibility(View.INVISIBLE);

            BitmapRotator rotator = new BitmapRotator(album.thumbnailOrientation);
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .preProcessor(rotator)
                    .build();

            ImageSize imageSize = new ImageSize(imageSide, imageSide);

            if (album.thumbnailUri != null)
                ImageLoader.getInstance().displayImage(album.thumbnailUri,
                        new ImageViewAware(row.thumbnail), options, imageSize, null, null);
            else
                row.thumbnail.setImageDrawable(mContext.getResources().getDrawable(
                        R.drawable.photopicker_placeholder));
        }
        catch (Exception e)
        {
            Log.d("MPPhotoPickerAdapter", "Could not load image: " + e.getMessage());
        }

        if (getSelectedState(position))
            row.selectionIndicator.setImageDrawable(mContext.getResources().
                    getDrawable(R.drawable.photopicker_checked));
        else
            row.selectionIndicator.setImageDrawable(mContext.getResources().
                    getDrawable(R.drawable.photopicker_unchecked));

        return view;
    }

    public interface PhotoAlbumAdapterListener
    {
        int getAlbumCount();

        MPPhotoAlbumModel getAlbum(int position);

        boolean getAlbumSelectedState(int position);

        boolean isRowVisible(int position);
    }

    private class BitmapRotator implements BitmapProcessor
    {
        private int mRotateBy;

        BitmapRotator(int rotateBy)
        {
            this.mRotateBy = rotateBy;
        }

        @Override
        public Bitmap process(Bitmap bitmap)
        {
            return MPCameraUtils.getRotatedImage(bitmap, mRotateBy);
        }
    }

    private boolean getSelectedState(int position)
    {
        return mListener.getAlbumSelectedState(position);
    }

    private boolean isRowVisible(int position)
    {
        return mListener.isRowVisible(position);
    }

    private class PhotoAlbumRow
    {
        ImageView thumbnail;
        ImageView cloudBadge;
        TextView titleLabel;
        TextView subtitleLabel;
        ImageView selectionIndicator;
        int position;
    }
}
