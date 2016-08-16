package com.autodesk.shejijia.consumer.personalcenter.resdecoration.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.DecorationNeedsListBean;

import java.util.List;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-8-15 .
 * @file DecorationConsumerAdapter.java .
 * @brief 消费者家装订单适配器 .
 */
public class DecorationConsumerAdapter extends android.widget.BaseAdapter {

    ///is_beishu:0 北舒套餐 1 非北舒.
    private static final String IS_NOT_BEI_SHU = "1";
    private static final int TYPE_IS_BEI_SHU = 0;
    private static final int TYPE_NOT_BEI_SHU = 1;

    private LayoutInflater mInflater;
    private List<DecorationNeedsListBean> mDecorationNeedsList;
    private ListView mDesignerListView;
    private DecorationDesignerListAdapter mDecorationDesignerListAdapter;

    private Activity mActivity;
    private Intent mIntent;

    public DecorationConsumerAdapter(Activity activity, List<DecorationNeedsListBean> mDecorationNeedsList) {
        mInflater = LayoutInflater.from(activity);
        mActivity = activity;
        this.mDecorationNeedsList = mDecorationNeedsList;
    }

    //    public DecorationConsumerAdapter(Activity activity, List<DecorationNeedsListBean> datas) {
//        super(activity, datas, R.layout.item_decoration_list);
//        mActivity = activity;
//        mDecorationNeedsList = datas;
//    }
//
//    @Override
//    public void convert(CommonViewHolder holder, DecorationNeedsListBean
//            decorationNeedsListBean) {
//        final ArrayList<DecorationBiddersBean> mBidders = (ArrayList<DecorationBiddersBean>) decorationNeedsListBean.getBidders();
//        final String mNeeds_id = decorationNeedsListBean.getNeeds_id();
//
//        String contacts_name = decorationNeedsListBean.getContacts_name();
//        String community_name = decorationNeedsListBean.getCommunity_name();
//
//        String province_name = decorationNeedsListBean.getProvince_name();
//        String city_name = decorationNeedsListBean.getCity_name();
//        String district_name = decorationNeedsListBean.getDistrict_name();
//
//        holder.setText(R.id.tv_decoration_name, contacts_name + "/" + community_name);
//        holder.setText(R.id.tv_decoration_needs_id, decorationNeedsListBean.getNeeds_id());
//        holder.setText(R.id.tv_decoration_house_type, decorationNeedsListBean.getHouse_type());
//        holder.setText(R.id.tv_decoration_address, province_name + city_name + district_name);
//        holder.setText(R.id.tv_decoration_phone, decorationNeedsListBean.getContacts_mobile());
//        holder.setText(R.id.tv_decoration_style, decorationNeedsListBean.getDecoration_style());
//        holder.setText(R.id.tv_bidder_count, decorationNeedsListBean.getBidder_count() + "人");
//        holder.setText(R.id.tv_decoration_end_day, " " + decorationNeedsListBean.getEnd_day() + " 天");
//
//        /**
//         * 订单状态
//         */
//        holder.setText(R.id.tv_decoration_state, decorationNeedsListBean.getIs_public());
//
//        /**
//         * 应标设计师列表
//         */
//        mDesignerListView = holder.getView(R.id.lv_decoration_bid);
//        if (null == mDecorationDesignerListAdapter) {
//            mDecorationDesignerListAdapter = new DecorationDesignerListAdapter(mActivity, mBidders, mNeeds_id);
//        }
//        mDesignerListView.setAdapter(mDecorationDesignerListAdapter);
//
//        holder.setOnClickListener(R.id.rl_bidder_count, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mIntent = new Intent(mContext, DecorationBidderActivity.class);
//                mIntent.putExtra(DecorationBidderActivity.BIDDER_KEY, mBidders);
//                mIntent.putExtra(Constant.ConsumerDecorationFragment.NEED_ID, mNeeds_id);
//                mActivity.startActivity(mIntent);
//            }
//        });
//        holder.setOnClickListener(R.id.tv_decoration_detail, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mIntent = new Intent(mActivity, DecorationDetailActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString(Constant.ConsumerDecorationFragment.NEED_ID, mNeeds_id);
//                mIntent.putExtras(bundle);
//                mActivity.startActivityForResult(mIntent, 0);
//            }
//        });
//    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DecorationNeedsListBean decorationNeedsListBean = mDecorationNeedsList.get(position);
        int itemViewType = getItemViewType(position);

        ViewHolder viewHolder = null;
        ViewHolderBeiShu viewHolderBeiShu = null;

        if (null == convertView) {
            switch (itemViewType) {
                case TYPE_NOT_BEI_SHU:
                    viewHolder = new ViewHolder();
                    convertView = mInflater.inflate(R.layout.item_decoration_beishu,
                            parent, false);
                    convertView.setTag(viewHolder);
                    break;
                case TYPE_IS_BEI_SHU:
                    viewHolderBeiShu = new ViewHolderBeiShu();
                    convertView = mInflater.inflate(R.layout.item_decoration_list,
                            parent, false);
                    convertView.setTag(viewHolderBeiShu);
                    break;
            }

        } else {
            switch (itemViewType) {
                case TYPE_NOT_BEI_SHU:
                    viewHolder = (ViewHolder) convertView.getTag();
                    break;
                case TYPE_IS_BEI_SHU:
                    viewHolderBeiShu = (ViewHolderBeiShu) convertView.getTag();
                    break;
            }
        }

        return convertView;
    }


    /**
     * 接受到消息为1，发送消息为0
     */
    @Override
    public int getItemViewType(int position) {
        DecorationNeedsListBean decorationNeedsListBean = mDecorationNeedsList.get(position);
        String is_beishu = decorationNeedsListBean.getIs_beishu();
        if (IS_NOT_BEI_SHU.equals(is_beishu)) {
            return TYPE_NOT_BEI_SHU;
        } else {
            return TYPE_IS_BEI_SHU;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return mDecorationNeedsList == null ? 0 : mDecorationNeedsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDecorationNeedsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 普通家装项目句柄
     */
    static class ViewHolder {
        public TextView createDate;
        public TextView name;
        public TextView content;
    }

    /**
     * 北舒句柄
     */
    static class ViewHolderBeiShu {
        public TextView createDate;
        public TextView name;
        public TextView content;
    }

}
