package com.autodesk.shejijia.consumer.personalcenter.resdecoration.listviewdelegate;


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

    public abstract void convert(MultiItemViewHolder holder, T t, int position);


}
