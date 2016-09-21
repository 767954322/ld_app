package com.autodesk.shejijia.consumer.home.decorationdesigners.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.Case3DBeen;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by yaoxuehua on 16-9-6.
 */
public class SeekDesigner3DCaseAdapter extends BaseAdapter {

    private List<Case3DBeen.CasesBean> datas;
    private Context context;
    private Map<String, String> styleJson;
    private Map<String, String> RoomHall;

    public SeekDesigner3DCaseAdapter(Context context, List datas) {
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

        List<Case3DBeen.CasesBean.DesignerFileBeen> design_file = datas.get(position).getDesign_file();


        if (design_file != null && design_file.size() != 0) {

            if (design_file.get(0).getLink() != null) {

                String urll = design_file.get(0).getLink()+"HD.jpg";
                ImageUtils.loadImageIcon(viewHolder.item_3D_case_img,urll);
            }
        }else {
            ImageUtils.loadImageIcon(viewHolder.item_3D_case_img,"");
        }

        styleJson = AppJsonFileReader.getStyle((Activity) context);



        if (datas.get(position).getCustom_string_style() != null){

            //将风格转换成汉字
            if (styleJson.containsKey(datas.get(position).getCustom_string_style())){

                viewHolder.style.setText(styleJson.get(datas.get(position).getCustom_string_style()));

            }
        }else {

            viewHolder.style.setText("其他");
        }
        if (datas.get(position).getCustom_string_type() != null){

            //获取户型，英文转汉字
            RoomHall = AppJsonFileReader.getRoomHall((Activity) context);
            if (RoomHall.containsKey(datas.get(position).getCustom_string_type())){

                viewHolder.hall.setText(RoomHall.get(datas.get(position).getCustom_string_type()));

            }
        }else {

            viewHolder.hall.setText("其他");
        }
        if (datas.get(position).getCustom_string_area() != null){

            viewHolder.room_area.setText(datas.get(position).getRoom_area()+"㎡");
        }else {
            viewHolder.room_area.setText("0m²");

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

    }
}
