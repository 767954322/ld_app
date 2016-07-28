package com.autodesk.shejijia.shared.components.common.tools.photopicker;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.utility.MPCameraUtils;
import com.autodesk.shejijia.shared.components.common.utility.ScreenUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

/**
 * Created by Administrator on 2016/3/24 0024.
 */
public class MPPhotoPickerAdapter extends BaseAdapter{
    private static final int kNumberOfColumns = 3;
    private Context mContext;
    private PhotoPickerAdapterListener mListener;
    private int mCellWidth;

    public MPPhotoPickerAdapter(Context context, PhotoPickerAdapterListener listener)
    {
        this.mContext = context;

        assert (listener != null);

        mListener = listener;

        mCellWidth = getCellWidth();
    }

    @Override
    public int getCount()
    {
        return mListener.getPhotoCount();
    }

    @Override
    public Object getItem(int position)
    {
        return mListener.getPhotoModel(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        View view;
        PhotoPickerCell cell;

        if (convertView == null)
        {
            view = inflater.inflate(R.layout.item_photopicker_cell, null);

            cell = new PhotoPickerCell();
            cell.image = (ImageView) view.findViewById(R.id.photopicker_cell_thumbnail);
            cell.selectionIndicator = (ImageView) view.findViewById(R.id.photopicker_cell_selectionindicator);

            // Resize the imageview to the calculated cell size
            RelativeLayout.LayoutParams imageLayoutParams = new RelativeLayout.LayoutParams
                    (mCellWidth, mCellWidth);
            cell.image.setLayoutParams(imageLayoutParams);

            view.setTag(cell);
        }
        else
        {
            view = convertView;

            cell = (PhotoPickerCell)view.getTag();

            Integer prevPosition = cell.position;
            if (!isCellVisible(prevPosition) && prevPosition != position)
            {
                cell.image.setImageBitmap(null);

                ImageLoader.getInstance().cancelDisplayTask(cell.image);
            }
        }

        cell.position = position;

        try
        {
            MPPhotoModel photo = (MPPhotoModel)getItem(position);

            if (photo != null)
            {
                int imageSide = mCellWidth;

                BitmapRotator rotator = new BitmapRotator(photo.orientation);
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .preProcessor(rotator)
                        .showImageOnLoading(R.drawable.photopicker_placeholder)
                        .build();

                ImageSize imageSize = new ImageSize(imageSide, imageSide);
                ImageLoader.getInstance().displayImage(photo.thumbnailUri,
                        new ImageViewAware(cell.image), options, imageSize, null, null);
            }
        }
        catch (Exception e)
        {
            Log.d("MPPhotoPickerAdapter", "Could not load image: " + e.getMessage());
        }

        if (getSelectedState(position))
            cell.selectionIndicator.setImageDrawable(mContext.getResources().
                    getDrawable(R.drawable.photopicker_checked));
        else
            cell.selectionIndicator.setImageDrawable(mContext.getResources().
                    getDrawable(R.drawable.photopicker_unchecked));

        return view;
    }

    public interface PhotoPickerAdapterListener
    {
        int getPhotoCount();

        MPPhotoModel getPhotoModel(int position);

        boolean getSelectedState(int position);

        boolean isCellVisible(int position);
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
            if (mRotateBy % 360 == 0)
                return bitmap;

            return MPCameraUtils.getRotatedImage(bitmap, mRotateBy);
        }
    }

    private boolean getSelectedState(int position)
    {
        return mListener.getSelectedState(position);
    }

    private boolean isCellVisible(int position)
    {
        return mListener.isCellVisible(position);
    }

    private int getCellWidth()
    {
        int[] screenDimensions = ScreenUtil.getScreenDimensionsInPixels(mContext);

        int cellWidth = screenDimensions[0] / kNumberOfColumns;

        return cellWidth;
    }

    private class PhotoPickerCell
    {
        public ImageView image;
        public ImageView selectionIndicator;
        public int position;
    }
}
