package com.autodesk.shejijia.consumer.personalcenter.recommend.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.base.adapter.CommonAdapter;
import com.autodesk.shejijia.consumer.base.adapter.CommonViewHolder;
import com.autodesk.shejijia.consumer.personalcenter.recommend.activity.DetailsViewCategoryActivity;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendBrandsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendMallsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendSCFDBean;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

import java.util.List;

/**
 * @User: 蜡笔小新
 * @date: 16-10-25
 * @GitHub: https://github.com/meikoz
 */

public class DcRecommendDetailsAdapter extends CommonAdapter<RecommendSCFDBean> {
    private LayoutInflater mInflater;
    private String mscfd;

    public DcRecommendDetailsAdapter(Context context, List<RecommendSCFDBean> datas, int layoutId, String scfd) {
        super(context, datas, layoutId);
        mInflater = LayoutInflater.from(context);
        this.mscfd = scfd;
    }

    @Override
    public void convert(final CommonViewHolder holder, RecommendSCFDBean item) {
        holder.setText(R.id.tv_category_name, UIUtils.substring(item.getSub_category_3d_name(), 4));
        holder.setOnClickListener(R.id.tv_category_name, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailsViewCategoryActivity.jumpTo((Activity) mContext, mscfd, holder.getPosition());
            }
        });
        LinearLayout llBrandView = holder.getView(R.id.ll_brand_view);
        List<RecommendBrandsBean> brands = item.getBrands();
        llBrandView.removeAllViews();
        for (int i = 0; i < brands.size(); i++) {
            RecommendBrandsBean bean = brands.get(i);
            View mItemView;
            if (StringUtils.isEmpty(bean.getSource()) || bean.getSource().equals("1")) {
                mItemView = mInflater.inflate(R.layout.item_brand_view, null);
            } else {
                mItemView = mInflater.inflate(R.layout.item_brand_logo_view, null);
            }
            updateView2ItemData(StringUtils.isEmpty(bean.getSource()) || item.getSource().equals("1"), mItemView, bean);
            llBrandView.addView(mItemView);
        }
    }

    private void updateView2ItemData(boolean hasFrom3D, View mItemView, RecommendBrandsBean bean) {
        TextView tvBrandName = (TextView) mItemView.findViewById(R.id.tv_brand_name);
        if (hasFrom3D) {
            TextView tvBrandNum = (TextView) mItemView.findViewById(R.id.tv_brand_num);
            tvBrandNum.setText(bean.getAmountAndUnit());
        } else {
            ImageView mBrandLogo = (ImageView) mItemView.findViewById(R.id.iv_brand_logo);
            ImageUtils.loadImageIcon(mBrandLogo, bean.getLogo_url());
        }
        TextView storeLocation = (TextView) mItemView.findViewById(R.id.tv_store_location);
        storeLocation.setVisibility(View.INVISIBLE);
        TextView tvBrandDimension = (TextView) mItemView.findViewById(R.id.tv_brand_dimension);
        TextView tvBrandApartment = (TextView) mItemView.findViewById(R.id.tv_brand_apartment);
        TextView tvBrandRemarks = (TextView) mItemView.findViewById(R.id.tv_brand_remarks);
        TextView tvBrandMallName = (TextView) mItemView.findViewById(R.id.tv_brand_mall_name);
        tvBrandName.setText(bean.getBrand_name());
        tvBrandDimension.setText(bean.getDimension());
        tvBrandApartment.setText(UIUtils.getNoStringIfEmpty(bean.getApartment()));
        tvBrandRemarks.setText(bean.getRemarks());
        StringBuffer mallName = new StringBuffer();
        for (RecommendMallsBean mallsBean : bean.getMalls()) {
            mallName.append(mallsBean.getMall_name() + "、");
        }
        tvBrandMallName.setText(mallName.substring(0, mallName.length() - 1));
    }
}
