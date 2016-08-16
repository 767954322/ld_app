package com.autodesk.shejijia.consumer.personalcenter.resdecoration.listviewdelegate;


import com.autodesk.shejijia.shared.framework.adapter.CommonViewHolder;

/**
 * <p>Description:用于区分不同布局的特定方法 </p>
 *
 * @author liuhea
 * @date 16/8/17
 *  
 */
public interface ItemViewDelegate<T> {

    public abstract int getItemViewLayoutId();

    public abstract boolean isForViewType(T item, int position);

    public abstract void convert(CommonViewHolder holder, T t, int position);


}
