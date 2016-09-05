package com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.activity.CaseLibraryDetailActivity;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.Case3DDetailBean;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;

import java.util.ArrayList;

public class List3DLibraryAdapter extends BaseAdapter {

    private ArrayList<Case3DDetailBean> list;
    private Context mContext;
    private int index = 0;
    private LayoutInflater inflater;
    View view[] = null;
    private Case3DDetailBean dto;

    public List3DLibraryAdapter(Context context, ArrayList<Case3DDetailBean> list) {
        mContext = context;
        this.list = list;
        inflater = LayoutInflater.from(mContext);
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        dto = list.get(position);

        //if(convertView == null) {
        convertView = inflater.inflate(R.layout.activity_case_library_detail_3d_item, null);
        //}
        TextView nameTV = (TextView) convertView.findViewById(R.id.nameTV);
        LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.layout_photo);



        if (dto.getDesign_file() != null&& dto.getDesign_file().size() >0) {

            if (dto.getDesign_file().get(position).getType().equals("4")){
                nameTV.setText("漫游图");
            }else if (dto.getDesign_file().get(position).getType().equals("")){
                nameTV.setText("渲染图");
            }else if (dto.getDesign_file().get(position).getType().equals("10")){
                nameTV.setText("户型图");
            }

            for (int i = 0; i < dto.getDesign_file().size(); i++) {

                view = new View[dto.getDesign_file().size()];
                view[i] = inflater.inflate(R.layout.dynamic_add_3d_view, null);

                ImageView img = (ImageView) view[i].findViewById(R.id.image_3d_photo);
                ImageUtils.loadImage(img, dto.getDesign_file().get(i).getLink() + "HD.png");
                index = i;
                view[i].setId(index);
                view[i].setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {

                        Intent intent = new Intent(mContext, CaseLibraryDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constant.CaseLibraryDetail.CASE_DETAIL_BEAN, dto);
                        bundle.putInt(Constant.CaseLibraryDetail.CASE_DETAIL_POSTION, position + 1);
                        bundle.putInt("moveState",1);
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);
                    }
                });
                layout.addView(view[i]);
            }
        }
        return convertView;
    }
}
