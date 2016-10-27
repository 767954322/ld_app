package com.autodesk.shejijia.consumer.frame.http.interfaces;

import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendDetailsEntity;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @Author: lizhipeng
 * @Data: 16/10/25 下午4:37
 * @Description:
 */

public interface INetInterface {


    /**
     * post 请求
     * @param body javabean请求体
     * @return
     */
//    @POST("")
//    Observable<WeatherResponse> postWeather(@Body WeatherRequest body);

    /**
     * post请求
     * @param params map请求体
     * @return
     */
//    @POST("")
//    Observable<WeatherResponse> postWeather2(@FieldMap Map<String, String> params);


    /**
     * 设计师清单详情
     * @param assetId
     * @return
     */
    @GET("recommends/{assetId}")
    Observable<RecommendDetailsEntity> getRecommendDetails(@Path("assetId") String assetId);
}
