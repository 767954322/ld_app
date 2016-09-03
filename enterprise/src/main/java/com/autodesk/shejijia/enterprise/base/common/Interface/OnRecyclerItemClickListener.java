package com.autodesk.shejijia.enterprise.base.common.Interface;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by t_xuz on 8/17/16.
 * recyclerView 的点击,长按监听接口
 */
public abstract class OnRecyclerItemClickListener implements RecyclerView.OnItemTouchListener{

    private GestureDetectorCompat mGestureDetector;
    private RecyclerView mRecyclerView;

    public OnRecyclerItemClickListener(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
        this.mGestureDetector = new GestureDetectorCompat(mRecyclerView.getContext(),new ItemTouchHelperGestureListener());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent   e) {
        mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onSingleTapUp(MotionEvent e) {//单击
            View child = mRecyclerView.findChildViewUnder(e.getX(),e.getY());
            if (child!=null){
                RecyclerView.ViewHolder viewHolder = mRecyclerView.getChildViewHolder(child);
                onItemClick(viewHolder,viewHolder.getAdapterPosition());
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {//长按
            View child = mRecyclerView.findChildViewUnder(e.getX(),e.getY());
            if (child != null){
                RecyclerView.ViewHolder viewHolder = mRecyclerView.getChildViewHolder(child);
                onItemLongClick(viewHolder,viewHolder.getAdapterPosition());
            }
        }
    }

    public abstract void onItemClick(RecyclerView.ViewHolder viewHolder, int position);
    public abstract void onItemLongClick(RecyclerView.ViewHolder viewHolder, int position);

}
