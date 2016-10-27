package com.autodesk.shejijia.consumer.personalcenter.recommend.adapter;

import android.content.Context;
import android.view.View;
import android.widget.RadioButton;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.base.adapter.CommonAdapter;
import com.autodesk.shejijia.consumer.base.adapter.CommonViewHolder;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendEntity;

import java.util.HashMap;
import java.util.List;


/**
 * Created by xueqiudong on 16-10-25.
 */

public class SelectProjectAdapter extends CommonAdapter<RecommendEntity.ItemsBean> {
    HashMap<String, Boolean> states = new HashMap<String, Boolean>();//用于记录每个RadioButton的状态，并保证只可选一个

    public SelectProjectAdapter(Context context, List<RecommendEntity.ItemsBean> datas, int layoutId) {
        super(context, datas, layoutId);

    }

    @Override
    public void convert(final CommonViewHolder holder, RecommendEntity.ItemsBean itemsBean) {

        holder.setText(R.id.tv_project_name, itemsBean.getCommunity_name());
        final RadioButton radioButton = holder.getView(R.id.cb_select_project);

        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //重置，确保最多只有一项被选中
                for (String key : states.keySet()) {
                    states.put(key, false);

                }
                states.put(String.valueOf(holder.getPosition()), radioButton.isChecked());
                SelectProjectAdapter.this.notifyDataSetChanged();
            }
        });

        boolean res = false;
        if (states.get(String.valueOf(holder.getPosition())) == null || states.get(String.valueOf(holder.getPosition())) == false) {
            res = false;
            states.put(String.valueOf(holder.getPosition()), false);
        } else
            res = true;

        radioButton.setChecked(res);

    }
}
