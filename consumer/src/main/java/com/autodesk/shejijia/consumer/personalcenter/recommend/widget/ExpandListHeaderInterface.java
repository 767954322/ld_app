package com.autodesk.shejijia.consumer.personalcenter.recommend.widget;

import android.view.View;

/**
 * Created by liuhe on 16-11-4.
 */

public interface ExpandListHeaderInterface {
    int PINNED_HEADER_GONE = 0;
    int PINNED_HEADER_VISIBLE = 1;
    int PINNED_HEADER_PUSHED_UP = 2;

    /**
     * 获取 Header 的状态
     *
     * @param groupPosition
     * @param childPosition
     * @return PINNED_HEADER_GONE, PINNED_HEADER_VISIBLE, PINNED_HEADER_PUSHED_UP 其中之一
     */
    int getHeaderState(int groupPosition, int childPosition);

    /**
     * 配置 Header, 让 Header 知道显示的内容
     *
     * @param header
     * @param groupPosition
     * @param childPosition
     * @param alpha
     */
    void configureHeader(View header, int groupPosition, int childPosition, int alpha);

    /**
     * 设置组按下的状态
     *
     * @param groupPosition
     * @param status
     */
    void setGroupClickStatus(int groupPosition, int status);

    /**
     * 获取组按下的状态
     *
     * @param groupPosition
     * @return
     */
    int getGroupClickStatus(int groupPosition);
}
