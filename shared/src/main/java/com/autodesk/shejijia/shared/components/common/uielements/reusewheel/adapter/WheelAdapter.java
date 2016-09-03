package com.autodesk.shejijia.shared.components.common.uielements.reusewheel.adapter;

/**
 * Created by yaoxuehua on 16-6-17.
 * 自定义轮子复用滚轮 ，view接口
 * wheel接口
 */
public interface WheelAdapter<T> {
    /**
     * Gets items count
     * @return the count of wheel items
     */
    public int getItemsCount();

    /**
     * Gets a wheel item by index.
     *
     * @param index the item index
     * @return the wheel item text or null
     */
    public T getItem(int index);

    /**
     * Gets maximum item length. It is used to determine the wheel width.
     * If -1 is returned there will be used the default wheel width.
     *
     * @return the maximum item length or -1
     */
    public int indexOf(T o);
}
