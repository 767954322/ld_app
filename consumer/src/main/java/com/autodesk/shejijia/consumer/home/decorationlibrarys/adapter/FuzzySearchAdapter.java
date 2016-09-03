package com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.SearchHoverCaseBean;

import java.util.List;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 2016/2/23 0023 16:05 .
 * @file FuzzySearchAdapter  .
 * @brief 查询页面适配器 .
 */
public class FuzzySearchAdapter extends BaseAdapter {

    public FuzzySearchAdapter(Context context, List<SearchHoverCaseBean> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View convertView = null;
        ViewHolder holder;
        if (convertView == null || convertView.getTag() == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_layout, null);
            holder = new ViewHolder();
            holder.tv_data = (TextView) convertView.findViewById(R.id.tv);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_data.setText(items.get(position).getValue());
        convertView.setTag(items.get(position));
        return convertView;
    }

    static class ViewHolder {
        public TextView tv_data;
    }

    private List<SearchHoverCaseBean> items;
    private Context context;
}
