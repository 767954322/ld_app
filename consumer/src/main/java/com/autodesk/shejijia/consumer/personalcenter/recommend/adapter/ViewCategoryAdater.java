package com.autodesk.shejijia.consumer.personalcenter.recommend.adapter;

import android.content.Context;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter.BaseCommonRvAdapter;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendSCFDBean;

import java.util.List;

/**
 * @User: 蜡笔小新
 * @date: 16-11-7
 * @GitHub: https://github.com/meikoz
 */

public class ViewCategoryAdater extends BaseCommonRvAdapter<RecommendSCFDBean> {

    private int mPosition;

    public ViewCategoryAdater(Context context, int layoutId, List<RecommendSCFDBean> datas, int position) {
        super(context, layoutId, datas);
        this.mPosition = position;
    }

    @Override
    public void convert(ViewHolder holder, RecommendSCFDBean item, int position) {
        TextView tv_category_name = holder.getView(R.id.tv_category_name);
        tv_category_name.setBackgroundResource(mPosition == position ? R.drawable.store_bg_btn_checked : R.drawable.store_bg_btn);
        tv_category_name.setText(item.getSub_category_3d_name());
    }
}
