package com.autodesk.shejijia.consumer.home.decorationdesigners.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.SeekDesignerDetailBean;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;

import java.util.List;
import java.util.Map;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 2016/2/25 0025 16:32 .
 * @file SeekDesignerDetailAdapter  .
 * @brief 为SeekDesignerDetailActivity适配数据 .
 */
public class SeekDesignerDetailAdapter extends BaseAdapter {

    public SeekDesignerDetailAdapter(Context context, List<SeekDesignerDetailBean.CasesEntity> datas, Activity activity) {
        this.mDatas = datas;
        this.context = context;
        mRoom = AppJsonFileReader.getRoomHall(activity);
        mStyle = AppJsonFileReader.getStyle(activity);
        mArea = AppJsonFileReader.getArea(activity);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View container, ViewGroup parent) {

        ViewHolder holder = null;
        if (container == null){
            holder = new ViewHolder();
            container = LayoutInflater.from(context).inflate(R.layout.item_lv_seek_designer_detail,null);
            holder.mSeekCase = (ImageView) container.findViewById(R.id.img_seek_designer_detail_case);
//            viewHolder.mSeekAddress = (TextView) container.findViewById(R.id.img_seek_designer_detail_address);
            holder.mSeekLivingRoom = (TextView) container.findViewById(R.id.img_seek_designer_detail_living_room);
            holder.mSeekStyle = (TextView) container.findViewById(R.id.img_seek_designer_detail_style);
            holder.mSeekArea = (TextView) container.findViewById(R.id.img_seek_designer_detail_area);

            container.setTag(holder);
        }else {

            holder = (ViewHolder) container.getTag();

        }

        if (null != mDatas) {
            if (null != mDatas && mDatas.size() > 0) {
                SeekDesignerDetailBean.CasesEntity casesEntity = mDatas.get(position);
                List<SeekDesignerDetailBean.CasesEntity.ImagesEntity> images = casesEntity.getImages();
                if (images != null && images.size() > 0) {
                    for (int i = 0; i < casesEntity.getImages().size(); i++) {
                        SeekDesignerDetailBean.CasesEntity.ImagesEntity imagesEntity = casesEntity.getImages().get(i);
                        if (imagesEntity.isIs_primary()) {
                            imageOneUrl = imagesEntity.getFile_url();
                        }
                    }
                    if (TextUtils.isEmpty(imageOneUrl)) {
                        imageOneUrl = casesEntity.getImages().get(0).getFile_url();
                    }
                    ImageUtils.loadImage(holder.mSeekCase, imageOneUrl + Constant.CaseLibraryDetail.JPG);
                }
            } else {
                holder.mSeekCase.setImageResource(R.drawable.common_case_icon);
            }
//            if (null != mDatas.get(position).getTitle()) {
//                viewHolder.mSeekAddress.setText(mDatas.get(position).getTitle());
//            } else {
//                viewHolder.mSeekAddress.setText(R.string.temporarily_no_data);
//            }
            //TODO MERGE 825
//            if (null != mDatas.get(position).getTitle()) {
//                ((ViewHolder) holder).mSeekAddress.setText(mDatas.get(position).getTitle());
//            } else {
//                ((ViewHolder) holder).mSeekAddress.setText(R.string.str_others);
//            }
            if (null != mDatas.get(position).getRoom_type()) {
                String room_type = mDatas.get(position).getRoom_type();
                if (mRoom.containsKey(room_type)) {
                    ((ViewHolder) holder).mSeekLivingRoom.setText(mRoom.get(room_type));
                } else {
                    ((ViewHolder) holder).mSeekLivingRoom.setText(room_type);
                }
            } else {
                ((ViewHolder) holder).mSeekLivingRoom.setText(R.string.str_others);
            }
            if (null != mDatas.get(position).getProject_style()) {
                String project_style = mDatas.get(position).getProject_style();
                if (mStyle.containsKey(project_style)) {
                    ((ViewHolder) holder).mSeekStyle.setText(mStyle.get(project_style));
                } else {
                    ((ViewHolder) holder).mSeekStyle.setText(project_style);
                }
            } else {
                ((ViewHolder) holder).mSeekStyle.setText(R.string.str_others);
            }
            if (null != mDatas.get(position).getRoom_area()) {
                String room_area = mDatas.get(position).getRoom_area();
                if (mArea.containsKey(room_area)) {
                    ((ViewHolder) holder).mSeekArea.setText(mArea.get(room_area) + "m²");
                } else {
                    ((ViewHolder) holder).mSeekArea.setText(room_area + "m²");
                }
            } else {
                ((ViewHolder) holder).mSeekArea.setText(R.string.str_others);
            }
            if (null!=mDatas.get(position).getFavorite_count()){
                        ((ViewHolder) holder).tv_thumb_up.setText(mDatas.get(position).getFavorite_count()+"");
            }
        } else {
            ((ViewHolder) holder).mSeekCase.setImageResource(R.drawable.common_case_icon);
//<<<<<<< HEAD
//            ((ViewHolder) holder).mSeekAddress.setText(R.string.temporarily_no_data);
            ((ViewHolder) holder).mSeekLivingRoom.setText(R.string.temporarily_no_data);
            ((ViewHolder) holder).mSeekStyle.setText(R.string.temporarily_no_data);
            ((ViewHolder) holder).mSeekArea.setText(R.string.temporarily_no_data);
            //TODO MERGE 825
//=======
//            ((ViewHolder) holder).mSeekAddress.setText(R.string.str_others);
//            ((ViewHolder) holder).mSeekLivingRoom.setText(R.string.str_others);
//            ((ViewHolder) holder).mSeekStyle.setText(R.string.str_others);
//            ((ViewHolder) holder).mSeekArea.setText(R.string.str_others);
//>>>>>>> release/July/Android-UAT
        }
        ((ViewHolder) holder).mSeekCase.setOnClickListener(new MyOnClickListener(position, (ViewHolder) holder));

        return container;
    }

    //加载更多数据
    public void addMoreData(List<SeekDesignerDetailBean.CasesEntity> mDatas){

        this.mDatas = mDatas;
    }


    /// 监听接口.
    public interface OnItemCaseLibraryClickListener {
        void OnItemCaseLibraryClick(int position);
    }

    public void setOnItemCaseLibraryClickListener(OnItemCaseLibraryClickListener mOnItemCaseLibraryClickListener) {
        this.mOnItemCaseLibraryClickListener = mOnItemCaseLibraryClickListener;
    }

    class MyOnClickListener implements View.OnClickListener {
        private int position;
        private ViewHolder mViewHolder;

        public MyOnClickListener(int position, ViewHolder viewHolder) {
            this.position = position;
            this.mViewHolder = viewHolder;
        }

        /// 监听事件.
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_seek_designer_detail_case:
                    if (mOnItemCaseLibraryClickListener != null) {
                        mOnItemCaseLibraryClickListener.OnItemCaseLibraryClick(position);
                    }
                    break;
                default:
            }
        }
    }

    public class ViewHolder {
        private ImageView mSeekCase;
       // private TextView mSeekAddress;
        private TextView mSeekLivingRoom;
        private TextView mSeekStyle;
        private TextView mSeekArea;
        private TextView tv_thumb_up;

    }

    private OnItemCaseLibraryClickListener mOnItemCaseLibraryClickListener;
    private Map<String, String> mRoom;
    private Map<String, String> mStyle;
    private Map<String, String> mArea;
    private String imageOneUrl;
    private Context context;
    private List<SeekDesignerDetailBean.CasesEntity> mDatas;
}
