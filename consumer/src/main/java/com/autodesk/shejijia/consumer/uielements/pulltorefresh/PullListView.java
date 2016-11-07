package com.autodesk.shejijia.consumer.uielements.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @author  luchongbin .
 * @version 1.0 .
 * @date    16-6-13 上午10:14
 * @file    PullableListView.java  .
 * @brief    自定义 ListView.
 */
public class PullListView extends ListView implements Pullable
{

    private boolean isCanRefresh = true;
    ///构造方法.
    public PullListView(Context context)
    {
        super(context);
    }
    ///构造方法.
    public PullListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    ///构造方法.
    public PullListView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canPullDown()
    {
//        if (getCount() == 0)
//        {
//            // 没有item的时候也可以下拉刷新
//            return true;
//        } else // 滑到ListView的顶部了
//            return getFirstVisiblePosition() == 0
//                    && getChildAt(0).getTop() >= 0;
        return setIsCanRefresh();
    }

    @Override
    public boolean canPullUp()
    {
        if (getCount() == 0)
        {
            // 没有item的时候也可以上拉加载
            return true;
        } else if (getLastVisiblePosition() == (getCount() - 1))
        {
            // 滑到底部了
            if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
                    && getChildAt(
                    getLastVisiblePosition()
                            - getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
                return true;
        }
        return false;
    }
    /**
     增加可手动控制的刷新功能控制
    * 修改人：xuehua.yao
    */
    public boolean setIsCanRefresh(){

        if (isCanRefresh){


            if (getCount() == 0)
            {
                // 没有item的时候也可以下拉刷新
                return true;
            } else // 滑到ListView的顶部了
                return getFirstVisiblePosition() == 0
                        && getChildAt(0).getTop() >= 0;
        }else {
            return false;
        }
    }

    /**
     设置外层调用。让刷新功能不可用
     *修改人：xuehua.yao
    */
    public void setCanRefresh(boolean refresh){

        this.isCanRefresh = refresh;
    }


}
