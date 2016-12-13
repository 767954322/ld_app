package com.autodesk.shejijia.shared.components.common.network;

import android.os.Handler;
import android.os.Looper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
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

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class FileHttpManager {

    public interface ResponseHandler {
        void onSuccess(String response);

        void onFailure();
    }

    private static RequestQueue queue;
    private static FileHttpManager instance = new FileHttpManager();

    public static FileHttpManager getInstance() {
        if (queue == null)
            queue = AdskApplication.getInstance().queue;

        return instance;
    }

    /**
     * 为X-Token 增加前缀
     *
     * @param xToken
     * @return
     */
    private String addX_Token(String xToken) {
        return Constant.NetBundleKey.X_TOKEN_PREFIX + xToken;
    }

    public void getUploadServer(OkStringRequest.OKResponseCallback callback) {
        String url = UrlMessagesContants.StrHttpServicerootCn +
                "/server/upload";

        queue.add(new OkStringRequest(Request.Method.GET, url, callback) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getDefaultHeaders();
            }
        });
    }


    public void getDownloadServer(OkStringRequest.OKResponseCallback callback) {
        String url = UrlMessagesContants.StrHttpServicerootCn +
                "/server/download";

        queue.add(new OkStringRequest(Request.Method.GET, url, callback) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getDefaultHeaders();
            }
        });
    }

    public void upLoadFileByType(final File file, final String mediaType, //AUDIO or IMAGE
                                 final ResponseHandler handler) {
        Assert.assertTrue(file != null);

        // first get the upload server
        getUploadServer(new OkStringRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                handler.onFailure();
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    final String uploadServer = jObj.optString("server");

                    String postURL = "http://" + uploadServer + "/api/v2/files/upload";

                    // the types are implicitly taken, we may need to revisit this again
                    String type = "image/jpg";
                    if (mediaType.equalsIgnoreCase("AUDIO"))
                        type = "audio/x-m4a";

                    RequestBody fileBody = RequestBody.create(MediaType.parse(type), file);
                    MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
                    builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"file\"; filename=\"" + file.getName() + "\""), fileBody);

                    HashMap<String, String> params = new HashMap<String, String>();

                    params.put("content_type", mediaType);
                    params.put("X-AFC", UrlMessagesContants.initializeMarketplaceWithAFC);
                    params.put("X-SESSION", AdskApplication.getInstance().getMemberEntity().getAcs_x_session());
                    params.put("public", "true");
                    for (Map.Entry<String, String> entry : params.entrySet())
                        builder.addFormDataPart(entry.getKey(), entry.getValue());
                    RequestBody reqBody = builder.build();

                    com.squareup.okhttp.Request.Builder reqBuilder = new com.squareup.okhttp.Request.Builder();
                    reqBuilder.url(postURL);
                    reqBuilder.post(reqBody);
                    Map<String, String> map = getDefaultHeaders();
                    for (Map.Entry<String, String> entry : map.entrySet())
                        reqBuilder.addHeader(entry.getKey(), entry.getValue());
                    com.squareup.okhttp.Request request = reqBuilder.build();

                    OkHttpClient okHttpClient = new OkHttpClient();
                    okHttpClient.setConnectTimeout(120, TimeUnit.SECONDS);
                    okHttpClient.setWriteTimeout(120, TimeUnit.SECONDS);
                    okHttpClient.setReadTimeout(120, TimeUnit.SECONDS);

                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(com.squareup.okhttp.Request request, IOException e) {
                            errorHandler(handler);
                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            if (response.isSuccessful())
                                successHandler(handler, response.body().string());
                            else
                                errorHandler(handler);
                        }
                    });
                } catch (JSONException e) {
                    handler.onFailure();
                } catch (Exception e) {
                    handler.onFailure();
                }
            }
        });
    }


    public void upLoadFileByType(final List<File> files, final String mediaType, //AUDIO or IMAGE
                                 final ResponseHandler handler) {
        Assert.assertTrue(files != null);

        // first get the upload server
        getUploadServer(new OkStringRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                handler.onFailure();
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    final String uploadServer = jObj.optString("server");

                    String postURL = "http://" + uploadServer + "/api/v2/server/upload";

                    // the types are implicitly taken, we may need to revisit this again
                    String type = "image/jpg";
                    if (mediaType.equalsIgnoreCase("AUDIO")){
                        type = "audio/x-m4a";
                    }

                    MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
                    for(File file : files){
                        RequestBody fileBody = RequestBody.create(MediaType.parse(type), file);
                        builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"file\"; filename=\"" + file.getName() + "\""), fileBody);
                    }

                    HashMap<String, String> params = new HashMap<String, String>();

                    params.put("content_type", mediaType);
                    for (Map.Entry<String, String> entry : params.entrySet()){
                        builder.addFormDataPart(entry.getKey(), entry.getValue());
                    }
                    RequestBody reqBody = builder.build();

                    com.squareup.okhttp.Request.Builder reqBuilder = new com.squareup.okhttp.Request.Builder();
                    reqBuilder.url(postURL);
                    reqBuilder.post(reqBody);
                    Map<String, String> map = getDefaultHeaders();
                    for (Map.Entry<String, String> entry : map.entrySet()){
                        reqBuilder.addHeader(entry.getKey(), entry.getValue());
                    }
                    com.squareup.okhttp.Request request = reqBuilder.build();

                    OkHttpClient okHttpClient = new OkHttpClient();
                    okHttpClient.setConnectTimeout(120, TimeUnit.SECONDS);
                    okHttpClient.setWriteTimeout(120, TimeUnit.SECONDS);
                    okHttpClient.setReadTimeout(120, TimeUnit.SECONDS);

                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(com.squareup.okhttp.Request request, IOException e) {
                            errorHandler(handler);
                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            if (response.isSuccessful())
                                successHandler(handler, response.body().string());
                            else
                                errorHandler(handler);
                        }
                    });
                } catch (JSONException e) {
                    handler.onFailure();
                } catch (Exception e) {
                    handler.onFailure();
                }
            }
        });
    }

    private void errorHandler(final ResponseHandler resHandler) {
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                resHandler.onFailure();
            }
        });
    }


    private void successHandler(final ResponseHandler resHandler, final String res) {
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                resHandler.onSuccess(res);
            }
        });
    }


    public void downloadFileFromURL(final String url, final String filePath, boolean isPublicURL, final ResponseHandler resHandler) {
        try {
            OkHttpClient client = new OkHttpClient();
            com.squareup.okhttp.Request.Builder builder = new com.squareup.okhttp.Request.Builder().url(new URL(url));

            // add default headers is it's not a public URL
            if (!isPublicURL) {
                Map<String, String> map = getDefaultHeaders();
                for (Map.Entry<String, String> entry : map.entrySet())
                    builder.addHeader(entry.getKey(), entry.getValue());
            }
            com.squareup.okhttp.Request request = builder.build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(com.squareup.okhttp.Request request, IOException e) {
                    errorHandler(resHandler);
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    if (response.isSuccessful()) {
                        InputStream is = response.body().byteStream();

                        BufferedInputStream input = new BufferedInputStream(is);
                        OutputStream output = new FileOutputStream(new File(filePath));

                        byte[] data = new byte[1024];

                        long total = 0;
                        int count = 0;
                        while ((count = input.read(data)) != -1) {
                            total += count;
                            output.write(data, 0, count);
                        }

                        output.flush();
                        output.close();
                        input.close();

                        successHandler(resHandler, null);
                    } else
                        errorHandler(resHandler);
                }
            });
        } catch (Exception e) {
            errorHandler(resHandler);
        }

    }

    public void downloadFileId(final String fileId, final String filePath, final ResponseHandler resHandler) {
        Assert.assertTrue(fileId != null && !fileId.isEmpty());
        getDownloadServer(new OkStringRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                errorHandler(resHandler);
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    final String downloadServer = jObj.optString("server");

                    String getFileURL = "http://" + downloadServer + "/api/v2/files/download?file_ids=" + fileId;

                    downloadFileFromURL(getFileURL, filePath, false, resHandler);
                } catch (Exception e) {
                    errorHandler(resHandler);
                }
            }
        });
    }

    private Map<String, String> getDefaultHeaders() {
        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/x-www-form-urlencoded");
        map.put("X-AFC", UrlMessagesContants.initializeMarketplaceWithAFC);
        if (AdskApplication.getInstance().getMemberEntity() != null){
            map.put("X-Session", AdskApplication.getInstance().getMemberEntity().getAcs_x_session());
        }
        return map;
    }
}
