package com.autodesk.shejijia.consumer.personalcenter.designer.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;
import com.autodesk.shejijia.shared.framework.adapter.BaseAdapter;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.TransactionRecordBean;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

import java.util.List;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 16-6-7
 * @file TransactionRecordAdapter.java  .
 * @brief 交易记录.
 */
public class TransactionRecordAdapter extends BaseAdapter<TransactionRecordBean.DesignerTransListEntity> {

    public TransactionRecordAdapter(Context context, List<TransactionRecordBean.DesignerTransListEntity> datas) {
        super(context, datas);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_lv_transation_record;
    }

    @Override
    public Holder initHolder(View container) {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.tv_project_name = (TextView) container.findViewById(R.id.tv_my_property_project_name);
        viewHolder.tv_project_time = (TextView) container.findViewById(R.id.tv_my_property_project_time);
        viewHolder.tv_order_reference = (TextView) container.findViewById(R.id.tv_my_property_order_reference);
        viewHolder.tv_transaction_name = (TextView) container.findViewById(R.id.tv_my_property_transaction_name);
        viewHolder.tv_transaction_type = (TextView) container.findViewById(R.id.tv_my_property_transaction_type);
        viewHolder.tv_transaction_amount = (TextView) container.findViewById(R.id.tv_my_property_transaction_amount);
        return viewHolder;
    }

    @Override
    public void initItem(View view, Holder holder, int position) {

        if (null != mDatas && null != mDatas.get(position)) {
            String name = mDatas.get(position).getName();
            if (!TextUtils.isEmpty(name)) {
                ((ViewHolder) holder).tv_project_name.setText(name);
            }

            String order_line_id = mDatas.get(position).getOrder_line_id() + "";
            if (!TextUtils.isEmpty(order_line_id)) {
                ((ViewHolder) holder).tv_order_reference.setText(order_line_id);
            }

            String title = mDatas.get(position).getTitle();
            if (!TextUtils.isEmpty(title)) {
                ((ViewHolder) holder).tv_transaction_name.setText(title);
            }

            long create_date = mDatas.get(position).getCreate_date();
            String data = DateUtil.showDate(create_date);
            ((ViewHolder) holder).tv_project_time.setText(data);

            double adjustment = mDatas.get(position).getAdjustment();
            ((ViewHolder) holder).tv_transaction_amount.setText("¥" + adjustment);

            String type = mDatas.get(position).getType();
            if (!TextUtils.isEmpty(type)) {
                if (type.equals("0")) {
                    ((ViewHolder) holder).tv_transaction_type.setText(UIUtils.getString(R.string.no_payment));
                } else if (type.equals("1")) {
                    ((ViewHolder) holder).tv_transaction_type.setText(UIUtils.getString(R.string.paid));
                } else if (type.equals("2")) {
                    ((ViewHolder) holder).tv_transaction_type.setText(UIUtils.getString(R.string.transfer_room_design));
                } else if (type.equals("3")) {
                    ((ViewHolder) holder).tv_transaction_type.setText(UIUtils.getString(R.string.has_been_booked));
                }
            }

        }


    }

    public class ViewHolder extends Holder {
        TextView tv_project_name;
        TextView tv_project_time;
        TextView tv_transaction_name;
        TextView tv_transaction_amount;
        TextView tv_order_reference;
        TextView tv_transaction_type;
    }
}
