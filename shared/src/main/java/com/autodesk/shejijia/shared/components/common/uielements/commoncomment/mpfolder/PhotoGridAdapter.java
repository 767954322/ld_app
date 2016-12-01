package com.autodesk.shejijia.shared.components.common.uielements.commoncomment.mpfolder;

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
import com.autodesk.shejijia.shared.components.common.tools.photopicker.MPPhotoModel;
import com.autodesk.shejijia.shared.components.common.utility.MPCameraUtils;
import com.autodesk.shejijia.shared.components.common.utility.ScreenUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

/**
 * Created by t_panya on 16/11/24.
 */

public class PhotoGridAdapter extends BaseAdapter {
    private static final String TAG = "PhotoGridAdapter";
    private static final int sNumberOfColumn = 3;
    private Context mContext;
    private PhotoGridAdapterListener mListener;
    private int mCellWidth;
    private LayoutInflater mInflater;

    public interface PhotoGridAdapterListener {
        int getPhotoCount();

        MPPhotoModel getPhotoModel(int position);

        boolean getSelectedState(int position);

        boolean isCellVisible(int position);
    }

    public PhotoGridAdapter(Context context, PhotoGridAdapterListener listener){
        mContext = context;
        mListener = listener;
        mCellWidth = getCellWidth();
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mListener.getPhotoCount();
    }

    @Override
    public Object getItem(int position) {
        return mListener.getPhotoModel(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        PhotoPickerCell cell;

        if(convertView == null){
            view = mInflater.inflate(R.layout.item_photopicker_cell, null);
            cell = new PhotoPickerCell();
            cell.image = (ImageView) view.findViewById(R.id.photopicker_cell_thumbnail);
            cell.selectionIndicator = (ImageView) view.findViewById(R.id.photopicker_cell_selectionindicator);

            RelativeLayout.LayoutParams imageLayoutParams = new RelativeLayout.LayoutParams
                    (mCellWidth, mCellWidth);
            cell.image.setLayoutParams(imageLayoutParams);

            view.setTag(cell);
        } else {
            view = convertView;
            cell = (PhotoPickerCell) view.getTag();
            Integer prevPosition = cell.position;
            if (!isCellVisible(prevPosition) && prevPosition != position) {
                cell.image.setImageBitmap(null);

                ImageLoader.getInstance().cancelDisplayTask(cell.image);
            }
        }

        try {
            if(position == 0){
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .cacheInMemory(false)
                        .cacheOnDisk(false)
                        .showImageForEmptyUri(R.drawable.photopicker_placeholder)
                        .build();
                ImageSize imageSize = new ImageSize(mCellWidth, mCellWidth);
                ImageLoader.getInstance().displayImage(null,
                        new ImageViewAware(cell.image), options, imageSize, null, null);
                cell.selectionIndicator.setVisibility(View.GONE);
            } else {
                MPPhotoModel photo = (MPPhotoModel)getItem(position - 1);

                if (photo != null) {
                    int imageSide = mCellWidth;

                    BitmapRotate rotate = new BitmapRotate(photo.orientation);
                    DisplayImageOptions options = new DisplayImageOptions.Builder()
                            .cacheInMemory(true)
                            .cacheOnDisk(true)
                            .bitmapConfig(Bitmap.Config.RGB_565)
                            .preProcessor(rotate)
                            .showImageOnLoading(R.drawable.photopicker_placeholder)
                            .build();

                    ImageSize imageSize = new ImageSize(imageSide, imageSide);
                    ImageLoader.getInstance().displayImage(photo.thumbnailUri,
                            new ImageViewAware(cell.image), options, imageSize, null, null);
                }

                if (getSelectedState(position - 1)) {
                    cell.selectionIndicator.setImageDrawable(mContext.getResources().
                            getDrawable(R.drawable.photopicker_checked));
                } else {
                    cell.selectionIndicator.setImageDrawable(mContext.getResources().
                            getDrawable(R.drawable.photopicker_unchecked));
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "Could not load image: " + e.getMessage());
        }


        return view;
    }

    private boolean getSelectedState(int position) {
        return mListener.getSelectedState(position);
    }

    private boolean isCellVisible(int position) {
        return mListener.isCellVisible(position);
    }

    private int getCellWidth() {
        int[] screenDimensions = ScreenUtil.getScreenDimensionsInPixels(mContext);

        int cellWidth = screenDimensions[0] / sNumberOfColumn;

        return cellWidth;
    }

    private class BitmapRotate implements BitmapProcessor {
        private int mRotateBy;

        BitmapRotate(int rotateBy)
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

    private class PhotoPickerCell {
        public ImageView image;
        public ImageView selectionIndicator;
        public int position;
    }
}
