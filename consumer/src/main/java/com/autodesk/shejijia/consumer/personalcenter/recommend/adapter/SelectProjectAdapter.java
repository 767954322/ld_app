package com.autodesk.shejijia.consumer.personalcenter.recommend.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckedTextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.base.adapter.CommonAdapter;
import com.autodesk.shejijia.consumer.base.adapter.CommonViewHolder;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendEntity;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.SelectProjectEntity;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

import java.util.List;


/**
 * Created by xueqiudong on 16-10-25.
 */

public class SelectProjectAdapter extends CommonAdapter<SelectProjectEntity.DesignerProjectsBean> {


    public SelectProjectAdapter(Context context, List<SelectProjectEntity.DesignerProjectsBean> datas) {
        super(context, datas, android.R.layout.simple_list_item_single_choice);

    }


    @Override
    public void convert(CommonViewHolder holder, SelectProjectEntity.DesignerProjectsBean designerProjectsBean) {
        holder.setText(android.R.id.text1, designerProjectsBean.getCommunity_name());
        CheckedTextView view = holder.getView(android.R.id.text1);
        view.setTextSize(13);
        view.setTextColor(UIUtils.getColor(R.color.text_item_name));

    }
}
