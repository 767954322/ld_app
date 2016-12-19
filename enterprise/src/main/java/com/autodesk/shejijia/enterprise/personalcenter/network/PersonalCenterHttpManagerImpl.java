package com.autodesk.shejijia.enterprise.personalcenter.network;
import android.support.annotation.NonNull;
import com.android.volley.AuthFailureError;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.appglobal.UrlConstants;
import com.autodesk.shejijia.shared.components.common.network.NetRequestManager;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.utility.UserInfoUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by luchongbin on 2016/12/18.
 */

public class PersonalCenterHttpManagerImpl implements PersonalCenterHttpManager {
    private static class ServerHttpManagerHolder {
        private static final PersonalCenterHttpManagerImpl INSTANCE = new PersonalCenterHttpManagerImpl();
    }

    public static PersonalCenterHttpManagerImpl getInstance() {
        return PersonalCenterHttpManagerImpl.ServerHttpManagerHolder.INSTANCE;
    }

    @Override
    public void getPersonalHeadPicPicture(String requestTag, String acsMemberId, @NonNull OkJsonRequest.OKResponseCallback callback) {
        String requestUrl = ConstructionConstants.MEMBER_PATH + "/members/" + acsMemberId;
        get(requestTag, requestUrl, callback);
    }

    @Override
    public void uploadPersonalHeadPicture(File file, @NonNull Callback callback) {
        try {
            uploadFileToServer(file,callback);
        } catch (Exception e) {
            CustomProgress.cancelDialog();
            e.printStackTrace();
        }
    }

    public void uploadFileToServer(File file,final Callback callback) throws Exception {
        if (!file.exists() && file.length() <= 0) {
            return;
        }
        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        OkHttpClient okHttpClient = getOkHttpClient();

        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        String xToken = null;
        if (memberEntity != null) {
            xToken = memberEntity.getHs_accesstoken();
        }
        RequestBody fileBody = RequestBody.create(MEDIA_TYPE_PNG, file);
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        builder.addFormDataPart("file", file.getName(), fileBody);

        RequestBody reqBody = builder.build();
        com.squareup.okhttp.Request.Builder reqBuilder = new com.squareup.okhttp.Request.Builder();
        reqBuilder.url(UrlConstants.URL_PUT_AMEND_DESIGNER_INFO_PHOTO);
        reqBuilder.header(Constant.NetBundleKey.X_TOKEN, Constant.NetBundleKey.X_TOKEN_PREFIX + xToken);
        reqBuilder.put(reqBody);
        okHttpClient.newCall(reqBuilder.build()).enqueue(callback);
//        okHttpClient.newCall(reqBuilder.build()).enqueue(new Callback() {
//            @Override
//            public void onFailure(com.squareup.okhttp.Request request, IOException e) {
////                callback.onErrorResponse();
//                CustomProgress.cancelDialog();
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                CustomProgress.cancelDialog();
////                callback.onResponse();
////                response.isSuccessful()
//            }
//        });
    }

    private void get(String requestTag, String requestUrl, @NonNull OkJsonRequest.OKResponseCallback callback) {
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, requestUrl, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, "Basic " + UserInfoUtils.getToken(AdskApplication.getInstance()));
                return header;
            }

            @Override
            public String getBodyContentType() {
                return Constant.NetBundleKey.APPLICATON_JSON;
            }
        };
        NetRequestManager.getInstance().addRequest(requestTag, okRequest);
    }

    @NonNull
    private OkHttpClient getOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(120, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(120, TimeUnit.SECONDS);
        return okHttpClient;
    }
}
