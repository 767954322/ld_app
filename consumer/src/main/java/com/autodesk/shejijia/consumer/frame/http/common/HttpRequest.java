package com.autodesk.shejijia.consumer.frame.http.common;

import com.autodesk.shejijia.consumer.frame.http.interfaces.INetInterface;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.qy.appframe.common.ResultSubscriber;
import com.qy.appframe.http.HTTPHelper;
import com.qy.appframe.model.IModel;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import rx.Observable;
import rx.Subscriber;

/**
 * @Author: lizhipeng
 * @Data: 16/10/25 下午3:24
 * @Description:
 */

public class HttpRequest {

    public HttpRequest() {
    }

    /**
     * 单例控制器
     */
    private static class SingletonHolder {
        private static final HttpRequest INSTANCE = new HttpRequest();
    }

    /**
     * 获取单例对象
     *
     * @return
     */
    public static HttpRequest getInstance() {
        return HttpRequest.SingletonHolder.INSTANCE;
    }

    /**
     * 为X-Token 增加前缀
     *
     * @return
     */
    private String getX_Token() {
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (memberEntity != null) {
//            return Constant.NetBundleKey.X_TOKEN_PREFIX + memberEntity.getHs_accesstoken();
            return memberEntity.getHs_accesstoken();
        } else {
            return "";
        }
    }

    /**
     * 设计师清单详情
     * @param assetId
     * @param resultType
     * @param listener
     * @param <T>
     * @return
     */
    public <T extends IModel> Subscriber getRecommendDetails(String assetId, int resultType, ResultSubscriber.OnResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("X-Token", getX_Token());
        Headers headers = Headers.of(map);
        Observable<T> observable = (Observable<T>) HTTPHelper.getInstance().init(Constant.Alpha.recommendApp, INetInterface.class, headers).getRecommendDetails(assetId);
        return HTTPHelper.getInstance().doRequest(observable, resultType, listener);
    }
}
