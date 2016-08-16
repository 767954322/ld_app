package com.autodesk.shejijia.consumer.personalcenter.resdecoration.adapter;

import android.app.Activity;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.DecorationNeedsListBean;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.listviewdelegate.ItemViewDelegate;
import com.autodesk.shejijia.shared.framework.adapter.CommonViewHolder;

/**
 * <p>Description:家装订单,北舒套餐布局 </p>
 *
 * @author liuhea
 * @date 16/8/16
 *  
 */
public class DecorationBeiShuDelegate implements ItemViewDelegate<DecorationNeedsListBean> {

    ///is_beishu:0 北舒套餐 1 非北舒.
    private static final String IS_NOT_BEI_SHU = "1";
    private Activity mActivity;

    public DecorationBeiShuDelegate(Activity activity) {
        mActivity = activity;

    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_decoration_beishu;
    }

    @Override
    public boolean isForViewType(DecorationNeedsListBean needsListBean, int position) {
        return !IS_NOT_BEI_SHU.equals(needsListBean.getIs_beishu());
    }

    @Override
    public void convert(CommonViewHolder holder, DecorationNeedsListBean decorationNeedsListBean, int position) {

    }
}
