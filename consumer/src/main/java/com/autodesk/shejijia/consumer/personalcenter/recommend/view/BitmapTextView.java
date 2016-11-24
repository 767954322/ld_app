package com.autodesk.shejijia.consumer.personalcenter.recommend.view;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;

/**
 * Created by yaoxuehua on 16-11-18.
 * 自定义实现图片旋转
 */

public class BitmapTextView extends TextView {
    private Bitmap bitmapNeed;
    private Bitmap lastBitmap;
    private String text;
    private int textViewWidth;
    private int textViewHeight;
    private int height;//该控件宽度
    private int width;//该控件高度
    private static double NO_CHANGE_HEIGHT = 1187.00d;
    private static double ADAPTER_COUNT = 1.00d;
    public static final int BITMAP_DOWN = 1;
    public static final int BITMAP_TOP = 2;
    private int PICTURE_DISTANCE_TEXT;
    private Activity activity;

    public BitmapTextView(Context context) {
        super(context);
    }

    public BitmapTextView(Activity context, AttributeSet attrs) {
        super(context, attrs);
        height = context.getWindowManager().getDefaultDisplay().getHeight();
        width = context.getWindowManager().getDefaultDisplay().getWidth();
        ADAPTER_COUNT = (double) height / NO_CHANGE_HEIGHT;

    }

    public void setBitmapAndSize(int drawble, String text) {

        this.text = text;
        Resources resources = getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(resources, drawble);
        Matrix matrix = new Matrix();
        int pictureHeight = bitmap.getHeight();
        int pictureWidth = bitmap.getWidth();
        int newHeight = 20;
        int newWidth = 20;
        float scaleHeight = (float) newHeight / pictureHeight;
        float scaleWidth = (float) newWidth / pictureWidth;
        matrix.setScale(1f, 1f);
        this.bitmapNeed = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        this.lastBitmap = bitmapNeed;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setTextCanvas(canvas);
    }

    public void setTextCanvas(Canvas canvas){
        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        String text = "";
        paint.setTextSize(20);
        int textWidth = getTextWidth(paint, text);
        int textHeight = getTextHeight(paint);
        canvas.drawText(text, textViewWidth / 2 - textWidth / 2, textViewHeight / 2, paint);
        setBitmapCanvas(canvas, textWidth, textHeight);
    }
    public void setBitmapCanvas(Canvas canvas, int textWidth, int textHeight) {

        textWidth = PICTURE_DISTANCE_TEXT;
        int bitmapWidth = lastBitmap.getWidth();
        int bitmapHeight = lastBitmap.getHeight();
        int left = textViewWidth / 2 - bitmapWidth / 2;
        int top = textViewHeight / 2 - bitmapHeight / 2;

        Paint paintBitmap = new Paint();
        Rect rectBitmap = new Rect(0, 0, bitmapWidth, bitmapHeight);
        Rect rectLocationForBitmap = new Rect(left + textWidth, top, left + bitmapWidth + textWidth, top + bitmapHeight);
        canvas.drawBitmap(lastBitmap, rectBitmap, rectLocationForBitmap, paintBitmap);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.textViewWidth = w;
        this.textViewHeight = h;
        PICTURE_DISTANCE_TEXT = getResources().getDimensionPixelSize(R.dimen.add_category_picture_distance_text);
    }

    /**
     * 精确测量文字的宽度
     */
    public int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

    /**
     * 获取文字的高度
     */
    public int getTextHeight(Paint paint) {

        Paint.FontMetrics fm = paint.getFontMetrics();
        int textHeight = (int) (Math.ceil(fm.descent - fm.ascent) + 2);
        return textHeight;
    }
    /**
     * 旋转
     * */
    public Bitmap adjustPhotoRotation(Bitmap bm, final int orientationDegree)
    {
        Matrix m = new Matrix();
        m.setRotate(orientationDegree, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);

        try {

            Bitmap bm1 = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);

            return bm1;
        } catch (OutOfMemoryError ex) {
        }

        return null;

    }

    /**
     * 外界动态改变bitmap
     * */
    public void changeBitmap(int direction){

        if (direction == BITMAP_DOWN){
            lastBitmap = bitmapNeed;
        }
        if (direction == BITMAP_TOP){

            lastBitmap = adjustPhotoRotation(bitmapNeed,180);
        }
        invalidate();
    }

}
