package com.autodesk.shejijia.shared.components.common.uielements.commentview.comment;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.CommentConfig;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.model.entity.ImageInfo;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.widget.CircleDisplayer;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * Created by t_panya on 16/11/30.
 */

public class SelectedImgAdapter extends RecyclerView.Adapter {
    private static final String sFilePrefix = "file://";
    private static final int ADD_ITEM = 0;
    private static final int IMG_ITEM = 1;
    private List<ImageInfo> mImages = Collections.emptyList();
    private CommentConfig mConfig;
    private CommentFragment.ImageItemClickListener mListener;
    private ImageLoader mLoader;
    private DisplayImageOptions emptyOption;
    private DisplayImageOptions options;

    public SelectedImgAdapter(CommentConfig config, CommentFragment.ImageItemClickListener listener, ImageLoader loader) {
        mConfig = config;
        mListener = listener;
        mLoader = loader;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.photopicker_placeholder)
                .showImageOnFail(R.drawable.photopicker_placeholder)
                .showImageForEmptyUri(R.drawable.photopicker_placeholder)
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .resetViewBeforeLoading(true)
                .build();
        emptyOption = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisk(false)
                .showImageForEmptyUri(R.drawable.photopicker_placeholder)
                .build();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView;
        int width = parent.getMeasuredWidth() / 3;
        Log.d("SelectedImgAdapter", "onCreateViewHolder: width == " + width);
        if (viewType == ADD_ITEM) {
            rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_addphoto, parent, false);
            rootView.getLayoutParams().height = width;      //width 已经有layoutmanager 确认过了。所以只需要确认height
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onAddItemClick();
                }
            });
            return new RecyclerView.ViewHolder(rootView) {
            };
        } else {
            rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gridphoto_cell, parent, false);
            rootView.getLayoutParams().height = width;      //width 已经有layoutmanager 确认过了。所以只需要确认height
            return new ImageViewHolder(rootView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (isImgItem(position)) {
            final ImageViewHolder viewHolder = (ImageViewHolder) holder;
            final String path = getListItem(position);
            if (mConfig.geteLayoutType() == CommentConfig.LayoutType.SHOW) {
                viewHolder.delete.setVisibility(View.GONE);
            }
            if (path != null) {
                mLoader.displayImage(path, viewHolder.image, options);
                viewHolder.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onImgItemClick(position);
                    }
                });
                viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onDeleteImageClick(position);
                    }
                });
            } else {
                mLoader.displayImage(null, viewHolder.image, emptyOption);
            }
        } else {
//            mLoader.displayImage(null,viewHolder.image,emptyOption);
        }
    }

    @Override
    public int getItemCount() {
        if (mConfig.geteLayoutType() == CommentConfig.LayoutType.EDIT
                || mConfig.geteLayoutType() == CommentConfig.LayoutType.EDIT_IMAGE_ONLY) {
            return mImages.size() + 1;
        } else {
            return mImages.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mConfig.geteLayoutType() == CommentConfig.LayoutType.EDIT
                || mConfig.geteLayoutType() == CommentConfig.LayoutType.EDIT_IMAGE_ONLY) {
            if (mImages == null || mImages.size() == 0) {
                return ADD_ITEM;
            } else {
                if (position == mImages.size()) {
                    return ADD_ITEM;
                }
                return IMG_ITEM;
            }
        } else {
            return IMG_ITEM;
        }
    }

    public void replaceData(List<ImageInfo> images) {
        mImages = images;
        notifyDataSetChanged();
    }

    public void showEmptyRecyclerView() {
        notifyDataSetChanged();
    }

    private boolean isImgItem(int position) {
        return getItemViewType(position) == IMG_ITEM ? true : false;
    }

    private String getListItem(int position) {
        if (isImgItem(position)) {
            return mImages.get(position).getPictureUri();
        } else {
            return null;
        }
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        ImageView delete;

        public ImageViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.iv_photo);
            delete = (ImageView) itemView.findViewById(R.id.iv_delete_picture);
        }
    }
}
