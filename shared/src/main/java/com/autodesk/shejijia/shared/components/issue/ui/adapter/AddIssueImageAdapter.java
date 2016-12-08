package com.autodesk.shejijia.shared.components.issue.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Menghao.Gu on 2016/12/8.
 */

public class AddIssueImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> imgList;
    private Context mContext;
    private int resId;

    public AddIssueImageAdapter(List<String> imgList, Context mContext, int resId) {
        this.imgList = imgList;
        this.mContext = mContext;
        this.resId = resId;
    }

    public void reflushList(List<String> imgList) {
        this.imgList = imgList;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
        return new AddIssueImageAdapter.IssueImageVH(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        AddIssueImageAdapter.IssueImageVH issueImagesVh = (AddIssueImageAdapter.IssueImageVH) holder;

        initView(issueImagesVh, position);

        initEvents(issueImagesVh, position);

    }

    private void initView(AddIssueImageAdapter.IssueImageVH issueImagesVh, int position) {
        if (imgList != null) {
            ImageUtils.loadImage(issueImagesVh.imgIssueDescription, "file:///" + imgList.get(position));
        }
    }

    private void initEvents(AddIssueImageAdapter.IssueImageVH issueImagesVh, final int position) {

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
