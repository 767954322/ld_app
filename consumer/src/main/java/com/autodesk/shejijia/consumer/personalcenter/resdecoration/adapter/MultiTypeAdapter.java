package com.autodesk.shejijia.consumer.personalcenter.resdecoration.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.autodesk.shejijia.shared.framework.adapter.CommonAdapter;
import com.autodesk.shejijia.shared.framework.adapter.CommonViewHolder;

import java.util.List;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: http://www.leediancn.com</p>
 *
 * @author he.liu .
 * @date 2016-08-16.
 */
public abstract class MultiTypeAdapter<T> extends CommonAdapter<T> {

    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private int layoutId;

    public MultiTypeAdapter(Context context, List<T> datas) {
        super(context, datas, -1);
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T t = mDatas.get(position);
        int currentViewType = getItemViewType(position);


        CommonViewHolder holder = CommonViewHolder.get(mContext, convertView, parent,
                layoutId, position);
        convert(holder, getItem(position));
        return holder.getConvertView();
    }

    
    public abstract void convert(CommonViewHolder holder, T t);

}
