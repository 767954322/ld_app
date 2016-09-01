package com.autodesk.shejijia.consumer.manager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.components.common.appglobal.UrlMessagesContants;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.socks.library.KLog;
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
import java.util.UUID;

/**
 * @author yangxuewu .
 * @version v1.0 .
 * @date 2016-6-7 .
 * @file FileManager.java .
 * @brief 文件管理类 .
 */
public class FileManager {

    private FileManager() {
    }

    public static FileManager getInstance() {
        return instance;
    }

    /**
     * get   Upload Download Server
     *
     * @param file
     */
    public void getUploadDownloadServer(final File file, int handlerState) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("software", "96");
            jsonObject.put("public", "true");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.handlerState = handlerState;
        String url = "https://api.acgcn.autodesk.com/api/v2/server/upload";
//        String url = UrlConstants.MAIN_DESIGN+"/v2/server/upload";
        OkJsonRequest okRequest = new OkJsonRequest(Request.Method.GET, url, jsonObject, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError("FileManager", volleyError);
            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                String server;
                try {
                    server = jsonObject.getString("server");
                    uploadFile(server, file);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        AdskApplication.getInstance().queue.add(okRequest);
    }

    public void uploadFile(String server, final File file) {

        final String url = "http://" + server + "/api/v2/files/upload";
        Callback callBack = new Callback() {
            @Override
            public void onFailure(com.squareup.okhttp.Request request, IOException e) {
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String results = response.body().string();
                if (results != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(results);
                        JSONArray files = jsonObject.getJSONArray("files");
                        JSONObject obj = (JSONObject) files.get(0);
                        file_id = obj.getString("file_id");
                        file_name = obj.getString("name");
                        file_url = (String) obj.get("public_url");

                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("file_id", file_id);
                        bundle.putString("file_name", file_name);
                        bundle.putString("file_url", file_url);
                        bundle.putInt("handlerState", handlerState);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    KLog.d("uploadFile " + results);
                } else {
                    KLog.d("uploadFile   fail ");
                }
            }
        };
        sendImageByPost(url, file, callBack, "Certification");
    }


    public void sendImageByPost(
            String serviceUrl,
            File saveFile,
            Callback callback,
            String tag) {

        String BOUNDARY = UUID.randomUUID().toString();
        String CONTENT_TYPE = "multipart/form-data";

        RequestBody fileBody = RequestBody.create(MediaType.parse("audio/x-m4a"), saveFile);
        RequestBody requestBody = null;
        com.squareup.okhttp.Request request = null;
        if ("IM".equals(tag)) {
            serviceUrl = "http://" + serviceUrl + "/api/v2/members/" +
                    AdskApplication.getInstance().getMemberEntity().getAcs_member_id() + "/chat/media";
            requestBody = new MultipartBuilder().type(MultipartBuilder.FORM)
                    .addPart(Headers.of("Content-Disposition", "form-data; name=\"file\"; filename=\"" + saveFile.getName() + "\""), fileBody)
                    .addFormDataPart("content_type", "AUDIO")
                    .addFormDataPart("app_id", UrlMessagesContants.appID)
                    .addFormDataPart("subject", "NEW")
                    .addFormDataPart("recipient_ids", String.valueOf(0))///To do set from API parameter.
                    .build();
            assert (false);
            request = new com.squareup.okhttp.Request
                    .Builder()
                    .url(serviceUrl)
                    .post(requestBody)
                    .addHeader("X-AFC", UrlMessagesContants.initializeMarketplaceWithAFC)
                    .addHeader("X-SESSION", AdskApplication.getInstance().getMemberEntity().getAcs_x_session() ).build();
        } else if ("Certification".equals(tag)) {
            requestBody = new MultipartBuilder().type(MultipartBuilder.FORM)
                    .addPart(Headers.of("Content-Disposition", "form-data; name=\"file\"; filename=\"" + saveFile.getName() + "\""), fileBody)
                    .addFormDataPart("type", "image/png")
                    .addFormDataPart("software", "96")
                    .addFormDataPart("public", "true")
                    .build();
            request = new com.squareup.okhttp.Request.Builder().url(serviceUrl)
                    .post(requestBody)
                    .addHeader("X-Session", AdskApplication.getInstance().getMemberEntity().getAcs_x_session() )
                    .addHeader("X-AFC", UrlMessagesContants.initializeMarketplaceWithAFC)
                    .addHeader("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY)
                    .build();

        }

        OkHttpClient okHttpClient1 = new OkHttpClient();
        Call call = okHttpClient1.newCall(request);
        call.enqueue(callback);

    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    private int handlerState;
    private String file_id;
    private String file_url;
    private String file_name;
    private Handler handler;
    private static FileManager instance = new FileManager();
}
