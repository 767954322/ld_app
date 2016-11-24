package com.autodesk.shejijia.shared.components.common.uielements.swiperecyclerview.footerview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by t_xuz on 11/24/16.
 */

public abstract class BaseFooterView extends FrameLayout implements FooterViewListener {

    public BaseFooterView(Context context) {
        super(context);
    }

    public BaseFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
