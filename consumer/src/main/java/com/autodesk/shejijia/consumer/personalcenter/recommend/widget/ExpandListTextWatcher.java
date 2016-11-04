package com.autodesk.shejijia.consumer.personalcenter.recommend.widget;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by liuhe on 16-11-4.
 */
public class ExpandListTextWatcher implements TextWatcher {
    //由于TextWatcher的afterTextChanged中拿不到对应的position值，所以自己创建一个子类
    private int mPosition;
    private int groupPosition;
    private TextWatcherCallback textWatcherCallBack;

    public void updatePosition(int groupPosition, int position) {
        this.mPosition = position;
        this.groupPosition = groupPosition;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        textWatcherCallBack.onCallBackData(groupPosition, mPosition, s.toString());
    }

    public void addListener(TextWatcherCallback textWatcherCallBack) {
        this.textWatcherCallBack = textWatcherCallBack;
    }

}