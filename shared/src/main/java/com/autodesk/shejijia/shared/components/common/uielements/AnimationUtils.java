package com.autodesk.shejijia.shared.components.common.uielements;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yaoxuehua on 16-9-14.
 */
public class AnimationUtils {


    public static AlphaAnimation alphaAnimationDismiss = null;
    public static AlphaAnimation alphaAnimationShow = null;
    public static Timer timer = null;
    public Handler handler;

    private Activity mContext;
    private static AnimationUtils apiStatusUtil = new AnimationUtils();

    public static AnimationUtils getInstance() {
        if (apiStatusUtil == null) {
            apiStatusUtil = new AnimationUtils();
        }
        return apiStatusUtil;
    }

    /**
     *
     */
    public void setAnimationDismiss(View view) {

        if (alphaAnimationDismiss == null) {

            alphaAnimationDismiss = new AlphaAnimation(1, 0);
            alphaAnimationDismiss.setDuration(1500);
            alphaAnimationDismiss.setFillAfter(true);
            view.startAnimation(alphaAnimationDismiss);
        }
    }

    public void setAnimationShow(View view) {

        if (alphaAnimationShow == null) {

            alphaAnimationShow = new AlphaAnimation(0, 1);
            alphaAnimationShow.setDuration(2000);
            alphaAnimationShow.setFillAfter(true);
            view.startAnimation(alphaAnimationShow);

        }
    }

    public void controlAnimation(View view){

        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                Message message = handler.obtainMessage();
                message.what = 0;
                handler.sendMessage(message);
                if (timer != null){

                    timer.cancel();
                    timer = null;
                }
            }
        };
        if (timer == null){

            timer = new Timer();
            timer.schedule(timerTask,1500,5000);
        }

    }

    public void clearAnimationControl(View view){

        if (view != null){

            if (alphaAnimationDismiss != null || alphaAnimationShow != null){
                alphaAnimationDismiss = null;
                alphaAnimationShow = null;
            }
        }
    }
}
