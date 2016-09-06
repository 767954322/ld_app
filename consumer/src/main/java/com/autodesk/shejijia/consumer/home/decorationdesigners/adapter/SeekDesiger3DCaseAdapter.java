package com.autodesk.shejijia.consumer.home.decorationdesigners.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.Case3DBeen;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;

import java.util.List;

/**
 * Created by yaoxuehua on 16-9-6.
 */
public class SeekDesiger3DCaseAdapter extends BaseAdapter {

    private List<Case3DBeen.CasesBean> datas;
    private Context context;

    public SeekDesiger3DCaseAdapter(Context context, List datas) {
        this.context = context;
        this.datas = datas;
    }


    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_three_d_case, null);
            viewHolder.item_3D_case_img = (ImageView) convertView.findViewById(R.id.item_3D_case_img);
            viewHolder.style = (TextView) convertView.findViewById(R.id.style);
            viewHolder.hall = (TextView) convertView.findViewById(R.id.hall);
            viewHolder.room_area = (TextView) convertView.findViewById(R.id.room_area);
            viewHolder.favorite_count = (TextView) convertView.findViewById(R.id.favorite_count);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (datas.get(position).getDesign_file() != null && datas.get(position).getDesign_file().size() != 0) {

            if (datas.get(position).getDesign_file().get(0).getLink() != null) {

                ImageUtils.displayAvatarImage(datas.get(position).getDesign_file().get(0).getLink(), viewHolder.item_3D_case_img);
            }
        }

        if (datas.get(position).getCustom_string_style() != null){

            viewHolder.style.setText(datas.get(position).getCustom_string_style());
        }
        if (datas.get(position).getCustom_string_type() != null){

            viewHolder.hall.setText(datas.get(position).getCustom_string_type());
        }
        if (datas.get(position).getCustom_string_area() != null){

            viewHolder.room_area.setText(datas.get(position).getCustom_string_area());
        }

        viewHolder.favorite_count.setText(datas.get(position).getFavorite_count()+"");
        return convertView;
    }


    public class ViewHolder {

        private ImageView item_3D_case_img;
        private TextView style;
        private TextView hall;
        private TextView room_area;
        private TextView favorite_count;
        private ImageView favorite_count_img;
    }

    public void addMoreData(List<Case3DBeen.CasesBean> datas){

        this.datas = datas;
        notifyDataSetChanged();

    }
}
