package com.autodesk.shejijia.consumer.personalcenter.recommend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.base.adapter.CommonAdapter;
import com.autodesk.shejijia.consumer.base.adapter.CommonViewHolder;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.ScfdEntity;

import java.util.List;

/**
 * @User: 蜡笔小新
 * @date: 16-10-25
 * @GitHub: https://github.com/meikoz
 */

public class CsRecommendDetailsAdapter extends CommonAdapter<ScfdEntity> {
    private LayoutInflater mInflater;

    public CsRecommendDetailsAdapter(Context context, List<ScfdEntity> datas, int layoutId) {
        super(context, datas, layoutId);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public void convert(CommonViewHolder holder, ScfdEntity item) {
        holder.setText(R.id.tv_category_name, item.getSub_category_3d_name());
        LinearLayout llBrandView = holder.getView(R.id.ll_brand_view);
        List<ScfdEntity.BrandsBean> brands = item.getBrands();
        for (int i = 0; i < brands.size(); i++) {
            ScfdEntity.BrandsBean bean = brands.get(i);
            View mItemView;
            if (item.getSource().equals("1")) {
                mItemView = mInflater.inflate(R.layout.item_brand_logo_view, null);
            } else {
                mItemView = mInflater.inflate(R.layout.item_brand_view, null);
            }
            updateView2ItemData(item.getSource().equals("1"), mItemView, bean);
            llBrandView.addView(mItemView);
        }
    }

    private void updateView2ItemData(boolean isFrom3D, View mItemView, ScfdEntity.BrandsBean bean) {
        TextView tvBrandName = (TextView) mItemView.findViewById(R.id.tv_brand_name);
        if (!isFrom3D) {
            TextView tvBrandNum = (TextView) mItemView.findViewById(R.id.tv_brand_num);
            tvBrandNum.setText(bean.getAmountAndUnit() + "个");
        }
        TextView tvBrandDimension = (TextView) mItemView.findViewById(R.id.tv_brand_dimension);
        TextView tvBrandApartment = (TextView) mItemView.findViewById(R.id.tv_brand_apartment);
        TextView tvBrandRemarks = (TextView) mItemView.findViewById(R.id.tv_brand_remarks);
        TextView tvBrandMallName = (TextView) mItemView.findViewById(R.id.tv_brand_mall_name);
        tvBrandName.setText(bean.getBrand_name());
        tvBrandDimension.setText(bean.getDimension());
        tvBrandApartment.setText(bean.getApartment());
        tvBrandRemarks.setText(bean.getRemarks());
        StringBuffer mallName = new StringBuffer();
        for (ScfdEntity.BrandsBean.MallsBean mallsBean : bean.getMalls()) {
            mallName.append(mallsBean.getMall_name() + "、");
        }
        tvBrandMallName.setText(mallName.substring(0, mallName.length() - 1));
    }
}
