package com.autodesk.shejijia.consumer.personalcenter.recommend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.base.adapter.CommonAdapter;
import com.autodesk.shejijia.consumer.base.adapter.CommonViewHolder;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendBrandsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendMallsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendSCFDBean;

import java.util.List;

/**
 * 编辑清单页面
 *
 * @author liuhea
 *         created at 16-10-25
 */
public class RecommendListEditAdapter extends CommonAdapter<RecommendSCFDBean> {
    private LayoutInflater mInflater;

    public RecommendListEditAdapter(Context context, List<RecommendSCFDBean> datas) {
        super(context, datas, R.layout.item_recommend_details_brand);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public void convert(CommonViewHolder holder, RecommendSCFDBean recommendSCFDBean) {
        holder.setText(R.id.tv_category_name, recommendSCFDBean.getSub_category_3d_name());
        List<RecommendBrandsBean> brands = recommendSCFDBean.getBrands();
        LinearLayout lLBrandView = holder.getView(R.id.ll_brand_view);
        final int position = holder.getPosition();
        holder.setOnClickListener(R.id.ll_brand_view, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
            }
        });

        for (int i = 0; i < brands.size(); i++) {
            RecommendBrandsBean recommendBrandsBean = brands.get(i);
            View ItemBrandView = mInflater.inflate(R.layout.item_brand_edit_view, null);
            updateItemBrandView(ItemBrandView, recommendBrandsBean);
            lLBrandView.addView(ItemBrandView);
        }
    }

    private void updateItemBrandView(View itemBrandView, RecommendBrandsBean recommendBrandsBean) {
        TextView tvBrandName = (TextView) itemBrandView.findViewById(R.id.tv_brand_name);
        TextView tvBrandNum = (TextView) itemBrandView.findViewById(R.id.tv_brand_num);
        TextView tvBrandDimension = (TextView) itemBrandView.findViewById(R.id.tv_brand_dimension);
        TextView tvBrandApartment = (TextView) itemBrandView.findViewById(R.id.tv_brand_apartment);
        TextView tvBrandRemarks = (TextView) itemBrandView.findViewById(R.id.tv_brand_remarks);
        TextView tvBrandMallName = (TextView) itemBrandView.findViewById(R.id.tv_brand_mall_name);
        tvBrandName.setText(recommendBrandsBean.getBrand_name());
        tvBrandNum.setText(recommendBrandsBean.getAmountAndUnit() + "个");
        tvBrandDimension.setText(recommendBrandsBean.getDimension());
        tvBrandApartment.setText(recommendBrandsBean.getApartment());
        tvBrandRemarks.setText(recommendBrandsBean.getRemarks());
        StringBuffer mallName = new StringBuffer();
        for (RecommendMallsBean mallsBean : recommendBrandsBean.getMalls()) {
            mallName.append(mallsBean.getMall_name() + "、");
        }
        tvBrandMallName.setText(mallName.substring(0, mallName.length() - 1));

    }
}
