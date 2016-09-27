package com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.DesignerWorkTimeBean;
import com.autodesk.shejijia.consumer.base.adapter.BaseAdapter;

import java.util.List;

/**
 * @author xueqiudong .
 * @version 1.0 .
 * @date 2016/8/15 0023 11:05 .
 * @file FiltrateWorkYearAdapter  .
 * @brief 筛选工作年限Adapter .
 */
public class FiltrateWorkYearAdapter extends BaseAdapter<DesignerWorkTimeBean.RelateInformationListBean> {

    public FiltrateWorkYearAdapter(Context context, List<DesignerWorkTimeBean.RelateInformationListBean> datas) {
        super(context, datas);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_gv_filtrate_style;
    }

    @Override
    public Holder initHolder(View container) {
        viewHolder = new ViewHolder();
        viewHolder.btn_filtrate = (TextView) container.findViewById(R.id.btn_filtrate_style);
        return viewHolder;
    }

    @Override
    public void initItem(View view, Holder holder, int position) {
        if (clickTemp != -1) {
            if (clickTemp == position) {
                ((ViewHolder) holder).btn_filtrate.setBackgroundResource(R.drawable.bg_btn_filtrate_pressed);
            } else {
                ((ViewHolder) holder).btn_filtrate.setBackgroundResource(R.drawable.bg_btn_filtrate_normal);
            }
        }
        ((ViewHolder) holder).btn_filtrate.setText(mDatas.get(position).getName());
    }

    /// 选中,并更新.
    public void setWorkYearSelection(int position) {
        clickTemp = position;
        notifyDataSetChanged();
    }

    public class ViewHolder extends BaseAdapter.Holder {
        TextView btn_filtrate;
    }

    private int clickTemp = 0;
    private ViewHolder viewHolder;

}
