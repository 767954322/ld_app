package com.autodesk.shejijia.consumer.personalcenter.recommend.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendBrandsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendMallsBean;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.listviewdelegate.ItemViewDelegate;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.listviewdelegate.MultiItemViewHolder;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 新建推荐清单详情
 *
 * @author liuhea
 *         created at 16-10-27
 */

public class RecommendListEditNewDelegate implements ItemViewDelegate<RecommendBrandsBean> {
    private Activity mActivity;

    public RecommendListEditNewDelegate(Activity activity) {
        mActivity = activity;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_brand_edit_view;
    }

    @Override
    public boolean isForViewType(RecommendBrandsBean recommendBrandsBean, int position) {
        return StringUtils.isEmpty(recommendBrandsBean.getSource()) || "0".equalsIgnoreCase(recommendBrandsBean.getSource());
    }

    @Override
    public void convert(MultiItemViewHolder holder, final RecommendBrandsBean recommendBrandsBean, int position) {
        StringBuffer mallName = new StringBuffer();
        for (RecommendMallsBean mallsBean : recommendBrandsBean.getMalls()) {
            mallName.append(mallsBean.getMall_name() + "、");
        }
        holder.setText(R.id.tv_brand_name, recommendBrandsBean.getBrand_name());
        holder.setText(R.id.et_brand_num, recommendBrandsBean.getAmountAndUnit());
        holder.setText(R.id.et_brand_dimension, recommendBrandsBean.getDimension());
        holder.setText(R.id.et_brand_remarks, recommendBrandsBean.getRemarks());
        holder.setText(R.id.tv_brand_mall_name, mallName.substring(0, mallName.length() - 1));

        final Spinner spinnerApartment = holder.getView((R.id.spinner_brand_apartment));
        String[] apartmentArray = UIUtils.getStringArray(R.array.recommend_apartments);
        final List<String> apartmentList = Arrays.asList(apartmentArray);
        final ArrayAdapter<String> apartmentArrayAdapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item, apartmentList);
        apartmentArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerApartment.setAdapter(apartmentArrayAdapter);
        String apartment = recommendBrandsBean.getApartment();
        for (int i = 0; i < apartmentList.size(); i++) {
            if (!StringUtils.isEmpty(apartment) && apartment.equalsIgnoreCase(apartmentList.get(i))) {
                spinnerApartment.setSelection(i, true);
            }
        }
        spinnerApartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String currentApartmentName = apartmentArrayAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holder.setOnClickListener(R.id.tv_brand_change, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String brand_name = recommendBrandsBean.getBrand_name();
                Toast.makeText(mActivity, "品牌变更：" + brand_name, Toast.LENGTH_SHORT).show();
            }
        });
    }
}