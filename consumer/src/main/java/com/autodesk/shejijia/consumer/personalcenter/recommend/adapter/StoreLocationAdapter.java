package com.autodesk.shejijia.consumer.personalcenter.recommend.adapter;

import android.content.Context;

import com.autodesk.shejijia.consumer.base.adapter.CommonAdapter;
import com.autodesk.shejijia.consumer.base.adapter.CommonViewHolder;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.MallAddressEntity;

import java.util.List;

/**
 * @User: 蜡笔小新
 * @date: 16-10-27
 * @GitHub: https://github.com/meikoz
 */

public class StoreLocationAdapter extends CommonAdapter<MallAddressEntity> {
    
    public StoreLocationAdapter(Context context, List<MallAddressEntity> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, MallAddressEntity entity) {

    }
}
