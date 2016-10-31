package com.autodesk.shejijia.shared.components.common.network;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.autodesk.shejijia.shared.components.common.appglobal.UrlMessagesContants;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * Created by t_panya on 16/10/28.
 */

public class FileManager {
    private int handlerState;
    private String file_id;
    private String file_url;
    private String file_name;
    private Handler handler;
    private FileUploadType fileType = FileUploadType.Image;
    private FileManager(){

    }

    private static class FileManagerHolder{
        private static final FileManager INSTANCE = new FileManager();
    }

    public FileManager getInstance(){
        return FileManagerHolder.INSTANCE;
    }

    public enum FileUploadType{
        Audio("Audio"),
        Image("Image"),
        OrdinaryFile("OrdinaryFile");

        private String fileType;

        FileUploadType(String fileType){
            this.fileType = fileType;
        }
        public void setFileType(String fileType){
            this.fileType = fileType;
        }

        public String getFileType(){
            return fileType;
        }
    }

    /**
     * 获取服务器地址
     * @param file
     * @param handlerState
     */
    public void getUploadServer(final File file, int handlerState){
        JSONObject jsonObject = new JSONObject();
        this.handlerState = handlerState;
        String url = "https://api.acgcn.autodesk.com/api/v2/server/upload";
        OkJsonRequest okRequest = new OkJsonRequest(Request.Method.GET, url, jsonObject, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                String server;
                try {
                    server = jsonObject.getString("server");
                    uploadFile(file,server);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 上传文件
     * @param file
     * @param server
     */
    private void uploadFile(final File file,final String server){
        final String url = "http://" + server + "/api/v2/files/upload";
        Callback callback = new Callback() {
            @Override
            public void onFailure(com.squareup.okhttp.Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String result = response.body().toString();
                if(result != null){
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray files = jsonObject.getJSONArray("files");
                        JSONObject subObject = (JSONObject) files.get(0);

                        file_id = subObject.getString("file_id");
                        file_name = subObject.getString("file_name");
                        file_url = subObject.getString("file_url");

                        Message message = handler.obtainMessage();
                        Bundle bundle = new Bundle();
                        bundle.putString("file_id",file_id);
                        bundle.putString("file_name",file_name);
                        bundle.putString("file_url",file_url);
                        bundle.putInt("handlerState",handlerState);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        sendFileByPost(server,file,callback,FileUploadType.Image);
    }

    /**
     * 上传单张图片
     * @param server
     * @param saveFile
     * @param callback
     * @param type
     */
    public void sendFileByPost(String server, File saveFile, Callback callback, FileUploadType type){
        String BOUNDARY = UUID.randomUUID().toString();
        String CONTENT_TYPE = "multipart/form-data";
        RequestBody fileBody = null;
        if("Image".equals(type)){
            fileBody = RequestBody.create(MediaType.parse("image/*"), saveFile);
        } else if ("Audio".equals(type)) {
            fileBody = RequestBody.create(MediaType.parse("audio/x-m4a"), saveFile);
        }

        RequestBody requestBody = null;
        com.squareup.okhttp.Request request = null;
        requestBody = new MultipartBuilder().type(MultipartBuilder.FORM)
                .addPart(Headers.of("Content-Disposition", "form-data; name=\"file\"; filename=\"" + saveFile.getName() + "\""), fileBody)
                .addFormDataPart("type", "image/png")
                .addFormDataPart("software", "96")
                .addFormDataPart("public", "true")
                .build();
        request = new com.squareup.okhttp.Request.Builder().url(server)
                .post(requestBody)
                .addHeader("X-Session", AdskApplication.getInstance().getMemberEntity().getAcs_x_session() )
                .addHeader("X-AFC", UrlMessagesContants.initializeMarketplaceWithAFC)
                .addHeader("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY)
                .build();

        OkHttpClient okHttpClient1 = new OkHttpClient();
        Call call = okHttpClient1.newCall(request);
        call.enqueue(callback);

    }

    public void sendMultiImageFiles(String server, Map<String,Object> map,Callback callback){
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        if(null != map){
            for(Map.Entry<String,Object> entry : map.entrySet()){
                if(entry.getValue() != null){
                    if(entry.getValue() instanceof File){
                        File file = (File) entry.getValue();
                        builder.addFormDataPart(entry.getKey(),file.getName(),RequestBody.create(MediaType.parse("image/*"),file));
                    }
                }
            }
        }
        RequestBody requestBody = builder.build();
        com.squareup.okhttp.Request request = new com.squareup.okhttp.Request
                                                .Builder()
                                                .url(server)
                        // TODO: 16/10/28  add Header
                        //                .addHeader()
                        //                .addHeader()
                                                .post(requestBody)
                                                .build();
        OkHttpClient okHttpClient1 = new OkHttpClient();
        Call call = okHttpClient1.newCall(request);
        call.enqueue(callback);


    }


    public void setHandler(Handler handler){
        this.handler = handler;
    }

}
