package com.autodesk.shejijia.shared.components.common.utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 16-6-13
 * @file PictureProcessingUtil.java  .
 * @brief 压缩处理图片工具类 .
 */
public class PictureProcessingUtil {
    public String tempFileName;
    public int targetLength = 256;
    public String temp_path = ImageLoader.getInstance().getDiskCache().getDirectory().getPath();

    public Object[] judgePicture(String imageFilePath) {

        FileInputStream fis = null;

        try {
            fis = new FileInputStream(imageFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(fis);
        String extensionName = imageFilePath.substring(imageFilePath.lastIndexOf(".") + 1, imageFilePath.length());  /// 取得副档名 .
        float factor;
        int targetWidth, targetHeight; /// 获取图片原本得宽高 .

        if (bitmap.getWidth() > bitmap.getHeight()) { /// 进行等比例缩放 .
            factor = (float) targetLength / (float) bitmap.getHeight();
            targetHeight = targetLength;
            targetWidth = (int) (factor * bitmap.getWidth());
        } else { /// 进行等比例缩放 .
            factor = (float) targetLength / (float) bitmap.getWidth();
            targetWidth = targetLength;
            targetHeight = (int) (factor * bitmap.getHeight());
        }
        Bitmap _bitmap = convertToBitmap(imageFilePath, targetWidth, targetHeight);  //缩图
        tempFileName = "tempImg." + extensionName;
        File tempFile = new File(temp_path + File.separator + tempFileName);

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(tempFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap2File(_bitmap, extensionName, 100, out);   /// bitmap to file .
        int fileSize = (int) ((tempFile.length() / 1024) / 1024);
        if (fileSize > 2 && fileSize < 10) { /// 图片质量大于2M小于10M，压缩到一半 .
            bitmap2File(_bitmap, extensionName, 50, out);
        } else if (fileSize > 10) { /// 图片质量大于10M，压缩到十分之一 .
            bitmap2File(_bitmap, extensionName, 10, out);
        }
        try {
            fis.close();
            out.close();
            bitmap.recycle();
        } catch (IOException e) {
            e.printStackTrace();
            e.printStackTrace();
        }
        Object[] objects = new Object[]{tempFile, _bitmap};
        return objects;
    }

    /**
     * @param path
     * @param w
     * @param h
     * @return
     * @brief 将图片等比例缩放 .
     */
    public Bitmap convertToBitmap(String path, int w, int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为true只获取图片大小
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        // 返回为空
        BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        float scaleWidth = 0.f, scaleHeight = 0.f;
        if (width > w || height > h) {
            // 缩放
            scaleWidth = ((float) width) / w;
            scaleHeight = ((float) height) / h;
        }
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);
        opts.inSampleSize = (int) scale;
        WeakReference<Bitmap> weak = new WeakReference<>(BitmapFactory.decodeFile(path, opts));
        return Bitmap.createScaledBitmap(weak.get(), w, h, true);
    }

    /**
     * @srcBitmap bitmap
     * @qulity quality
     * @extensionName extensionName
     * @FileOutputStream fileOutputStream
     * @brief bitmap to jpg or png file
     */
    public void bitmap2File(Bitmap srcBitmap, String extensionName, int quality, FileOutputStream out) {
        if (extensionName.equals("jpg") || extensionName.equals("JPG")) {
            srcBitmap.compress(Bitmap.CompressFormat.JPEG, quality, out); // quality数值越大图片的品质越高，档案越大上线是100
        } else if (extensionName.equals("png") || extensionName.equals("PNG")) {
            srcBitmap.compress(Bitmap.CompressFormat.PNG, quality, out);
        }
    }

    /**
     * @param bitmap
     * @return 压缩后的bitmap
     */
    public static Bitmap compressionBigBitmap(Bitmap bitmap, boolean isSysUp) {
        Bitmap destBitmap = null;
        /* 图片宽度调整为100，大于这个比例的，按一定比例缩放到宽度为100 */
        if (bitmap.getWidth() > 200) {
            float scaleValue = (200f / bitmap.getWidth());
            System.out.println("缩放比例---->" + scaleValue);

            Matrix matrix = new Matrix();
            /* 针对系统拍照，旋转90° */
            if (isSysUp)
                matrix.setRotate(90);
            matrix.postScale(scaleValue, scaleValue);

            destBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
            int widthTemp = destBitmap.getWidth();
            int heightTemp = destBitmap.getHeight();
            Log.i("MaLiDong", "压缩后的宽高----> width: " + heightTemp
                    + " height:" + widthTemp);
        } else {
            return bitmap;
        }
        return destBitmap;
    }
}
