package com.autodesk.shejijia.shared.components.issue.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.model.entity.ImageInfo;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Menghao.Gu on 2016/12/8.
 */

public class IssueAddListImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ImageInfo> imgList;
    private Context mContext;
    private int resId;

    public IssueAddListImageAdapter(ArrayList<ImageInfo> imgList, Context mContext, int resId) {
        this.imgList = imgList;
        this.mContext = mContext;
        this.resId = resId;
    }

    public void reflushList(ArrayList<ImageInfo> imgList) {
        this.imgList = imgList;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
        return new IssueAddListImageAdapter.IssueImageVH(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        IssueAddListImageAdapter.IssueImageVH issueImagesVh = (IssueAddListImageAdapter.IssueImageVH) holder;

        initView(issueImagesVh, position);

        initEvents(issueImagesVh, position);

    }

    private void initView(IssueAddListImageAdapter.IssueImageVH issueImagesVh, int position) {
        if (imgList != null) {
            ImageUtils.loadImage(issueImagesVh.imgIssueDescription, imgList.get(position).getPictureUri());
        }
    }

    private void initEvents(IssueAddListImageAdapter.IssueImageVH issueImagesVh, final int position) {

    }

    @Override
    public int getItemCount() {
        return null == imgList ? 0 : imgList.size();
    }

    private static class IssueImageVH extends RecyclerView.ViewHolder {
        private ImageView imgIssueDescription;

        IssueImageVH(View itemView) {
            super(itemView);
            imgIssueDescription = (ImageView) itemView.findViewById(R.id.iv_item_addissue_image);
        }
    }

}
