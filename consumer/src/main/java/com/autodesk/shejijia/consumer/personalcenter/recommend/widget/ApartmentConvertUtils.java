package com.autodesk.shejijia.consumer.personalcenter.recommend.widget;

import android.app.Activity;

import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.ApartmentConvertBean;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;

import org.json.JSONObject;

import java.util.List;

/**
 * 涉及到推荐模块转换工具类
 *
 * @author liuhea
 *         created at 16-11-17
 */
public class ApartmentConvertUtils {
    private static final String APARTMENT_PATH = "apartment.json";

    /**
     * 通过空间名字获取空间的id
     *
     * @param activity
     * @param name
     * @return
     */
    public static String getApartmentIdFromName(Activity activity, String name) {
        JSONObject jsonObject = AppJsonFileReader.getXmlJsonObject(activity, APARTMENT_PATH);

        ApartmentConvertBean apartmentConvertBean = GsonUtil.jsonToBean(jsonObject.toString(), ApartmentConvertBean.class);
        List<ApartmentConvertBean.ApartmentListBean> apartmentList = apartmentConvertBean.getApartment_list();
        for (ApartmentConvertBean.ApartmentListBean apartment : apartmentList) {
            if (apartment.getName().equals(name)) {
                return apartment.getId();
            }
        }
        return "";
    }

    /**
     * 通过空间id获取空间的name
     *
     * @param activity
     * @param id
     * @return
     */
    public static String getApartmentNameFromId(Activity activity, String id) {
        JSONObject jsonObject = AppJsonFileReader.getXmlJsonObject(activity, APARTMENT_PATH);
        ApartmentConvertBean apartmentConvertBean = GsonUtil.jsonToBean(jsonObject.toString(), ApartmentConvertBean.class);
        List<ApartmentConvertBean.ApartmentListBean> apartmentList = apartmentConvertBean.getApartment_list();
        for (ApartmentConvertBean.ApartmentListBean apartment : apartmentList) {
            if (apartment.getId().equals(id)) {
                return apartment.getName();
            }
        }
        return "";
    }
}
