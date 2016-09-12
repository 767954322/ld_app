package com.autodesk.shejijia.consumer.codecorationBase.coelite.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.codecorationBase.coelite.entity.DesignWorksBean;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;

import java.util.List;

/**
 * Created by luchongbin on 16-8-17.
 */
public class SelectionAdapter extends PagerAdapter {
    private List<DesignWorksBean.InnerPicListBean> mInnerPicListBeans;
    private Activity mContext;

    public SelectionAdapter(Activity context, List<DesignWorksBean.InnerPicListBean> innerPicListBeans) {
        this.mContext = context;
        this.mInnerPicListBeans = innerPicListBeans;
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
        ImageView imageView;
        if (mInnerPicListBeans == null) {
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ImageUtils.displayIconImage("drawable://" + R.drawable.pic1_ico2x, imageView);
        } else {
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            DesignWorksBean.InnerPicListBean innerPicListBean = mInnerPicListBeans.get(position % mInnerPicListBeans.size());
            ImageUtils.loadImageIcon(imageView, innerPicListBean.getAndroid());
        }

        ((ViewPager) container).addView(imageView);
        return imageView;
    }
}
