package com.autodesk.shejijia.consumer.home.decorationdesigners.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.AppraiseDesignBeen;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * @author yaoxuehua .
 * @version 1.0 .
 * @date on 16-8-11
 * @file AppraiseDesignBeen  .
 * @brief 查看设计师appraiseFragment评价的数据been.
 */
public class SeekDesignerAppraiseAdapter extends BaseAdapter {

    private Context context;

    private List<AppraiseDesignBeen.EstimatesBean> mDatas;

    public SeekDesignerAppraiseAdapter(Context context, List<AppraiseDesignBeen.EstimatesBean> datas) {


        this.context = context;
        this.mDatas = datas;
    }

    public SeekDesignerAppraiseAdapter(Context context, List<AppraiseDesignBeen.EstimatesBean> datas, ImageLoader imageLoader) {

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

        ViewHolder viewHolder = null;
        if (container == null) {

            viewHolder = new ViewHolder();
            container = LayoutInflater.from(context).inflate(R.layout.item_appraise_layout, null);
            viewHolder.mTvName = (TextView) container.findViewById(R.id.tv_name);
            viewHolder.mTvTime = (TextView) container.findViewById(R.id.tv_time);
            viewHolder.mTvAppraiseContent = (TextView) container.findViewById(R.id.consumer_appraise_content);
            viewHolder.rating_star = (RatingBar) container.findViewById(R.id.rating_star);
            viewHolder.mImageViewAvatar = (ImageView) container.findViewById(R.id.piv_designer_avatar);

            container.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) container.getTag();

        }

        if (null != mDatas) {

            String name = mDatas.get(position).getMember_name();
            if (!TextUtils.isEmpty(name)) {

                ((ViewHolder) viewHolder).mTvName.setText(name);
            }

            String content = mDatas.get(position).getMember_estimate();
            if (!TextUtils.isEmpty(content)) {

                ((ViewHolder) viewHolder).mTvAppraiseContent.setText(content);
            }

            String time = mDatas.get(position).getEstimate_date();
            if (!TextUtils.isEmpty(time)) {

                ((ViewHolder) viewHolder).mTvTime.setText(time);
            }

            int grade = mDatas.get(position).getMember_grade();
            if (!TextUtils.isEmpty(grade + "")) {

                ((ViewHolder) viewHolder).rating_star.setRating(grade);
            }

            String avatar = mDatas.get(position).getAvatar();
            if (!TextUtils.isEmpty(avatar)) {
                ImageUtils.displayAvatarImage(avatar, ((ViewHolder) viewHolder).mImageViewAvatar);
            }


        }


        return container;
    }

    public class ViewHolder {
        public TextView mTvName;
        public TextView mTvTime;
        public TextView mTvAppraiseContent;
        public ImageView mImageViewAvatar;
        public RatingBar rating_star;
    }

    public void addMoreData(List<AppraiseDesignBeen.EstimatesBean> moreData) {

        this.mDatas = moreData;
        notifyDataSetChanged();
    }

}
