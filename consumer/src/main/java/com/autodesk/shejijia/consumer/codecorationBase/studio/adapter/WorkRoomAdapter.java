package com.autodesk.shejijia.consumer.codecorationBase.studio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.codecorationBase.studio.entity.WorkRoomListBeen;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;

import java.util.List;

/**
 * @author yaoxuehua .
 * @version 1.0 .
 * @date 2016/8/22 0025 14:46 .
 * @file WorkRoomAdapter  .
 * @brief 工作室的适配器 .
 */
public class WorkRoomAdapter extends BaseAdapter{


    private Context context;
    private List<WorkRoomListBeen.DesignerListBean> list;

    public WorkRoomAdapter(Context context, List<WorkRoomListBeen.DesignerListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder viewHolder;

        if (convertView == null){

            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_work_room,null);
            viewHolder.workRoomImageView = (ImageView) convertView.findViewById(R.id.work_room_imageView);
            viewHolder.workRoomName = (TextView) convertView.findViewById(R.id.work_room_name);
            convertView.setTag(viewHolder);
        }else {

            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.workRoomName.setText(list.get(position).getNick_name());//工作室名字
        String sss = list.get(position).getDesigner().getDesigner_profile_cover().getPublic_url().replace(" ","");
        ImageUtils.loadImage(viewHolder.workRoomImageView, sss/*list.get(position).getDesigner().getDesigner_profile_cover().getPublic_url()*/);



        return convertView;
    }

    class ViewHolder{

        private ImageView workRoomImageView;
        private TextView workRoomName;

    }

    public void addMoreData(List<WorkRoomListBeen.DesignerListBean> list){

        this.list = list;
        notifyDataSetChanged();
    }
}
