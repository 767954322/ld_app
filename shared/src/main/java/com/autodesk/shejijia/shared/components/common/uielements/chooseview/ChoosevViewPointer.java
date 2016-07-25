package com.autodesk.shejijia.shared.components.common.uielements.chooseview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.autodesk.shejijia.shared.R;

/**
 * Created by yaoxuehua on 16-7-19.
 */
public class ChoosevViewPointer extends View{

    private Context context;
    private int width,height,initWidth = 0;
    private float A = 1/3f,B = 2/3f;
    private boolean initBoolean = true;
    private View view;
    public ChoosevViewPointer(Context context,int width,int height/*,float a,float b*/) {
        super(context);
        this.context = context;
        this.width = width - (104*2);
        this.height = height;
//        this.A = a;
//        this.B = b;
        init();
    }

    public ChoosevViewPointer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChoosevViewPointer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void init(){

     // setWidthOrHeight(width,height,A,B);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.my_project_title_pointer_color));

        paint.setStrokeWidth(20);
        if (initWidth != 0){
            if (initBoolean){
                canvas.drawLine(initWidth * 1/3f,0,initWidth * 2/3f,0,paint);
                initBoolean = false;
            }

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

    public void setInitCHooseVoewPoint(int width){

        this.initWidth = width;
    }


}
