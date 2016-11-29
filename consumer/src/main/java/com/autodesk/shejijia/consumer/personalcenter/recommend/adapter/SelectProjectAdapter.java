package com.autodesk.shejijia.consumer.personalcenter.recommend.adapter;

import android.content.Context;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.base.adapter.CommonAdapter;
import com.autodesk.shejijia.consumer.base.adapter.CommonViewHolder;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.SelectProjectEntity;

import java.util.List;


/**
 * Created by xueqiudong on 16-10-25.
 */

public class SelectProjectAdapter extends CommonAdapter<SelectProjectEntity.DesignerProjectsBean> {
     private Context context;

    public SelectProjectAdapter(Context context, List<SelectProjectEntity.DesignerProjectsBean> datas) {
        super(context, datas, R.layout.select_check_textview);
        this.context = context;
    }

    @Override
    public void convert(CommonViewHolder holder, SelectProjectEntity.DesignerProjectsBean designerProjectsBean) {
        holder.setText(R.id.ctv_select, designerProjectsBean.getCommunity_name());
    }
}
