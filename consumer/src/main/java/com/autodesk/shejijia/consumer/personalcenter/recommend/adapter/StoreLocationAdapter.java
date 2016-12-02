package com.autodesk.shejijia.consumer.personalcenter.recommend.adapter;

import android.content.Context;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.base.adapter.CommonAdapter;
import com.autodesk.shejijia.consumer.base.adapter.CommonViewHolder;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.MallAddressEntity;

import java.util.List;

/**
 * @User: 蜡笔小新
 * @date: 16-10-27
 * @GitHub: https://github.com/meikoz
 */

public class StoreLocationAdapter extends CommonAdapter<MallAddressEntity.MallAddressesBean> {

    public StoreLocationAdapter(Context context, List<MallAddressEntity.MallAddressesBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, MallAddressEntity.MallAddressesBean entity) {
        holder.setText(R.id.tv_category_name, entity.getStorefront_name());
        holder.setText(R.id.tv_office_hours, entity.getBusiness_hours());
        holder.setText(R.id.tv_mall_address, entity.getDetailed_address());
        holder.setText(R.id.tv_booth_address, entity.getDetailed_address());
        holder.setText(R.id.tv_route_line, entity.getRide_route());
        holder.setText(R.id.tv_mall_mobile, entity.getBooth_phone());
    }
}
