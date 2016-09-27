package com.autodesk.shejijia.consumer.base.utils;

import android.text.TextUtils;

import com.autodesk.shejijia.shared.components.common.utility.ObjectUtils;

import java.util.Map;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-6-8 .
 * @file ConvertUtils.java .
 * @brief 中英文转换工具类 .
 */
public class ConvertUtils {
    private ConvertUtils() {
    }

    /**
     * 将英文室厅卫转为中文
     *
     * @param map
     * @param key 英文室厅卫
     * @return
     */
    public static String getConvert2CN(Map<String, String> map, String key) {
        String newKey = TextUtils.isEmpty(key) ? "" : key;

        return map.containsKey(key) ? map.get(key) : newKey;
    }

    /**
     * 判断当前Map集合是否为空
     *
     * @param sourceMap 　Map集合
     * @param <K>       Map集合的key
     * @param <V>       Map集合的value
     * @return 如果Map集合为null，或者Map集合的长度为０，返回true, 否则，返回false
     */
    public static <K, V> boolean isEmpty(Map<K, V> sourceMap) {
        return (sourceMap == null || sourceMap.size() == 0);
    }

    /**
     * 通过Map的value值，获取相应第一次匹配的key值
     *
     * @param map   　Map集合
     * @param value 　使用到的value值
     * @param <K>   　Map集合的key
     * @param <V>   　Map集合的key
     * @return <ul>
     * <li>if map is null, return null</li>
     * <li>if value exist, return key</li>
     * <li>return null</li>
     */
    public static <K, V> K getKeyByValue(Map<K, V> map, V value) {
        if (isEmpty(map)) {
            return null;
        }

        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (ObjectUtils.isEquals(entry.getValue(), value)) {
                return entry.getKey();
            }
        }
        return (K) value;
    }
}
