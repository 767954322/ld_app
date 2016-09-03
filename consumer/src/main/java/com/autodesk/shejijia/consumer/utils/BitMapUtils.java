package com.autodesk.shejijia.consumer.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * @Author: lizhipeng
 * @Data: 16/8/25 下午5:23
 * @Description: 图片处理工具类
 */
public class BitMapUtils {

    /**
     * 获取图片的方向（旋转角度）
     * @param context
     * @param photoUri
     * @return
     */
    public static int getOrientation(Context context, Uri photoUri) {
        int orientation = 0;
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);
        if (cursor != null) {
            if (cursor.getCount() != 1) {
                return -1;
            }
            cursor.moveToFirst();
            orientation = cursor.getInt(0);
            cursor.close();
        }
        return orientation;
    }

    /**
     * 旋转图片
     * @param bmp
     * @param degrees
     * @return
     */
    public static Bitmap rotateImage(Bitmap bmp, int degrees) {
        if (degrees != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(degrees);
            Bitmap newBitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
            bmp.recycle();
            return newBitmap;
        }
        return bmp;
    }

}
