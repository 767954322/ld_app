package com.autodesk.shejijia.consumer.uielements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-6-8 .
 * @file DeliverySelector.java .
 * @brief 交付物选择全局控制类 .
 */
public class DeliverySelector {
    public static int max = 0;
    /**
     * 选择的design_asset_id
     */
    public static String select_design_asset_id = new String();
    public static String sLink = new String();
    /**
     * 多选的design_file_id
     * key  第几个条目
     * value 每个条目的值
     */
    public static Map<Integer, ArrayList<String>> select_design_file_id_map = new HashMap<>();

    public static boolean isSelected = false;

    private DeliverySelector() {
    }
}
