package com.autodesk.shejijia.shared.components.common.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.util.DisplayMetrics;
import android.util.Log;

import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.im.constants.ImageParameter;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * @author  Malidong .
 * @version 1.0 .
 * @date    16-6-6
 * @file    ImageProcessingUtil.java  .
 * @brief    .
 */
public class ImageProcessingUtil {

    private PictureProcessingUtil pictureProcessingUtil;

    /**
     * 取得指定长宽之影像
     */
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {

        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        bm.recycle();
        return resizedBitmap;
    }


    //This function will also scaled bitmap
    // but this function will try to align with either newwidth or newheight

    public Bitmap getScaledBitmap(final Bitmap bm, final int newWidth, final int newHeight) {

        int imageW = bm.getWidth();
        int imageH = bm.getHeight();

        int finalImageW;
        int finalImageH;

        float aspectRatio = imageW / (float) imageH;

        // 1. First check image is in portraint or landscape
        if (newWidth > newHeight)//landscape
        {
            if (imageW > imageH) { //landscape image
                finalImageW = newWidth;
                finalImageH = (int) (finalImageW / aspectRatio);

                if (finalImageH > newHeight) {
                    finalImageH = newHeight;
                    finalImageW = (int) (aspectRatio * finalImageH);
                }
            } else {//portrait image

                finalImageH = newHeight;
                finalImageW = (int) (finalImageH * aspectRatio);
            }
        } else {
            if (imageW > imageH) {//landscape image
                finalImageW = newWidth;
                finalImageH = (int) (finalImageW / aspectRatio);
            } else { //portrait image
                finalImageH = newHeight;
                finalImageW = (int) (finalImageH * aspectRatio);

                if (finalImageW > newWidth) {
                    finalImageW = newWidth;
                    finalImageH = (int) (finalImageW / aspectRatio);
                }
            }
        }

        return (Bitmap.createScaledBitmap(bm, finalImageW, finalImageH, false));
    }


    /**
     * 取得原始影像缩放至当前展示影像之缩放比率
     */
    public float getImageMagnification(Bitmap bmp, boolean isRotate) {

        float result = 0;

        int[] screen_info = getCurrentImgWH();

        int width, height;

        if (!isRotate) {

            width = bmp.getWidth();
            height = bmp.getHeight();

        } else {

            height = bmp.getWidth();
            width = bmp.getHeight();

        }

        if ((float) width / (float) height > (float) screen_info[0] / (float) screen_info[1]) {

            result = (float) screen_info[0] / (float) width;

        } else {

            result = (float) screen_info[1] / (float) height;

        }

        return result;

    }


    /**
     * 取得当前点图影像长宽
     */
    public int[] getCurrentImgWH() {

        //temp[2] is the screen_height
        Context context = AdskApplication.getInstance();

        int[] temp = new int[3];

        int titleBarHeight = context.getResources().getDimensionPixelSize(R.dimen.size_50);
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);

        } catch (Exception e) {
            e.printStackTrace();
        }

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        temp[0] = metrics.widthPixels;
        temp[1] = metrics.heightPixels - statusHeight - titleBarHeight;
        temp[2] = metrics.heightPixels;

        return temp;

    }


    public Bitmap cutImageInPath(Context context, Bitmap image, int width, int height, boolean isLeft) {
        float outerRadiusRat = 25;
        Drawable imageDrawable = new BitmapDrawable(context.getResources(), image);
        imageDrawable.setBounds(0, 0, width, height);

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        RectF outerRect;

        if (isLeft) {

            outerRect = new RectF(40, 0, width, height);

        } else {

            outerRect = new RectF(0, 0, width - 40, height);

        }

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        canvas.drawRoundRect(outerRect, outerRadiusRat, outerRadiusRat, paint);

        Path path = new Path();

        if (isLeft) {
            path.moveTo(0, 0);
            path.lineTo(60, 0);
            path.lineTo(60, 30);

        } else {

            path.moveTo(width, 0);
            path.lineTo(width - 60, 0);
            path.lineTo(width - 60, 30);

        }

        path.close();
        canvas.drawPath(path, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.saveLayer(new RectF(0, 0, width, height), paint, Canvas.ALL_SAVE_FLAG);
        imageDrawable.draw(canvas);
        canvas.restore();
        image.recycle();

        return output;
    }

    /**
     * 聊天列表自定义贴图形状
     */
    public Bitmap createFramedPhoto(Context context, Bitmap image, boolean isLeft) {

        float outerRadiusRat = 25;
        float src_angle = 36;

        DisplayMetrics outMetrics = context.getResources().getDisplayMetrics();
        float density = context.getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels / density;
        float dpWidth = outMetrics.widthPixels / density;

        float x, y;
        float width = 0, height = 0;

        int widtho = image.getWidth(), heighto = image.getHeight();

        width = image.getWidth() * ((float) 160 / image.getHeight());
        height = 160;
        if ((width - (dpWidth - 100)) > 0) {
            height = (height * ((dpWidth - 100) / width));
            width = (dpWidth - 100);
        }

        image = getResizedBitmap(image, (int) (width * density), (int) (height * density));
        image = cutImageInPath(context, image, (int) (width * density), (int) (height * density), isLeft);
        return image;
    }

    /**
     * 压缩影像品质
     */
    public File compressFileSize(File file) throws IOException {

        pictureProcessingUtil = new PictureProcessingUtil();
        Object[] object = pictureProcessingUtil.judgePicture(file.getPath());
        Bitmap bmp = (Bitmap) object[1];
        int qulity = ImageParameter.highQuality;

        int size = (int) (file.length() / 1024 / 1024);

        if (size > 2 && size < 10) {

            qulity = ImageParameter.normalQuality;

        } else if (size > 10) {

            qulity = ImageParameter.lowQuality;

        }

        File newFile = new File(ImageParameter.tempImageFilePath);

        FileOutputStream out = new FileOutputStream(newFile);

        String[] split = file.getName().split("\\.");

        String extension = split[split.length - 1];

        //png to jpg
        if (extension.equals("png") || extension.equals("PNG")) {

            Bitmap targetBitmap = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.RGB_565);

            Canvas vBitmapCanvas = new Canvas(targetBitmap);
            vBitmapCanvas.drawColor(Color.WHITE);
            vBitmapCanvas.drawBitmap(bmp, 0, 0, null);
            targetBitmap.compress(Bitmap.CompressFormat.JPEG, qulity, out);
            targetBitmap.recycle();
            bmp.recycle();

        } else {

            bmp.compress(Bitmap.CompressFormat.JPEG, qulity, out);

            bmp.recycle();

        }

        out.close();

        return newFile;

    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 读取图片属性：修正旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public void fixImageOrientation(String path) {

        int degree = readPictureDegree(path);

        Bitmap original;

        if (degree != 0) {

            try {

                FileInputStream fileInputStream = new FileInputStream(new File(path));

                original = BitmapFactory.decodeStream(fileInputStream);

                //original = getResizedBitmap(original, original.getWidth(), original.getHeight());

                int width = original.getWidth();
                int height = original.getHeight();

                Matrix matrix = new Matrix();

                matrix.postRotate(degree, width, height);

                original = Bitmap.createBitmap(original, 0, 0, width,
                        height, matrix, false);

                FileOutputStream out = new FileOutputStream(path);

                original.compress(Bitmap.CompressFormat.JPEG, 100, out);

                out.close();

                original.recycle();

            } catch (Exception e) {

            }

        }

    }

    /**
     * 删除暂存档案（拍照或压缩后的暂存档）
     */
    public void deleteTempImageFile() {

        File file = new File(ImageParameter.tempImageFilePath);

        if (file.exists()) {

            file.delete();
        }

    }

    // This function will rotate the image using Renderscript
    public void fastRotateAndCopyImage(final String inImagePath, final String outImagePath,
                                       final int rotateBy, final Context context,
                                       final ImageSaverHandler handler)
    {
        FastRotateBitmapTask rotateBitmapTask = new FastRotateBitmapTask(handler, rotateBy, context);
        rotateBitmapTask.execute(inImagePath, outImagePath);
    }


    // This function will read the EXIF tags to find out how much to rotate the image by
    // This function will rotate the image using Renderscript
    public void fastRotateAndCopyImage(final String inImagePath, final String outImagePath,
                                       final Context context, final ImageSaverHandler handler)
    {
        FastRotateBitmapTask rotateBitmapTask = new FastRotateBitmapTask(handler, context);
        rotateBitmapTask.execute(inImagePath, outImagePath);
    }

    //This function will load image in memory, rotate it if image is having rotation in EXIF
    // if succeeds, savepath will be returned
    // this function runs async

    public void copyImageWithOrientation(final String inImagePath, final String outImagePath,final ImageSaverHandler handler) {

        SaveBitmapWorkerTask saveBitmapWorkerTask = new SaveBitmapWorkerTask(handler);
        saveBitmapWorkerTask.execute(inImagePath,outImagePath);
    }


    public void copyImageWithOrientation(final String inImagePath, final String outImagePath,
                                         final int rotateBy, final ImageSaverHandler handler) {
        SaveBitmapWorkerTask saveBitmapWorkerTask = new SaveBitmapWorkerTask(handler,rotateBy);
        saveBitmapWorkerTask.execute(inImagePath,outImagePath);
    }


    public Bitmap scaleAndRotateImage(final Bitmap subsampledBitmap, final int rotation, final boolean flipHorizontal) {
        Matrix m = new Matrix();
        // Flip bitmap if need
        if (flipHorizontal) {
            m.postScale(-1, 1);
        }
        // Rotate bitmap if need
        if (rotation != 0) {
            m.postRotate(rotation);
        }

        Bitmap finalBitmap = Bitmap.createBitmap(subsampledBitmap, 0, 0, subsampledBitmap.getWidth(), subsampledBitmap
                .getHeight(), m, true);
        if (finalBitmap != subsampledBitmap) {
            subsampledBitmap.recycle();
        }
        return finalBitmap;
    }


    public Bitmap fastRotate(final Bitmap bitmap, final int rotation, final Context context)
    {
        int actualRotation;

        if (rotation < 0)
        {
            Log.d("ImageProcessingUtil", "Cannot rotate bitmap: Please pass a rotation " +
                    "value that is a positive integral multiple of 90");
            return null;
        }

        if (rotation % 360 == 0)
            return bitmap;

        if (rotation % 90 != 0)
        {
            Log.d("ImageProcessingUtil", "Cannot rotate bitmap: Please pass a rotation " +
                    "value that is a positive integral multiple of 90");
            return null;
        }
        else if (rotation > 360)
            actualRotation = rotation % 360;
        else
            actualRotation = rotation;

        Bitmap target = null;

        try
        {
            RenderScript rs = RenderScript.create(context);
            ScriptC_rotator script = new ScriptC_rotator(rs);

            int originalWidth = bitmap.getWidth();
            int originalHeight = bitmap.getHeight();
            Bitmap.Config config = bitmap.getConfig();

            script.set_inWidth(originalWidth);
            script.set_inHeight(originalHeight);

            Allocation sourceAllocation = Allocation.createFromBitmap(rs, bitmap,
                    Allocation.MipmapControl.MIPMAP_NONE,
                    Allocation.USAGE_SCRIPT);
            bitmap.recycle();
            script.set_inImage(sourceAllocation);

            int targetHeight = 0;
            int targetWidth = 0;

            switch (actualRotation)
            {
                case 90:
                    targetHeight = originalWidth;
                    targetWidth = originalHeight;
                    break;

                case 180:
                    targetHeight = originalHeight;
                    targetWidth = originalWidth;
                    break;

                case 270:
                    targetHeight = originalWidth;
                    targetWidth = originalHeight;
                    break;

                default:
                    Log.d("ImageProcessingUtil", "Unexpected rotation value found!");
                    return null;
            }

            target = Bitmap.createBitmap(targetWidth, targetHeight, config);
            final Allocation targetAllocation = Allocation.createFromBitmap(rs, target,
                    Allocation.MipmapControl.MIPMAP_NONE,
                    Allocation.USAGE_SCRIPT);

            switch (actualRotation)
            {
                case 90:
                    script.forEach_rotate_90_clockwise(targetAllocation, targetAllocation);
                    break;

                case 180:
                    script.forEach_rotate_180_clockwise(targetAllocation, targetAllocation);
                    break;

                case 270:
                    script.forEach_rotate_270_clockwise(targetAllocation, targetAllocation);
                    break;
            }

            targetAllocation.copyTo(target);
            rs.destroy();
        }
        catch (Exception e)
        {
            Log.d("ImageProcessingUtil", "Exception while fast-rotating image: " + e.getMessage());
            target = null;
        }

        return target;
    }


    public interface ImageSaverHandler {
        void onSuccess(String outImagePath);
        void onFailure();
    }


    private ExifInfo getExifInfo(String inImagePath)
    {
        InputStream inputStream = null;

        try
        {
            inputStream = getStreamFromFile(inImagePath);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, options);

            ExifInfo exifInfo;

            if(options.outMimeType.equalsIgnoreCase("image/jpeg")) {
                exifInfo = getImageOrientation(inImagePath);
            } else {
                exifInfo = new ExifInfo();
            }

            return exifInfo;
        }
        catch (Exception e)
        {
            Log.d("ImageProcessingUtil", e.getMessage());
        }
        finally
        {
            if (inputStream != null)
                closeSilently(inputStream);
        }

        return new ExifInfo();
    }


    private Bitmap decodeBitmap(String inImagePath)
    {
        InputStream inputStream = null;
        Bitmap decodedBitmap = null;

        try
        {
            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inSampleSize = 2;

            inputStream = getStreamFromFile(inImagePath);
            decodedBitmap = BitmapFactory.decodeStream(inputStream, null, options);
        }
        catch (Exception e)
        {
            Log.d("ImageProcessingUtil", e.getMessage());
        }
        finally
        {
            if (inputStream != null)
                closeSilently(inputStream);
        }

        return decodedBitmap;
    }


    private boolean saveBitmapAsJPEG(String outImagePath, Bitmap bitmap)
    {
        FileOutputStream fileOutputStream = null;
        boolean success = false;

        try
        {
            File file = new File(outImagePath);
            fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            bitmap.recycle();

            success = true;
        }
        catch (Exception e)
        {
            Log.d("ImageProcessingUtil", e.getMessage());
        }
        finally
        {
            if (fileOutputStream != null)
                closeSilently(fileOutputStream);
        }

        return success;
    }


    private InputStream getStreamFromFile(final String imagePath) throws IOException {
        final int BUFFER_SIZE = 32 * 1024; // 32 Kb

        BufferedInputStream imageStream = new BufferedInputStream(new FileInputStream(imagePath), BUFFER_SIZE);
        return new ContentLengthInputStream(imageStream, (int) new File(imagePath).length());
    }


    private void closeSilently(final Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception ignored) {
            }
        }
    }


    private ExifInfo getImageOrientation(final String imagePath) {
        int rotation = 0;
        boolean flip = false;
        try {
            ExifInterface exif = new ExifInterface(imagePath);
            int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (exifOrientation) {
                case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                    flip = true;
                case ExifInterface.ORIENTATION_NORMAL:
                    rotation = 0;
                    break;
                case ExifInterface.ORIENTATION_TRANSVERSE:
                    flip = true;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotation = 90;
                    break;
                case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                    flip = true;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotation = 180;
                    break;
                case ExifInterface.ORIENTATION_TRANSPOSE:
                    flip = true;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotation = 270;
                    break;
            }
        } catch (IOException e) {

            Log.d("ImageProcessingUtil", e.getMessage());
        }
        return new ExifInfo(rotation, flip);
    }


    private class SaveBitmapWorkerTask extends AsyncTask<String,Void,String> {

        private ImageSaverHandler mHandler;
        private Integer mRotateBy = null;

        public SaveBitmapWorkerTask(ImageSaverHandler handler) {
            mHandler = handler;
        }

        public SaveBitmapWorkerTask(ImageSaverHandler handler, int rotateBy) {
            mHandler = handler;
            mRotateBy = rotateBy;
        }

        // Decode image in background.
        @Override
        protected String doInBackground(String... params) {

            String inImagePath = params[0];
            String outImagePath = params[1];
            String result = null;

            if (inImagePath != null && !inImagePath.isEmpty()) {

                try {
                    // Read exif data of image
                    ExifInfo exifInfo = getExifInfo(inImagePath);

                    int rotateBy = 0;

                    //1. Get rotation value
                    if (mRotateBy == null)
                    {
                        // Get rotation value from EXIF
                        rotateBy = exifInfo.rotation;
                    }
                    else
                        rotateBy = mRotateBy; // We had the rotation value passed to us

                    //2. decode it
                    Bitmap decodedBitmap = decodeBitmap(inImagePath);

                    if (decodedBitmap == null) {
                        Log.d("ImageProcessingUtil", "Problem in decoding bitmap");
                    }
                    else {

                        //3. rotate it
                        decodedBitmap = scaleAndRotateImage(decodedBitmap,rotateBy,exifInfo.flipHorizontal);

                        //4. save it
                        boolean success = saveBitmapAsJPEG(outImagePath, decodedBitmap);

                        if (!success)
                            result = null;
                        else
                            result = outImagePath;
                    }
                }
                catch (Exception e) {
                    Log.d("ImageProcessingUtil", e.getMessage());
                    return null;
                }
            }

            //5. return path
            return result;
        }

        @Override
        protected void onPostExecute(String outImagePath) {
            if (mHandler != null) {
                if (outImagePath != null) {
                    mHandler.onSuccess(outImagePath);
                } else {
                    mHandler.onFailure();
                }
            }
        }
    }


    private class FastRotateBitmapTask extends AsyncTask<String, Void, String>
    {
        private ImageSaverHandler mHandler;
        private Integer mRotateBy = null;
        private Context mContext;

        public FastRotateBitmapTask(ImageSaverHandler handler, Context context)
        {
            mHandler = handler;
            mContext = context;
        }

        public FastRotateBitmapTask(ImageSaverHandler handler, int rotateBy, Context context)
        {
            mRotateBy = rotateBy;
            mHandler = handler;
            mContext = context;
        }

        // Decode image in background.
        @Override
        protected String doInBackground(String... params)
        {
            String inImagePath = params[0];
            String outImagePath = params[1];
            String result = null;

            if (inImagePath != null && !inImagePath.isEmpty())
            {
                try
                {
                    int rotateBy = 0;

                    //1. Get rotation value
                    if (mRotateBy == null)
                    {
                        // Read EXIF
                        ExifInfo exifInfo = getExifInfo(inImagePath);
                        rotateBy = 360 - exifInfo.rotation;
                    }
                    else
                        rotateBy = mRotateBy; // We had the rotation value passed to us

                    Log.d("ImageProcessingUtil", "Rotating bitmap by: " + rotateBy + " degrees CW");

                    //2. decode it
                    Bitmap decodedBitmap = decodeBitmap(inImagePath);

                    if (decodedBitmap == null)
                        Log.d("ImageProcessingUtil", "Problem in decoding bitmap");
                    else
                    {
                        //3. rotate it
                        decodedBitmap = fastRotate(decodedBitmap, rotateBy, mContext);

                        //4. save it
                        boolean success = saveBitmapAsJPEG(outImagePath, decodedBitmap);

                        if (!success)
                            result = null;
                        else
                            result = outImagePath;
                    }
                }
                catch (Exception e)
                {
                    Log.d("ImageProcessingUtil", e.getMessage());
                    result = null;
                }
            }

            //5. return path
            return result;
        }

        @Override
        protected void onPostExecute(String outImagePath)
        {
            if (mHandler != null)
            {
                if (outImagePath != null)
                    mHandler.onSuccess(outImagePath);
                else
                    mHandler.onFailure();
            }
        }
    }

    private class ContentLengthInputStream extends InputStream {

        private final InputStream stream;
        private final int length;

        public ContentLengthInputStream(InputStream stream, int length) {
            this.stream = stream;
            this.length = length;
        }

        @Override
        public int available() {
            return length;
        }

        @Override
        public void close() throws IOException {
            stream.close();
        }

        @Override
        public void mark(int readLimit) {
            stream.mark(readLimit);
        }

        @Override
        public int read() throws IOException {
            return stream.read();
        }

        @Override
        public int read(byte[] buffer) throws IOException {
            return stream.read(buffer);
        }

        @Override
        public int read(byte[] buffer, int byteOffset, int byteCount) throws IOException {
            return stream.read(buffer, byteOffset, byteCount);
        }

        @Override
        public void reset() throws IOException {
            stream.reset();
        }

        @Override
        public long skip(long byteCount) throws IOException {
            return stream.skip(byteCount);
        }

        @Override
        public boolean markSupported() {
            return stream.markSupported();
        }
    }

    private class ExifInfo {

        int rotation = 0;
        boolean flipHorizontal = false;

        ExifInfo() {
            this.rotation = 0;
            this.flipHorizontal = false;
        }

        ExifInfo(int rotation, boolean flipHorizontal) {
            this.rotation = rotation;
            this.flipHorizontal = flipHorizontal;
        }
    }
}
