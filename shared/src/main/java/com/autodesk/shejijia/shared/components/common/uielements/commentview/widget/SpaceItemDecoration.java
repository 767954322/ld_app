package com.autodesk.shejijia.shared.components.common.uielements.commentview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.CommentConfig;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

//    private static final int[] ATTRS = new int[] { android.R.attr.listDivider };

    private int space;
    private int mColumn;

    public SpaceItemDecoration(int space, int column) {
        this.space = space;
        this.mColumn = column;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
        outRect.left = space;
        outRect.bottom = space;
        //把左边距设为0
        if (parent.getChildLayoutPosition(view) % mColumn == 0) {
            outRect.left = 0;
        }
    }
}
