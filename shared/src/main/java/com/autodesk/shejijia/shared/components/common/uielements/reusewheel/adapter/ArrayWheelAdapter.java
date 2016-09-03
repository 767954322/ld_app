package com.autodesk.shejijia.shared.components.common.uielements.reusewheel.adapter;

/**
 * Created by yaoxuehua on 16-6-17.
 * 自定义轮子复用滚轮adapter
 * ArrayWheelAdapter
 */
import java.util.ArrayList;

/**
 * The simple Array wheel adapter
 * @param <T> the element type
 */
public class ArrayWheelAdapter<T> implements WheelAdapter {

    /** 默认的item的长度 */
    public static final int DEFAULT_LENGTH = 4;

    // items
    private ArrayList<T> items;
    // length
    private int length;

    /**
     * Constructor
     * @param items the items
     * @param length the max items length
     */
    public ArrayWheelAdapter(ArrayList<T> items, int length) {
        this.items = items;
        this.length = length;
    }

    /**
     * Contructor
     * @param items the items
     */
    public ArrayWheelAdapter(ArrayList<T> items) {
        this(items, DEFAULT_LENGTH);
    }

    /**获取item上数值*/
    @Override
    public Object getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return "";
    }

    @Override
    public int getItemsCount() {
        return items.size();
    }

    @Override
    public int indexOf(Object o){
        return items.indexOf(o);
    }

}