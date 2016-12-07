package com.autodesk.shejijia.shared.components.common.uielements.commentview.comment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.uielements.photoview.PhotoView;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_panya on 16/12/6.
 */

public class CommentPreviewActivity extends AppCompatActivity {
    private static final String sFilePrefix = "file://";
    private ArrayList<String> mPictureData;

    private ViewPager mViewPager;

    private int mCurrentPosition;

    private ImageDetailAdapter mPagerAdapter;

    private TextView mIndicatorText;

    private ImageLoader mImageLoader;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_comment_image_preview);
        mCurrentPosition = getIntent().getIntExtra(CommentFragment.POSITION,0);
        mPictureData = getIntent().getStringArrayListExtra(CommentFragment.STRING_LIST);
        mImageLoader = ImageLoader.getInstance();
        mViewPager = (ViewPager) findViewById(R.id.vp_comment_preview);
        mIndicatorText = (TextView) findViewById(R.id.tv_photo_range);
        mPagerAdapter = new ImageDetailAdapter(mImageLoader,mPictureData);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(mCurrentPosition);
        mViewPager.addOnPageChangeListener(onPageChangeListener);
        updateIndicator();
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            updateIndicator();
        }
    };

    public void updateIndicator() {
        String text = String.format("(%1$d/%2$d)", mViewPager.getCurrentItem() + 1, mPagerAdapter.getCount());
        mIndicatorText.setText(text);
    }

    public void showToast(String message) {
        ToastUtils.showShort(this,message);
    }

    static class ImageDetailAdapter extends PagerAdapter {

        private List<String> mData = new ArrayList<>();

        private ImageLoader mLoader;

        private DisplayImageOptions options;

        public ImageDetailAdapter(ImageLoader loader, List<String> data) {
            mLoader = loader;
            options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .showImageOnLoading(R.drawable.photopicker_placeholder)
                    .build();
            mData = data;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = (PhotoView) LayoutInflater.from(container.getContext())
                    .inflate(R.layout.item_image_detail, container, false);
//            mRequestManager
//                    .load(mData.get(position).getPath())
//                    .asBitmap()
//                    .fitCenter()
//                    .thumbnail(0.2f)
//                    .into(photoView);
            mLoader.displayImage(sFilePrefix + mData.get(position),photoView,options);
            container.addView(photoView);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
