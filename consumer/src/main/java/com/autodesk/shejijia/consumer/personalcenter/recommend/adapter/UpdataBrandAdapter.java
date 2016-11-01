package com.autodesk.shejijia.consumer.personalcenter.recommend.adapter;

import android.content.Context;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.base.adapter.CommonAdapter;
import com.autodesk.shejijia.consumer.base.adapter.CommonViewHolder;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.ShowBrandsBean;

import java.util.List;

/**
 * Created by luchongbin on 16-11-1.
 */

public class UpdataBrandAdapter extends CommonAdapter<ShowBrandsBean.BrandsBean> {

    public UpdataBrandAdapter(Context context, List<ShowBrandsBean.BrandsBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }
    @Override
    public void convert(CommonViewHolder holder, ShowBrandsBean.BrandsBean brandsBean) {
        holder.setText(R.id.ctv_select, brandsBean.getBrand_name());
    }
}
