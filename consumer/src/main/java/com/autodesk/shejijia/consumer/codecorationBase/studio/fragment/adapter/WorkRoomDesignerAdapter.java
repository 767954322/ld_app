package com.autodesk.shejijia.consumer.codecorationBase.studio.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.codecorationBase.studio.fragment.entity.WorkRoomDetailsBeen;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;

import java.util.List;

/**
 * @author yaoxuehua .
 * @version 1.0 .
 * @date 2016/8/22 0025 14:46 .
 * @file WorkRoomAdapter  .
 * @brief 工作室主设计师的适配器 .
 */
public class WorkRoomDesignerAdapter extends BaseAdapter{


    private List<WorkRoomDetailsBeen.MainDesignersBean[]> list;
    private List<WorkRoomDetailsBeen.CasesListBean> casesBeenList;
    private Context context;

    public WorkRoomDesignerAdapter(Context context, List<WorkRoomDetailsBeen.MainDesignersBean[]> list,List<WorkRoomDetailsBeen.CasesListBean> casesBeenList) {
        this.context = context;
        this.list = list;
        this.casesBeenList = casesBeenList;
    }

    @Override
    public int getCount() {
        return list.size()+casesBeenList.size();
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
            if (position < casesBeenList.size()){//案例
                convertView = LayoutInflater.from(context).inflate(R.layout.item_work_room_case_list,null);
                viewHolder.caseImageView = (ImageView) convertView.findViewById(R.id.work_room_design_imageView);

            }else if (position == casesBeenList.size()){

                convertView = LayoutInflater.from(context).inflate(R.layout.item_first_work_room_designer,null);
                viewHolder.workRoomImageViewOne = (ImageView) convertView.findViewById(R.id.work_room_design_logo_one);
                viewHolder.workRoomNameOne = (TextView) convertView.findViewById(R.id.tv_designer_name);
                viewHolder.llWorkRoomOne = (LinearLayout) convertView.findViewById(R.id.ll_work_room_one);

                viewHolder.workRoomImageViewTwo = (ImageView) convertView.findViewById(R.id.work_room_design_logo_two);
                viewHolder.workRoomNameTwo = (TextView) convertView.findViewById(R.id.tv_designer_name_two);
                viewHolder.llWorkRoomTwo = (LinearLayout) convertView.findViewById(R.id.ll_work_room_two);

                viewHolder.workRoomImageViewThree = (ImageView) convertView.findViewById(R.id.work_room_design_logo_three);
                viewHolder.workRoomNameThree = (TextView) convertView.findViewById(R.id.tv_designer_name_three);
                viewHolder.llWorkRoomThree = (LinearLayout) convertView.findViewById(R.id.ll_work_room_three);

                viewHolder.space_one = (TextView) convertView.findViewById(R.id.space_one);
                viewHolder.space_two = (TextView) convertView.findViewById(R.id.space_two);
            }else {

                convertView = LayoutInflater.from(context).inflate(R.layout.item_gridview_work_room,null);
                viewHolder.workRoomImageViewOne = (ImageView) convertView.findViewById(R.id.work_room_design_logo_one);
                viewHolder.workRoomNameOne = (TextView) convertView.findViewById(R.id.tv_designer_name);
                viewHolder.llWorkRoomOne = (LinearLayout) convertView.findViewById(R.id.ll_work_room_one);

                viewHolder.workRoomImageViewTwo = (ImageView) convertView.findViewById(R.id.work_room_design_logo_two);
                viewHolder.workRoomNameTwo = (TextView) convertView.findViewById(R.id.tv_designer_name_two);
                viewHolder.llWorkRoomTwo = (LinearLayout) convertView.findViewById(R.id.ll_work_room_two);

                viewHolder.workRoomImageViewThree = (ImageView) convertView.findViewById(R.id.work_room_design_logo_three);
                viewHolder.workRoomNameThree = (TextView) convertView.findViewById(R.id.tv_designer_name_three);
                viewHolder.llWorkRoomThree = (LinearLayout) convertView.findViewById(R.id.ll_work_room_three);

                viewHolder.space_one = (TextView) convertView.findViewById(R.id.space_one);
                viewHolder.space_two = (TextView) convertView.findViewById(R.id.space_two);

            }


            convertView.setTag(viewHolder);
        }else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position < casesBeenList.size()){
            viewHolder.caseImageView = (ImageView) convertView.findViewById(R.id.work_room_design_imageView);
            if (casesBeenList.get(position).getImages() != null && casesBeenList.get(position).getImages().size() != 0){

                ImageUtils.loadImage(viewHolder.caseImageView, casesBeenList.get(position).getImages().get(0).getFile_url()+"HD.jpg");
            }
        }else if (position == casesBeenList.size()){
            if (list.get(position - casesBeenList.size()).length == 1 ){

                viewHolder.workRoomNameOne.setText(list.get(position - casesBeenList.size())[0].getName());
                ImageUtils.loadImage(viewHolder.workRoomImageViewOne, list.get(position - casesBeenList.size())[0].getAvatar());
                viewHolder.llWorkRoomTwo.setVisibility(View.GONE);
                viewHolder.llWorkRoomThree.setVisibility(View.GONE);
                viewHolder.space_one.setVisibility(View.GONE);
                viewHolder.space_two.setVisibility(View.GONE);
            }
            if (list.get(position - casesBeenList.size()).length == 2){

                viewHolder.workRoomNameOne.setText(list.get(position - casesBeenList.size())[0].getName());
                ImageUtils.loadImage(viewHolder.workRoomImageViewOne, list.get(position - casesBeenList.size())[0].getAvatar());
                viewHolder.workRoomNameTwo.setText(list.get(position - casesBeenList.size())[1].getName());
                ImageUtils.loadImage(viewHolder.workRoomImageViewTwo, list.get(position - casesBeenList.size())[1].getAvatar());
                viewHolder.llWorkRoomThree.setVisibility(View.GONE);
            }
            if (list.get(position - casesBeenList.size()).length == 3){

                viewHolder.workRoomNameOne.setText(list.get(position - casesBeenList.size())[0].getName());
                ImageUtils.loadImage(viewHolder.workRoomImageViewOne, list.get(position - casesBeenList.size())[0].getAvatar());
                viewHolder.workRoomNameTwo.setText(list.get(position - casesBeenList.size())[1].getName());
                ImageUtils.loadImage(viewHolder.workRoomImageViewTwo, list.get(position - casesBeenList.size())[1].getAvatar());
                viewHolder.workRoomNameThree.setText(list.get(position - casesBeenList.size())[2].getName());
                ImageUtils.loadImage(viewHolder.workRoomImageViewThree, list.get(position - casesBeenList.size())[2].getAvatar());
                viewHolder.space_one.setVisibility(View.VISIBLE);
                viewHolder.space_two.setVisibility(View.VISIBLE);
            }
        }else {

            if (list.get(position - casesBeenList.size()).length == 1 ){

                viewHolder.workRoomNameOne.setText(list.get(position - casesBeenList.size())[0].getName());
                ImageUtils.loadImage(viewHolder.workRoomImageViewOne, list.get(position - casesBeenList.size())[0].getAvatar());
                viewHolder.llWorkRoomTwo.setVisibility(View.GONE);
                viewHolder.llWorkRoomThree.setVisibility(View.GONE);
                viewHolder.space_one.setVisibility(View.GONE);
                viewHolder.space_two.setVisibility(View.GONE);
            }
            if (list.get(position - casesBeenList.size()).length == 2){

                viewHolder.workRoomNameOne.setText(list.get(position - casesBeenList.size())[0].getName());
                ImageUtils.loadImage(viewHolder.workRoomImageViewOne, list.get(position - casesBeenList.size())[0].getAvatar());
                viewHolder.workRoomNameTwo.setText(list.get(position - casesBeenList.size())[1].getName());
                ImageUtils.loadImage(viewHolder.workRoomImageViewTwo, list.get(position - casesBeenList.size())[1].getAvatar());
                viewHolder.llWorkRoomThree.setVisibility(View.GONE);
            }
            if (list.get(position - casesBeenList.size()).length == 3){

                viewHolder.workRoomNameOne.setText(list.get(position - casesBeenList.size())[0].getName());
                ImageUtils.loadImage(viewHolder.workRoomImageViewOne, list.get(position - casesBeenList.size())[0].getAvatar());
                viewHolder.workRoomNameTwo.setText(list.get(position - casesBeenList.size())[1].getName());
                ImageUtils.loadImage(viewHolder.workRoomImageViewTwo, list.get(position - casesBeenList.size())[1].getAvatar());
                viewHolder.workRoomNameThree.setText(list.get(position - casesBeenList.size())[2].getName());
                ImageUtils.loadImage(viewHolder.workRoomImageViewThree, list.get(position - casesBeenList.size())[2].getAvatar());
                viewHolder.space_one.setVisibility(View.VISIBLE);
                viewHolder.space_two.setVisibility(View.VISIBLE);
            }
        }


        return convertView;
    }


    class ViewHolder{

        private ImageView workRoomImageViewOne;
        private TextView workRoomNameOne;
        private ImageView workRoomImageViewTwo;
        private TextView workRoomNameTwo;
        private ImageView workRoomImageViewThree;
        private TextView workRoomNameThree;
        private LinearLayout llWorkRoomTwo;
        private LinearLayout llWorkRoomOne;
        private LinearLayout llWorkRoomThree;
        private ImageView caseImageView;
        private TextView space_one,space_two;


    }
}
