package com.autodesk.shejijia.shared.components.common.uielements.chooseview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yaoxuehua on 16-7-19.
 */
public class ChoosevViewPointer extends View{

    private Context context;
    private int width,height;
    private float A = 0f,B = 1/3f;
    private boolean initBoolean = true;
    private View view;
    public ChoosevViewPointer(Context context,int width,int height,float a,float b) {
        super(context);
        this.context = context;
        this.width = width;
        this.height = height;
        this.A = a;
        this.B = b;
        init();
    }

    public ChoosevViewPointer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChoosevViewPointer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void init(){

        setWidthOrHeight(width,height,A,B);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.GRAY);

        paint.setStrokeWidth(20);
        if (initBoolean){

            canvas.drawLine(0,0,280,0,paint);
            initBoolean = false;
        }
        canvas.drawLine(width * A,0,width * B,0,paint);
    }

    public void setWidthOrHeight(int width,int height,float a,float b){

        this.width = width;
        this.height = height;
        this.A = a;
        this.B = b;
        invalidate();

    }



}
