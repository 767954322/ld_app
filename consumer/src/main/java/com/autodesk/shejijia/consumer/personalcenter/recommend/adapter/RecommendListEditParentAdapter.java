package com.autodesk.shejijia.consumer.personalcenter.recommend.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.base.adapter.CommonAdapter;
import com.autodesk.shejijia.consumer.base.adapter.CommonViewHolder;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendBrandsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendSCFDBean;
import com.autodesk.shejijia.consumer.uielements.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * 编辑清单页面第一级
 *
 * @author liuhea
 *         created at 16-10-25
 */
public class RecommendListEditParentAdapter extends CommonAdapter<RecommendSCFDBean> {
    private LayoutInflater mInflater;

    public RecommendListEditParentAdapter(Context context, List<RecommendSCFDBean> datas) {
        super(context, datas, R.layout.item_recommend_list_parent_brand);

        mInflater = LayoutInflater.from(context);
    }

    @Override
    public void convert(CommonViewHolder holder, final RecommendSCFDBean recommendSCFDBean) {
        holder.setText(R.id.tv_category_name, recommendSCFDBean.getSub_category_3d_name());
        holder.setOnClickListener(R.id.tv_category_name, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, "快速定位", Toast.LENGTH_SHORT).show();
            }
        });

        holder.setOnClickListener(R.id.tv_create_brand, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "添加品牌", Toast.LENGTH_SHORT).show();
            }
        });

        holder.setOnClickListener(R.id.tv_delete_brand, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "删除品类：" + recommendSCFDBean.getSub_category_3d_name(), Toast.LENGTH_SHORT).show();
            }
        });

        ListViewForScrollView childListView = holder.getView(R.id.lv_child_brand_view);

        List<RecommendBrandsBean> brands = recommendSCFDBean.getBrands();
        RecommendListEditChildAdapter recommendListEditChildAdapter = new RecommendListEditChildAdapter((Activity) mContext, brands,recommendSCFDBean);
        childListView.setAdapter(recommendListEditChildAdapter);
    }
}
