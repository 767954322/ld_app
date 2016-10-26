package com.autodesk.shejijia.consumer.home.decorationlibrarys.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.SeekDesignerDetailBean;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.CaseDetailBean;
import com.autodesk.shejijia.consumer.utils.ToastUtil;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.consumer.uielements.ActionSheetDialog;
import com.autodesk.shejijia.shared.components.common.uielements.photoview.HackyViewPager;
import com.autodesk.shejijia.shared.components.common.uielements.photoview.PhotoView;
import com.autodesk.shejijia.shared.components.common.uielements.photoview.PhotoViewAttacher;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @version 1.0 .
 * @date 2016/3/25 0025 9:53 .
 * @filename CaseLibraryDetailActivity.
 * 图片放大
 */
public class CaseLibraryDetailActivity extends NavigationBarActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_case_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        mViewPager = (HackyViewPager) findViewById(R.id.case_librafy_detail_activity_vp);
    }

    // List<Case3DDetailImageListBean> imageListBean;
    private List<String> imageLists;

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        a = getIntent().getIntExtra("moveState", -1);
        if (a == 1) {
            imageLists = (List<String>) getIntent().getSerializableExtra(Constant.CaseLibraryDetail.CASE_DETAIL_BEAN);
            intExtra = getIntent().getIntExtra(Constant.CaseLibraryDetail.CASE_DETAIL_POSTION, 0);//获得点击的位置
        } else {
            caseDetailBean = (CaseDetailBean) getIntent().getSerializableExtra(Constant.CaseLibraryDetail.CASE_DETAIL_BEAN);
            intExtra = getIntent().getIntExtra(Constant.CaseLibraryDetail.CASE_DETAIL_POSTION, 0);//获得点击的位置
        }
        // caseDetailBean = (CaseDetailBean) getIntent().getSerializableExtra(Constant.CaseLibraryDetail.CASE_DETAIL_BEAN);
        //intExtra = getIntent().getIntExtra(Constant.CaseLibraryDetail.CASE_DETAIL_POSTION, 0);//获得点击的位置
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        updateViewFromData();
        mViewPager.setAdapter(new SamplePagerAdapter(mImageUrl));
        mViewPager.setCurrentItem(intExtra);
    }

    /**
     * 获取所有图片的url地址
     * <p>
     * TODO  BUG
     */
    private void updateViewFromData() {
        if (a == 1) {
            if (imageLists != null) {
//                for (int i = 0; i < imageListBean.size(); i++) {
//                    List<String> imageList = imageListBean.get(i).getImageList();
//                    int imageItemSize = imageList.size();
//                    for (int j=0;j<imageItemSize;j++){
//                        imageUrl =imageList.get(j);
//
//
//                    }
//
//                }
                mImageUrl.addAll(imageLists);
            }
        } else if (caseDetailBean.getImages() != null) {
            for (int i = 0; i < caseDetailBean.getImages().size(); i++) {
                if (null != caseDetailBean && caseDetailBean.getImages().size() != 0) {
                    imageUrl = caseDetailBean.getImages().get(i).getFile_url() + Constant.CaseLibraryDetail.JPG;
                    mImageUrl.add(imageUrl);
                }
            }
        } else if (jumpStatus == 2)

        {

            if (null != mSeekDesignerDetailBean && null != mSeekDesignerDetailBean.getCases()) {
                for (int i = 0; i < mSeekDesignerDetailBean.getCases().size(); i++) {
                    if (null != mSeekDesignerDetailBean && mSeekDesignerDetailBean.getCases().get(i).getImages().size() != 0) {
                        imageUrl = mSeekDesignerDetailBean.getCases().get(i).getImages().get(0).getFile_url() + Constant.CaseLibraryDetail.JPG;
                        mImageUrl.add(imageUrl);
                    }
                    //if (null != caseDetailBean && null != caseDetailBean.getImages()) {
                    //  for (int i = 0; i < caseDetailBean.getImages().size(); i++) {
                    //    if (null != caseDetailBean && caseDetailBean.getImages().size() != 0) {
                    //      imageUrl = caseDetailBean.getImages().get(i).getFile_url() + Constant.CaseLibraryDetail.JPG;
                    //    mImageUrl.add(imageUrl);
                    //}
                }
            }
        }
    }


    class SamplePagerAdapter extends PagerAdapter {

        private ArrayList<String> mImageUrl1;

        public SamplePagerAdapter(ArrayList<String> mImageUrl1) {
            this.mImageUrl1 = mImageUrl1;
        }

        @Override
        public int getCount() {
            return mImageUrl1.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, final int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            ImageUtils.loadImage(photoView, mImageUrl1.get(position));
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    CaseLibraryDetailActivity.this.finish();
                }
            });
            photoView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    ActionSheetDialog actionSheetDialog = new ActionSheetDialog(CaseLibraryDetailActivity.this)
                            .builder()
                            .setCancelable(true)
                            .setCanceledOnTouchOutside(true)
                            .addSheetItem(getResources().getString(R.string.savelocal), ActionSheetDialog.SheetItemColor.Blue,
                                    new ActionSheetDialog.OnSheetItemClickListener() {
                                        @Override
                                        public void onClick(int which) {

                                            ImageLoader.getInstance().loadImage(mImageUrl.get(position), new ImageLoadingListener() {
                                                @Override
                                                public void onLoadingStarted(String imageUri, View view) {

                                                }

                                                @Override
                                                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                                                }

                                                @Override
                                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                                                    try {
                                                        saveImageToGallery(loadedImage);
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }

                                                }

                                                @Override
                                                public void onLoadingCancelled(String imageUri, View view) {

                                                }
                                            });

                                        }
                                    });
                    actionSheetDialog.setCancelTextColor(Color.GRAY);
                    actionSheetDialog.show();

                    return false;
                }
            });
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /**
         * 保存图片到本地
         */
        public void saveImageToGallery(Bitmap bmp) throws IOException {
            if (bmp == null) {
                ToastUtil.showCustomToast(CaseLibraryDetailActivity.this, getString(R.string.save_failure));
                return;
            }

            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File appDir = new File(Environment.getExternalStorageDirectory(), "shejijia");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                String fileName = System.currentTimeMillis() + ".jpg";
                File file = new File(appDir, fileName);

                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();

                    // 最后通知图库更新
                    try {
                        MediaStore.Images.Media.insertImage(CaseLibraryDetailActivity.this.getContentResolver(), file.getAbsolutePath(), fileName, null);
//                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                        Uri uri = Uri.fromFile(new File("/sdcard/image.jpg"));
//                        intent.setData(uri);
//                        CaseLibraryDetailActivity.this.sendBroadcast(intent);
                        Uri localUri = Uri.fromFile(file);
                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
                        CaseLibraryDetailActivity.this.sendBroadcast(intent);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
//                    MediaStore.Images.Media.insertImage(getContentResolver(), bmp, "title", "description");
//                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                    Uri uri = Uri.fromFile(file);
//                    intent.setData(uri);
//                    CaseLibraryDetailActivity.this.sendBroadcast(intent);
                    ToastUtil.showCustomToast(CaseLibraryDetailActivity.this, getString(R.string.save_success));
                } catch (IOException e) {
                    e.printStackTrace();
                    ToastUtil.showCustomToast(CaseLibraryDetailActivity.this, getString(R.string.save_failure));
                }
            }
        }

    }

    private HackyViewPager mViewPager;
    private int intExtra;
    private String imageUrl;
    private int jumpStatus;
    private SeekDesignerDetailBean mSeekDesignerDetailBean;
    private CaseDetailBean caseDetailBean;
    private int a;
    //private Case3DDetailBean case3DDetailBean;
    private ArrayList<String> mImageUrl = new ArrayList<>();
}
