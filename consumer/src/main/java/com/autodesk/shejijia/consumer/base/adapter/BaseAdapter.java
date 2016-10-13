package com.autodesk.shejijia.consumer.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * @author   Malidong .
 * @version  v1.0 .
 * @date       2016-6-6 .
 * @file          BaseAdapter.java .
 * @brief       适配器的基类 .
 */
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {

    protected ReentrantReadWriteLock mLock;
    protected ImageLoader mImageLoader;
    protected Context mContext;
    protected List<T> mDatas;
    private View itemView;
    protected onClickChild onClickChildListener;

    protected MemberEntity mMemberEntity;
//    protected String xToken;
//    protected String memberType;
//    protected String ascToken;

    public interface onClickChild<T> {

        void onClickChild1(T bean);

        void onClickChild2(T bean);

        void onClickChild3(T bean);
    }

    public void setOnClickChildListener(onClickChild listener) {
        this.onClickChildListener = listener;
    }

    public BaseAdapter(Context context, List<T> datas) {
        super();
        mLock = new ReentrantReadWriteLock();
        mContext = context;
        mDatas = datas;
        mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (mMemberEntity == null) {
            return;
        }
//        xToken = mMemberEntity.getHs_accesstoken();
//        memberType = mMemberEntity.getMember_type();
//        ascToken = mMemberEntity.getAcs_token();
    }


    public BaseAdapter(Context context, List<T> datas, ImageLoader imageLoader) {
        super();
        mLock = new ReentrantReadWriteLock();
        mContext = context;
        mDatas = datas;
        mImageLoader = imageLoader;
        mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (mMemberEntity == null) {
            return;
        }
//        xToken = mMemberEntity.getHs_accesstoken();
//        memberType = mMemberEntity.getMember_type();
//        ascToken = mMemberEntity.getAcs_token();
    }

    @Override
    public int getCount() {
        final ReadLock lock = mLock.readLock();
        try {
            lock.lock();
            return mDatas != null ? mDatas.size() : 0;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public T getItem(int position) {
        final ReadLock lock = mLock.readLock();
        try {
            lock.lock();
            return mDatas != null ? mDatas.get(position) : null;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void appendData(List<T> data) {
        final WriteLock lock = mLock.writeLock();
        try {
            lock.lock();
            if (mDatas == null) {
                mDatas = data;
            } else {
                mDatas.addAll(data);
            }
        } finally {
            lock.unlock();
        }
        notifyDataSetChanged();
    }

    public void refreshData(List<T> data) {
        refreshDataWithoutNotify(data);
        notifyDataSetChanged();
    }


    public void refreshDataWithoutNotify(List<T> data) {
        final WriteLock lock = mLock.writeLock();
        try {
            lock.lock();
            if (mDatas == null) {
                mDatas = data;
            } else {
                mDatas.clear();
                if (data != null && !data.isEmpty()) {
                    mDatas.addAll(data);
                }
            }

        } finally {
            lock.unlock();
        }
    }


    public void clearUnSystemData() {
        final WriteLock lock = mLock.writeLock();
        try {
            lock.lock();

        } finally {
            lock.unlock();
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder mHolder;

        if (convertView == null) {
            try {
                convertView = LayoutInflater.from(mContext).inflate(getLayoutId(), null);
            } catch (Exception e) {
                e.printStackTrace();
            }

            mHolder = initHolder(convertView);

            convertView.setTag(mHolder);
        } else {
            mHolder = (Holder) convertView.getTag();
        }
        initItem(convertView, mHolder, position);

        return convertView;
    }

    public List<T> getData() {
        return mDatas;
    }

    public void setData(List<T> list) {
        this.mDatas = list;
    }

    public abstract int getLayoutId();

    public abstract Holder initHolder(View container);

    public abstract void initItem(View view, Holder holder, int position);

    public class Holder {
    }
}
