package com.autodesk.shejijia.shared.components.common.uielements.commentview.comment;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.uielements.commoncomment.CommentConfig;
import com.autodesk.shejijia.shared.components.common.uielements.commoncomment.comment.CommentFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Collections;
import java.util.List;

/**
 * Created by t_panya on 16/11/30.
 */

public class SelectedImgAdapter extends RecyclerView.Adapter {
    private static final String sFilePrefix = "file://";
    private static final int ADD_ITEM = 0;
    private static final int IMG_ITEM = 1;
    private List<String> mData = Collections.emptyList();
    private CommentConfig mConfig;
    private com.autodesk.shejijia.shared.components.common.uielements.commoncomment.comment.CommentFragment.ImageAddItemClickListener mListener;
    private ImageLoader mLoader;
    private DisplayImageOptions emptyOption;
    private DisplayImageOptions options;

    public SelectedImgAdapter(CommentConfig config, CommentFragment.ImageAddItemClickListener listener, ImageLoader loader){
        mConfig = config;
        mListener = listener;
        mLoader = loader;
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showImageOnLoading(R.drawable.photopicker_placeholder)
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
        if(viewType == ADD_ITEM){
            rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gridphoto_cell,parent,false);
            rootView.getLayoutParams().height = width;      //width 已经有layoutmanager 确认过了。所以只需要确认height
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onAddItemClick();
                }
            });
            return new ImageViewHolder(rootView);
        } else {
            rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gridphoto_cell,parent,false);
            rootView.getLayoutParams().height = width;      //width 已经有layoutmanager 确认过了。所以只需要确认height
            return new ImageViewHolder(rootView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ImageViewHolder viewHolder = (ImageViewHolder) holder;
        if(isImgItem(position)){
//            final ImageViewHolder viewHolder = (ImageViewHolder) holder;
            final String path = getListItem(position);
            if(path != null){
                mLoader.displayImage(sFilePrefix + path,viewHolder.image,options);
                viewHolder.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onImgItemClick(position + 1);
                    }
                });
            } else {
                mLoader.displayImage(null,viewHolder.image,emptyOption);
            }
        }else {
//            mLoader.displayImage(null,viewHolder.image,emptyOption);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(mData == null || mData.size() == 0){
            return ADD_ITEM;
        } else {
            if(position == mData.size()){
                return ADD_ITEM;
            }
            return IMG_ITEM;
        }
    }

    public void replaceData(List<String> data){
        mData = data;
        notifyDataSetChanged();
    }

    public void showEmptyRecyclerView(){
        notifyDataSetChanged();
    }

    private boolean isImgItem(int position){
        return  getItemViewType(position) == IMG_ITEM ? true : false;
    }

    private String getListItem(int position){
        if(isImgItem(position)){
            return mData.get(position);
        }else {
            return null;
        }
    }

    class ImageViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        public ImageViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.iv_gridphoto_cell_thumbnail);
        }
    }
}
