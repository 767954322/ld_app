package com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.framework.adapter.BaseAdapter;

import java.util.List;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 2016/2/23 0023 16:05 .
 * @file FiltrateAdapter  .
 * @brief 筛选页面 .
 */
public class FiltrateAdapter extends BaseAdapter {

    public FiltrateAdapter(Context context, List datas) {
        super(context, datas);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_gv_filtrate_style;
    }

    @Override
    public Holder initHolder(View container) {
        viewHolder = new ViewHolder();
        viewHolder.btn_filtrate = (Button) container.findViewById(R.id.btn_filtrate_style);
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
        ((ViewHolder) holder).btn_filtrate.setText(mDatas.get(position).toString());
    }

    /// 选中,并更新.
    public void setSelection(int position) {
        clickTemp = position;
        notifyDataSetChanged();
    }

    public class ViewHolder extends BaseAdapter.Holder {
        Button btn_filtrate;
    }

    private int clickTemp = 0;
    private ViewHolder viewHolder;

}
