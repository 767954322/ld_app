package com.autodesk.shejijia.consumer.personalcenter.recommend.adapter;

import android.content.Context;
import android.view.View;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.base.adapter.BaseAdapter;
import com.autodesk.shejijia.consumer.base.adapter.CommonAdapter;
import com.autodesk.shejijia.consumer.base.adapter.CommonViewHolder;

import java.util.List;

/**
 * @User: 蜡笔小新
 * @date: 16-10-21
 * @GitHub: https://github.com/meikoz
 */

public class RecommendAdapter extends CommonAdapter<String> {
    private boolean isDesiner = false;

    public RecommendAdapter(Context context, List<String> datas, int layoutId, boolean isDesiner) {
        super(context, datas, layoutId);
        this.isDesiner = isDesiner;
    }

    @Override
    public void convert(CommonViewHolder holder, String s) {
        holder.setVisible(R.id.tv_cancel_btn, isDesiner);
        holder.setText(R.id.tv_edit_btn, "删除");
        holder.setText(R.id.tv_recommend_name, s);
    }
}
