package com.autodesk.shejijia.consumer.utils;

import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Author: lizhipeng
 * @Data: 16/12/8 上午10:45
 * @Description:
 */

public class HttpUtils {

    private static DownloadImageListener mDownloadListener;


    /**
     * 开始下载
     * @param path
     */
    public static void doDownload(String path,DownloadImageListener listener){
        mDownloadListener = listener;
        if (mDownloadListener == null){
            mDownloadListener = new DownloadImageListener() {
                @Override
                public void onStart() {
                }
                @Override
                public void onSuccessed(byte[] data) {
                }
                @Override
                public void onCancelled() {
                }
            };
        }
        new DownloadAs().execute(path);
    }

    static class DownloadAs extends AsyncTask<String,Integer,byte[]>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDownloadListener.onStart();
        }

        @Override
        protected byte[] doInBackground(String... params) {
            InputStream inputStream = null;
            byte[] data = null;
            try {
                //2:把网址封装为一个URL对象
                URL url = new URL(params[0]);

                //3:获取客户端和服务器的连接对象，此时还没有建立连接
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //4:初始化连接对象
                conn.setRequestMethod("GET");
                //设置连接超时
                conn.setConnectTimeout(5000);
                //设置读取超时
                conn.setReadTimeout(5000);
                //5:发生请求，与服务器建立连接
                conn.connect();
                //如果响应码为200，说明请求成功
                if (conn.getResponseCode() == 200) {
                    //获取服务器响应头中的流
                    inputStream = conn.getInputStream();
                    data = getBytes(inputStream);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(byte[] data) {
            super.onPostExecute(data);
            mDownloadListener.onSuccessed(data);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            mDownloadListener.onCancelled();
        }

        @Override
        protected void onCancelled(byte[] data) {
            super.onCancelled(data);
            mDownloadListener.onCancelled();
        }
    }

    /**
     * 将流转为数组
     * @param is
     * @return
     */
    public static byte[] getBytes(InputStream is){
        ByteArrayOutputStream outstream = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[1024]; // 用数据装
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                outstream.write(buffer, 0, len);
            }
            outstream.close();
            is.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return outstream.toByteArray();
    }

    public interface DownloadImageListener{
        void onStart();
        void onSuccessed(byte[] data);
        void onCancelled();
    }

}
