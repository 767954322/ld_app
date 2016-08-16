package com.autodesk.shejijia.consumer.personalcenter.consumer.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.DecorationNeedsListBean;
import com.autodesk.shejijia.shared.framework.adapter.CommonAdapter;
import com.autodesk.shejijia.shared.framework.adapter.CommonViewHolder;

import java.util.List;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-8-15 .
 * @file ConsumerDecorationAdapter.java .
 * @brief 消费者家装订单适配器 .
 */
public class ConsumerDecorationAdapter extends CommonAdapter<DecorationNeedsListBean> implements
        View.OnClickListener {

    private Context mContext;
    private List<DecorationNeedsListBean> mDecorationNeedsList;

    public ConsumerDecorationAdapter(Context context, List<DecorationNeedsListBean> datas) {
        super(context, datas, R.layout.fragment_consumer_decoration);
        mContext = context;
        mDecorationNeedsList = datas;
    }

    @Override
    public void convert(CommonViewHolder holder, DecorationNeedsListBean decorationNeedsListBean) {

        String province_name = decorationNeedsListBean.getProvince_name();
        String city_name = decorationNeedsListBean.getCity_name();
        String district_name = decorationNeedsListBean.getDistrict_name();


        holder.setText(R.id.tv_decoration_name, decorationNeedsListBean.getCommunity_name());
        holder.setText(R.id.tv_decoration_needs_id, decorationNeedsListBean.getNeeds_id());
        holder.setText(R.id.tv_decoration_house_type, decorationNeedsListBean.getHouse_type());
        holder.setText(R.id.tv_decoration_address, province_name + city_name + district_name);


        holder.setOnClickListener(R.id.rl_bidder_count, this);
        holder.setOnClickListener(R.id.tv_decoration_detail, this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_bidder_count:
                Toast.makeText(mContext, "应标人数详情", Toast.LENGTH_SHORT).show();
                break;

            case R.id.tv_decoration_detail:
                Toast.makeText(mContext, "应标人数详情", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
