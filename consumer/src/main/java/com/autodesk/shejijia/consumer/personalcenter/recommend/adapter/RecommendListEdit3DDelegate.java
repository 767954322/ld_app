package com.autodesk.shejijia.consumer.personalcenter.recommend.adapter;

import android.app.Activity;
import android.widget.ImageView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendBrandsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendMallsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendSCFDBean;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.listviewdelegate.ItemViewDelegate;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.listviewdelegate.MultiItemViewHolder;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

/**
 * 基于BOM单的推荐清单详情
 *
 * @author liuhea
 *         created at 16-10-27
 */

public class RecommendListEdit3DDelegate implements ItemViewDelegate<RecommendBrandsBean> {
    public RecommendListEdit3DDelegate(Activity activity, RecommendSCFDBean recommendSCFDBean) {
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_brand_edit_logo_view;
    }

    @Override
    public boolean isForViewType(RecommendBrandsBean recommendBrandsBean, int position) {
        return null != recommendBrandsBean && "1".equalsIgnoreCase(recommendBrandsBean.getSource());
    }

    @Override
    public void convert(MultiItemViewHolder holder, RecommendBrandsBean recommendBrandsBean, int position) {
        holder.setText(R.id.tv_brand_name, recommendBrandsBean.getName());

        ImageView mBrandLogo = holder.getView((R.id.iv_brand_logo));
        if (StringUtils.isEmpty(mBrandLogo)){
            mBrandLogo.setImageDrawable(UIUtils.getDrawable(R.drawable.shejjijiaicon_ico));
        }else {
            ImageUtils.loadImageIcon(mBrandLogo, recommendBrandsBean.getLogo_url());
        }

        holder.setText(R.id.tv_brand_num, recommendBrandsBean.getAmountAndUnit() + "个");
        holder.setText(R.id.tv_brand_dimension, recommendBrandsBean.getDimension());
        holder.setText(R.id.tv_brand_apartment, recommendBrandsBean.getApartment());
        holder.setText(R.id.tv_brand_remarks, recommendBrandsBean.getRemarks());
        StringBuffer mallName = new StringBuffer();
        for (RecommendMallsBean mallsBean : recommendBrandsBean.getMalls()) {
            mallName.append(mallsBean.getMall_name() + "、");
        }
        holder.setText(R.id.tv_brand_mall_name, mallName.substring(0, mallName.length() - 1));

    }
}
