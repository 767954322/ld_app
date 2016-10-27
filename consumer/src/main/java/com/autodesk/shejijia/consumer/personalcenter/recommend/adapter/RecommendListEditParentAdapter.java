package com.autodesk.shejijia.consumer.personalcenter.recommend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.base.adapter.CommonAdapter;
import com.autodesk.shejijia.consumer.base.adapter.CommonViewHolder;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendBrandsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendMallsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendSCFDBean;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

import java.util.Arrays;
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
        TextView tvBrandChange = (TextView) itemBrandView.findViewById(R.id.tv_brand_change);
        TextView tvBrandMallName = (TextView) itemBrandView.findViewById(R.id.tv_brand_mall_name);
        EditText etBrandNum = (EditText) itemBrandView.findViewById(R.id.et_brand_num);
        EditText etBrandDimension = (EditText) itemBrandView.findViewById(R.id.et_brand_dimension);
        final Spinner spinnerApartment = (Spinner) itemBrandView.findViewById(R.id.spinner_brand_apartment);
        EditText etBrandRemarks = (EditText) itemBrandView.findViewById(R.id.et_brand_remarks);
        tvBrandName.setText(recommendBrandsBean.getBrand_name());
        etBrandNum.setText(recommendBrandsBean.getAmountAndUnit());
        etBrandDimension.setText(recommendBrandsBean.getDimension());


        String[] apartmentArray = UIUtils.getStringArray(R.array.recommend_apartments);
        List<String> apartmentList = Arrays.asList(apartmentArray);
        final ArrayAdapter<String> apartmentArrayAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, apartmentList);
        apartmentArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerApartment.setAdapter(apartmentArrayAdapter);
        spinnerApartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String currentApartmentName = apartmentArrayAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        etBrandRemarks.setText(recommendBrandsBean.getRemarks());
        StringBuffer mallName = new StringBuffer();
        for (RecommendMallsBean mallsBean : recommendBrandsBean.getMalls()) {
            mallName.append(mallsBean.getMall_name() + "、");
        }
        tvBrandMallName.setText(mallName.substring(0, mallName.length() - 1));

        tvBrandChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "品牌变更", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
