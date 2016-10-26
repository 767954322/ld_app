package com.autodesk.shejijia.consumer.personalcenter.recommend.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.base.adapter.CommonAdapter;
import com.autodesk.shejijia.consumer.base.adapter.CommonViewHolder;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendEntity;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;

import java.util.Date;
import java.util.List;

import static u.aly.av.T;

/**
 * Created by xueqiudong on 16-10-25.
 */

public class SelectProjectAdapter extends CommonAdapter<RecommendEntity.ItemsBean> {

    public SelectProjectAdapter(Context context, List<RecommendEntity.ItemsBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, RecommendEntity.ItemsBean itemsBean) {

        holder.setText(R.id.tv_project_name, itemsBean.getCity_name());

    }
}
