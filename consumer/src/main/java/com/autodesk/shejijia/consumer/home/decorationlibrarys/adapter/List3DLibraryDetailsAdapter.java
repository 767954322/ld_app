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
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.util.List;

/**
 * Created by t_xuz on 9/6/16.
 * 3d 详情显示图片列表的 adapter
 */
public class List3DLibraryDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<String> imageLists;
    private int resId;
    private Context mContext;

    public List3DLibraryDetailsAdapter(List<String> imageLists, int resId, Context mContext){
        this.imageLists = imageLists;
        this.resId = resId;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resId,parent,false);
        return new List3dViewVH(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        List3dViewVH viewVH = (List3dViewVH) holder;
        if (!TextUtils.isEmpty(imageLists.get(position))){
            ImageLoader.getInstance().displayImage(imageLists.get(position),viewVH.m3DDetailsImage);
        }

        //监听,跳转到图片放大页面:CaseLibraryDetailActivity
        viewVH.m3DDetailsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CaseLibraryDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.CaseLibraryDetail.CASE_DETAIL_BEAN, (Serializable) imageLists);
                bundle.putInt(Constant.CaseLibraryDetail.CASE_DETAIL_POSTION, position);
                bundle.putInt("moveState",1);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageLists.size();
    }

    private class List3dViewVH extends RecyclerView.ViewHolder{
        public ImageView m3DDetailsImage;

        public List3dViewVH(View itemView) {
            super(itemView);
            m3DDetailsImage = (ImageView)itemView.findViewById(R.id.image_3d_photo);
        }
    }
}
