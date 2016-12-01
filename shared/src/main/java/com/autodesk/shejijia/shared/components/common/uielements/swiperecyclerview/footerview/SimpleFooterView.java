package com.autodesk.shejijia.shared.components.common.uielements.swiperecyclerview.footerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;

/**
 * Created by t_xuz on 11/24/16.
 * a simple footerView
 * if you change footer view can extends BaseFooterView
 */

public class SimpleFooterView extends BaseFooterView {

    private ProgressBar mProgressbar;
    private TextView mFooterText;

    public SimpleFooterView(Context context) {
        this(context, null);
    }

    public SimpleFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        View view = inflate(context, R.layout.layout_footer_view, this);
        mProgressbar = (ProgressBar) view.findViewById(R.id.progressbar_footer_view);
        mFooterText = (TextView) view.findViewById(R.id.tv_footer_view);
    }

    @Override
    public void onNetChange(String message) {
        if (message == null) {
            showText(getResources().getString(R.string.footer_net_error));
        } else {
            showText(message);
        }
    }

    @Override
    public void onLoadingMore() {
        mProgressbar.setVisibility(VISIBLE);
        mFooterText.setVisibility(GONE);
    }

    @Override
    public void onRefreshing() {
        mProgressbar.setVisibility(GONE);
        mFooterText.setVisibility(GONE);
    }

    @Override
    public void onNoMore(String message) {
        if (message == null) {
            showText(getResources().getString(R.string.footer_no_more));
        } else {
            showText(message);
        }
    }

    @Override
    public void onError(String message) {
        if (message == null) {
            showText(getResources().getString(R.string.footer_error));
        } else {
            showText(message);
        }
    }

    private void showText(String message) {
        mProgressbar.setVisibility(GONE);
        mFooterText.setVisibility(VISIBLE);
        mFooterText.setText(message);
    }
}
