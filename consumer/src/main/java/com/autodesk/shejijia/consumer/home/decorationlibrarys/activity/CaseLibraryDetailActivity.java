package com.autodesk.shejijia.consumer.home.decorationlibrarys.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.ImagesBean;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.CaseDetailBean;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.uielements.ActionSheetDialog;
import com.autodesk.shejijia.shared.components.common.uielements.ImageShowView;
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
 */
public class CaseLibraryDetailActivity extends NavigationBarActivity implements ImageShowView.ImageShowViewListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_case_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        mImageShowView = (ImageShowView) findViewById(R.id.ad_view);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        caseDetailBean = (CaseDetailBean) getIntent().getSerializableExtra(Constant.CaseLibraryDetail.CASE_DETAIL_BEAN);
        intExtra = getIntent().getIntExtra(Constant.CaseLibraryDetail.CASE_DETAIL_POSTION, 0);//获得点击的位置
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        updateViewFromData();
    }

    @Override
    protected void initListener() {
        super.initListener();
    }


    /**
     * 长按图片可以获得图片的url
     *
     * @param position  　图片的索引
     * @param imageView 控件
     */
    @Override
    public void onImageClick(final int position, View imageView) {
//        Toast.makeText(this, "长按图片" + mImageUrl.get(position), Toast.LENGTH_SHORT).show();
        new ActionSheetDialog(this)
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
                        })
                .show();
    }

    /**
     * 保存图片到本地
     */
    public void saveImageToGallery(Bitmap bmp) throws IOException {
        if (bmp == null) {
            Toast.makeText(CaseLibraryDetailActivity.this, "保存出错了", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            String fileName = System.currentTimeMillis() + ".jpg";
//            String appDirName = Environment.getExternalStorageDirectory() + "Boohee";
//            File file = new File(appDirName);
            File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
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
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                MediaStore.Images.Media.insertImage(getContentResolver(), bmp, "title", "description");
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(file);
                intent.setData(uri);
                CaseLibraryDetailActivity.this.sendBroadcast(intent);
                Toast.makeText(CaseLibraryDetailActivity.this, "保存成功了", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

//        // 首先保存图片
//        File appDir = new File(SAVE_PIC_PATH, "iamge");
//        if (!appDir.exists()) {
//            appDir.mkdir();
//        }
//        String fileName = System.currentTimeMillis() + ".jpg";
//        File file = new File(appDir, fileName);
//        try {
//            FileOutputStream fos = new FileOutputStream(file);
//            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//            fos.flush();
//            fos.close();
//        } catch (FileNotFoundException e) {
//            Toast.makeText(CaseLibraryDetailActivity.this, "文件未发现", Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        } catch (IOException e) {
//            Toast.makeText(CaseLibraryDetailActivity.this, "保存出错了", Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        } catch (Exception e) {
//            Toast.makeText(CaseLibraryDetailActivity.this, "保存出错了", Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        }
//        // 最后通知图库更新
//        try {
//            MediaStore.Images.Media.insertImage(CaseLibraryDetailActivity.this.getContentResolver(), file.getAbsolutePath(), fileName, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        Uri uri = Uri.fromFile(file);
//        intent.setData(uri);
//        CaseLibraryDetailActivity.this.sendBroadcast(intent);
//        Toast.makeText(CaseLibraryDetailActivity.this, "保存成功了", Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取所有图片的url地址
     */
    private void updateViewFromData() {
        List<ImagesBean> images = caseDetailBean.getImages();
        if (null == images || images.size() < 1) {
            return;
        }
        for (int i = 0; i < caseDetailBean.getImages().size(); i++) {
            if (null != caseDetailBean && caseDetailBean.getImages().size() != 0) {
                ImagesBean imagesEntity = caseDetailBean.getImages().get(i);
                if (null != imagesEntity) {
                    imageUrl = imagesEntity.getFile_url() + Constant.CaseLibraryDetail.JPG;
                    mImageUrl.add(imageUrl);
                }
            }
        }
        mImageShowView.setImageResources(mImageUrl, this, intExtra);
    }

    private final String SAVE_PIC_PATH = Environment.getExternalStorageState()
            .equalsIgnoreCase(Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory()
            .getAbsolutePath() : "/mnt/sdcard";
    //    private static final String SAVE_REAL_PATH = SAVE_PIC_PATH + "/good/savePic";
    private int intExtra;
    private ImageShowView mImageShowView;
    private String imageUrl;
    private CaseDetailBean caseDetailBean;
    private ArrayList<String> mImageUrl = new ArrayList<>();
}
