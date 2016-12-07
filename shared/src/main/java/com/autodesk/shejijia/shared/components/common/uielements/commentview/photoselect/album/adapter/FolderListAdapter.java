package com.autodesk.shejijia.shared.components.common.uielements.commentview.photoselect.album.adapter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.photoselect.album.AlbumFragment;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.photoselect.model.entity.AlbumFolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import static com.autodesk.shejijia.shared.components.common.uielements.commentview.photoselect.utils.CommonUtils.checkNotNull;

public class FolderListAdapter extends RecyclerView.Adapter<FolderListAdapter.FolderViewHolder> {
    private static final String sFilePrefix = "file://";

    private List<AlbumFolder> mData;

    private int mSelectedIndex = 0;

//    private final RequestManager mRequestManager;

    private AlbumFragment.FolderItemListener mListener;

    private DisplayImageOptions options;

//    public FolderListAdapter(RequestManager requestManager, AlbumFragment.FolderItemListener listener) {
//        mRequestManager = checkNotNull(requestManager);
//        mListener = listener;
//        mData = new ArrayList<>();
//    }

    public FolderListAdapter(AlbumFragment.FolderItemListener listener) {
        mListener = listener;
        mData = new ArrayList<>();
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showImageOnLoading(R.drawable.photopicker_placeholder)
                .build();
    }

    @Override
    public FolderListAdapter.FolderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album_folder_list, parent, false);
        return new FolderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FolderListAdapter.FolderViewHolder holder, int position) {

        final AlbumFolder folder = mData.get(position);

//        mRequestManager
//                .load(folder.getCover().getPath())
//                .asBitmap()
//                .into(holder.mCoverView);
        ImageLoader.getInstance().displayImage(sFilePrefix + folder.getCover().getPath(),holder.mCoverView,options);
        holder.mFolderName.setText(folder.getFloderName());
        holder.mFolderSize.setText(holder.itemView.getContext()
                .getString(R.string.folder_size, folder.getImgInfos().size()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFolderItemClick(folder);
                holder.itemView.setBackgroundColor(holder.mSelectedColor);
                mSelectedIndex = holder.getAdapterPosition();
                notifyDataSetChanged();
            }
        });

        if (mSelectedIndex == position) {
            holder.itemView.setBackgroundColor(holder.mSelectedColor);
        } else {
            holder.itemView.setBackgroundColor(holder.mNormalColor);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(@NonNull List<AlbumFolder> data) {
        mData = checkNotNull(data);
        notifyDataSetChanged();
    }

    static class FolderViewHolder extends RecyclerView.ViewHolder {
        ImageView mCoverView;
        TextView mFolderName;
        TextView mFolderSize;
        int mNormalColor;
        int mSelectedColor;

        public FolderViewHolder(View itemView) {
            super(itemView);
            mCoverView = (ImageView) itemView.findViewById(R.id.iv_cover);
            mFolderName = (TextView) itemView.findViewById(R.id.tv_floder_name);
            mFolderSize = (TextView) itemView.findViewById(R.id.tv_folder_size);
            mNormalColor = ContextCompat.getColor(itemView.getContext(), R.color.background);
            mSelectedColor = ContextCompat.getColor(itemView.getContext(), R.color.primary_light);
        }
    }

}
