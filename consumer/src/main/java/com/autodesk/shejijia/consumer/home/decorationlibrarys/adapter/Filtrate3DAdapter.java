package com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.base.adapter.BaseAdapter;

import java.util.List;


/**
 * @author xueqiudong .
 * @version 1.0 .
 * @date 2016/8/15 0023 11:05 .
 * @file FiltrateCostAdapter  .
 * @brief 筛选Adapter .
 */
public class Filtrate3DAdapter extends BaseAdapter {

    public Filtrate3DAdapter(Context context, List datas) {
        super(context, datas);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_gv_3d_filtrate_style;
    }

    @Override
    public Holder initHolder(View container) {
        viewHolder = new ViewHolder();
        //fix button settext 小写变大写的问题
        viewHolder.btn_filtrate = (TextView) container.findViewById(R.id.btn_filtrate_style);
        return viewHolder;
    }

    @Override
    public void initItem(View view, Holder holder, int position) {
        if (clickTemp != -1) {
            if (clickTemp == position) {
                ((ViewHolder) holder).btn_filtrate.setBackgroundResource(R.drawable.bg_btn_filtrate_pressed);
                ((ViewHolder) holder).btn_filtrate.setTextColor(mContext.getResources().getColor(R.color.tx_ef));
            } else {
                ((ViewHolder) holder).btn_filtrate.setTextColor(mContext.getResources().getColor(R.color.bg_33));
                ((ViewHolder) holder).btn_filtrate.setBackgroundResource(R.drawable.bg_btn_filtrate_normal);
            }
        }
        if (mDatas.get(position).toString().contains("㎡")) {
            ((ViewHolder) holder).btn_filtrate.setText(mDatas.get(position).toString().replace("㎡", "m²"));
        } else {
            ((ViewHolder) holder).btn_filtrate.setText(mDatas.get(position).toString());
        }

    }

    /// 选中,并更新.
    public void setSelection(int position) {
        clickTemp = position;
        notifyDataSetChanged();
    }

    public class ViewHolder extends Holder {
        TextView btn_filtrate;
    }

    private int clickTemp = 0;
    private ViewHolder viewHolder;

}
