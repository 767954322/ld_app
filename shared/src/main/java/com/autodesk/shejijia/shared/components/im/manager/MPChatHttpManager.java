package com.autodesk.shejijia.shared.components.im.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.components.common.appglobal.UrlConstants;
import com.autodesk.shejijia.shared.components.common.utility.DeviceUtils;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.components.common.appglobal.ApiManager;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.UrlMessagesContants;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
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
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author yangxuewu .
 * @version 1.0 .
 * @date 16-6-18
 * @file MPChatHttpManager.java  .
 * @brief .
 */
public class MPChatHttpManager {
    public interface ResponseHandler {
        void onSuccess(String response);

        void onFailure();
    }

    private static RequestQueue queue;
    private static MPChatHttpManager instance = new MPChatHttpManager();

    public static MPChatHttpManager getInstance() {
        if (queue == null)
            queue = AdskApplication.getInstance().queue;

        return instance;
    }

    public String getConnectWebSocketUrl(
            Context context) {
        String deviceID = DeviceUtils.getDeviceID(context);
        String WebSocketUrl = UrlMessagesContants.ConnectWebSocketUrl +
                "sessionId=" + AdskApplication.getInstance().getMemberEntity().getAcs_x_session() +
                "&memberId=" + AdskApplication.getInstance().getMemberEntity().getAcs_member_id() +
                "&appId=" + UrlMessagesContants.appID +
                "&deviceType=ANDROID&deviceId=" + deviceID + "&messageVersion=v1";
        return WebSocketUrl;
    }

    public void retrieveMemberThreads(String memberId, boolean onlyAttachedToFile,
                                      int offset, int limit,
                                      OkStringRequest.OKResponseCallback callback) {
//        Assert.assertTrue(memberId != null && !memberId.isEmpty());

        String entityTypes = "ASSET,NONE";

        if (onlyAttachedToFile)
            entityTypes = "FILE";

        String url = UrlMessagesContants.StrHttpServicerootCn +
                "/members/" + memberId +
                "/threads?" +
                "mailbox=CHAT" +
                "&latest_message_info=true" +
                "&sort_order=recent" +
                "&offset=" + String.valueOf(offset) +
                "&limit=" + String.valueOf(limit) +
                "&entity_info=true" +
                "&entity_types=" + entityTypes;

        LogUtils.e("im-requestUrl-> ",url);

        queue.add(new OkStringRequest(Request.Method.GET, url, callback) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getDefaultHeaders();
            }
        });
    }


    public void retrieveMultipleMemberThreads(String recipientsIds, // comma seperated ids
                                              int offset, int limit,
                                              OkStringRequest.OKResponseCallback callback) {
//        Assert.assertTrue(recipientsIds != null);

        String url = UrlMessagesContants.StrHttpServicerootCn +
                "/members" +
                "/threads" +
                "?recipient_ids=" + recipientsIds +
                "&latest_message_info=true" +
                "&sort_order=desc" +
                "&offset=" + String.valueOf(offset) +
                "&limit=" + String.valueOf(limit) +
                "&entity_info=true" +
                "&entity_types=" + "NONE";

        Log.d("test", url + "");
        queue.add(new OkStringRequest(Request.Method.GET, url, callback) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getDefaultHeaders();
            }
        });
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


    public void retrieveThreadMessages(String memberId, String threadId,
                                       int offset, int limit,
                                       OkStringRequest.OKResponseCallback callback) {
//        Assert.assertTrue(memberId != null && !memberId.isEmpty());
//        Assert.assertTrue(threadId != null && !threadId.isEmpty());

        String url = UrlMessagesContants.StrHttpServicerootCn +
                "/members/" + memberId +
                "/messages/" + threadId +
                "?mailbox=IN" +
                "&sort_order=desc" +
                "&offset=" + String.valueOf(offset) +
                "&limit=" + String.valueOf(limit);
        LogUtils.e("im-requestUrl-> ",url);
        queue.add(new OkStringRequest(Request.Method.GET, url, callback) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getDefaultHeaders();
            }
        });
    }


    public void retrieveMemberUnreadMessageCount(String memberId, boolean needAllMessages,
                                                 OkStringRequest.OKResponseCallback callback) {
//        Assert.assertTrue(memberId != null && !memberId.isEmpty());

        String entityTypes = "FILE";

        if (needAllMessages)
            entityTypes = "NONE,ASSET,FILE,WORKFLOW_STEP";

        String url = UrlMessagesContants.StrHttpServicerootCn +
                "/members/" + memberId +
                "/messages" + "/counts" +
                "?mailbox=CHAT" +
                "&entity_types=" + entityTypes;

        queue.add(new OkStringRequest(Request.Method.GET, url, callback) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getDefaultHeaders();
            }
        });
    }


    public void retrieveFileUnreadMessageCount(String memberId, String fileId,
                                               OkStringRequest.OKResponseCallback callback) {
//        Assert.assertTrue(memberId != null && !memberId.isEmpty());
//        Assert.assertTrue(fileId != null && !fileId.isEmpty());

        String entityTypes = "FILE";

        String url = UrlMessagesContants.StrHttpServicerootCn +
                "/members/" + memberId +
                "/messages" + "/counts" +
                "?mailbox=CHAT" +
                "&entity_types=" + entityTypes +
                "&entity_ids=" + fileId;

        queue.add(new OkStringRequest(Request.Method.GET, url, callback) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getDefaultHeaders();
            }
        });
    }


    public void retrieveAllHotspotUnreadmessageCount(String memberId, String threadId,
                                                     OkStringRequest.OKResponseCallback callback) {
//        Assert.assertTrue(memberId != null && !memberId.isEmpty());
//        Assert.assertTrue(threadId != null && !threadId.isEmpty());

        String url = UrlMessagesContants.StrHttpServicerootCn +
                "/members/" + memberId +
                "/messages/" + threadId +
                "/media/counts";

        queue.add(new OkStringRequest(Request.Method.GET, url, callback) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getDefaultHeaders();
            }
        });
    }


    public void retrieveMediaMessages(String memberId, String threadId,
                                      int offset, int limit,
                                      OkStringRequest.OKResponseCallback callback) {
//        Assert.assertTrue(memberId != null && !memberId.isEmpty());
//        Assert.assertTrue(threadId != null && !threadId.isEmpty());

        String url = UrlMessagesContants.StrHttpServicerootCn +
                "/members/" + memberId +
                "/messages" +
                "/media" +
                "?sort_order=desc" +
                "&offset=" + String.valueOf(offset) +
                "&limit=" + String.valueOf(limit) +
                "&message_media_types=" + "IMAGE" +
                "&thread_ids=" + threadId;

        queue.add(new OkStringRequest(Request.Method.GET, url, callback) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getDefaultHeaders();
            }
        });
    }


    public void retrieveThreadDetails(String memberId, String threadId,
                                      OkStringRequest.OKResponseCallback callback) {
//        Assert.assertTrue(memberId != null && !memberId.isEmpty());
//        Assert.assertTrue(threadId != null && !threadId.isEmpty());

        String url = UrlMessagesContants.StrHttpServicerootCn +
                "/members/" + memberId +
                "/threads/" + threadId +
                "?latest_message_info=true" +
                "&entity_info=true";

        queue.add(new OkStringRequest(Request.Method.GET, url, callback) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getDefaultHeaders();
            }
        });
    }


    public void retrieveFileConversations(String fileId,
                                          OkStringRequest.OKResponseCallback callback) {
//        Assert.assertTrue(fileId != null && !fileId.isEmpty());

        String url = UrlMessagesContants.StrHttpServicerootCn +
                "/entity" +
                "/conversations" +
                "?entity_types=FILE" +
                "&entity_ids=" + fileId +
                "&thread_info=true";

        queue.add(new OkStringRequest(Request.Method.GET, url, callback) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getDefaultHeaders();
            }
        });
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


    public void sendNewThreadMessage(String memberId, String receipentId,
                                     final String messageText, final String subject,
                                     OkStringRequest.OKResponseCallback callback) {
//        Assert.assertTrue(memberId != null && !memberId.isEmpty());
//        Assert.assertTrue(receipentId != null && !receipentId.isEmpty());
//        Assert.assertTrue(messageText != null && !messageText.isEmpty());
//        Assert.assertTrue(subject != null && !subject.isEmpty());

        String url = UrlMessagesContants.StrHttpServicerootCn +
                "/members/" + memberId +
                "/messages";
        final String receipentIds = receipentId + "," + ApiManager.ADMIN_USER_ID;

        queue.add(new OkStringRequest(Request.Method.POST, url, callback) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getDefaultHeaders();
            }

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("body", messageText);
                map.put("subject", subject);
                map.put("recipient_ids", receipentIds);
                map.put("mailbox", "CHAT");
                map.put("app_id", UrlMessagesContants.appID);
                return map;
            }
        });

    }

    public void replyToThread(String memberId, final String threadId,
                              final String messageText,
                              OkStringRequest.OKResponseCallback callback) {
//        Assert.assertTrue(memberId != null && !memberId.isEmpty());
//        Assert.assertTrue(threadId != null && !threadId.isEmpty());
//        Assert.assertTrue(messageText != null && !messageText.isEmpty());

        String url = UrlMessagesContants.StrHttpServicerootCn +
                "/members/" + memberId +
                "/messages/reply";

        queue.add(new OkStringRequest(Request.Method.POST, url, callback) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getDefaultHeaders();
            }

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("body", messageText);
                map.put("thread_id", threadId);
                map.put("app_id", UrlMessagesContants.appID);
                return map;
            }
        });
    }


    public void markThreadAsRead(String memberId, final String threadId,
                                 OkStringRequest.OKResponseCallback callback) {
//        Assert.assertTrue(memberId != null && !memberId.isEmpty());
//        Assert.assertTrue(threadId != null && !threadId.isEmpty());
//        Assert.assertEquals(memberId != null && !memberId.isEmpty(),threadId != null && !threadId.isEmpty());

        String url = UrlMessagesContants.StrHttpServicerootCn +
                "/members/" + memberId +
                "/messages" +
                "?action=read" +
                "&thread_id=" + threadId;

        queue.add(new OkStringRequest(Request.Method.PUT, url, callback) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getDefaultHeaders();
            }
        });
    }


    public void markMessageAsRead(String memberId, final String msgId,
                                  OkStringRequest.OKResponseCallback callback) {
//        Assert.assertTrue(memberId != null && !memberId.isEmpty());
//        Assert.assertTrue(msgId != null && !msgId.isEmpty());

        String url = UrlMessagesContants.StrHttpServicerootCn +
                "/members/" + memberId +
                "/messages" +
                "?action=read" +
                "&message_id=" + msgId;

        queue.add(new OkStringRequest(Request.Method.PUT, url, callback) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getDefaultHeaders();
            }
        });
    }


    public void addConversationToFile(String fileId, final String threadId,
                                      final int xCoordinate, final int yCoordinate,
                                      OkStringRequest.OKResponseCallback callback) {
//        Assert.assertTrue(fileId != null && !fileId.isEmpty());
//        Assert.assertTrue(threadId != null && !threadId.isEmpty());

        String url = UrlMessagesContants.StrHttpServicerootCn +
                "/entity/" + fileId +
                "/conversations";

        queue.add(new OkStringRequest(Request.Method.POST, url, callback) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getDefaultHeaders();
            }

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("thread_id", threadId);
                map.put("entity_type", "FILE");
                map.put("x_coordinate", String.valueOf(xCoordinate));
                map.put("y_coordinate", String.valueOf(yCoordinate));
                map.put("z_coordinate", String.valueOf(0));
                return map;
            }
        });
    }


    public void sendMediaMessage(final String memberId, final String recipientId,
                                 final String subject, final String threadId,
                                 final File file, final String mediaType, //AUDIO or IMAGE
                                 final ResponseHandler handler) {
//        Assert.assertTrue(!memberId.equalsIgnoreCase(recipientId));
//        Assert.assertTrue(file != null);

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

                    String postURL = "http://" + uploadServer + "/api/v2/members/" + memberId + "/chat/media";

                    // the types are implicitly taken, we may need to revisit this again
                    String type = "image/jpg";
                    if (mediaType.equalsIgnoreCase("AUDIO"))
                        type = "audio/x-m4a";

                    RequestBody fileBody = RequestBody.create(MediaType.parse(type), file);
                    MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
                    builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"file\"; filename=\"" + file.getName() + "\""), fileBody);

                    HashMap<String, String> params = new HashMap<String, String>();
                    if (threadId != null)
                        params.put("thread_id", threadId);
                    else if (recipientId != null) {
//                        Assert.assertTrue(subject != null && !subject.isEmpty());
                        params.put("subject", subject);
                        params.put("recipient_ids", recipientId);
                        params.put("app_id", UrlMessagesContants.appID);
                    }
                    params.put("content_type", mediaType);
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


    public void addFileToAsset(String fileId, String assetId, OkStringRequest.OKResponseCallback callback) {
//        Assert.assertTrue(fileId != null && !fileId.isEmpty());
//        Assert.assertTrue(assetId != null && !assetId.isEmpty());

        String url = UrlMessagesContants.StrHttpServicerootCn +
                "/assets/" + assetId +
                "/sources" +
                "?file_ids=" + fileId;

        queue.add(new OkStringRequest(Request.Method.POST, url, callback) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getDefaultHeaders();
            }
        });
    }


    public void addFileToWorkflowStep(String fileId, String assetId, String workflowId,
                                      String workflowStepId, OkStringRequest.OKResponseCallback callback) {
//        Assert.assertTrue(fileId != null && !fileId.isEmpty());
//        Assert.assertTrue(assetId != null && !assetId.isEmpty());
//        Assert.assertTrue(workflowId != null && !workflowId.isEmpty());
//        Assert.assertTrue(workflowStepId != null && !workflowStepId.isEmpty());

        String url = UrlMessagesContants.StrHttpServicerootCn +
                "/assets/" + assetId +
                "/workflows/" + workflowId +
                "/steps/" + workflowStepId +
                "?file_ids=" + fileId;

        queue.add(new OkStringRequest(Request.Method.PUT, url, callback) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getDefaultHeaders();
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
//        Assert.assertTrue(fileId != null && !fileId.isEmpty());
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
        if (AdskApplication.getInstance().getMemberEntity() != null)
            map.put("X-Session", AdskApplication.getInstance().getMemberEntity().getAcs_x_session());
        return map;
    }

    public void getThreadIdIfNotChatBefore(final String acs_number_id, final String desiner_id, OkStringRequest.OKResponseCallback callback) {

        String url = UrlConstants.MAIN_DESIGN + "/chat/" + acs_number_id + "/" + desiner_id;
        queue.add(new OkStringRequest(Request.Method.POST, url, callback) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getDefaultHeaders();
            }

        });
    }
}
