package com.autodesk.shejijia.consumer.personalcenter.recommend.adapter;

import android.content.Context;
import android.view.View;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.base.adapter.CommonAdapter;
import com.autodesk.shejijia.consumer.base.adapter.CommonViewHolder;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.ShowBrandsBean;

import java.util.List;

/**
 * Created by yaoxuehua on 16-10-26.
 */

public class AddBrandShowAdapter extends CommonAdapter<ShowBrandsBean.BrandsBean> {



    public AddBrandShowAdapter(Context context, List<ShowBrandsBean.BrandsBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(final CommonViewHolder holder, ShowBrandsBean.BrandsBean brandsBean) {

        holder.setText(R.id.brand_name,brandsBean.getBrand_name());
        String storeName = "";
        for (int i=0;i<brandsBean.getMalls().size();i++){

            storeName  = storeName +brandsBean.getMalls().get(i).getMall_name()+"  ";
        }

        holder.setText(R.id.store_dreass,storeName);
        holder.setBackgroundRes(R.id.checked_img,R.drawable.brand_unchecked);
        holder.setOnClickListener(R.id.checked_img, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.setBackgroundRes(R.id.checked_img,R.drawable.brand_checked);
            }
        });
    }

}
