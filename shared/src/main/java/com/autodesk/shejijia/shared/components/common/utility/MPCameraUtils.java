package com.autodesk.shejijia.shared.components.common.utility;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;

/**
 * @author   jainar .
 * @version  v1.0 .
 * @date       2016-6-13 .
 * @file          MPCameraUtils.java .
 * @brief        .
 */
public class MPCameraUtils
{
    public static Intent cookCameraLaunchIntent(Context context, Uri fileUri)
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null)
            return takePictureIntent;

        return null;
    }


    public static String getImageFileForGallery()
    {
        String imageFileName = MPFileUtility.getUniqueFileName();
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);

        File imageFile = null;

        try
        {
            imageFile = File.createTempFile(imageFileName, ".jpg",
                    storageDir);
        }
        catch (Exception e)
        {
            MPNetworkUtils.logError("MPCameraUtils", "Exception while creating image file for " +
                    "camera: " + e.getMessage());
        }

        if (imageFile != null)
            return imageFile.getAbsolutePath();
        else
            return null;
    }

    // File should not be inside app folder
    public static void makeFileAvailableToGallery(String filePath, Context context)
    {
        assert (filePath != null && !filePath.isEmpty());

        File f = new File(filePath);
        assert (f.exists());

        Uri contentUri = Uri.fromFile(f);

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(contentUri);

        context.sendBroadcast(mediaScanIntent);
    }


    /**
     * Use this ONLY for small bitmaps, this will OOM otherwise
     *
     * @param original The source bitmap to rotate
     * @param degrees  Degrees to rotate by
     * @return Bitmap rotated by the desired amount
     */
    public static Bitmap getRotatedImage(Bitmap original, int degrees)
    {
        checkBitmap(original);

        if (degrees == 0)
            return original;

        Matrix matrix = new Matrix();
        matrix.postRotate(degrees, original.getWidth(), original.getHeight());

        Bitmap rotated = Bitmap.createBitmap(original, 0, 0, original.getWidth(),
                original.getHeight(), matrix, true);

        original.recycle();
        return rotated;
    }

    private static void checkBitmap(Object bitmap) throws RuntimeException
    {
        if (bitmap == null)
            throw new RuntimeException("Bitmap is null");
    }
}
