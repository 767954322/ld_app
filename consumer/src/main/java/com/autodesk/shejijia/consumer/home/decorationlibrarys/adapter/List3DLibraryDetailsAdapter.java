package com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.activity.CaseLibraryDetailActivity;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.activity.CaseLibraryRoamingWebView;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.util.List;

/**
 * Created by t_xuz on 9/6/16.
 * 3d 详情显示图片列表的 adapter
 */
public class List3DLibraryDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> imageLists;
    private int resId;
    private Context mContext;
    private String mType;

    public List3DLibraryDetailsAdapter(String type, List<String> imageLists, int resId, Context mContext) {
        this.imageLists = imageLists;
        this.resId = resId;
        this.mContext = mContext;
        this.mType = type;


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
        return new List3dViewVH(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        List3dViewVH viewVH = (List3dViewVH) holder;
        if (!TextUtils.isEmpty(imageLists.get(position))) {
            if (mType.equals("4")) {
                String maYouUrl = imageLists.get(position);
                if (maYouUrl.equals("drawable")) {
                    ImageUtils.loadFileImage(viewVH.m3DDetailsImage, "drawable://" + R.drawable.default_3d_details);
                    // ImageUtils.loadFileImage(viewVH.m3DetailsImage,"drawable://"+R.drawable.roaming_figure);
                    viewVH.m3DetailsImage.setVisibility(View.GONE);
                } else {
                    ImageUtils.loadFileImage(viewVH.m3DDetailsImage, "drawable://" + R.drawable.images_3d);
                    ImageUtils.loadFileImage(viewVH.m3DetailsImage, "drawable://" + R.drawable.roaming_figure);
                    viewVH.m3DetailsImage.setVisibility(View.VISIBLE);
                }
            } else {
                ImageLoader.getInstance().displayImage(imageLists.get(position), viewVH.m3DDetailsImage);
            }

        }

        //监听,跳转到图片放大页面:CaseLibraryDetailActivity
        viewVH.m3DDetailsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mType.equals("4")) {
                    String webUrl = imageLists.get(position);
                    if (!webUrl.equals("drawable")) {
                        Intent intent = new Intent(mContext, CaseLibraryRoamingWebView.class);
                        intent.putExtra("roaming", webUrl);
                        mContext.startActivity(intent);
                    }
                    // Toast.makeText(mContext, "漫游图", Toast.LENGTH_SHORT).show();

                } else {
                    Intent intent = new Intent(mContext, CaseLibraryDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.CaseLibraryDetail.CASE_DETAIL_BEAN, (Serializable) imageLists);
                    bundle.putInt(Constant.CaseLibraryDetail.CASE_DETAIL_POSTION, position);
                    bundle.putInt("moveState", 1);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return imageLists.size();
    }

    private class List3dViewVH extends RecyclerView.ViewHolder {
        public ImageView m3DDetailsImage;
        public ImageView m3DetailsImage;

        public List3dViewVH(View itemView) {
            super(itemView);
            m3DDetailsImage = (ImageView) itemView.findViewById(R.id.image_3d_photo);
            m3DetailsImage = (ImageView) itemView.findViewById(R.id.image_quanjing);
        }
    }
}
