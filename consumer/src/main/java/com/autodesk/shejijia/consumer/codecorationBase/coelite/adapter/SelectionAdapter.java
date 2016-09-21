package com.autodesk.shejijia.consumer.codecorationBase.coelite.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.codecorationBase.coelite.entity.SixProductsPicturesBean;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;

import java.util.List;

/**
 * Created by luchongbin on 16-8-17.
 */
public class SelectionAdapter extends PagerAdapter {
    private String [] pictures;
    private Activity mContext;

    public SelectionAdapter(Activity context, String [] pictures) {
        this.mContext = context;
        this.pictures = pictures;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }


    @Override
    public Object instantiateItem(View container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        String url = "";
        if (pictures != null&&pictures.length > 0) {
            url = pictures[position % pictures.length];
//            imageView = new ImageView(mContext);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            ImageUtils.displayIconImage("drawable://" + R.drawable.pic1_ico2x, imageView);
        }
//        else {
////            imageView = new ImageView(mContext);
////            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
////            SixProductsPicturesBean.InnerPicListBean innerPicListBean = mInnerPicListBeans.get(position % mInnerPicListBeans.size());
//            ImageUtils.loadCircleIcon(imageView, pictures[position]);
//        }
        ImageUtils.loadCircleIcon(imageView, url);
        ((ViewPager) container).addView(imageView);
        return imageView;
    }
}
