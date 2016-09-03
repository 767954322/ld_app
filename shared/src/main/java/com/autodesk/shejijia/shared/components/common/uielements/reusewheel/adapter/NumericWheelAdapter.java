package com.autodesk.shejijia.shared.components.common.uielements.reusewheel.adapter;

/**
 * Created by yaoxuehua on 16-6-17.
 * 自定义轮子复用滚轮adapter
 * NumericWheelAdapter
 */
/**
 * Numeric Wheel adapter.
 */
public class NumericWheelAdapter implements WheelAdapter {

    /** 默认的最大数值 */
    public static final int DEFAULT_MAX_VALUE = 9;

    /** 默认的最小数值*/
    private static final int DEFAULT_MIN_VALUE = 0;

    // 最大最小数值
    private int minValue;
    private int maxValue;

    /**
     * Default constructor，构造
     */
    public NumericWheelAdapter() {
        this(DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE);
    }

    /**
     * Constructor
     * @param minValue the wheel min value
     * @param maxValue the wheel max value
     */
    public NumericWheelAdapter(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public Object getItem(int index) {
        if (index >= 0 && index < getItemsCount()) {
            int value = minValue + index;
            return value;
        }
        return 0;
    }

    @Override
    public int getItemsCount() {
        return maxValue - minValue + 1;
    }

    @Override
    public int indexOf(Object o){
        return (int)o - minValue;
    }
}

