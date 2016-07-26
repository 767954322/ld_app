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
 * @author yaoxuehua
 * @version v1.0 .
 * @date 2016-7-19 .
 * @file ChooseViewPointer.java .
 * @brief 运行环境的管理切换类.
 */
public class ChooseViewPointer extends View{

    private Context context;
    private int width,height,initWidth = 0;
    private float A = 1/3f,B = 2/3f;
    private boolean initBoolean = true;
    private View view;
    public ChooseViewPointer(Context context, int width, int height/*,float a,float b*/) {
        super(context);
        this.context = context;
        this.width = width - (104*2);
        this.height = height;
//        this.A = a;
//        this.B = b;
        init();
    }

    public ChooseViewPointer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChooseViewPointer(Context context, AttributeSet attrs, int defStyleAttr) {
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
            }


        }
        canvas.drawLine(width * A,0,width * B,0,paint);
    }

    public void setWidthOrHeight(int width,int height,float a,float b){
        this.initWidth = 0;
        this.width = width;
        this.height = height;
        this.A = a;
        this.B = b;
        initBoolean = false;
        invalidate();

    }

    public void setInitChooseVoewPoint(int width,boolean just){

            this.initWidth = width;

            invalidate();
    }


}
