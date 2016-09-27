package com.autodesk.shejijia.consumer.codecorationBase.coelite.entity;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.LinearLayout;

import java.io.IOException;
import java.net.URL;

/**
 * Created by luchongbin on 16-9-27.
 */

public class DownloadImageTask extends AsyncTask<String, Void, Drawable> {
    private LinearLayout llBackground;
    public DownloadImageTask(LinearLayout llBackground){
       this.llBackground = llBackground;
    }

    protected Drawable doInBackground(String... urls) {
        return loadImageFromNetwork(urls[0]);
    }

    protected void onPostExecute(Drawable result) {
        llBackground.setBackgroundDrawable(result);
    }
    private Drawable loadImageFromNetwork(String imageUrl) {
        Drawable drawable = null;
        try {
            // 可以在这里通过第二个参数(文件名)来判断，是否本地有此图片
            drawable = Drawable.createFromStream(new URL(imageUrl).openStream(), null);
        } catch (IOException e) {
            Log.d(getClass().getName(), e.getMessage());
        }
        return drawable;
    }
}